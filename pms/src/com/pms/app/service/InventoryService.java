package com.pms.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pms.app.dao.InventoryDao;
import com.pms.app.dao.InventoryDetailDao;
import com.pms.app.dao.PledgeConfigDao;
import com.pms.app.dao.PledgePurityDao;
import com.pms.app.dao.PledgeRecordDao;
import com.pms.app.dao.PledgeRecordDetailDao;
import com.pms.app.dao.StockDao;
import com.pms.app.dao.SupervisionCustomerDao;
import com.pms.app.entity.Delegator;
import com.pms.app.entity.Inventory;
import com.pms.app.entity.InventoryDetail;
import com.pms.app.entity.PledgeConfig;
import com.pms.app.entity.PledgePurity;
import com.pms.app.entity.PledgeRecord;
import com.pms.app.entity.PledgeRecordDetail;
import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.Supervisor;
import com.pms.app.entity.Warehouse;
import com.pms.app.util.CodeUtils;
import com.pms.app.util.Digests;
import com.pms.app.util.Encodes;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;
import com.pms.base.service.ServiceException;

@Service
public class InventoryService extends BaseService<Inventory, String> {

	@Autowired private InventoryDao inventoryDao;
	@Autowired private InventoryDetailDao inventoryDetailDao;
	@Autowired private StockDao stockDao;
	@Autowired private PledgeConfigDao pledgeConfigDao;
	@Autowired private SupervisionCustomerDao supervisionCustomerDao;
	@Autowired private PledgePurityDao pledgePurityDao;
	@Autowired private PledgeRecordDao pledgeRecordDao;
	@Autowired private PledgeRecordDetailDao pledgeRecordDetailDao;

	@Override
	protected BaseDao<Inventory, String> getEntityDao() {
		return inventoryDao;
	}
	
	@Transactional
	public void save(Warehouse warehouse, SupervisionCustomer supervisionCustomer, String supName, List<InventoryDetail> inventoryDetails) {
		Inventory inventory = new Inventory();
		inventory.setWarehouse(warehouse);
		inventory.setCode(CodeUtils.getInventoryCode(supervisionCustomer.getCode()));
		inventory.setSupName(supName);
		List<InventoryDetail> inventoryDetailList = new ArrayList<InventoryDetail>();
		for (InventoryDetail inventoryDetail : inventoryDetails) {
			inventoryDetail.setInventory(inventory);
			inventoryDetailList.add(inventoryDetail);
		}
		inventoryDao.save(inventory);
		inventoryDetailDao.save(inventoryDetailList);
	}

	public void save(List<InventoryDetail> inventoryDetailList, Warehouse warehouse, Supervisor supervisor) {
		Date now = new Date();
		Inventory inventory = new Inventory();
		inventory.setWarehouse(warehouse);
		inventory.setCode(CodeUtils.getInventoryCode(warehouse.getId()));
		inventory.setSupName(supervisor.getName());
		inventory.setDate(now);
		
		PledgePurity pledgePurity = pledgePurityDao.findListByType(1).get(0);
		SupervisionCustomer supervisionCustomer = supervisionCustomerDao.findListByWarehouseId(warehouse.getId()).get(0);
		Delegator delegator = supervisionCustomer.getDelegator();
		PledgeRecord pledgeRecord = new PledgeRecord();
		String code = CodeUtils.getPledgeRecordCode(warehouse.getId());
		pledgeRecord.setCode(code);
		pledgeRecord.setDate(new Date());
		pledgeRecord.setDelegator(delegator);
		pledgeRecord.setPasscode(Encodes.encodeHex(Digests.md5(code.getBytes())));
		pledgeRecord.setSupervisionCustomer(supervisionCustomer);
		pledgeRecord.setWarehouse(warehouse);
		List<PledgeRecordDetail> pledgeRecordDetails = new ArrayList<PledgeRecordDetail>();
		
		double invSumWeight = 0.0;
		for (InventoryDetail inventoryDetail : inventoryDetailList) {
			invSumWeight += inventoryDetail.getWeight();
			
			inventoryDetail.setInventory(inventory);
			inventoryDetail.setDate(now);
			inventoryDetail.setWarehouse(warehouse);
			
			PledgeRecordDetail pledgeRecordDetail = new PledgeRecordDetail();
			pledgeRecordDetail.setPledgeRecord(pledgeRecord);
			pledgeRecordDetail.setStyle(inventoryDetail.getStyle());
			pledgeRecordDetail.setPledgePurity(pledgePurity);
			pledgeRecordDetail.setSumWeight(inventoryDetail.getWeight());
			pledgeRecordDetail.setStorage(warehouse.getAddress());
			
			pledgeRecordDetails.add(pledgeRecordDetail);
		}
		
		pledgeRecord.setSumWeight(invSumWeight);
		pledgeRecord.setPledgeRecordDetails(pledgeRecordDetails);
		
		inventory.setSumWeight(invSumWeight);
		double stockSumWeight = stockDao.findSumWeightByWarehouseId(warehouse.getId());
		
		PledgeConfig config = pledgeConfigDao.findBySupervisionCustomerId(supervisionCustomer.getId()).get(0);
		double range = config.getIeRange();
		if(Math.abs(stockSumWeight - invSumWeight) > range){
			throw new ServiceException("盘存不一致，已超出盘存误差范围!");
		}
		
		inventoryDao.save(inventory);
		inventoryDetailDao.save(inventoryDetailList);
		
		
		pledgeRecordDao.save(pledgeRecord);
		pledgeRecordDetailDao.save(pledgeRecordDetails);
		
	}

}
