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

import com.pms.app.entity.reference.Gender;

/**
 * 监管员
 * @author wangzz
 */
@Entity
@Table(name = "t_supervisor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Supervisor {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "s_id")
	private String id;
	
	/**
	 * 编号
	 */
	@Column(name = "s_code")
	private String code;
	
	/**
	 * 姓名
	 */
	@Column(name = "s_name")
	private String name;
	
	/**
	 * 用户名
	 */
	@Column(name = "s_username")
	private String username;

	/**
	 * 登陆密码
	 */
	@Column(name = "s_password")
	private String password;
	
	/**
	 * 性别
	 */
	@Column(name = "s_gender")
	private Gender gender;
	
	/**
	 * 身份证
	 */
	@Column(name = "s_idcard") 
	private String idcard; 
	
	/**
	 * 住址
	 */
	@Column(name = "s_address")
	private String address;
	
	/**
	 * 电话
	 */
	@Column(name = "s_phone")
	private String phone;
	
	/**
	 * 邮箱
	 */
	@Column(name = "s_email")
	private String email;
	
	/**
	 * 所属辖区
	 */
	@Column(name = "s_area")
	private String area;
	
	/**
	 * 备注
	 */
	@Column(name = "s_desc")
	private String desc;
	
	/**
	 * 出货重量权限(kg)
	 */
	@Column(name = "s_shippingWeight")
	private Double shippingWeight;
	
	/**
	 * 是否已分配<br>
	 * 1:已分配
	 * 0:未分配
	 */
	@Column(name = "s_used")
	private Integer isUsed = 0;

	
	/**
	 * 监管客户
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "sc_id")
	private SupervisionCustomer supervisionCustomer;
	
	
	public SupervisionCustomer getSupervisionCustomer() {
		return supervisionCustomer;
	}

	public void setSupervisionCustomer(SupervisionCustomer supervisionCustomer) {
		this.supervisionCustomer = supervisionCustomer;
	}
	
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Double getShippingWeight() {
		return shippingWeight;
	}

	public void setShippingWeight(Double shippingWeight) {
		this.shippingWeight = shippingWeight;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public Integer getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}
	
}
