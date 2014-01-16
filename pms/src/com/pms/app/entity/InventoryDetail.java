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

import com.pms.app.entity.reference.AuditState;
import com.pms.app.entity.reference.CheckMethod;

/**
 * 盘存明细
 * @author wangzz
 */
@Entity
@Table(name = "t_inventoryDetail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InventoryDetail {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "id_id")
	private String id;
	
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
	 * 所属盘存
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "i_id")
	private Inventory inventory;
	
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
	@Column(name = "id_specWeight")
	private Double specWeight;
	
	/**
	 * 数量（件）
	 */
	@Column(name = "id_amount")
	private Double amount;
	
	/**
	 * 总重量
	 */
	@Column(name = "id_sumWeight")
	private Double sumWeight;
	
	/**
	 * 生产厂家
	 */
	@Column(name = "id_company")
	private String company = "";

	/**
	 * 是否封闭运输<br>
	 * 0:否<br>
	 * 1:是
	 */
	@Column(name = "id_closedTran")
	private Integer closedTran;
	
	/**
	 * 实际数量（件）
	 */
	@Column(name = "id_realAmount")
	private Double realAmount;
	
	/**
	 * 实际重量
	 */
	@Column(name = "id_realWeight")
	private Double realWeight;
	
	/**
	 * 实际成色
	 */
	@Column(name = "id_realPledgePurity")
	private String realPledgePurity;
	
	/**
	 * 检测方法
	 */
	@Column(name = "id_checkMethod")
	private CheckMethod checkMethod;
	
	/**
	 * 是否相符<br>
	 * 1:一致
	 * 0:不一致
	 */
	@Column(name = "id_equation")
	private Integer equation = 1;
	
	/**
	 * 状态
	 */
	private AuditState auditState = AuditState.Wait;
	
	public int getEqual() {
		if(realAmount.doubleValue() != amount.doubleValue()) {
			equation = 0;
		} else if (realWeight.doubleValue() != sumWeight.doubleValue()) {
			equation = 0;
		} else if (!realPledgePurity.equals(pledgePurity.getName())) {
			equation = 0;
		}
		return equation;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
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

	public Double getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(Double realAmount) {
		this.realAmount = realAmount;
	}

	public String getRealPledgePurity() {
		return realPledgePurity;
	}

	public void setRealPledgePurity(String realPledgePurity) {
		this.realPledgePurity = realPledgePurity;
	}

	public Integer getEquation() {
		return equation;
	}

	public void setEquation(Integer equation) {
		this.equation = equation;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getSumWeight() {
		return sumWeight;
	}

	public void setSumWeight(Double sumWeight) {
		this.sumWeight = sumWeight;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getClosedTran() {
		return closedTran;
	}

	public void setClosedTran(Integer closedTran) {
		this.closedTran = closedTran;
	}

	public Double getRealWeight() {
		return realWeight;
	}

	public void setRealWeight(Double realWeight) {
		this.realWeight = realWeight;
	}

	public CheckMethod getCheckMethod() {
		return checkMethod;
	}

	public void setCheckMethod(CheckMethod checkMethod) {
		this.checkMethod = checkMethod;
	}

	public AuditState getAuditState() {
		return auditState;
	}

	public void setAuditState(AuditState auditState) {
		this.auditState = auditState;
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
