package com.pms.app.web.supervisor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.OutsRecord;
import com.pms.app.entity.Warehouse;
import com.pms.app.entity.vo.OutStock;
import com.pms.app.service.OutsRecordService;
import com.pms.app.service.StockService;
import com.pms.app.util.CodeUtils;
import com.pms.app.util.UploadUtils;
import com.pms.app.web.supervisor.form.StockForm;

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
	public String uploadNoticeFile(HttpServletRequest request, Model model, RedirectAttributes ra) {
		try {
			String filePath = UploadUtils.uploadTempFile(request);
			HttpSession session = request.getSession();
			session.setAttribute("tempImg", filePath);
			model.addAttribute("message", "ok");
			model.addAttribute("filePath", filePath);
		} catch (Exception e) {
			logger.error("上传提货通知书", e);
			ra.addFlashAttribute("error", e.getMessage());
			return "redirect:/supervisor/outsRecord/toUpload";
		}
		return "supervisor/outsRecord/toUpload";
	}
	
	
	@RequestMapping(value = "/saveDetail")
	public String saveDetail(Model model, StockForm stockForm, HttpSession session) {
		session.setAttribute("outStocks", stockForm.getOutStocks());
		return "supervisor/outsRecord/addRecordInfo";
	}
	
	
	@RequestMapping(value = "/saveOutsRecord")
	public String saveOutsRecord(Model model, OutsRecord outsRecord, HttpServletRequest request, RedirectAttributes ra) {
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<OutStock> outStocks = (List<OutStock>) session.getAttribute("outStocks");
		String supervisionCustomerCode = (String)session.getAttribute("supervisionCustomerCode");
		String tempImg = (String)session.getAttribute("tempImg");
		try {
			outsRecord.setCode(CodeUtils.getOutsRecordCode(supervisionCustomerCode));
			String idCardFile = UploadUtils.uploadFile(request, 2, supervisionCustomerCode, outsRecord.getCode());
			outsRecord.setPickerIdCardPic(idCardFile);			
			int hasPickFile = (tempImg == null) ? 0 : 1;
			if(hasPickFile == 1) {
				String pickNoticeUrl = UploadUtils.uploadPickFile(request, tempImg, outsRecord.getCode(), supervisionCustomerCode);
				outsRecord.setPickNoticeUrl(pickNoticeUrl);
				outsRecord.setAttachState(1);
			}
			outsRecordService.save(outsRecord, outStocks, hasPickFile, supervisionCustomerCode);
			ra.addFlashAttribute("messageOK", "保存成功！");
			session.removeAttribute("outStocks");
			session.removeAttribute("tempImg");
		} catch (Exception e) {
			logger.error("保存失败", e);
			ra.addFlashAttribute("messageErr", "保存失败！");
			return "redirect:/supervisor/outsRecord/stockToOut";
		}
		return "redirect:/supervisor/outsRecord/list";
	}
	
}
