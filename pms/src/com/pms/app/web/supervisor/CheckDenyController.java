package com.pms.app.web.supervisor;

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

import com.pms.app.entity.CheckDeny;
import com.pms.app.service.CheckDenyService;

@Controller
@RequestMapping(value = "/supervisor/checkDeny")
public class CheckDenyController {
	
	private Logger logger = LoggerFactory.getLogger(CheckDenyController.class);
	
	@Autowired private CheckDenyService checkDenyService;
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, @PageableDefaults(value = 50)Pageable pageable, String queryName, String queryValue) {
		model.addAttribute("queryName", queryName);
		model.addAttribute("queryValue", queryValue);
		model.addAttribute("page", checkDenyService.findAllLike(pageable, queryName, queryValue));
		return "supervisor/checkDeny/list";
	}
	
	
	@RequestMapping(value = "/add")
	public String add(Model model){
		return "supervisor/checkDeny/add";
	}
	
	
	@RequestMapping(value = "/save")
	public String save(CheckDeny CheckDeny, RedirectAttributes ra){
		try {
			checkDenyService.save(CheckDeny);
			ra.addFlashAttribute("messageOK", "保存成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			logger.error("监管员保存异常", e);
		}
		return "redirect:/supervisor/checkDeny/list";
	}
	
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable("id")String id, Model model){
		model.addAttribute("CheckDeny", checkDenyService.findById(id));
		return "supervisor/checkDeny/edit";
	}
	

	@RequestMapping(value = "/delete")
	public String delete(String[] idGroup, RedirectAttributes ra){
		try {
			checkDenyService.delete(idGroup);
			ra.addFlashAttribute("messageOK", "删除成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "删除失败！");
			logger.error("监管员保存异常", e);
		}
		return "redirect:/supervisor/checkDeny/list";
	}
	
	
	
}
