package com.pms.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pms.app.dao.InventoryCheckDao;
import com.pms.app.dao.InventoryCheckDetailDao;
import com.pms.app.entity.InventoryCheck;
import com.pms.app.entity.InventoryCheckDetail;
import com.pms.app.entity.InventoryCheckTemplate;
import com.pms.app.entity.Warehouse;
import com.pms.app.util.CodeUtils;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;
import com.pms.base.service.ServiceException;

@Service
public class InventoryCheckService extends BaseService<InventoryCheck, String> {

	@Autowired private InventoryCheckDao inventoryCheckDao;
	@Autowired private InventoryCheckDetailDao inventoryCheckDetailDao;

	@Override
	protected BaseDao<InventoryCheck, String> getEntityDao() {
		return inventoryCheckDao;
	}
	
	@Transactional
	public InventoryCheck save(List<InventoryCheckDetail> inventoryCheckDetails, Warehouse warehouse) {
		Date now = new Date();
		
		InventoryCheck inventoryCheck = new InventoryCheck();
		inventoryCheck.setCode(CodeUtils.getInventoryCheckCode(warehouse.getId()));
		inventoryCheck.setDate(now);
		inventoryCheck.setWarehouse(warehouse);
		
		Integer checkCount = 0;
		
		for (InventoryCheckDetail inventoryCheckDetail : inventoryCheckDetails) {
			inventoryCheckDetail.setInventoryCheck(inventoryCheck);
			inventoryCheckDetail.setDate(now);
			inventoryCheckDetail.setWarehouse(warehouse);
			
			checkCount += inventoryCheckDetail.getAmount();
		}
		inventoryCheck.setInventoryCheckDetails(inventoryCheckDetails);
		
		if(checkCount <= 30) {
			throw new ServiceException("检测件数小于30件,无法保存!");
		}
		
		inventoryCheck.setState(1);
		inventoryCheckDao.save(inventoryCheck);
		inventoryCheckDetailDao.save(inventoryCheckDetails);
		
		return inventoryCheck;
	}
	

	@Transactional
	public InventoryCheck saveCheck(List<InventoryCheckTemplate> inventoryCheckTemplateList, Warehouse warehouse) {
		Date now = new Date();
		
		InventoryCheck inventoryCheck = new InventoryCheck();
		inventoryCheck.setCode(CodeUtils.getInventoryCheckCode(warehouse.getId()));
		inventoryCheck.setDate(now);
		inventoryCheck.setWarehouse(warehouse);
		
		List<InventoryCheckDetail> inventoryCheckDetails = new ArrayList<InventoryCheckDetail>();
		for (InventoryCheckTemplate inventoryCheckTemplate : inventoryCheckTemplateList) {
			InventoryCheckDetail inventoryCheckDetail = new InventoryCheckDetail();
			inventoryCheckDetail.setInventoryCheck(inventoryCheck);
			inventoryCheckDetail.setDate(now);
			inventoryCheckDetail.setWarehouse(warehouse);
			inventoryCheckDetail.setTrayNo(inventoryCheckTemplate.getTrayNo());
			inventoryCheckDetail.setStyle(inventoryCheckTemplate.getStyle());
			inventoryCheckDetail.setAmount(1);
			inventoryCheckDetails.add(inventoryCheckDetail);
		}
		inventoryCheck.setInventoryCheckDetails(inventoryCheckDetails);
		
		inventoryCheckDao.save(inventoryCheck);
		inventoryCheckDetailDao.save(inventoryCheckDetails);
		
		return inventoryCheck;
	}
	

}
