package com.pms.app.web.supervisor;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.Supervisor;
import com.pms.app.entity.Warehouse;
import com.pms.app.service.CheckMinValueService;
import com.pms.app.service.SupervisionCustomerService;

@Controller
@RequestMapping(value = "/supervisor/checkMinValue")
public class CheckMinValueController {
	
	@Autowired CheckMinValueService checkMinValueService;
	@Autowired SupervisionCustomerService supervisionCustomerService;
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, HttpSession session) {
		Supervisor supervisor = (Supervisor)session.getAttribute("user");
		SupervisionCustomer customer = supervisionCustomerService.findBySupervisorId(supervisor.getId());
		String warehouseId = ((Warehouse)session.getAttribute("warehouse")).getId();
		model.addAttribute("stockMinValue", checkMinValueService.getStockMinValue(warehouseId));
		model.addAttribute("warehouseMinValue", checkMinValueService.getWarehouseMinValue(customer.getId()));
		return "supervisor/checkMinValue/list";
	}
	
}
