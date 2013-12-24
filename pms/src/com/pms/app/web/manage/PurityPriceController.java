package com.pms.app.web.manage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.PurityPrice;
import com.pms.app.service.PurityPriceService;

@Controller
@RequestMapping(value = "/manage/purityPrice")
public class PurityPriceController {
	
	private Logger logger = LoggerFactory.getLogger(PurityPriceController.class);
	
	@Autowired private PurityPriceService purityPriceService;
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Pageable pageable, String queryName, String queryValue) {
		model.addAttribute("queryName", queryName);
		model.addAttribute("queryValue", queryValue);
		model.addAttribute("page", purityPriceService.findAllLike(pageable, queryName, queryValue));
		return "manage/purityPrice/list";
	}
	
	
	@RequestMapping(value = "/add")
	public String add(Model model){
		return "manage/purityPrice/add";
	}
	
	
	@RequestMapping(value = "/save")
	public String save(PurityPrice PurityPrice, RedirectAttributes ra){
		try {
			purityPriceService.save(PurityPrice);
			ra.addFlashAttribute("messageOK", "保存成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			logger.error("保存异常", e);
		}
		return "redirect:/manage/purityPrice/list";
	}
	
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable("id")String id, Model model){
		model.addAttribute("purityPrice", purityPriceService.findById(id));
		return "manage/purityPrice/edit";
	}
	

	@RequestMapping(value = "/delete")
	public String delete(String[] idGroup, RedirectAttributes ra){
		try {
			purityPriceService.delete(idGroup);
			ra.addFlashAttribute("messageOK", "删除成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "删除失败！");
			logger.error("删除异常", e);
		}
		return "redirect:/manage/purityPrice/list";
	}
	
	
	
}
