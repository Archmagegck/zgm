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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.OutsRecord;
import com.pms.app.entity.Supervisor;
import com.pms.app.entity.Warehouse;
import com.pms.app.service.OutsRecordService;
import com.pms.app.service.StockService;
import com.pms.app.service.WarehouseService;
import com.pms.app.util.UploadUtils;
import com.pms.app.web.supervisor.form.StockForm;
import com.pms.base.service.ServiceException;

@Controller
@RequestMapping(value = "/supervisor/outsRecord")
public class OutsRecordController {
	
	private Logger logger = LoggerFactory.getLogger(OutsRecordController.class);
	
	@Autowired private OutsRecordService outsRecordService;
	@Autowired private StockService stockService;
	@Autowired private WarehouseService warehouseService;
	
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
		model.addAttribute("page", outsRecordService.findPageByQuery(pageable, (String)session.getAttribute("warehouseId"), beginDate, endDate));
		return "supervisor/outsRecord/list";
	}
	
	
	@RequestMapping(value = "/{id}/details")
	public String detailList(Model model, @PathVariable("id")String id){
		OutsRecord outsRecord = outsRecordService.findById(id);
		model.addAttribute("outsRecord", outsRecord);
		model.addAttribute("detailList", outsRecord.getOutsRecordDetails());
		return "supervisor/outsRecord/detailList";
	}

	
	@RequestMapping(value = "/stockToOut")
	public String stockToOut(Model model, Pageable pageable, HttpSession session) {
		model.addAttribute("stockList", stockService.findByWarehouseId((String)session.getAttribute("warehouseId")));
		return "supervisor/outsRecord/stockToOut";
	}
	
	
	@RequestMapping(value = "/saveOutsRecord")
	public String saveOutsRecord(Model model, StockForm stockForm, HttpServletRequest request, String desc, RedirectAttributes ra) {
		try {
			HttpSession session = request.getSession();
			Warehouse warehouse = warehouseService.findById((String)session.getAttribute("warehouseId"));
			String attachPath = UploadUtils.uploadFile(request, 2, warehouse.getName());
			String message = outsRecordService.saveDetails(stockForm.getOutStocks(), warehouse, (Supervisor) session.getAttribute("user"), desc, attachPath);
			ra.addFlashAttribute("messageOK", message);
		} catch (ServiceException e) {
			ra.addFlashAttribute("messageErr", "保存失败：" + e.getMessage());
			logger.error("出库保存异常", e);
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			logger.error("出库保存异常", e);
		}
		return "redirect:/supervisor/outsRecord/list";
	}
	
	
	@RequestMapping(value = "/{id}/print")
	public String print(@PathVariable("id")String id, Model model, HttpSession session) {
		OutsRecord outsRecord = outsRecordService.findById(id);
		model.addAttribute("outsRecord", outsRecord);
		return "supervisor/outsRecord/print";
	}
	
	
}
