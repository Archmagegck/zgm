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

import com.pms.app.entity.reference.CheckMethod;
import com.pms.app.entity.reference.CheckResult;

/**
 * 初始状态检测记录
 * @author wangzz
 */
@Entity
@Table(name = "t_iniCheck")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IniCheck {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "ic_id")
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
	 * 检测方法
	 */
	@Column(name = "ic_checkMethod")
	private CheckMethod checkMethod;
	
	
	/**
	 * 检测重量（kg）
	 */
	@Column(name = "ic_checkWeight")
	private Double checkWeight;
	
	
	/**
	 * 检测结果
	 */
	@Column(name = "ic_checkResult")
	private CheckResult checkResult;


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


	public Style getStyle() {
		return style;
	}


	public void setStyle(Style style) {
		this.style = style;
	}


	public CheckMethod getCheckMethod() {
		return checkMethod;
	}


	public void setCheckMethod(CheckMethod checkMethod) {
		this.checkMethod = checkMethod;
	}


	public Double getCheckWeight() {
		return checkWeight;
	}


	public void setCheckWeight(Double checkWeight) {
		this.checkWeight = checkWeight;
	}


	public CheckResult getCheckResult() {
		return checkResult;
	}


	public void setCheckResult(CheckResult checkResult) {
		this.checkResult = checkResult;
	}


	
	
}
