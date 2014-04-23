package com.pms.app.web.supervisor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.IniCheck;
import com.pms.app.service.IniCheckService;
import com.pms.app.service.StyleService;

@Controller
@RequestMapping(value = "/supervisor/iniCheck")
@SuppressWarnings("unchecked")
public class IniCheckController {
	
	private Logger logger = LoggerFactory.getLogger(IniCheckController.class);
	
	@Autowired private IniCheckService iniCheckService;
	@Autowired private StyleService styleService;
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, HttpSession session) {
		model.addAttribute("iniCheckList", iniCheckService.findAllEq("warehouse.id", (String)session.getAttribute("warehouseId")));
		return "supervisor/iniCheck/list";
	}
	
	
	@RequestMapping(value = "/addList")
	public String addList(Model model, HttpSession session){
		List<IniCheck> iniChecks = iniCheckService.findAllEq("warehouse.id", (String)session.getAttribute("warehouseId"));
		if(!iniChecks.isEmpty()) return "redirect:/supervisor/iniCheck/list";
		model.addAttribute("iniCheckList", (List<IniCheck>) session.getAttribute("iniCheckList"));
		return "supervisor/iniCheck/addList";
	}
	
	
	@RequestMapping(value = "/add")
	public String add(Model model){
		model.addAttribute("styleList", styleService.findAll());
		return "supervisor/iniCheck/add";
	}
	
	
	@RequestMapping(value = "/save")
	public String save(IniCheck iniCheck, HttpSession session){
		List<IniCheck> iniCheckList = new ArrayList<IniCheck>();
		if(session.getAttribute("iniCheckList") != null) {
			iniCheckList = (List<IniCheck>) session.getAttribute("iniCheckList");
		}
		iniCheckList.add(iniCheck);
		session.setAttribute("iniCheckList", iniCheckList);
		return "redirect:/supervisor/iniCheck/addList";
	}
	
	@RequestMapping(value = "/edit/{index}")
	public String edit(@PathVariable("index")Integer index, Model model, HttpSession session){
		List<IniCheck> iniCheckList = (List<IniCheck>) session.getAttribute("iniCheckList");
		model.addAttribute("iniCheck", iniCheckList.get(index));
		model.addAttribute("index", index);
		model.addAttribute("styleList", styleService.findAll());
		return "supervisor/iniCheck/edit";
	}
	
	@RequestMapping(value = "/update/{index}")
	public String update(@PathVariable("index")Integer index, IniCheck iniCheck, HttpSession session){
		List<IniCheck> iniCheckList = (List<IniCheck>) session.getAttribute("iniCheckList");
		IniCheck checkOld = iniCheckList.get(index);
		checkOld.update(iniCheck);
		return "redirect:/supervisor/iniCheck/addList";
	}
	
	
	@RequestMapping(value = "/del/{index}")
	public String del(@PathVariable("index")Integer index, HttpSession session){
		List<IniCheck> iniCheckList = (List<IniCheck>) session.getAttribute("iniCheckList");
		iniCheckList.remove(index - 1);
		session.setAttribute("iniCheckList", iniCheckList);
		return "redirect:/supervisor/iniCheck/addList";
	}
	
	
	@RequestMapping(value = "/saveList")
	public String saveList(HttpSession session, RedirectAttributes ra){
		try {
			List<IniCheck> iniCheckList = (List<IniCheck>) session.getAttribute("iniCheckList");
			iniCheckService.save(iniCheckList);
			ra.addFlashAttribute("messageOK", "保存成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			logger.error("保存异常", e);
		}
		return "redirect:/supervisor/iniCheck/list";
	}
	
	
}
