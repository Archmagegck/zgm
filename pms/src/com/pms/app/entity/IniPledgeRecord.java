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
import org.joda.time.DateTime;

/**
 * 质物清单
 * @author wangzz
 */
@Entity
@Table(name = "t_iniPledgeRecord")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IniPledgeRecord {
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "ipr_id")
	private String id;
	
	/**
	 * 所属仓库
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "w_id")
	private Warehouse warehouse;
	
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
	 * 质物清单号
	 */
	@Column(name = "ipr_code")
	private String code;
	
	/**
	 * 质物清单加密号
	 */
	@Column(name = "ipr_passcode")
	private String passcode;
	
	/**
	 * 总重量（g）
	 */
	@Column(name = "ipr_sumWeight")
	private Double sumWeight = 0.0;
	
	/**
	 * 日期
	 */
	@Column(name = "ipr_date")
	private Date date = new Date();

	
	/**
	 * 质物清单明细
	 */
	@OneToMany(mappedBy = "iniPledgeRecord")
	private List<IniPledgeRecordDetail> iniPledgeRecordDetails = new ArrayList<IniPledgeRecordDetail>();
	
	public String getDateStr(){
		return new DateTime(date).toString("yyyy-MM-dd");
	}
	
	public String getDateYmdhmsStr(){
		return new DateTime(date).toString("yyyy-MM-dd HH:mm:ss");
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

	public String getPasscode() {
		return passcode;
	}

	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}

	public List<IniPledgeRecordDetail> getIniPledgeRecordDetails() {
		return iniPledgeRecordDetails;
	}

	public void setIniPledgeRecordDetails(List<IniPledgeRecordDetail> iniPledgeRecordDetails) {
		this.iniPledgeRecordDetails = iniPledgeRecordDetails;
	}
	
	
}
