package com.huasheng.sysq.util.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.huasheng.sysq.util.PathConstants;

public class SequenceUtils {
	
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
	
	public static int getNextSeq(){
		
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
			throw new RuntimeException("获取序列号失败：数据库配置不正确");
		}
		
		//获取数据库连接
		Connection conn = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://"+ip4db+":"+port4db+"/"+db, username4db, password4db);
		}catch(Exception e){
			throw new RuntimeException("获取序列号失败：连接数据库失败");
		}
		
		//获取序列号
		int seq = -1;
		PreparedStatement queryPst = null;
		PreparedStatement updatePst = null;
		ResultSet queryRs = null;
		try{
			queryPst = conn.prepareStatement("select seq from sysq_sequence");
			queryRs = queryPst.executeQuery();
			if(queryRs.next()){
				seq = queryRs.getInt("seq");
			}
			
			updatePst = conn.prepareStatement("update sysq_sequence set seq = ?");
			updatePst.setInt(1, seq+1);
			updatePst.execute();
			
			return seq;
		}catch(Exception e){
			throw new RuntimeException("获取序列号失败："+e.getMessage());
		}finally{
			if(queryRs != null){
				try{
					queryRs.close();
				}catch(Exception e){
				}
			}
			if(updatePst != null){
				try{
					updatePst.close();
				}catch(Exception e){
				}
			}
			if(queryPst != null){
				try{
					queryPst.close();
				}catch(Exception e){
				}
			}
			if(conn != null){
				try{
					conn.close();
				}catch(Exception e){
				}
			}
		}
	}
}
