package com.pms.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
