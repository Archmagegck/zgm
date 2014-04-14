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
//		insRecord.setCode(CodeUtils.getInsRecordCode(supervisionCustomer.getCode()));
//		stockService.save(stockMap);
	}

}
