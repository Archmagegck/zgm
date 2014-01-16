package com.pms.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * Au9995每日价格
 * @author wangzz
 */
@Entity
@Table(name = "t_au9995Price")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Au9995Price {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "au_id")
	private String id;
	
	/**
	 * 日期
	 */
	@Column(name = "au_date")
	@Temporal(TemporalType.DATE)
	private Date date = new Date();
	
	/**
	 * 价格
	 */
	@Column(name = "au_price")
	private Double price;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	
}
