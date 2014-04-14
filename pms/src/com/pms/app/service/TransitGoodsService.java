package com.pms.app.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pms.app.dao.TransitGoodsDao;
import com.pms.app.entity.TransitGoods;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class TransitGoodsService extends BaseService<TransitGoods, String> {

	@Autowired private TransitGoodsDao transitGoodsDao;

	@Override
	protected BaseDao<TransitGoods, String> getEntityDao() {
		return transitGoodsDao;
	}
	
	
	@Transactional
	public void save(TransitGoods transitGoods, String warehouseId) {
		double sumWeight = new BigDecimal(transitGoods.getAmount() * transitGoods.getSpecWeight()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		transitGoods.setSumWeight(sumWeight);
		transitGoodsDao.save(transitGoods);
	}
	
	
	@Transactional
	public void saveStock(TransitGoods transitGoods, String warehouseId) {
		transitGoodsDao.save(transitGoods);
	}

}
