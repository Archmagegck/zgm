package com.pms.app.web.manage.form;

import java.util.ArrayList;
import java.util.List;

import com.pms.app.entity.PurityPrice;

public class PurityPricesForm {
	
	private List<PurityPrice> purityPrices = new ArrayList<PurityPrice>();

	public List<PurityPrice> getPurityPrices() {
		return purityPrices;
	}

	public void setPurityPrices(List<PurityPrice> purityPrices) {
		this.purityPrices = purityPrices;
	}
}
