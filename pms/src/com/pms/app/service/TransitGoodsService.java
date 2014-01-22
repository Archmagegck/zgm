package com.pms.app.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pms.app.dao.TransitGoodsDao;
import com.pms.app.entity.Stock;
import com.pms.app.entity.TransitGoods;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class TransitGoodsService extends BaseService<TransitGoods, String> {

	@Autowired private TransitGoodsDao transitGoodsDao;
	@Autowired private StockService stockService;

	@Override
	protected BaseDao<TransitGoods, String> getEntityDao() {
		return transitGoodsDao;
	}
	
	
	@Transactional
	public void save(TransitGoods transitGoods, String warehouseId) {
		double sumWeight = new BigDecimal(transitGoods.getAmount() * transitGoods.getSpecWeight()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		transitGoods.setSumWeight(sumWeight);
		Map<String, Stock> stockMap = stockService.findTranStockKeyMapByWarehouseId(warehouseId);
		Stock stock = stockMap.get(transitGoods.getKey());
		if(stock == null) {
			stock = new Stock(transitGoods);
			stockService.save(stock);
		} else {
			stock.add(transitGoods);
			stockService.save(stock);
		}
		transitGoodsDao.save(transitGoods);
	}
	
	
	@Transactional
	public void saveStock(TransitGoods transitGoods, String warehouseId) {
		Map<String, Stock> tranStockMap = stockService.findTranStockKeyMapByWarehouseId(warehouseId);
		Map<String, Stock> stockMap = stockService.findNoTranStockKeyMapByWarehouseId(warehouseId);
		String key = transitGoods.getKey();
		Stock tranStock = tranStockMap.get(key);
		double remainAmount = tranStock.getAmount() - transitGoods.getAmount();
		boolean ifdel = false;
		if(remainAmount > 0) {
			tranStock.minus(transitGoods);
			stockService.save(tranStock);
		} else {
			ifdel = true;
			stockService.delete(tranStock);
		}
		
		Stock inStock = stockMap.get(key);
		if(inStock == null) {
			if(ifdel) {
				Stock stt = new Stock(transitGoods);
				stt.setInStock(1);
				stockService.save(stt);
			} else {
				tranStock.setInStock(1);
				stockService.save(tranStock);
			}
		} else {
			inStock.add(transitGoods);
			stockService.save(inStock);
		}
		
		transitGoodsDao.save(transitGoods);
	}

}
