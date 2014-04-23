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
 * 盘存检测
 * @author wangzz
 */
@Entity
@Table(name = "t_inventoryCheck")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InventoryCheck {
	
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
	 * 检测单号
	 */
	@Column(name = "ic_code")
	private String code;
	

	/**
	 * 检测日期
	 */
	@Column(name = "ic_date")
	private Date date = new Date();
	
	
	/**
	 * 检测状态
	 * 1:已登记
	 * 0:未登记
	 */
	@Column(name = "ic_state")
	private Integer state = 0;
	
	/**
	 * 盘存明细
	 */
	@OneToMany(mappedBy = "inventoryCheck")
	private List<InventoryCheckDetail> inventoryCheckDetails = new ArrayList<InventoryCheckDetail>();
	
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


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
		this.state = state;
	}


	public List<InventoryCheckDetail> getInventoryCheckDetails() {
		return inventoryCheckDetails;
	}


	public void setInventoryCheckDetails(List<InventoryCheckDetail> inventoryCheckDetails) {
		this.inventoryCheckDetails = inventoryCheckDetails;
	}
	
	
	
}
