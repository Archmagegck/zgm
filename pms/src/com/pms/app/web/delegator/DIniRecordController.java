package com.pms.app.web.delegator;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.service.IniRecordService;
import com.pms.app.service.SupervisionCustomerService;

@Controller
@RequestMapping("/delegator/inirecord/{id}")
public class DIniRecordController {

	@Autowired IniRecordService iniRecordService;
	@Autowired SupervisionCustomerService supervisionCustomerService;
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Pageable pageable, HttpSession session, @PathVariable("id")String id) {
		SupervisionCustomer supervisionCustomer = supervisionCustomerService.findById(id);
		model.addAttribute("iniRecordList", iniRecordService.findAllEq("warehouse.id", supervisionCustomer.getWarehouse().getId()));
		return "delegator/iniRecordList";
	}
}
