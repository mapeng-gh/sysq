package com.huasheng.sysq.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import android.os.Environment;

public class UploadUtils {
	
	/**
	 * 读取配置信息
	 * @return
	 */
	private static Properties loadConfig(){
		Properties props = new Properties();
		InputStream is = UploadUtils.class.getClassLoader().getResourceAsStream("ftp.properties");
		try{
			props.load(is);
		}catch(IOException e){
			throw new RuntimeException("加载配置文件失败", e);
		}finally{
			if(is != null){
				try{
					is.close();
				}catch(IOException e){}
			}
		}
		return props;
	}
	
	/**
	 * 连接服务器
	 * @param ip
	 * @param port
	 * @param username
	 * @param password
	 * @return
	 */
	private static FTPClient openConnection(){
		
		Properties props = loadConfig();
		String ip = props.getProperty("ip");
		int port = Integer.parseInt(props.getProperty("port"));
		String username = props.getProperty("username");
		String password = props.getProperty("password");
		
		FTPClient ftpClient = new FTPClient();
		try{
			ftpClient.connect(ip, port);
			boolean loginResult = ftpClient.login(username, password);  
			if(loginResult && FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){
				return ftpClient;
			}
			throw new RuntimeException("连接FTP服务器错误");
		}catch(Exception e){
			throw new RuntimeException("连接FTP服务器错误", e);
		}
	}
	
	/**
	 * 准备文件
	 * @return
	 */
	private static File[] prepareFiles(){
		File[] files = new File(Environment.getExternalStorageDirectory() + File.separator + "sysq" + File.separator + "log").listFiles();
		return files;
	}
	
	/**
	 * 上传文件
	 * @param files
	 */
	public static void upload(){
		FTPClient ftpClient = openConnection();
		List<InputStream> inputStreamList = new ArrayList<InputStream>();
		try{
			File[] files = prepareFiles();
			for(File file : files){
				InputStream is = new FileInputStream(file);
				ftpClient.storeFile(file.getName(),is);
				inputStreamList.add(is);
			}
		}catch(Exception e){
			throw new RuntimeException("上传文件错误", e);
		}finally{
			for(InputStream is : inputStreamList){
				try{
					is.close();
				}catch(Exception e){
				}
			}
			if(ftpClient.isConnected()){
				try{
					ftpClient.disconnect();
				}catch(Exception e){
				}
			}
		}
	}
}
