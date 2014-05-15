package com.pms.app.web.supervisor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

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

import com.pms.app.entity.ExtendedCheck;
import com.pms.app.entity.ExtendedCheckDetail;
import com.pms.app.entity.Supervisor;
import com.pms.app.service.ExtendedCheckService;
import com.pms.app.service.StyleService;
import com.pms.base.service.ServiceException;

@Controller
@RequestMapping(value = "/supervisor/extendedCheck")
@SuppressWarnings("unchecked")
public class ExtendedCheckController {
	
	private Logger logger = LoggerFactory.getLogger(ExtendedCheckController.class);
	
	@Autowired private ExtendedCheckService extendedCheckService;
	@Autowired private StyleService styleService;
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) throws Exception {  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	    CustomDateEditor dateEditor = new CustomDateEditor(df, true);  
	    binder.registerCustomEditor(Date.class, dateEditor);      
	}  
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Date date, Pageable pageable, HttpSession session) {
		model.addAttribute("page", extendedCheckService.findPageByQuery(pageable, (String)session.getAttribute("warehouseId"), date));
		return "supervisor/extendedCheck/list";
	}
	
	
	@RequestMapping(value = "/{id}/details")
	public String detailList(Model model, @PathVariable("id")String id){
		ExtendedCheck extendedCheck = extendedCheckService.findById(id);
		model.addAttribute("extendedCheck", extendedCheck);
		model.addAttribute("detailList", extendedCheck.getExtendedCheckDetails());
		return "supervisor/extendedCheck/detailList";
	}
	
	
	@RequestMapping(value = "/inputWeight")
	public String inputSumWeight(Model model, HttpSession session){
		return "supervisor/extendedCheck/inputWeight";
	}
	
	
	@RequestMapping(value = "/showDetailList")
	public String showDetailList(Model model, Double sumWeight, ExtendedCheckDetail extendedCheckDetail, HttpSession session){
		if(sumWeight != null) {
			double gpWeight = sumWeight * 5 / 100;
			double rjWeight = sumWeight * 50 / 100;
			session.setAttribute("weight", new double[]{sumWeight, gpWeight, rjWeight});
		} 
		List<ExtendedCheckDetail> ExtendedCheckDetailsList = new ArrayList<ExtendedCheckDetail>();
		if(session.getAttribute("extendedCheckDetailList") != null) {
			ExtendedCheckDetailsList = (List<ExtendedCheckDetail>) session.getAttribute("extendedCheckDetailList");
		}
		if(extendedCheckDetail.getCheckWeight() != null) {
			ExtendedCheckDetailsList.add(extendedCheckDetail);
			session.setAttribute("extendedCheckDetailList", ExtendedCheckDetailsList);
		}
		model.addAttribute("detailList", ExtendedCheckDetailsList);
		model.addAttribute("detailListCount", ExtendedCheckDetailsList.size());
		return "supervisor/extendedCheck/showDetailList";
	}
	
	
	@RequestMapping(value = "/addDetail")
	public String addDetail(Model model){
		model.addAttribute("styleList", styleService.findAll());
		return "supervisor/extendedCheck/addDetail";
	}
	
	
	@RequestMapping(value = "/editDetail/{index}")
	public String editDetail(@PathVariable("index")Integer index, Model model, HttpSession session){
		List<ExtendedCheckDetail> extendedCheckDetailsList = (List<ExtendedCheckDetail>) session.getAttribute("extendedCheckDetailList");
		model.addAttribute("extendedCheckDetail", extendedCheckDetailsList.get(index - 1));
		model.addAttribute("index", index);
		model.addAttribute("styleList", styleService.findAll());
		return "supervisor/extendedCheck/editDetail";
	}
	
	
	@RequestMapping(value = "/updateDetail/{index}")
	public String updateDetail(@PathVariable("index")Integer index, ExtendedCheckDetail extendedCheckDetail, HttpSession session){
		List<ExtendedCheckDetail> extendedCheckDetailsList = (List<ExtendedCheckDetail>) session.getAttribute("extendedCheckDetailList");
		ExtendedCheckDetail insDetail = extendedCheckDetailsList.get(index - 1);
		insDetail.replace(extendedCheckDetail);
		return "redirect:/supervisor/extendedCheck/showDetailList";
	}
	
	
	@RequestMapping(value = "/delDetail/{index}")
	public String delDetail(@PathVariable("index")Integer index, HttpSession session){
		List<ExtendedCheckDetail> extendedCheckDetailsList = (List<ExtendedCheckDetail>) session.getAttribute("extendedCheckDetailList");
		extendedCheckDetailsList.remove(index - 1);
		return "redirect:/supervisor/extendedCheck/showDetailList";
	}
	
	
	@RequestMapping(value = "/saveList")
	public String saveList(HttpSession session, RedirectAttributes ra){
		try {
			double sumWeight = ((double[])session.getAttribute("weight"))[0];
			double gpWeight = ((double[])session.getAttribute("weight"))[1];
			double rjWeight = ((double[])session.getAttribute("weight"))[2];
			
			List<ExtendedCheckDetail> extendedCheckDetailsList = (List<ExtendedCheckDetail>) session.getAttribute("extendedCheckDetailList");
			extendedCheckService.save(sumWeight, gpWeight, rjWeight, extendedCheckDetailsList, (Supervisor) session.getAttribute("user"), (String)session.getAttribute("warehouseId"));
			
			session.removeAttribute("weight");
			ra.addFlashAttribute("messageOK", "保存成功！");
		}  catch (ServiceException e) {
			ra.addFlashAttribute("messageErr", e.getMessage());
			logger.warn("保存异常", e.getMessage());
			return "redirect:/supervisor/extendedCheck/showDetailList";
		}  catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			logger.error("保存异常", e);
		}
		return "redirect:/supervisor/extendedCheck/list";
	}
	
	
}
