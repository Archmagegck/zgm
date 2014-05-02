package com.pms.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.pms.app.entity.IniCheck;
import com.pms.base.dao.BaseDao;

public interface IniCheckDao extends BaseDao<IniCheck, String> {
	
	public List<IniCheck> findByWarehouseId(String warehouseId);
	
	@Query("select s.warehouse from IniCheck s GROUP BY s.warehouse")
	public List<Object[]> findTotalWarehouseList();
	
}
