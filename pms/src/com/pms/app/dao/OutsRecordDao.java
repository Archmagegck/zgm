package com.pms.app.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pms.app.entity.OutsRecord;
import com.pms.app.entity.reference.AuditState;
import com.pms.base.dao.BaseDao;

public interface OutsRecordDao extends BaseDao<OutsRecord, String> {
	
	public Page<OutsRecord> findPageByAuditStateNot(Pageable pageable, AuditState auditState);
	
	public Page<OutsRecord> findPageByNotice(Pageable pageable, int notice);
	
}
