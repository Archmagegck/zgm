package com.pms.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pms.app.dao.Au9995PriceDao;
import com.pms.app.dao.OutsRecordDao;
import com.pms.app.dao.OutsRecordDetailDao;
import com.pms.app.dao.PledgeConfigDao;
import com.pms.app.dao.PledgeRecordDao;
import com.pms.app.dao.PledgeRecordDetailDao;
import com.pms.app.entity.Au9995Price;
import com.pms.app.entity.OutsRecord;
import com.pms.app.entity.OutsRecordDetail;
import com.pms.app.entity.PledgeConfig;
import com.pms.app.entity.PledgeRecord;
import com.pms.app.entity.PledgeRecordDetail;
import com.pms.app.entity.Stock;
import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.reference.AuditState;
import com.pms.app.entity.reference.PickType;
import com.pms.app.entity.vo.OutStock;
import com.pms.app.util.CodeUtils;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;
import com.pms.base.service.ServiceException;

@Service
public class OutsRecordService extends BaseService<OutsRecord, String> {

	@Autowired private OutsRecordDao outsRecordDao;
	@Autowired private OutsRecordDetailDao outsRecordDetailDao;
	@Autowired private PledgeRecordDao pledgeRecordDao;
	@Autowired private PledgeRecordDetailDao pledgeRecordDetailDao;
	@Autowired private PledgeConfigDao pledgeConfigDao;
	@Autowired private Au9995PriceDao au9995PriceDao;
	@Autowired private StockService stockService;

	@Override
	protected BaseDao<OutsRecord, String> getEntityDao() {
		return outsRecordDao;
	}
	
	public Page<OutsRecord> findWaitOutsRecord(Pageable pageable) {
		return outsRecordDao.findPageByAuditState(pageable, AuditState.Wait);
	}
	
	
	@Transactional
	public void save(OutsRecord outsRecord, List<OutStock> outStocks, int hasPickFile, SupervisionCustomer supervisionCustomer) throws ServiceException {
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
			outsRecordDetail.setDelegator(supervisionCustomer.getDelegator());
			outsRecordDetail.setSupervisionCustomer(supervisionCustomer);
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
		outsRecord.setDelegator(supervisionCustomer.getDelegator());
		outsRecord.setSupervisionCustomer(supervisionCustomer);
		
		if(delStocks.size() == stockMap.size()) {//全部出库
			outsRecord.setPickType(PickType.All);
		}
		
		PledgeRecord pledgeRecord = new PledgeRecord();
		pledgeRecord.setDelegator(supervisionCustomer.getDelegator());
		pledgeRecord.setSupervisionCustomer(supervisionCustomer);
		pledgeRecord.setCode(outsRecord.getWarehouse().getPledgeRecordCode());
		pledgeRecord.setRecordName(CodeUtils.getPledgeRecordCode(supervisionCustomer.getCode()));
		pledgeRecord.setWarehouse(outsRecord.getWarehouse());
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
		
		//出库逻辑
		double value9995 = 0;
		PledgeConfig config = pledgeConfigDao.findBySupervisionCustomerId(supervisionCustomer.getId()).get(0);
		List<Au9995Price> au9995PriceList = au9995PriceDao.findAll(new PageRequest(0, 1, new Sort(Direction.DESC, "date"))).getContent();
		if(!au9995PriceList.isEmpty()) {
			value9995 = au9995PriceList.get(0).getPrice();
		}
		double shippingWeight = config.getSupervisor().getShippingWeight();//监管员出库重量
		double minWeight = config.getMinWeight();//最低重量
		double minValue = config.getMinValue();//质押物的最低价值要求
		double mincordon = config.getMinCordon();//警戒线下限（%）
		double maxcordon = config.getMaxCordon();//警戒线上限（%）
		double stockSumValue = new BigDecimal(stockSumWeight * value9995).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//出库后质押物价值
		if(hasPickFile == 1) {
			if(sumWeight > shippingWeight || stockSumWeight < minWeight || stockSumValue < ((minValue * mincordon) / 100)) {
				outsRecord.setAuditState(AuditState.Wait);
			} else {
				outsRecord.setAuditState(AuditState.Pass);
			}
		} else {
			if(stockSumValue < ((minValue * mincordon) / 100) || stockSumWeight < minWeight) {
				throw new ServiceException("出库超出权限,无法出库!");
			}
			if(sumWeight > shippingWeight || (stockSumValue > ((minValue * mincordon) / 100) && stockSumValue < ((minValue * maxcordon) / 100))) {
				outsRecord.setAuditState(AuditState.Wait);
			} else {
				outsRecord.setAuditState(AuditState.Pass);
			}
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
