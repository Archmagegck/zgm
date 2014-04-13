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

	@Override
	protected BaseDao<Stock, String> getEntityDao() {
		return stockDao;
	}
	
	public List<Stock> findByWarehouseId(String warehouseId) {
		return stockDao.findByWarehouseId(warehouseId);
	}
	
	public List<Stock> findInStockByWarehouseId(String warehouseId) {
		return stockDao.findByWarehouseIdAndInStock(warehouseId, 1);
	}
	
	public List<Stock> findTranStockByWarehouseId(String warehouseId) {
		return stockDao.findByWarehouseIdAndInStock(warehouseId, 0);
	}
	
	public Map<String, Stock> findStockKeyMapByWarehouseId(String warehouseId) {
		Map<String, Stock> stockMap = new HashMap<String, Stock>();
		List<Stock> stockList = stockDao.findByWarehouseId(warehouseId);
		for (Stock stock : stockList) {
			stockMap.put(stock.getKey(), stock);
		}
		return stockMap;
	}
	
	public Map<String, Stock> findOutStockKeyMapByWarehouseId(String warehouseId) {
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
	
	public Map<String, Stock> findTranStockKeyMapByWarehouseId(String warehouseId) {
		Map<String, Stock> stockMap = new HashMap<String, Stock>();
		List<Stock> stockList = stockDao.findByWarehouseIdAndInStock(warehouseId, 0);
		for (Stock stock : stockList) {
			stockMap.put(stock.getKey(), stock);
		}
		return stockMap;
	}
	
	public Map<String, Stock> findNoTranStockKeyMapByWarehouseId(String warehouseId) {
		Map<String, Stock> stockMap = new HashMap<String, Stock>();
		List<Stock> stockList = stockDao.findByWarehouseIdAndInStock(warehouseId, 1);
		for (Stock stock : stockList) {
			stockMap.put(stock.getKey(), stock);
		}
		return stockMap;
	}
	
	public void save(Map<String, Stock> stockMap) {
		stockDao.save(stockMap.values());
	}
	
	public List<TotalStock> findTotalList() {
//		double newestValue = au9995PriceService.findNewestPrice();
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
			totalStock.setAmount((Double)ob[4]);
			totalStock.setSumWeight((Double)ob[5]);
//			double sumValue = totalStock.getSumWeight().doubleValue() * newestValue;
//			totalStock.setSumValue(sumValue);
			int inStock = (Integer)ob[6];
			if(inStock == 1) {
				totalStock.setStorage(warehouse.getAddress());
			} else {
				totalStock.setStorage("在途");
			}
			totalStocks.add(totalStock);
		}
		return totalStocks;
	}


}
