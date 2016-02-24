package com.huasheng.sysq.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huasheng.sysq.model.Interviewer;

public class InterviewerDB {
	
	public static Interviewer findByLoginName(String loginName){
		SQLiteDatabase db = SysQOpenHelper.getDatabase();
		Cursor cursor = db.query("interviewer", null, "login_name = ?", 
				new String[]{loginName}, null, null, null);
		Interviewer interviewer = null;
		if(cursor.moveToFirst()){
			interviewer = new Interviewer();
			interviewer.setId(cursor.getInt(cursor.getColumnIndex("id")));
			interviewer.setLoginName(cursor.getString(cursor.getColumnIndex("login_name")));
			interviewer.setPassword(cursor.getString(cursor.getColumnIndex("password")));
			interviewer.setUsername(cursor.getString(cursor.getColumnIndex("username")));
			interviewer.setMobile(cursor.getString(cursor.getColumnIndex("mobile")));
			interviewer.setEmail(cursor.getString(cursor.getColumnIndex("email")));
			interviewer.setWorkingPlace(cursor.getString(cursor.getColumnIndex("working_place")));
		}
		cursor.close();
		return interviewer;
	}
}
