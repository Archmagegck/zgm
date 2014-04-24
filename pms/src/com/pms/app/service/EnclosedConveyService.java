package com.pms.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pms.app.dao.EnclosedConveyDao;
import com.pms.app.entity.EnclosedConvey;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class EnclosedConveyService extends BaseService<EnclosedConvey, String>{
	
	@Autowired EnclosedConveyDao enclosedConveyDao;
	
	@Override
	protected BaseDao<EnclosedConvey, String> getEntityDao() {
		return enclosedConveyDao;
	}
	
	public Page<EnclosedConvey> findPageByDelegatorId(Pageable page, String findByDelegatorId){
		return enclosedConveyDao.findPageByDelegatorId(page, findByDelegatorId);
	}
}
