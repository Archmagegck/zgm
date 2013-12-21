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
 * 委托方
 * @author wangzz
 */
@Entity
@Table(name = "t_delegator")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Delegator {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "d_id")
	private String id;
	
	/**
	 * 委托方编号
	 */
	@Column(name = "d_code")
	private String code;
	
	/**
	 * 委托方名称
	 */
	@Column(name = "d_name")
	private String name;
	
	/**
	 * 用户名
	 */
	@Column(name = "d_username")
	private String username;

	/**
	 * 登陆密码
	 */
	@Column(name = "d_password")
	private String password;
	
	/**
	 * 地址
	 */
	@Column(name = "d_address")
	private String address;
	
	/**
	 * 联系人
	 */
	@Column(name = "d_contact")
	private String contact;
	
	/**
	 * 联系电话
	 */
	@Column(name = "d_phone")
	private String phone;
	
	/**
	 * 邮箱
	 */
	@Column(name = "d_email")
	private String email;
	
	/**
	 * 开票信息
	 */
	@Column(name = "d_invoices")
	private String invoices;
	
	/**
	 * 备注
	 */
	@Column(name = "d_desc")
	private String desc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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

	public String getInvoices() {
		return invoices;
	}

	public void setInvoices(String invoices) {
		this.invoices = invoices;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
