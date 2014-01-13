package com.pms.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pms.app.dao.OutsRecordDao;
import com.pms.app.dao.OutsRecordDetailDao;
import com.pms.app.dao.PledgeRecordDao;
import com.pms.app.dao.PledgeRecordDetailDao;
import com.pms.app.entity.OutsRecord;
import com.pms.app.entity.OutsRecordDetail;
import com.pms.app.entity.PledgeRecord;
import com.pms.app.entity.PledgeRecordDetail;
import com.pms.app.entity.Stock;
import com.pms.app.entity.reference.PickType;
import com.pms.app.entity.vo.OutStock;
import com.pms.app.util.CodeUtils;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class OutsRecordService extends BaseService<OutsRecord, String> {

	@Autowired private OutsRecordDao outsRecordDao;
	@Autowired private OutsRecordDetailDao outsRecordDetailDao;
	@Autowired private PledgeRecordDao pledgeRecordDao;
	@Autowired private PledgeRecordDetailDao pledgeRecordDetailDao;
	@Autowired private StockService stockService;

	@Override
	protected BaseDao<OutsRecord, String> getEntityDao() {
		return outsRecordDao;
	}
	
	
	@Transactional
	public void save(OutsRecord outsRecord, List<OutStock> outStocks, int hasPickFile, String supervisionCustomerCode) {
		double sumWeight = 0;
		Map<String, Stock> stockMap = stockService.findStockMapByWarehouseId(outsRecord.getWarehouse().getId());
		List<Stock> updateStocks = new ArrayList<Stock>();
		List<Stock> delStocks = new ArrayList<Stock>();
		List<OutsRecordDetail> saveOutsRecordDetails = new ArrayList<OutsRecordDetail>();
		
		for (OutStock outStock : outStocks) {
			Double outAmount = outStock.getOutAmount();
			if(outAmount == null) continue;
			sumWeight += outAmount;
			Stock stock = stockMap.get(outStock.getStockId());
			double remainAmount = stock.getAmount() - outAmount;
			if(remainAmount == 0) 
				delStocks.add(stock);
			if(remainAmount > 0) {
				double weight = new BigDecimal(stock.getSpecWeight() * remainAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				stock.setAmount(remainAmount);
				stock.setSumWeight(weight);
				updateStocks.add(stock);
			}
			OutsRecordDetail outsRecordDetail = new OutsRecordDetail();
			outsRecordDetail.setAmount(outStock.getOutAmount());
			outsRecordDetail.setCompany(stock.getCompany());
			outsRecordDetail.setDesc(stock.getCompany());
			outsRecordDetail.setOutsRecord(outsRecord);
			outsRecordDetail.setPledgePurity(stock.getPledgePurity());
			outsRecordDetail.setSpecWeight(stock.getSpecWeight());
			outsRecordDetail.setStyle(stock.getStyle());
			outsRecordDetail.setSumWeight(new BigDecimal(outsRecordDetail.getSpecWeight() * outsRecordDetail.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
			outsRecordDetail.setRemainWeight(remainAmount);
			saveOutsRecordDetails.add(outsRecordDetail);
		}
		outsRecord.setSumWeight(sumWeight);
		
		if(delStocks.size() == stockMap.size()) {//全部出库
			outsRecord.setPickType(PickType.All);
		}
		
		PledgeRecord pledgeRecord = new PledgeRecord();
		pledgeRecord.setCode(outsRecord.getWarehouse().getPledgeRecordCode());
		pledgeRecord.setRecordName(CodeUtils.getPledgeRecordCode(supervisionCustomerCode));
		pledgeRecord.setWarehouse(outsRecord.getWarehouse());
		List<PledgeRecordDetail> pledgeRecordDetails = new ArrayList<PledgeRecordDetail>();
		for (Stock stock : stockMap.values()) {
			PledgeRecordDetail detail = new PledgeRecordDetail(stock, sumWeight);
			detail.setPledgeRecord(pledgeRecord);
			pledgeRecordDetails.add(detail);
		}
		pledgeRecordDao.save(pledgeRecord);
		pledgeRecordDetailDao.save(pledgeRecordDetails);
		
		outsRecord.setPledgeRecord(pledgeRecord);
		stockService.save(updateStocks);
		stockService.delete(delStocks);
		outsRecordDao.save(outsRecord);
		outsRecordDetailDao.save(saveOutsRecordDetails);
		
		
	}
	
	
	

}
