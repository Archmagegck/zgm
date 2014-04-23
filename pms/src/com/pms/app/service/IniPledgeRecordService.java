package com.pms.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.IniPledgeRecordDao;

import com.pms.app.entity.IniPledgeRecord;

import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;


@Service
public class IniPledgeRecordService extends BaseService<IniPledgeRecord, String>{
	@Autowired private IniPledgeRecordDao iniPledgeRecordDao;

	@Override
	protected BaseDao<IniPledgeRecord, String> getEntityDao() {
		return iniPledgeRecordDao;
	}
}
