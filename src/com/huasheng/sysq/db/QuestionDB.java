package com.huasheng.sysq.db;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.huasheng.sysq.model.Question;
import com.huasheng.sysq.util.ColumnConstants;
import com.huasheng.sysq.util.TableConstants;

public class QuestionDB {

	public static List<Question> getList(String questionaireCode,int versionId,int isEnd){
		Cursor cursor = SysQOpenHelper.getDatabase().query(
				TableConstants.TABLE_QUESTION,
				null,
				ColumnConstants.COLUMN_QUESTION_QUESTIONAIRE_CODE + " = ? and " +ColumnConstants.COLUMN_QUESTION_VERSION_ID + " = ? and " + ColumnConstants.COLUMN_QUESTION_IS_END + " = ?",
				new String[]{questionaireCode,versionId+"",isEnd+""},
				null,null,ColumnConstants.COLUMN_QUESTION_SEQ_NUM + " asc");
		List<Question> questionList = new ArrayList<Question>();
		while(cursor.moveToNext()){
			Question question = fill(cursor);
			questionList.add(question);
		}
		cursor.close();
		return questionList;
	}
	
	private static Question fill(Cursor cursor){
		Question question = new Question();
		question.setId(cursor.getInt(cursor.getColumnIndex("id")));
		question.setCode(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_QUESTION_CODE)));
		question.setDescription(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_QUESTION_DESCRIPTION)));
		question.setRemark(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_QUESTION_REMARK)));
		question.setIsEnd(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_QUESTION_IS_END)));
		question.setSeqNum(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_QUESTION_SEQ_NUM)));
		question.setQuestionaireCode(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_QUESTION_QUESTIONAIRE_CODE)));
		question.setEntryLogic(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_QUESTION_ENTRY_LOGIC)));
		question.setExitLogic(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_QUESTION_EXIT_LOGIC)));
		question.setVersionId(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_QUESTION_VERSION_ID)));
		return question;
	}
}
