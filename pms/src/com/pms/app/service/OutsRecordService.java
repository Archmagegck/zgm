package com.pms.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pms.app.dao.OutsRecordDao;
import com.pms.app.dao.OutsRecordDetailDao;
import com.pms.app.dao.PledgeConfigDao;
import com.pms.app.dao.PurityPriceDao;
import com.pms.app.dao.SupervisionCustomerDao;
import com.pms.app.entity.Delegator;
import com.pms.app.entity.OutsRecord;
import com.pms.app.entity.OutsRecordDetail;
import com.pms.app.entity.PledgeConfig;
import com.pms.app.entity.PurityPrice;
import com.pms.app.entity.Stock;
import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.Supervisor;
import com.pms.app.entity.Warehouse;
import com.pms.app.entity.reference.AuditState;
import com.pms.app.entity.vo.OutStock;
import com.pms.app.util.CodeUtils;
import com.pms.app.util.Digests;
import com.pms.app.util.Encodes;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class OutsRecordService extends BaseService<OutsRecord, String> {

	@Autowired private OutsRecordDao outsRecordDao;
	@Autowired private OutsRecordDetailDao outsRecordDetailDao;
	@Autowired private SupervisionCustomerDao supervisionCustomerDao;
	@Autowired private StockService stockService;
	@Autowired private PledgeConfigDao pledgeConfigDao;
	@Autowired private PurityPriceDao purityPriceDao;


	@Override
	protected BaseDao<OutsRecord, String> getEntityDao() {
		return outsRecordDao;
	}

	public Page<OutsRecord> findWaitOutsRecord(Pageable pageable) {
		return outsRecordDao.findPageByAuditStateNot(pageable, AuditState.Pass);
	}
	
	/**
	 * 监管经理助理待审核出库单查询
	 * @param pageable
	 * @return
	 */
	public List<OutsRecord> findWaitOutsRecordForMA( ){
		List<OutsRecord> outRecordsWait=new ArrayList<OutsRecord>();
		PurityPrice purityPrice = purityPriceDao.findNewestList().get(0);
		List<OutsRecord> outsRecords=outsRecordDao.findByAuditStateNot(AuditState.Pass);		
		for(OutsRecord outsRecord:outsRecords){
			PledgeConfig pledgeConfig=pledgeConfigDao.findBySupervisionCustomerId(outsRecord.getSupervisionCustomer().getId()).get(0);
			double minValuePercent=outsRecord.getSumStock()*purityPrice.getPrice()/pledgeConfig.getMinValue()*100;
			double minWeightPercent=outsRecord.getSumStock()/pledgeConfig.getMinWeight()*100;
			double min;
			if(minValuePercent<=minWeightPercent)
				min = minValuePercent;
			else
				min = minWeightPercent;
			if(min>=pledgeConfig.getMinCordon()){				
				outRecordsWait.add(outsRecord);
			}	
		}
		return outRecordsWait;
	}
	
	public Page<OutsRecord> findNoticeOutsRecord(Pageable pageable) {
		return outsRecordDao.findPageByNotice(pageable, 1);
	}

	/**
	 * 出库
	 */
	@Transactional
	public String saveDetails(List<OutStock> outStocks, Warehouse warehouse, Supervisor supervisor, String desc, String attachPath) {
		String message = "";
		Date now = new Date();
		SupervisionCustomer supervisionCustomer = supervisionCustomerDao.findListByWarehouseId(warehouse.getId()).get(0);
		Delegator delegator = supervisionCustomer.getDelegator();
		
		List<Stock> updateStocks = new ArrayList<Stock>();
		List<Stock> delStocks = new ArrayList<Stock>();
		List<OutsRecordDetail> saveOutsRecordDetails = new ArrayList<OutsRecordDetail>();
		
		Map<String, Stock> stockMap = stockService.findStockMapByWarehouseId(warehouse.getId());
		
		OutsRecord outsRecord = new OutsRecord();
		outsRecord.setDate(now);
		String code = CodeUtils.getOutsRecordCode(warehouse.getId());
		outsRecord.setCode(code);
		outsRecord.setSecretCode(Encodes.encodeHex(Digests.md5(code.getBytes())));
		outsRecord.setWarehouse(warehouse);
		outsRecord.setDelegator(delegator);
		outsRecord.setSupervisionCustomer(supervisionCustomer);
		outsRecord.setSupName(supervisor.getName());
		outsRecord.setAttach(attachPath);
		outsRecord.setDesc(desc);
		
		Map<String, Stock> stockMapVo = new HashMap<String, Stock>();
		for (Stock stock : stockMap.values()) {
			Stock stockVO = new Stock();
			stock.copyTo(stockVO);
			stockMapVo.put(stockVO.getId(), stockVO);
		}

		
		double outSumWeight = 0.0;
		for (OutStock outStock : outStocks) {
			Double outWeight = outStock.getSumWeight();
			if (outWeight == null)
				continue;
			outSumWeight += outWeight;
			
			Stock stock = stockMapVo.get(outStock.getStockId());
			double remainWeight = stock.getSumWeight() - outWeight;
			if (remainWeight == 0)
				delStocks.add(stock);
			if (remainWeight > 0) {
				stock.setSumWeight(remainWeight);
				updateStocks.add(stock);
			}
			
			OutsRecordDetail outsRecordDetail = new OutsRecordDetail();
			outsRecordDetail.setOutsRecord(outsRecord);
			outsRecordDetail.setDelegator(supervisionCustomer.getDelegator());
			outsRecordDetail.setSupervisionCustomer(supervisionCustomer);
			outsRecordDetail.setStyle(stock.getStyle());
			outsRecordDetail.setPledgePurity(stock.getPledgePurity());
			outsRecordDetail.setWeight(outWeight);
			outsRecordDetail.setDate(now);
			saveOutsRecordDetails.add(outsRecordDetail);
			
		}
		outsRecord.setSumWeight(outSumWeight);
		outsRecord.setOutsRecordDetails(saveOutsRecordDetails);
		
		double stockSumWeight = 0;//出库后库存重量
		for (Stock stock : stockMapVo.values()) {
			if(stock.getPledgePurity().getType() == 1)
				stockSumWeight += stock.getSumWeight();
		}
		
		//出库逻辑
		PledgeConfig config = pledgeConfigDao.findBySupervisionCustomerId(supervisionCustomer.getId()).get(0);
		
		double price = 0.0;
		List<PurityPrice> purityPriceList = purityPriceDao.findNewestList();
		if(!purityPriceList.isEmpty()) {
			price = purityPriceList.get(0).getPrice();
		}
		
		double supOutWeight = config.getOutWeight();//监管员权限
		double maxcordon = config.getMaxCordon();// 警戒线上限（%）
		double stockValue = stockSumWeight * price;//出库后仓库库存价值
		double warnValue = (config.getMinValue() * maxcordon) / 100;//质押物最低价值的警戒线
		double warnWeight = (config.getMinWeight() * maxcordon) / 100;//质押物最低重量的警戒线
		
		outsRecord.setSumStock(stockSumWeight);
		outsRecord.setSumValue(stockValue);
		double weightRate = new BigDecimal(stockSumWeight / config.getMinWeight()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		outsRecord.setWeightRate(weightRate);
		double priceRate = new BigDecimal(stockValue / config.getMinValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		outsRecord.setPriceRate(priceRate);
		
		boolean ifOutStock = true;
		int warnType = 0;
		
		//判断1：出库重量是否超过监管员权限
		//判断2：出库后仓库库存是否低于质押物最低价值的警戒线(115%)
		//判断3：是否低于最低重量的警戒线（115%）
		if(outSumWeight > supOutWeight) {
			warnType += 4;
			ifOutStock = false;
			message += "出库重量超过监管员权限.";
		}
		if(stockValue < warnValue) {
			warnType += 2;
			ifOutStock = false;
			message += "出库后仓库库存低于质押物最低价值的警戒线.";
		}
		if(stockSumWeight < warnWeight) {
			warnType += 1;
			ifOutStock = false;
			message += "出库后仓库重量低于最低重量的警戒线.";
		}
		outsRecord.setNotice(warnType);
		
		if(ifOutStock) { //可以出库
			outsRecord.setAuditState(AuditState.Pass);
			stockService.save(updateStocks);
			stockService.delete(delStocks); 
			message = "出库成功!";
		} else {
			message += " 请等待监管经理审核!";
		}
		
		outsRecordDao.save(outsRecord);
		outsRecordDetailDao.save(saveOutsRecordDetails);
		
		return message;
	}

	
	public void audit(OutsRecord outsRecord, Integer state) {
		switch (state) {
		case 1:
			outsRecord.setAuditState(AuditState.Pass);
			List<Stock> updateStocks = new ArrayList<Stock>();
			List<Stock> delStocks = new ArrayList<Stock>();
			Map<String, Stock> stockMap = stockService.findStockKeyMapByWarehouseId(outsRecord.getWarehouse().getId());
			List<OutsRecordDetail> outsRecordDetails = outsRecord.getOutsRecordDetails();
			for (OutsRecordDetail outsRecordDetail : outsRecordDetails) {
				String key = outsRecordDetail.getKey();
				Stock stock = stockMap.get(key);
				double outWeight = outsRecordDetail.getWeight();
				double remainWeight = stock.getSumWeight() - outWeight;
				if (remainWeight == 0)
					delStocks.add(stock);
				if (remainWeight > 0) {
					stock.setSumWeight(remainWeight);
					updateStocks.add(stock);
				}
			}

			stockService.save(updateStocks);
			stockService.delete(delStocks);
			break;
		case 0:
			outsRecord.setAuditState(AuditState.Refuse);
			break;
		}
		outsRecordDao.save(outsRecord);
	}

	

}
