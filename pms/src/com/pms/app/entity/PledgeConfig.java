package com.pms.app.entity;

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
 * 质押物要求
 * @author wangzz
 */
@Entity
@Table(name = "t_pledgeConfig")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PledgeConfig {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "pc_id")
	private String id;
	
	/**
	 * 委托方
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "d_id")
	private Delegator delegator;
	
	/**
	 * 监管客户
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "sc_id")
	private SupervisionCustomer supervisionCustomer;
	
	
	/**
	 * 监管员
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "s_id")
	private Supervisor supervisor;
	
	
	/**
	 * 监管员出库重量（kg）
	 */
	@Column(name = "pc_outWeight")
	private Double outWeight;
	
	
	/**
	 * 质押物的最低重量要求（kg）
	 */
	@Column(name = "pc_minWeight")
	private Double minWeight;
	
	/**
	 * 质押物的最低价值要求（元）
	 */
	@Column(name = "pc_minValue")
	private Double minValue;
	
	/**
	 * 警戒线上限（%）
	 */
	@Column(name = "pc_maxCordon")
	private Double maxCordon;
	
	/**
	 * 警戒线下限（%）
	 */
	@Column(name = "pc_minCordon")
	private Double minCordon;
	
	/**
	 * 盘存误差范围（%）
	 */
	@Column(name = "pc_ieRange")
	private Double ieRange;
	
	/**
	 * 初始状态登记记录
	 * 0：未记录
	 * 1：已记录
	 */
	@Column(name = "pc_iniRecord")
	private int iniRecord = 0;
	
	/**
	 * 初始状态检测记录
	 * 0：未记录
	 * 1：已记录
	 */
	@Column(name = "pc_iniCheck")
	private int iniCheck = 0;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Delegator getDelegator() {
		return delegator;
	}

	public void setDelegator(Delegator delegator) {
		this.delegator = delegator;
	}

	public SupervisionCustomer getSupervisionCustomer() {
		return supervisionCustomer;
	}

	public void setSupervisionCustomer(SupervisionCustomer supervisionCustomer) {
		this.supervisionCustomer = supervisionCustomer;
	}

	public Supervisor getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}


	public Double getMinWeight() {
		return minWeight;
	}

	public void setMinWeight(Double minWeight) {
		this.minWeight = minWeight;
	}

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public Double getMaxCordon() {
		return maxCordon;
	}

	public void setMaxCordon(Double maxCordon) {
		this.maxCordon = maxCordon;
	}

	public Double getMinCordon() {
		return minCordon;
	}

	public void setMinCordon(Double minCordon) {
		this.minCordon = minCordon;
	}

	public Double getOutWeight() {
		return outWeight;
	}

	public void setOutWeight(Double outWeight) {
		this.outWeight = outWeight;
	}

	public Double getIeRange() {
		return ieRange;
	}

	public void setIeRange(Double ieRange) {
		this.ieRange = ieRange;
	}

	public int getIniRecord() {
		return iniRecord;
	}

	public void setIniRecord(int iniRecord) {
		this.iniRecord = iniRecord;
	}

	public int getIniCheck() {
		return iniCheck;
	}

	public void setIniCheck(int iniCheck) {
		this.iniCheck = iniCheck;
	}
	
	
}
