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

import com.pms.app.entity.PledgePurity;
import com.pms.app.service.PledgePurityService;

@Controller
@RequestMapping(value = "/manage/pledgePurity")
public class PledgePurityController {
	
	private Logger logger = LoggerFactory.getLogger(PledgePurityController.class);
	
	@Autowired private PledgePurityService pledgePurityService;
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Pageable pageable, String queryName, String queryValue) {
		model.addAttribute("queryName", queryName);
		model.addAttribute("queryValue", queryValue);
		model.addAttribute("page", pledgePurityService.findAllLike(pageable, queryName, queryValue));
		return "manage/pledgePurity/list";
	}
	
	
	@RequestMapping(value = "/add")
	public String add(Model model){
		return "manage/pledgePurity/add";
	}
	
	
	@RequestMapping(value = "/save")
	public String save(PledgePurity pledgePurity, RedirectAttributes ra){
		try {
			pledgePurityService.save(pledgePurity);
			ra.addFlashAttribute("messageOK", "保存成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			logger.error("监管员保存异常", e);
		}
		return "redirect:/manage/pledgePurity/list";
	}
	
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable("id")String id, Model model){
		model.addAttribute("pledgePurity", pledgePurityService.findById(id));
		return "manage/pledgePurity/edit";
	}
	

	@RequestMapping(value = "/delete")
	public String delete(String[] idGroup, RedirectAttributes ra){
		try {
			pledgePurityService.delete(idGroup);
			ra.addFlashAttribute("messageOK", "删除成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "删除失败！");
			logger.error("监管员保存异常", e);
		}
		return "redirect:/manage/pledgePurity/list";
	}
	
	
	
}
