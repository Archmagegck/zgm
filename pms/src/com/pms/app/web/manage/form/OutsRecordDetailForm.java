package com.pms.app.web.manage.form;

import java.util.ArrayList;
import java.util.List;

import com.pms.app.entity.OutsRecordDetail;

public class OutsRecordDetailForm {
	
	private List<OutsRecordDetail> outsRecordDetails = new ArrayList<OutsRecordDetail>();

	public List<OutsRecordDetail> getOutsRecordDetails() {
		return outsRecordDetails;
	}

	public void setOutsRecordDetails(List<OutsRecordDetail> outsRecordDetails) {
		this.outsRecordDetails = outsRecordDetails;
	}
	
}
