package com.huasheng.sysq.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class RenderUtils {

	public static final String TEMPLATE_DIR = "tpl";
	
	private static String loadTemplate(String filename){
		String tpl = "";
		InputStream is = null;
		try{
			is = SysqApplication.getContext().getAssets().open(TEMPLATE_DIR + File.separator + filename);
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
	
	public static void render(final String filename,Object obj,String[] excludeFields){
		final String tpl = loadTemplate(filename);
		final String data = JsonUtils.toJson(obj, excludeFields);
		
		InterviewContext.getWebView().post(new Runnable() {
			@Override
			public void run() {
				InterviewContext.getWebView().loadUrl("javascript:renderContent('"+tpl+"','"+data+"')");
			}
		});
	}
	
}
