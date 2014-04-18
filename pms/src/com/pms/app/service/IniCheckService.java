package com.pms.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.IniCheckDao;
import com.pms.app.entity.IniCheck;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class IniCheckService extends BaseService<IniCheck, String> {

	@Autowired private IniCheckDao iniCheckDao;

	@Override
	protected BaseDao<IniCheck, String> getEntityDao() {
		return iniCheckDao;
	}

}
