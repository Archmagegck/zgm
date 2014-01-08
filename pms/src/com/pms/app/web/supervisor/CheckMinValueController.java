package com.pms.app.web.supervisor;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/supervisor/checkMinValue")
public class CheckMinValueController {
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, HttpSession session) {
		model.addAttribute("checkMinValueList", null);
		return "supervisor/checkMinValue/list";
	}
	
}
