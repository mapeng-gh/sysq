package com.huasheng.sysq.util;

import java.io.File;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

public class SystemUpdateUtils {

	/**
	 * 获取app版本号
	 * @return
	 */
	public static int getVersionCode(){
		PackageManager pm = SysqApplication.getContext().getPackageManager();
		try{
			PackageInfo pi = pm.getPackageInfo("com.huasheng.sysq",0);
			return pi.versionCode;
		}catch(NameNotFoundException e){
			throw new RuntimeException("package[com.huasheng.sysq] not found", e);
		}
	}
	
	/**
	 * 安装apk
	 * @param filePath
	 */
	public static void installApk(String filePath){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(filePath)),"application/vnd.android.package-archive");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		SysqApplication.getContext().startActivity(intent);
	}
}
