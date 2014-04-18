package com.pms.app.web.supervisor;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pms.app.service.StockService;

@Controller
@RequestMapping(value = "/supervisor/realTimeStocks")
public class RealTimeStocksController {
	
	@Autowired StockService stockService;
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, HttpSession session) {
		model.addAttribute("realTimeStocksList", stockService.findAllEq("warehouse.id", (String)session.getAttribute("warehouseId")));
		return "supervisor/realTimeStocks/list";
	}
	
}
