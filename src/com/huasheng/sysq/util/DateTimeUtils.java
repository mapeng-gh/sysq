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
	 * ��ȡ��ǰʱ�䣨yyyy-MM-dd hh:mm��
	 * @return
	 */
	public static String getCurTime(){
		Date curDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return dateFormat.format(curDate);
	}
	
	/**
	 * ��ȡ��ǰ���ڣ�yyyy-MM-dd��
	 * @return
	 */
	public static String getCurDate(){
		Date curDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(curDate);
	}
}
