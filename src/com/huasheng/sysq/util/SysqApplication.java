package com.huasheng.sysq.util;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SysqApplication extends Application{
	
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
	}
	
	public static Context getContext(){
		return context;
	}
	
	/**
	 * ��ת���
	 * @param activityClass
	 */
	public static void jumpToActivity(Class activityClass){
		Intent intent = new Intent(getContext(),activityClass);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getContext().startActivity(intent);
	}
	
	/**
	 * ����Toast
	 * @param message
	 */
	public static void showMessage(String message){
		Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
	}

}
