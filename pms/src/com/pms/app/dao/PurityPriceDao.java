package com.pms.app.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.pms.app.entity.PurityPrice;
import com.pms.base.dao.BaseDao;

public interface PurityPriceDao extends BaseDao<PurityPrice, String>{
	
	public List<PurityPrice> findListByDate(Date date);
	
	@Query("select p from PurityPrice p where p.date = (SELECT max(r.date) from PurityPrice r)")
	public List<PurityPrice> findNewestList();
	
}
