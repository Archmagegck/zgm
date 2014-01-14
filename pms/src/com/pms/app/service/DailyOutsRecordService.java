package com.pms.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.OutsRecordDetailDao;
import com.pms.app.entity.OutsRecordDetail;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class DailyOutsRecordService extends BaseService<OutsRecordDetail, String> {

	@Autowired OutsRecordDetailDao outsRecordDetailDao;
	
	@Override
	protected BaseDao<OutsRecordDetail, String> getEntityDao() {
		return outsRecordDetailDao;
	}
	
}
