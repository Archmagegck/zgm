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

import com.pms.app.service.DailyStockService;
import com.pms.app.service.DelegatorService;

@Controller
@RequestMapping(value = "/manage/dailyStock")
public class DailyStockController {
	
	@Autowired DelegatorService delegatorService;
	@Autowired DailyStockService dailyStockService;
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) throws Exception {  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    CustomDateEditor dateEditor = new CustomDateEditor(df, true);
	    binder.registerCustomEditor(Date.class, dateEditor);
	}  
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, String delegatorId) {
		model.addAttribute("date", new DateTime().toString("yyyy-MM-dd"));
		model.addAttribute("delegatorList", delegatorService.findAll());
		model.addAttribute("delegatorId", delegatorId);
		model.addAttribute("stockMap", dailyStockService.queryByDelegatorAndDate(delegatorId));
		return "manage/dailyStock/list";
	}
	
	
}
