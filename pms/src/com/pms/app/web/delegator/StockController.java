package com.pms.app.web.delegator;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.Warehouse;
import com.pms.app.service.StockService;
import com.pms.app.service.SupervisionCustomerService;

@Controller
@RequestMapping("/delegator/stock")
public class StockController {
	
	@Autowired StockService stockService;
	@Autowired SupervisionCustomerService supervisionCustomerService;
	
	@RequestMapping(value = "/{id}")
	public String list(Model model, Pageable pageable, @PathVariable("id")String id, HttpSession session) {
		SupervisionCustomer supervisionCustomer = supervisionCustomerService.findById(id);
		Warehouse warehouse = supervisionCustomer.getWarehouse();
		
		
		model.addAttribute("stocksList", stockService.findTotalStockByWarehouseId(warehouse.getId()));
		model.addAttribute("supervisionCustomer",supervisionCustomer);
	
		session.setAttribute("warehouse", warehouse);

		return "delegator/stockList";
	}
}
