package com.pms.app.dao;

import java.util.List;

import com.pms.app.entity.Warehouse;
import com.pms.base.dao.BaseDao;

public interface WarehouseDao extends BaseDao<Warehouse, String> {
	
	List<Warehouse> findByIsUsed(Integer isUsed);
	
}
