package com.pms.app.web.manage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pms.app.entity.PledgeRecord;
import com.pms.app.service.DelegatorService;
import com.pms.app.service.PledgeRecordService;
import com.pms.app.service.SupervisionCustomerService;

@Controller
@RequestMapping(value = "/manage/dailyPledge")
public class DailyPledgeController {
	
	@Autowired DelegatorService delegatorService;
	@Autowired PledgeRecordService pledgeRecordService;
	@Autowired SupervisionCustomerService supervisionCustomerService;
	
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
		model.addAttribute("detailList", pledgeRecord.getPledgeRecordDetails());
		return "manage/dailyPledge/detailList";
	}
	
	
	@RequestMapping(value = "/{id}/print")
	public String print(@PathVariable("id")String id, Model model, HttpSession session) {
		PledgeRecord pledgeRecord = pledgeRecordService.findById(id);
		model.addAttribute("pledgeRecord", pledgeRecord);
		return "manage/dailyPledge/print";
	}
	
	
}
