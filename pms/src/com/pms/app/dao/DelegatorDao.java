package com.pms.app.dao;

import java.util.List;

import com.pms.app.entity.Delegator;
import com.pms.base.dao.BaseDao;

public interface DelegatorDao extends BaseDao<Delegator, String>{
	
	List<Delegator> findByUsernameAndPassword(String username, String password);
}
