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
	private String company = "";
	
	/**
	 * 是否封闭运输<br>
	 * 0:否<br>
	 * 1:是
	 */
	@Column(name = "s_closedTran")
	private Integer closedTran;

	
	/**
	 * 备注/标记
	 */
	@Column(name = "s_desc")
	private String desc = "";
	
	/**
	 * 是否在库,0:不在，1：在
	 */
	@Column(name = "s_inStock")
	private Integer inStock = 1;
	
	public Stock() {}
	
	public Stock(Warehouse warehouse, Integer closedTran, InsRecordDetail insRecordDetail) {
		this.warehouse = warehouse;
		this.style = insRecordDetail.getStyle();
		this.pledgePurity = insRecordDetail.getPledgePurity();
		this.specWeight = insRecordDetail.getSpecWeight();
		this.amount = insRecordDetail.getAmount();
		this.sumWeight = insRecordDetail.getSumWeight();
		this.company = insRecordDetail.getCompany();
		this.closedTran = closedTran;
		this.desc = insRecordDetail.getDesc();
	}
	
	/**
	 * 入库
	 * @param insRecordDetail 入库明细
	 */
	public void add(InsRecordDetail insRecordDetail) {
		this.amount += insRecordDetail.getAmount();
		this.sumWeight += insRecordDetail.getSumWeight();
	}
	
	public String getKey() {
		StringBuffer sb = new StringBuffer("{");
		sb.append("\"warehouse\":\"").append(warehouse.getId()).append("\",");
		sb.append("\"style\":\"").append(style.getId()).append("\",");
		sb.append("\"pledgePurity\":\"").append(pledgePurity.getId()).append("\",");
		sb.append("\"specWeight\":\"").append(specWeight).append("\",");
		sb.append("\"company\":\"").append(company).append("\",");
		sb.append("\"closedTran\":\"").append(closedTran).append("\",");
		sb.append("\"desc\":\"").append(desc).append("\"");
		sb.append("}");
		return sb.toString();
	}
	
	public String getOutKey() {
		StringBuffer sb = new StringBuffer("{");
		sb.append("\"warehouse\":\"").append(warehouse.getId()).append("\",");
		sb.append("\"style\":\"").append(style.getId()).append("\",");
		sb.append("\"pledgePurity\":\"").append(pledgePurity.getId()).append("\",");
		sb.append("\"specWeight\":\"").append(specWeight).append("\",");
		sb.append("\"company\":\"").append(company).append("\",");
		sb.append("\"desc\":\"").append(desc).append("\"");
		sb.append("}");
		return sb.toString();
	}

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getClosedTran() {
		return closedTran;
	}

	public void setClosedTran(Integer closedTran) {
		this.closedTran = closedTran;
	}

	public Integer getInStock() {
		return inStock;
	}

	public void setInStock(Integer inStock) {
		this.inStock = inStock;
	}
	
}	
