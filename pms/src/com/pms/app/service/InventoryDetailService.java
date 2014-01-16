package com.pms.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.InventoryDetailDao;
import com.pms.app.entity.InventoryDetail;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class InventoryDetailService extends BaseService<InventoryDetail, String> {

	@Autowired private InventoryDetailDao inventoryDetailDao;

	@Override
	protected BaseDao<InventoryDetail, String> getEntityDao() {
		return inventoryDetailDao;
	}
	

}
