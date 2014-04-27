package com.pms.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.pms.app.entity.Stock;
import com.pms.base.dao.BaseDao;

public interface StockDao extends BaseDao<Stock, String> {
	
	public List<Stock> findByWarehouseId(String warehouseId);
	
	@Query("select s from Stock s where s.warehouse.id=?1 and s.pledgePurity.type=1")
	public List<Stock> findInByWarehouseId(String warehouseId);
	
	@Query("select s.style,s.pledgePurity,sum(s.sumWeight) from Stock s GROUP BY s.style,s.pledgePurity")
	public List<Object[]> findTotalList();
	
	@Query("select s.style,s.pledgePurity,sum(s.sumWeight) from Stock s where s.pledgePurity.type=1 GROUP BY s.style,s.pledgePurity")
	public List<Object[]> findInTotalList();
	
	@Query("select s.style,s.pledgePurity,sum(s.sumWeight) from Stock s where s.pledgePurity.type=1 and s.warehouse.id=?1 GROUP BY s.style,s.pledgePurity")
	public List<Object[]> findInTotalListByWarehouseId(String warehouseId);
	
	@Query("select sum(s.sumWeight) from Stock s where s.warehouse.id=?1")
	public double findSumWeightByWarehouseId(String warehouseId);
	
}
