package com.huasheng.sysq.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtil {
	
	public static Map<String,String> readProperties(File propsFile,String charset){
		Properties prop = new Properties();
		Reader reader = null;
		Map<String,String> map = new HashMap<String,String>();
		try {
			reader = new InputStreamReader(new FileInputStream(propsFile),charset);
			prop.load(reader);
			Enumeration<Object> keys = prop.keys();
			while(keys.hasMoreElements()){
				String key = keys.nextElement().toString();
				map.put(key, prop.getProperty(key));
			}
			return map;
		} catch (Exception e) {
			throw new RuntimeException("读取配置文件失败："+propsFile.getName(),e);
		}finally{
			if(reader != null){
				try{
					reader.close();
				}catch(Exception e){
					
				}
			}
		}
	}
}
