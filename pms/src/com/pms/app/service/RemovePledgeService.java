package com.pms.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pms.app.entity.Delegator;
import com.pms.app.entity.SupervisionCustomer;
import com.pms.app.entity.Supervisor;
import com.pms.app.entity.Warehouse;
import com.pms.app.entity.vo.RemovePledge;

@Service
public class RemovePledgeService {
	
	@Autowired DelegatorService delegatorService;
	@Autowired SupervisionCustomerService supervisionCustomerService;
	@Autowired SupervisorService supervisorService;
	
	public List<RemovePledge> findListBySupervisionCustomerId(Pageable pageable, String supervisionCustomerId){
		
		List<RemovePledge> removePledgeList= new ArrayList<RemovePledge>();	
		
		SupervisionCustomer supervisionCustomer = supervisionCustomerService.findById(supervisionCustomerId);
		Warehouse warehouse = supervisionCustomer.getWarehouse();
		Supervisor supervisor = supervisorService.findOneByWarehouseId(warehouse.getId());
		Delegator delegator = supervisionCustomer.getDelegator();

		RemovePledge removePledge = new RemovePledge();
		removePledge.setSupervisionCustomer(supervisionCustomer);
		removePledge.setSupervisor(supervisor);
		removePledge.setDelegator(delegator);
		removePledge.setWarehouse(warehouse);
		
		removePledgeList.add(removePledge);
		
		return removePledgeList;
	}
	
	public List<RemovePledge> findListByDelegatorId(Pageable pageable, String delegatorId){
		List<RemovePledge> removePledgeList= new ArrayList<RemovePledge>();	
		
		Delegator delegator = delegatorService.findById(delegatorId);
		List<SupervisionCustomer> supervisionCustomerList= supervisionCustomerService.findListByDelegatorId(delegatorId);
		for(SupervisionCustomer supervisionCustomer : supervisionCustomerList){
			RemovePledge removePledge = new RemovePledge();
			
			removePledge.setSupervisionCustomer(supervisionCustomer);
			removePledge.setDelegator(delegator);
			
			Warehouse warehouse = supervisionCustomer.getWarehouse();
			removePledge.setWarehouse(warehouse);
			
			Supervisor supervisor = supervisorService.findOneByWarehouseId(warehouse.getId());
			removePledge.setSupervisor(supervisor);
			
			removePledgeList.add(removePledge);
		}
		
		return removePledgeList;
	}
	
	public List<RemovePledge> findAll(){
		List<RemovePledge> removePledgeList= new ArrayList<RemovePledge>();	
		
		List<SupervisionCustomer> supervisionCustomerList=supervisionCustomerService.findAll();
		for(SupervisionCustomer supervisionCustomer : supervisionCustomerList){
			RemovePledge removePledge = new RemovePledge();
			
			removePledge.setSupervisionCustomer(supervisionCustomer);
			
			Delegator delegator = supervisionCustomer.getDelegator();
			removePledge.setDelegator(delegator);
			
			Warehouse warehouse = supervisionCustomer.getWarehouse();
			removePledge.setWarehouse(warehouse);
			
			Supervisor supervisor = supervisorService.findOneByWarehouseId(warehouse.getId());
			removePledge.setSupervisor(supervisor);
			
			removePledgeList.add(removePledge);
		}
		return removePledgeList;
	}
	
}
