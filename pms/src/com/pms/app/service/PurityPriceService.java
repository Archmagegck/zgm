package com.pms.app.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	public Map<String, Double> findPriceMap() {
		Map<String, Double> priceMap = new HashMap<String, Double>();
		List<PurityPrice> purityPrices = purityPriceDao.findNewestList();
		for (PurityPrice purityPrice : purityPrices) {
			priceMap.put(purityPrice.getPledgePurity().getId(), purityPrice.getPrice());
		}
		return priceMap;
	}
	
	public boolean hasDatePrice(Date date) {
		List<PurityPrice> list = purityPriceDao.findListByDate(date);
		if(list.isEmpty()) return false;
		else return true;
	}
	
	public Page<PurityPrice> findByDate(Pageable pageable, Date date) {
		if(date == null)
			return purityPriceDao.findAll(pageable);
		else
			return purityPriceDao.findByDate(pageable, date);
	}

}
