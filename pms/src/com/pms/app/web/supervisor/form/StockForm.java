package com.pms.app.web.supervisor.form;

import java.util.ArrayList;
import java.util.List;

import com.pms.app.entity.vo.OutStock;

public class StockForm {
	
	private List<OutStock> outStocks = new ArrayList<OutStock>();

	public List<OutStock> getOutStocks() {
		return outStocks;
	}

	public void setOutStocks(List<OutStock> outStocks) {
		this.outStocks = outStocks;
	}

	
}
