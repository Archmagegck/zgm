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
 * 入库检测记录明细
 * @author wangzz
 */
@Entity
@Table(name = "t_insCheckDetail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InsCheckDetail {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "icd_id")
	private String id;
	
	
	/**
	 * 所属入库检测记录
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "ic_id")
	private InsCheck InsCheck;
	
	
	/**
	 * 款式大类
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "sty_id")
	private Style style;
	
	
	/**
	 * 检测方法
	 */
	@Column(name = "icd_checkMethod")
	private CheckMethod checkMethod;
	
	
	/**
	 * 检测重量（kg）
	 */
	@Column(name = "icd_checkWeight")
	private Double checkWeight;
	
	
	/**
	 * 检测结果
	 */
	@Column(name = "icd_checkResult")
	private CheckResult checkResult;


	public void replace(InsCheckDetail detail) {
		this.checkMethod = detail.getCheckMethod();
		this.checkResult = detail.getCheckResult();
		this.checkWeight = detail.getCheckWeight();
		this.style = detail.getStyle();
	}
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public InsCheck getInsCheck() {
		return InsCheck;
	}


	public void setInsCheck(InsCheck insCheck) {
		InsCheck = insCheck;
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
