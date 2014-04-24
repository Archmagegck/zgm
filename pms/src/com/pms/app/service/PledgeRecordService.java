package com.pms.app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.pms.app.dao.PledgeRecordDao;

import com.pms.app.entity.PledgeRecord;

import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class PledgeRecordService extends BaseService<PledgeRecord, String> {

	@Autowired private PledgeRecordDao PledgeRecordDao;

	@Override
	protected BaseDao<PledgeRecord, String> getEntityDao() {
		return PledgeRecordDao;
	}


}
