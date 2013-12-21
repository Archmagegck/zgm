package com.pms.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pms.app.dao.PledgePriceDao;
import com.pms.app.entity.PledgePrice;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class PledgePriceService extends BaseService<PledgePrice, String> {

	@Autowired private PledgePriceDao pledgePriceDao;

	@Override
	protected BaseDao<PledgePrice, String> getEntityDao() {
		return pledgePriceDao;
	}

}
