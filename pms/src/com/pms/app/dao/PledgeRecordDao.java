package com.pms.app.dao;

import java.util.List;

import com.pms.app.entity.PledgeRecord;
import com.pms.base.dao.BaseDao;

public interface PledgeRecordDao extends BaseDao<PledgeRecord, String> {
	
	public List<PledgeRecord> findBySupervisionCustomerId(String id);
}
