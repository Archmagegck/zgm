package com.pms.app.dao;

import java.util.Date;
import java.util.List;

import com.pms.app.entity.ExtendedCheck;
import com.pms.base.dao.BaseDao;

public interface ExtendedCheckDao extends BaseDao<ExtendedCheck, String> {

	public List<ExtendedCheck> findByDateBetween(Date dateBegin, Date dateEnd);

	public List<ExtendedCheck> findByWarehouseIdAndDateBetween(String warehouseId, Date dateBegin, Date dateEnd);
	
}
