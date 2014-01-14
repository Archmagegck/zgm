package com.pms.app.web.supervisor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.PledgeRecord;
import com.pms.app.entity.Stock;
import com.pms.app.entity.Warehouse;
import com.pms.app.service.PledgeRecordService;
import com.pms.app.service.StockService;
import com.pms.app.util.UploadUtils;

@Controller
@RequestMapping(value = "/supervisor/pledgeRecord")
public class PledgeRecordController {
	
	private Logger logger = LoggerFactory.getLogger(PledgeRecordController.class);
	
	@Autowired private PledgeRecordService pledgeRecordService;
	@Autowired private StockService stockService;
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) throws Exception {  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	    CustomDateEditor dateEditor = new CustomDateEditor(df, true);  
	    binder.registerCustomEditor(Date.class, dateEditor);
	}  
	
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Pageable pageable, Date date, HttpSession session) {
		DateTime dateTime = new DateTime();
		if(date != null) {
			dateTime = new DateTime(date);
			model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
		}
		model.addAttribute("page", pledgeRecordService.findPageByQuery(pageable, ((Warehouse)session.getAttribute("warehouse")).getId(), date));
		return "supervisor/pledgeRecord/list";
	}
	
	
	@RequestMapping(value = "add")
	public String add(Model model, HttpSession session){
		model.addAttribute("stockList", stockService.findByWarehouseId(((Warehouse)session.getAttribute("warehouse")).getId()));
		return "supervisor/pledgeRecord/add";
	}
	
	
	@RequestMapping(value = "save")
	public String save(HttpSession session, RedirectAttributes ra){
		try {
			String supervisionCustomerCode = (String)session.getAttribute("supervisionCustomerCode");
			Warehouse warehouse = (Warehouse)session.getAttribute("warehouse");
			List<Stock> stocks = stockService.findByWarehouseId(warehouse.getId());
			pledgeRecordService.save(supervisionCustomerCode, warehouse, stocks);
		} catch (Exception e) {
			logger.error("保存失败", e);
			ra.addFlashAttribute("messageErr", "保存失败！");
			return "redirect:/supervisor/pledgeRecord/add";
		}
		return "redirect:/supervisor/pledgeRecord/list";
	}
	
	
	@RequestMapping(value = "/{id}/printPledgeRecord")
	public String printPledgeRecord(@PathVariable("id")String id, Model model) {
		PledgeRecord pledgeRecord = pledgeRecordService.findById(id);
		model.addAttribute("pledgeRecord", pledgeRecord);
		model.addAttribute("detailList", pledgeRecord.getPledgeRecordDetails());
		return "supervisor/pledgeRecord/printPledgeRecord";
	}
	
	
	@RequestMapping(value = "/{id}/toUpload")
	public String toUpload(@PathVariable("id")String id, Model model){
		model.addAttribute("id", id);
		return "supervisor/pledgeRecord/toUpload";
	}
	
	
	@RequestMapping(value = "/uploadAttach")
	public String upload(HttpServletRequest request, String pledgeRecordId, Model model, RedirectAttributes ra){
		String supervisionCustomerCode = (String)request.getSession().getAttribute("supervisionCustomerCode");
		try {
			PledgeRecord pledgeRecord = pledgeRecordService.findById(pledgeRecordId);
			pledgeRecord = UploadUtils.uploadPledgeRecordFile(request, pledgeRecord, supervisionCustomerCode);
			pledgeRecord.setIfUpload(1);
			pledgeRecordService.save(pledgeRecord);
			ra.addFlashAttribute("messageOK", "上传文件成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "上传文件出现异常！");
			logger.error("上传文件出现异常", e);
		}
		return "redirect:/supervisor/pledgeRecord/list";
	}
	
	
}
