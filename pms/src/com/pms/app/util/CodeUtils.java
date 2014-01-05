package com.pms.app.util;

import org.joda.time.DateTime;

public class CodeUtils {
	
	public synchronized static String getInsRecordCode(String supervisionCustomerCode){
		return supervisionCustomerCode + "RK" + new DateTime().toString("yyyyMMddHHmmss");
	}
	
	public synchronized static String getOutsRecordCode(String supervisionCustomerCode){
		return supervisionCustomerCode + "CK" + new DateTime().toString("yyyyMMddHHmmss");
	}
}
