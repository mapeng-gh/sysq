package com.huasheng.sysq.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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
	
	/**
	 * 下载资源 
	 * @param url
	 * @param destFile
	 */
	public static void download(String url,File destFile){
		
		//since4.3 use ClosableHttpClient
		DefaultHttpClient httpclient = new DefaultHttpClient();
		InputStream is = null;
		
		try{
			HttpGet get = new HttpGet(url);
			HttpResponse response = httpclient.execute(get);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				
				if(response.getEntity() != null){
					is = response.getEntity().getContent();
					FileUtils.copyInputStreamToFile(is, destFile);
					
				}else{
					throw new RuntimeException("下载文件失败：entiry is null");
				}
			}else{
				throw new RuntimeException("下载文件失败【statusCode=】"+response.getStatusLine().getStatusCode());
			}
		}catch(Exception e){
			throw new RuntimeException("下载文件失败【url=】"+url, e);
		}finally{
			
			//all entity implementations must ensure that all allocated resources are properly deallocated
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {}
			}
		}
		
		//immediate deallocation of all system resources
		httpclient.getConnectionManager().shutdown();
	}
	
	/**
	 * 解压缩
	 * @param zipFilePath
	 * @param destDirPath
	 */
	
	public static void unzip(String zipFilePath,String destDirPath){
		ZipFile zFile = null;
		try{
			zFile = new ZipFile(zipFilePath);
			Enumeration<? extends ZipEntry> zEntryList = zFile.entries();
			while(zEntryList.hasMoreElements()){
				ZipEntry zEntry = zEntryList.nextElement();
				InputStream is = zFile.getInputStream(zEntry);
				FileUtils.copyInputStreamToFile(is, new File(destDirPath,zEntry.getName()));
			}
			
		}catch(Exception e){
			throw new RuntimeException("解压缩失败", e);
		}finally{
			if(zFile != null){
				try{
					zFile.close();
				}catch(Exception e){}
			}
		}
	}
}
