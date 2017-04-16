package com.huasheng.sysq.util;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

public class PackageUtils {
	
	/**
	 * 获取当前应用包名
	 * @param context
	 * @return
	 */
	public static String getCurPackageName(Context context){
		return context.getPackageName();
	}

	/**
	 * 获取app版本号
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context){
		
		PackageManager pm = context.getPackageManager();
		try{
			PackageInfo pi = pm.getPackageInfo(getCurPackageName(context),0);
			return pi.versionCode;
		}catch(NameNotFoundException e){
			throw new RuntimeException("package[com.huasheng.sysq] not found", e);
		}
	}
	
	/**
	 * 安装Apk
	 * @param context
	 * @param apkFile
	 */
	public static void installApk(Context context , File apkFile){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(apkFile),"application/vnd.android.package-archive");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	/**
	 * 重启app
	 * @param context
	 */
	public static void restartApp(Context context){
		PackageManager pm = context.getPackageManager();
		Intent intent = pm.getLaunchIntentForPackage(getCurPackageName(context));
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
		context.startActivity(intent);
	}
}
