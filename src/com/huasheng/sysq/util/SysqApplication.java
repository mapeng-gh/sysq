package com.huasheng.sysq.util;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import com.huasheng.sysq.util.db.SysQOpenHelper;

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
		initFtpConfig();
		initMysqlConfig();
		
		//初始化问卷
		initDB();
	}
	
	private void initDB(){
		
		//检测是否已经初始化
		File sqliteDBFile = new File(PathConstants.getDBDir(),"sysq.db");
		if(sqliteDBFile.exists())	return;
		
		//初始化
		SysQOpenHelper.getDatabase();
		DialogUtils.showLongToast(context,"问卷初始化完成");
	}
	
	private void initFtpConfig(){
		
		//检测是否已经初始化
		File ftpConfigFile = new File(PathConstants.getSettingsDir(),"ftp.config");
		if(ftpConfigFile.exists())	return;
		
		//初始化
		InputStream is = null;
		try{
			is = AssetUtils.openAsStream(context, "config" + File.separator + "ftp.config");
			FileUtils.copyInputStreamToFile(is, new File(PathConstants.getSettingsDir(),"ftp.config"));
			
			DialogUtils.showLongToast(context,"Ftp配置初始化完成");
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
	
	private void initMysqlConfig(){
		
		//检测是否已经初始化
		File mysqlConfigFile = new File(PathConstants.getSettingsDir(),"db.config");
		if(mysqlConfigFile.exists())	return;
		
		//初始化
		InputStream is = null;
		try{
			is = AssetUtils.openAsStream(context, "config" + File.separator + "db.config");
			FileUtils.copyInputStreamToFile(is, new File(PathConstants.getSettingsDir(),"db.config"));
			
			DialogUtils.showLongToast(context,"Mysql配置初始化完成");
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
