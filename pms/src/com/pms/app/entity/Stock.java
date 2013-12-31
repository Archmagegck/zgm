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
 * 库存
 * @author wangzz
 */
@Entity
@Table(name = "t_stock")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Stock {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "s_id")
	private String id;
	
	/**
	 * 所属仓库
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "w_id")
	private Warehouse warehouse;
	
	/**
	 * 款式大类
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "sty_id")
	private Style style;
	
	/**
	 * 成色
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "pp_id")
	private PledgePurity pledgePurity;
	
	/**
	 * 规格重量（kg/件）
	 */
	@Column(name = "s_specWeight")
	private Double specWeight;
	
	/**
	 * 数量（件）
	 */
	@Column(name = "s_amount")
	private Double amount;
	
	/**
	 * 总重量
	 */
	@Column(name = "s_sumWeight")
	private Double sumWeight;
	
	/**
	 * 生产厂家
	 */
	@Column(name = "s_company")
	private String company;
	
	/**
	 * 总价值
	 */
	@Column(name = "s_sumValue")
	private String sumValue;
	
	/**
	 * 备注/标记
	 */
	@Column(name = "s_desc")
	private String desc;

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

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	public PledgePurity getPledgePurity() {
		return pledgePurity;
	}

	public void setPledgePurity(PledgePurity pledgePurity) {
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

	public Double getSumWeight() {
		return sumWeight;
	}

	public void setSumWeight(Double sumWeight) {
		this.sumWeight = sumWeight;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getSumValue() {
		return sumValue;
	}

	public void setSumValue(String sumValue) {
		this.sumValue = sumValue;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}	
