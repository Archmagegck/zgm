package com.pms.app.web.delegator;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pms.app.entity.Delegator;
import com.pms.app.service.SupervisionCustomerService;

@Controller
@RequestMapping(value = "/delegator/supervisorandsupervisoncustomer")
public class SupervisorAndSupervisionCustomerController {
	
	@Autowired private SupervisionCustomerService supervisionCustomerService;
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Pageable pageable, String supervisionCustomerId, HttpSession session) {
		Delegator delegator = (Delegator)session.getAttribute("user");
		model.addAttribute("page", supervisionCustomerService.findByDelegator(delegator,pageable));
		model.addAttribute("supervisionCustomerId", supervisionCustomerId);
		
		return "delegator/supervisionCustomerList";
	}
		
	
}
