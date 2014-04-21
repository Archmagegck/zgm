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
 * 盘存记录
 * @author wangzz
 */
@Entity
@Table(name = "t_inventory")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Inventory {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "i_id")
	private String id;
	
	/**
	 * 所属仓库
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "w_id")
	private Warehouse warehouse;
	
	/**
	 * 盘存编号
	 */
	@Column(name = "i_code")
	private String code;
	
	/**
	 * 监管员姓名
	 */
	@Column(name = "i_supName")
	private String supName;
	
	/**
	 * 盘存日期
	 */
	@Column(name = "i_invDate")
	private Date date = new Date();
	
	/**
	 * 盘存总重量
	 */
	@Column(name = "i_sumWeight")
	private Double sumWeight;
	
	/**
	 * 盘存明细
	 */
	@OneToMany(mappedBy = "inventory")
	private List<InventoryDetail> inventoryDetails = new ArrayList<InventoryDetail>();
	
	public String getDateStr() {
		return new DateTime(date).toString("yyyy-MM-dd HH:mm:ss");
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

	public String getSupName() {
		return supName;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<InventoryDetail> getInventoryDetails() {
		return inventoryDetails;
	}

	public void setInventoryDetails(List<InventoryDetail> inventoryDetails) {
		this.inventoryDetails = inventoryDetails;
	}

	public Double getSumWeight() {
		return sumWeight;
	}

	public void setSumWeight(Double sumWeight) {
		this.sumWeight = sumWeight;
	}
	

}
