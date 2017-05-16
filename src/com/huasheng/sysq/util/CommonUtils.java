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

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;

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
	
	public static int getCurrentSeconds(){
		return (int)(System.currentTimeMillis()/1000);
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
			return null;
		}finally{
			if(reader != null){
				try{
					reader.close();
				}catch(Exception e){
					
				}
			}
		}
	}
	
	/**
	 * 获取Mac地址
	 * @param context
	 * @return
	 */
	public static String getMacAddress(Context context){
		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE); 
        WifiInfo info = wifi.getConnectionInfo(); 
        return info.getMacAddress(); 
	}
	
	/**
	 * 身份证校验（18位）
	 * @param identityCard
	 * @return
	 */
	public static boolean checkIdentityCard(String identityCard){
		
		//基本组成以及位数校验
		if(!test("^[0-9]{17}[0-9X]$",identityCard))	return false;
		
		return true;
		
		/*//校验和
		String before17 = identityCard.substring(0, 17);
		String last = identityCard.charAt(17)+"";
		
		//1、乘系数并累加
		int checkSum = 0;
		int [] checkSumConstant = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
		for(int i = 0; i<17; i++){
			checkSum += Integer.parseInt(before17.charAt(i) + "") * checkSumConstant[i];
		}
		
		//对11求余数
		int yushu = checkSum % 11;
		
		//余数映射
		String result = "";
		Map<String,String> mapConstant = new HashMap<String,String>();
		mapConstant.put("0","1");
		mapConstant.put("1","0");
		mapConstant.put("2","X");
		mapConstant.put("3","9");
		mapConstant.put("4","8");
		mapConstant.put("5","7");
		mapConstant.put("6","6");
		mapConstant.put("7","5");
		mapConstant.put("8","4");
		mapConstant.put("9","3");
		mapConstant.put("10","2");
		result = mapConstant.get(yushu+"");
		
		return result.equals(last);*/
	}
	
	/**
	 * 发送消息
	 * @param handler
	 * @param what
	 * @param obj
	 */
	public static void sendMessage(Handler handler,int what,Object obj){
		Message message = new Message();
		message.what = what;
		message.obj = obj;
		handler.sendMessage(message);
	}
}
