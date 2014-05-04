package com.pms.app.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.pms.app.entity.Inventory;
import com.pms.app.entity.PurityPrice;
import com.pms.base.dao.BaseDao;

public interface InventoryDao extends BaseDao<Inventory, String> {
	
	public List<Inventory> findByWarehouseId(String warehouseId);
	public List<Inventory>  findByDateBetween(Date dateBegin, Date dateEnd);
	public List<Inventory> findByWarehouseIdAndDateBetween(String warehouseId, Date dateBegin, Date dateEnd);
	
	@Query("select p from Inventory p where p.date = (SELECT max(r.date) from Inventory r)")
	public List<Inventory> findNewestInventoryList();
}
