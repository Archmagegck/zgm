package com.pms.app.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pms.app.dao.UserDao;
import com.pms.app.entity.User;
import com.pms.base.dao.BaseDao;
import com.pms.base.service.BaseService;

@Service
public class UserService extends BaseService<User, String> {

	@Autowired private UserDao userDao;

	@Override
	protected BaseDao<User, String> getEntityDao() {
		return userDao;
	}

	public Page<User> findPageByQuery(Pageable page, UserType userType, String queryName, String queryValue) {
		return userDao.findAll(UserCustomerSelect.findByUserTypeAndParam(userType, queryName, queryValue), page);
	}

	public List<User> findByLoginNameAndPassword(String username, String loginPwd) {
		return userDao.findByLoginNameAndLoginPwd(username, loginPwd);
	}

	static class UserCustomerSelect {
		public static Specification<User> findByUserTypeAndParam(final UserType type, final String queryName, final String queryValue) {
			return new Specification<User>() {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> predicates = new ArrayList<Predicate>();
//					if(type != null) {
//						Path<String> typeExp = root.get("userType");
//						predicates.add(cb.equal(typeExp, type));
//					}
					if (StringUtils.hasText(queryValue) && StringUtils.hasText(queryName)) {
						String[] names = StringUtils.split(queryName, ".");
						if(names != null) {
							Path expression = root.get(names[0]);
							for (int i = 1; i < names.length; i++) {
								expression = expression.get(names[i]);
							}
							predicates.add(cb.like(expression, "%" + queryValue + "%"));
						} else {
							Path expression = root.get(queryName);
							predicates.add(cb.like(expression, "%" + queryValue + "%"));
						}
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
