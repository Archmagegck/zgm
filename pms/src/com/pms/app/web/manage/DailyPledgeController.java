package com.pms.app.web.manage;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pms.app.entity.ExtendedCheck;
import com.pms.app.entity.ExtendedCheckDetail;
import com.pms.app.entity.InsCheck;
import com.pms.app.entity.InsCheckDetail;
import com.pms.app.entity.InventoryCheck;
import com.pms.app.entity.InventoryCheckDetail;
import com.pms.app.entity.PledgeConfig;
import com.pms.app.entity.PledgeRecord;
import com.pms.app.entity.PledgeRecordDetail;
import com.pms.app.entity.Stock;
import com.pms.app.entity.reference.CheckMethod;
import com.pms.app.service.DelegatorService;
import com.pms.app.service.ExtendedCheckService;
import com.pms.app.service.InsCheckService;
import com.pms.app.service.InventoryCheckService;
import com.pms.app.service.PledgeConfigService;
import com.pms.app.service.PledgePurityService;
import com.pms.app.service.PledgeRecordService;
import com.pms.app.service.StockService;
import com.pms.app.service.SupervisionCustomerService;

@Controller
@RequestMapping(value = "/manage/dailyPledge")
public class DailyPledgeController {
	
	@Autowired DelegatorService delegatorService;
	@Autowired PledgeRecordService pledgeRecordService;
	@Autowired SupervisionCustomerService supervisionCustomerService;
	
	@Autowired InventoryCheckService inventoryCheckService;
	@Autowired InsCheckService insCheckService;
	@Autowired ExtendedCheckService extendedCheckService;
	@Autowired StockService stockService;
	@Autowired PledgeConfigService pledgeConfigService;
	@Autowired PledgePurityService pledgePurityService;
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) throws Exception {  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	    CustomDateEditor dateEditor = new CustomDateEditor(df, true);  
	    binder.registerCustomEditor(Date.class, dateEditor);
	}  
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, String delegatorId, String supervisionCustomerId) {
		model.addAttribute("delegatorList", delegatorService.findAll());
		model.addAttribute("delegatorId", delegatorId);
		model.addAttribute("supervisionCustomerList", supervisionCustomerService.findAll());
		model.addAttribute("supervisionCustomerId", supervisionCustomerId);
		model.addAttribute("pledgeRecordList", pledgeRecordService.findListByQuery(delegatorId, supervisionCustomerId, new Date()));
		return "manage/dailyPledge/list";
	}
	
	@RequestMapping(value = "/{id}/details")
	public String detailList(Model model, @PathVariable("id")String id) {
		PledgeRecord pledgeRecord = pledgeRecordService.findById(id);
		model.addAttribute("pledgeRecord", pledgeRecord);
		String warehouseId = pledgeRecord.getWarehouse().getId();
		Date date = pledgeRecord.getDate();
		
		List<InventoryCheckDetail> inventoryDetails = new ArrayList<InventoryCheckDetail>();
		InventoryCheck inventoryCheck = inventoryCheckService.findByWarehouseDateDay(warehouseId, date);
		if(inventoryCheck != null) inventoryDetails = inventoryCheck.getInventoryCheckDetails();
		
		List<InsCheckDetail> insCheckDetails = new ArrayList<InsCheckDetail>();
		InsCheck insCheck = insCheckService.findByWarehouseDateDay(warehouseId, date);
		if(insCheck != null)  insCheckDetails = insCheck.getInsCheckDetails();
		
		List<ExtendedCheckDetail> extendedCheckDetails = new ArrayList<ExtendedCheckDetail>();
		ExtendedCheck extendedCheck = extendedCheckService.findByWarehouseDateDay(warehouseId, date);
		if(extendedCheck != null) extendedCheckDetails = extendedCheck.getExtendedCheckDetails();
		
		List<Stock> stocks = stockService.findByWarehouseId(warehouseId);
		
		List<PledgeRecordDetail> pledgeRecordDetails = pledgeRecord.getPledgeRecordDetails();
		for (PledgeRecordDetail pledgeRecordDetail : pledgeRecordDetails) {
			double gpWeight = 0.0;
			double rjWeight = 0.0;
			for (InventoryCheckDetail inv : inventoryDetails) {
				if(inv.getStyle().getId().equals(pledgeRecordDetail.getStyle().getId())) {
					gpWeight += inv.getWeight();
				}
			}
			for (InsCheckDetail insCheckDetail : insCheckDetails) {
				if(insCheckDetail.getStyle().getId().equals(pledgeRecordDetail.getStyle().getId())) {
					if(insCheckDetail.getCheckMethod() == CheckMethod.Spectrum) {
						gpWeight += insCheckDetail.getCheckWeight();
					}
					if(insCheckDetail.getCheckMethod() == CheckMethod.Dissolve) {
						rjWeight += insCheckDetail.getCheckWeight();
					}
				}
			}
			for (ExtendedCheckDetail extendedCheckDetail : extendedCheckDetails) {
				if(extendedCheckDetail.getStyle().getId().equals(pledgeRecordDetail.getStyle().getId())) {
					if(extendedCheckDetail.getCheckMethod() == CheckMethod.Spectrum) {
						gpWeight += extendedCheckDetail.getCheckWeight();
					}
					if(extendedCheckDetail.getCheckMethod() == CheckMethod.Dissolve) {
						rjWeight += extendedCheckDetail.getCheckWeight();
					}
				}
			}
			double stockWeight = 1;
			for(Stock stock : stocks) {
				if(stock.getStyle().getId().equals(pledgeRecordDetail.getStyle().getId())) {
					stockWeight = stock.getSumWeight();
				}
			}
			
			pledgeRecordDetail.setSpectrumRate(new BigDecimal(gpWeight / stockWeight).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
			pledgeRecordDetail.setDissolveRate(new BigDecimal(rjWeight / stockWeight).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
		}
		
		model.addAttribute("detailList", pledgeRecordDetails);
		
		PledgeConfig config = pledgeConfigService.findBySupervisionCustomerId(pledgeRecord.getSupervisionCustomer().getId());
		model.addAttribute("config", config);
		
		model.addAttribute("pledgePurityName", pledgePurityService.findOK().getName());
		
		DateTime now = new DateTime();
		model.addAttribute("year", now.getYear());
		model.addAttribute("month", now.getMonthOfYear());
		model.addAttribute("day", now.getDayOfMonth());
		
		return "manage/dailyPledge/detailList";
	}
	
	
	@RequestMapping(value = "/{id}/print")
	public String print(@PathVariable("id")String id, Model model) {
		PledgeRecord pledgeRecord = pledgeRecordService.findById(id);
		model.addAttribute("pledgeRecord", pledgeRecord);
		return "manage/dailyPledge/print";
	}
	
	@RequestMapping(value = "/{id}/updateInput")
	public String updateInput(@PathVariable("id")String id, Integer iyear, Integer imonth, Integer iday, String preCode, String proCode) {
		PledgeRecord pledgeRecord = pledgeRecordService.findById(id);
		DateTime dateTime = new DateTime(iyear, imonth, iday, 1 , 0);
		pledgeRecord.setRecDate(dateTime.toDate());
		pledgeRecord.setPreCode(preCode);
		pledgeRecord.setProCode(proCode);
		pledgeRecord.setWriteIn(1);
		pledgeRecordService.save(pledgeRecord);
		return "redirect:/manage/dailyPledge/" + id + "/details";
	}
	
	
	
	
}
