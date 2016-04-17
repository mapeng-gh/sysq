package com.huasheng.sysq.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huasheng.sysq.model.Interviewer;
import com.huasheng.sysq.util.ColumnConstants;
import com.huasheng.sysq.util.SysQOpenHelper;
import com.huasheng.sysq.util.TableConstants;

public class InterviewerDB {
	
	public static Interviewer findByLoginName(String loginName){
		SQLiteDatabase db = SysQOpenHelper.getDatabase();
		Cursor cursor = db.query(TableConstants.TABLE_INTERVIEWER, null, ColumnConstants.COLUMN_INTERVIEWER_LOGIN_NAME + " = ?", 
				new String[]{loginName}, null, null, null);
		Interviewer interviewer = null;
		if(cursor.moveToFirst()){
			interviewer = fillObjectFromDB(cursor);
		}
		cursor.close();
		return interviewer;
	}
	
	public static void update(Interviewer interviewer){
		ContentValues values = fillDBFromObject(interviewer);
		SysQOpenHelper.getDatabase().update(TableConstants.TABLE_INTERVIEWER, values,"id = ?",new String[]{interviewer.getId()+""});
	}
	
	public static void insert(Interviewer interviewer){
		ContentValues values = fillDBFromObject(interviewer);
		SysQOpenHelper.getDatabase().insert(TableConstants.TABLE_INTERVIEWER, null, values);
	}
	
	private static ContentValues fillDBFromObject(Interviewer interviewer){
		ContentValues values = new ContentValues();
		values.put(ColumnConstants.COLUMN_INTERVIEWER_LOGIN_NAME, interviewer.getLoginName());
		values.put(ColumnConstants.COLUMN_INTERVIEWER_PASSWORD, interviewer.getPassword());
		values.put(ColumnConstants.COLUMN_INTERVIEWER_USERNAME, interviewer.getUsername());
		values.put(ColumnConstants.COLUMN_INTERVIEWER_EMAIL, interviewer.getEmail());
		values.put(ColumnConstants.COLUMN_INTERVIEWER_MOBILE, interviewer.getMobile());
		values.put(ColumnConstants.COLUMN_INTERVIEWER_WORKING_PLACE, interviewer.getWorkingPlace());
		return values;
	}
	
	private static Interviewer fillObjectFromDB(Cursor cursor){
		Interviewer interviewer = new Interviewer();
		interviewer.setId(cursor.getInt(cursor.getColumnIndex("id")));
		interviewer.setLoginName(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEWER_LOGIN_NAME)));
		interviewer.setPassword(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEWER_PASSWORD)));
		interviewer.setUsername(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEWER_USERNAME)));
		interviewer.setMobile(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEWER_MOBILE)));
		interviewer.setEmail(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEWER_EMAIL)));
		interviewer.setWorkingPlace(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEWER_WORKING_PLACE)));
		return interviewer;
	}
}
