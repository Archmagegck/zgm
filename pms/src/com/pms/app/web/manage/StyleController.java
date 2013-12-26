package com.pms.app.web.manage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.Style;
import com.pms.app.service.StyleService;

@Controller
@RequestMapping(value = "/manage/style")
public class StyleController {
	
	private Logger logger = LoggerFactory.getLogger(StyleController.class);
	
	@Autowired private StyleService styleService;
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, @PageableDefaults(value = 50)Pageable pageable, String queryName, String queryValue) {
		model.addAttribute("queryName", queryName);
		model.addAttribute("queryValue", queryValue);
		model.addAttribute("page", styleService.findAllLike(pageable, queryName, queryValue));
		return "manage/style/list";
	}
	
	
	@RequestMapping(value = "/add")
	public String add(Model model){
		return "manage/style/add";
	}
	
	
	@RequestMapping(value = "/save")
	public String save(Style Style, RedirectAttributes ra){
		try {
			styleService.save(Style);
			ra.addFlashAttribute("messageOK", "保存成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			logger.error("保存异常", e);
		}
		return "redirect:/manage/style/list";
	}
	
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable("id")String id, Model model){
		model.addAttribute("style", styleService.findById(id));
		return "manage/style/edit";
	}
	

	@RequestMapping(value = "/delete")
	public String delete(String[] idGroup, RedirectAttributes ra){
		try {
			styleService.delete(idGroup);
			ra.addFlashAttribute("messageOK", "删除成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "删除失败！");
			logger.error("删除异常", e);
		}
		return "redirect:/manage/style/list";
	}
	
	
	
}
