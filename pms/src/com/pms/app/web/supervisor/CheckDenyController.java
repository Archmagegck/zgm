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
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.CheckDeny;
import com.pms.app.entity.Supervisor;
import com.pms.app.entity.Warehouse;
import com.pms.app.service.CheckDenyService;
import com.pms.app.service.PledgePurityService;
import com.pms.app.service.StyleService;
import com.pms.app.service.WarehouseService;

@Controller
@RequestMapping(value = "/supervisor/checkDeny")
public class CheckDenyController {
	
	private Logger logger = LoggerFactory.getLogger(CheckDenyController.class);
	
	@Autowired private CheckDenyService checkDenyService;
	@Autowired private PledgePurityService pledgePurityService;
	@Autowired private StyleService styleService;
	@Autowired private WarehouseService warehouseService;
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) throws Exception {  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	    CustomDateEditor dateEditor = new CustomDateEditor(df, true);  
	    binder.registerCustomEditor(Date.class, dateEditor);      
	}
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, @PageableDefaults(value = 50)Pageable pageable, Date beginDate, Date endDate, HttpSession session) {
		if(beginDate != null) {
			DateTime beginTime = new DateTime(beginDate);
			model.addAttribute("beginDate", beginTime.toString("yyyy-MM-dd"));
		}
		if(endDate != null) {
			DateTime endTime = new DateTime(endDate);
			model.addAttribute("endDate", endTime.toString("yyyy-MM-dd"));
		}
		model.addAttribute("page", checkDenyService.findPageByQuery(pageable, ((Warehouse)session.getAttribute("warehouse")).getId(), beginDate, endDate));
		return "supervisor/checkDeny/list";
	}
	
	
	@RequestMapping(value = "/add")
	public String add(Model model){
		model.addAttribute("pledgePurityList",pledgePurityService.findAll());
		model.addAttribute("styleList",styleService.findAll());
		return "supervisor/checkDeny/add";
	}
	
	
	@RequestMapping(value = "/save")
	public String save(CheckDeny checkDeny, RedirectAttributes ra, HttpSession session){
		try {
			//算出总重量，获得监管员姓名，获得所属仓库
			checkDeny.setSumWeight(String.valueOf(checkDeny.getSpecWeight()*checkDeny.getAmount()));		
			checkDeny.setWarehouse(warehouseService.findWarehouseBySupervisorId(((Supervisor)session.getAttribute("user")).getId()));
			checkDeny.setSupervisor((Supervisor)session.getAttribute("user"));
			checkDenyService.save(checkDeny);
			ra.addFlashAttribute("messageOK", "保存成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			logger.error("监管员保存异常", e);
		}
		return "redirect:/supervisor/checkDeny/list";
	}
	
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable("id")String id, Model model){
		model.addAttribute("styleList", styleService.findAll());
		model.addAttribute("pledgePurityList", pledgePurityService.findAll());
		model.addAttribute("checkDeny", checkDenyService.findById(id));
		return "supervisor/checkDeny/edit";
	}
	

	@RequestMapping(value = "/delete")
	public String delete(String[] idGroup, RedirectAttributes ra){
		try {
			checkDenyService.delete(idGroup);
			ra.addFlashAttribute("messageOK", "删除成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "删除失败！");
			logger.error("监管员保存异常", e);
		}
		return "redirect:/supervisor/checkDeny/list";
	}
	
	
	
}
