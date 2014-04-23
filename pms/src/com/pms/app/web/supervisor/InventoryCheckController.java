package com.pms.app.web.supervisor;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.InventoryCheck;
import com.pms.app.entity.InventoryCheckTemplate;
import com.pms.app.service.InventoryCheckService;
import com.pms.app.service.InventoryCheckTemplateService;
import com.pms.app.service.StyleService;
import com.pms.base.service.ServiceException;

@Controller
@RequestMapping(value = "/supervisor/inventoryCheck")
public class InventoryCheckController {
	
	private Logger logger = LoggerFactory.getLogger(InventoryCheckController.class);
	
	@Autowired private InventoryCheckService inventoryCheckService;
	@Autowired private StyleService styleService;
//	@Autowired private WarehouseService warehouseService;
	@Autowired private InventoryCheckTemplateService inventoryCheckTemplateService;
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Pageable pageable, Date date, HttpSession session) {
		DateTime dateTime = new DateTime();
		if(date != null) dateTime = new DateTime(date);
		model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
		model.addAttribute("page", inventoryCheckService.findPageByQuery(pageable, (String)session.getAttribute("warehouseId"), dateTime.toDate()));
		return "supervisor/inventoryCheck/list";
	}
	
	@RequestMapping(value = "/{id}/details")
	public String detailList(Model model, @PathVariable("id")String id){
		InventoryCheck inventoryCheck = inventoryCheckService.findById(id);
		model.addAttribute("inventoryCheck", inventoryCheck);
		model.addAttribute("detailList", inventoryCheck.getInventoryCheckDetails());
		return "supervisor/inventoryCheck/detailList";
	}
	
	@RequestMapping(value = "/tempList")
	public String tempList(Model model, HttpSession session){
		model.addAttribute("inventoryCheckDetailList", inventoryCheckTemplateService.findByWarehouseId((String)session.getAttribute("warehouseId")));
		return "supervisor/inventoryCheck/tempList";
	}
	
	@RequestMapping(value = "/addTemp")
	public String addTemp(Model model){
		model.addAttribute("styleList", styleService.findAll());
		return "supervisor/inventoryCheck/addTemp";
	}
	
	
	@RequestMapping(value = "/saveTemp")
	public String saveTemp(InventoryCheckTemplate inventoryCheckTemplate, RedirectAttributes ra){
		try {
			inventoryCheckTemplateService.save(inventoryCheckTemplate);
			ra.addFlashAttribute("messageOK", "保存成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			logger.error("保存异常", e);
		}
		return "redirect:/supervisor/inventoryCheck/tempList";
	}
	
	
	@RequestMapping(value = "/editTemp/{id}")
	public String editTemp(@PathVariable("id")String id, Model model){
		model.addAttribute("inventoryCheckTemplate", inventoryCheckTemplateService.findById(id));
		return "supervisor/inventoryCheck/editTemp";
	}
	

	@RequestMapping(value = "/deleteTemp")
	public String deleteTemp(String id, RedirectAttributes ra){
		try {
			inventoryCheckTemplateService.delete(id);
			ra.addFlashAttribute("messageOK", "删除成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "删除失败！");
			logger.error("删除异常", e);
		}
		return "redirect:/supervisor/inventoryCheck/tempList";
	}
	
	
	@RequestMapping(value = "/saveList")
	public String saveList(HttpSession session, RedirectAttributes ra){
		try {
//			Warehouse warehouse = warehouseService.findById((String)session.getAttribute("warehouseId"));
//			List<InventoryCheckDetail> InventoryCheckDetailList = (List<InventoryCheckDetail>) session.getAttribute("InventoryCheckDetailList");
//			inventoryCheckService.save(InventoryCheckDetailList ,warehouse, (Supervisor) session.getAttribute("user"));
			ra.addFlashAttribute("messageOK", "保存成功！");
		} catch (ServiceException e) {
			ra.addFlashAttribute("messageErr", "盘存不一致，无法保存！");
			logger.error("保存异常", e);
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			logger.error("保存异常", e);
		}
		return "redirect:/supervisor/inventoryCheck/list";
	}
	
	
	
}
