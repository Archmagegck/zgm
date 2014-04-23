package com.pms.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.InventoryCheckDao;
import com.pms.app.entity.InventoryCheck;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class InventoryCheckService extends BaseService<InventoryCheck, String> {

	@Autowired private InventoryCheckDao inventoryCheckDao;

	@Override
	protected BaseDao<InventoryCheck, String> getEntityDao() {
		return inventoryCheckDao;
	}

}
