package com.pms.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.PledgeRecordDetailDao;
import com.pms.app.entity.PledgeRecordDetail;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class PledgeRecordDetailService extends BaseService<PledgeRecordDetail, String>  {
	
	@Autowired private PledgeRecordDetailDao pledgeRecordDetailDao;

	@Override
	protected BaseDao<PledgeRecordDetail, String> getEntityDao() {
		return pledgeRecordDetailDao;
	}
}
