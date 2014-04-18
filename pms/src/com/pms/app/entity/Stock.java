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

/**
 * 库存
 * @author wangzz
 */
@Entity
@Table(name = "t_stock")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Stock {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "uuid")
	@Column(name = "s_id")
	private String id;
	
	/**
	 * 所属仓库
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "w_id")
	private Warehouse warehouse;
	
	/**
	 * 款式大类
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "sty_id")
	private Style style;
	
	/**
	 * 成色
	 */
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "pp_id")
	private PledgePurity pledgePurity;
	
	/**
	 * 总重量
	 */
	@Column(name = "s_sumWeight")
	private Double sumWeight;
	
	public Stock() {}
	
	//直接入库的构造方法
	public Stock(Warehouse warehouse, InsRecordDetail insRecordDetail) {
		this.warehouse = warehouse;
		this.style = insRecordDetail.getStyle();
		this.pledgePurity = insRecordDetail.getPledgePurity();
		this.sumWeight = insRecordDetail.getWeight();
	}
	
	public Stock(TransitGoods transitGoods) {
		this.warehouse = transitGoods.getWarehouse();
		this.style = transitGoods.getStyle();
		this.pledgePurity = transitGoods.getPledgePurity();
		this.sumWeight = transitGoods.getSumWeight();
	}
	
	/**
	 * 直接入库
	 * @param weight 入库重量
	 */
	public void add(double weight) {
		this.sumWeight += weight;
	}
	
	public String getKey() {
		StringBuffer sb = new StringBuffer("{");
		sb.append("\"warehouse\":\"").append(warehouse.getId()).append("\",");
		sb.append("\"style\":\"").append(style.getId()).append("\",");
		sb.append("\"pledgePurity\":\"").append(pledgePurity.getType()).append("\",");
		sb.append("}");
		return sb.toString();
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

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	public PledgePurity getPledgePurity() {
		return pledgePurity;
	}

	public void setPledgePurity(PledgePurity pledgePurity) {
		this.pledgePurity = pledgePurity;
	}

	public Double getSumWeight() {
		return sumWeight;
	}

	public void setSumWeight(Double sumWeight) {
		this.sumWeight = sumWeight;
	}
	
}	
