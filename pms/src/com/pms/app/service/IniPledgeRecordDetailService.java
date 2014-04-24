package com.pms.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pms.app.dao.IniPledgeRecordDetailDao;
import com.pms.app.entity.IniPledgeRecordDetail;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class IniPledgeRecordDetailService extends BaseService<IniPledgeRecordDetail, String> {
	@Autowired private IniPledgeRecordDetailDao iniPledgeRecordDetailDao;

	@Override
	protected BaseDao<IniPledgeRecordDetail, String> getEntityDao() {
		return iniPledgeRecordDetailDao;
	}
}
