package com.huasheng.sysq.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.huasheng.sysq.model.InterviewQuestion;
import com.huasheng.sysq.util.db.DBConstants;
import com.huasheng.sysq.util.db.SysQOpenHelper;

public class InterviewQuestionDB {

	public static int insert(InterviewQuestion interviewQuestion){
		ContentValues values = fillDBFromObject(interviewQuestion);
		return (int)SysQOpenHelper.getDatabase().insert(DBConstants.TABLE_INTERVIEW_QUESTION, null, values);
	}
	
	/**
	 * 删除：访谈记录ID
	 * @param interviewBasicId
	 */
	public static void delete(int interviewBasicId){
		SysQOpenHelper.getDatabase().delete(
				DBConstants.TABLE_INTERVIEW_QUESTION,
				DBConstants.COLUMN_INTERVIEW_QUESTION_INTERVIEW_BASIC_ID + "=?", 
				new String[]{interviewBasicId + ""});
	}
	
	/**
	 * 删除：访谈记录ID、访谈问卷Code
	 * @param interviewBasicId
	 * @param questionaireCode
	 */
	public static void deleteByInterviewQuestionaire(int interviewBasicId,String questionaireCode){
		SysQOpenHelper.getDatabase().delete(DBConstants.TABLE_INTERVIEW_QUESTION, DBConstants.COLUMN_INTERVIEW_QUESTION_INTERVIEW_BASIC_ID + "=?" + " and " + DBConstants.COLUMN_INTERVIEW_QUESTION_QUESTIONAIRE_CODE + "=?", new String[]{interviewBasicId + "",questionaireCode});
	}
	
	/**
	 * 删除：访谈记录ID、访谈问题Code
	 * @param interviewBasicId
	 * @param questionCode
	 */
	public static void delete(int interviewBasicId,String questionCode){
		SysQOpenHelper.getDatabase().delete(
				DBConstants.TABLE_INTERVIEW_QUESTION,
				DBConstants.COLUMN_INTERVIEW_QUESTION_INTERVIEW_BASIC_ID + "=?" + " and " + DBConstants.COLUMN_INTERVIEW_QUESTION_QUESTION_CODE + "=?",
				new String[]{interviewBasicId + "",questionCode});
	}
	
	/**
	 * 查询列表：访谈记录ID、访谈问卷Code
	 * @param interviewBasicId
	 * @param questionaireCode
	 * @return
	 */
	public static List<InterviewQuestion> selectByQuestionaire(int interviewBasicId,String questionaireCode){
		Cursor cursor = SysQOpenHelper.getDatabase().query(
				DBConstants.TABLE_INTERVIEW_QUESTION,
				null,
				DBConstants.COLUMN_INTERVIEW_QUESTION_INTERVIEW_BASIC_ID + "=?" + " and " + DBConstants.COLUMN_INTERVIEW_QUESTION_QUESTIONAIRE_CODE + "=?",
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
		interviewQuestion.setInterviewBasicId(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_QUESTION_INTERVIEW_BASIC_ID)));
		interviewQuestion.setQuestionaireCode(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_QUESTION_QUESTIONAIRE_CODE)));
		interviewQuestion.setQuestionCode(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_QUESTION_QUESTION_CODE)));
		interviewQuestion.setSeqNum(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_QUESTION_SEQ_NUM)));
		interviewQuestion.setVersionId(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_QUESTION_VERSION_ID)));
		return interviewQuestion;
	}
	
	private static ContentValues fillDBFromObject(InterviewQuestion interviewQuestion){
		ContentValues values = new ContentValues();
		values.put(DBConstants.COLUMN_INTERVIEW_QUESTION_INTERVIEW_BASIC_ID, interviewQuestion.getInterviewBasicId());
		values.put(DBConstants.COLUMN_INTERVIEW_QUESTION_QUESTIONAIRE_CODE, interviewQuestion.getQuestionaireCode());
		values.put(DBConstants.COLUMN_INTERVIEW_QUESTION_QUESTION_CODE, interviewQuestion.getQuestionCode());
		values.put(DBConstants.COLUMN_INTERVIEW_QUESTION_SEQ_NUM, interviewQuestion.getSeqNum());
		values.put(DBConstants.COLUMN_INTERVIEW_QUESTION_VERSION_ID, interviewQuestion.getVersionId());
		return values;
	}
}
