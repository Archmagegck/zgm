package com.pms.app.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pms.app.dao.Au9995PriceDao;
import com.pms.app.entity.Au9995Price;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class Au9995PriceService extends BaseService<Au9995Price, String> {

	@Autowired private Au9995PriceDao au9995PriceDao;

	@Override
	protected BaseDao<Au9995Price, String> getEntityDao() {
		return au9995PriceDao;
	}
	
	public boolean hasDatePrice(Date date) {
		List<Au9995Price> list = au9995PriceDao.findByDate(date);
		if(list.isEmpty()) return false;
		else return true;
	}
	
	public Page<Au9995Price> findByDate(Pageable pageable, Date date) {
		if(date == null)
			return au9995PriceDao.findAll(pageable);
		else
			return au9995PriceDao.findByDate(pageable, date);
	}

}
