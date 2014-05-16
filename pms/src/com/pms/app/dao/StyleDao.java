package com.pms.app.dao;

import java.util.List;

import com.pms.app.entity.Style;
import com.pms.base.dao.BaseDao;

public interface StyleDao extends BaseDao<Style, String> {
	
	public List<Style> findListByName(String name);
	
}
