package com.huasheng.sysq.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;

import com.huasheng.sysq.activity.LoginActivity;
import com.huasheng.sysq.service.SystemUpdateService;

public class SystemUpdateUtils {
	
	public static final String CONFIG_ADDRESS = "http://o77m1ke38.bkt.clouddn.com/version.properties";//配置文件地址
	public static final File UPDATE_DIR = new File(Environment.getExternalStorageDirectory(),"sysq" + File.separator + "update");//临时目录
	
	/**
	 * 检查更新
	 */
	public static void checkUpdate(final Context context,final boolean isJumpToLogin){
		
		//网络检查
		if(!checkNetwork()){
			SysqApplication.showMessage("当前网络不可用");
			return;
		}
		
		//检查dialog
		AlertDialog.Builder checkDialogBuilder = new AlertDialog.Builder(context);
		checkDialogBuilder.setTitle("系统更新");
		checkDialogBuilder.setMessage("正在进行版本检查，请稍候...");
		checkDialogBuilder.setCancelable(false);
		final AlertDialog checkDialog = checkDialogBuilder.create();
		checkDialog.show();
		
		//NetworkOnMainThreadException
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				//清空文件夹
				try{
					FileUtils.cleanDirectory(UPDATE_DIR);
				}catch(IOException e){}
				
				//下载配置文件
				File configFileTmp = new File(UPDATE_DIR,"version.properties"); 
				download(CONFIG_ADDRESS+"?v="+new Date().getTime(),configFileTmp);
				
				//读取配置信息
				final int newAppVersionCode;
				String newAppVersionName;
				final String appDownload;
				final int newInterviewVersionCode;
				String newInterviewVersionName;
				final String zipDownloadUrl;
				Properties prop = loadConfig(configFileTmp);
				newAppVersionCode = Integer.parseInt(prop.getProperty("app_version_code"));
				newAppVersionName = prop.getProperty("app_version_name");
				appDownload = prop.getProperty("app_download");
				newInterviewVersionCode = Integer.parseInt(prop.getProperty("interview_version_code"));
				newInterviewVersionName = prop.getProperty("interview_version_name");
				zipDownloadUrl = prop.getProperty("interview_download");
				
				//版本检查
				final int curAppVersionCode = getAppCurVersion();
				final int curInterviewVersionCode = SystemUpdateService.getCurrentInterviewVersion().getId();
				if(newAppVersionCode == curAppVersionCode && newInterviewVersionCode == curInterviewVersionCode){
					checkDialog.dismiss();
					//can't create handle inside thread that has not called Looper.prepare()
					Looper.prepare();
					SysqApplication.showMessage("已是最新版本");
					Looper.loop();
					return;
				}else{
					checkDialog.dismiss();
					
					//确认dialog
					Looper.prepare();
					AlertDialog.Builder confirmDialogBuilder = new AlertDialog.Builder(context);
					confirmDialogBuilder.setTitle("系统更新");
					List<String> msgs = new ArrayList<String>();
					if(newAppVersionCode > curAppVersionCode){
						msgs.add("app有新版本：" + newAppVersionName);
					}
					if(newInterviewVersionCode > curInterviewVersionCode ){
						msgs.add("问卷有新版本：" +newInterviewVersionName );
					}
					confirmDialogBuilder.setMessage(StringUtils.join(msgs,"\n"));
					confirmDialogBuilder.setPositiveButton("确定", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface confirmDialog, int which) {
							
							//更新dialog
							confirmDialog.dismiss();
//							Looper.prepare();
							AlertDialog.Builder updateDialogBuilder = new AlertDialog.Builder(context);
							updateDialogBuilder.setTitle("系统更新");
							updateDialogBuilder.setMessage("正在进行更新，请稍候...");
							updateDialogBuilder.setCancelable(false);
							final AlertDialog updateDialog = updateDialogBuilder.create();
							updateDialog.show();
//							Looper.loop();//only one looper may be created per thread
							
							//开启新线程，防止updateDialog阻塞
							new Thread(new Runnable() {
								
								@Override
								public void run() {
									
									//更新问卷
									if(newInterviewVersionCode > curInterviewVersionCode){
										File zipFileTmp = new File(UPDATE_DIR,"interview.zip");
										download(zipDownloadUrl+"?v="+new Date().getTime(),zipFileTmp);
										
										File tmpUnzipDir = new File(UPDATE_DIR,"unzip");
										unzip(zipFileTmp,tmpUnzipDir);
										
										File[] files = tmpUnzipDir.listFiles();
										for(File file : files){
											execSQL(file);
										}
										
										SystemUpdateService.switchInterviewVersion(curInterviewVersionCode,newInterviewVersionCode);
									}
									
									//更新app
									if(newAppVersionCode > curAppVersionCode){
										File appDownloadFile = new File(UPDATE_DIR,"sysq.apk");
										download(appDownload+"?v="+new Date().getTime(),appDownloadFile);
										installApk(appDownloadFile);
									}
									
									updateDialog.dismiss();
									
									//跳转登录
									if(isJumpToLogin && newInterviewVersionCode > curInterviewVersionCode && newAppVersionCode == curAppVersionCode){
										SysqApplication.jumpToActivity(LoginActivity.class);
									}
								}
							}).start();
							
						}
					});
					confirmDialogBuilder.setNegativeButton("取消", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					AlertDialog confirmDialog = confirmDialogBuilder.create();
					confirmDialog.show();
					Looper.loop();
				}
			}
		}).start();
	}
	
	/**
	 * 检查网络
	 * @return
	 */
	private static boolean checkNetwork(){
		Context context = SysqApplication.getContext();
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null;
		
	}
	
	/**
	 * 读取配置信息
	 * @param propertyFile
	 * @return
	 */
	private static Properties loadConfig(File propertyFile){
		Properties prop = new Properties();
		InputStream configIS = null; 
		try{
			configIS = new FileInputStream(propertyFile);
			prop.load(configIS);
			return prop;
		}catch(IOException e){
			throw new RuntimeException("读取配置文件错误", e);
		}finally{
			if(configIS != null){
				try{
					configIS.close();
				}catch(Exception e){}
			}
		}
	}
	
	/**
	 * 插入数据
	 * @param filename
	 */
	private static void execSQL(File dataFile){
		try{
			List<String> lines = FileUtils.readLines(dataFile,"UTF-8");
			if(lines != null && lines.size() > 0){
				for(String line : lines){
					if(!TextUtils.isEmpty(line.trim())){
						SysQOpenHelper.getDatabase().execSQL(line);
					}
				}
			}
		}catch(Exception e){
			throw new RuntimeException("插入数据失败", e);
		}
		
	}

	/**
	 * 获取app版本号
	 * @return
	 */
	private static int getAppCurVersion(){
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
	private static void installApk(File apkFile){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(apkFile),"application/vnd.android.package-archive");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		SysqApplication.getContext().startActivity(intent);
	}
	
	/**
	 * 下载资源 
	 * @param url
	 * @param destFile
	 */
	private static void download(String url,File destFile){
		
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
	
	private static void unzip(File zipFile,File destDir){
		ZipFile zFile = null;
		try{
			zFile = new ZipFile(zipFile);
			Enumeration<? extends ZipEntry> zEntryList = zFile.entries();
			while(zEntryList.hasMoreElements()){
				ZipEntry zEntry = zEntryList.nextElement();
				InputStream is = zFile.getInputStream(zEntry);
				FileUtils.copyInputStreamToFile(is, new File(destDir,zEntry.getName()));
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
