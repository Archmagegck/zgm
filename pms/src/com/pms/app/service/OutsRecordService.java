package com.pms.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pms.app.dao.OutsRecordDao;
import com.pms.app.entity.OutsRecord;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
@Transactional
public class OutsRecordService extends BaseService<OutsRecord, String> {

	@Autowired private OutsRecordDao OutsRecordDao;

	@Override
	protected BaseDao<OutsRecord, String> getEntityDao() {
		return OutsRecordDao;
	}
	
	
	

}
