package com.huasheng.sysq.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.huasheng.sysq.model.Interviewee;
import com.huasheng.sysq.util.db.DBConstants;
import com.huasheng.sysq.util.db.SequenceUtils;
import com.huasheng.sysq.util.db.SysQOpenHelper;

public class IntervieweeDB {
	
	public static int insert(Interviewee interviewee){
		interviewee.setId(SequenceUtils.getNextSeq());
		ContentValues values = fillDBFromObject(interviewee);
		return (int)SysQOpenHelper.getDatabase().insert(DBConstants.TABLE_INTERVIEWEE, null, values);
	}
	
	public static void update(Interviewee interviewee){
		ContentValues values = fillDBFromObject(interviewee);
		SysQOpenHelper.getDatabase().update(DBConstants.TABLE_INTERVIEWEE, values,"id = ?",new String[]{interviewee.getId()+""});
	}
	
	public static Interviewee selectById(int id){
		Cursor cursor = SysQOpenHelper.getDatabase().query(DBConstants.TABLE_INTERVIEWEE, null, "id = ?", new String[]{id + ""}, null, null, null);
		Interviewee interviewee = null;
		if(cursor.moveToNext()){
			interviewee = fillObjectFromDB(cursor);
		}
		return interviewee;
	}
	
	public static List<Interviewee> getList(){
		List<Interviewee> intervieweeList = new ArrayList<Interviewee>();
		Cursor cursor = SysQOpenHelper.getDatabase().query(DBConstants.TABLE_INTERVIEWEE, null, null, null, null, null,null);
		while(cursor.moveToNext()){
			Interviewee interviewee = fillObjectFromDB(cursor);
			intervieweeList.add(interviewee);
		}
		cursor.close();
		return intervieweeList;
	}
	
	private static Interviewee fillObjectFromDB(Cursor cursor){
		
		Interviewee interviewee = new Interviewee();
		interviewee.setId(cursor.getInt(cursor.getColumnIndex("id")));
		interviewee.setUsername(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEWEE_USERNAME)));
		interviewee.setIdentityCard(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEWEE_IDENTITY_CARD)));
		interviewee.setMobile(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEWEE_MOBILE)));
		interviewee.setProvince(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEWEE_PROVINCE)));
		interviewee.setCity(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEWEE_CITY)));
		interviewee.setAddress(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEWEE_ADDRESS)));
		interviewee.setPostCode(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEWEE_POST_CODE)));
		interviewee.setFamilyMobile(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEWEE_FAMILY_MOBILE)));
		interviewee.setFamilyAddress(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEWEE_FAMILY_ADDRESS)));
		interviewee.setDna(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEWEE_DNA)));
		interviewee.setRemark(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEWEE_REMARK)));
		return interviewee;
	}
	
	private static ContentValues fillDBFromObject(Interviewee interviewee){
		ContentValues values = new ContentValues();
		values.put("id", interviewee.getId());
		values.put(DBConstants.COLUMN_INTERVIEWEE_USERNAME, interviewee.getUsername());
		values.put(DBConstants.COLUMN_INTERVIEWEE_IDENTITY_CARD, interviewee.getIdentityCard());
		values.put(DBConstants.COLUMN_INTERVIEWEE_MOBILE, interviewee.getMobile());
		values.put(DBConstants.COLUMN_INTERVIEWEE_PROVINCE, interviewee.getProvince());
		values.put(DBConstants.COLUMN_INTERVIEWEE_CITY, interviewee.getCity());
		values.put(DBConstants.COLUMN_INTERVIEWEE_ADDRESS, interviewee.getAddress());
		values.put(DBConstants.COLUMN_INTERVIEWEE_POST_CODE, interviewee.getPostCode());
		values.put(DBConstants.COLUMN_INTERVIEWEE_FAMILY_MOBILE, interviewee.getFamilyMobile());
		values.put(DBConstants.COLUMN_INTERVIEWEE_FAMILY_ADDRESS, interviewee.getFamilyAddress());
		values.put(DBConstants.COLUMN_INTERVIEWEE_DNA, interviewee.getDna());
		values.put(DBConstants.COLUMN_INTERVIEWEE_REMARK, interviewee.getRemark());
		return values;
	}
}
