package com.pms.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.InventoryCheckDetailDao;
import com.pms.app.entity.InventoryCheckDetail;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class InventoryCheckDetailService extends BaseService<InventoryCheckDetail, String> {

	@Autowired private InventoryCheckDetailDao inventoryCheckDetailDao;

	@Override
	protected BaseDao<InventoryCheckDetail, String> getEntityDao() {
		return inventoryCheckDetailDao;
	}

}
