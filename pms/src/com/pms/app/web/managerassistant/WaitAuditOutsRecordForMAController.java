package com.pms.app.web.managerassistant;

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
import com.pms.app.service.PledgeConfigService;

@Controller
@RequestMapping(value = "/managerassistant/waitAuditOutsRecord")
public class WaitAuditOutsRecordForMAController {
	
	private Logger logger = LoggerFactory.getLogger(WaitAuditOutsRecordForMAController.class);
	
	@Autowired OutsRecordService outsRecordService;
	@Autowired PledgeConfigService pledgeConfigService;
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, @PageableDefaults(sort="date", sortDir=Direction.DESC)Pageable pageable) {
		model.addAttribute("outsRecordList", outsRecordService.findWaitOutsRecordForMA());
		return "managerassistant/waitAuditOutsRecord/list";
	}
	
	
	@RequestMapping(value = "/{id}/details")
	public String detailList(Model model, @PathVariable("id")String id){
		OutsRecord outsRecord = outsRecordService.findById(id);
		model.addAttribute("outsRecord", outsRecord);
		model.addAttribute("detailList", outsRecord.getOutsRecordDetails());
		return "managerassistant/waitAuditOutsRecord/detailList";
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
		return "redirect:/managerassistant/waitAuditOutsRecord/list";
	}
	
	
}
