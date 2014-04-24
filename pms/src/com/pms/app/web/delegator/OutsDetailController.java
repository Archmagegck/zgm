package com.pms.app.web.delegator;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pms.app.entity.OutsRecord;
import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.service.OutsRecordService;
import com.pms.app.service.StockService;
import com.pms.app.service.SupervisionCustomerService;

@Controller
@RequestMapping("/delegator/outs")
public class OutsDetailController {
	
	@Autowired StockService stockService;
	@Autowired SupervisionCustomerService supervisionCustomerService;
	@Autowired OutsRecordService outsRecordService;
	
	@RequestMapping(value = "/{SCId}/{outsId}")
	public String list(Model model, Pageable pageable, @PathVariable("SCId")String SCId, @PathVariable("outsId")String outsId, HttpSession session) {
		OutsRecord outsRecord = outsRecordService.findById(outsId);
		SupervisionCustomer supervisionCustomer = supervisionCustomerService.findById(SCId);
		
		model.addAttribute("detailList", outsRecord.getOutsRecordDetails());
		model.addAttribute("outsRecord",outsRecord);
		model.addAttribute("supervisionCustomer", supervisionCustomer);
		
		return "delegator/outsDetailList";
	}
}
