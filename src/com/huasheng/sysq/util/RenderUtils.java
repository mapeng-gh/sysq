package com.huasheng.sysq.util;

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

	private static String loadTemplate(String filename){
		String tpl = "";
		InputStream is = null;
		try{
			is = MyApplication.getContext().getAssets().open(filename);
			tpl = IOUtils.toString(is,"utf-8");
		}catch(Exception e){
			throw new RuntimeException("����ģ��ʧ��", e);
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
	
	public static void render(final String filename,final String data){
		final String tpl = loadTemplate(filename);
		InterviewContext.getWebView().post(new Runnable() {
			@Override
			public void run() {
				InterviewContext.getWebView().loadUrl("javascript:renderContent('"+tpl+"','"+data+"')");
			}
		});
	}
}
