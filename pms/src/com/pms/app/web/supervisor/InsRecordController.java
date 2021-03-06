package com.pms.app.web.supervisor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.InsRecord;
import com.pms.app.entity.InsRecordDetail;
import com.pms.app.entity.Supervisor;
import com.pms.app.entity.Warehouse;
import com.pms.app.service.InsRecordService;
import com.pms.app.service.PledgePurityService;
import com.pms.app.service.StyleService;
import com.pms.app.service.WarehouseService;
import com.pms.app.util.UploadUtils;
import com.pms.app.web.supervisor.view.InsRecordViewExcel;
import com.pms.base.service.ServiceException;

@Controller
@RequestMapping(value = "/supervisor/insRecord")
@SuppressWarnings("unchecked")
public class InsRecordController {
	
	private Logger logger = LoggerFactory.getLogger(InsRecordController.class);
	
	@Autowired private InsRecordService insRecordService;
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
	public String list(Model model, Pageable pageable, Date date, HttpSession session) {
		if(date != null) {
			DateTime now = new DateTime(date);
			model.addAttribute("date", now.toString("yyyy-MM-dd"));
		} else {
			date = new Date();
			DateTime now = new DateTime();
			model.addAttribute("date", now.toString("yyyy-MM-dd"));
		}
		model.addAttribute("insRecordList", insRecordService.findListByQuery((String)session.getAttribute("warehouseId"), date));
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
		insDetail.replace(insRecordDetail);
		return "redirect:/supervisor/insRecord/showDetailList";
	}
	
	
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, HttpSession session, RedirectAttributes ra){
		try {
			Warehouse warehouse = warehouseService.findById((String)session.getAttribute("warehouseId"));
			String attachPath = UploadUtils.uploadFile(request, 1, warehouse.getName());
			insRecordService.saveDetails((List<InsRecordDetail>) session.getAttribute("insRecordDetailList"), warehouse, (Supervisor) session.getAttribute("user"), attachPath);
			session.removeAttribute("insRecordDetailList");
			ra.addFlashAttribute("messageOK", "保存成功！");
		} catch (ServiceException e) {
			ra.addFlashAttribute("messageErr", "保存失败：" + e.getMessage());
			logger.error("入库保存异常", e);
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			logger.error("入库保存异常", e);
		}
		return "redirect:/supervisor/insRecord/list";
	}
	
	
	@RequestMapping(value = "/{id}/print")
	public String print(@PathVariable("id")String id, Model model, HttpSession session) {
		InsRecord insRecord = insRecordService.findById(id);
		model.addAttribute("insRecord", insRecord);
		return "supervisor/insRecord/print";
	}
	
	@RequestMapping(value = "/export")
	public ModelAndView export(Date date, HttpSession session) {
		if(date == null) {
			date = new Date();
		} 
		Map<String, Object> model = new HashMap<String, Object>();
		List<InsRecord> insRecordList = insRecordService.findListByQuery((String)session.getAttribute("warehouseId"), date);
		model.put("insRecordList", insRecordList);
		model.put("date", date);
		return new ModelAndView(new InsRecordViewExcel(), model);
	}
	
	
	
}
