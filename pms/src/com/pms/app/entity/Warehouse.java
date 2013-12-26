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
 * 仓库
 * @author wangzz
 */
@Entity
@Table(name = "t_warehouse")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Warehouse {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "w_id")
	private String id;
	
	/**
	 * 所属监管员
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "s_id")
	private Supervisor supervisor;
	
	/**
	 * 款式大类
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "sty_id")
	private Style style;
	
	
	/**
	 * 成色
	 */
	@Column(name = "w_pledgePurity")
	private String pledgePurity;
	
	
	/**
	 * 规格重量（kg/件）
	 */
	@Column(name = "w_specWeight")
	private Double specWeight;
	
	
	/**
	 * 款式数量（件）
	 */
	@Column(name = "w_amount")
	private Double amount;
	
	
	/**
	 * 总重量
	 */
	@Column(name = "w_sumWeight")
	private String sumWeight;
	
	
	/**
	 * 总价值
	 */
	@Column(name = "w_sumValue")
	private String sumValue;
	
	
	public Warehouse() { }
	
	
	
	public Warehouse(Supervisor supervisor) {
		this.supervisor = supervisor;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Style getStyle() {
		return style;
	}


	public void setStyle(Style style) {
		this.style = style;
	}


	public String getPledgePurity() {
		return pledgePurity;
	}


	public void setPledgePurity(String pledgePurity) {
		this.pledgePurity = pledgePurity;
	}


	public Double getSpecWeight() {
		return specWeight;
	}


	public void setSpecWeight(Double specWeight) {
		this.specWeight = specWeight;
	}


	public Double getAmount() {
		return amount;
	}


	public void setAmount(Double amount) {
		this.amount = amount;
	}


	public String getSumWeight() {
		return sumWeight;
	}


	public void setSumWeight(String sumWeight) {
		this.sumWeight = sumWeight;
	}


	public String getSumValue() {
		return sumValue;
	}


	public void setSumValue(String sumValue) {
		this.sumValue = sumValue;
	}


	public Supervisor getSupervisor() {
		return supervisor;
	}


	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}
	
}
