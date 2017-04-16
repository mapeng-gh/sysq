package com.huasheng.sysq.util;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import android.database.sqlite.SQLiteDatabase;

public class SqliteUtils {

	/**
	 * 执行sql
	 * @param db
	 * @param lines
	 */
	public static void execSQL(SQLiteDatabase db,String sql){
		if(!StringUtils.isEmpty(sql)){
			db.execSQL(sql);
		}
	}
	
	/**
	 * 执行sql文件
	 * @param db
	 * @param sqlFile
	 */
	public static void execSQL(SQLiteDatabase db,File sqlFile){
		try{
			if(sqlFile.exists()){
				List<String> sqls = FileUtils.readLines(sqlFile,"UTF-8");
				if(sqls != null && sqls.size() > 0){
					for(String sql : sqls){
						execSQL(db, sql);
					}
				}
			}
		}catch(Exception e){
			throw new RuntimeException("执行sql失败", e);
		}
		
	}
}
