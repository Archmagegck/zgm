package com.pms.app.web.manage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.OutsRecord;
import com.pms.app.service.OutsRecordService;

@Controller
@RequestMapping(value = "/manage/waitAuditOutsRecord")
public class WaitAuditOutsRecordController {
	
	private Logger logger = LoggerFactory.getLogger(WaitAuditOutsRecordController.class);
	
	@Autowired OutsRecordService outsRecordService;
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, @PageableDefaults(sort="date", sortDir=Direction.DESC)Pageable pageable) {
		model.addAttribute("page", outsRecordService.findWaitOutsRecord(pageable));
		return "manage/waitAuditOutsRecord/list";
	}
	
	
	@RequestMapping(value = "/{id}/details")
	public String detailList(Model model, @PathVariable("id")String id){
		OutsRecord outsRecord = outsRecordService.findById(id);
		model.addAttribute("outsRecord", outsRecord);
		model.addAttribute("detailList", outsRecord.getOutsRecordDetails());
		return "manage/waitAuditOutsRecord/detailList";
	}
	
	
	@RequestMapping(value = "/{id}/audit")
	public String audit(Model model, @PathVariable("id")String id, Integer state, RedirectAttributes ra){
		try {
			outsRecordService.audit(outsRecordService.findById(id), state);
			ra.addFlashAttribute("messageOK", "审核结果操作成功！");
		}  catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			logger.error("监管员保存异常", e);
		}
		return "redirect:/manage/waitAuditOutsRecord/list";
	}
	
	
}
