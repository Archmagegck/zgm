package com.pms.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.pms.app.entity.IniCheck;
import com.pms.app.entity.IniRecord;
import com.pms.base.dao.BaseDao;

public interface IniRecordDao extends BaseDao<IniRecord, String> {
	
	public List<IniRecord> findByWarehouseId(String warehouseId);
	
	@Query("select s.warehouse from IniRecord s GROUP BY s.warehouse")
	public List<Object[]> findTotalWarehouseList();
}
