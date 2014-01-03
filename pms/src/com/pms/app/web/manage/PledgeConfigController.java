package com.pms.app.web.manage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pms.app.entity.PledgeConfig;
import com.pms.app.service.DelegatorService;
import com.pms.app.service.PledgeConfigService;
import com.pms.app.service.PledgePurityService;
import com.pms.app.service.SupervisionCustomerService;

@Controller
@RequestMapping(value = "/manage/pledgeConfig")
public class PledgeConfigController {
	
	private Logger logger = LoggerFactory.getLogger(PledgeConfigController.class);
	
	@Autowired private PledgeConfigService pledgeConfigService;
	@Autowired private DelegatorService delegatorService;
	@Autowired private SupervisionCustomerService supervisionCustomerService;
	@Autowired private PledgePurityService pledgePurityService;
	
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Pageable pageable, String delegatorId, String supervisionCustomerId) {
		model.addAttribute("delegatorList", delegatorService.findAll());
		model.addAttribute("supervisionCustomerList", supervisionCustomerService.findAll());
		model.addAttribute("delegatorId", delegatorId);
		model.addAttribute("supervisionCustomerId", supervisionCustomerId);
		model.addAttribute("page", pledgeConfigService.findByDelegatorIdAndSupervisionCustomerId(delegatorId, supervisionCustomerId, pageable));
		return "manage/pledgeConfig/list";
	}
	
	
	@RequestMapping(value = "/update")
	public String update(PledgeConfig pledgeConfig, Double shippingWeight, RedirectAttributes ra){
		try {
			pledgeConfigService.update(pledgeConfig, shippingWeight);
			ra.addFlashAttribute("messageOK", "保存成功！");
		} catch (Exception e) {
			ra.addFlashAttribute("messageErr", "保存失败！");
			logger.error("监管员保存异常", e);
		}
		return "redirect:/manage/pledgeConfig/list";
	}
	
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable("id")String id, Model model){
		model.addAttribute("pledgePurityList", pledgePurityService.findAll());
		model.addAttribute("pledgeConfig", pledgeConfigService.findById(id));
		return "manage/pledgeConfig/edit";
	}

	
	
}
