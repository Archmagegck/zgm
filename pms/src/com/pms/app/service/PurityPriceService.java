package com.pms.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.PurityPriceDao;
import com.pms.app.entity.PurityPrice;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class PurityPriceService extends BaseService<PurityPrice, String> {

	@Autowired private PurityPriceDao purityPriceDao;

	@Override
	protected BaseDao<PurityPrice, String> getEntityDao() {
		return purityPriceDao;
	}

}
