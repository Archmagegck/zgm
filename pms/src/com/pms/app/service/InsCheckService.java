package com.pms.app.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pms.app.dao.InsCheckDao;
import com.pms.app.dao.InsCheckDetailDao;
import com.pms.app.entity.InsCheck;
import com.pms.app.entity.InsCheckDetail;
import com.pms.app.entity.Supervisor;
import com.pms.app.entity.Warehouse;
import com.pms.app.entity.reference.CheckMethod;
import com.pms.app.util.CodeUtils;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;
import com.pms.base.service.ServiceException;

@Service
public class InsCheckService extends BaseService<InsCheck, String> {

	@Autowired private InsCheckDao insCheckDao;
	@Autowired private InsCheckDetailDao insCheckDetailDao;

	@Override
	protected BaseDao<InsCheck, String> getEntityDao() {
		return insCheckDao;
	}

	@Transactional
	public void save(double sumWeight, double gpWeight, double rjWeight, List<InsCheckDetail> insCheckDetailsList, Supervisor supervisor, String warehouseId) {
		Date now = new Date();
		String code = CodeUtils.getInsRecordCode(warehouseId);
		InsCheck insCheck = new InsCheck();
		insCheck.setCode(code);
		insCheck.setDate(now);
		insCheck.setGpWeight(gpWeight);
		insCheck.setRjWeight(rjWeight);
		insCheck.setSumWeight(sumWeight);
		insCheck.setSupervisor(supervisor);
		Warehouse warehouse = new Warehouse();
		warehouse.setId(warehouseId);
		insCheck.setWarehouse(warehouse);
		
		double gpCheck = 0.0;
		double rjCheck = 0.0;
		
		for (InsCheckDetail insCheckDetail : insCheckDetailsList) {
			if(insCheckDetail.getCheckMethod() == CheckMethod.Spectrum) {
				gpCheck += insCheckDetail.getCheckWeight();
			}
			if(insCheckDetail.getCheckMethod() == CheckMethod.Dissolve) {
				rjCheck += insCheckDetail.getCheckWeight();
			}
			insCheckDetail.setInsCheck(insCheck);
		}
		insCheck.setInsCheckDetails(insCheckDetailsList);
		
		if(gpCheck < gpWeight || rjCheck < rjWeight) {
			throw new ServiceException("检测重量低于系统设定值!");
		}
		
		insCheckDao.save(insCheck);
		insCheckDetailDao.save(insCheckDetailsList);
		
		
	}
	
	

}
