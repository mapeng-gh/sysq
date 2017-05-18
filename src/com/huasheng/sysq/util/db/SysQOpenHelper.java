package com.huasheng.sysq.util.db;

import java.io.File;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.huasheng.sysq.util.PathConstants;
import com.huasheng.sysq.util.SqliteUtils;
import com.huasheng.sysq.util.SysqApplication;


public class SysQOpenHelper extends SQLiteOpenHelper{
	
	private static final String TAG = SysQOpenHelper.class.getSimpleName();
	private static SQLiteDatabase db;
	
	public SysQOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DBConstants.CREATE_INTERVIEWER);
		db.execSQL(DBConstants.CRETE_RESERVATION);
		db.execSQL(DBConstants.CREATE_VERSION);
		db.execSQL(DBConstants.CREATE_QUESTIONAIRE);
		db.execSQL(DBConstants.CREATE_QUESTION);
		db.execSQL(DBConstants.CREATE_ANSWER);
		db.execSQL(DBConstants.CREATE_INTERVIEWEE);
		db.execSQL(DBConstants.CREATE_INTERVIEW_BASIC);
		db.execSQL(DBConstants.CREATE_INTERVIEW_QUESTIONAIRE);
		db.execSQL(DBConstants.CREATE_INTERVIEW_QUESTION);
		db.execSQL(DBConstants.CREATE_INTERVIEW_ANSWER);
		
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
			db = new SysQOpenHelper(SysqApplication.getContext(),PathConstants.getDBDir()+File.separator+"sysq.db",null,DBConstants.VERSION).getWritableDatabase();
		}
		return db;
	}
	
	/**
	 * 数据库数据初始化
	 * @param db
	 */
	public void initData(SQLiteDatabase db){
		
		String[] dataFiles = new String[]{"data/interviewer.sql","data/version.sql","data/questionaire.sql","data/question.sql","data/answer.sql"};
		AssetManager assetManager = SysqApplication.getContext().getAssets();
		for(String dataFile : dataFiles){
			InputStream is = null;
			try{
				is = assetManager.open(dataFile);
				SqliteUtils.execSQL(db, is);
			}catch(Exception e){
				throw new RuntimeException("初始化数据失败："+e.getMessage(),e);
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
