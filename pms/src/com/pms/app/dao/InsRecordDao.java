package com.pms.app.dao;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.pms.app.entity.InsRecord;
import com.pms.app.entity.InsRecordDetail;
import com.pms.base.dao.BaseDao;

public interface InsRecordDao extends BaseDao<InsRecord, String> {
	
	public List<InsRecord> findBySupervisionCustomerId(String id);


}
