package com.pms.app.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pms.app.entity.EnclosedConvey;
import com.pms.base.dao.BaseDao;

public interface EnclosedConveyDao extends BaseDao<EnclosedConvey, String>{

	public Page<EnclosedConvey> findPageByDelegatorId(Pageable page, String findByDelegatorId);
}
