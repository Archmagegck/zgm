package com.pms.app.web.manage;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pms.app.entity.Delegator;
import com.pms.app.service.DailyStockService;
import com.pms.app.service.DelegatorService;
import com.pms.app.service.SupervisionCustomerService;

@Controller
@RequestMapping(value = "/manage/dailyStock")
public class DailyStockController {
	
	@Autowired DelegatorService delegatorService;
	@Autowired DailyStockService dailyStockService;
	@Autowired SupervisionCustomerService supervisionCustomerService;
	@Autowired JavaMailSender javaMailSender;
	
	private Logger logger = LoggerFactory.getLogger(DailyStockController.class);
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) throws Exception {  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    CustomDateEditor dateEditor = new CustomDateEditor(df, true);
	    binder.registerCustomEditor(Date.class, dateEditor);
	}  
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, String delegatorId, String supervisionCustomerId) {
		model.addAttribute("date", new DateTime().toString("yyyy-MM-dd"));
		model.addAttribute("delegatorList", delegatorService.findAll());
		model.addAttribute("delegatorId", delegatorId);
		model.addAttribute("supervisionCustomerList", supervisionCustomerService.findAll());
		model.addAttribute("supervisionCustomerId", supervisionCustomerId);
		model.addAttribute("stockMap", dailyStockService.queryByDelegatorAndDate(delegatorId, supervisionCustomerId));
		return "manage/dailyStock/list";
	}
	
	@RequestMapping(value = "/list/toPrint")
	public String toPrint(Model model, String delegatorId, String supervisionCustomerId) {
		model.addAttribute("delegator", delegatorService.findById(delegatorId));
		model.addAttribute("date", new DateTime().toString("yyyy-MM-dd"));
		model.addAttribute("supervisionCustomerId", supervisionCustomerId);
		model.addAttribute("stockMap", dailyStockService.queryByDelegatorAndDate(delegatorId, supervisionCustomerId));
		return "manage/dailyStock/toPrint";
	}
	
	
	@RequestMapping(value = "/list/print")
	@ResponseBody
	public String print(Model model, String delegatorId, String supervisionCustomerId) {
		try {
			Delegator delegator = delegatorService.findById(delegatorId);
					
			File file = dailyStockService.generalRecordFile(delegator, supervisionCustomerId);
			
			MimeMessage msg = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true);
			helper.setFrom("hellodongdongmiao@163.com");
			helper.setTo(delegator.getEmail());
			helper.setSubject("总库存");
			helper.setText("您好！总库存报表见附件.", true);
			helper.addAttachment("总库存报表.xls", file);
			javaMailSender.send(msg);
			return "ok";
		} catch (MessagingException e) {
			logger.error("发送邮件异常!", e);
			return "error";
		} catch (Exception e) {
			logger.error("发送邮件发生未知异常!", e);
			return "error";
		}
	}
	
	
}
