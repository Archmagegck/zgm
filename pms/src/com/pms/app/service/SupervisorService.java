package com.pms.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pms.app.dao.SupervisorDao;
import com.pms.app.dao.WarehouseDao;
import com.pms.app.entity.Supervisor;
import com.pms.app.entity.Warehouse;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
@Transactional
public class SupervisorService extends BaseService<Supervisor, String> {

	@Autowired private SupervisorDao supervisorDao;
	@Autowired private WarehouseDao warehouseDao;

	@Override
	protected BaseDao<Supervisor, String> getEntityDao() {
		return supervisorDao;
	}
	
	public List<Supervisor> findByUsernameAndPassword(String username, String password) {
		return supervisorDao.findByUsernameAndPassword(username, password);
	}
	
	public Warehouse findWarehouseBySupervisorId(String supervisorId) {
		return warehouseDao.findListBySupervisorId(supervisorId).get(0);
	}

	public void save(Supervisor supervisor) {
		supervisorDao.save(supervisor);
		warehouseDao.save(new Warehouse(supervisor));
	}

	public void delete(String[] idGroup) {
		for (String id : idGroup) {
			Supervisor supervisor = supervisorDao.findOne(id);
			warehouseDao.delete(findWarehouseBySupervisorId(supervisor.getId()));
			delete(supervisor);
		}
	}

}
