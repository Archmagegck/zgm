package com.pms.app.dao;

import java.util.List;

import com.pms.app.entity.SupervisionCustomer;
import com.pms.base.dao.BaseDao;

public interface SupervisionCustomerDao extends BaseDao<SupervisionCustomer, String>{
	
	public List<SupervisionCustomer> findListBySupervisorId(String supervisorId);
	
}
