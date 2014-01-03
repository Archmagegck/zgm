package com.pms.app.web.manage;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.Supervisor;
import com.pms.app.entity.Warehouse;
import com.pms.app.service.DelegatorService;
import com.pms.app.service.SupervisionCustomerService;
import com.pms.app.service.SupervisorService;
import com.pms.app.service.WarehouseService;

@Controller
@RequestMapping(value = "/manage/supervisionCustomer")
public class SupervisionCustomerController {
	
	private Logger logger = LoggerFactory.getLogger(SupervisionCustomerController.class);
	
	@Autowired private SupervisionCustomerService supervisionCustomerService;
	@Autowired private SupervisorService supervisorService;
	@Autowired private DelegatorService delegatorService;
	@Autowired private WarehouseService warehouseService;
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Pageable pageable, String queryName, String queryValue) {
		model.addAttribute("queryName", queryName);
		model.addAttribute("queryValue", queryValue);
		model.addAttribute("page", supervisionCustomerService.findAllLike(pageable, queryName, queryValue));
		return "manage/supervisionCustomer/list";
	}
	
	
	@RequestMapping(value = "/add")
	public String add(Model model){
		model.addAttribute("delegatorList", delegatorService.findAll());
		model.addAttribute("supervisorList", supervisorService.findByUnUsedList());
		model.addAttribute("warehouseList", warehouseService.findByUnUsedList());
		return "manage/supervisionCustomer/add";
	}
	
	
	@RequestMapping(value = "/save")
	public String save(SupervisionCustomer supervisionCustomer, String oldWarehouseId, String oldSupervisorId, RedirectAttributes ra){
		try {
			supervisionCustomerService.save(supervisionCustomer, oldWarehouseId, oldSupervisorId);
			ra.addFlashAttribute("messageOK", "保存成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			logger.error("监管客户保存异常", e);
		}
		return "redirect:/manage/supervisionCustomer/list";
	}
	
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable("id")String id, Model model){
		SupervisionCustomer supervisionCustomer = supervisionCustomerService.findById(id);
		model.addAttribute("supervisionCustomer", supervisionCustomer);
		model.addAttribute("delegatorList", delegatorService.findAll());
		
		Supervisor supervisor = supervisorService.findById(supervisionCustomer.getSupervisor().getId());
		List<Supervisor> supervisorList = supervisorService.findByUnUsedList();
		supervisorList.add(supervisor);
		model.addAttribute("supervisorList", supervisorList);
		
		Warehouse warehouse = warehouseService.findById(supervisionCustomer.getWarehouse().getId());
		List<Warehouse> warehouseList = warehouseService.findByUnUsedList();
		warehouseList.add(warehouse);
		model.addAttribute("warehouseList", warehouseList);
		return "manage/supervisionCustomer/edit";
	}
	

	@RequestMapping(value = "/delete")
	public String delete(String[] idGroup, RedirectAttributes ra){
		try {
			supervisionCustomerService.delete(idGroup);
			ra.addFlashAttribute("messageOK", "删除成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "删除失败！");
			logger.error("监管客户删除异常", e);
		}
		return "redirect:/manage/supervisionCustomer/list";
	}
	
	
	
}
