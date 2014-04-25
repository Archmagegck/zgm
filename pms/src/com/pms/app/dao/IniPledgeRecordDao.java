package com.pms.app.dao;

import java.util.List;

import com.pms.app.entity.IniPledgeRecord;
import com.pms.base.dao.BaseDao;

public interface IniPledgeRecordDao extends BaseDao<IniPledgeRecord, String>{
	
	public List<IniPledgeRecord> findBySupervisionCustomerId(String id);
}
