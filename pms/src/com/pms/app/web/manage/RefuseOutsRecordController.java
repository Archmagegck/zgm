package com.pms.app.web.manage;

import javax.servlet.http.HttpSession;


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
import com.pms.app.service.PledgeConfigService;


@Controller
@RequestMapping(value = "/manage/refuseOutsRecord")
public class RefuseOutsRecordController {
	//private Logger logger = LoggerFactory.getLogger(WaitAuditOutsRecordController.class);
	
	@Autowired OutsRecordService outsRecordService;
	@Autowired PledgeConfigService pledgeConfigService;
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, @PageableDefaults(sort="date", sortDir=Direction.DESC)Pageable pageable, HttpSession session) {
		
		
		model.addAttribute("page", outsRecordService.findRefuseOutsRecord(pageable));
		
		return "manage/refuseOutsRecord/list";
	}
	
	
	@RequestMapping(value = "/{id}/details")
	public String detailList(Model model, @PathVariable("id")String id){
		OutsRecord outsRecord = outsRecordService.findById(id);
		model.addAttribute("outsRecord", outsRecord);
		model.addAttribute("detailList", outsRecord.getOutsRecordDetails());
		return "manage/refuseOutsRecord/detailList";
	}
	

}
