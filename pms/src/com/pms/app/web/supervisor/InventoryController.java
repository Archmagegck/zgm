package com.pms.app.web.supervisor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.Inventory;
import com.pms.app.entity.InventoryDetail;
import com.pms.app.entity.Supervisor;
import com.pms.app.entity.Warehouse;
import com.pms.app.service.InventoryService;
import com.pms.app.service.StyleService;
import com.pms.app.service.WarehouseService;
import com.pms.base.service.ServiceException;

@Controller
@RequestMapping(value = "/supervisor/inventory")
@SuppressWarnings("unchecked")
public class InventoryController {
	
	private Logger logger = LoggerFactory.getLogger(InventoryController.class);
	
	@Autowired private InventoryService inventoryService;
	@Autowired private StyleService styleService;
	@Autowired private WarehouseService warehouseService;
	
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
		model.addAttribute("page", inventoryService.findPageByQuery(pageable, (String)session.getAttribute("warehouse"), dateTime.toDate()));
		return "supervisor/inventory/list";
	}
	
	
	@RequestMapping(value = "/{id}/details")
	public String detailList(Model model, @PathVariable("id")String id){
		Inventory inventory = inventoryService.findById(id);
		model.addAttribute("inventory", inventory);
		model.addAttribute("detailList", inventory.getInventoryDetails());
		return "supervisor/inventory/detailList";
	}
	
	
	@RequestMapping(value = "/addList")
	public String addList(Model model, HttpSession session){
		model.addAttribute("inventoryDetailList", (List<InventoryDetail>) session.getAttribute("inventoryDetailList"));
		return "supervisor/inventory/addList";
	}
	
	
	@RequestMapping(value = "/add")
	public String add(Model model){
		model.addAttribute("styleList", styleService.findAll());
		return "supervisor/inventory/add";
	}
	
	
	@RequestMapping(value = "/save")
	public String save(InventoryDetail inventoryDetail, HttpSession session){
		List<InventoryDetail> inventoryDetailList = new ArrayList<InventoryDetail>();
		if(session.getAttribute("inventoryDetailList") != null) {
			inventoryDetailList = (List<InventoryDetail>) session.getAttribute("inventoryDetailList");
		}
		inventoryDetailList.add(inventoryDetail);
		session.setAttribute("inventoryDetailList", inventoryDetailList);
		return "redirect:/supervisor/inventory/addList";
	}
	
	@RequestMapping(value = "/edit/{index}")
	public String edit(@PathVariable("index")Integer index, Model model, HttpSession session){
		List<InventoryDetail> inventoryDetailList = (List<InventoryDetail>) session.getAttribute("inventoryDetailList");
		model.addAttribute("inventoryDetail", inventoryDetailList.get(index));
		model.addAttribute("index", index);
		model.addAttribute("styleList", styleService.findAll());
		return "supervisor/inventory/edit";
	}
	
	@RequestMapping(value = "/update/{index}")
	public String update(@PathVariable("index")Integer index, InventoryDetail inventoryDetail, HttpSession session){
		List<InventoryDetail> inventoryDetailList = (List<InventoryDetail>) session.getAttribute("inventoryDetailList");
		InventoryDetail invOld = inventoryDetailList.get(index);
		invOld.update(inventoryDetail);
		return "redirect:/supervisor/inventory/addList";
	}
	
	
	@RequestMapping(value = "/del/{index}")
	public String del(@PathVariable("index")Integer index, HttpSession session){
		System.out.println("inventoryController.del()" + index);
		List<InventoryDetail> inventoryDetailList = (List<InventoryDetail>) session.getAttribute("inventoryDetailList");
		inventoryDetailList.remove(index - 1);
		session.setAttribute("inventoryDetailList", inventoryDetailList);
		return "redirect:/supervisor/inventory/addList";
	}
	
	
	@RequestMapping(value = "/saveList")
	public String saveList(HttpSession session, RedirectAttributes ra){
		try {
			Warehouse warehouse = warehouseService.findById((String)session.getAttribute("warehouseId"));
			List<InventoryDetail> inventoryDetailList = (List<InventoryDetail>) session.getAttribute("inventoryDetailList");
			inventoryService.save(inventoryDetailList ,warehouse, (Supervisor) session.getAttribute("user"));
			ra.addFlashAttribute("messageOK", "保存成功！");
		} catch (ServiceException e) {
			ra.addFlashAttribute("messageErr", "盘存不一致，无法保存！");
			logger.error("保存异常", e);
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			logger.error("保存异常", e);
		}
		return "redirect:/supervisor/inventory/list";
	}
	
	
}
