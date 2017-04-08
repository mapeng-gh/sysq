package com.huasheng.sysq.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.huasheng.sysq.model.Interviewer;
import com.huasheng.sysq.util.db.DBConstants;
import com.huasheng.sysq.util.db.SequenceUtils;
import com.huasheng.sysq.util.db.SysQOpenHelper;

public class InterviewerDB {
	
	public static void insert(Interviewer interviewer){
		interviewer.setId(SequenceUtils.getNextSeq());
		ContentValues values = fillDBFromObject(interviewer);
		SysQOpenHelper.getDatabase().insert(DBConstants.TABLE_INTERVIEWER, null, values);
	}
	
	public static void update(Interviewer interviewer){
		ContentValues values = fillDBFromObject(interviewer);
		SysQOpenHelper.getDatabase().update(DBConstants.TABLE_INTERVIEWER, values,"id = ?",new String[]{interviewer.getId()+""});
	}
	
	public static Interviewer selectById(int id){
		Cursor cursor = SysQOpenHelper.getDatabase().query(DBConstants.TABLE_INTERVIEWER, null, "id = ?", new String[]{id + ""}, null, null, null);
		Interviewer interviewer = null;
		if(cursor.moveToNext()){
			interviewer = fillObjectFromDB(cursor);
		}
		return interviewer;
	}
	
	public static List<Interviewer> selectAll(){
		List<Interviewer> interviewerList = new ArrayList<Interviewer>(); 
		Cursor cursor = SysQOpenHelper.getDatabase().query(DBConstants.TABLE_INTERVIEWER, null, null, null, null, null, null);
		while(cursor.moveToNext()){
			Interviewer interviewer = fillObjectFromDB(cursor);
			interviewerList.add(interviewer);
		}
		cursor.close();
		return interviewerList;
	}
	
	public static Interviewer findByLoginName(String loginName){
		Cursor cursor = SysQOpenHelper.getDatabase().query(
				DBConstants.TABLE_INTERVIEWER, null, 
				DBConstants.COLUMN_INTERVIEWER_LOGIN_NAME + " = ?", 
				new String[]{loginName}, null, null, null);
		Interviewer interviewer = null;
		if(cursor.moveToFirst()){
			interviewer = fillObjectFromDB(cursor);
		}
		cursor.close();
		return interviewer;
	}
	
	private static ContentValues fillDBFromObject(Interviewer interviewer){
		ContentValues values = new ContentValues();
		values.put("id", interviewer.getId());
		values.put(DBConstants.COLUMN_INTERVIEWER_LOGIN_NAME, interviewer.getLoginName());
		values.put(DBConstants.COLUMN_INTERVIEWER_PASSWORD, interviewer.getPassword());
		values.put(DBConstants.COLUMN_INTERVIEWER_USERNAME, interviewer.getUsername());
		values.put(DBConstants.COLUMN_INTERVIEWER_EMAIL, interviewer.getEmail());
		values.put(DBConstants.COLUMN_INTERVIEWER_MOBILE, interviewer.getMobile());
		values.put(DBConstants.COLUMN_INTERVIEWER_WORKING_PLACE, interviewer.getWorkingPlace());
		values.put(DBConstants.COLUMN_INTERVIEWER_UPLOAD_STATUS, interviewer.getUploadStatus());
		return values;
	}
	
	private static Interviewer fillObjectFromDB(Cursor cursor){
		Interviewer interviewer = new Interviewer();
		interviewer.setId(cursor.getInt(cursor.getColumnIndex("id")));
		interviewer.setLoginName(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEWER_LOGIN_NAME)));
		interviewer.setPassword(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEWER_PASSWORD)));
		interviewer.setUsername(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEWER_USERNAME)));
		interviewer.setMobile(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEWER_MOBILE)));
		interviewer.setEmail(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEWER_EMAIL)));
		interviewer.setWorkingPlace(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEWER_WORKING_PLACE)));
		interviewer.setUploadStatus(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEWER_UPLOAD_STATUS)));
		return interviewer;
	}
}
