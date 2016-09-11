package com.huasheng.sysq.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;

public class UploadUtils {
	
	/**
	 * 读取配置信息
	 * @return
	 * @throws IOException 
	 */
	private  static Properties loadConfig(){
		
		Properties props = new Properties();
		
		InputStream is = null; 
		try{
			is = new FileInputStream(new File(PathConstants.getFTPConfigPath()));
		}catch(FileNotFoundException fnfe){
			throw new RuntimeException("读取配置文件失败：ftp.properties文件不存在");
		}
		
		Reader reader = null;
		
		try{
			reader = new InputStreamReader(is, "utf-8");
		}catch(UnsupportedEncodingException e){
			try{
				is.close();
			}catch(IOException ioe){
				//ignore
			}
			throw new RuntimeException("读取配置文件失败：" + e.getMessage());
		}
		
		try{
			props.load(is);
		}catch(IOException ioe){
			throw new RuntimeException("读取配置文件失败：" + ioe.getMessage());
		}finally{
			try{
				reader.close();
			}catch(IOException e){
				//ignore
			}
		}
		
		String ip = props.getProperty("ip");
		if(ip == null || ip.trim().equals("")){
			throw new RuntimeException("读取配置文件失败：ip地址为空");
		}
		
		String port = props.getProperty("port");
		if(port == null || port.trim().equals("")){
			throw new RuntimeException("读取配置文件失败：端口为空");
		}
		
		String username = props.getProperty("username");
		if(username == null || username.trim().equals("")){
			throw new RuntimeException("读取配置文件失败：用户名为空");
		}
		
		String password = props.getProperty("password");
		if(password == null || password.trim().equals("")){
			throw new RuntimeException("读取配置文件失败：密码为空");
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
	 * @throws IOException 
	 */
	private static FTPClient openConnection(){
		
		//加载配置信息
		Properties props = null;
		try{
			props = loadConfig();
		}catch(Exception e){
			throw e;
		}
		
		FTPClient ftpClient = new FTPClient();
		
		//连接服务器
		try{
			ftpClient.connect(props.getProperty("ip"), Integer.parseInt(props.getProperty("port")));
		}catch(SocketException  se){
			throw new RuntimeException("连接服务器失败：请检查ip地址、端口是否正确以及FTP服务是否正常启动");
		}catch(IOException ioe){
			throw new RuntimeException("连接服务器失败：" + ioe.getMessage());
		}
		
		//登录服务器
		try{
			boolean loginResult = ftpClient.login(props.getProperty("username"), props.getProperty("password"));  
			if(!loginResult){
				try{
					ftpClient.disconnect();
				}catch(IOException e){
					//ignore
				}
				throw new RuntimeException("登录服务器失败：请检查用户名和密码是否正确");
			}
			
		}catch(IOException ioe){
			try{
				ftpClient.disconnect();
			}catch(IOException e){
				//ignore
			}
			throw new RuntimeException("登录服务器失败：" + ioe.getMessage());
		}
		
		//设置传输编码
		try{
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		}catch(IOException e){
			try{
				ftpClient.disconnect();
			}catch(IOException ioe){
				//ignore
			}
			throw new RuntimeException("设置传输编码失败：" + e.getMessage());
		}
		
		return ftpClient;
	}
	
	/**
	 * 准备文件
	 * @return
	 */
	private static List<File> prepareFiles(){
		
		List<File> fileList = new ArrayList<File>();
		
		//录音文件、数据库文件
		File audioDir = new File(PathConstants.getAudioDir());
		File dbFile = new File(PathConstants.getDBPath());
		
		fileList.addAll(Arrays.asList(audioDir.listFiles()));
		fileList.add(dbFile);
		
		return fileList;
	}
	
	/**
	 * 检查环境
	 * @return
	 */
	public static void checkEnv(){
		
		FTPClient ftpClient = openConnection();
		try{
			ftpClient.logout();
			ftpClient.disconnect();
		}catch(IOException closeIOE){
			//ignore
		}
	}
	
	/**
	 * 上传文件
	 * @param files
	 * @throws FileNotFoundException 
	 */
	public static void upload(){
		
		FTPClient ftpClient = null;
		
		//连接服务器
		try{
			ftpClient = openConnection();
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
		
		//上传文件
		List<File> uploadFiles = prepareFiles();
		for(File uploadFile : uploadFiles){
			
			InputStream uploadIS = null;
			try{
				uploadIS = new FileInputStream(uploadFile);
				
				try{
					
					boolean isUploadSuccess = ftpClient.storeFile(uploadFile.getName(),uploadIS);
					if(!isUploadSuccess){
						try{
							uploadIS.close();
							ftpClient.logout();
							ftpClient.disconnect();
						}catch(IOException closeIOE){
							//ignore
						}
						throw new RuntimeException("上传文件失败：" + ", 文件名 : " + uploadFile.getName());
					}
					
				}catch(IOException ioe){
					try{
						uploadIS.close();
						ftpClient.logout();
						ftpClient.disconnect();
					}catch(IOException closeIOE){
						//ignore
					}
					throw new RuntimeException("上传文件失败：" + ioe.getMessage() + ", 文件名 : " + uploadFile.getName());
				}
				
				try{
					uploadIS.close();
				}catch(IOException e){
					//ignore
				}
				
			}catch(FileNotFoundException fnfe){
				//ignore
			}
		}
		
		try{
			ftpClient.logout();
			ftpClient.disconnect();
		}catch(IOException closeIOE){
			//ignore
		}
	}
}
