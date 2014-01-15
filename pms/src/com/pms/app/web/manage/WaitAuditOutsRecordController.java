package com.pms.app.web.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pms.app.service.OutsRecordService;

@Controller
@RequestMapping(value = "/manage/waitAuditOutsRecord")
public class WaitAuditOutsRecordController {
	
	@Autowired OutsRecordService outsRecordService;
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Pageable pageable) {
		model.addAttribute("outsRecordList", outsRecordService.findPage(pageable));
		return "manage/waitAuditOutsRecord/list";
	}
	
	
}
