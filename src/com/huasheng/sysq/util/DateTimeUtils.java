package com.huasheng.sysq.util;

public class DateTimeUtils {
	
	public static String showDoubleNumber(int singleNumber){
		if(singleNumber < 10){
			return "0" + singleNumber;
		}
		return "" + singleNumber;
	}
}
