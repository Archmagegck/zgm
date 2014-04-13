package com.pms.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.WarehouseDao;
import com.pms.app.entity.Warehouse;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class WarehouseService extends BaseService<Warehouse, String> {

	@Autowired private WarehouseDao warehouseDao;

	@Override
	protected BaseDao<Warehouse, String> getEntityDao() {
		return warehouseDao;
	}

}
