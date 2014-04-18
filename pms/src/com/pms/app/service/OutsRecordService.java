package com.pms.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.pms.app.dao.PledgeRecordDao;
import com.pms.app.dao.PledgeRecordDetailDao;
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

	@Autowired
	private OutsRecordDao outsRecordDao;
	@Autowired
	private OutsRecordDetailDao outsRecordDetailDao;
	@Autowired
	private PledgeRecordDao pledgeRecordDao;
	@Autowired
	private PledgeRecordDetailDao pledgeRecordDetailDao;
	@Autowired
	private PledgeConfigDao pledgeConfigDao;
	@Autowired
	private StockService stockService;

	@Override
	protected BaseDao<OutsRecord, String> getEntityDao() {
		return outsRecordDao;
	}

	public Page<OutsRecord> findWaitOutsRecord(Pageable pageable) {
		return outsRecordDao.findPageByAuditState(pageable, AuditState.Wait);
	}
	
	public Page<OutsRecord> findNoticeOutsRecord(Pageable pageable) {
		return outsRecordDao.findPageByNotice(pageable, 1);
	}

	@Transactional
	public String save(OutsRecord outsRecord, List<OutStock> outStocks, int hasPickFile, SupervisionCustomer supervisionCustomer) throws ServiceException {
		String message = "出库成功!";
		double sumWeight = 0;
//		Map<String, Stock> stockMap = stockService.findStockMapByWarehouseId(outsRecord.getWarehouse().getId());
//		List<Stock> updateStocks = new ArrayList<Stock>();
//		List<Stock> delStocks = new ArrayList<Stock>();
//		List<OutsRecordDetail> saveOutsRecordDetails = new ArrayList<OutsRecordDetail>();

		for (OutStock outStock : outStocks) {
//			Double outAmount = outStock.getOutAmount();
//			if (outAmount == null)
//				continue;
//			Stock stock = stockMap.get(outStock.getStockId());
//			sumWeight += new BigDecimal(stock.getSpecWeight() * outAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//			double remainAmount = stock.getAmount() - outAmount;
//			if (remainAmount == 0)
//				delStocks.add(stock);
//			if (remainAmount > 0) {
//				double weight = new BigDecimal(stock.getSpecWeight() * remainAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//				stock.setAmount(remainAmount);
//				stock.setSumWeight(weight);
//				updateStocks.add(stock);
//			}
//			OutsRecordDetail outsRecordDetail = new OutsRecordDetail();
//			outsRecordDetail.setDelegator(supervisionCustomer.getDelegator());
//			outsRecordDetail.setSupervisionCustomer(supervisionCustomer);
//			outsRecordDetail.setAmount(outStock.getOutAmount());
//			outsRecordDetail.setCompany(stock.getCompany());
//			outsRecordDetail.setDesc(stock.getDesc());
//			outsRecordDetail.setOutsRecord(outsRecord);
//			outsRecordDetail.setPledgePurity(stock.getPledgePurity());
//			outsRecordDetail.setSpecWeight(stock.getSpecWeight());
//			outsRecordDetail.setStyle(stock.getStyle());
//			outsRecordDetail.setSumWeight(new BigDecimal(outsRecordDetail.getSpecWeight() * outsRecordDetail.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//			outsRecordDetail.setRemainWeight(new BigDecimal(stock.getSpecWeight() * remainAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//			saveOutsRecordDetails.add(outsRecordDetail);
		}
		outsRecord.setWeight(sumWeight);
		outsRecord.setDelegator(supervisionCustomer.getDelegator());
		outsRecord.setSupervisionCustomer(supervisionCustomer);


		PledgeRecord pledgeRecord = new PledgeRecord();
		pledgeRecord.setDelegator(supervisionCustomer.getDelegator());
		pledgeRecord.setSupervisionCustomer(supervisionCustomer);
//		pledgeRecord.setCode(outsRecord.getWarehouse().getPledgeRecordCode());
		pledgeRecord.setRecordName(CodeUtils.getPledgeRecordCode(supervisionCustomer.getCode()));
		pledgeRecord.setWarehouse(outsRecord.getWarehouse());
		double stockSumWeight = 0;
//		List<PledgeRecordDetail> pledgeRecordDetails = new ArrayList<PledgeRecordDetail>();
//		for (Stock stock : stockMap.values()) {
//			PledgeRecordDetail detail = new PledgeRecordDetail(stock, sumWeight);
//			detail.setPledgeRecord(pledgeRecord);
//			stockSumWeight += detail.getSumWeight();
//			pledgeRecordDetails.add(detail);
//		}
		stockSumWeight = new BigDecimal(stockSumWeight).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		pledgeRecord.setSumWeight(stockSumWeight);

		// 出库逻辑
		double value9995 = 0;
		PledgeConfig config = pledgeConfigDao.findBySupervisionCustomerId(supervisionCustomer.getId()).get(0);
//		List<Au9995Price> au9995PriceList = au9995PriceDao.findAll(new PageRequest(0, 1, new Sort(Direction.DESC, "date"))).getContent();
//		if (!au9995PriceList.isEmpty()) {
//			value9995 = au9995PriceList.get(0).getPrice();
//		}
		double shippingWeight = 0;// 监管员出库重量
		double minWeight = config.getMinWeight();// 最低重量
		double minValue = config.getMinValue();// 质押物的最低价值要求
		double mincordon = config.getMinCordon();// 警戒线下限（%）
		double maxcordon = config.getMaxCordon();// 警戒线上限（%）
		double stockSumValue = new BigDecimal(stockSumWeight * value9995).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();// 出库后质押物价值
		if (hasPickFile == 1) {
			// 如果出库重量超过监管员出库重量条件
			// 出库后质押物价值低于设定最低*警戒值下线
			// 可以出库或者出库后质押物重量小于最低重量
			// 系统需给监管经理发送通知
			if (sumWeight > shippingWeight || stockSumWeight < minWeight || stockSumValue < ((minValue * mincordon) / 100)) {
				outsRecord.setNotice(1);
			}
			outsRecord.setAuditState(AuditState.Pass);// 发送通知后，直接通过
		} else {

			// 如果出库后质押物价值低于设定的最低价值*警戒值下线,或者质押物重量低于设定的最低重量则出库失败
			if (stockSumValue < ((minValue * mincordon) / 100) || stockSumWeight < minWeight) {
				throw new ServiceException("出库超出权限,无法出库!");
			}

			// 出库重量超过该监管员的出货权限重量
			// 或出库后仓库的实际质押物价值大于设定最低价值*警戒值下线,且低于最低价值*警戒值上限）
			// 需审核
			if (sumWeight > shippingWeight || (stockSumValue > ((minValue * mincordon) / 100) && stockSumValue < ((minValue * maxcordon) / 100))) {
				outsRecord.setAuditState(AuditState.Wait);// 出库待定
				message = "请等待监管经理审核!";
			} else {
				outsRecord.setAuditState(AuditState.Pass);
			}
		}

//		if (outsRecord.getAuditState() == AuditState.Pass) { // 如果是通过，更改库存，生成质物清单，不通过则不更改库存
//			pledgeRecordDao.save(pledgeRecord);
//			pledgeRecordDetailDao.save(pledgeRecordDetails);
//
////			outsRecord.setPledgeRecord(pledgeRecord);
//			stockService.save(updateStocks);
//			stockService.delete(delStocks);
//		}
//
//		outsRecordDao.save(outsRecord);
//		outsRecordDetailDao.save(saveOutsRecordDetails);

		return message;
	}

	public void audit(OutsRecord outsRecord, Integer state) {
		switch (state) {
		case 1:
			outsRecord.setAuditState(AuditState.Pass);
			List<Stock> updateStocks = new ArrayList<Stock>();
			List<Stock> delStocks = new ArrayList<Stock>();
//			Map<String, Stock> stockMap = stockService.findOutStockKeyMapByWarehouseId(outsRecord.getWarehouse().getId());
			List<OutsRecordDetail> outsRecordDetails = outsRecord.getOutsRecordDetails();
			for (OutsRecordDetail outsRecordDetail : outsRecordDetails) {
				String key = outsRecordDetail.getKey();
//				Stock stock = stockMap.get(key);
//				double outAmount = outsRecordDetail.getAmount();
//				double remainAmount = stock.getAmount() - outAmount;
//				if (remainAmount == 0)
//					delStocks.add(stock);
//				if (remainAmount > 0) {
//					double weight = new BigDecimal(stock.getSpecWeight() * remainAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//					stock.setAmount(remainAmount);
//					stock.setSumWeight(weight);
//					updateStocks.add(stock);
//				}
			}

			PledgeRecord pledgeRecord = new PledgeRecord();
			pledgeRecord.setDelegator(outsRecord.getDelegator());
			pledgeRecord.setSupervisionCustomer(outsRecord.getSupervisionCustomer());
//			pledgeRecord.setCode(outsRecord.getWarehouse().getPledgeRecordCode());
			pledgeRecord.setRecordName(CodeUtils.getPledgeRecordCode(outsRecord.getSupervisionCustomer().getCode()));
			pledgeRecord.setWarehouse(outsRecord.getWarehouse());

			double stockSumWeight = 0;
//			List<PledgeRecordDetail> pledgeRecordDetails = new ArrayList<PledgeRecordDetail>();
//			for (Stock stock : stockMap.values()) {
//				PledgeRecordDetail detail = new PledgeRecordDetail(stock, outsRecord.getWeight());
//				detail.setPledgeRecord(pledgeRecord);
//				stockSumWeight += detail.getSumWeight();
//				pledgeRecordDetails.add(detail);
//			}
			stockSumWeight = new BigDecimal(stockSumWeight).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			pledgeRecord.setSumWeight(stockSumWeight);

			// 保存
			pledgeRecordDao.save(pledgeRecord);
//			pledgeRecordDetailDao.save(pledgeRecordDetails);

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
