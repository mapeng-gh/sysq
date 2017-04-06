package com.huasheng.sysq.util;

import java.lang.Thread.UncaughtExceptionHandler;

import com.huasheng.sysq.util.log.LogUtils;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class SysqApplication extends Application{
	
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		
		//获取context
		context = getApplicationContext();
		
		//拦截未捕获异常
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread thread, Throwable ex) {
				
				LogUtils.exception(ex);
				
	            android.os.Process.killProcess(android.os.Process.myPid());  
	            System.exit(10);
			}
		});
	}
	
	public static Context getContext(){
		return context;
	}
	
	/**
	 * 弹出Toast
	 * @param message
	 */
	public static void showMessage(String message){
		Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
	}

}
