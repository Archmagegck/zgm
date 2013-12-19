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
 * 用户.
 */
@Entity
@Table(name = "r_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User {

	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "u_id")
	private String id;

	@Column(name = "u_loginName")
	private String loginName;// 登录名

	@Column(name = "user_loginPwd")
	private String loginPwd;// 密码

	@Column(name = "u_realName")
	private String realName;// 真实姓名

	@Column(name = "u_phone")
	private String phone;// 手机号码

	@Column(name = "u_email")
	private String email;// 邮箱

	@Column(name = "u_address")
	private String address;// 地址

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}