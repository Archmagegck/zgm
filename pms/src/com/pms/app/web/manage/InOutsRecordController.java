package com.pms.app.web.manage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pms.app.service.DelegatorService;
import com.pms.app.service.InOutsRecordService;
import com.pms.app.service.SupervisionCustomerService;

@Controller
@RequestMapping(value = "/manage/inOutsRecord")
public class InOutsRecordController {
	
	@Autowired DelegatorService delegatorService;
	@Autowired SupervisionCustomerService supervisionCustomerService;
	@Autowired InOutsRecordService inOutsRecordService;
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) throws Exception {  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    CustomDateEditor dateEditor = new CustomDateEditor(df, true);
	    binder.registerCustomEditor(Date.class, dateEditor);
	}  
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, String delegatorId, Date beginDate, Date endDate) {
		model.addAttribute("beginDate", new DateTime(beginDate).toString("yyyy-MM-dd"));
		model.addAttribute("endDate", new DateTime(endDate).toString("yyyy-MM-dd"));
		model.addAttribute("delegatorList", delegatorService.findAll());
//		model.addAttribute("delegator", delegatorService.findById(delegatorId));
		model.addAttribute("delegatorId", delegatorId);
		model.addAttribute("inoutsMap", inOutsRecordService.queryByDelegatorAndDateBetween(delegatorId, beginDate, endDate));
		return "manage/inOutsRecord/list";
	}
	
	
}
