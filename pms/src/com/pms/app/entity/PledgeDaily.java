package com.pms.app.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * 每日质物清单
 * @author wangzz
 */
@Entity
@Table(name = "t_pledgeDaily")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PledgeDaily {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "pd_id")
	private String id;
	
	/**
	 * 所属仓库
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "w_id")
	private Warehouse warehouse;
	
	/**
	 * 质物清单号
	 */
	@Column(name = "pd_code")
	private String code;
	
	/**
	 * 总重量（kg）
	 */
	@Column(name = "pd_sumWeight")
	private Double sumWeight;
	
	/**
	 * 日期
	 */
	@Column(name = "pd_date")
	private Date date;
	
	/**
	 * 明细
	 */
	@OneToMany(mappedBy = "pledgeDaily")
	private List<PledgeDailyDetail> pledgeDailyDetails = new ArrayList<PledgeDailyDetail>();

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

	public Double getSumWeight() {
		return sumWeight;
	}

	public void setSumWeight(Double sumWeight) {
		this.sumWeight = sumWeight;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<PledgeDailyDetail> getPledgeDailyDetails() {
		return pledgeDailyDetails;
	}

	public void setPledgeDailyDetails(List<PledgeDailyDetail> pledgeDailyDetails) {
		this.pledgeDailyDetails = pledgeDailyDetails;
	}
	
	
	
}
