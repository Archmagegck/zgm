package com.pms.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pms.app.dao.InventoryDao;
import com.pms.app.dao.PurityPriceDao;

import com.pms.app.entity.Inventory;
import com.pms.app.entity.PurityPrice;
import com.pms.app.entity.Warehouse;
import com.pms.app.entity.vo.InventoryShow;
import com.pms.app.util.DateUtils;

@Service
public class InventoryStatisticsService {
	
	@Autowired InventoryDao inventoryDao;
	@Autowired PurityPriceDao purityPriceDao;
	@Autowired WarehouseService warehouseService;
	@Autowired PurityPriceService purityPriceService;
	
	public List<InventoryShow> findDailySvgWeightAndPrice(Date beginDate, Date endDate){
		List<Warehouse> warehouses=warehouseService.findAll();
		List<InventoryShow> inventoryShows=new ArrayList<InventoryShow>();
		//Map=purityPriceService.findPriceMap();
		PurityPrice theNewestPrices = purityPriceDao.findNewestList().get(0);
		double price=theNewestPrices.getPrice();
		for(Warehouse warehouse:warehouses){
			List<Inventory> inventorys = inventoryDao.findAll(getInsSpec( warehouse.getId() ,beginDate, endDate));
			InventoryShow inventoryShow=new InventoryShow();
			int days=inventorys.size();
			double inventorySumWeight=0.0;
			for(Inventory inventory:inventorys){
				inventorySumWeight+=inventory.getSumWeight();
			}
			inventoryShow.setWarehouse(warehouse);
			inventoryShow.setInventoryWeight(inventorySumWeight/days);
			inventoryShow.setInventoryPrice((inventorySumWeight/days)*price);
			inventoryShows.add(inventoryShow);
		}
		return inventoryShows;
	}
	
	
	public Specification<Inventory> getInsSpec( final String warehouseId,final Date beginDate, final Date endDate) {
		Specification<Inventory> specificationIns = new Specification<Inventory>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Predicate toPredicate(Root<Inventory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (StringUtils.hasText(warehouseId)) {
					Path expression = root.get("warehouse");
					expression = expression.get("id");
					predicates.add(cb.equal(expression, warehouseId));
				}
				if (!StringUtils.isEmpty(beginDate)) {
					Path expression = root.get("date");
					predicates.add(cb.greaterThanOrEqualTo(expression, DateUtils.dateToDayBegin(beginDate)));
				}
				if (!StringUtils.isEmpty(endDate)) {
					Path expression = root.get("date");
					predicates.add(cb.lessThanOrEqualTo(expression, DateUtils.dateToDayEnd(endDate)));
				}
				query.orderBy(cb.desc(root.get("date")));
				if (predicates.size() > 0) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				}
				return cb.conjunction();
			}
		};
		return specificationIns;
	}
}
