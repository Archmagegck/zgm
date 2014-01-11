package com.pms.app.web.manage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pms.app.entity.vo.TotalStock;
import com.pms.app.service.StockService;

@Controller
@RequestMapping(value = "/manage/totalStocks")
public class TotalStocksController {
	
	@Autowired private StockService stockService;
	
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model) {
		List<TotalStock> totalStocks = stockService.findTotalList();
		double sumWeight = 0;
		double sumValue = 0;
		for (TotalStock totalStock : totalStocks) {
			sumWeight += totalStock.getSumWeight().doubleValue();
			sumValue += totalStock.getSumValue().doubleValue();
		}
		model.addAttribute("stockList", totalStocks);
		model.addAttribute("sumWeight", sumWeight);
		model.addAttribute("sumValue", sumValue);
		return "manage/totalStocks/list";
	}
	
	
	
	
}
