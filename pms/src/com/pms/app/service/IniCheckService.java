package com.pms.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pms.app.dao.IniCheckDao;
import com.pms.app.entity.IniCheck;
import com.pms.app.entity.IniRecord;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class IniCheckService extends BaseService<IniCheck, String> {

	@Autowired private IniCheckDao iniCheckDao;
	@Autowired private IniRecordService iniRecordService;
	@Autowired private IniPledgeRecordService iniPledgeRecordService;

	@Override
	protected BaseDao<IniCheck, String> getEntityDao() {
		return iniCheckDao;
	}
	
	@Transactional
	public void save(List<IniCheck> iniCheckList, String warehouseId) {
		super.save(iniCheckList);
		
		List<IniRecord> iniRecordList = iniRecordService.findAllEq("warehouse.id", warehouseId);
		if(!iniRecordList.isEmpty()) {
			iniPledgeRecordService.save(iniRecordList, iniCheckList, warehouseId);
		}
	}

}
