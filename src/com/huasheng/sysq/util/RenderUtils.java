package com.huasheng.sysq.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RenderUtils {

	public static final String TEMPLATE_DIR = "tpl";
	
	private static String loadTemplate(String filename){
		String tpl = "";
		InputStream is = null;
		try{
			is = SysqApplication.getContext().getAssets().open(TEMPLATE_DIR + File.separator + filename);
			tpl = IOUtils.toString(is,"utf-8");
		}catch(Exception e){
			throw new RuntimeException("¼ÓÔØÄ£°æÊ§°Ü", e);
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return tpl;
	}
	
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
	
	public static void render(final String filename,Object obj,String[] excludeFields){
		final String tpl = loadTemplate(filename);
		final String data = toJson(obj, excludeFields);
		
		InterviewContext.getWebView().post(new Runnable() {
			@Override
			public void run() {
				InterviewContext.getWebView().loadUrl("javascript:renderContent('"+tpl+"','"+data+"')");
			}
		});
	}
	
}
