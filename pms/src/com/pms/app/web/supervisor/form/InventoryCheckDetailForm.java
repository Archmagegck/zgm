package com.pms.app.web.supervisor.form;

import java.util.ArrayList;
import java.util.List;

import com.pms.app.entity.InventoryCheckDetail;

public class InventoryCheckDetailForm {
	
	private List<InventoryCheckDetail> inventoryCheckDetails = new ArrayList<InventoryCheckDetail>();

	public List<InventoryCheckDetail> getInventoryCheckDetails() {
		return inventoryCheckDetails;
	}

	public void setInventoryCheckDetails(List<InventoryCheckDetail> inventoryCheckDetails) {
		this.inventoryCheckDetails = inventoryCheckDetails;
	}
	
}
