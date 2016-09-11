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
	 * 获取录音目录
	 * @return
	 */
	public static String getAudioDir(){
		File dbDir = new File(Environment.getExternalStorageDirectory() + File.separator + "sysq" + File.separator + "audio");
		if(!dbDir.exists()){
			dbDir.mkdirs();
		}
		return dbDir.getPath();
	}
	
	/**
	 * 获取ftp配置路径
	 * @return
	 */
	public static String getFTPConfigPath(){
		
		File ftpConfigDir = new File(Environment.getExternalStorageDirectory() + File.separator + "sysq" + File.separator + "ftp");
		
		if(!ftpConfigDir.exists()){
			ftpConfigDir.mkdirs();
		}
		
		File ftpConfigFile = new File(ftpConfigDir,"ftp.properties");
		if(!ftpConfigFile.exists()){
			try{
				ftpConfigFile.createNewFile();
			}catch(IOException newIOE){
				//ignore
			}
		}
		
		return ftpConfigFile.getPath();
	}
}
