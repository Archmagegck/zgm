package com.pms.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pms.app.dao.InventoryDao;
import com.pms.app.dao.InventoryDetailDao;
import com.pms.app.dao.PledgePurityDao;
import com.pms.app.entity.Inventory;
import com.pms.app.entity.InventoryDetail;
import com.pms.app.entity.PledgePurity;
import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.Warehouse;
import com.pms.app.util.CodeUtils;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class InventoryService extends BaseService<Inventory, String> {

	@Autowired private InventoryDao inventoryDao;
	@Autowired private InventoryDetailDao inventoryDetailDao;
	@Autowired private PledgePurityDao pledgePurityDao;

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
			PledgePurity pledgePurity = pledgePurityDao.findOne(inventoryDetail.getPledgePurity().getId());
			inventoryDetail.setPledgePurity(pledgePurity);
			inventoryDetail.setInventory(inventory);
			if(inventoryDetail.getEqual() == 0) {
				inventory.setEquation(0);
			}
			inventoryDetail.setDelegator(supervisionCustomer.getDelegator());
			inventoryDetail.setSupervisionCustomer(supervisionCustomer);
			inventoryDetailList.add(inventoryDetail);
		}
		inventoryDao.save(inventory);
		inventoryDetailDao.save(inventoryDetailList);
	}

}
