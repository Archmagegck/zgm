package com.pms.app.web.supervisor;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.InsRecord;
import com.pms.app.entity.InsRecordDetail;
import com.pms.app.entity.PledgeRecord;
import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.Supervisor;
import com.pms.app.entity.Warehouse;
import com.pms.app.service.InsRecordService;
import com.pms.app.service.PledgePurityService;
import com.pms.app.service.PledgeRecordService;
import com.pms.app.service.StyleService;
import com.pms.app.service.SupervisionCustomerService;
import com.pms.app.util.CodeUtils;
import com.pms.app.util.UploadUtils;

@Controller
@RequestMapping(value = "/supervisor/insRecord")
@SuppressWarnings("unchecked")
public class InsRecordController {
	
	private Logger logger = LoggerFactory.getLogger(InsRecordController.class);
	
	@Autowired private InsRecordService insRecordService;
	@Autowired private PledgeRecordService pledgeRecordService;
	@Autowired private PledgePurityService pledgePurityService;
	@Autowired private SupervisionCustomerService supervisionCustomerService;
	@Autowired private StyleService styleService;
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) throws Exception {  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	    CustomDateEditor dateEditor = new CustomDateEditor(df, true);  
	    binder.registerCustomEditor(Date.class, dateEditor);      
	}  
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Pageable pageable, Date beginDate, Date endDate, HttpSession session) {
		if(beginDate != null) {
			DateTime beginTime = new DateTime(beginDate);
			model.addAttribute("beginDate", beginTime.toString("yyyy-MM-dd"));
		}
		if(endDate != null) {
			DateTime endTime = new DateTime(endDate);
			model.addAttribute("endDate", endTime.toString("yyyy-MM-dd"));
		}
		model.addAttribute("page", insRecordService.findPageByQuery(pageable,  ((Warehouse)session.getAttribute("warehouse")).getId(), beginDate, endDate));
		return "supervisor/insRecord/list";
	}
	
	
	@RequestMapping(value = "/{id}/details")
	public String detailList(Model model, @PathVariable("id")String id){
		InsRecord insRecord = insRecordService.findById(id);
		model.addAttribute("insRecord", insRecord);
		model.addAttribute("detailList", insRecord.getInsRecordDetails());
		return "supervisor/insRecord/detailList";
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
		model.addAttribute("detailListCount", insRecordDetailsList.size());
		return "supervisor/insRecord/showDetailList";
	}
	
	
	@RequestMapping(value = "/addDetail")
	public String addDetail(Model model){
		model.addAttribute("styleList", styleService.findAll());
		model.addAttribute("pledgePurityList", pledgePurityService.findAll());
		return "supervisor/insRecord/addDetail";
	}
	
	
	@RequestMapping(value = "/editDetail/{index}")
	public String editDetail(@PathVariable("index")Integer index, Model model, HttpSession session){
		List<InsRecordDetail> insRecordDetailsList = (List<InsRecordDetail>) session.getAttribute("insRecordDetailList");
		model.addAttribute("insRecordDetail", insRecordDetailsList.get(index - 1));
		model.addAttribute("index", index);
		model.addAttribute("styleList", styleService.findAll());
		model.addAttribute("pledgePurityList", pledgePurityService.findAll());
		return "supervisor/insRecord/editDetail";
	}
	
	
	@RequestMapping(value = "/delDetail/{index}")
	public String delDetail(@PathVariable("index")Integer index, HttpSession session){
		List<InsRecordDetail> insRecordDetailsList = (List<InsRecordDetail>) session.getAttribute("insRecordDetailList");
		insRecordDetailsList.remove(index - 1);
		return "redirect:/supervisor/insRecord/showDetailList";
	}
	
	
	@RequestMapping(value = "/updateDetail/{index}")
	public String updateDetail(@PathVariable("index")Integer index, InsRecordDetail insRecordDetail, HttpSession session){
		List<InsRecordDetail> insRecordDetailsList = (List<InsRecordDetail>) session.getAttribute("insRecordDetailList");
		InsRecordDetail insDetail = insRecordDetailsList.get(index - 1);
		insDetail.copy(insRecordDetail);
		return "redirect:/supervisor/insRecord/showDetailList";
	}
	
	
	@RequestMapping(value = "/addRecordInfo")
	public String addRecordInfo(){
		return "supervisor/insRecord/addRecordInfo";
	}
	
	
	@RequestMapping(value = "/save")
	public String save(InsRecord insRecord, HttpSession session, RedirectAttributes ra){
		try {
			insRecord.setInsRecordDetails((List<InsRecordDetail>) session.getAttribute("insRecordDetailList"));
			insRecordService.save(insRecord, (String)session.getAttribute("supervisionCustomerCode"));
			session.setAttribute("warehouse", insRecord.getWarehouse());
			session.removeAttribute("insRecordDetailList");
			ra.addFlashAttribute("messageOK", "保存成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			logger.error("监管员保存异常", e);
		}
		return "redirect:/supervisor/insRecord/list";
	}
	
	
	@RequestMapping(value = "/{id}/toUpload")
	public String toUpload(@PathVariable("id")String id, Model model){
		model.addAttribute("id", id);
		return "supervisor/insRecord/toUpload";
	}
	
	
	@RequestMapping(value = "/uploadAttach")
	public String upload(HttpServletRequest request, String insRecordId, Model model, RedirectAttributes ra){
		String supervisionCustomerCode = (String)request.getSession().getAttribute("supervisionCustomerCode");
		try {
			InsRecord insRecord = insRecordService.findById(insRecordId);
			insRecord = UploadUtils.uploadInsFile(request, insRecord, supervisionCustomerCode);
			insRecord.setAttachState(1);
			insRecordService.save(insRecord);
			
			MultipartFile pledgeRecordFile = ((MultipartHttpServletRequest) request).getFile("pledgeRecordFile");
			if (!(pledgeRecordFile.getOriginalFilename() == null || "".equals(pledgeRecordFile.getOriginalFilename()))) {
				PledgeRecord pledgeRecord = pledgeRecordService.findById(insRecord.getPledgeRecord().getId());
				pledgeRecord.setRecordFile(insRecord.getPledgeRecord().getRecordFile());
				pledgeRecord.setIfUpload(1);
				pledgeRecord.setRecordName(CodeUtils.getPledgeRecordCode(supervisionCustomerCode));
				pledgeRecordService.save(pledgeRecord);
			}
			
			ra.addFlashAttribute("messageOK", "上传扫描件成功！");
			
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "上传文件出现异常！");
			logger.error("上传文件出现异常", e);
		}
		return "redirect:/supervisor/insRecord/list";
	}
	
	
	@RequestMapping(value = "/{id}/printInsRecord")
	public String printInsRecord(@PathVariable("id")String id, Model model, HttpSession session) {
		Supervisor supervisor = (Supervisor)session.getAttribute("user");
		SupervisionCustomer supervisionCustomer = supervisionCustomerService.findBySupervisorId(supervisor.getId());
		InsRecord insRecord = insRecordService.findById(id);
		model.addAttribute("supervisionCustomerName", supervisionCustomer.getName());
		model.addAttribute("insRecord", insRecord);
		model.addAttribute("detailList", insRecord.getInsRecordDetails());
		double sumAmount = 0;
		double sumWeight = 0;
		for(InsRecordDetail detail : insRecord.getInsRecordDetails()) {
			sumAmount += detail.getAmount();
			sumWeight += detail.getSumWeight();
		}
		sumAmount = new BigDecimal(sumAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		sumWeight = new BigDecimal(sumWeight).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		model.addAttribute("sumAmount", sumAmount);
		model.addAttribute("sumWeight", sumWeight);
		return "supervisor/insRecord/printInsRecord";
	}
	
	
	@RequestMapping(value = "/{id}/printCheckRecord")
	public String printCheckRecord(@PathVariable("id")String id, Model model, HttpSession session) {
		Supervisor supervisor = (Supervisor)session.getAttribute("user");
		SupervisionCustomer supervisionCustomer = supervisionCustomerService.findBySupervisorId(supervisor.getId());
		InsRecord insRecord = insRecordService.findById(id);
		model.addAttribute("supervisionCustomerName", supervisionCustomer.getName());
		model.addAttribute("insRecord", insRecord);
		model.addAttribute("detailList", insRecord.getInsRecordDetails());
		double checkSumWeight = 0;
		for(InsRecordDetail detail : insRecord.getInsRecordDetails()) {
			checkSumWeight += detail.getCheckWeight();
		}
		model.addAttribute("checkSumWeight", new BigDecimal(checkSumWeight).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		return "supervisor/insRecord/printCheckRecord";
	}
	
	
	@RequestMapping(value = "/{id}/printPledgeRecord")
	public String printPledgeRecord(@PathVariable("id")String id, Model model, HttpSession session) {
		InsRecord insRecord = insRecordService.findById(id);
		PledgeRecord pledgeRecord = pledgeRecordService.findById(insRecord.getPledgeRecord().getId());
		model.addAttribute("detailList", pledgeRecord.getPledgeRecordDetails());
		return "supervisor/insRecord/printPledgeRecord";
	}
	
	
	
}
