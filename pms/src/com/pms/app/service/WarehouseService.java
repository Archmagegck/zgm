package com.pms.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.SupervisionCustomerDao;
import com.pms.app.dao.WarehouseDao;
import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.Warehouse;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class WarehouseService extends BaseService<Warehouse, String> {

	@Autowired private WarehouseDao warehouseDao;
	@Autowired private SupervisionCustomerDao supervisionCustomerDao;

	@Override
	protected BaseDao<Warehouse, String> getEntityDao() {
		return warehouseDao;
	}

	public Warehouse findWarehouseBySupervisorId(String supervisorId) {
		List<SupervisionCustomer> supervisionCustomerList = supervisionCustomerDao.findListBySupervisorId(supervisorId);
		if(supervisionCustomerList.isEmpty()) return null;
		Warehouse warehouse = supervisionCustomerList.get(0).getWarehouse();
		return (warehouse != null) ? warehouse : null;
	}
	
}
