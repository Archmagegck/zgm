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
 * 质押物成色
 * @author wangzz
 */
@Entity
@Table(name = "t_pledgePurity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PledgePurity {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "pp_id")
	private String id;
	
	/**
	 * 类型
	 * 1：合格
	 * 0：不合格
	 */
	private Integer type;
	
	/**
	 * 成色
	 */
	@Column(name = "pp_name")
	private String name;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	
}
