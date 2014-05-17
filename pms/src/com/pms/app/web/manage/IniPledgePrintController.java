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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pms.app.entity.IniPledgeRecord;
import com.pms.app.entity.PledgeConfig;
import com.pms.app.service.IniPledgeRecordService;
import com.pms.app.service.PledgeConfigService;
import com.pms.app.service.PledgePurityService;


@Controller
@RequestMapping(value = "/manage/iniPledgePrint")
public class IniPledgePrintController {
	
	@Autowired private IniPledgeRecordService iniPledgeRecordService;
	@Autowired PledgeConfigService pledgeConfigService;
	@Autowired PledgePurityService pledgePurityService;
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) throws Exception {  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	    CustomDateEditor dateEditor = new CustomDateEditor(df, true);  
	    binder.registerCustomEditor(Date.class, dateEditor);
	} 
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model) {
		model.addAttribute("iniPledgeRecordList",iniPledgeRecordService.findAll());
		return "manage/iniPledgePrint/list";
	}

	@RequestMapping(value = "/{id}/printPledgeRecord")
	public String printPledgeRecord(@PathVariable("id")String id, Model model) {
		IniPledgeRecord iniPledgeRecord = iniPledgeRecordService.findById(id);
		model.addAttribute("iniPledgeRecord", iniPledgeRecord);
		model.addAttribute("detailList", iniPledgeRecord.getIniPledgeRecordDetails());
		
		PledgeConfig config = pledgeConfigService.findBySupervisionCustomerId(iniPledgeRecord.getSupervisionCustomer().getId());
		model.addAttribute("config", config);
		
		model.addAttribute("pledgePurityName", pledgePurityService.findOK().getName());
		
		DateTime now = new DateTime();
		model.addAttribute("year", now.getYear());
		model.addAttribute("month", now.getMonthOfYear());
		model.addAttribute("day", now.getDayOfMonth());
		
		return "manage/iniPledgePrint/printPledgeRecord";
	}
	
	@RequestMapping(value = "/{id}/updateInput")
	public String updateInput(@PathVariable("id")String id, Integer iyear, Integer imonth, Integer iday, String preCode, String proCode) {
		IniPledgeRecord iniPledgeRecord = iniPledgeRecordService.findById(id);
		DateTime dateTime = new DateTime(iyear, imonth, iday, 1 , 0);
		iniPledgeRecord.setRecDate(dateTime.toDate());
		iniPledgeRecord.setPreCode(preCode);
		iniPledgeRecord.setProCode(proCode);
		iniPledgeRecord.setWriteIn(1);
		iniPledgeRecordService.save(iniPledgeRecord);
		return "redirect:/manage/iniPledgePrint/" + id + "/printPledgeRecord";
	}
	
}
