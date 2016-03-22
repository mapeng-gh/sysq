package com.huasheng.sysq.db;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.huasheng.sysq.model.Question;
import com.huasheng.sysq.util.TableConstants;

public class QuestionDB {

	public static List<Question> getList(String questionaireCode,int versionId){
		Cursor cursor = SysQOpenHelper.getDatabase().query(
				TableConstants.TABLE_QUESTION,
				null,
				TableConstants.COLUMN_QUESTION_QUESTIONAIRE_CODE + " = ? and " +TableConstants.COLUMN_QUESTION_VERSION_ID + " = ?",
				new String[]{questionaireCode,versionId+""},
				null,null,TableConstants.COLUMN_QUESTION_SEQ_NUM + " asc");
		List<Question> questionList = new ArrayList<Question>();
		while(cursor.moveToNext()){
			Question question = fill(cursor);
			questionList.add(question);
		}
		cursor.close();
		return questionList;
	}
	
	public static List<Question> getSubList(String questionCode,int versionId){
		List<Question> subQuesList = new ArrayList<Question>();
		
		Cursor cursor = SysQOpenHelper.getDatabase().query(
				TableConstants.TABLE_QUESTION,
				null,
				TableConstants.COLUMN_QUESTION_P_QUESTION_CODE + " = ? and " + TableConstants.COLUMN_QUESTION_VERSION_ID +" = ?",
				new String[]{questionCode,versionId+""},
				null,null,TableConstants.COLUMN_QUESTION_SEQ_NUM + " asc");
		
		while(cursor.moveToNext()){
			Question question = fill(cursor);
			subQuesList.add(question);
		}
		
		cursor.close();
		
		return subQuesList;
	}
	
	private static Question fill(Cursor cursor){
		Question question = new Question();
		question.setId(cursor.getInt(cursor.getColumnIndex("id")));
		question.setCode(cursor.getString(cursor.getColumnIndex(TableConstants.COLUMN_QUESTION_CODE)));
		question.setDescription(cursor.getString(cursor.getColumnIndex(TableConstants.COLUMN_QUESTION_DESCRIPTION)));
		question.setRemark(cursor.getString(cursor.getColumnIndex(TableConstants.COLUMN_QUESTION_REMARK)));
		question.setType(cursor.getString(cursor.getColumnIndex(TableConstants.COLUMN_QUESTION_TYPE)));
		question.setShowType(cursor.getString(cursor.getColumnIndex(TableConstants.COLUMN_QUESTION_SHOW_TYPE)));
		question.setIsEnd(cursor.getInt(cursor.getColumnIndex(TableConstants.COLUMN_QUESTION_IS_END)));
		question.setSeqNum(cursor.getInt(cursor.getColumnIndex(TableConstants.COLUMN_QUESTION_SEQ_NUM)));
		question.setQuestionaireCode(cursor.getString(cursor.getColumnIndex(TableConstants.COLUMN_QUESTION_QUESTIONAIRE_CODE)));
		question.setParentQuesCode(cursor.getString(cursor.getColumnIndex(TableConstants.COLUMN_QUESTION_P_QUESTION_CODE)));
		question.setEntryLogic(cursor.getString(cursor.getColumnIndex(TableConstants.COLUMN_QUESTION_ENTRY_LOGIC)));
		question.setExitLogic(cursor.getString(cursor.getColumnIndex(TableConstants.COLUMN_QUESTION_EXIT_LOGIC)));
		question.setVersionId(cursor.getInt(cursor.getColumnIndex(TableConstants.COLUMN_QUESTION_VERSION_ID)));
		return question;
	}
}
