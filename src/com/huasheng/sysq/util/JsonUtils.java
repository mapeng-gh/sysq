package com.huasheng.sysq.util;

import java.lang.reflect.Type;

import org.apache.commons.lang3.ArrayUtils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {

	public static String toJson(Object obj,final String[] excludeFields){
		Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
			
			@Override
			public boolean shouldSkipField(FieldAttributes field) {
				String name = field.getName();
				if(excludeFields != null && excludeFields.length > 0){
					if(ArrayUtils.contains(excludeFields, name)){
						return true;
					}
				}
				return false;
			}
			
			@Override
			public boolean shouldSkipClass(Class<?> arg0) {
				return false;
			}
		}).create();
		return gson.toJson(obj);
	}
	
	public static Object fromJson(String json,Type type){
		Gson gson = new Gson();
		return gson.fromJson(json, type);
	}
}
