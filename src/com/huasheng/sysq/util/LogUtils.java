package com.huasheng.sysq.util;

import android.util.Log;

public class LogUtils {

	public static boolean isDebugEnabled = true;
	
	public static void debug(String tag,String msg){
		if(isDebugEnabled){
			Log.d(tag, msg);
		}
	}
	
	public static void warn(String tag,String msg){
		Log.w(tag, msg);
	}
	
	public static void info(String tag,String msg){
		Log.i(tag, msg);
	}
}
