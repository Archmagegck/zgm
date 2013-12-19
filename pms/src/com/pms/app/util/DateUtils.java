package com.pms.app.util;

import java.util.Date;

import org.joda.time.DateTime;

public class DateUtils {

	public static String getNowDateStr(){
		DateTime nowDate = new DateTime();
		return nowDate.toString("yyyy-MM-dd HH:mm:ss");
	}
	
	public static Date dateToDayBegin(Date date){
		DateTime dt = new DateTime(date);
		DateTime beginDateTime = new DateTime(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth(), 0, 0, 0);
		return beginDateTime.toDate();
	}
	
	public static Date dateToDayEnd(Date date){
		DateTime dt = new DateTime(date);
		DateTime endDateTime = new DateTime(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth(), 23, 59, 59);
		return endDateTime.toDate();
	}
	
	public static String formatStr(Date date, String format){
		DateTime dt = new DateTime(date);
		return dt.toString(format);
	}
	
}
