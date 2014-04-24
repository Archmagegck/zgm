package com.pms.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.OutsRecordDao;
import com.pms.app.entity.OutsRecord;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class DailyOutsRecordService extends BaseService<OutsRecord, String> {

	@Autowired OutsRecordDao outsRecordDao;
	
	@Override
	protected BaseDao<OutsRecord, String> getEntityDao() {
		return outsRecordDao;
	}
	

}
