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
 * 超期检测记录
 * @author wangzz
 */
@Entity
@Table(name = "t_extendedCheck")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ExtendedCheck {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "ec_id")
	private String id;
	
	/**
	 * 所属仓库
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "w_id")
	private Warehouse warehouse;
	
	/**
	 * 超期检测单号
	 */
	@Column(name = "ec_code")
	private String code;
	
	/**
	 * 超期检测总重量
	 */
	@Column(name = "ec_sumWeight")
	private Double sumWeight;
	
	/**
	 * 光谱法检测重量
	 */
	@Column(name = "ec_gpWeight")
	private Double gpWeight;
	
	/**
	 * 溶金法检测重量
	 */
	@Column(name = "ec_rjWeight")
	private Double rjWeight;
	
	
	/**
	 * 操作监管员
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "s_id")
	private Supervisor supervisor;
	
	
	/**
	 * 检测时间
	 */
	@Column(name = "ec_date")
	private Date date = new Date();
	
	
	/**
	 * 明细
	 */
	@OneToMany(mappedBy = "extendedCheck")
	private List<ExtendedCheckDetail> extendedCheckDetails = new ArrayList<ExtendedCheckDetail>();


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


	public Double getGpWeight() {
		return gpWeight;
	}


	public void setGpWeight(Double gpWeight) {
		this.gpWeight = gpWeight;
	}


	public Double getRjWeight() {
		return rjWeight;
	}


	public void setRjWeight(Double rjWeight) {
		this.rjWeight = rjWeight;
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


	public List<ExtendedCheckDetail> getExtendedCheckDetails() {
		return extendedCheckDetails;
	}


	public void setExtendedCheckDetails(List<ExtendedCheckDetail> extendedCheckDetails) {
		this.extendedCheckDetails = extendedCheckDetails;
	}


	
}
