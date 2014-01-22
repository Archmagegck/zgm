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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pms.app.dao.InventoryDetailDao;
import com.pms.app.entity.InventoryDetail;
import com.pms.app.util.DateUtils;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class InventoryDetailService extends BaseService<InventoryDetail, String> {

	@Autowired private InventoryDetailDao inventoryDetailDao;

	@Override
	protected BaseDao<InventoryDetail, String> getEntityDao() {
		return inventoryDetailDao;
	}
	
	public Page<InventoryDetail> findPageByQuery(Pageable pageable, final String delegatorId, final String supervisionCustomerId, final Date date) {
		Specification<InventoryDetail> specification = new Specification<InventoryDetail>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Predicate toPredicate(Root<InventoryDetail> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				Path e = root.get("equation");
				predicates.add(cb.equal(e, 0));
				if(StringUtils.hasText(delegatorId)) {
					Path expression = root.get("delegator");
					expression = expression.get("id");
					predicates.add(cb.equal(expression, delegatorId));
				}
				if(StringUtils.hasText(supervisionCustomerId)) {
					Path expression = root.get("supervisionCustomer");
					expression = expression.get("id");
					predicates.add(cb.equal(expression, supervisionCustomerId));
				}
				if(!StringUtils.isEmpty(date)) {
					Path expression = root.get("date");
					predicates.add(cb.between(expression, DateUtils.dateToDayBegin(date), DateUtils.dateToDayEnd(date)));
				}
				query.orderBy(cb.desc(root.get("date")));
				if (predicates.size() > 0) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				}
				return cb.conjunction();
			}
		};
		return getEntityDao().findAll(specification, pageable);
	}
	

}
