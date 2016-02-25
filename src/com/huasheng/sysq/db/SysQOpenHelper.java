package com.huasheng.sysq.db;

import com.huasheng.sysq.util.MyApplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SysQOpenHelper extends SQLiteOpenHelper{
	
	private static SQLiteDatabase db;
	
	public static final String CREATE_INTERVIEWER = "create table interviewer (" +
			"id integer primary key autoincrement," +
			"login_name varchar," +
			"password varchar," +
			"username varchar," +
			"mobile varchar," +
			"email varchar," +
			"working_place varchar)";
	
	public static final String DB_NAME = "sysq.db";
	public static final int VERSION = 1;
	
	private SysQOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_INTERVIEWER);
		this.initData(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	public static SQLiteDatabase getDatabase(){
		if(db == null){
			db = new SysQOpenHelper(MyApplication.getContext(),DB_NAME,null,VERSION).getWritableDatabase();
		}
		return db;
	}

	public void initData(SQLiteDatabase db){
		ContentValues values = new ContentValues();
		values.put("login_name", "admin");
		values.put("password", "admin");
		values.put("username", "admin");
		db.insert("interviewer", null,values);
	}

}
