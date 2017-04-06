package com.huasheng.sysq.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.huasheng.sysq.model.InterviewQuestion;
import com.huasheng.sysq.util.db.ColumnConstants;
import com.huasheng.sysq.util.db.SysQOpenHelper;
import com.huasheng.sysq.util.db.TableConstants;

public class InterviewQuestionDB {

	public static int insert(InterviewQuestion interviewQuestion){
		ContentValues values = fillDBFromObject(interviewQuestion);
		return (int)SysQOpenHelper.getDatabase().insert(TableConstants.TABLE_INTERVIEW_QUESTION, null, values);
	}
	
	public static void deleteByInterviewQuestionaire(int interviewBasicId,String questionaireCode){
		SysQOpenHelper.getDatabase().delete(TableConstants.TABLE_INTERVIEW_QUESTION, ColumnConstants.COLUMN_INTERVIEW_QUESTION_INTERVIEW_BASIC_ID + "=?" + " and " + ColumnConstants.COLUMN_INTERVIEW_QUESTION_QUESTIONAIRE_CODE + "=?", new String[]{interviewBasicId + "",questionaireCode});
	}
	
	public static void delete(int interviewBasicId,String questionCode){
		SysQOpenHelper.getDatabase().delete(
				TableConstants.TABLE_INTERVIEW_QUESTION,
				ColumnConstants.COLUMN_INTERVIEW_QUESTION_INTERVIEW_BASIC_ID + "=?" + " and " + ColumnConstants.COLUMN_INTERVIEW_QUESTION_QUESTION_CODE + "=?",
				new String[]{interviewBasicId + "",questionCode});
	}
	
	public static List<InterviewQuestion> selectByQuestionaire(int interviewBasicId,String questionaireCode){
		Cursor cursor = SysQOpenHelper.getDatabase().query(
				TableConstants.TABLE_INTERVIEW_QUESTION,
				null,
				ColumnConstants.COLUMN_INTERVIEW_QUESTION_INTERVIEW_BASIC_ID + "=?" + " and " + ColumnConstants.COLUMN_INTERVIEW_QUESTION_QUESTIONAIRE_CODE + "=?",
			    new String[]{interviewBasicId + "",questionaireCode},
			    null,null,"seq_num asc");
		List<InterviewQuestion> interviewQuestionList = new ArrayList<InterviewQuestion>();
		while(cursor.moveToNext()){
			InterviewQuestion interviewQuestion = fillObjectFromDB(cursor);
			interviewQuestionList.add(interviewQuestion);
		}
		cursor.close();
		return interviewQuestionList;
	}
	
	private static InterviewQuestion fillObjectFromDB(Cursor cursor){
		InterviewQuestion interviewQuestion = new InterviewQuestion();
		interviewQuestion.setId(cursor.getInt(cursor.getColumnIndex("id")));
		interviewQuestion.setInterviewBasicId(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_QUESTION_INTERVIEW_BASIC_ID)));
		interviewQuestion.setQuestionaireCode(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_QUESTION_QUESTIONAIRE_CODE)));
		interviewQuestion.setQuestionCode(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_QUESTION_QUESTION_CODE)));
		interviewQuestion.setSeqNum(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_QUESTION_SEQ_NUM)));
		interviewQuestion.setVersionId(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_QUESTION_VERSION_ID)));
		return interviewQuestion;
	}
	
	private static ContentValues fillDBFromObject(InterviewQuestion interviewQuestion){
		ContentValues values = new ContentValues();
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTION_INTERVIEW_BASIC_ID, interviewQuestion.getInterviewBasicId());
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTION_QUESTIONAIRE_CODE, interviewQuestion.getQuestionaireCode());
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTION_QUESTION_CODE, interviewQuestion.getQuestionCode());
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTION_SEQ_NUM, interviewQuestion.getSeqNum());
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTION_VERSION_ID, interviewQuestion.getVersionId());
		return values;
	}
}
