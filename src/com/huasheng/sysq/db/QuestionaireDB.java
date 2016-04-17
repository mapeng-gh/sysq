package com.huasheng.sysq.db;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.huasheng.sysq.model.Questionaire;
import com.huasheng.sysq.util.ColumnConstants;
import com.huasheng.sysq.util.InterviewConstants;
import com.huasheng.sysq.util.TableConstants;

public class QuestionaireDB {

	public static List<Questionaire> getList(int versionId,int type){
		Cursor cursor = SysQOpenHelper.getDatabase().query(
				TableConstants.TABLE_QUESTIONAIRE, 
				null, 
				"version_id = ? and type in (?,?)",
				new String[]{versionId+"",type+"",InterviewConstants.TYPE_COMMON+""},
				null,null,"seq_num asc");
		
		List<Questionaire> questionaireList = new ArrayList<Questionaire>();
		while(cursor.moveToNext()){
			Questionaire questionaire = fillObjectFromDB(cursor);
			questionaireList.add(questionaire);
		}
		
		cursor.close();
		
		return questionaireList;
	}
	
	public static Questionaire selectByCode(String code,int versionId){
		Cursor cursor = SysQOpenHelper.getDatabase().query(
				TableConstants.TABLE_QUESTIONAIRE, 
				null, 
				ColumnConstants.COLUMN_QUESTIONAIRE_CODE + "=?" + " and " + ColumnConstants.COLUMN_QUESTIONAIRE_VERSION_ID + "=?",
				new String[]{code,versionId+""},
				null,null,null);
		
		Questionaire questionaire = null;
		if(cursor.moveToNext()){
			questionaire = fillObjectFromDB(cursor);
		}
		cursor.close();
		
		return questionaire;
	}
	
	private static Questionaire fillObjectFromDB(Cursor cursor){
		Questionaire questionaire = new Questionaire();
		questionaire.setId(cursor.getInt(cursor.getColumnIndex("id")));
		questionaire.setCode(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_QUESTIONAIRE_CODE)));
		questionaire.setTitle(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_QUESTIONAIRE_TITLE)));
		questionaire.setIntroduction(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_QUESTIONAIRE_INTRODUCTION)));
		questionaire.setRemark(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_QUESTIONAIRE_REMARK)));
		questionaire.setType(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_QUESTIONAIRE_TYPE)));
		questionaire.setSeqNum(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_QUESTIONAIRE_SEQ_NUM)));
		questionaire.setVersionId(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_QUESTIONAIRE_VERSION_ID)));
		return questionaire;
	}
}
