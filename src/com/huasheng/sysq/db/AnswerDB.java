package com.huasheng.sysq.db;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.huasheng.sysq.model.Answer;
import com.huasheng.sysq.util.ColumnConstants;
import com.huasheng.sysq.util.TableConstants;

public class AnswerDB {

	public static List<Answer> getList(String questionCode,int versionId){
		List<Answer> answerList = new ArrayList<Answer>();
		
		Cursor cursor = SysQOpenHelper.getDatabase().query(
				TableConstants.TABLE_ANSWER, 
				null,
				"question_code = ? and version_id = ?",
				new String[]{questionCode,versionId+""},
				null,null,"seq_num asc");
		
		while(cursor.moveToNext()){
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
			answerList.add(answer);
		}
		
		cursor.close();
		
		return answerList;
	}
}
