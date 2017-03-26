package com.huasheng.sysq.util;

import java.io.File;
import java.io.IOException;

import android.os.Environment;

/**
 * 系统全局常量
 * @author mapeng
 *
 */
public class PathConstants {

	/**
	 * 获取数据库路径
	 * @return
	 */
	public static String getDBPath(){
		File dbDir = new File(Environment.getExternalStorageDirectory() + File.separator + "sysq" + File.separator + "db");
		if(!dbDir.exists()){
			dbDir.mkdirs();
		}
		return dbDir.getPath() + File.separator + "sysq.db";
	}
	
	/**
	 * 获取多媒体目录
	 * @return
	 */
	public static String getMediaDir(){
		File audioDir = new File(Environment.getExternalStorageDirectory() + File.separator + "sysq" + File.separator + "media");
		if(!audioDir.exists()){
			audioDir.mkdirs();
		}
		return audioDir.getPath();
	}
	
	/**
	 * 获取配置目录
	 * @return
	 */
	public static String getSettingsDir(){
		
		File ftpConfigDir = new File(Environment.getExternalStorageDirectory() + File.separator + "sysq" + File.separator + "settings");
		
		if(!ftpConfigDir.exists()){
			ftpConfigDir.mkdirs();
		}
		
		return ftpConfigDir.getPath();
	}
	
	/**
	 * 获取临时目录
	 * @return
	 */
	public static String getTmpDir(){
		File tmpDir = new File(Environment.getExternalStorageDirectory() + File.separator + "sysq" + File.separator + "tmp");
		if(!tmpDir.exists()){
			tmpDir.mkdirs();
		}
		return tmpDir.getPath();
	}
}
