package com.pms.app.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pms.app.dao.InsRecordDao;
import com.pms.app.dao.InsRecordDetailDao;
import com.pms.app.entity.InsRecord;
import com.pms.app.entity.InsRecordDetail;
import com.pms.app.entity.Stock;
import com.pms.app.util.CodeUtils;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
@Transactional
public class InsRecordService extends BaseService<InsRecord, String> {

	@Autowired private InsRecordDao insRecordDao;
	@Autowired private InsRecordDetailDao insRecordDetailDao;
	@Autowired private StockService stockService;

	@Override
	protected BaseDao<InsRecord, String> getEntityDao() {
		return insRecordDao;
	}

	
	@Transactional
	public void save(InsRecord insRecord) {
		insRecord.setCode(CodeUtils.getInsRecordCode());
		Map<String, Stock> stockMap = stockService.findStockMapByWarehouseId(insRecord.getWarehouse().getId());
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
		insRecord.setSumWeight(new BigDecimal(sumWeight).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		insRecordDao.save(insRecord);
		insRecordDetailDao.save(insRecordDetailList);
		stockService.save(stockMap);
	}
	
	

}
