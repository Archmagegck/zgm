package com.pms.app.entity.vo;

import com.pms.app.entity.Delegator;
import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.Supervisor;
import com.pms.app.entity.Warehouse;

/**
 * 解压管理表
 * @author gck
 *
 */
public class RemovePledge {
	
	private Delegator delegator;
	
	private SupervisionCustomer supervisionCustomer;
	
	private Warehouse warehouse;
	
	private Supervisor supervisor;

	public Delegator getDelegator() {
		return delegator;
	}

	public void setDelegator(Delegator delegator) {
		this.delegator = delegator;
	}

	public SupervisionCustomer getSupervisionCustomer() {
		return supervisionCustomer;
	}

	public void setSupervisionCustomer(SupervisionCustomer supervisionCustomer) {
		this.supervisionCustomer = supervisionCustomer;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	public Supervisor getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}
	
	
}
