package com.pms.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.PledgePurityDao;
import com.pms.app.entity.PledgePurity;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class PledgePurityService extends BaseService<PledgePurity, String> {

	@Autowired private PledgePurityDao pledgePurityDao;

	@Override
	protected BaseDao<PledgePurity, String> getEntityDao() {
		return pledgePurityDao;
	}

}
