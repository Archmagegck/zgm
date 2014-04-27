package com.pms.app.web.manage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import com.pms.app.entity.EnclosedConvey;
import com.pms.app.service.DelegatorService;
import com.pms.app.service.EnclosedConveyService;
import com.pms.app.service.WarehouseService;

/**
 * 封闭运输管理
 * @author gck
 *
 */
@Controller
@RequestMapping(value = "/manage/enclosedConvey")
public class EnclosedConveyController {
	
	private Logger logger = LoggerFactory.getLogger(SupervisorController.class);
	
	@Autowired DelegatorService delegatorService;
	@Autowired WarehouseService warehouseService;
	@Autowired EnclosedConveyService enclosedConveyService;
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) throws Exception {  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	    CustomDateEditor dateEditor = new CustomDateEditor(df, true);  
	    binder.registerCustomEditor(Date.class, dateEditor);
	}  
	
	@RequestMapping(value = "/add")
	public String add(Model model){
		model.addAttribute("delegatorList",delegatorService.findAll());
		model.addAttribute("warehouseList",warehouseService.findAll());
		return "manage/enclosedConvey/add";
	}
	
	@RequestMapping(value = "/save")
	public String save(Model model, Pageable pageable, EnclosedConvey enclosedConvey, RedirectAttributes ra){
		try{
		enclosedConveyService.save(enclosedConvey);
		}catch(Exception e){
			logger.error("封闭运输保存异常", e);
		}
		model.addAttribute("page",enclosedConveyService.findPage(pageable));
		model.addAttribute("delegatorList",delegatorService.findAll());
		return "redirect:/manage/enclosedConvey/list";
	}
	
	@RequestMapping(value = "/list")
	public String list(Model model, Pageable pageable, String delegatorId){
		if(delegatorId!=null)
			model.addAttribute("page",enclosedConveyService.findPageByDelegatorId(pageable, delegatorId));
		else
			model.addAttribute("page",enclosedConveyService.findPage(pageable));
		model.addAttribute("delegatorList",delegatorService.findAll());
		return "manage/enclosedConvey/list";
	}
	
	@RequestMapping(value = "/delete/{id}")
	public String delete(Model model, Pageable pageable, @PathVariable("id")String id){
		enclosedConveyService.delete(enclosedConveyService.findById(id));
		model.addAttribute("delegatorList",delegatorService.findAll());
		model.addAttribute("page",enclosedConveyService.findPage(pageable));
		return "manage/enclosedConvey/list";
	}
}
