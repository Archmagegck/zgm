package com.pms.app.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.pms.app.entity.Inventory;
import com.pms.base.dao.BaseDao;

public interface InventoryDao extends BaseDao<Inventory, String> {
	
	public List<Inventory> findByWarehouseId(String warehouseId);
	public List<Inventory>  findByDateBetween(Date dateBegin, Date dateEnd);
	public List<Inventory> findByWarehouseIdAndDateBetween(String warehouseId, Date dateBegin, Date dateEnd);
	
	//需添加，根据仓库添加。and p.warehouse=?1
	@Query("select p from Inventory p where p.date = (SELECT max(r.date) from Inventory r) and p.warehouse.id=?1")
	public List<Inventory> findNewestInventoryList(String warehouseId);
	
}
