package com.pms.app.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.PledgeConfigDao;
import com.pms.app.dao.StockDao;
import com.pms.app.entity.PledgeConfig;
import com.pms.app.entity.Stock;

@Service
public class CheckMinValueService {

	@Autowired StockDao stockDao;
	@Autowired PledgeConfigDao pledgeConfigDao;
	
	public double getStockMinValue(String warehouseId) {
		double stockminValue = 0.0;
		double sumWeight = 0.0;
		double value9995 = 0.0;
		List<Stock> stockList = stockDao.findByWarehouseId(warehouseId);
		for (Stock stock : stockList) {
			sumWeight += stock.getSumWeight();
		}
//		List<Au9995Price> au9995PriceList = au9995PriceDao.findAll(new PageRequest(0, 1, new Sort(Direction.DESC, "date"))).getContent();
//		if(!au9995PriceList.isEmpty()) {
//			value9995 = au9995PriceList.get(0).getPrice();
//		}
		stockminValue = new BigDecimal(sumWeight * value9995).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return stockminValue;
	}
	
	
	public double getWarehouseMinValue(String supervisionCustomerId) {
		double minValue = 0.0;
		List<PledgeConfig> pledgeConfigs = pledgeConfigDao.findBySupervisionCustomerId(supervisionCustomerId);
		if(!pledgeConfigs.isEmpty()) {
			PledgeConfig config = pledgeConfigs.get(0);
			Double mv = config.getMinValue();
			if (mv != null) {
				minValue = mv.doubleValue();
			}
		}
		return minValue;
	}
	
	
}
