package com.pms.app.web.delegator;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pms.app.entity.Delegator;
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
@RequestMapping(value = "/delegator/pledgeRecord/{id}")
public class DelegatorPledgeRecordController {
	@Autowired DelegatorService delegatorService;
	@Autowired SupervisionCustomerService supervisionCustomerService;
	@Autowired PledgeRecordService pledgeRecordService;
	@Autowired InventoryCheckService inventoryCheckService;
	@Autowired PledgeConfigService pledgeConfigService;
	@Autowired InsCheckService insCheckService;
	@Autowired ExtendedCheckService extendedCheckService;
	@Autowired StockService stockService;
	@Autowired PledgePurityService pledgePurityService;
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) throws Exception {  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    CustomDateEditor dateEditor = new CustomDateEditor(df, true);
	    binder.registerCustomEditor(Date.class, dateEditor);
	}  
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Pageable pageable, @PathVariable("id")String id, Date date, HttpSession session) {
		DateTime dateTime = new DateTime();
		if(date != null) {
			dateTime = new DateTime(date);
			model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
		}
		model.addAttribute("delegatorList", delegatorService.findAll());
		model.addAttribute("supervisionCustomerList", supervisionCustomerService.findAll());
		model.addAttribute("supervisionCustomerId", id);
		Delegator delegator = (Delegator)session.getAttribute("user");
		model.addAttribute("page", pledgeRecordService.findPageByQuery(pageable, delegator.getId(), id, date));
		return "delegator/pledgeRecord/list";
	}
	
	
	@RequestMapping(value = "/detail")
	public String printPledgeRecord(@PathVariable("id")String id, Model model) {
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
		
		return "delegator/pledgeRecord/detail";
	}
}
