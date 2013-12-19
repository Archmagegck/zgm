package com.pms.app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.User;
import com.pms.app.service.UserService;


@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired private UserService userService;
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Pageable pageable, String queryName, String queryValue) {
		model.addAttribute("queryName", queryName);
		model.addAttribute("queryValue", queryValue);
//		model.addAttribute("page", userService.findPageByQuery(pageable, userType, queryName, queryValue));
		return "user/list";
	}
	
	
	@RequestMapping(value = "/add")
	public String add(Model model){
		return "user/add";
	}
	
	
	@RequestMapping(value = "/save")
	public String save(User user, RedirectAttributes ra){
		try {
			userService.save(user);
			ra.addFlashAttribute("messageOK", "保存成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			e.printStackTrace();
		}
		return "redirect:/user/list";
	}
	
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable("id")String id, Model model){
		User user = userService.findById(id);
		model.addAttribute("user", user);
		return "user/edit";
	}
	

	@RequestMapping(value = "/delete")
	public String delete(String[] idGroup, RedirectAttributes ra){
		try {
			userService.delete(idGroup);
			ra.addFlashAttribute("messageOK", "删除成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "删除失败！");
			e.printStackTrace();
		}
		return "redirect:/user/list";
	}
}
