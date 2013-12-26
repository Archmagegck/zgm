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
 * 入库单
 * @author wangzz
 */
@Entity
@Table(name = "t_insRecord")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InsRecord {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "in_id")
	private String id;
	
	/**
	 * 入库单号
	 */
	@Column(name = "in_code")
	private String code;
	
	
	/**
	 * 总重量
	 */
	@Column(name = "in_sumWeight")
	private Double sumWeight;
	
	
	/**
	 * 所属仓库
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "w_id")
	private Warehouse warehouse;
	
	
	/**
	 * 送货人姓名
	 */
	@Column(name = "in_sender")
	private String sender;
	
	
	/**
	 * 送货人身份证
	 */
	@Column(name = "in_senderIdCard")
	private String senderIdCard;
	
	
	/**
	 * 存储地点
	 */
	@Column(name = "in_storage")
	private String storage;
	
	
	/**
	 * 是否封闭运输<br>
	 * 0:否<br>
	 * 1:是
	 */
	@Column(name = "in_closedTran")
	private Integer closedTran;
	
	
	/**
	 * 备注
	 */
	@Column(name = "in_desc")
	private String desc;
	
	
	/**
	 * 操作监管员
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "s_id")
	private Supervisor supervisor;
	
	
	/**
	 * 入库时间
	 */
	@Column(name = "in_date")
	private Date inDate = new Date();
	
	
	/**
	 * 扫描件状态<br>
	 * 0:未上传<br>
	 * 1:已上传
	 */
	@Column(name = "in_attachState")
	private Integer attachState = 0;
	
	
	/**
	 * 入库单URL
	 */
	@Column(name = "in_inRecordUrl")
	private String inRecordUrl;
	
	
	/**
	 * 检测结果评价单URL
	 */
	@Column(name = "in_checkRecordUrl")
	private String checkRecordUrl;
	
	
	/**
	 * 质物清单URL
	 */
	@Column(name = "in_pledgeUrl")
	private String pledgeUrl;
	
	
	/**
	 * 明细
	 */
	@OneToMany(mappedBy = "insRecord")
	private List<InsRecordDetail> insRecordDetails = new ArrayList<InsRecordDetail>();


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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


	public Warehouse getWarehouse() {
		return warehouse;
	}


	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}


	public String getSender() {
		return sender;
	}


	public void setSender(String sender) {
		this.sender = sender;
	}


	public String getSenderIdCard() {
		return senderIdCard;
	}


	public void setSenderIdCard(String senderIdCard) {
		this.senderIdCard = senderIdCard;
	}


	public String getStorage() {
		return storage;
	}


	public void setStorage(String storage) {
		this.storage = storage;
	}


	public Integer getClosedTran() {
		return closedTran;
	}


	public void setClosedTran(Integer closedTran) {
		this.closedTran = closedTran;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public Date getInDate() {
		return inDate;
	}


	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}


	public Integer getAttachState() {
		return attachState;
	}


	public void setAttachState(Integer attachState) {
		this.attachState = attachState;
	}


	public String getInRecordUrl() {
		return inRecordUrl;
	}


	public void setInRecordUrl(String inRecordUrl) {
		this.inRecordUrl = inRecordUrl;
	}


	public String getCheckRecordUrl() {
		return checkRecordUrl;
	}


	public void setCheckRecordUrl(String checkRecordUrl) {
		this.checkRecordUrl = checkRecordUrl;
	}


	public String getPledgeUrl() {
		return pledgeUrl;
	}


	public void setPledgeUrl(String pledgeUrl) {
		this.pledgeUrl = pledgeUrl;
	}


	public List<InsRecordDetail> getInsRecordDetails() {
		return insRecordDetails;
	}


	public void setInsRecordDetails(List<InsRecordDetail> insRecordDetails) {
		this.insRecordDetails = insRecordDetails;
	}


	public Supervisor getSupervisor() {
		return supervisor;
	}


	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}
	
}
