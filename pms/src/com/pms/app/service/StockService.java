package com.pms.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.StockDao;
import com.pms.app.entity.Stock;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class StockService extends BaseService<Stock, String> {

	@Autowired private StockDao stockDao;

	@Override
	protected BaseDao<Stock, String> getEntityDao() {
		return stockDao;
	}
	
	public List<Stock> findByWarehouseId(String warehouseId) {
		return stockDao.findByWarehouseId(warehouseId);
	}
	
	public Map<String, Stock> findStockMapByWarehouseId(String warehouseId) {
		Map<String, Stock> stockMap = new HashMap<String, Stock>();
		List<Stock> stockList = stockDao.findByWarehouseId(warehouseId);
		for (Stock stock : stockList) {
			System.out.println(stock.getKey());
			stockMap.put(stock.getKey(), stock);
		}
		return stockMap;
	}
	
	public void save(Map<String, Stock> stockMap) {
		stockDao.save(stockMap.values());
	}

}
