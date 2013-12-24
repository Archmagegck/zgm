package com.pms.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.CheckDenyDao;
import com.pms.app.entity.CheckDeny;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class CheckDenyService extends BaseService<CheckDeny, String> {

	@Autowired private CheckDenyDao checkDenyDao;

	@Override
	protected BaseDao<CheckDeny, String> getEntityDao() {
		return checkDenyDao;
	}

}
