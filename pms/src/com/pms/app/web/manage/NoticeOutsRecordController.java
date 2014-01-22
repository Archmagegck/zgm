package com.pms.app.web.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pms.app.entity.OutsRecord;
import com.pms.app.service.OutsRecordService;

@Controller
@RequestMapping(value = "/manage/noticeOutsRecord")
public class NoticeOutsRecordController {
	
	@Autowired OutsRecordService outsRecordService;
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, @PageableDefaults(sort="date", sortDir=Direction.DESC)Pageable pageable) {
		model.addAttribute("page", outsRecordService.findNoticeOutsRecord(pageable));
		return "manage/noticeOutsRecord/list";
	}
	
	
	@RequestMapping(value = "/{id}/details")
	public String detailList(Model model, @PathVariable("id")String id){
		OutsRecord outsRecord = outsRecordService.findById(id);
		model.addAttribute("outsRecord", outsRecord);
		model.addAttribute("detailList", outsRecord.getOutsRecordDetails());
		return "manage/noticeOutsRecord/detailList";
	}
	
	
	
}
