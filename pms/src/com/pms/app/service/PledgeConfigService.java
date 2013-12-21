package com.pms.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.PledgeConfigDao;
import com.pms.app.entity.PledgeConfig;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class PledgeConfigService extends BaseService<PledgeConfig, String> {

	@Autowired private PledgeConfigDao pledgeConfigDao;

	@Override
	protected BaseDao<PledgeConfig, String> getEntityDao() {
		return pledgeConfigDao;
	}

}
