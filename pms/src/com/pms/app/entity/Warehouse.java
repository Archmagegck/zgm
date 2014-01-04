package com.pms.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * 仓库
 * @author wangzz
 */
@Entity
@Table(name = "t_warehouse")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Warehouse {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "w_id")
	private String id;
	
	/**
	 * 仓库名称
	 */
	@Column(name = "w_name")
	private String name;
	
	
	/**
	 * 存储地址
	 */
	@Column(name = "w_address")
	private String address;
	
	
	/**
	 * 备注
	 */
	@Column(name = "w_desc")
	private String desc;
	
	
	/**
	 * 是否已分配
	 */
	@Column(name = "s_used")
	private Integer isUsed = 0;
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public Integer getIsUsed() {
		return isUsed;
	}


	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}

	
}
