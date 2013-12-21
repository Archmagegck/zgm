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
 * 管理员(监管经理)
 * @author wangzz
 */
@Entity
@Table(name = "t_admin")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Admin {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "ad_id")
	private String id;
	
	/**
	 * 用户名
	 */
	@Column(name = "ad_username")
	private String username;

	/**
	 * 登陆密码
	 */
	@Column(name = "ad_password")
	private String password;
	
	/**
	 * 真实姓名
	 */
	@Column(name = "ad_name")
	private String name;
	
	
	/**
	 * 联系电话
	 */
	@Column(name = "ad_phone")
	private String phone;
	
	/**
	 * 邮箱
	 */
	@Column(name = "ad_email")
	private String email;
	
	/**
	 * 备注
	 */
	@Column(name = "ad_desc")
	private String desc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
