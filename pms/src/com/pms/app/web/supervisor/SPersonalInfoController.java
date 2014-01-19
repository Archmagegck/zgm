package com.pms.app.web.supervisor;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.Supervisor;
import com.pms.app.service.SupervisorService;

@Controller
@RequestMapping("/supervisor/personalInfo")
public class SPersonalInfoController {
	
	private Logger logger = LoggerFactory.getLogger(SPersonalInfoController.class);
	
	@Autowired SupervisorService supervisorService = new SupervisorService();
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Pageable pageable, HttpSession session ) {
		Supervisor supervisor = (Supervisor)session.getAttribute("user");
		model.addAttribute("supervisor", supervisor);
		return "supervisor/personalInfo/list";
	}
	
	
	@RequestMapping(value = "/save")
	public String save( RedirectAttributes ra, String newpassword , String username, HttpSession session){
		try {			
				Supervisor supervisor = (Supervisor)session.getAttribute("user");
				supervisor.setUsername(username);
				supervisor.setPassword(newpassword);
				supervisorService.save(supervisor);			
				ra.addFlashAttribute("messageOK", "修改成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "修改失败！");
			logger.error("监管员保存异常", e);
		}
		return "redirect:/supervisor/personalInfo/list";
	}
	
	
	@RequestMapping(value = "/edit")
	public String edit(Model model, HttpSession session){
		Supervisor supervisor = (Supervisor)session.getAttribute("user");
		model.addAttribute("supervisor", supervisor);
		return "supervisor/personalInfo/edit";
	}

}
