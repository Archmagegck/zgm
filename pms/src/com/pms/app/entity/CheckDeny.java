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

import com.pms.app.entity.reference.CheckMethod;

/**
 * 检测拒绝记录
 * @author wangzz
 */
@Entity
@Table(name = "t_checkDeny")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CheckDeny {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "cd_id")
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
	@Column(name = "cd_specWeight")
	private Double specWeight;
	
	
	/**
	 * 数量（件）
	 */
	@Column(name = "cd_amount")
	private Double amount;
	
	
	/**
	 * 总重量
	 */
	@Column(name = "cd_sumWeight")
	private String sumWeight;
	
	
	/**
	 * 生产厂家
	 */
	@Column(name = "cd_company")
	private String company;
	
	
	/**
	 * 检测成色
	 */
	@Column(name = "cd_checkPurity")
	private String checkPurity;
	
	
	/**
	 * 检测规格重量（kg/件）
	 */
	@Column(name = "cd_checkSpecWeight")
	private Double checkSpecWeight;
	
	
	/**
	 * 检测数量（件）
	 */
	@Column(name = "cd_checkAmount")
	private Double checkAmount;
	
	
	/**
	 * 检测重量（kg）
	 */
	@Column(name = "cd_checkWeight")
	private Double checkWeight;
	
	
	/**
	 * 检测方法
	 */
	@Column(name = "cd_checkMethod")
	private CheckMethod checkMethod;
	
	
	/**
	 * 送货人姓名
	 */
	@Column(name = "cd_sender")
	private String sender;
	
	
	/**
	 * 送货人身份证
	 */
	@Column(name = "cd_senderIdCard")
	private String senderIdCard;
	
	
	/**
	 * 存储地点
	 */
	@Column(name = "cd_storage")
	private String storage;
	
	
	/**
	 * 是否封闭运输<br>
	 * 0:否<br>
	 * 1:是
	 */
	@Column(name = "cd_closedTran")
	private Integer closedTran;
	
	
	/**
	 * 操作监管员
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "s_id")
	private Supervisor supervisor;
	
	
	/**
	 * 备注
	 */
	@Column(name = "cd_desc")
	private String desc;
	
	
	/**
	 * 日期
	 */
	@Column(name = "cd_date")
	private Date date = new Date();
	

	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
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


	public String getSumWeight() {
		return sumWeight;
	}


	public void setSumWeight(String sumWeight) {
		this.sumWeight = sumWeight;
	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}


	public String getCheckPurity() {
		return checkPurity;
	}


	public void setCheckPurity(String checkPurity) {
		this.checkPurity = checkPurity;
	}


	public Double getCheckSpecWeight() {
		return checkSpecWeight;
	}


	public void setCheckSpecWeight(Double checkSpecWeight) {
		this.checkSpecWeight = checkSpecWeight;
	}


	public Double getCheckAmount() {
		return checkAmount;
	}


	public void setCheckAmount(Double checkAmount) {
		this.checkAmount = checkAmount;
	}


	public Double getCheckWeight() {
		return checkWeight;
	}


	public void setCheckWeight(Double checkWeight) {
		this.checkWeight = checkWeight;
	}


	public CheckMethod getCheckMethod() {
		return checkMethod;
	}


	public void setCheckMethod(CheckMethod checkMethod) {
		this.checkMethod = checkMethod;
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


	public Style getStyle() {
		return style;
	}


	public void setStyle(Style style) {
		this.style = style;
	}


	public Supervisor getSupervisor() {
		return supervisor;
	}


	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}
	
}
