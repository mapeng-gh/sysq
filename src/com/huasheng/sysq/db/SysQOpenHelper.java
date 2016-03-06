package com.huasheng.sysq.db;

import com.huasheng.sysq.util.Constants;
import com.huasheng.sysq.util.MyApplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SysQOpenHelper extends SQLiteOpenHelper{
	
	private static SQLiteDatabase db;
	
	public static final String CREATE_INTERVIEWER = "create table " + Constants.TABLE_INTERVIEWER + " (" +
			"id integer primary key autoincrement," +
			"login_name varchar," +
			"password varchar," +
			"username varchar," +
			"mobile varchar," +
			"email varchar," +
			"working_place varchar)";
	public static final String CRETE_RESERVATION = "create table " + Constants.TABLE_RESERVATION + " (" +
			"id integer primary key autoincrement," +
			Constants.TABLE_RESERVATION_COLUMN_NAME + " varchar," +
			Constants.TABLE_RESERVATION_COLUMN_IDENTITY_CARD + " varchar," +
			Constants.TABLE_RESERVATION_COLUMN_MOBILE + " varchar," +
			Constants.TABLE_RESERVATION_COLUMN_TYPE + " integer," +
			Constants.TABLE_RESERVATION_COLUMN_BOOK_DATE + " varchar," +
			Constants.TABLE_RESERVATION_COLUMN_FAMILY_MOBILE + " varchar," +
			Constants.TABLE_RESERVATION_COLUMN_STATUS + " integer )";
	
	public static final String DB_NAME = "sysq.db";
	public static final int VERSION = 1;
	
	public SysQOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_INTERVIEWER);
		db.execSQL(CRETE_RESERVATION);
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
