package com.pms.app.dao;

import java.util.List;

import com.pms.app.entity.PledgeConfig;
import com.pms.base.dao.BaseDao;

public interface PledgeConfigDao extends BaseDao<PledgeConfig, String>{
	
	public List<PledgeConfig> findBySupervisionCustomerId(String supervisionCustomerId);
	
}
