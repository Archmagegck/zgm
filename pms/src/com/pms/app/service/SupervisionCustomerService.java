package com.pms.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.pms.app.dao.IniPledgeRecordDao;
import com.pms.app.dao.IniPledgeRecordDetailDao;
import com.pms.app.dao.InsRecordDao;
import com.pms.app.dao.InsRecordDetailDao;
import com.pms.app.dao.OutsRecordDao;
import com.pms.app.dao.OutsRecordDetailDao;
import com.pms.app.dao.PledgeConfigDao;
import com.pms.app.dao.PledgeRecordDao;
import com.pms.app.dao.PledgeRecordDetailDao;
import com.pms.app.dao.SupervisionCustomerDao;
import com.pms.app.entity.Delegator;
import com.pms.app.entity.IniPledgeRecord;
import com.pms.app.entity.IniPledgeRecordDetail;
import com.pms.app.entity.InsRecord;
import com.pms.app.entity.InsRecordDetail;
import com.pms.app.entity.OutsRecord;
import com.pms.app.entity.OutsRecordDetail;
import com.pms.app.entity.PledgeConfig;
import com.pms.app.entity.PledgeRecord;
import com.pms.app.entity.PledgeRecordDetail;
import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.Supervisor;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class SupervisionCustomerService extends BaseService<SupervisionCustomer, String> {

	@Autowired private SupervisionCustomerDao supervisionCustomerDao;
	@Autowired private SupervisorService supervisorService;
	@Autowired private PledgeConfigDao pledgeConfigDao;
	@Autowired private OutsRecordDao outsRecordDao;
	@Autowired private InsRecordDao insRecordDao;
	@Autowired private PledgeRecordDao pledgeRecordDao;
	@Autowired private IniPledgeRecordDao iniPledgeRecordDao;
	@Autowired private OutsRecordDetailDao outsRecordDetailDao;
	@Autowired private InsRecordDetailDao insRecordDetailDao;
	@Autowired private PledgeRecordDetailDao pledgeRecordDetailDao;
	@Autowired private IniPledgeRecordDetailDao iniPledgeRecordDetailDao;

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
	
	public List<SupervisionCustomer> findListByWarehouseId(String warehouseId){
		return supervisionCustomerDao.findListByWarehouseId(warehouseId);
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
		
		List<OutsRecord> outsRecords = outsRecordDao.findBySupervisionCustomerId(id);
		if(!outsRecords.isEmpty()){
			for(OutsRecord outsRecord : outsRecords){
				List<OutsRecordDetail> outsRecordDetails = outsRecord.getOutsRecordDetails();
				if(!outsRecordDetails.isEmpty())
					outsRecordDetailDao.delete(outsRecordDetails);
			}
			outsRecordDao.delete(outsRecords);
		}
		
		List<InsRecord> insRecords = insRecordDao.findBySupervisionCustomerId(id);
		if(!insRecords.isEmpty()){
			for(InsRecord insRecord : insRecords){
				List<InsRecordDetail> insRecordDetails = insRecord.getInsRecordDetails();
				if(!insRecordDetails.isEmpty())
					insRecordDetailDao.delete(insRecordDetails);
			}
			insRecordDao.delete(insRecords);
		}
		
		List<PledgeRecord> pledgeRecords = pledgeRecordDao.findBySupervisionCustomerId(id);
		if(!pledgeRecords.isEmpty()){
			for(PledgeRecord pledgeRecord : pledgeRecords){
				List<PledgeRecordDetail> pledgeRecordDetails = pledgeRecord.getPledgeRecordDetails();
				if(!pledgeRecordDetails.isEmpty())
					pledgeRecordDetailDao.delete(pledgeRecordDetails);
			}
			pledgeRecordDao.delete(pledgeRecords);
		}
		
		List<IniPledgeRecord> iniPledgeRecords = iniPledgeRecordDao.findBySupervisionCustomerId(id);
		if(!iniPledgeRecords.isEmpty()){
			for(IniPledgeRecord iniPledgeRecord : iniPledgeRecords){
				List<IniPledgeRecordDetail> iniPledgeRecordDetails =  iniPledgeRecord.getIniPledgeRecordDetails();
				if(!iniPledgeRecordDetails.isEmpty()){
					iniPledgeRecordDetailDao.delete(iniPledgeRecordDetails);
				}
			}
			iniPledgeRecordDao.delete(iniPledgeRecords);
		}
		supervisionCustomerDao.delete(id);
	}
	

}
