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

/**
 * 超期检测记录明细
 * @author wangzz
 */
@Entity
@Table(name = "t_extendedCheckDetail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ExtendedCheckDetail {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "ecd_id")
	private String id;
	
	
	/**
	 * 所属超期检测记录
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "ec_id")
	private ExtendedCheck extendedCheck;
	
	
	/**
	 * 款式大类
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "sty_id")
	private Style style;
	
	
	/**
	 * 检测方法
	 */
	@Column(name = "ecd_checkMethod")
	private CheckMethod checkMethod;
	
	
	/**
	 * 检测重量（kg）
	 */
	@Column(name = "ecd_checkWeight")
	private Double checkWeight;
	
	
	/**
	 * 检测结果
	 */
	@Column(name = "ecd_checkResult")
	private CheckMethod checkResult;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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


	public CheckMethod getCheckResult() {
		return checkResult;
	}


	public void setCheckResult(CheckMethod checkResult) {
		this.checkResult = checkResult;
	}


	public ExtendedCheck getExtendedCheck() {
		return extendedCheck;
	}


	public void setExtendedCheck(ExtendedCheck extendedCheck) {
		this.extendedCheck = extendedCheck;
	}



	
}
