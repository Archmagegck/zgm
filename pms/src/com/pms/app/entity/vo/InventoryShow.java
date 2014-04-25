package com.pms.app.entity.vo;

import com.pms.app.entity.Warehouse;

public class InventoryShow {
	private Warehouse warehouse;
	
	private double inventoryWeight;
	
	private double inventoryPrice;

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public double getInventoryWeight() {
		return inventoryWeight;
	}

	public void setInventoryWeight(double inventoryWeight) {
		this.inventoryWeight = inventoryWeight;
	}

	public double getInventoryPrice() {
		return inventoryPrice;
	}

	public void setInventoryPrice(double inventoryPrice) {
		this.inventoryPrice = inventoryPrice;
	}
	
	
}
