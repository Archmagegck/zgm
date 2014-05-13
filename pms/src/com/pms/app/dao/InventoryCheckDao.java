package com.pms.app.dao;

import java.util.Date;
import java.util.List;

import com.pms.app.entity.InventoryCheck;
import com.pms.base.dao.BaseDao;

public interface InventoryCheckDao extends BaseDao<InventoryCheck, String> {

	public List<InventoryCheck> findByWarehouseIdAndDateBetween(String warehouseId, Date dateToDayBegin, Date dateToDayEnd);
	
}
