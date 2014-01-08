package com.pms.app.dao;

import java.util.List;

import com.pms.app.entity.Inventory;
import com.pms.base.dao.BaseDao;

public interface InventoryDao extends BaseDao<Inventory, String> {
	
	public List<Inventory> findByWarehouseId(String warehouseId);
	
}
