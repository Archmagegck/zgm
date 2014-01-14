package com.pms.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.pms.app.dao.InsRecordDao;
import com.pms.app.dao.InsRecordDetailDao;
import com.pms.app.dao.PledgeRecordDao;
import com.pms.app.dao.PledgeRecordDetailDao;
import com.pms.app.dao.WarehouseDao;
import com.pms.app.entity.InsRecord;
import com.pms.app.entity.InsRecordDetail;
import com.pms.app.entity.PledgeRecord;
import com.pms.app.entity.PledgeRecordDetail;
import com.pms.app.entity.Stock;
import com.pms.app.entity.Warehouse;
import com.pms.app.util.CodeUtils;
import com.pms.app.util.IdWorker;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
@Transactional
public class InsRecordService extends BaseService<InsRecord, String> {

	@Autowired private InsRecordDao insRecordDao;
	@Autowired private InsRecordDetailDao insRecordDetailDao;
	@Autowired private PledgeRecordDao pledgeRecordDao;
	@Autowired private PledgeRecordDetailDao pledgeRecordDetailDao;
	@Autowired private WarehouseDao warehouseDao;
	@Autowired private StockService stockService;
	

	@Override
	protected BaseDao<InsRecord, String> getEntityDao() {
		return insRecordDao;
	}

	
	@Transactional
	public void save(InsRecord insRecord, String supervisionCustomerCode) {
		insRecord.setCode(CodeUtils.getInsRecordCode(supervisionCustomerCode));
		Map<String, Stock> stockMap = stockService.findStockKeyMapByWarehouseId(insRecord.getWarehouse().getId());
		
		String pledgeRecordCode = insRecord.getWarehouse().getPledgeRecordCode();
		if(StringUtils.hasText(pledgeRecordCode)) {
			if(stockMap.isEmpty()){
				pledgeRecordCode = String.valueOf(new IdWorker(1, 2, 3).getId());
				Warehouse warehouse = warehouseDao.findOne(insRecord.getWarehouse().getId());
				warehouse.setPledgeRecordCode(pledgeRecordCode);
				warehouseDao.save(warehouse);
				insRecord.setWarehouse(warehouse);
			}
		} else {
			pledgeRecordCode = String.valueOf(new IdWorker(1, 2, 3).getId());
			Warehouse warehouse = warehouseDao.findOne(insRecord.getWarehouse().getId());
			warehouse.setPledgeRecordCode(pledgeRecordCode);
			warehouseDao.save(warehouse);
			insRecord.setWarehouse(warehouse);
		}
		
		double sumWeight = 0;
		List<InsRecordDetail> insRecordDetailList = insRecord.getInsRecordDetails();
		for(InsRecordDetail detail : insRecordDetailList) {
			detail.setInsRecord(insRecord);
			
			double detailSumWeight = new BigDecimal(detail.getAmount() * detail.getSpecWeight()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			detail.setSumWeight(detailSumWeight);
			sumWeight += detailSumWeight;
			
			String key = detail.getKey();
			Stock stock = stockMap.get(key);
			if(stock == null) {
				stock = new Stock(insRecord.getWarehouse(), insRecord.getClosedTran(), detail);
				stockMap.put(key, stock);
			} else {
				stock.add(detail);
			}
		}
		sumWeight = new BigDecimal(sumWeight).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		PledgeRecord pledgeRecord = new PledgeRecord();
		pledgeRecord.setCode(pledgeRecordCode);
		pledgeRecord.setRecordName(CodeUtils.getPledgeRecordCode(supervisionCustomerCode));
		pledgeRecord.setWarehouse(insRecord.getWarehouse());
		double stockSumWeight = 0;
		List<PledgeRecordDetail> pledgeRecordDetails = new ArrayList<PledgeRecordDetail>();
		for (Stock stock : stockMap.values()) {
			PledgeRecordDetail detail = new PledgeRecordDetail(stock, sumWeight);
			detail.setPledgeRecord(pledgeRecord);
			stockSumWeight += detail.getSumWeight();
			pledgeRecordDetails.add(detail);
		}
		stockSumWeight = new BigDecimal(stockSumWeight).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		pledgeRecord.setSumWeight(stockSumWeight);
		pledgeRecordDao.save(pledgeRecord);
		pledgeRecordDetailDao.save(pledgeRecordDetails);
		
		insRecord.setPledgeRecord(pledgeRecord);
		insRecord.setSumWeight(sumWeight);
		insRecordDao.save(insRecord);
		insRecordDetailDao.save(insRecordDetailList);
		stockService.save(stockMap);
		
	}
	
	

}
