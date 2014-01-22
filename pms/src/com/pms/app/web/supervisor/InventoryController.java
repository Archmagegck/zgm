package com.pms.app.web.supervisor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.Inventory;
import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.Supervisor;
import com.pms.app.entity.Warehouse;
import com.pms.app.service.InventoryService;
import com.pms.app.service.StockService;
import com.pms.app.web.supervisor.form.InventoryForm;

@Controller
@RequestMapping(value = "/supervisor/inventory")
public class InventoryController {
	
	private Logger logger = LoggerFactory.getLogger(InventoryController.class);
	
	@Autowired private InventoryService inventoryService;
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
		model.addAttribute("page", inventoryService.findPageByQuery(pageable, ((Warehouse)session.getAttribute("warehouse")).getId(), dateTime.toDate()));
		return "supervisor/inventory/list";
	}
	
	
	@RequestMapping(value = "/{id}/details")
	public String detailList(Model model, @PathVariable("id")String id){
		Inventory inventory = inventoryService.findById(id);
		model.addAttribute("inventory", inventory);
		model.addAttribute("detailList", inventory.getInventoryDetails());
		return "supervisor/inventory/detailList";
	}
	
	
	@RequestMapping(value = "add")
	public String add(Model model, HttpSession session){
		model.addAttribute("stockList", stockService.findInStockByWarehouseId(((Warehouse)session.getAttribute("warehouse")).getId()));
		return "supervisor/inventory/add";
	}
	
	
	@RequestMapping(value = "save")
	public String save(Model model, InventoryForm inventoryForm, HttpSession session, RedirectAttributes ra){
		try {
			Warehouse warehouse = (Warehouse)session.getAttribute("warehouse");
			SupervisionCustomer supervisionCustomer = (SupervisionCustomer)session.getAttribute("supervisionCustomer");
			String supName = ((Supervisor)session.getAttribute("user")).getName();
			inventoryService.save(warehouse, supervisionCustomer, supName, inventoryForm.getInventoryDetails());
		} catch (Exception e) {
			logger.error("保存失败", e);
			ra.addFlashAttribute("messageErr", "保存失败！");
			return "redirect:/supervisor/inventory/add";
		}
		return "redirect:/supervisor/inventory/list";
	}
	
	
}
