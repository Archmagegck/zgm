package com.pms.app.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pms.app.entity.OutsRecord;
import com.pms.app.entity.reference.AuditState;
import com.pms.base.dao.BaseDao;

public interface OutsRecordDao extends BaseDao<OutsRecord, String> {
	
	public Page<OutsRecord> findPageByAuditStateNot(Pageable pageable, AuditState auditState);
	
	public Page<OutsRecord> findPageByNoticeAndAuditStateNot(Pageable pageable, Integer notice, AuditState auditState);
	
	public Page<OutsRecord> findPageByNoticeOrNoticeAndAuditStateNot(Pageable pageable, Integer notice1,Integer notice2, AuditState auditState);
	
	public Page<OutsRecord> findPageByNotice(Pageable pageable, int notice);
	
	public List<OutsRecord> findBySupervisionCustomerId(String id);
	
	public List<OutsRecord> findByAuditStateNot(AuditState auditState);
}
