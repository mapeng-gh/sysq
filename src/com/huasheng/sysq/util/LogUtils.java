package com.huasheng.sysq.util;

import android.util.Log;

public class LogUtils {

	public static boolean isDebugEnabled = true;
	
	public static void debug(String tag,String msg){
		
		if(isDebugEnabled){
			Log.d(tag, msg);
		}
	}
	
}
