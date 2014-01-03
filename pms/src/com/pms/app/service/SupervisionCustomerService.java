package com.pms.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.pms.app.dao.PledgeConfigDao;
import com.pms.app.dao.SupervisionCustomerDao;
import com.pms.app.dao.SupervisorDao;
import com.pms.app.dao.WarehouseDao;
import com.pms.app.entity.PledgeConfig;
import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.Supervisor;
import com.pms.app.entity.Warehouse;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class SupervisionCustomerService extends BaseService<SupervisionCustomer, String> {

	@Autowired private SupervisionCustomerDao supervisionCustomerDao;
	@Autowired private PledgeConfigDao pledgeConfigDao;
	@Autowired private SupervisorDao supervisorDao;
	@Autowired private WarehouseDao warehouseDao;

	@Override
	protected BaseDao<SupervisionCustomer, String> getEntityDao() {
		return supervisionCustomerDao;
	}
	
	@Transactional
	public void save(SupervisionCustomer supervisionCustomer, String oldWarehouseId, String oldSupervisorId){
		if(!StringUtils.hasText(supervisionCustomer.getId())) {
			PledgeConfig pledgeConfig = new PledgeConfig();
			pledgeConfig.setDelegator(supervisionCustomer.getDelegator());
			pledgeConfig.setSupervisionCustomer(supervisionCustomer);
			pledgeConfig.setSupervisor(supervisionCustomer.getSupervisor());
			pledgeConfigDao.save(pledgeConfig);
		} else {
			if(StringUtils.hasText(oldWarehouseId)) {
				Warehouse warehouseOld = warehouseDao.findOne(oldWarehouseId);
				warehouseOld.setIsUsed(0);
				warehouseDao.save(warehouseOld);
			}
			if(StringUtils.hasText(oldSupervisorId)) {
				Supervisor supervisorOld = supervisorDao.findOne(oldSupervisorId);
				supervisorOld.setIsUsed(0);
				supervisorDao.save(supervisorOld);
			}
		}
		
		Warehouse warehouse = warehouseDao.findOne(supervisionCustomer.getWarehouse().getId());
		warehouse.setIsUsed(1);
		warehouseDao.save(warehouse);
		
		Supervisor supervisor = supervisorDao.findOne(supervisionCustomer.getSupervisor().getId());
		supervisor.setIsUsed(1);
		supervisorDao.save(supervisor);
		
		supervisionCustomerDao.save(supervisionCustomer);
	}
	
	
	@Transactional
	public void delete(String id) {
		List<PledgeConfig> pledgeConfigs = pledgeConfigDao.findBySupervisionCustomerId(id);
		if(!pledgeConfigs.isEmpty()) {
			pledgeConfigDao.delete(pledgeConfigs);
		}
		SupervisionCustomer supervisionCustomer = supervisionCustomerDao.findOne(id);
		
		Warehouse warehouse = warehouseDao.findOne(supervisionCustomer.getWarehouse().getId());
		warehouse.setIsUsed(0);
		warehouseDao.save(warehouse);
		
		Supervisor supervisor = supervisorDao.findOne(supervisionCustomer.getSupervisor().getId());
		supervisor.setIsUsed(0);
		supervisorDao.save(supervisor);
		
		supervisionCustomerDao.delete(supervisionCustomer);
	}
	
	
	
	

}
