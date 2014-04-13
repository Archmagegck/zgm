package com.pms.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pms.app.dao.PledgeRecordDao;
import com.pms.app.dao.PledgeRecordDetailDao;
import com.pms.app.entity.PledgeRecord;
import com.pms.app.entity.PledgeRecordDetail;
import com.pms.app.entity.Stock;
import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.Warehouse;
import com.pms.app.util.CodeUtils;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class PledgeRecordService extends BaseService<PledgeRecord, String> {

	@Autowired private PledgeRecordDao PledgeRecordDao;
	@Autowired private PledgeRecordDetailDao pledgeRecordDetailDao;

	@Override
	protected BaseDao<PledgeRecord, String> getEntityDao() {
		return PledgeRecordDao;
	}

	@Transactional
	public void save(SupervisionCustomer supervisionCustomer, Warehouse warehouse, List<Stock> stocks) {
		List<PledgeRecordDetail> pledgeRecordDetails = new ArrayList<PledgeRecordDetail>();
		PledgeRecord pledgeRecord = new PledgeRecord();
		pledgeRecord.setDelegator(supervisionCustomer.getDelegator());
		pledgeRecord.setSupervisionCustomer(supervisionCustomer);
//		pledgeRecord.setCode(warehouse.getPledgeRecordCode());
		pledgeRecord.setRecordName(CodeUtils.getPledgeRecordCode(supervisionCustomer.getCode()));
		pledgeRecord.setWarehouse(warehouse);
		double sumWeight = 0;
		for (Stock stock : stocks) {
			PledgeRecordDetail pledgeRecordDetail = new PledgeRecordDetail();
			pledgeRecordDetail.setPledgeRecord(pledgeRecord);
			pledgeRecordDetail.setStyle(stock.getStyle());
			pledgeRecordDetail.setPledgePurity(stock.getPledgePurity());
			pledgeRecordDetail.setSumWeight(stock.getSumWeight());
			double weight = stock.getSumWeight();
			sumWeight += weight;
			pledgeRecordDetail.setStorage(warehouse.getAddress());
			
			//TODO
			//各种占比计算？
			
			pledgeRecordDetails.add(pledgeRecordDetail);
		}
		pledgeRecord.setSumWeight(sumWeight);
		PledgeRecordDao.save(pledgeRecord);
		pledgeRecordDetailDao.save(pledgeRecordDetails);
	}

}
