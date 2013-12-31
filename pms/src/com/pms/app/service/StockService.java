package com.pms.app.service;

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

}
