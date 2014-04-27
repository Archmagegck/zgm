package com.pms.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.ManagerAssistantDao;
import com.pms.app.entity.ManagerAssistant;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;


@Service
public class ManagerAssistantService extends BaseService<ManagerAssistant, String>{
	@Autowired private ManagerAssistantDao managerAssistantDao;

	@Override
	protected BaseDao<ManagerAssistant, String> getEntityDao() {
		return managerAssistantDao;
	}
	
	public List<ManagerAssistant> findByUsernameAndPassword(String username, String password) {
		return managerAssistantDao.findByUsernameAndPassword(username, password);
	}

}
