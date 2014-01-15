package com.pms.app.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pms.app.dao.InsRecordDetailDao;
import com.pms.app.dao.OutsRecordDetailDao;
import com.pms.app.dao.SupervisionCustomerDao;
import com.pms.app.entity.InsRecordDetail;
import com.pms.app.entity.OutsRecordDetail;
import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.vo.InOutsRecord;
import com.pms.app.util.DateUtils;

@Service
public class InOutsRecordService {

	@Autowired InsRecordDetailDao insRecordDetailDao;
	@Autowired OutsRecordDetailDao outsRecordDetailDao;
	@Autowired SupervisionCustomerDao supervisionCustomerDao;

	public Map<SupervisionCustomer, List<InOutsRecord>> queryByDelegatorAndDateBetween(String delegatorId, Date beginDate, Date endDate) {
		Map<SupervisionCustomer, List<InOutsRecord>> inoutsRecordMap = new HashMap<SupervisionCustomer, List<InOutsRecord>>();
		List<SupervisionCustomer> supervisionCustomerList = supervisionCustomerDao.findListByDelegatorId(delegatorId);
		for (SupervisionCustomer supervisionCustomer : supervisionCustomerList) {
			List<InOutsRecord> inOutsRecordList = new ArrayList<InOutsRecord>();
			String supervisionCustomerId = supervisionCustomer.getId();
			List<InsRecordDetail> insRecordDetails = insRecordDetailDao.findAll(getInsSpec(supervisionCustomerId, beginDate, endDate));
			for (InsRecordDetail insRecordDetail : insRecordDetails) {
				inOutsRecordList.add(new InOutsRecord(insRecordDetail));
			}
			List<OutsRecordDetail> outsRecordDetails = outsRecordDetailDao.findAll(getOutsSpec(supervisionCustomerId, beginDate, endDate));
			for (OutsRecordDetail outsRecordDetail : outsRecordDetails) {
				inOutsRecordList.add(new InOutsRecord(outsRecordDetail));
			}
			Collections.sort(inOutsRecordList);
			if(!inOutsRecordList.isEmpty())
				inoutsRecordMap.put(supervisionCustomer, inOutsRecordList);
		}
		return inoutsRecordMap;
	}

	public Specification<InsRecordDetail> getInsSpec(final String supervisionCustomerId, final Date beginDate, final Date endDate) {
		Specification<InsRecordDetail> specificationIns = new Specification<InsRecordDetail>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Predicate toPredicate(Root<InsRecordDetail> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (StringUtils.hasText(supervisionCustomerId)) {
					Path expression = root.get("supervisionCustomer");
					expression = expression.get("id");
					predicates.add(cb.equal(expression, supervisionCustomerId));
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
	
	
	public Specification<OutsRecordDetail> getOutsSpec(final String supervisionCustomerId, final Date beginDate, final Date endDate) {
		Specification<OutsRecordDetail> specificationIns = new Specification<OutsRecordDetail>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Predicate toPredicate(Root<OutsRecordDetail> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (StringUtils.hasText(supervisionCustomerId)) {
					Path expression = root.get("supervisionCustomer");
					expression = expression.get("id");
					predicates.add(cb.equal(expression, supervisionCustomerId));
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
