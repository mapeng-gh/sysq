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
	 * 获取自定义格式当前时间
	 * @param format
	 * @return
	 */
	public static String getCustomDateTime(String format){
		Date curDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(curDate);
	}
	
	public static String getCurDateTime(){
		return getCustomDateTime("yyyy-MM-dd HH:mm:ss");
	}
	
	public static String getCurDate(){
		return getCustomDateTime("yyyy-MM-dd");
	}
	
	public static String getCurTime(){
		return getCustomDateTime("HH:mm:ss");
	}
}
