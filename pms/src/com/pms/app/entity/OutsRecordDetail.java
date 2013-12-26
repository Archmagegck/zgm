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
 * 出库单明细
 * @author wangzz
 */
@Entity
@Table(name = "t_outsRecordDetail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OutsRecordDetail {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "outd_id")
	private String id;
	
	
	/**
	 * 所属出库单
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "out_id")
	private OutsRecord outsRecord;
	
	
	/**
	 * 款式大类
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "sty_id")
	private Style style;
	
	
	/**
	 * 成色
	 */
	@Column(name = "out_pledgePurity")
	private String pledgePurity;
	
	
	/**
	 * 规格重量（kg/件）
	 */
	@Column(name = "outd_specWeight")
	private Double specWeight;
	
	
	/**
	 * 数量（件）
	 */
	@Column(name = "outd_amount")
	private Double amount;
	
	
	/**
	 * 生产厂家
	 */
	@Column(name = "outd_company")
	private String company;
	
	
	
	/**
	 * 备注
	 */
	@Column(name = "outd_desc")
	private String desc;



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public OutsRecord getOutsRecord() {
		return outsRecord;
	}



	public void setOutsRecord(OutsRecord outsRecord) {
		this.outsRecord = outsRecord;
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


	
}
