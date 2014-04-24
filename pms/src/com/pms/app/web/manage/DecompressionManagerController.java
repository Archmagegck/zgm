package com.pms.app.web.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import com.pms.app.service.DelegatorService;
import com.pms.app.service.SupervisionCustomerService;
import com.pms.app.service.WarehouseService;
import com.pms.app.util.DateUtils;


@Controller
@RequestMapping(value = "/manage/decompressionManager")
public class DecompressionManagerController {
	
	private Logger logger = LoggerFactory.getLogger(DailyInventoryRecordController.class);
	
	
	@Autowired WarehouseService warehouseService;
	@Autowired SupervisionCustomerService supervisionCustomerService;
	@Autowired DelegatorService delegatorService;
	
	@RequestMapping(value = { "/list", "" })
	public String list(Model model, Pageable pageable,String supervisionCustomerId,String delegatorId) {
		
		model.addAttribute("supervisionCustomerId",supervisionCustomerId);
		model.addAttribute("delegatorId",delegatorId);
		model.addAttribute("supervisionCustomerlist",supervisionCustomerService.findAll());
		model.addAttribute("delegatorlist",delegatorService.findAll());
		//1.查询所有仓库
		//2.
		//	model.addAttribute("解压管理VO对象",decompressionManagerService.findAllWarehouseList(pageable,supervisionCustomerId,String delegatorId));
		return "manage/decompressionManager/list";
	}

	@RequestMapping(value = { "/{id}/delete"})
	public String list(Model model, Pageable pageable,String id) {
		//1.删除仓库关联的所有信息，该仓库与该仓库的监管员可重新分配
		//decompressionManagerService.deleteWarehouseReference(pageable,supervisionCustomerId,String delegatorId);
		//model.addAttribute("解压管理VO对象",decompressionManagerService.findAllWarehouseList(pageable,supervisionCustomerId,String delegatorId));
		
		return "manage/decompressionManager/list";
	}
	
}
