package com.pms.app.web.delegator;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pms.app.entity.InsRecord;
import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.service.InsRecordService;
import com.pms.app.service.StockService;
import com.pms.app.service.SupervisionCustomerService;

@Controller
@RequestMapping("/delegator/ins")
public class InsDetailController {
	
	@Autowired StockService stockService;
	@Autowired SupervisionCustomerService supervisionCustomerService;
	@Autowired InsRecordService insRecordService;
	
	@RequestMapping(value = "/{SCId}/{insId}")
	public String list(Model model, Pageable pageable, @PathVariable("SCId")String SCId, @PathVariable("insId")String insId, HttpSession session) {
		InsRecord insRecord = insRecordService.findById(insId);
		SupervisionCustomer supervisionCustomer = supervisionCustomerService.findById(SCId);
		
		model.addAttribute("insRecord", insRecord);
		model.addAttribute("detailList", insRecord.getInsRecordDetails());
		model.addAttribute("supervisionCustomer", supervisionCustomer);
		
		return "delegator/insDetailList";
	}
}
