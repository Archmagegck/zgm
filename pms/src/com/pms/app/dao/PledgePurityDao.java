package com.pms.app.dao;

import java.util.List;

import com.pms.app.entity.PledgePurity;
import com.pms.base.dao.BaseDao;

public interface PledgePurityDao extends BaseDao<PledgePurity, String>{
	
	public List<PledgePurity> findListByType(int type);
	
}
