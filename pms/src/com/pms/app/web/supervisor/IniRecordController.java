package com.pms.app.web.supervisor;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.IniRecord;
import com.pms.app.service.IniRecordService;
import com.pms.app.service.StyleService;
import com.pms.app.web.supervisor.form.IniRecordForm;

@Controller
@RequestMapping(value = "/supervisor/iniRecord")
public class IniRecordController {
	
	private Logger logger = LoggerFactory.getLogger(IniRecordController.class);
	
	@Autowired private IniRecordService iniRecordService;
	@Autowired private StyleService styleService;
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, HttpSession session) {
		model.addAttribute("iniRecordList", iniRecordService.findAllEq("warehouse.id", (String)session.getAttribute("warehouseId")));
		return "supervisor/iniRecord/list";
	}
	
	
	@RequestMapping(value = "/add")
	public String add(Model model, HttpSession session){
		List<IniRecord> iniRecords = iniRecordService.findAllEq("warehouse.id", (String)session.getAttribute("warehouseId"));
		if(!iniRecords.isEmpty()) return "redirect:/supervisor/iniRecord/list";
		model.addAttribute("styleList", styleService.findAll());
		return "supervisor/iniRecord/add";
	}
	
	
	@RequestMapping(value = "/save")
	public String save(IniRecordForm iniRecordForm, HttpSession session, RedirectAttributes ra){
		try {
			iniRecordService.save(iniRecordForm.getIniRecords(), (String)session.getAttribute("warehouseId"));
			ra.addFlashAttribute("messageOK", "保存成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			logger.error("保存异常", e);
		}
		return "redirect:/supervisor/iniRecord/list";
	}
	
	
}
