package com.huasheng.sysq.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.Interviewee;
import com.huasheng.sysq.util.db.ColumnConstants;
import com.huasheng.sysq.util.db.SysQOpenHelper;
import com.huasheng.sysq.util.db.TableConstants;

public class IntervieweeDB {
	
	public static int insert(Interviewee interviewee){
		ContentValues values = fill(interviewee);
		return (int)SysQOpenHelper.getDatabase().insert(TableConstants.TABLE_INTERVIEWEE, null, values);
	}
	
	public static void update(Interviewee interviewee){
		ContentValues values = fill(interviewee);
		SysQOpenHelper.getDatabase().update(TableConstants.TABLE_INTERVIEWEE, values,"id = ?",new String[]{interviewee.getId()+""});
	}
	
	public static Interviewee selectById(int id){
		Cursor cursor = SysQOpenHelper.getDatabase().query(TableConstants.TABLE_INTERVIEWEE, null, "id = ?", new String[]{id + ""}, null, null, null);
		Interviewee interviewee = null;
		if(cursor.moveToNext()){
			interviewee = fillObjectFromDB(cursor);
		}
		return interviewee;
	}
	
	public static List<Interviewee> getList(){
		List<Interviewee> intervieweeList = new ArrayList<Interviewee>();
		Cursor cursor = SysQOpenHelper.getDatabase().query(TableConstants.TABLE_INTERVIEWEE, null, null, null, null, null,null);
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
		interviewee.setUsername(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEWEE_USERNAME)));
		interviewee.setIdentityCard(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEWEE_IDENTITY_CARD)));
		interviewee.setMobile(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEWEE_MOBILE)));
		interviewee.setProvince(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEWEE_PROVINCE)));
		interviewee.setCity(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEWEE_CITY)));
		interviewee.setAddress(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEWEE_ADDRESS)));
		interviewee.setPostCode(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEWEE_POST_CODE)));
		interviewee.setFamilyMobile(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEWEE_FAMILY_MOBILE)));
		interviewee.setFamilyAddress(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEWEE_FAMILY_ADDRESS)));
		interviewee.setDna(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEWEE_DNA)));
		interviewee.setRemark(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEWEE_REMARK)));
		return interviewee;
	}
	
	private static ContentValues fill(Interviewee interviewee){
		ContentValues values = new ContentValues();
		values.put(ColumnConstants.COLUMN_INTERVIEWEE_USERNAME, interviewee.getUsername());
		values.put(ColumnConstants.COLUMN_INTERVIEWEE_IDENTITY_CARD, interviewee.getIdentityCard());
		values.put(ColumnConstants.COLUMN_INTERVIEWEE_MOBILE, interviewee.getMobile());
		values.put(ColumnConstants.COLUMN_INTERVIEWEE_PROVINCE, interviewee.getProvince());
		values.put(ColumnConstants.COLUMN_INTERVIEWEE_CITY, interviewee.getCity());
		values.put(ColumnConstants.COLUMN_INTERVIEWEE_ADDRESS, interviewee.getAddress());
		values.put(ColumnConstants.COLUMN_INTERVIEWEE_POST_CODE, interviewee.getPostCode());
		values.put(ColumnConstants.COLUMN_INTERVIEWEE_FAMILY_MOBILE, interviewee.getFamilyMobile());
		values.put(ColumnConstants.COLUMN_INTERVIEWEE_FAMILY_ADDRESS, interviewee.getFamilyAddress());
		values.put(ColumnConstants.COLUMN_INTERVIEWEE_DNA, interviewee.getDna());
		values.put(ColumnConstants.COLUMN_INTERVIEWEE_REMARK, interviewee.getRemark());
		return values;
	}
}
