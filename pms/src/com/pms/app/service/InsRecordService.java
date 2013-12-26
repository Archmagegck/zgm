package com.pms.app.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pms.app.dao.InsRecordDao;
import com.pms.app.dao.InsRecordDetailDao;
import com.pms.app.dao.WarehouseDao;
import com.pms.app.entity.InsRecord;
import com.pms.app.entity.InsRecordDetail;
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
