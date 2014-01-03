package com.pms.app.dao;

import java.util.List;

import com.pms.app.entity.Stock;
import com.pms.base.dao.BaseDao;

public interface StockDao extends BaseDao<Stock, String> {
	
	public List<Stock> findByWarehouseId(String warehouseId);
	
}
