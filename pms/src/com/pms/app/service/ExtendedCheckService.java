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
import com.pms.app.entity.reference.CheckMethod;
import com.pms.app.util.CodeUtils;
import com.pms.app.util.DateUtils;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;
import com.pms.base.service.ServiceException;

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
		
		double gpCheck = 0.0;
		double rjCheck = 0.0;
		
		for (ExtendedCheckDetail extendedCheckDetail : extendedCheckDetailsList) {
			if(extendedCheckDetail.getCheckMethod() == CheckMethod.Spectrum) {
				gpCheck += extendedCheckDetail.getCheckWeight();
			}
			if(extendedCheckDetail.getCheckMethod() == CheckMethod.Dissolve) {
				rjCheck += extendedCheckDetail.getCheckWeight();
			}
			extendedCheckDetail.setExtendedCheck(extendedCheck);
		}
		extendedCheck.setExtendedCheckDetails(extendedCheckDetailsList);
		
		if(gpCheck < gpWeight || rjCheck < rjWeight) {
			throw new ServiceException("检测重量低于系统设定值!");
		}
		
		extendedCheckDao.save(extendedCheck);
		extendedCheckDetailDao.save(extendedCheckDetailsList);
		
		
	}
	
	public List<ExtendedCheck> findByWarehouseIdAndDateBetween(String warehouseId, Date dateBegin, Date dateEnd) {
 		return extendedCheckDao.findByWarehouseIdAndDateBetween(warehouseId, dateBegin, dateEnd);
	}
	
	public List<ExtendedCheck> findByDateBetween(Date dateBegin, Date dateEnd) {
 		return extendedCheckDao.findByDateBetween(dateBegin, dateEnd);
	}
	
	public ExtendedCheck findByDateDay(Date date) {
		List<ExtendedCheck> extendedChecks = extendedCheckDao.findByDateBetween(DateUtils.dateToDayBegin(date), DateUtils.dateToDayEnd(date));
		if(extendedChecks.isEmpty()) return null;
		return extendedChecks.get(0);
	}

	public ExtendedCheck findByWarehouseDateDay(String warehouseId, Date date) {
		List<ExtendedCheck> extendedChecks = extendedCheckDao.findByWarehouseIdAndDateBetween(warehouseId, DateUtils.dateToDayBegin(date), DateUtils.dateToDayEnd(date));
		if(extendedChecks.isEmpty()) return null;
		return extendedChecks.get(0);
	}

}
