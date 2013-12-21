package com.pms.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.SupervisionCustomerDao;
import com.pms.app.entity.SupervisionCustomer;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class SupervisionCustomerService extends BaseService<SupervisionCustomer, String> {

	@Autowired private SupervisionCustomerDao supervisionCustomerDao;

	@Override
	protected BaseDao<SupervisionCustomer, String> getEntityDao() {
		return supervisionCustomerDao;
	}

}
