package com.pms.app.entity.vo;

import java.util.Date;

import org.joda.time.DateTime;

import com.pms.app.entity.InsRecordDetail;
import com.pms.app.entity.OutsRecordDetail;

/**
 * 出入库
 * @author wangzz
 */
public class InOutsRecord implements Comparable<InOutsRecord> {
	
	private Date date;
	
	private String method;//操作类型
	
	private String style;
	
	private String pledgePurity;
	
	private String specWeight;
	
	private Double amount;
	
	private Double sumWeight;
	
	public InOutsRecord() {}
	
	public InOutsRecord(InsRecordDetail detail) {
		this.method = "入库";
		this.date = detail.getDate();
		this.style = detail.getStyle().getName();
		this.pledgePurity = detail.getPledgePurity().getName();
		this.specWeight = detail.getSpecWeight().toString();
		this.amount = detail.getAmount();
		this.sumWeight = detail.getSumWeight();
	}
	
	public InOutsRecord(OutsRecordDetail detail) {
		this.method = "出库";
		this.date = detail.getDate();
		this.style = detail.getStyle().getName();
		this.pledgePurity = detail.getPledgePurity().getName();
		this.specWeight = detail.getSpecWeight().toString();
		this.amount = detail.getAmount();
		this.sumWeight = detail.getSumWeight();
	}
	
	@Override
	public int compareTo(InOutsRecord obj) {
		if (null == obj) return 1;   
		else {   
			return this.date.compareTo(obj.date);
		}   
	}
	
	public String getDateStr() {
		return new DateTime(date).toString("yyyy-MM-dd");
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getPledgePurity() {
		return pledgePurity;
	}

	public void setPledgePurity(String pledgePurity) {
		this.pledgePurity = pledgePurity;
	}

	public String getSpecWeight() {
		return specWeight;
	}

	public void setSpecWeight(String specWeight) {
		this.specWeight = specWeight;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getSumWeight() {
		return sumWeight;
	}

	public void setSumWeight(Double sumWeight) {
		this.sumWeight = sumWeight;
	}

	
}
