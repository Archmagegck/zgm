package com.pms.app.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * 盘存记录
 * @author wangzz
 */
@Entity
@Table(name = "t_inventory")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Inventory {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "i_id")
	private String id;
	
	/**
	 * 所属仓库
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "w_id")
	private Warehouse warehouse;
	
	/**
	 * 记录编号
	 */
	@Column(name = "i_code")
	private String code;
	
	/**
	 * 是否相符<br>
	 * 1:一致
	 * 0:不一致
	 */
	@Column(name = "i_equation")
	private Integer equation = 1;
	
	/**
	 * 监管员姓名
	 */
	@Column(name = "i_supName")
	private String supName;
	
	/**
	 * 盘存日期
	 */
	@Column(name = "i_invDate")
	private Date invDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getEquation() {
		return equation;
	}

	public void setEquation(Integer equation) {
		this.equation = equation;
	}

	public String getSupName() {
		return supName;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}

	public Date getInvDate() {
		return invDate;
	}

	public void setInvDate(Date invDate) {
		this.invDate = invDate;
	}
	
}
