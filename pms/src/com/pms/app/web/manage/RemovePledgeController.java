package com.pms.app.web.manage;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.service.DelegatorService;
import com.pms.app.service.RemovePledgeService;
import com.pms.app.service.SupervisionCustomerService;
import com.pms.app.service.WarehouseService;
import com.pms.app.util.UploadUtils;

@Controller
@RequestMapping(value = "/manage/removePledge")
public class RemovePledgeController {
	
	@Autowired RemovePledgeService removePledgeService;
	@Autowired WarehouseService warehouseService;
	@Autowired SupervisionCustomerService supervisionCustomerService;
	@Autowired DelegatorService delegatorService;
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Pageable pageable, String delegatorId, String supervisionCustomerId)
	{
		if(supervisionCustomerId!=null){
			model.addAttribute("removePledgeList", removePledgeService.findListBySupervisionCustomerId(pageable, supervisionCustomerId));
		}else if(delegatorId!=null){
			model.addAttribute("removePledgeList", removePledgeService.findListByDelegatorId(pageable, delegatorId));
		}
		else{ 
			model.addAttribute("removePledgeList", removePledgeService.findAll());
		}		
		model.addAttribute("delegatorList", delegatorService.findAll());
		model.addAttribute("supervisionCustomerList", supervisionCustomerService.findAll());
		
		return "manage/removePledge/list";
	}
	
	@RequestMapping(value="/delete")
	public String delete(Model model, String removeSupervisionCustomerId ){
		model.addAttribute("removeSupervisionCustomerId",removeSupervisionCustomerId);
		return "manage/removePledge/delete";
	}
	
	@RequestMapping(value="/remove")
	public String removePledge(Model model, HttpServletRequest request, String removeSupervisionCustomerId){
		
		SupervisionCustomer supervisionCustomer = supervisionCustomerService.findById(removeSupervisionCustomerId);
		String attachPath = UploadUtils.uploadFile(request, 3, supervisionCustomer.getName());	//没有实体保存该路径
		
		//解除质押——删除监管客户，清空仓库
		supervisionCustomerService.delete(removeSupervisionCustomerId);	
		
		model.addAttribute("removePledgeList", removePledgeService.findAll());
		model.addAttribute("delegatorList", delegatorService.findAll());
		model.addAttribute("supervisionCustomerList", supervisionCustomerService.findAll());
		return "manage/removePledge/list";
	}
	
	
}
