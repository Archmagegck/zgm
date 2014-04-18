package com.pms.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pms.app.dao.InsRecordDao;
import com.pms.app.dao.InsRecordDetailDao;
import com.pms.app.dao.SupervisionCustomerDao;
import com.pms.app.entity.Delegator;
import com.pms.app.entity.InsRecord;
import com.pms.app.entity.InsRecordDetail;
import com.pms.app.entity.Stock;
import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.Supervisor;
import com.pms.app.entity.Warehouse;
import com.pms.app.util.CodeUtils;
import com.pms.app.util.Digests;
import com.pms.app.util.Encodes;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
@Transactional
public class InsRecordService extends BaseService<InsRecord, String> {

	@Autowired private InsRecordDao insRecordDao;
	@Autowired private InsRecordDetailDao insRecordDetailDao;
	@Autowired private SupervisionCustomerDao supervisionCustomerDao;
	@Autowired private StockService stockService;
	

	@Override
	protected BaseDao<InsRecord, String> getEntityDao() {
		return insRecordDao;
	}

	
	@Transactional
	public void saveDetails(List<InsRecordDetail> insRecordDetailList, Warehouse warehouse, Supervisor supervisor, String attachPath) {
		Date now = new Date();
		SupervisionCustomer supervisionCustomer = supervisionCustomerDao.findListByWarehouseId(warehouse.getId()).get(0);
		Delegator delegator = supervisionCustomer.getDelegator();
		Map<String, Stock> stockMap = stockService.findStockKeyMapByWarehouseId(warehouse.getId());
		List<Stock> updateStocks = new ArrayList<Stock>();
		
		InsRecord insRecord = new InsRecord();
		insRecord.setDate(now);
		String code = CodeUtils.getInsRecordCode(warehouse.getId());
		insRecord.setCode(code);
		insRecord.setSecretCode(Encodes.encodeHex(Digests.md5(code.getBytes())));
		insRecord.setWarehouse(warehouse);
		insRecord.setSupervisor(supervisor);
		insRecord.setAttach(attachPath);
		
		double sumWeight = 0;
		for (InsRecordDetail insRecordDetail : insRecordDetailList) {
			insRecordDetail.setInsRecord(insRecord);
			insRecordDetail.setDelegator(delegator);
			insRecordDetail.setSupervisionCustomer(supervisionCustomer);
			insRecordDetail.setDate(now);
			
			Stock stock = stockMap.get(insRecordDetail.getKey());
			if(stock != null) {
				stock.add(insRecordDetail.getWeight());
				updateStocks.add(stock);
			}
			sumWeight += insRecordDetail.getWeight();
		}
		
		insRecord.setSumWeight(sumWeight);
		insRecord.setInsRecordDetails(insRecordDetailList);
		
		insRecordDao.save(insRecord);
		insRecordDetailDao.save(insRecordDetailList);
		stockService.save(updateStocks);
		
	}


}
