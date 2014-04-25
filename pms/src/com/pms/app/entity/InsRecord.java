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
	 * 入库单加密号
	 */
	@Column(name = "in_secretCode")
	private String secretCode;
	
	
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
	 * 操作监管员
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "s_id")
	private Supervisor supervisor;
	
	
	/**
	 * 入库时间
	 */
	@Column(name = "in_date")
	private Date date = new Date();
	
	
	/**
	 * 扫描件<br>
	 * 保存上传地址
	 */
	@Column(name = "in_attach")
	private String attach;
	
	
	/**
	 * 明细
	 */
	@OneToMany(mappedBy = "insRecord")
	private List<InsRecordDetail> insRecordDetails = new ArrayList<InsRecordDetail>();

	/**
	 * 监管客户
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "sc_id")
	private SupervisionCustomer supervisionCustomer;
	
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


	public String getSecretCode() {
		return secretCode;
	}


	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
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


	public Supervisor getSupervisor() {
		return supervisor;
	}


	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}

	
	public String getAttach() {
		return attach;
	}


	public void setAttach(String attach) {
		this.attach = attach;
	}


	public List<InsRecordDetail> getInsRecordDetails() {
		return insRecordDetails;
	}


	public void setInsRecordDetails(List<InsRecordDetail> insRecordDetails) {
		this.insRecordDetails = insRecordDetails;
	}


	public SupervisionCustomer getSupervisionCustomer() {
		return supervisionCustomer;
	}


	public void setSupervisionCustomer(SupervisionCustomer supervisionCustomer) {
		this.supervisionCustomer = supervisionCustomer;
	}

	
}
