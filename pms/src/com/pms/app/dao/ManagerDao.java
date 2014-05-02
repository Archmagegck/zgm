package com.pms.app.dao;

import java.util.List;

import com.pms.app.entity.Manager;
import com.pms.base.dao.BaseDao;

public interface ManagerDao extends BaseDao<Manager, String> {

	List<Manager> findByUsernameAndPassword(String username, String password);
	
}
