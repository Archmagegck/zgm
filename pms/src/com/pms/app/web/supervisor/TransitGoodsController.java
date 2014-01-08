package com.pms.app.web.supervisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.TransitGoods;
import com.pms.app.service.TransitGoodsService;

@Controller
@RequestMapping(value = "/supervisor/transitGoods")
public class TransitGoodsController {
	
private Logger logger = LoggerFactory.getLogger(TransitGoodsController.class);
	
	@Autowired private TransitGoodsService TransitGoodsService;
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Pageable pageable, String queryName, String queryValue) {
		model.addAttribute("queryName", queryName);
		model.addAttribute("queryValue", queryValue);
		model.addAttribute("page", TransitGoodsService.findAllLike(pageable, queryName, queryValue));
		return "manage/transitGoods/list";
	}
	
	
	@RequestMapping(value = "/add")
	public String add(Model model){
		return "manage/transitGoods/add";
	}
	
	
	@RequestMapping(value = "/save")
	public String save(TransitGoods TransitGoods, RedirectAttributes ra){
		try {
			TransitGoodsService.save(TransitGoods);
			ra.addFlashAttribute("messageOK", "保存成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			logger.error("保存异常", e);
		}
		return "redirect:/manage/transitGoods/list";
	}
	
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable("id")String id, Model model){
		model.addAttribute("TransitGoods", TransitGoodsService.findById(id));
		return "manage/transitGoods/edit";
	}
	

	@RequestMapping(value = "/delete")
	public String delete(String[] idGroup, RedirectAttributes ra){
		try {
			TransitGoodsService.delete(idGroup);
			ra.addFlashAttribute("messageOK", "删除成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "删除失败！");
			logger.error("删除异常", e);
		}
		return "redirect:/manage/transitGoods/list";
	}
	
	
}
