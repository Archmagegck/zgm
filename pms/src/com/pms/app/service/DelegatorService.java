package com.pms.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.DelegatorDao;
import com.pms.app.entity.Delegator;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class DelegatorService extends BaseService<Delegator, String> {

	@Autowired private DelegatorDao delegatorDao;

	@Override
	protected BaseDao<Delegator, String> getEntityDao() {
		return delegatorDao;
	}
	
	public List<Delegator> findByUsernameAndPassword(String username, String password) {
		return delegatorDao.findByUsernameAndPassword(username, password);
	}

}
