package com.huasheng.sysq.db;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.huasheng.sysq.model.Question;
import com.huasheng.sysq.util.db.DBConstants;
import com.huasheng.sysq.util.db.SysQOpenHelper;

public class QuestionDB {

	public static List<Question> getList(String questionaireCode,int versionId,int isEnd){
		Cursor cursor = SysQOpenHelper.getDatabase().query(
				DBConstants.TABLE_QUESTION,
				null,
				DBConstants.COLUMN_QUESTION_QUESTIONAIRE_CODE + " = ? and " +DBConstants.COLUMN_QUESTION_VERSION_ID + " = ? and " + DBConstants.COLUMN_QUESTION_IS_END + " = ?",
				new String[]{questionaireCode,versionId+"",isEnd+""},
				null,null,DBConstants.COLUMN_QUESTION_SEQ_NUM + " asc");
		List<Question> questionList = new ArrayList<Question>();
		while(cursor.moveToNext()){
			Question question = fillObjectFromDB(cursor);
			questionList.add(question);
		}
		cursor.close();
		return questionList;
	}
	
	public static Question selectByCode(String questionCode,int versionId){
		Cursor cursor = SysQOpenHelper.getDatabase().query(
				DBConstants.TABLE_QUESTION,
				null,
				DBConstants.COLUMN_QUESTION_CODE + "=?" + " and " + DBConstants.COLUMN_QUESTION_VERSION_ID + "=?",
				new String[]{questionCode,versionId + ""},
				null,null,null);
		Question question = null;
		if(cursor.moveToNext()){
			question = fillObjectFromDB(cursor);
		}
		cursor.close();
		return question;
	}
	
	private static Question fillObjectFromDB(Cursor cursor){
		Question question = new Question();
		question.setId(cursor.getInt(cursor.getColumnIndex("id")));
		question.setCode(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_QUESTION_CODE)));
		question.setDescription(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_QUESTION_DESCRIPTION)));
		question.setRemark(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_QUESTION_REMARK)));
		question.setIsEnd(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_QUESTION_IS_END)));
		question.setSeqNum(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_QUESTION_SEQ_NUM)));
		question.setQuestionaireCode(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_QUESTION_QUESTIONAIRE_CODE)));
		question.setEntryLogic(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_QUESTION_ENTRY_LOGIC)));
		question.setExitLogic(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_QUESTION_EXIT_LOGIC)));
		question.setVersionId(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_QUESTION_VERSION_ID)));
		question.setAssociateQuestionCode(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_QUESTION_ASSOCIATE_QUESTION_CODE)));
		return question;
	}
}
