package com.huasheng.sysq.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import android.database.sqlite.SQLiteDatabase;

public class SqliteUtils {

	/**
	 * 执行sql文件
	 * @param db
	 * @param sqlFile
	 */
	public static void execSQL(SQLiteDatabase db,File file){
		
		if(!file.exists()){
			throw new RuntimeException("sqlfile不存在：" + file.getAbsolutePath());
		}
		
		InputStream is = null;
		try{
			is = new FileInputStream(file);
			execSQL(db,is);
		}catch(Exception e){
			throw new RuntimeException(e.getMessage(),e);
		}finally{
			if(is != null){
				try{
					is.close();
				}catch(Exception e){
				}
			}
		}
	}
	
	/**
	 * 执行sql流
	 * @param is
	 */
	public static void execSQL(SQLiteDatabase db,InputStream is){
		try{
			List<String> sqls = IOUtils.readLines(is, "UTF-8");
			if(sqls != null && sqls.size() > 0){
				for(String sql : sqls){
					if(!StringUtils.isEmpty(sql) && !sql.startsWith("--")){
						db.execSQL(sql);
					}
				}
			}
		}catch(Exception e){
			throw new RuntimeException("执行sql失败：" + e.getMessage(), e);
		}
	}
}
