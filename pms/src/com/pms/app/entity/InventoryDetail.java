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
import org.joda.time.DateTime;

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
	 * 所属仓库
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "w_id")
	private Warehouse warehouse;
	
	/**
	 * 所属盘存
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "i_id")
	private Inventory inventory;
	
	/**
	 * 托盘号
	 */
	@Column(name = "id_trayNo")
	private int trayNo;
	
	/**
	 * 款式大类
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "sty_id")
	private Style style;
	
	/**
	 * 重量
	 */
	@Column(name = "id_weight")
	private Double weight;
	
	/**
	 * 盘存日期
	 */
	@Column(name = "id_invDate")
	private Date date = new Date();
	
	
	public String getDateStr() {
		return new DateTime(date).toString("yyyy-MM-dd");
	}
	
	public void update(InventoryDetail inventoryDetail) {
		this.trayNo = inventoryDetail.getTrayNo();
		this.style = inventoryDetail.getStyle();
		this.weight = inventoryDetail.getWeight();
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


	public Inventory getInventory() {
		return inventory;
	}


	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}


	public int getTrayNo() {
		return trayNo;
	}


	public void setTrayNo(int trayNo) {
		this.trayNo = trayNo;
	}


	public Style getStyle() {
		return style;
	}


	public void setStyle(Style style) {
		this.style = style;
	}


	public Double getWeight() {
		return weight;
	}


	public void setWeight(Double weight) {
		this.weight = weight;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}

	
	
	
}
