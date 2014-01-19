package com.pms.app.web.delegator;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.Delegator;
import com.pms.app.service.DelegatorService;

@Controller
@RequestMapping("/delegator/personalInfo")
public class DPersonalInfoController {
	
	private Logger logger = LoggerFactory.getLogger(DPersonalInfoController.class);
	
	@Autowired DelegatorService delegatorService = new DelegatorService();
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Pageable pageable, HttpSession session ) {
		Delegator delegator = (Delegator)session.getAttribute("user");
		model.addAttribute("delegator", delegator);
		return "delegator/personalInfo/list";
	}
	
	
	@RequestMapping(value = "/save")
	public String save( RedirectAttributes ra, String newpassword, HttpSession session){
		try {			
				Delegator delegator = (Delegator)session.getAttribute("user");
				delegator.setPassword(newpassword);
				delegatorService.save(delegator);			
				ra.addFlashAttribute("messageOK", "修改密码成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "修改密码失败！");
			logger.error("委托方保存异常", e);
		}
		return "redirect:/delegator/personalInfo/list";
	}
	
	
	@RequestMapping(value = "/edit")
	public String edit(Model model, HttpSession session){
		return "delegator/personalInfo/edit";
	}

}
