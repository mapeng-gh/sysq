package com.huasheng.sysq.util;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

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
		
		//初始化配置
		initConfig();
	}
	
	private void initConfig(){
		
		InputStream is = null;
		try{
			is = AssetUtils.openAsStream(context, "config" + File.separator + "ftp.config");
			FileUtils.copyInputStreamToFile(is, new File(PathConstants.getSettingsDir(),"ftp.config"));
			
			is = AssetUtils.openAsStream(context, "config" + File.separator + "db.config");
			FileUtils.copyInputStreamToFile(is, new File(PathConstants.getSettingsDir(),"db.config"));
			
			DialogUtils.showLongToast(context,"配置初始化完成");
		}catch(Exception e){
			DialogUtils.showLongToast(context,e.getMessage());
		}finally{
			try{
				if(is != null){
					is.close();
				}
			}catch(Exception e){
			}
		}
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
