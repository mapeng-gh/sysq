package com.huasheng.sysq.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class TemplateUtils {

	public static String loadTemplate(String filename){
		String tpl = "";
		InputStream is = null;
		try{
			is = MyApplication.getContext().getAssets().open(filename);
			tpl = IOUtils.toString(is,"utf-8");
		}catch(Exception e){
			throw new RuntimeException("º”‘ÿƒ£∞Ê ß∞‹", e);
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
}
