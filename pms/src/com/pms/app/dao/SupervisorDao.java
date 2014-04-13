package com.pms.app.dao;

import java.util.List;

import com.pms.app.entity.Supervisor;
import com.pms.base.dao.BaseDao;

public interface SupervisorDao extends BaseDao<Supervisor, String> {
	
	List<Supervisor> findByUsernameAndPassword(String username, String password);
	
}
