package com.pms.app.web.manage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import com.pms.app.entity.PledgeRecord;
import com.pms.app.service.DelegatorService;
import com.pms.app.service.PledgeRecordService;
import com.pms.app.service.SupervisionCustomerService;

@Controller
@RequestMapping(value = "/manage/pledgeRecord")
public class ManagePledgeRecordController {
	
	@Autowired DelegatorService delegatorService;
	@Autowired SupervisionCustomerService supervisionCustomerService;
	@Autowired PledgeRecordService pledgeRecordService;
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) throws Exception {  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    CustomDateEditor dateEditor = new CustomDateEditor(df, true);
	    binder.registerCustomEditor(Date.class, dateEditor);
	}  
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Pageable pageable, String delegatorId, String supervisionCustomerId, Date date) {
		DateTime dateTime = new DateTime();
		if(date != null) {
			dateTime = new DateTime(date);
			model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
		}
		model.addAttribute("delegatorList", delegatorService.findAll());
		model.addAttribute("supervisionCustomerList", supervisionCustomerService.findAll());
		model.addAttribute("delegatorId", delegatorId);
		model.addAttribute("supervisionCustomerId", supervisionCustomerId);
		model.addAttribute("page", pledgeRecordService.findPageByQuery(pageable, delegatorId, supervisionCustomerId, date));
		return "manage/pledgeRecord/list";
	}
	
	
	@RequestMapping(value = "/{id}/detail")
	public String printPledgeRecord(@PathVariable("id")String id, Model model) {
		PledgeRecord pledgeRecord = pledgeRecordService.findById(id);
		model.addAttribute("pledgeRecord", pledgeRecord);
		model.addAttribute("detailList", pledgeRecord.getPledgeRecordDetails());
		return "manage/pledgeRecord/detail";
	}
	
}
