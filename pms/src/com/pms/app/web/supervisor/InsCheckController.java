package com.pms.app.web.supervisor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.InsCheck;
import com.pms.app.entity.InsCheckDetail;
import com.pms.app.entity.Supervisor;
import com.pms.app.service.InsCheckService;
import com.pms.app.service.StyleService;

@Controller
@RequestMapping(value = "/supervisor/insCheck")
@SuppressWarnings("unchecked")
public class InsCheckController {
	
	private Logger logger = LoggerFactory.getLogger(InsCheckController.class);
	
	@Autowired private InsCheckService insCheckService;
	@Autowired private StyleService styleService;
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Date date, Pageable pageable, HttpSession session) {
		model.addAttribute("page", insCheckService.findPageByQuery(pageable, (String)session.getAttribute("warehouseId"), date));
		return "supervisor/insCheck/list";
	}
	
	
	@RequestMapping(value = "/{id}/details")
	public String detailList(Model model, @PathVariable("id")String id){
		InsCheck insCheck = insCheckService.findById(id);
		model.addAttribute("insCheck", insCheck);
		model.addAttribute("detailList", insCheck.getInsCheckDetails());
		return "supervisor/insCheck/detailList";
	}
	
	
	@RequestMapping(value = "/inputWeight")
	public String inputSumWeight(Model model, HttpSession session){
		return "supervisor/insCheck/inputWeight";
	}
	
	
	@RequestMapping(value = "/showDetailList")
	public String showDetailList(Model model, Double sumWeight, InsCheckDetail insCheckDetail, HttpSession session){
		if(sumWeight != null) {
			double gpWeight = sumWeight * 5 / 100;
			double rjWeight = sumWeight * 50 / 100;
			session.setAttribute("weight", new double[]{sumWeight, gpWeight, rjWeight});
		} 
		List<InsCheckDetail> insCheckDetailsList = new ArrayList<InsCheckDetail>();
		if(session.getAttribute("insCheckDetailList") != null) {
			insCheckDetailsList = (List<InsCheckDetail>) session.getAttribute("insCheckDetailList");
		}
		if(insCheckDetail.getCheckWeight() != null) {
			insCheckDetailsList.add(insCheckDetail);
			session.setAttribute("insCheckDetailList", insCheckDetailsList);
		}
		model.addAttribute("detailList", insCheckDetailsList);
		model.addAttribute("detailListCount", insCheckDetailsList.size());
		return "supervisor/insCheck/showDetailList";
	}
	
	
	@RequestMapping(value = "/addDetail")
	public String addDetail(Model model){
		model.addAttribute("styleList", styleService.findAll());
		return "supervisor/insCheck/addDetail";
	}
	
	
	@RequestMapping(value = "/editDetail/{index}")
	public String editDetail(@PathVariable("index")Integer index, Model model, HttpSession session){
		List<InsCheckDetail> insCheckDetailsList = (List<InsCheckDetail>) session.getAttribute("insCheckDetailList");
		model.addAttribute("insCheckDetail", insCheckDetailsList.get(index - 1));
		model.addAttribute("index", index);
		model.addAttribute("styleList", styleService.findAll());
		return "supervisor/insCheck/editDetail";
	}
	
	
	@RequestMapping(value = "/updateDetail/{index}")
	public String updateDetail(@PathVariable("index")Integer index, InsCheckDetail insCheckDetail, HttpSession session){
		List<InsCheckDetail> insCheckDetailsList = (List<InsCheckDetail>) session.getAttribute("insCheckDetailList");
		InsCheckDetail insDetail = insCheckDetailsList.get(index - 1);
		insDetail.replace(insCheckDetail);
		return "redirect:/supervisor/insCheck/showDetailList";
	}
	
	
	@RequestMapping(value = "/delDetail/{index}")
	public String delDetail(@PathVariable("index")Integer index, HttpSession session){
		List<InsCheckDetail> insCheckDetailsList = (List<InsCheckDetail>) session.getAttribute("insCheckDetailList");
		insCheckDetailsList.remove(index - 1);
		return "redirect:/supervisor/insCheck/showDetailList";
	}
	
	
	@RequestMapping(value = "/saveList")
	public String saveList(HttpSession session, RedirectAttributes ra){
		try {
			double sumWeight = ((double[])session.getAttribute("weight"))[0];
			double gpWeight = ((double[])session.getAttribute("weight"))[1];
			double rjWeight = ((double[])session.getAttribute("weight"))[2];
			
			List<InsCheckDetail> insCheckDetailsList = (List<InsCheckDetail>) session.getAttribute("insCheckDetailList");
			insCheckService.save(sumWeight, gpWeight, rjWeight, insCheckDetailsList, (Supervisor) session.getAttribute("user"), (String)session.getAttribute("warehouseId"));
			ra.addFlashAttribute("messageOK", "保存成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			logger.error("保存异常", e);
		}
		return "redirect:/supervisor/insCheck/list";
	}
	
	
}
