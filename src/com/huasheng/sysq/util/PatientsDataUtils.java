package com.huasheng.sysq.util;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import android.content.Context;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class PatientsDataUtils {

	/**
	 * 根据身份证查询
	 * @param context
	 * @param identityCard
	 * @param isCase
	 * @return
	 */
	public static List<Map<String,String>> getPatientListByIdentityCard(Context context,String identityCard){
		InputStream is = null;
		try{
			is = CommonUtils.getAssetsStream(context, "data"+File.separator+"patients.json");
			String patients = IOUtils.toString(is);
			JSONArray jsonArray = JSONArray.parseArray(patients);
			
			if(jsonArray.size() == 0)		return null;
			
			List<Map<String,String>> patientsList = new ArrayList<Map<String,String>>();
			for(int i = 0;i<jsonArray.size();i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if(identityCard.equals(jsonObject.getString("id_card"))){
					Map<String,String> patientMap = jsonObject2Map(jsonObject);
					patientsList.add(patientMap);
				}
			}
			return patientsList;
		}catch(Exception e){
			throw new RuntimeException("查询一期数据失败：" + e.getMessage(), e);
		}finally{
			try{
				if(is != null){
					is.close();
				}
			}catch(Exception e){
			}
		}
	}
	
	/**
	 * 根据姓名查询
	 * @param context
	 * @param username
	 * @param isCase
	 * @return
	 */
	public static List<Map<String,String>> getPatientListByUsername(Context context,String username){
		InputStream is = null;
		try{
			is = CommonUtils.getAssetsStream(context, "data"+File.separator+"patients.json");
			String patients = IOUtils.toString(is);
			JSONArray jsonArray = JSONArray.parseArray(patients);
			
			if(jsonArray.size() == 0)		return null;
			
			List<Map<String,String>> patientsList = new ArrayList<Map<String,String>>();
			for(int i = 0;i<jsonArray.size();i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if(username.equals(jsonObject.getString("name"))){
					Map<String,String> patientMap = jsonObject2Map(jsonObject);
					patientsList.add(patientMap);
				}
			}
			return patientsList;
		}catch(Exception e){
			throw new RuntimeException("查询一期数据失败：" + e.getMessage(), e);
		}finally{
			try{
				if(is != null){
					is.close();
				}
			}catch(Exception e){
			}
		}
	}
	
	public static Map<String,String> jsonObject2Map(JSONObject jsonObject){
		Map<String,String> map = new HashMap<String,String>();
		map.put("pid", jsonObject.getString("pid"));
		map.put("year", jsonObject.getString("year"));
		map.put("month", jsonObject.getString("month"));
		map.put("day", jsonObject.getString("day"));
		map.put("is_case", jsonObject.getString("is_case"));
		map.put("name", jsonObject.getString("name"));
		map.put("id_card", jsonObject.getString("id_card"));
		map.put("province", jsonObject.getString("province"));
		map.put("city", jsonObject.getString("city"));
		map.put("address", jsonObject.getString("address"));
		map.put("zip_code", jsonObject.getString("zip_code"));
		map.put("phone", jsonObject.getString("phone"));
		return map;
	}
}
