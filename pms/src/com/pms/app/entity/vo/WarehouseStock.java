package com.pms.app.entity.vo;

public class WarehouseStock {

	private String styleName;
	
	private String pledgePurityName;
	
	private Double sumWeight;
	
	private String storage;

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public Double getSumWeight() {
		return sumWeight;
	}

	public void setSumWeight(Double sumWeight) {
		this.sumWeight = sumWeight;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public String getPledgePurityName() {
		return pledgePurityName;
	}

	public void setPledgePurityName(String pledgePurityName) {
		this.pledgePurityName = pledgePurityName;
	}
	
	
}
