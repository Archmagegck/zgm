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
 * 封闭运输
 * @author gck
 *
 */
@Entity
@Table(name = "t_enclosedConvey")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EnclosedConvey {
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "ec_id")
	private String id;
	
	/**
	 * 单号
	 */
	@Column(name="ec_code")
	private String code;
	
	/**
	 * 委托方
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name="d_id")
	private Delegator delegator;
	
	/**
	 * 起运地
	 */
	@Column(name="ec_startPlace")
	private String startPlace;
	
	/**
	 * 起运时间
	 */
	@Column(name="ec_startDate")
	private Date startDate;
	
	/**
	 * 仓库
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "w_id")
	private Warehouse warehouse;
	
	/**
	 * 预计到达时间
	 */
	@Column(name="ec_endDate")
	private Date endDate;
	
	/**
	 * 重量
	 */
	@Column(name="ec_weight")
	private String weight;
	
	/**
	 * 登记时间
	 */
	@Column(name="ec_registerDate")
	private Date registerDate = new Date();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Delegator getDelegator() {
		return delegator;
	}

	public void setDelegator(Delegator delegator) {
		this.delegator = delegator;
	}

	public String getStartPlace() {
		return startPlace;
	}

	public void setStartPlace(String startPlace) {
		this.startPlace = startPlace;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
	
}
