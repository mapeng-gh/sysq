package com.huasheng.sysq.util.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;

import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.InterviewBasicWrap;
import com.huasheng.sysq.model.Interviewee;
import com.huasheng.sysq.model.Interviewer;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.util.PathConstants;
import com.huasheng.sysq.util.SysqApplication;
import com.huasheng.sysq.util.db.SequenceUtils;

public class UploadUtils {
	
	/**
	 * 读取FTP配置信息
	 * @return
	 */
	private  static Map<String,String> loadFTPConfig(){
		
		File ftpConfigFile = new File(PathConstants.getSettingsDir(),"ftp.config");
		Map<String,String> ftpConfigMap = new HashMap<String,String>();
		try{
			List<String> ftpConfigs = FileUtils.readLines(ftpConfigFile, "utf-8");
			if(ftpConfigs == null || ftpConfigs.size() == 0){
				throw new RuntimeException("FTP服务器配置不正确");
			}
			for(String ftpConfig : ftpConfigs){
				if(!StringUtils.isEmpty(ftpConfig)){
					String[] keyValues = ftpConfig.split("=");
					if(keyValues != null && keyValues.length ==2){
						if(!StringUtils.isEmpty(keyValues[0]) && !StringUtils.isEmpty(keyValues[1])){
							ftpConfigMap.put(keyValues[0], keyValues[1]);
						}
					}
				}
			}
			String ip = ftpConfigMap.get("ip");
			if(StringUtils.isEmpty(ip)){
				throw new RuntimeException("FTP服务器配置不正确");
			}
			String port = ftpConfigMap.get("port");
			if(StringUtils.isEmpty(port)){
				throw new RuntimeException("FTP服务器配置不正确");
			}
			String username = ftpConfigMap.get("username");
			String password = ftpConfigMap.get("password");
			if(!StringUtils.isEmpty(password) && StringUtils.isEmpty(username)){
				throw new RuntimeException("FTP服务器配置不正确");
			}
			
			return ftpConfigMap;
		}catch(IOException e){
			throw new RuntimeException("FTP服务器配置不正确");
		}
	}
	
	/**
	 * 读取数据库配置信息
	 * @return
	 */
	private  static Map<String,String> loadDBConfig(){
		
		File dbConfigFile = new File(PathConstants.getSettingsDir(),"db.config");
		Map<String,String> dbConfigMap = new HashMap<String,String>();
		try{
			List<String> dbConfigs = FileUtils.readLines(dbConfigFile, "utf-8");
			if(dbConfigs == null || dbConfigs.size() == 0){
				throw new RuntimeException("数据库配置不正确");
			}
			for(String dbConfig : dbConfigs){
				if(!StringUtils.isEmpty(dbConfig)){
					String[] keyValues = dbConfig.split("=");
					if(keyValues != null && keyValues.length ==2){
						if(!StringUtils.isEmpty(keyValues[0]) && !StringUtils.isEmpty(keyValues[1])){
							dbConfigMap.put(keyValues[0], keyValues[1]);
						}
					}
				}
			}
			String ip = dbConfigMap.get("ip");
			if(StringUtils.isEmpty(ip)){
				throw new RuntimeException("数据库配置不正确");
			}
			String port = dbConfigMap.get("port");
			if(StringUtils.isEmpty(port)){
				throw new RuntimeException("数据库配置不正确");
			}
			String db = dbConfigMap.get("db");
			if(StringUtils.isEmpty(db)){
				throw new RuntimeException("数据库配置不正确");
			}
			String username = dbConfigMap.get("username");
			String password = dbConfigMap.get("password");
			if(!StringUtils.isEmpty(password) && StringUtils.isEmpty(username)){
				throw new RuntimeException("数据库配置不正确");
			}
			
			return dbConfigMap;
		}catch(IOException e){
			throw new RuntimeException("数据库配置不正确");
		}
	}
	
	/**
	 * 获取Mac地址
	 * @param context
	 * @return
	 */
	public static String getMacAddress(Context context){
		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE); 
        WifiInfo info = wifi.getConnectionInfo(); 
        return info.getMacAddress(); 
	}
	
	/**
	 * 发送消息
	 * @param handler
	 * @param what
	 * @param obj
	 */
	private static void sendMessage(Handler handler,int what,Object obj){
		Message message = new Message();
		message.what = what;
		message.obj = obj;
		handler.sendMessage(message);
	}
	
	public static int UPLOAD_FTP_CONFIG_ERROR = 1;
	public static int UPLOAD_NETWORK_ERROR = 2;
	public static int UPLOAD_NO_DATA = 3;
	public static int UPLOAD_DB_CONFIG_ERROR = 4;
	public static int UPLOAD_DB_CONNECTION_ERROR = 5;
	public static int UPLOAD_FTP_CONNECTION_ERROR = 6;
	public static int UPLOAD_FTP_TRANSFER_ERROR = 7;
	public static int UPLOAD_DB_TRANSFER_ERROR = 8;
	public static int UPLOAD_PROGRESS = 9;
	public static int UPLOAD_FINISH = 10;
	
	/**
	 * 上传
	 * @param files
	 * @throws FileNotFoundException 
	 */
	public static void upload(Handler handler){
		
		//获取上传数据（真实数据、已完成、未上传）
		List<InterviewBasicWrap> uploadInterviewBasicWrapList = new ArrayList<InterviewBasicWrap>();
		List<InterviewBasicWrap> interviewBasicWrapList = InterviewService.getAllInterviewBasic();
		if(interviewBasicWrapList != null && interviewBasicWrapList.size() > 0){
			for(InterviewBasicWrap interviewBasicWrap : interviewBasicWrapList){
				if(interviewBasicWrap.getInterviewBasic().getIsTest() == InterviewBasic.TEST_NO 
						&& interviewBasicWrap.getInterviewBasic().getStatus() == InterviewBasic.STATUS_DONE 
						&& interviewBasicWrap.getInterviewBasic().getIsUpload() == InterviewBasic.UPLOAD_NO){
					uploadInterviewBasicWrapList.add(interviewBasicWrap);
				}
			}
		}
		if(uploadInterviewBasicWrapList.size() == 0){
			sendMessage(handler,UPLOAD_NO_DATA,"暂无可上传访谈记录");
			return;
		}
		
		//检查网络是否通
		Context context = SysqApplication.getContext();
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if(ni == null){
			sendMessage(handler,UPLOAD_NETWORK_ERROR,"请先检查网络是否连通");
			return;
		}
		
		//读取FTP配置
		String ip4ftp,port4ftp,username4ftp,password4ftp;
		try{
			Map<String,String> ftpConfigMap = loadFTPConfig();
			ip4ftp = ftpConfigMap.get("ip");
			port4ftp = ftpConfigMap.get("port");
			username4ftp = ftpConfigMap.get("username");
			password4ftp = ftpConfigMap.get("password");
		}catch(Exception e){
			sendMessage(handler,UPLOAD_FTP_CONFIG_ERROR,"请先检查FTP配置是否正确");
			return;
		}
		
		//读取数据库配置
		String ip4db,port4db,username4db,password4db,db;
		try{
			Map<String,String> dbConfigMap = loadDBConfig();
			ip4db = dbConfigMap.get("ip");
			port4db = dbConfigMap.get("port");
			username4db = dbConfigMap.get("username");
			password4db = dbConfigMap.get("password");
			db = dbConfigMap.get("db");
		}catch(Exception e){
			sendMessage(handler,UPLOAD_DB_CONFIG_ERROR,"请先检查数据库配置是否正确");
			return;
		}
		
		//获取数据库连接
		Connection conn = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://"+ip4db+":"+port4db+"/"+db, username4db, password4db);
			conn.setAutoCommit(false);	//事务：非自动提交
		}catch(Exception e){
			sendMessage(handler,UPLOAD_DB_CONNECTION_ERROR,"数据库连接失败："+e.getMessage());
			return;
		}
		
		//获取FTP连接
		FTPClient ftpClient = new FTPClient();
		try{
			ftpClient.connect(ip4ftp, Integer.parseInt(port4ftp));
			if(StringUtils.isEmpty(username4ftp)){
				username4ftp = "anonymous";
			}
			if(ftpClient.login(username4ftp,password4ftp) == false){
				throw new RuntimeException("FTP登录失败");
			}
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();	//设置被动模式
			if(FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))){
				ftpClient.setControlEncoding("UTF-8");
			}
		}catch(Exception e){
			sendMessage(handler,UPLOAD_FTP_CONNECTION_ERROR,"FTP连接失败："+e.getMessage());
			return;
		}
		
		//上传
		int index = 0;	//上传进度
		sendMessage(handler,UPLOAD_PROGRESS,"数据正在上传中，请稍候..."+"0/"+uploadInterviewBasicWrapList.size());
		String operateType = "";
		for(InterviewBasicWrap interviewBasicWrap : uploadInterviewBasicWrapList){
			InterviewBasic interviewBasic = interviewBasicWrap.getInterviewBasic();
			Interviewee interviewee = interviewBasicWrap.getInterviewee();
			Interviewer interviewer = interviewBasicWrap.getInterviewer();
			
			PreparedStatement pst = null;
			InputStream zipIS = null;
			try{
				
				//上传数据库数据
				operateType = "db";
				pst = conn.prepareStatement("insert into test(username) values (?)");
				pst.setString(1, interviewee.getUsername());
				pst.execute();
				
				//多媒体文件：打包
				operateType = "ftp";
				File mediaDir = new File(PathConstants.getMediaDir(),interviewee.getIdentityCard()+"("+interviewee.getUsername()+")");
				File zipFile = new File(PathConstants.getTmpDir(),mediaDir.getName()+".zip");
				ZipUtil.zip(mediaDir.getPath(), zipFile.getPath());
				
				//多媒体文件：上传
				zipIS = new FileInputStream(zipFile);
				boolean isSuccess = ftpClient.storeFile(new String(zipFile.getName().getBytes("UTF-8"),"ISO8859-1"),zipIS);
				if(isSuccess == false){
					throw new RuntimeException(ftpClient.getReplyString());
				}
				
				conn.commit();		//事务：上传数据数据、多媒体文件作为事务
				
				//更新上传进度
				index++;
				sendMessage(handler,UPLOAD_PROGRESS,"数据正在上传中，请稍候..."+index+"/"+uploadInterviewBasicWrapList.size());
			}catch(Exception e){
				try{
					conn.rollback();		//事务：回滚
				}catch(SQLException sqle){
				}
				
				if(operateType.equals("db")){
					sendMessage(handler,UPLOAD_NO_DATA,"上传数据库失败："+e.getMessage());
				}else if(operateType.equals("ftp")){
					sendMessage(handler,UPLOAD_NO_DATA,"上传多媒体包失败："+e.getMessage());
				}
				
				break;	//退出循环
			}finally{
				
				//释放资源：PreparedStatement、InputStream
				if(pst != null){
					try{
						pst.close();
					}catch(Exception e){
					}
				}
				if(zipIS != null){
					try{
						zipIS.close();
					}catch(Exception e){
					}
				}
			}
		}
		
		//上传完成
		if(index == uploadInterviewBasicWrapList.size()){
			sendMessage(handler,UPLOAD_FINISH,"上传完成");
		}
		
		//清理临时文件夹
		try{
			FileUtils.cleanDirectory(new File(PathConstants.getTmpDir()));
		}catch(Exception e){
		}
		
		//关闭连接：数据库
		if(conn != null){
			try{
				conn.close();
			}catch(Exception e){
			}
		}
		
		//关闭连接：FTP
		if(ftpClient != null){
			try{
				ftpClient.logout();
				ftpClient.disconnect();
			}catch(Exception e){
			}
		}
	}
}
