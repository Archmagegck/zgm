package com.pms.base.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.pms.app.util.DateUtils;
import com.pms.base.dao.BaseDao;

/**
 * 通用service基类，封装了一般的CURD<BR>
 * 简单的service只要实现此类就可实现添删改查<BR>
 * 
 * @author wangzz
 * 
 * @param <T> 实体
 * @param <PK> 主键类型
 */
@Transactional
public abstract class BaseService<T, PK extends Serializable> {

	/**
	 * 获取当前dao<br>
	 * 子类实现此方法注入dao
	 * 
	 * @return 继承IBaseDao的dao
	 */
	protected abstract BaseDao<T, PK> getEntityDao();

	/**
	 * 根据Id查询实体
	 * 
	 * @param id
	 * @return 实体
	 */
	@Transactional(readOnly = true)
	public T findById(PK id) {
		return getEntityDao().findOne(id);
	}

	/**
	 * 更新实体
	 * 
	 * @param entity 实体对象
	 */
	public void save(T entity) {
		getEntityDao().save(entity);
	}

	/**
	 * 批量更新实体
	 * 
	 * @param entityList 实体列表
	 */
	public void save(List<T> entityList) {
		getEntityDao().save(entityList);
	}

	/**
	 * 删除实体
	 * 
	 * @param entity 实体对象
	 */
	public void delete(T entity) {
		getEntityDao().delete(entity);
	}

	/**
	 * 根据Id删除实体
	 * 
	 * @param id
	 */
	public void delete(PK id) {
		getEntityDao().delete(id);
	}

	/**
	 * 根据Id数组批量删除实体
	 */
	public void delete(PK[] id) {
		for (PK pk : id) {
			delete(pk);
		}
	}

	/**
	 * 根据实体列表批量删除
	 */
	public void delete(List<T> entityList) {
		getEntityDao().delete(entityList);
	}

	/**
	 * 判断此id的实体是否存在
	 * 
	 * @param hql hql语句
	 * @param values 可变参数
	 * @return 影响的个数
	 */
	@Transactional(readOnly = true)
	public boolean exists(PK id) {
		return getEntityDao().exists(id);
	}

	/**
	 * 查询所有实体
	 * 
	 * @return List 查询结果集
	 */
	@Transactional(readOnly = true)
	public List<T> findAll() {
		return (List<T>) getEntityDao().findAll();
	}

	/**
	 * 分页查询所有实体
	 * 
	 * @param pageable 分页条件
	 * @return Page 分页查询结果,附带结果列表及所有查询时的参数.<br>
	 *         可通过page.getResult()获取.
	 */
	@Transactional(readOnly = true)
	public Page<T> findPage(Pageable pageable) {
		return getEntityDao().findAll(pageable);
	}

	/**
	 * 根据条件精确查询所有实体
	 * 
	 * @param queryName 要查询的列名
	 * @param queryValue 要查询的值
	 * @return List 查询结果集
	 */
	@Transactional(readOnly = true)
	public List<T> findAllEq(final String queryName, final String queryValue) {
		return getEntityDao().findAll(new Specification<T>() {
			@SuppressWarnings("rawtypes")
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (StringUtils.hasText(queryName) && StringUtils.hasText(queryValue)) {
					String[] names = StringUtils.split(queryName, ".");
					if (names != null) {
						Path expression = root.get(names[0]);
						for (int i = 1; i < names.length; i++) {
							expression = expression.get(names[i]);
						}
						return cb.equal(expression, queryValue);
					} else {
						Path expression = root.get(queryName);
						return cb.equal(expression, queryValue);
					}
				}
				return cb.conjunction();
			}
		});
	}

	/**
	 * 根据条件精确分页查询所有实体
	 * 
	 * @param pageable 分页参数
	 * @param queryName 要查询的列名
	 * @param queryValue 要查询的值
	 * @return List 查询结果集
	 */
	@Transactional(readOnly = true)
	public Page<T> findAllEq(Pageable pageable, final String queryName, final String queryValue) {
		return getEntityDao().findAll(new Specification<T>() {
			@SuppressWarnings("rawtypes")
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (StringUtils.hasText(queryName) && StringUtils.hasText(queryValue)) {
					String[] names = StringUtils.split(queryName, ".");
					if (names != null) {
						Path expression = root.get(names[0]);
						for (int i = 1; i < names.length; i++) {
							expression = expression.get(names[i]);
						}
						return cb.equal(expression, queryValue);
					} else {
						Path expression = root.get(queryName);
						return cb.equal(expression, queryValue);
					}
				}
				return cb.conjunction();
			}
		}, pageable);
	}

	/**
	 * 根据查询条件分页查询结果集
	 * 
	 * @param pageable 分页参数
	 * @param queryName 查询条件
	 * @param queryValue 查询值
	 * @return 分页列表
	 */
	@Transactional(readOnly = true)
	public Page<T> findAllLike(Pageable pageable, final String queryName, final String queryValue) {
		return getEntityDao().findAll(new Specification<T>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (StringUtils.hasText(queryValue) && StringUtils.hasText(queryName)) {
					String[] names = StringUtils.split(queryName, ".");
					if (names != null) {
						Path expression = root.get(names[0]);
						for (int i = 1; i < names.length; i++) {
							expression = expression.get(names[i]);
						}
						return cb.like(expression, "%" + queryValue + "%");
					} else {
						Path expression = root.get(queryName);
						return cb.like(expression, "%" + queryValue + "%");
					}
				}
				return cb.conjunction();
			}
		}, pageable);
	}

	/**
	 * 根据查询条件查询结果集
	 * 
	 * @param queryName 查询条件
	 * @param queryValue 查询值
	 * @return 查询结果列表
	 */
	@Transactional(readOnly = true)
	public List<T> findAllLike(final String queryName, final String queryValue) {
		return getEntityDao().findAll(new Specification<T>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (StringUtils.hasText(queryValue) && StringUtils.hasText(queryName)) {
					String[] names = StringUtils.split(queryName, ".");
					if (names != null) {
						Path expression = root.get(names[0]);
						for (int i = 1; i < names.length; i++) {
							expression = expression.get(names[i]);
						}
						return cb.like(expression, "%" + queryValue + "%");
					} else {
						Path expression = root.get(queryName);
						return cb.like(expression, "%" + queryValue + "%");
					}
				}
				return cb.conjunction();
			}
		});
	}
	
	public Page<T> findPageByQuery(Pageable pageable, final String warehouseId, final Date beginDate, final Date endDate) {
		Specification<T> specification = new Specification<T>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if(StringUtils.hasText(warehouseId)) {
					Path expression = root.get("warehouse");
					expression = expression.get("id");
					predicates.add(cb.equal(expression, warehouseId));
				}
				if(!StringUtils.isEmpty(beginDate)) {
					Path expression = root.get("date");
					predicates.add(cb.greaterThanOrEqualTo(expression, DateUtils.dateToDayBegin(beginDate)));
				}
				if(!StringUtils.isEmpty(endDate)) {
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
		return getEntityDao().findAll(specification, pageable);
	}
	
	public Page<T> findPageByQuery(Pageable pageable, final String warehouseId, final Date date) {
		Specification<T> specification = new Specification<T>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if(StringUtils.hasText(warehouseId)) {
					Path expression = root.get("warehouse");
					expression = expression.get("id");
					predicates.add(cb.equal(expression, warehouseId));
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
	
	
	public Page<T> findPageByQuery(Pageable pageable, final String delegatorId, final String supervisionCustomerId, final Date date) {
		Specification<T> specification = new Specification<T>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
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
	
	
	public List<T> findListByQuery(final String delegatorId, final String supervisionCustomerId, final Date date) {
		Specification<T> specification = new Specification<T>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
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
		return getEntityDao().findAll(specification);
	}
	
}
