package com.pms.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.AdminDao;
import com.pms.app.entity.Admin;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class AdminService extends BaseService<Admin, String> {

	@Autowired private AdminDao adminDao;

	@Override
	protected BaseDao<Admin, String> getEntityDao() {
		return adminDao;
	}
	
	public List<Admin> findByUsernameAndPassword(String username, String password) {
		return adminDao.findByUsernameAndPassword(username, password);
	}

}
