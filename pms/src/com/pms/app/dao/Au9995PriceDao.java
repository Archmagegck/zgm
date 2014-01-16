package com.pms.app.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pms.app.entity.Au9995Price;
import com.pms.base.dao.BaseDao;

public interface Au9995PriceDao extends BaseDao<Au9995Price, String> {

	public Page<Au9995Price> findByDate(Pageable pageable, Date date);
	
	public List<Au9995Price> findByDate(Date date);
	
}
