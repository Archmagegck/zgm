package com.pms.app.dao;

import java.util.List;

import com.pms.app.entity.Admin;
import com.pms.base.dao.BaseDao;

public interface AdminDao extends BaseDao<Admin, String> {

	List<Admin> findByUsernameAndPassword(String username, String password);
	
}
