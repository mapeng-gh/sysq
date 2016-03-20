package com.huasheng.sysq.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.huasheng.sysq.util.MyApplication;
import com.huasheng.sysq.util.TableConstants;

public class SysQOpenHelper extends SQLiteOpenHelper{
	
	private static SQLiteDatabase db;
	
	public static final String CREATE_INTERVIEWER = "create table " + TableConstants.TABLE_INTERVIEWER + 
			"(" +
				"id integer primary key autoincrement," +
				TableConstants.COLUMN_INTERVIEWER_LOGIN_NAME + " varchar," +
				TableConstants.COLUMN_INTERVIEWER_PASSWORD + " varchar," +
				TableConstants.COLUMN_INTERVIEWER_USERNAME + " varchar," +
				TableConstants.COLUMN_INTERVIEWER_MOBILE + " varchar," +
				TableConstants.COLUMN_INTERVIEWER_EMAIL + " varchar," +
				TableConstants.COLUMN_INTERVIEWER_WORKING_PLACE + " varchar" +
			")";
	public static final String CRETE_RESERVATION = "create table " + TableConstants.TABLE_RESERVATION + 
			"(" +
				"id integer primary key autoincrement," +
				TableConstants.COLUMN_RESERVATION_USERNAME + " varchar," +
				TableConstants.COLUMN_RESERVATION_IDENTITY_CARD + " varchar," +
				TableConstants.COLUMN_RESERVATION_MOBILE + " varchar," +
				TableConstants.COLUMN_RESERVATION_TYPE + " integer," +
				TableConstants.COLUMN_RESERVATION_BOOK_DATE + " varchar," +
				TableConstants.COLUMN_RESERVATION_FAMILY_MOBILE + " varchar" +
			")";
	public static final String CREATE_VERSION = "create table " + TableConstants.TABLE_VERSION +
			"(" +
				"id integer primary key autoincrement," +
				TableConstants.COLUMN_VERSION_NAME + " varchar," +
				TableConstants.COLUMN_VERSION_PUBLISH_DATE + " varchar," +
				TableConstants.COLUMN_VERSION_REMARK + " varchar," +
				TableConstants.COLUMN_VERSION_IS_CURRENT + " integer" +
			")";
	public static final String CREATE_QUESTIONAIRE = "create table " + TableConstants.TABLE_QUESTIONAIRE +
			"(" +
				"id integer primary key autoincrement," +
				TableConstants.COLUMN_QUESTIONAIRE_CODE + " varchar," +
				TableConstants.COLUMN_QUESTIONAIRE_TITLE + " varchar," +
				TableConstants.COLUMN_QUESTIONAIRE_INTRODUCTION + " varchar," +
				TableConstants.COLUMN_QUESTIONAIRE_REMARK + " varchar," +
				TableConstants.COLUMN_QUESTIONAIRE_TYPE + " integer," +
				TableConstants.COLUMN_QUESTIONAIRE_SEQ_NUM + " integer," +
				TableConstants.COLUMN_QUESTIONAIRE_VERSION_ID + " integer" +
			")";
	public static final String CREATE_QUESTION = "create table " + TableConstants.TABLE_QUESTION + 
			"(" +
				"id integer primary key autoincrement," +
				TableConstants.COLUMN_QUESTION_CODE + " varchar," +
				TableConstants.COLUMN_QUESTION_DESCRIPTION + " varchar," +
				TableConstants.COLUMN_QUESTION_REMARK + " varchar," +
				TableConstants.COLUMN_QUESTION_TYPE + " varchar," +
				TableConstants.COLUMN_QUESTION_SHOW_TYPE + " varchar," +
				TableConstants.COLUMN_QUESTION_IS_END + " integer," +
				TableConstants.COLUMN_QUESTION_SEQ_NUM + " integer," +
				TableConstants.COLUMN_QUESTION_QUESTIONAIRE_CODE + " varchar," +
				TableConstants.COLUMN_QUESTION_P_QUESTION_CODE + " varchar," +
				TableConstants.COLUMN_QUESTION_ENTRY_LOGIC + " varchar," +
				TableConstants.COLUMN_QUESTION_EXIT_LOGIC + " varchar," +
				TableConstants.COLUMN_QUESTION_VERSION_ID + " integer" +
			")";
	public static final String CREATE_ANSWER = "create table " + TableConstants.TABLE_ANSWER +
			"(" +
				"id integer primary key autoincrement," +
				TableConstants.COLUMN_ANSWER_CODE + " varchar," +
				TableConstants.COLUMN_ANSWER_LABEL + " varchar," +
				TableConstants.COLUMN_ANSWER_TYPE + " varchar," +
				TableConstants.COLUMN_ANSWER_EXTRA + " varchar," +
				TableConstants.COLUMN_ANSWER_IS_SHOW + " integer," +
				TableConstants.COLUMN_ANSWER_EVENT_TYPE + " varchar," +
				TableConstants.COLUMN_ANSWER_EVENT_EXECUTE + " varchar," +
				TableConstants.COLUMN_ANSWER_SEQ_NUM + " integer," +
				TableConstants.COLUMN_ANSWER_QUESTION_CODE + " varchar," +
				TableConstants.COLUMN_ANSWER_VERSION_ID + " integer" +
			")";
	public static final String CREATE_INTERVIEW = "create table " + TableConstants.TABLE_INTERVIEW +
			"(" +
				"id integer primary key autoincrement," +
				TableConstants.COLUMN_INTERVIEW_USERNAME + " varchar," +
				TableConstants.COLUMN_INTERVIEW_IDENTITY_CARD + " varchar," +
				TableConstants.COLUMN_INTERVIEW_MOBILE + " varchar," +
				TableConstants.COLUMN_INTERVIEW_PROVINCE + " varchar," +
				TableConstants.COLUMN_INTERVIEW_CITY + " varchar," +
				TableConstants.COLUMN_INTERVIEW_ADDRESS + " varchar," +
				TableConstants.COLUMN_INTERVIEW_POST_CODE + " varchar," +
				TableConstants.COLUMN_INTERVIEW_FAMILY_MOBILE + " varchar," +
				TableConstants.COLUMN_INTERVIEW_FAMILY_ADDRESS + " varchar," +
				TableConstants.COLUMN_INTERVIEW_REMARK + " varchar," +
				TableConstants.COLUMN_INTERVIEW_DNA + " varchar," +
				TableConstants.COLUMN_INTERVIEW_INTERVIEWER_ID + " integer," +
				TableConstants.COLUMN_INTERVIEW_TYPE + " integer," +
				TableConstants.COLUMN_INTERVIEW_IS_TEST + " integer," +
				TableConstants.COLUMN_INTERVIEW_START_TIME + " varchar," +
				TableConstants.COLUMN_INTERVIEW_STATUS + " integer," +
				TableConstants.COLUMN_INTERVIEW_CUR_QUESTIONAIRE_CODE + " varchar," +
				TableConstants.COLUMN_INTERVIEW_NEXT_QUESTION_CODE + " varchar," +
				TableConstants.COLUMN_INTERVIEW_END_TIME + " varchar," +
				TableConstants.COLUMN_INTERVIEW_VERSION_ID + " integer" +
			")";
	public static final String CREATE_RESULT = "create table " + TableConstants.TABLE_RESULT +
			"("+
				"id integer primary key autoincrement," +
				TableConstants.COLUMN_RESULT_INTERVIEW_ID + " integer," +
				TableConstants.COLUMN_RESULT_INTERVIEW_QUESTION_CODE + " varchar," +
				TableConstants.COLUMN_RESULT_INTERVIEW_QUESTION_VALUE + " varchar" +
			")";
	
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
		db.execSQL(CREATE_VERSION);
		db.execSQL(CREATE_QUESTIONAIRE);
		db.execSQL(CREATE_QUESTION);
		db.execSQL(CREATE_ANSWER);
		db.execSQL(CREATE_INTERVIEW);
		db.execSQL(CREATE_RESULT);
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
		
		AssetManager assetManager = MyApplication.getContext().getAssets();
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new InputStreamReader(assetManager.open("data.txt"), "utf-8"));
			String line;
			while((line=reader.readLine())!=null){
				db.execSQL(line);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}

}
