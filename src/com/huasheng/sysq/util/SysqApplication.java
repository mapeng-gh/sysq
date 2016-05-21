package com.huasheng.sysq.util;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
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
	 * 跳转到活动
	 * @param activityClass
	 */
	public static void jumpToActivity(Class activityClass){
		Intent intent = new Intent(getContext(),activityClass);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getContext().startActivity(intent);
	}
	
	/**
	 * 弹出Toast
	 * @param message
	 */
	public static void showMessage(String message){
		Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
	}

}
