package com.pms.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.ManagerDao;
import com.pms.app.entity.Manager;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class ManagerService extends BaseService<Manager, String> {

	@Autowired private ManagerDao managerDao;

	@Override
	protected BaseDao<Manager, String> getEntityDao() {
		return managerDao;
	}
	
	public List<Manager> findByUsernameAndPassword(String username, String password) {
		return managerDao.findByUsernameAndPassword(username, password);
	}

}
