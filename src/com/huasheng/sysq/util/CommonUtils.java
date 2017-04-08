package com.huasheng.sysq.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

	/**
	 * 转化双位数字
	 * @param singleNumber
	 * @return
	 */
	public static String showDoubleNumber(int singleNumber){
		if(singleNumber < 10){
			return "0" + singleNumber;
		}
		return "" + singleNumber;
	}
	
	/**
	 * 日期和时间格式相关函数
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
	
	/**
	 * 正则匹配
	 * @param regex
	 * @param str
	 * @return
	 */
	public static boolean test(String regex,String str){
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	/**
	 * 读取属性文件
	 * @param propsFile
	 * @param charset
	 * @return
	 */
	public static Map<String,String> readProperties(File propsFile,String charset){
		Properties prop = new Properties();
		Reader reader = null;
		Map<String,String> map = new HashMap<String,String>();
		try {
			reader = new InputStreamReader(new FileInputStream(propsFile),charset);
			prop.load(reader);
			Enumeration<Object> keys = prop.keys();
			while(keys.hasMoreElements()){
				String key = keys.nextElement().toString();
				map.put(key, prop.getProperty(key));
			}
			return map;
		} catch (Exception e) {
			throw new RuntimeException("读取配置文件失败："+propsFile.getName(),e);
		}finally{
			if(reader != null){
				try{
					reader.close();
				}catch(Exception e){
					
				}
			}
		}
	}
}
