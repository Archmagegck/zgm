package com.pms.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.StyleDao;
import com.pms.app.entity.Style;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class StyleService extends BaseService<Style, String> {

	@Autowired private StyleDao styleDao;

	@Override
	protected BaseDao<Style, String> getEntityDao() {
		return styleDao;
	}

}
