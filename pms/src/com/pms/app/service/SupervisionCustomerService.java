package com.pms.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.pms.app.dao.PledgeConfigDao;
import com.pms.app.dao.SupervisionCustomerDao;
import com.pms.app.entity.Delegator;
import com.pms.app.entity.PledgeConfig;
import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.Supervisor;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class SupervisionCustomerService extends BaseService<SupervisionCustomer, String> {

	@Autowired private SupervisionCustomerDao supervisionCustomerDao;
	@Autowired private SupervisorService supervisorService;
	@Autowired private PledgeConfigDao pledgeConfigDao;

	@Override
	protected BaseDao<SupervisionCustomer, String> getEntityDao() {
		return supervisionCustomerDao;
	}
	
	public Page<SupervisionCustomer> findByDelegator(Delegator delegator, Pageable page) {
		Page<SupervisionCustomer> supervisionCustomerPage = supervisionCustomerDao.findPageByDelegator(delegator,page);
		return supervisionCustomerPage;
	}
	
	public List<SupervisionCustomer> findListByDelegatorId(String delegatorId){
		return supervisionCustomerDao.findListByDelegatorId(delegatorId);
	}
	
	@Transactional
	public void save(SupervisionCustomer supervisionCustomer){
		PledgeConfig pledgeConfig = new PledgeConfig();
		if(StringUtils.hasText(supervisionCustomer.getId())) {
			List<PledgeConfig> pledgeConfigList = pledgeConfigDao.findBySupervisionCustomerId(supervisionCustomer.getId());
			if(!pledgeConfigList.isEmpty()) {
				pledgeConfig = pledgeConfigList.get(0);
			}
		} 
		pledgeConfig.setDelegator(supervisionCustomer.getDelegator());
		pledgeConfig.setSupervisionCustomer(supervisionCustomer);
		
		Supervisor supervisor = supervisorService.findOneByWarehouseId(supervisionCustomer.getWarehouse().getId());
		pledgeConfig.setSupervisor(supervisor);
		pledgeConfigDao.save(pledgeConfig);
		
		supervisionCustomerDao.save(supervisionCustomer);
	}
	
	
	@Transactional
	public void delete(String id) {
		List<PledgeConfig> pledgeConfigs = pledgeConfigDao.findBySupervisionCustomerId(id);
		if(!pledgeConfigs.isEmpty()) {
			pledgeConfigDao.delete(pledgeConfigs);
		}
		supervisionCustomerDao.delete(id);
	}
	
	
	
	

}
