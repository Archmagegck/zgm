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
	 * 成色
	 */
	@Column(name = "pp_purity")
	private String purity;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPurity() {
		return purity;
	}

	public void setPurity(String purity) {
		this.purity = purity;
	}
	
	
}
