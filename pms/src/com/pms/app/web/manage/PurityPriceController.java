package com.pms.app.web.manage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.service.PledgePurityService;
import com.pms.app.service.PurityPriceService;
import com.pms.app.web.manage.form.PurityPricesForm;

@Controller
@RequestMapping(value = "/manage/purityPrice")
public class PurityPriceController {
	
	private Logger logger = LoggerFactory.getLogger(PurityPriceController.class);
	
	@Autowired private PurityPriceService purityPriceService;
	@Autowired private PledgePurityService pledgePurityService;
	
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) throws Exception {  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	    CustomDateEditor dateEditor = new CustomDateEditor(df, true);  
	    binder.registerCustomEditor(Date.class, dateEditor);      
	}  
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Date date) {
		model.addAttribute("purityPriceList", purityPriceService.findListByDate(date == null ? new Date() : date));
		return "manage/purityPrice/list";
	}
	
	
	@RequestMapping(value = "/add")
	public String add(Model model){
		model.addAttribute("nowDate", new DateTime().toString("yyyy-MM-dd"));
		model.addAttribute("pledgePurityList", pledgePurityService.findAll());
		return "manage/purityPrice/add";
	}
	
	
	@RequestMapping(value = "/save")
	public String save(PurityPricesForm purityPricesForm, RedirectAttributes ra){
		try {
			purityPriceService.save(purityPricesForm.getPurityPrices());
			ra.addFlashAttribute("messageOK", "保存成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			logger.error("保存异常", e);
		}
		return "redirect:/manage/purityPrice/list";
	}
	
	
	
	
	
}
