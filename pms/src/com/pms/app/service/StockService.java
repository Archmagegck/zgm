package com.pms.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.PurityPriceDao;
import com.pms.app.dao.StockDao;
import com.pms.app.entity.PledgePurity;
import com.pms.app.entity.PurityPrice;
import com.pms.app.entity.Stock;
import com.pms.app.entity.Style;
import com.pms.app.entity.Warehouse;
import com.pms.app.entity.vo.TotalStock;
import com.pms.app.entity.vo.WarehouseStock;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class StockService extends BaseService<Stock, String> {

	@Autowired private StockDao stockDao;
	@Autowired private PurityPriceDao purityPriceDao;
	@Autowired private WarehouseService warehouseService;

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
		double newestValue = 0.0;
		List<PurityPrice> purityPriceList = purityPriceDao.findNewestList();
		if(!purityPriceList.isEmpty()) {
			newestValue = purityPriceList.get(0).getPrice();
		}
		List<TotalStock> totalStocks = new ArrayList<TotalStock>();
		List<Object[]> objList = stockDao.findInTotalList();
		for(Object[] ob : objList) {
			TotalStock totalStock = new TotalStock();
			Style style = (Style) ob[0];
			totalStock.setStyleName(style.getName());
			PledgePurity pledgePurity = (PledgePurity) ob[1];
			totalStock.setPledgePurityName(pledgePurity.getName());
			totalStock.setSumWeight((Double)ob[2]);
			double sumValue = totalStock.getSumWeight().doubleValue() * newestValue;
			totalStock.setSumValue(sumValue);
			totalStocks.add(totalStock);
		}
		return totalStocks;
	}

	public List<WarehouseStock> findTotalStockByWarehouseId(String warehouseId){
		List<WarehouseStock> warehouseStocks = new ArrayList<WarehouseStock>();
		List<Object[]> objList = stockDao.findInTotalListByWarehouseId(warehouseId);
		
		Warehouse warehouse = warehouseService.findById(warehouseId);
		for(Object[] ob : objList) {
			WarehouseStock warehouseStock = new WarehouseStock();
			Style style = (Style) ob[0];
			warehouseStock.setStyleName(style.getName());
			PledgePurity pledgePurity = (PledgePurity) ob[1];
			warehouseStock.setPledgePurityName(pledgePurity.getName());
			warehouseStock.setSumWeight((Double)ob[2]);
			warehouseStock.setStorage(warehouse.getName());
			warehouseStocks.add(warehouseStock);
		}
		return warehouseStocks;
	}
}
