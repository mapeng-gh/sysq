package com.huasheng.sysq.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
	
	public static String showDoubleNumber(int singleNumber){
		if(singleNumber < 10){
			return "0" + singleNumber;
		}
		return "" + singleNumber;
	}
	
	/**
	 * 获取当前时间（yyyy-MM-dd hh:mm）
	 * @return
	 */
	public static String getCurTime(){
		Date curDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return dateFormat.format(curDate);
	}
	
	/**
	 * 获取当前日期（yyyy-MM-dd）
	 * @return
	 */
	public static String getCurDate(){
		Date curDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(curDate);
	}
}
