package com.pms.app.dao;

import java.util.Date;
import java.util.List;

import com.pms.app.entity.PurityPrice;
import com.pms.base.dao.BaseDao;

public interface PurityPriceDao extends BaseDao<PurityPrice, String>{
	
	public List<PurityPrice> findListByDate(Date date);
	
}
