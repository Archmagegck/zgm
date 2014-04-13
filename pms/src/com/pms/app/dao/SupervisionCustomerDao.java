package com.pms.app.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pms.app.entity.Delegator;
import com.pms.app.entity.SupervisionCustomer;
import com.pms.base.dao.BaseDao;

public interface SupervisionCustomerDao extends BaseDao<SupervisionCustomer, String>{
	
	public List<SupervisionCustomer> findListByDelegatorId(String delegatorId);
	
	public Page<SupervisionCustomer> findPageByDelegator(Delegator delegator, Pageable pageable);
	
	public Page<SupervisionCustomer> findPageByName(String name, Pageable pageable);
	
}
