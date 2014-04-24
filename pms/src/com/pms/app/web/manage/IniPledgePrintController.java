package com.pms.app.web.manage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import com.pms.app.entity.IniCheck;
import com.pms.app.entity.IniPledgeRecord;
import com.pms.app.entity.IniRecord;
import com.pms.app.entity.PledgeRecord;
import com.pms.app.entity.Warehouse;
import com.pms.app.service.IniCheckService;
import com.pms.app.service.IniPledgeRecordService;
import com.pms.app.service.IniRecordService;
import com.pms.app.service.PledgeRecordService;
import com.pms.app.service.StockService;


@Controller
@RequestMapping(value = "/manage/iniPledgePrint")
public class IniPledgePrintController {
	private Logger logger = LoggerFactory.getLogger(DailyPledgeController.class);
	
	@Autowired private PledgeRecordService pledgeRecordService;
	@Autowired private StockService stockService;
	@Autowired private IniRecordService iniRecordService;
	@Autowired private IniCheckService iniCheckService;
	@Autowired private IniPledgeRecordService iniPledgeRecordService;
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) throws Exception {  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	    CustomDateEditor dateEditor = new CustomDateEditor(df, true);  
	    binder.registerCustomEditor(Date.class, dateEditor);
	} 
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model) {
//		public String list(Model model, Pageable pageable, Date date, HttpSession session) {
//		DateTime dateTime = new DateTime();
//		if(date != null) {
//			dateTime = new DateTime(date);
//			model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
//		}
		//获取所有初始状态登记的仓库
		model.addAttribute("iniRecordList",iniRecordService.findTotalWarehouseList());		
		//model.addAttribute("page", pledgeRecordService.findPageByQuery(pageable, ((Warehouse)session.getAttribute("warehouse")).getId(), date));
		return "manage/iniPledgePrint/list";
	}
	
	
	@RequestMapping(value = "/{id}/printPledgeRecord")
	public String printPledgeRecord(@PathVariable("id")String id, Model model) {
		IniPledgeRecord iniPledgeRecord = iniPledgeRecordService.findById(id);
		model.addAttribute("iniPledgeRecord", iniPledgeRecord);
		model.addAttribute("detailList", iniPledgeRecord.getPledgeRecordDetails());
		return "manage/pledgeRecord/printPledgeRecord";
	}
}
