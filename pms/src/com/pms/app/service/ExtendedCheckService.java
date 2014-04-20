package com.pms.app.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pms.app.dao.ExtendedCheckDao;
import com.pms.app.dao.ExtendedCheckDetailDao;
import com.pms.app.entity.ExtendedCheck;
import com.pms.app.entity.ExtendedCheckDetail;
import com.pms.app.entity.Supervisor;
import com.pms.app.entity.Warehouse;
import com.pms.app.util.CodeUtils;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class ExtendedCheckService extends BaseService<ExtendedCheck, String> {

	@Autowired private ExtendedCheckDao extendedCheckDao;
	@Autowired private ExtendedCheckDetailDao extendedCheckDetailDao;

	@Override
	protected BaseDao<ExtendedCheck, String> getEntityDao() {
		return extendedCheckDao;
	}

	@Transactional
	public void save(double sumWeight, double gpWeight, double rjWeight, List<ExtendedCheckDetail> extendedCheckDetailsList, Supervisor supervisor, String warehouseId) {
		Date now = new Date();
		String code = CodeUtils.getExtendedCheckCode(warehouseId);
		ExtendedCheck extendedCheck = new ExtendedCheck();
		extendedCheck.setCode(code);
		extendedCheck.setDate(now);
		extendedCheck.setGpWeight(gpWeight);
		extendedCheck.setRjWeight(rjWeight);
		extendedCheck.setSumWeight(sumWeight);
		extendedCheck.setSupervisor(supervisor);
		Warehouse warehouse = new Warehouse();
		warehouse.setId(warehouseId);
		extendedCheck.setWarehouse(warehouse);
		
		for (ExtendedCheckDetail extendedCheckDetail : extendedCheckDetailsList) {
			extendedCheckDetail.setExtendedCheck(extendedCheck);
		}
		extendedCheck.setExtendedCheckDetails(extendedCheckDetailsList);
		
		extendedCheckDao.save(extendedCheck);
		extendedCheckDetailDao.save(extendedCheckDetailsList);
		
		
	}
	
	

}
