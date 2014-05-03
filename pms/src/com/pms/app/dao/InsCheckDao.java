package com.pms.app.dao;

import java.util.Date;
import java.util.List;

import com.pms.app.entity.InsCheck;
import com.pms.base.dao.BaseDao;

public interface InsCheckDao extends BaseDao<InsCheck, String> {

	public List<InsCheck> findByDateBetween(Date dateBegin, Date dateEnd);

	public List<InsCheck> findByWarehouseIdAndDateBetween(String warehouseId, Date dateBegin, Date dateEnd);
	
}
