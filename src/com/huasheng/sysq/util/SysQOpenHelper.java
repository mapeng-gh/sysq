package com.huasheng.sysq.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class SysQOpenHelper extends SQLiteOpenHelper{
	
	private static final String TAG = SysQOpenHelper.class.getSimpleName();
	
	private static final int VERSION = 1;
	private static SQLiteDatabase db;
	
	public SysQOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TableConstants.CREATE_INTERVIEWER);
		db.execSQL(TableConstants.CRETE_RESERVATION);
		db.execSQL(TableConstants.CREATE_VERSION);
		db.execSQL(TableConstants.CREATE_QUESTIONAIRE);
		db.execSQL(TableConstants.CREATE_QUESTION);
		db.execSQL(TableConstants.CREATE_ANSWER);
		db.execSQL(TableConstants.CREATE_INTERVIEW_BASIC);
		db.execSQL(TableConstants.CREATE_INTERVIEW_QUESTIONAIRE);
		db.execSQL(TableConstants.CREATE_INTERVIEW_QUESTION);
		db.execSQL(TableConstants.CREATE_INTERVIEW_ANSWER);
		
		this.initData(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	/**
	 * 获取SQLiteDatabase单例
	 * @return
	 */
	public static SQLiteDatabase getDatabase(){
		if(db == null){
			db = new SysQOpenHelper(SysqApplication.getContext(),PathConstants.getDBPath(),null,VERSION).getWritableDatabase();
		}
		return db;
	}
	
	/**
	 * 插入数据文件
	 * @param dataFilePath
	 * @throws FileNotFoundException 
	 */
	public static void insert(String dataFilePath,SQLiteDatabase db){
		LogUtils.info(TAG, "data file '" + dataFilePath + "' is inserting");
		InputStream is = null;
		try{
			is = new FileInputStream(new File(dataFilePath));
			insert(new FileInputStream(new File(dataFilePath)),db);
			LogUtils.info(TAG, "data file '" + dataFilePath + "' is inserted");
		}catch(Exception e){
			throw new RuntimeException("insert data file error : " + dataFilePath, e);
		}finally{
			if(is != null){
				try{
					is.close();
				}catch(Exception e){}
			}
		}
	}
	
	/**
	 * 插入数据文件流
	 * @param is
	 */
	private static void insert(InputStream is,SQLiteDatabase db){
		try{
			List<String> lines = IOUtils.readLines(is, "UTF-8");
			if(lines == null || lines.size() <= 0){
				LogUtils.warn(TAG,"数据文件为空");
				return;
			}
			for(String line : lines){
				if(!StringUtils.isEmpty(line) && !line.startsWith("--")){
					try{
						db.execSQL(line);
					}catch(SQLException e){
						throw new RuntimeException("sql error : [" + line +"]", e);
					}
					
				}
			}
		}catch(Exception e){
			throw new RuntimeException("read lines error", e);
		}
	}

	/**
	 * 数据库数据初始化
	 * @param db
	 */
	public void initData(SQLiteDatabase db){
		
		String[] dataFiles = new String[]{"data/interviewer.sql","data/version.sql","data/questionaire.sql","data/question.sql","data/answer.sql"};
		AssetManager assetManager = SysqApplication.getContext().getAssets();
		for(String dataFile : dataFiles){
			LogUtils.info(TAG, "data file '" + dataFile + "' is inserting");
			InputStream is = null;
			try{
				is = assetManager.open(dataFile);
				insert(is,db);
				LogUtils.info(TAG, "data file '" + dataFile + "' is inserted");
			}catch(IOException e){
				throw new RuntimeException("open data file error : " + dataFile, e);
			}catch(RuntimeException e){
				throw e;
			}finally{
				if(is != null){
					try{
						is.close();
					}catch(Exception e){}
				}
			}
		}
	}

}
