package com.pms.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.StockDao;
import com.pms.app.dao.SupervisionCustomerDao;
import com.pms.app.entity.Stock;
import com.pms.app.entity.SupervisionCustomer;

@Service
public class DailyStockService {
	
	@Autowired SupervisionCustomerDao supervisionCustomerDao;
	@Autowired StockDao stockDao;

	public  Map<SupervisionCustomer, List<Stock>>  queryByDelegatorAndDate(String delegatorId) {
		Map<SupervisionCustomer, List<Stock>> stockMap = new HashMap<SupervisionCustomer, List<Stock>>();
		List<SupervisionCustomer> supervisionCustomerList = supervisionCustomerDao.findListByDelegatorId(delegatorId);
		for (SupervisionCustomer supervisionCustomer : supervisionCustomerList) {
			String warehouseId = supervisionCustomer.getWarehouse().getId();
			List<Stock> stockList = stockDao.findByWarehouseId(warehouseId);
			if(!stockList.isEmpty())
				stockMap.put(supervisionCustomer, stockList);
		}
		return stockMap;
	}
	
}
