package com.pms.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pms.app.dao.SupervisorDao;
import com.pms.app.entity.Supervisor;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
@Transactional
public class SupervisorService extends BaseService<Supervisor, String> {

	@Autowired private SupervisorDao supervisorDao;

	@Override
	protected BaseDao<Supervisor, String> getEntityDao() {
		return supervisorDao;
	}
	
	public List<Supervisor> findByUsernameAndPassword(String username, String password) {
		return supervisorDao.findByUsernameAndPassword(username, password);
	}
	
	public List<Supervisor> findByUnUsedList() {
		return supervisorDao.findByIsUsed(0);
	}
	
}
