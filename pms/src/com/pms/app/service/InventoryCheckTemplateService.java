package com.pms.app.service;

import java.util.ArrayList;
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

import com.pms.app.dao.InventoryCheckTemplateDao;
import com.pms.app.entity.InventoryCheckTemplate;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class InventoryCheckTemplateService extends BaseService<InventoryCheckTemplate, String> {

	@Autowired private InventoryCheckTemplateDao inventoryCheckTemplateDao;

	@Override
	protected BaseDao<InventoryCheckTemplate, String> getEntityDao() {
		return inventoryCheckTemplateDao;
	}
	
	public List<InventoryCheckTemplate> findByWarehouseId(final String warehouseId) {
		Specification<InventoryCheckTemplate> specification = new Specification<InventoryCheckTemplate>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Predicate toPredicate(Root<InventoryCheckTemplate> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if(StringUtils.hasText(warehouseId)) {
					Path expression = root.get("warehouse");
					expression = expression.get("id");
					predicates.add(cb.equal(expression, warehouseId));
				}
				query.orderBy(cb.asc(root.get("trayNo")));
				if (predicates.size() > 0) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				}
				return cb.conjunction();
			}
		};
		return getEntityDao().findAll(specification);
	}

}
