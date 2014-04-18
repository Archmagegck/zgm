package com.pms.app.web.supervisor.form;

import java.util.ArrayList;
import java.util.List;

import com.pms.app.entity.IniRecord;

public class IniRecordForm {
	
	private List<IniRecord> iniRecords = new ArrayList<IniRecord>();

	public List<IniRecord> getIniRecords() {
		return iniRecords;
	}

	public void setIniRecords(List<IniRecord> iniRecords) {
		this.iniRecords = iniRecords;
	}
	
}
