package com.huasheng.sysq.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class SysQOpenHelper extends SQLiteOpenHelper{
	
	private static SQLiteDatabase db;
	
	public static final String CREATE_INTERVIEWER = "create table " + TableConstants.TABLE_INTERVIEWER + 
			"(" +
				"id integer primary key autoincrement," +
				ColumnConstants.COLUMN_INTERVIEWER_LOGIN_NAME + " varchar," +
				ColumnConstants.COLUMN_INTERVIEWER_PASSWORD + " varchar," +
				ColumnConstants.COLUMN_INTERVIEWER_USERNAME + " varchar," +
				ColumnConstants.COLUMN_INTERVIEWER_MOBILE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEWER_EMAIL + " varchar," +
				ColumnConstants.COLUMN_INTERVIEWER_WORKING_PLACE + " varchar" +
			")";
	public static final String CRETE_RESERVATION = "create table " + TableConstants.TABLE_RESERVATION + 
			"(" +
				"id integer primary key autoincrement," +
				ColumnConstants.COLUMN_RESERVATION_USERNAME + " varchar," +
				ColumnConstants.COLUMN_RESERVATION_IDENTITY_CARD + " varchar," +
				ColumnConstants.COLUMN_RESERVATION_MOBILE + " varchar," +
				ColumnConstants.COLUMN_RESERVATION_TYPE + " integer," +
				ColumnConstants.COLUMN_RESERVATION_BOOK_DATE + " varchar," +
				ColumnConstants.COLUMN_RESERVATION_FAMILY_MOBILE + " varchar" +
			")";
	public static final String CREATE_VERSION = "create table " + TableConstants.TABLE_VERSION +
			"(" +
				"id integer primary key autoincrement," +
				ColumnConstants.COLUMN_VERSION_NAME + " varchar," +
				ColumnConstants.COLUMN_VERSION_PUBLISH_DATE + " varchar," +
				ColumnConstants.COLUMN_VERSION_REMARK + " varchar," +
				ColumnConstants.COLUMN_VERSION_IS_CURRENT + " integer" +
			")";
	public static final String CREATE_QUESTIONAIRE = "create table " + TableConstants.TABLE_QUESTIONAIRE +
			"(" +
				"id integer primary key autoincrement," +
				ColumnConstants.COLUMN_QUESTIONAIRE_CODE + " varchar," +
				ColumnConstants.COLUMN_QUESTIONAIRE_TITLE + " varchar," +
				ColumnConstants.COLUMN_QUESTIONAIRE_INTRODUCTION + " varchar," +
				ColumnConstants.COLUMN_QUESTIONAIRE_REMARK + " varchar," +
				ColumnConstants.COLUMN_QUESTIONAIRE_TYPE + " integer," +
				ColumnConstants.COLUMN_QUESTIONAIRE_SEQ_NUM + " integer," +
				ColumnConstants.COLUMN_QUESTIONAIRE_VERSION_ID + " integer" +
			")";
	public static final String CREATE_QUESTION = "create table " + TableConstants.TABLE_QUESTION + 
			"(" +
				"id integer primary key autoincrement," +
				ColumnConstants.COLUMN_QUESTION_CODE + " varchar," +
				ColumnConstants.COLUMN_QUESTION_DESCRIPTION + " varchar," +
				ColumnConstants.COLUMN_QUESTION_REMARK + " varchar," +
				ColumnConstants.COLUMN_QUESTION_IS_END + " integer," +
				ColumnConstants.COLUMN_QUESTION_SEQ_NUM + " integer," +
				ColumnConstants.COLUMN_QUESTION_QUESTIONAIRE_CODE + " varchar," +
				ColumnConstants.COLUMN_QUESTION_ENTRY_LOGIC + " varchar," +
				ColumnConstants.COLUMN_QUESTION_EXIT_LOGIC + " varchar," +
				ColumnConstants.COLUMN_QUESTION_VERSION_ID + " integer" +
			")";
	public static final String CREATE_ANSWER = "create table " + TableConstants.TABLE_ANSWER +
			"(" +
				"id integer primary key autoincrement," +
				ColumnConstants.COLUMN_ANSWER_CODE + " varchar," +
				ColumnConstants.COLUMN_ANSWER_LABEL + " varchar," +
				ColumnConstants.COLUMN_ANSWER_TYPE + " varchar," +
				ColumnConstants.COLUMN_ANSWER_EXTRA + " varchar," +
				ColumnConstants.COLUMN_ANSWER_SHOW_TYPE + " varchar," +
				ColumnConstants.COLUMN_ANSWER_IS_SHOW + " integer," +
				ColumnConstants.COLUMN_ANSWER_EVENT_TYPE + " varchar," +
				ColumnConstants.COLUMN_ANSWER_EVENT_EXECUTE + " varchar," +
				ColumnConstants.COLUMN_ANSWER_SEQ_NUM + " integer," +
				ColumnConstants.COLUMN_ANSWER_QUESTION_CODE + " varchar," +
				ColumnConstants.COLUMN_ANSWER_VERSION_ID + " integer" +
			")";
	public static final String CREATE_INTERVIEW_BASIC = "create table " + TableConstants.TABLE_INTERVIEW_BASIC +
			"(" +
				"id integer primary key autoincrement," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_USERNAME + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_IDENTITY_CARD + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_MOBILE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_PROVINCE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_CITY + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_ADDRESS + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_POST_CODE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_FAMILY_MOBILE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_FAMILY_ADDRESS + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_REMARK + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_DNA + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID + " integer," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_TYPE + " integer," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_IS_TEST + " integer," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_START_TIME + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_STATUS + " integer," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_CUR_QUESTIONAIRE_CODE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_NEXT_QUESTION_CODE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_LAST_MODIFYED_TIME + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_VERSION_ID + " integer" +
			")";
	
	public static final String CREATE_INTERVIEW_QUESTIONAIRE = "create table " + TableConstants.TABLE_INTERVIEW_QUESTIONAIRE +
			"(" +
				"id integer primary key autoincrement," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_INTERVIEW_BASIC_ID + " integer," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_QUESTIONAIRE_CODE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_START_TIME + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_LAST_MODIFIED_TIME + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_STATUS + " integer," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_SEQ_NUM + " integer," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_VERSION_ID + " integer" +
			")";
	
	public static final String CREATE_INTERVIEW_QUESTION = "create table " + TableConstants.TABLE_INTERVIEW_QUESTION +
			"(" +
				"id integer primary key autoincrement," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTION_INTERVIEW_BASIC_ID + " integer," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTION_QUESTIONAIRE_CODE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTION_QUESTION_CODE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTION_SEQ_NUM + " integer," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTION_VERSION_ID + " integer" +
			")";
	
	public static final String CREATE_INTERVIEW_ANSWER = "create table " + TableConstants.TABLE_INTERVIEW_ANSWER +
			"(" +
				"id integer primary key autoincrement," +
				ColumnConstants.COLUMN_INTERVIEW_ANSWER_INTERVIEW_BASIC_ID + " integer," +
				ColumnConstants.COLUMN_INTERVIEW_ANSWER_QUESTION_CODE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_LABEL + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_CODE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_VALUE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_TEXT + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_SEQ_NUM + " integer," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTION_VERSION_ID + " integer" +
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
		db.execSQL(CREATE_INTERVIEW_BASIC);
		db.execSQL(CREATE_INTERVIEW_QUESTIONAIRE);
		db.execSQL(CREATE_INTERVIEW_QUESTION);
		db.execSQL(CREATE_INTERVIEW_ANSWER);
		this.initData(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	public static SQLiteDatabase getDatabase(){
		if(db == null){
			db = new SysQOpenHelper(SysqApplication.getContext(),DB_NAME,null,VERSION).getWritableDatabase();
		}
		return db;
	}

	public void initData(SQLiteDatabase db){
		
//		String[] dataFiles = new String[]{"data/interviewer.sql","data/version.sql","data/questionaire.sql","data/question.sql","data/answer.sql"};
		String[] dataFiles = new String[]{"data/data.sql"};
		AssetManager assetManager = SysqApplication.getContext().getAssets();
		BufferedReader reader = null;
		
		try{
			for(String dataFile : dataFiles){
				reader = new BufferedReader(new InputStreamReader(assetManager.open(dataFile), "utf-8"));
				String line;
				while((line=reader.readLine()) != null){
					LogUtils.debug("SysQOpenHelper", line);
					db.execSQL(line);
				}
			}
			LogUtils.debug("SysQOpenHelper","数据导入完成");
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
