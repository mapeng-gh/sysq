package com.huasheng.sysq.util;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.io.FileUtils;

import android.os.Environment;
import android.util.Log;

public class LogUtils {

	public static boolean isDebugEnabled = true;
	
	public static void debug(String tag,String msg){
		if(isDebugEnabled){
			Log.d(tag, msg);
		}
	}
	
	public static void info(String tag,String msg){
		if(isDebugEnabled){
			Log.i(tag, msg);
		}else{
			writeLog("[info]" + "["+DateTimeUtils.getCurDateTime() + "] " + msg + System.getProperty("line.separator"));
		}
	}
	
	public static void warn(String tag,String msg){
		if(isDebugEnabled){
			Log.w(tag, msg);
		}else{
			writeLog("[warn]" + "["+DateTimeUtils.getCurDateTime() + "] " + msg + System.getProperty("line.separator"));
		}
	}
	
	public static void error(String tag,String msg){
		if(isDebugEnabled){
			Log.e(tag, msg);
		}else{
			writeLog("[error]" + "["+DateTimeUtils.getCurDateTime() + "] " + msg + System.getProperty("line.separator"));
		}
	}
	
	public static void exception(Throwable ex){
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		String exStr = writer.toString();
		error("",exStr);
	}
	
	public static String LOG_PATH = Environment.getExternalStorageDirectory()+File.separator+"sysq"+File.separator+"log";
	
	/**
	 * 写日志到文件
	 * @param msg
	 */
	public static void writeLog(String msg){
		try{
			File logFile = new File(LOG_PATH,DateTimeUtils.getCurDate()+".log");
			FileUtils.writeStringToFile(logFile,msg,"UTF-8",true);
		}catch(Exception e){}
	}
}
