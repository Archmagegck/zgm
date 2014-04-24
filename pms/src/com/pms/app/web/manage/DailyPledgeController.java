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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.IniPledgeRecord;
import com.pms.app.entity.Stock;
import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.Warehouse;
import com.pms.app.service.PledgeRecordService;
import com.pms.app.service.StockService;


@Controller
@RequestMapping(value = "/manage/dailyPledge")
public class DailyPledgeController {

private Logger logger = LoggerFactory.getLogger(DailyPledgeController.class);
	
	@Autowired private PledgeRecordService pledgeRecordService;
	@Autowired private StockService stockService;
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) throws Exception {  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	    CustomDateEditor dateEditor = new CustomDateEditor(df, true);  
	    binder.registerCustomEditor(Date.class, dateEditor);
	}
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Pageable pageable,String supervisionCustomerId,String delegatorId) {
		DateTime dateTime = new DateTime();	
		model.addAttribute("supervisionCustomerId", supervisionCustomerId);
		model.addAttribute("delegatorId", delegatorId);
		//1.从仓库表中查出所有仓库
		//2.从盘存表看当天每个仓库的盘存情况（盘存/未盘存）
		//3.并可通过委托方、监管客户查询
//		modle.addAttribute("dailyPledgeList",dailyPledgeService.findBySupervisionIdAndCustomerIdAndDate(supervisionCustomerId,delegatorId,dateTime));
		return "manage/dailyPledge/list";
	}
	
	@RequestMapping(value = "/{id}/printPledgeRecord")
	public String printPledgeRecord(@PathVariable("id")String id, Model model) {
		DateTime dateTime = new DateTime();	
		//id 为监管客户Id
		//1.根据监管客户，当天时间
		//2.生成质物清单及明细
		//3.查询该质物清单及明细
////		IniPledgeRecord iniPledgeRecord = iniPledgeRecordService.findBySupervisionCustomerAndDate(id,dateTime);
//		model.addAttribute("iniPledgeRecord", iniPledgeRecord);
//		model.addAttribute("detailList", iniPledgeRecord.getPledgeRecordDetails());
		return "manage/dailyPledge/printPledgeRecord";
	}

	
}
