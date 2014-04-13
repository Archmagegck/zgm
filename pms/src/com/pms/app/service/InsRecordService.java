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
import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.Warehouse;
import com.pms.app.util.CodeUtils;
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
	public void save(InsRecord insRecord, SupervisionCustomer supervisionCustomer) {
		insRecord.setCode(CodeUtils.getInsRecordCode(supervisionCustomer.getCode()));
		Map<String, Stock> stockMap = stockService.findNoTranStockKeyMapByWarehouseId(insRecord.getWarehouse().getId());
		
//		String pledgeRecordCode = insRecord.getWarehouse().getPledgeRecordCode();
		if(StringUtils.hasText("")) {
			if(stockMap.isEmpty()){
//				pledgeRecordCode = String.valueOf(new IdWorker(1, 2, 3).getId());
				Warehouse warehouse = warehouseDao.findOne(insRecord.getWarehouse().getId());
//				warehouse.setPledgeRecordCode(pledgeRecordCode);
				warehouseDao.save(warehouse);
				insRecord.setWarehouse(warehouse);
			}
		} else {
//			pledgeRecordCode = String.valueOf(new IdWorker(1, 2, 3).getId());
			Warehouse warehouse = warehouseDao.findOne(insRecord.getWarehouse().getId());
//			warehouse.setPledgeRecordCode(pledgeRecordCode);
			warehouseDao.save(warehouse);
			insRecord.setWarehouse(warehouse);
		}
		
		double sumWeight = 0;
		List<InsRecordDetail> insRecordDetailList = insRecord.getInsRecordDetails();
		for(InsRecordDetail detail : insRecordDetailList) {
			detail.setInsRecord(insRecord);
			detail.setDelegator(supervisionCustomer.getDelegator());
			detail.setSupervisionCustomer(supervisionCustomer);
			
			detail.setWeight(detail.getWeight());
			sumWeight += detail.getWeight();
			
			String key = detail.getKey();
			Stock stock = stockMap.get(key);
			if(stock == null) {
				stock = new Stock(insRecord.getWarehouse(), detail);
				stockMap.put(key, stock);
			} else {
				stock.add(detail);
			}
		}
		sumWeight = new BigDecimal(sumWeight).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		PledgeRecord pledgeRecord = new PledgeRecord();
		pledgeRecord.setDelegator(supervisionCustomer.getDelegator());
		pledgeRecord.setSupervisionCustomer(supervisionCustomer);
//		pledgeRecord.setCode(pledgeRecordCode);
		pledgeRecord.setRecordName(CodeUtils.getPledgeRecordCode(supervisionCustomer.getCode()));
		pledgeRecord.setWarehouse(insRecord.getWarehouse());
		
		double stockSumWeight = 0;
		List<PledgeRecordDetail> pledgeRecordDetails = new ArrayList<PledgeRecordDetail>();
		for (Stock stock : stockMap.values()) {
			PledgeRecordDetail detail = new PledgeRecordDetail(stock, sumWeight);
			detail.setPledgeRecord(pledgeRecord);
			stockSumWeight += detail.getSumWeight();
			pledgeRecordDetails.add(detail);
		}
		Map<String, Stock> tranStockMap = stockService.findTranStockKeyMapByWarehouseId(insRecord.getWarehouse().getId());
		for (Stock stock : tranStockMap.values()) {
			PledgeRecordDetail detail = new PledgeRecordDetail(stock, sumWeight);
			detail.setPledgeRecord(pledgeRecord);
			stockSumWeight += detail.getSumWeight();
			pledgeRecordDetails.add(detail);
		}
		
		stockSumWeight = new BigDecimal(stockSumWeight).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		pledgeRecord.setSumWeight(stockSumWeight);
		pledgeRecordDao.save(pledgeRecord);
		pledgeRecordDetailDao.save(pledgeRecordDetails);
		
		insRecord.setSumWeight(sumWeight);
		insRecordDao.save(insRecord);
		insRecordDetailDao.save(insRecordDetailList);
		stockService.save(stockMap);
		
	}
	
//	@Transactional
//	public void transitGoodsSave(InsRecord insRecord, SupervisionCustomer supervisionCustomer) {
//		//保存入库单及入库单明细，同时修改stock中的存储地点
//		
//		
//	}

}
