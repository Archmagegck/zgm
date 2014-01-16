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
@Table(name = "t_pledgeRecord")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PledgeRecord {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "pr_id")
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
	@Column(name = "pr_code")
	private String code;
	
	/**
	 * 总重量（kg）
	 */
	@Column(name = "pr_sumWeight")
	private Double sumWeight = 0.0;
	
	/**
	 * 日期
	 */
	@Column(name = "pr_date")
	private Date date = new Date();
	
	/**
	 * 文件名称
	 */
	@Column(name = "pr_recordName")
	public String recordName;
	
	/**
	 * 文件路径
	 */
	@Column(name = "pr_recordFile")
	public String recordFile;
	
	/**
	 * 是否上传
	 * 0:未上传
	 * 1:已上传
	 */
	@Column(name = "pr_ifUpload")
	public Integer ifUpload = 0;
	
	/**
	 * 质物清单明细
	 */
	@OneToMany(mappedBy = "pledgeRecord")
	private List<PledgeRecordDetail> pledgeRecordDetails = new ArrayList<PledgeRecordDetail>();
	
	public String getDateStr(){
		return new DateTime(date).toString("yyyy-MM-dd");
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

	public String getRecordFile() {
		return recordFile;
	}

	public void setRecordFile(String recordFile) {
		this.recordFile = recordFile;
	}

	public Integer getIfUpload() {
		return ifUpload;
	}

	public void setIfUpload(Integer ifUpload) {
		this.ifUpload = ifUpload;
	}

	public List<PledgeRecordDetail> getPledgeRecordDetails() {
		return pledgeRecordDetails;
	}

	public void setPledgeRecordDetails(List<PledgeRecordDetail> pledgeRecordDetails) {
		this.pledgeRecordDetails = pledgeRecordDetails;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
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
	
	
}
