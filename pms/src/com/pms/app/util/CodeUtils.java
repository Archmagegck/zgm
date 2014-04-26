package com.pms.app.util;

import java.util.Arrays;

import org.joda.time.DateTime;

public class CodeUtils {

	public synchronized static String getInsRecordCode(String warehouseCode) {
		return warehouseCode + "RK" + new DateTime().toString("yyyyMMddHHmmss");
	}

	public synchronized static String getOutsRecordCode(String warehouseCode) {
		return warehouseCode + "CK" + new DateTime().toString("yyyyMMddHHmmss");
	}

	public synchronized static String getInventoryCode(String supervisionCustomerCode) {
		return supervisionCustomerCode + "KC" + new DateTime().toString("yyyyMMddHHmmss");
	}

	public synchronized static String getPledgeRecordCode(String warehouseCode) {
		return warehouseCode + "ZWQD" + new DateTime().toString("yyyyMMddHHmmss");
	}

	public synchronized static String getExtendedCheckCode(String warehouseCode) {
		return warehouseCode + "CQJC" + new DateTime().toString("yyyyMMddHHmmss");
	}

	public synchronized static String getInventoryCheckCode(String warehouseCode) {
		return warehouseCode + "PCJC" + new DateTime().toString("yyyyMMddHHmmss");
	}

	/**
	 * 随机指定范围内N个不重复的数
	 * 最简单最基本的方法
	 * 
	 * @param min 指定范围最小值
	 * @param max 指定范围最大值
	 * @param n 随机数个数
	 */
	public static int[] randomCommon(int min, int max, int n) {
		if (n > (max - min + 1) || max < min) {
			return null;
		}
		int[] result = new int[n];
		int count = 0;
		while (count < n) {
			int num = (int) (Math.random() * (max - min)) + min;
			boolean flag = true;
			for (int j = 0; j < n; j++) {
				if (num == result[j]) {
					flag = false;
					break;
				}
			}
			if (flag) {
				result[count] = num;
				count++;
			}
		}
		Arrays.sort(result); 
		return result;
	}
	
	public static void main(String[] args) {
		int[] reult1 = randomCommon(1,50,15);  
	    for (int i : reult1) {  
	        System.out.println(i);  
	    }  
	}
}
