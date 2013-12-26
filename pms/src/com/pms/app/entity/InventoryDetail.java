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
	 * 所属盘存
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "i_id")
	private Inventory inventory;
	
	/**
	 * 款式大类
	 */
	@Column(name = "id_style")
	private String style;
	
	/**
	 * 成色
	 */
	@Column(name = "id_pledgePurity")
	private String pledgePurity;
	
	/**
	 * 规格重量（kg/件）
	 */
	@Column(name = "id_specWeight")
	private Double specWeight;
	
	/**
	 * 实际数量（件）
	 */
	@Column(name = "id_amount")
	private Double realAmount;
	
	/**
	 * 实际成色
	 */
	@Column(name = "id_realPledgePurity")
	private String realPledgePurity;
	
	/**
	 * 是否相符<br>
	 * 1:一致
	 * 0:不一致
	 */
	@Column(name = "i_equation")
	private Integer equation = 1;

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

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getPledgePurity() {
		return pledgePurity;
	}

	public void setPledgePurity(String pledgePurity) {
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
	
	
	
	
}
