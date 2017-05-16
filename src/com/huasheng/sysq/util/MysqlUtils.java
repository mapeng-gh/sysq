package com.huasheng.sysq.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class MysqlUtils {
	
	private static Connection getConnection(){
		
		//读取配置
		Map<String,String> configMap  = CommonUtils.readProperties(new File(PathConstants.getSettingsDir(),"db.config"),"UTF-8");
		if(configMap == null)	throw new RuntimeException("获取mysql连接：配置失败");
		String ip,port,db,username,password;
		ip = configMap.get("ip");
		port = configMap.get("port");
		db = configMap.get("db");
		username = configMap.get("username");
		password = configMap.get("password");
		if(StringUtils.isEmpty(ip) || StringUtils.isEmpty(port) || StringUtils.isEmpty(db) || StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
			throw new RuntimeException("获取mysql连接：配置不正确");
		}
		
		//检查网络环境
		if(!NetworkUtils.isNetworkEnable(SysqApplication.getContext())){
			throw new RuntimeException("获取连接失败：当前网络不可用");
		}
		
		//获取连接
		Connection conn = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://"+ip+":"+port+"/"+db+"?connectTimeout=3000", username, password);
		}catch(Exception e){
			try{
				if(conn != null){
					conn.close();
				}
			}catch(Exception sqle){
			}
			throw new RuntimeException("获取mysql连接：打开连接失败");
		}
		return conn;
	}
	
	/**
	 * 查询病人：根据身份证号码（一期）
	 * @param identityCard
	 * @return
	 */
	public static List<Map<String,String>> selectPatientByIdentityCard4Old(String identityCard){
		List<Map<String,String>> data = new ArrayList<Map<String,String>>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			pst = conn.prepareStatement("select id,pid,year,month,day,is_case,name,id_card,province,city,address,zip_code,phone from sysq_patient_old patient where patient.id_card = ?");
			pst.setString(1, identityCard);
			rs = pst.executeQuery();
			while(rs.next()){
				data.add(fillMap4PatientOld(rs));
			}
			return data;
		}catch(Exception e){
			throw new RuntimeException("查询mysql数据：" + e.getMessage());
		}finally{
			try{
				if(rs != null)	rs.close();
				if(pst != null)	pst.close();
				if(conn != null)	conn.close();
			}catch(Exception e){
			}
		}
	}
	
	/**
	 * 查询病人：根据姓名（一期）
	 * @param identityCard
	 * @return
	 */
	public static List<Map<String,String>> selectPatientByName4Old(String name){
		List<Map<String,String>> data = new ArrayList<Map<String,String>>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			pst = conn.prepareStatement("select id,pid,year,month,day,is_case,name,id_card,province,city,address,zip_code,phone from sysq_patient_old patient where patient.name = ?");
			pst.setString(1, name);
			rs = pst.executeQuery();
			while(rs.next()){
				data.add(fillMap4PatientOld(rs));
			}
			return data;
		}catch(Exception e){
			throw new RuntimeException("查询mysql数据：" + e.getMessage());
		}finally{
			try{
				if(rs != null)	rs.close();
				if(pst != null)	pst.close();
				if(conn != null)	conn.close();
			}catch(Exception e){
			}
		}
	}
	
	/**
	 * 查询病人：根据身份证号码
	 * @param identityCard
	 * @return
	 */
	public static List<Map<String,String>> selectPatientByIdentityCard(String identityCard){
		List<Map<String,String>> data = new ArrayList<Map<String,String>>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			pst = conn.prepareStatement("select id,username,identity_card,mobile,province,city,address,post_code,family_mobile,family_address,dna,remark from sysq_patient patient where patient.identity_card = ?");
			pst.setString(1, identityCard);
			rs = pst.executeQuery();
			while(rs.next()){
				data.add(fillMap4Patient(rs));
			}
			return data;
		}catch(Exception e){
			throw new RuntimeException("查询mysql数据：" + e.getMessage());
		}finally{
			try{
				if(rs != null)	rs.close();
				if(pst != null)	pst.close();
				if(conn != null)	conn.close();
			}catch(Exception e){
			}
		}
	}
	
	/**
	 * 填充：病人（二期）
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private static Map<String,String> fillMap4PatientOld(ResultSet rs) throws SQLException{
		Map<String,String> map = new HashMap<String,String>();
		map.put("id", rs.getInt("id")+"");
		map.put("pid", rs.getString("pid"));
		map.put("year", rs.getString("year"));
		map.put("month", rs.getString("month"));
		map.put("day", rs.getString("day"));
		map.put("is_case", rs.getString("is_case"));
		map.put("name", rs.getString("name"));
		map.put("id_card", rs.getString("id_card"));
		map.put("province", rs.getString("province"));
		map.put("city", rs.getString("city"));
		map.put("address", rs.getString("address"));
		map.put("zip_code", rs.getString("zip_code"));
		map.put("phone", rs.getString("phone"));
		return map;
	}
	
	/**
	 * 填充：病人
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private static Map<String,String> fillMap4Patient(ResultSet rs) throws SQLException{
		Map<String,String> map = new HashMap<String,String>();
		map.put("id", rs.getInt("id")+"");
		map.put("username", rs.getString("username"));
		map.put("identity_card", rs.getString("identity_card"));
		map.put("mobile", rs.getString("mobile"));
		map.put("province", rs.getString("province"));
		map.put("city", rs.getString("city"));
		map.put("address", rs.getString("address"));
		map.put("post_code", rs.getString("post_code"));
		map.put("family_mobile", rs.getString("family_mobile"));
		map.put("family_address", rs.getString("family_address"));
		map.put("dna", rs.getString("dna"));
		map.put("remark", rs.getString("remark"));
		return map;
	}
}
