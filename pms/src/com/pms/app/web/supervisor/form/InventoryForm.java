package com.pms.app.web.supervisor.form;

import java.util.ArrayList;
import java.util.List;

import com.pms.app.entity.InventoryDetail;


public class InventoryForm {
	
	private List<InventoryDetail> inventoryDetails = new ArrayList<InventoryDetail>();

	public List<InventoryDetail> getInventoryDetails() {
		return inventoryDetails;
	}

	public void setInventoryDetails(List<InventoryDetail> inventoryDetails) {
		this.inventoryDetails = inventoryDetails;
	}

	
}
