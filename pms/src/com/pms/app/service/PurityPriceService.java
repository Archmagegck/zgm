package com.pms.app.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	public List<PurityPrice> findListByDate(Date date) {
		return purityPriceDao.findListByDate(date);
	}
	
	@Transactional
	public void save(PurityPrice[] purityPrices) {
		for(PurityPrice purityPrice : purityPrices) {
			purityPriceDao.save(purityPrice);
		}
	}

}
