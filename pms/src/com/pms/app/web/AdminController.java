package com.pms.app.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.User;
import com.pms.app.service.UserService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = {""})
	public String admin(){
		return "login/login";
	}
	
	@RequestMapping(value = "/top")
	public String top(){
		return "main/top";
	}
	
	@RequestMapping(value = "/left")
	public String left(){
		return "main/left";
	}
	
	@RequestMapping(value = "/right")
	public String right(Model model, Pageable pageable, String queryName, String queryValue){
		return "main/right";
	}
	
	@RequestMapping(value = "/login")
	public String login(RedirectAttributes ra, String username, String password, HttpSession session){
		List<User> list = userService.findByLoginNameAndPassword(username, password);
		if(!list.isEmpty()){
			if(list.size() == 1){
				User user = list.get(0);
				session.setAttribute("user", user);
				return "main/main";
			}
		}
		ra.addFlashAttribute("message", "账号或密码错误！");
		return "redirect:/admin";
	}
	
	@RequestMapping(value = "/exit")
	public String exit(HttpServletRequest request){
		request.getSession().invalidate();
		return "login/login";
	}
}
