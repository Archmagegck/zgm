package com.pms.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.StockDao;
import com.pms.app.entity.PledgePurity;
import com.pms.app.entity.Stock;
import com.pms.app.entity.Style;
import com.pms.app.entity.Warehouse;
import com.pms.app.entity.vo.TotalStock;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class StockService extends BaseService<Stock, String> {

	@Autowired private StockDao stockDao;
	@Autowired private PurityPriceService purityPriceService;

	@Override
	protected BaseDao<Stock, String> getEntityDao() {
		return stockDao;
	}
	
	public List<Stock> findByWarehouseId(String warehouseId) {
		return stockDao.findByWarehouseId(warehouseId);
	}
	
	public Map<String, Stock> findStockKeyMapByWarehouseId(String warehouseId) {
		Map<String, Stock> stockMap = new HashMap<String, Stock>();
		List<Stock> stockList = stockDao.findByWarehouseId(warehouseId);
		for (Stock stock : stockList) {
			stockMap.put(stock.getKey(), stock);
		}
		return stockMap;
	}
	
	public Map<String, Stock> findStockMapByWarehouseId(String warehouseId) {
		Map<String, Stock> stockMap = new HashMap<String, Stock>();
		List<Stock> stockList = stockDao.findByWarehouseId(warehouseId);
		for (Stock stock : stockList) {
			stockMap.put(stock.getId(), stock);
		}
		return stockMap;
	}
	
	public void save(Map<String, Stock> stockMap) {
		stockDao.save(stockMap.values());
	}
	
	public List<TotalStock> findTotalList() {
		Map<String, Double> priceMap = purityPriceService.findPriceMap();
		List<TotalStock> totalStocks = new ArrayList<TotalStock>();
		List<Object[]> objList = stockDao.findTotalList();
		for(Object[] ob : objList) {
			TotalStock totalStock = new TotalStock();
			Style style = (Style) ob[0];
			totalStock.setStyleName(style.getName());
			PledgePurity pledgePurity = (PledgePurity) ob[1];
			totalStock.setPledgePurityName(pledgePurity.getName());
			totalStock.setSpecWeight((Double)ob[2]);
			Warehouse warehouse = (Warehouse) ob[3];
			totalStock.setStorage(warehouse.getAddress());
			totalStock.setAmount((Double)ob[4]);
			totalStock.setSumWeight((Double)ob[5]);
			double price = 0;
			if(priceMap.get(pledgePurity.getId()) != null) 
				price = priceMap.get(pledgePurity.getId());
			double sumValue = totalStock.getSumWeight().doubleValue() * price;
			totalStock.setSumValue(sumValue);
			totalStocks.add(totalStock);
		}
		return totalStocks;
	}

}
