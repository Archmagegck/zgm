package com.pms.app.web.supervisor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import com.pms.app.entity.OutsRecord;
import com.pms.app.entity.Warehouse;
import com.pms.app.service.OutsRecordService;
import com.pms.app.service.StockService;
import com.pms.app.util.UploadUtils;
import com.pms.app.web.manage.form.OutsRecordDetailForm;

@Controller
@RequestMapping(value = "/supervisor/outsRecord")
public class OutsRecordController {
	
	private Logger logger = LoggerFactory.getLogger(OutsRecordController.class);
	
	@Autowired private OutsRecordService outsRecordService;
	@Autowired private StockService stockService;
	
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
		model.addAttribute("page", outsRecordService.findPageByQuery(pageable, ((Warehouse)session.getAttribute("warehouse")).getId(), beginDate, endDate));
		return "supervisor/outsRecord/list";
	}
	
	
	@RequestMapping(value = "/{id}/details")
	public String detailList(Model model, @PathVariable("id")String id){
		OutsRecord OutsRecord = outsRecordService.findById(id);
		model.addAttribute("outsRecord", OutsRecord);
		model.addAttribute("detailList", OutsRecord.getOutsRecordDetails());
		return "supervisor/outsRecord/detailList";
	}

	
	@RequestMapping(value = "/stockToOut")
	public String stockToOut(Model model, Pageable pageable, HttpSession session) {
		model.addAttribute("stockList", stockService.findByWarehouseId(((Warehouse)session.getAttribute("warehouse")).getId()));
		return "supervisor/outsRecord/stockToOut";
	}
	
	
	@RequestMapping(value = "/toUpload")
	public String toUpload(Model model) {
		return "supervisor/outsRecord/toUpload";
	}
	
	
	@RequestMapping(value = "/uploadNoticeFile")
	public String uploadNoticeFile(Model model, HttpServletRequest request) {
		String picName = UploadUtils.uploadIndexPic(request, "product", 1);
		System.out.println(picName);
		return "supervisor/outsRecord/uploadNoticeFile";
	}
	
	
	@RequestMapping(value = "/saveDetail")
	public String saveDetail(Model model, OutsRecordDetailForm outsRecordDetailForm, HttpSession session) {
		return "supervisor/outsRecord/stockToOut";
	}
	
	
	@RequestMapping(value = "/saveOutsRecord")
	public String saveOutsRecord(Model model, OutsRecord outsRecord, HttpSession session) {
		return "supervisor/outsRecord/stockToOut";
	}
	
}
