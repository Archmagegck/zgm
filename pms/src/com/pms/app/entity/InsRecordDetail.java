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
import org.joda.time.DateTime;

/**
 * 入库单明细
 * @author wangzz
 */
@Entity
@Table(name = "t_insRecordDetail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InsRecordDetail {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "ind_id")
	private String id;
	
	
	/**
	 * 所属入库单
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "in_id")
	private InsRecord insRecord;
	
	
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
	 * 数量（件）
	 */
	@Column(name = "ind_amount")
	private Double amount;
	
	
	/**
	 * 重量
	 */
	@Column(name = "ind_weight")
	private Double weight = 0.0;
	
	
	/**
	 * 生产厂家
	 */
	@Column(name = "ind_company")
	private String company;
	
	
	/**
	 * 备注
	 */
	@Column(name = "ind_desc")
	private String desc;
	
	
	/**
	 * 入库时间
	 */
	@Column(name = "ind_date")
	private Date date = new Date();
	
	
	public String getKey() {
		StringBuffer sb = new StringBuffer("{");
		sb.append("\"warehouse\":\"").append(insRecord.getWarehouse().getId()).append("\",");
		sb.append("\"style\":\"").append(style.getId()).append("\",");
		sb.append("\"pledgePurity\":\"").append(pledgePurity.getId()).append("\",");
		sb.append("}");
		return sb.toString();
	}
	
	
	public String getDateStr() {
		return new DateTime(date).toString("yyyy-MM-dd");
	}
	
	public String getTimeStr() {
		return new DateTime(date).toString("HH:mm");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public InsRecord getInsRecord() {
		return insRecord;
	}

	public void setInsRecord(InsRecord insRecord) {
		this.insRecord = insRecord;
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	
}
