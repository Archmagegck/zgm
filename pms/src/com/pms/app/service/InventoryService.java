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
import com.pms.app.dao.StockDao;
import com.pms.app.dao.SupervisionCustomerDao;
import com.pms.app.entity.Inventory;
import com.pms.app.entity.InventoryDetail;
import com.pms.app.entity.PledgeConfig;
import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.Supervisor;
import com.pms.app.entity.Warehouse;
import com.pms.app.util.CodeUtils;
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
		
		double invSumWeight = 0.0;
		for (InventoryDetail inventoryDetail : inventoryDetailList) {
			invSumWeight += inventoryDetail.getWeight();
			
			inventoryDetail.setInventory(inventory);
			inventoryDetail.setDate(now);
			inventoryDetail.setWarehouse(warehouse);
		}
		
		inventory.setSumWeight(invSumWeight);
		double stockSumWeight = stockDao.findSumWeightByWarehouseId(warehouse.getId());
		SupervisionCustomer supervisionCustomer = supervisionCustomerDao.findListByWarehouseId(warehouse.getId()).get(0);
		PledgeConfig config = pledgeConfigDao.findBySupervisionCustomerId(supervisionCustomer.getId()).get(0);
		double range = config.getIeRange();
		if(Math.abs(stockSumWeight - invSumWeight) > range){
			throw new ServiceException("盘存不一致，已超出盘存误差范围!");
		}
		
		inventoryDao.save(inventory);
		inventoryDetailDao.save(inventoryDetailList);
		
	}
	
	
	public List<Inventory> findByWarehouseIdAndDateBetween(String warehouseId, Date dateBegin, Date dateEnd) {
		
 		return inventoryDao.findByWarehouseIdAndDateBetween(warehouseId, dateBegin, dateEnd);
		
	}
	
	
	public List<Inventory> findByDateBetween(Date dateBegin, Date dateEnd) {
		
 		return inventoryDao.findByDateBetween(dateBegin, dateEnd);
		
	}

}
