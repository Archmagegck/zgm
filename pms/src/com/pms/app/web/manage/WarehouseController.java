package com.pms.app.web.manage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.Warehouse;
import com.pms.app.service.WarehouseService;

@Controller
@RequestMapping(value = "/manage/warehouse")
public class WarehouseController {
	
	private Logger logger = LoggerFactory.getLogger(WarehouseController.class);
	
	@Autowired private WarehouseService warehouseService;
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, @PageableDefaults(value = 50)Pageable pageable, String queryName, String queryValue) {
		model.addAttribute("queryName", queryName);
		model.addAttribute("queryValue", queryValue);
		model.addAttribute("page", warehouseService.findAllLike(pageable, queryName, queryValue));
		return "manage/warehouse/list";
	}
	
	
	@RequestMapping(value = "/add")
	public String add(Model model){
		return "manage/warehouse/add";
	}
	
	
	@RequestMapping(value = "/save")
	public String save(Warehouse Warehouse, RedirectAttributes ra){
		try {
			warehouseService.save(Warehouse);
			ra.addFlashAttribute("messageOK", "保存成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			logger.error("保存异常", e);
		}
		return "redirect:/manage/warehouse/list";
	}
	
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable("id")String id, Model model){
		model.addAttribute("warehouse", warehouseService.findById(id));
		return "manage/warehouse/edit";
	}
	

	@RequestMapping(value = "/delete")
	public String delete(String[] idGroup, RedirectAttributes ra){
		try {
			warehouseService.delete(idGroup);
			ra.addFlashAttribute("messageOK", "删除成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "删除失败！");
			logger.error("删除异常", e);
		}
		return "redirect:/manage/warehouse/list";
	}
	
	
	
}
