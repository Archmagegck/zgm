package com.pms.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.pms.app.entity.Stock;
import com.pms.base.dao.BaseDao;

public interface StockDao extends BaseDao<Stock, String> {
	
	public List<Stock> findByWarehouseId(String warehouseId);
	
	@Query("select s.style,s.pledgePurity,s.specWeight,s.warehouse,sum(s.amount),sum(s.sumWeight) from Stock s GROUP BY s.style,s.pledgePurity,s.specWeight,s.warehouse")
	public List<Object[]> findTotalList();
	
}
