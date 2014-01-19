package com.pms.app.web.delegator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.Warehouse;
import com.pms.app.service.InsRecordService;
import com.pms.app.service.OutsRecordService;
import com.pms.app.service.SupervisionCustomerService;

@Controller
@RequestMapping("/delegator/insAndOuts/{id}")
public class InsAndOutsController {
	
	@Autowired SupervisionCustomerService supervisionCustomerService;
	@Autowired InsRecordService insRecordService;
	@Autowired OutsRecordService outsRecordService;
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) throws Exception {  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	    CustomDateEditor dateEditor = new CustomDateEditor(df, true);  
	    binder.registerCustomEditor(Date.class, dateEditor);      
	} 
	
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Pageable pageable, @PathVariable("id")String id, Date beginDate, Date endDate, Integer state, HttpSession session) {
		SupervisionCustomer supervisionCustomer = supervisionCustomerService.findById(id);
		model.addAttribute("supervisionCustomer",supervisionCustomer);
		
		Warehouse warehouse = supervisionCustomer.getWarehouse();
		session.setAttribute("warehouse", warehouse);

		if(state!=null && state==0){	//查询入库操作
			if(beginDate != null) {
				DateTime beginTime = new DateTime(beginDate);
				model.addAttribute("beginDate", beginTime.toString("yyyy-MM-dd"));
			}
			if(endDate != null) {
				DateTime endTime = new DateTime(endDate);
				model.addAttribute("endDate", endTime.toString("yyyy-MM-dd"));
			}
			model.addAttribute("page", insRecordService.findPageByQuery(pageable,  ((Warehouse)supervisionCustomer.getWarehouse()).getId(), beginDate, endDate));
		}
		else if(state!=null && state==1){	//查询出库操作
			if(beginDate != null) {
				DateTime beginTime = new DateTime(beginDate);
				model.addAttribute("beginDate", beginTime.toString("yyyy-MM-dd"));
			}
			if(endDate != null) {
				DateTime endTime = new DateTime(endDate);
				model.addAttribute("endDate", endTime.toString("yyyy-MM-dd"));
			}
			model.addAttribute("page", outsRecordService.findPageByQuery(pageable,  ((Warehouse)supervisionCustomer.getWarehouse()).getId(), beginDate, endDate));
		}
		
		model.addAttribute("state",state);
		return "delegator/insAndOutsList";
	}
}
