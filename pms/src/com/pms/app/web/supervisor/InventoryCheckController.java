package com.pms.app.web.supervisor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.pms.app.entity.InventoryCheckDetail;
import com.pms.app.entity.InventoryCheckTemplate;
import com.pms.app.entity.Warehouse;
import com.pms.app.service.InventoryCheckDetailService;
import com.pms.app.service.InventoryCheckService;
import com.pms.app.service.InventoryCheckTemplateService;
import com.pms.app.service.StyleService;
import com.pms.app.service.WarehouseService;
import com.pms.app.util.CodeUtils;
import com.pms.app.web.supervisor.form.InventoryCheckDetailForm;
import com.pms.base.service.ServiceException;

@Controller
@RequestMapping(value = "/supervisor/inventoryCheck")
public class InventoryCheckController {
	
	private Logger logger = LoggerFactory.getLogger(InventoryCheckController.class);
	
	@Autowired private InventoryCheckService inventoryCheckService;
	@Autowired private InventoryCheckDetailService inventoryCheckDetailService;
	@Autowired private StyleService styleService;
	@Autowired private WarehouseService warehouseService;
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
	
	@RequestMapping(value = "/inputNo")
	public String inputNo(Model model, HttpSession session){
		return "supervisor/inventoryCheck/inputCount";
	}
	
	@RequestMapping(value = "/general")
	public String general(Model model, Integer count, HttpSession session){
		List<InventoryCheckDetail> details = new ArrayList<InventoryCheckDetail>();
		int newCount = 0;
		int[] trayNos = new int[15];
		if(count > 15)  {
			newCount = 15;
			trayNos = CodeUtils.randomCommon(1, count, 15);
		}
		else newCount = count;
		for (int i = 0; i < newCount; i++) {
			InventoryCheckDetail inventoryCheckDetail = new InventoryCheckDetail();
			if(count > 15) {
				inventoryCheckDetail.setTrayNo(trayNos[i]);
			} else {
				inventoryCheckDetail.setTrayNo(i + 1);
			}
			details.add(inventoryCheckDetail);
		}
		model.addAttribute("styleList", styleService.findAll());
		model.addAttribute("details", details);
		return "supervisor/inventoryCheck/addCheck";
	}
	
	@RequestMapping(value = "/save")
	public String save(InventoryCheckDetailForm inventoryCheckDetailForm, HttpSession session, RedirectAttributes ra){
		List<InventoryCheckDetail> details = inventoryCheckDetailForm.getInventoryCheckDetails();
		try {
			Warehouse warehouse = warehouseService.findById((String)session.getAttribute("warehouseId"));
			InventoryCheck inventoryCheck = inventoryCheckService.save(details, warehouse);
			return "redirect:/supervisor/inventoryCheck/" + inventoryCheck.getId() + "/details";
		} catch (ServiceException e) {
			ra.addFlashAttribute("messageErr", e.getMessage());
			logger.warn("保存异常", e.getMessage());
			return "redirect:/supervisor/inventoryCheck/general?count=" + details.size();
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			logger.error("保存异常", e);
			return "redirect:/supervisor/inventoryCheck/general?count=" + details.size();
		}
	}
	
	
	@RequestMapping(value = "/tempList")
	public String tempList(Model model, HttpSession session){
		model.addAttribute("inventoryCheckDetailList", inventoryCheckTemplateService.findByWarehouseId((String)session.getAttribute("warehouseId")));
		return "supervisor/inventoryCheck/tempList";
	}
	
	@RequestMapping(value = "/addTemp")
	public String addTemp(Model model){
		model.addAttribute("styleList", styleService.findAll());
		return "supervisor/inventoryCheck/addTempp";
	}
	
	
	@RequestMapping(value = "/saveTemp")
	public String saveTemp(InventoryCheckTemplate inventoryCheckTemplate, RedirectAttributes ra){
		try {
			inventoryCheckTemplate.setUpdateDate(new Date());
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
		model.addAttribute("styleList", styleService.findAll());
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
	
	
	@RequestMapping(value = "/saveCheck")
	public String saveCheck(HttpSession session, RedirectAttributes ra){
		try {
			Warehouse warehouse = warehouseService.findById((String)session.getAttribute("warehouseId"));
			List<InventoryCheckTemplate> inventoryCheckTemplateList = inventoryCheckTemplateService.findByWarehouseId((String)session.getAttribute("warehouseId"));
			InventoryCheck inventoryCheck = inventoryCheckService.saveCheck(inventoryCheckTemplateList, warehouse);
			return "redirect:/supervisor/inventoryCheck/checkList?id=" + inventoryCheck.getId();
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			logger.error("保存异常", e);
			return "redirect:/supervisor/inventoryCheck/tempList";
		}
	}
	
	
	@RequestMapping(value = "/checkList")
	public String checkList(Model model, String id){
		model.addAttribute("id", id);
		model.addAttribute("inventoryCheckDetailList", inventoryCheckDetailService.findAllEq("inventoryCheck.id", id));
		return "supervisor/inventoryCheck/checkList";
	}
	
	
}
