package com.huasheng.sysq.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Environment;

public class UploadUtils {
	
	public static final String AUDIO_STORAGE_DIR = Environment.getExternalStorageDirectory() + File.separator + "sysq" + File.separator + "log";//录音文件存储目录
	
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

	private static void upload(File[] files){
		//读取配置
		Properties props = loadConfig();
		String ip = props.getProperty("ip");
		int port = Integer.parseInt(props.getProperty("port"));
		String username = props.getProperty("username");
		String password = props.getProperty("password");
		String remoteDir = props.getProperty("remoteDir");
		
		//上传
		FTPClient ftpClient = new FTPClient(); 
		try{
			ftpClient.connect(ip, port);
		}catch(Exception e){
			SysqApplication.showMessage("连接服务器错误");
			return;
		}
		
		try{
			boolean loginResult = ftpClient.login(username, password);  
			int returnCode = ftpClient.getReplyCode();
			if (loginResult && FTPReply.isPositiveCompletion(returnCode)){
				//ftpClient.changeWorkingDirectory(remoteDir);
				for(File file : files){
					InputStream is = new FileInputStream(file);
					ftpClient.storeFile(file.getName(),is);
					is.close();
				}
				ftpClient.disconnect();
			}else{
				SysqApplication.showMessage("登录服务器失败");
				return;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void upload(Context context){
		
		File auditDir = new File(AUDIO_STORAGE_DIR);
		final File[] files = auditDir.listFiles();
		if(files != null && files.length > 0){
			
			//上传dialog
			AlertDialog.Builder uploadDialogBuilder = new AlertDialog.Builder(context);
			uploadDialogBuilder.setTitle("上传");
			uploadDialogBuilder.setMessage("正在上传中，请稍候...");
			uploadDialogBuilder.setCancelable(false);
			AlertDialog uploadDialog = uploadDialogBuilder.create();
			uploadDialog.show();
			
			//新起线程防止阻塞dialog
			new Thread(new Runnable() {
				@Override
				public void run() {
					upload(files);
				}
			}).start();
		}
	}
}
