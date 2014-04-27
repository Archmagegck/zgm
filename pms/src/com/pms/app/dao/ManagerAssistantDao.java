package com.pms.app.dao;

import java.util.List;

import com.pms.app.entity.ManagerAssistant;
import com.pms.base.dao.BaseDao;

public interface ManagerAssistantDao extends BaseDao<ManagerAssistant, String> {
	
	List<ManagerAssistant> findByUsernameAndPassword(String username, String password);

}
