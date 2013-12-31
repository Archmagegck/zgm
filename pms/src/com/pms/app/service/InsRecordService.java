package com.pms.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pms.app.dao.InsRecordDao;
import com.pms.app.dao.InsRecordDetailDao;
import com.pms.app.dao.WarehouseDao;
import com.pms.app.entity.InsRecord;
import com.pms.app.entity.InsRecordDetail;
import com.pms.app.entity.Warehouse;
import com.pms.app.util.CodeUtils;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
@Transactional
public class InsRecordService extends BaseService<InsRecord, String> {

	@Autowired private InsRecordDao insRecordDao;
	@Autowired private InsRecordDetailDao insRecordDetailDao;
	@Autowired private WarehouseDao warehouseDao;

	@Override
	protected BaseDao<InsRecord, String> getEntityDao() {
		return insRecordDao;
	}

	
	// TODO 
	public void save(InsRecord insRecord, String warehouseId) {
		Warehouse Warehouse = warehouseDao.findOne(warehouseId);
		
		insRecord.setCode(CodeUtils.getInsRecordCode());
		double sumWeight = 0;
		List<InsRecordDetail> insRecordDetailList = insRecord.getInsRecordDetails();
		for(InsRecordDetail detail : insRecordDetailList) {
			sumWeight += detail.getSumWeight();
			detail.setInsRecord(insRecord);
		}
		insRecord.setSumWeight(new BigDecimal(sumWeight).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		insRecordDao.save(insRecord);
		insRecordDetailDao.save(insRecordDetailList);
	}
	
	

}
