package com.pms.app.web.supervisor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.InsRecord;
import com.pms.app.entity.InsRecordDetail;
import com.pms.app.service.InsRecordService;
import com.pms.app.service.PledgePurityService;

@Controller
@RequestMapping(value = "/supervisor/insRecord")
@SuppressWarnings("unchecked")
public class InsRecordController {
	
	private Logger logger = LoggerFactory.getLogger(InsRecordController.class);
	
	@Autowired private InsRecordService insRecordService;
	@Autowired private PledgePurityService pledgePurityService;
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Pageable pageable, String queryName, String queryValue) {
		model.addAttribute("queryName", queryName);
		model.addAttribute("queryValue", queryValue);
		model.addAttribute("page", insRecordService.findAllLike(pageable, queryName, queryValue));
		return "supervisor/insRecord/list";
	}
	
	
	@RequestMapping(value = "/showDetailList")
	public String showDetailList(Model model, InsRecordDetail insRecordDetail, HttpSession session){
		List<InsRecordDetail> insRecordDetailsList = new ArrayList<InsRecordDetail>();
		if(session.getAttribute("insRecordDetailList") != null) {
			insRecordDetailsList = (List<InsRecordDetail>) session.getAttribute("insRecordDetailList");
		}
		if(insRecordDetail.getAmount() != null) {
			insRecordDetail.setSumWeight(insRecordDetail.getAmount() * insRecordDetail.getSpecWeight());
			insRecordDetailsList.add(insRecordDetail);
			session.setAttribute("insRecordDetailList", insRecordDetailsList);
		}
		model.addAttribute("detailList", insRecordDetailsList);
		return "supervisor/insRecord/showDetailList";
	}
	
	
	@RequestMapping(value = "/addDetail")
	public String addDetail(Model model){
		model.addAttribute("pledgePurityList", pledgePurityService.findAll());
		return "supervisor/insRecord/addDetail";
	}
	
	
	@RequestMapping(value = "/addRecordInfo")
	public String addRecordInfo(Model model){
		return "supervisor/insRecord/addRecordInfo";
	}
	
	
	@RequestMapping(value = "/save")
	public String save(InsRecord insRecord, HttpSession session, RedirectAttributes ra){
		try {
			insRecord.setInsRecordDetails((List<InsRecordDetail>) session.getAttribute("insRecordDetailList"));
			insRecordService.save(insRecord, "");
			// TODO 
			ra.addFlashAttribute("messageOK", "保存成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			logger.error("监管员保存异常", e);
		}
		return "redirect:/supervisor/insRecord/list";
	}
	
	
}
