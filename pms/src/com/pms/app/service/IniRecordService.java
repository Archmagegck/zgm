package com.pms.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pms.app.dao.IniRecordDao;
import com.pms.app.dao.PledgePurityDao;
import com.pms.app.entity.IniRecord;
import com.pms.app.entity.PledgePurity;
import com.pms.app.entity.Stock;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class IniRecordService extends BaseService<IniRecord, String> {

	@Autowired private IniRecordDao iniRecordDao;
	@Autowired private StockService stockService;
	@Autowired private PledgePurityDao pledgePurityDao;

	@Override
	protected BaseDao<IniRecord, String> getEntityDao() {
		return iniRecordDao;
	}

	@Transactional
	public void save(List<IniRecord> iniRecordList, String warehouseId) {
		PledgePurity pledgePurity = pledgePurityDao.findListByType(1).get(0);
		
		Map<String, Stock> stockMap = stockService.findStockKeyMapByWarehouseId(warehouseId);
		List<Stock> updateStocks = new ArrayList<Stock>();
		
		for (IniRecord iniRecord : iniRecordList) {
			Stock stock = stockMap.get(iniRecord.getKey());
			if(stock == null) {
				stock = new Stock();
				stock.setPledgePurity(pledgePurity);
				stock.setStyle(iniRecord.getStyle());
				stock.setWarehouse(iniRecord.getWarehouse());
				stock.setSumWeight(iniRecord.getWeight());
			} else {
				stock.add(iniRecord.getWeight());
			}
			updateStocks.add(stock);
		}
		stockService.save(updateStocks);
		super.save(iniRecordList);
	}
	

}
