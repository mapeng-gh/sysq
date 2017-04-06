package com.huasheng.sysq.db;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.huasheng.sysq.model.Answer;
import com.huasheng.sysq.util.db.ColumnConstants;
import com.huasheng.sysq.util.db.SysQOpenHelper;
import com.huasheng.sysq.util.db.TableConstants;

public class AnswerDB {

	public static List<Answer> getList(String questionCode,int versionId){
		
		Cursor cursor = SysQOpenHelper.getDatabase().query(
				TableConstants.TABLE_ANSWER, 
				null,
				"question_code = ? and version_id = ?",
				new String[]{questionCode,versionId+""},
				null,null,"seq_num asc");
		
		List<Answer> answerList = new ArrayList<Answer>();
		while(cursor.moveToNext()){
			Answer answer = fillObjectFromDB(cursor);
			answerList.add(answer);
		}
		cursor.close();
		
		return answerList;
	}
	
	public static Answer selectByCode(String answerCode,int versionId){
		Cursor cursor = SysQOpenHelper.getDatabase().query(
				TableConstants.TABLE_ANSWER,
				null,
				ColumnConstants.COLUMN_ANSWER_CODE + "=?" + " and " + ColumnConstants.COLUMN_ANSWER_VERSION_ID + "=?",
				new String[]{answerCode,versionId+""},
				null,null,null);
		Answer answer = null;
		if(cursor.moveToNext()){
			answer = fillObjectFromDB(cursor);
		}
		cursor.close();
		return answer;
	}
	
	private static Answer fillObjectFromDB(Cursor cursor){
		Answer answer = new Answer();
		answer.setId(cursor.getInt(cursor.getColumnIndex("id")));
		answer.setCode(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_ANSWER_CODE)));
		answer.setLabel(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_ANSWER_LABEL)));
		answer.setType(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_ANSWER_TYPE)));
		answer.setExtra(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_ANSWER_EXTRA)));
		answer.setShowType(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_ANSWER_SHOW_TYPE)));
		answer.setIsShow(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_ANSWER_IS_SHOW)));
		answer.setEventType(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_ANSWER_EVENT_TYPE)));
		answer.setEventExecute(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_ANSWER_EVENT_EXECUTE)));
		answer.setSeqNum(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_ANSWER_SEQ_NUM)));
		answer.setQuestionCode(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_ANSWER_QUESTION_CODE)));
		answer.setVersionId(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_ANSWER_VERSION_ID)));
		return answer;
	}
}
