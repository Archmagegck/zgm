package com.pms.app.web.manage;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.Admin;
import com.pms.app.service.AdminService;

@Controller
@RequestMapping("/manage/personalInfo")
public class PersonalInfoController {
	
	private Logger logger = LoggerFactory.getLogger(PersonalInfoController.class);
	
	@Autowired AdminService adminService = new AdminService();
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Pageable pageable, HttpSession session ) {
		Admin admin = (Admin)session.getAttribute("user");
		model.addAttribute("admin", admin);
		return "manage/personalInfo/list";
	}
	
	
	@RequestMapping(value = "/save")
	public String save( RedirectAttributes ra, String newpassword, HttpSession session){
		try {			
				Admin admin = (Admin)session.getAttribute("user");
				admin.setPassword(newpassword);
				adminService.save(admin);			
				ra.addFlashAttribute("messageOK", "修改密码成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "修改密码失败！");
			logger.error("监管经理保存异常", e);
		}
		return "redirect:/manage/personalInfo/list";
	}
	
	
	@RequestMapping(value = "/edit")
	public String edit(Model model, HttpSession session){
		return "manage/personalInfo/edit";
	}

}
