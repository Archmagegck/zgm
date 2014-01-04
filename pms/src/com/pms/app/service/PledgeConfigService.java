package com.pms.app.service;

import java.util.ArrayList;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.pms.app.dao.PledgeConfigDao;
import com.pms.app.dao.SupervisorDao;
import com.pms.app.entity.PledgeConfig;
import com.pms.app.entity.Supervisor;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class PledgeConfigService extends BaseService<PledgeConfig, String> {

	@Autowired private PledgeConfigDao pledgeConfigDao;
	@Autowired private SupervisorDao supervisorDao;

	@Override
	protected BaseDao<PledgeConfig, String> getEntityDao() {
		return pledgeConfigDao;
	}
	
	@Transactional
	public void update(PledgeConfig pledgeConfig, Double shippingWeight) {
		Supervisor supervisor = supervisorDao.findOne(pledgeConfig.getSupervisor().getId());
		supervisor.setShippingWeight(shippingWeight);
		pledgeConfigDao.save(pledgeConfig);
	}
	
	public Page<PledgeConfig> findByDelegatorIdAndSupervisionCustomerId(String delegatorId, String supervisionCustomerId, Pageable page) {
		return pledgeConfigDao.findAll(DelegatorAndSupervisionCustomerSelect.findByUserTypeAndParam(delegatorId, supervisionCustomerId), page);
	}
	
	static class DelegatorAndSupervisionCustomerSelect {
		public static Specification<PledgeConfig> findByUserTypeAndParam(final String delegatorId, final String supervisionCustomerId) {
			return new Specification<PledgeConfig>() {
				@Override
				public Predicate toPredicate(Root<PledgeConfig> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> predicates = new ArrayList<Predicate>();
					if(StringUtils.hasText(delegatorId)) {
						Path<String> expression = root.get("delegator");
						expression = expression.get("id");
						predicates.add(cb.equal(expression, delegatorId));
					}
					if(StringUtils.hasText(supervisionCustomerId)) {
						Path<String> expression = root.get("supervisionCustomer");
						expression = expression.get("id");
						predicates.add(cb.equal(expression, supervisionCustomerId));
					}
					if (predicates.size() > 0) {
						return cb.and(predicates.toArray(new Predicate[predicates.size()]));
					}
					return cb.conjunction();
				}
			};
		}
	}

}
