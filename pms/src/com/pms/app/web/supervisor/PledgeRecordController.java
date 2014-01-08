package com.pms.app.web.supervisor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.Stock;
import com.pms.app.entity.Warehouse;
import com.pms.app.service.PledgeRecordService;
import com.pms.app.service.StockService;

@Controller
@RequestMapping(value = "/supervisor/pledgeRecord")
public class PledgeRecordController {
	
	private Logger logger = LoggerFactory.getLogger(PledgeRecordController.class);
	
	@Autowired private PledgeRecordService pledgeRecordService;
	@Autowired private StockService stockService;
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) throws Exception {  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	    CustomDateEditor dateEditor = new CustomDateEditor(df, true);  
	    binder.registerCustomEditor(Date.class, dateEditor);
	}  
	
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Pageable pageable, Date date, HttpSession session) {
		DateTime dateTime = new DateTime();
		if(date != null) dateTime = new DateTime(date);
		model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
		model.addAttribute("page", pledgeRecordService.findPageByQuery(pageable, ((Warehouse)session.getAttribute("warehouse")).getId(), date));
		return "supervisor/pledgeRecord/list";
	}
	
	
	@RequestMapping(value = "add")
	public String add(Model model, HttpSession session){
		model.addAttribute("stockList", stockService.findByWarehouseId(((Warehouse)session.getAttribute("warehouse")).getId()));
		return "supervisor/pledgeRecord/add";
	}
	
	
	@RequestMapping(value = "save")
	public String save(HttpSession session, RedirectAttributes ra){
		try {
			String supervisionCustomerCode = (String)session.getAttribute("supervisionCustomerCode");
			Warehouse warehouse = (Warehouse)session.getAttribute("warehouse");
			List<Stock> stocks = stockService.findByWarehouseId(warehouse.getId());
			pledgeRecordService.save(supervisionCustomerCode, warehouse, stocks);
		} catch (Exception e) {
			logger.error("保存失败", e);
			ra.addFlashAttribute("messageErr", "保存失败！");
			return "redirect:/supervisor/pledgeRecord/add";
		}
		return "redirect:/supervisor/pledgeRecord/list";
	}
	
	
}
