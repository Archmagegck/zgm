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
 * 每日质物清单明细
 * @author wangzz
 */
@Entity
@Table(name = "t_pledgeDailyDetail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PledgeDailyDetail {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "pdd_id")
	private String id;
	
	/**
	 * 所属质物清单
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "pd_id")
	private PledgeDaily pledgeDaily;
	
	/**
	 * 款式大类
	 */
	@Column(name = "pdd_style")
	private String style;
	
	/**
	 * 成色
	 */
	@Column(name = "pdd_pledgePurity")
	private String pledgePurity;
	
	/**
	 * 规格重量（kg/件）
	 */
	@Column(name = "pdd_specWeight")
	private Double specWeight;
	
	/**
	 * 实际数量（件）
	 */
	@Column(name = "pdd_amount")
	private Double realAmount;
	
	/**
	 * 总重量（kg）
	 */
	@Column(name = "pdd_sumWeight")
	private Double sumWeight;
	
	/**
	 * 生产厂家
	 */
	@Column(name = "pdd_company")
	private String company;
	
	/**
	 * 存储地点
	 */
	@Column(name = "pdd_storage")
	private String storage;
	
	/**
	 * 是否封闭运输<br>
	 * 0:否<br>
	 * 1:是
	 */
	@Column(name = "pdd_closedTran")
	private int closedTran;
	
	/**
	 * 光谱法抽检重量占比
	 */
	@Column(name = "pdd_spectrumRate")
	private Double spectrumRate;
	
	/**
	 * 溶金法抽检重量占比
	 */
	@Column(name = "pdd_dissolveRate")
	private Double dissolveRate;
	
	/**
	 * 标记
	 */
	@Column(name = "pdd_desc")
	private String desc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PledgeDaily getPledgeDaily() {
		return pledgeDaily;
	}

	public void setPledgeDaily(PledgeDaily pledgeDaily) {
		this.pledgeDaily = pledgeDaily;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
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

	public Double getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(Double realAmount) {
		this.realAmount = realAmount;
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

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public int getClosedTran() {
		return closedTran;
	}

	public void setClosedTran(int closedTran) {
		this.closedTran = closedTran;
	}

	public Double getSpectrumRate() {
		return spectrumRate;
	}

	public void setSpectrumRate(Double spectrumRate) {
		this.spectrumRate = spectrumRate;
	}

	public Double getDissolveRate() {
		return dissolveRate;
	}

	public void setDissolveRate(Double dissolveRate) {
		this.dissolveRate = dissolveRate;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
	
	
}
