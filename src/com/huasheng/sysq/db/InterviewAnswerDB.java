package com.huasheng.sysq.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.huasheng.sysq.model.InterviewAnswer;
import com.huasheng.sysq.util.db.DBConstants;
import com.huasheng.sysq.util.db.SysQOpenHelper;

public class InterviewAnswerDB {

	public static int insert(InterviewAnswer interviewAnswer){
		ContentValues values = fillDBFromObject(interviewAnswer);
		return (int)SysQOpenHelper.getDatabase().insert(DBConstants.TABLE_INTERVIEW_ANSWER, null, values);
	}
	
	/**
	 * 删除：访谈记录ID
	 * @param interviewBasicId
	 */
	public static void delete(int interviewBasicId){
		SysQOpenHelper.getDatabase().delete(
				DBConstants.TABLE_INTERVIEW_ANSWER, 
				DBConstants.COLUMN_INTERVIEW_ANSWER_INTERVIEW_BASIC_ID + "=?",
				new String[]{interviewBasicId + ""});
	}
	
	/**
	 * 删除：访谈记录ID、访谈问题Code
	 * @param interviewBasicId
	 * @param questionCode
	 */
	public static void deleteByInterviewQuestion(int interviewBasicId,String questionCode){
		SysQOpenHelper.getDatabase().delete(DBConstants.TABLE_INTERVIEW_ANSWER, DBConstants.COLUMN_INTERVIEW_ANSWER_INTERVIEW_BASIC_ID + "=?" + " and " + DBConstants.COLUMN_INTERVIEW_ANSWER_QUESTION_CODE + "=?",new String[]{interviewBasicId + "",questionCode});
	}
	
	public static void update(InterviewAnswer interviewAnswer){
		ContentValues values = fillDBFromObject(interviewAnswer);
		SysQOpenHelper.getDatabase().update(DBConstants.TABLE_INTERVIEW_ANSWER, values,"id=?",new String[]{interviewAnswer.getId()+""});
	}
	
	public static List<InterviewAnswer> selectByQuestion(int interviewBasicId,String questionCode){
		Cursor cursor = SysQOpenHelper.getDatabase().query(
				DBConstants.TABLE_INTERVIEW_ANSWER,
				null,
				DBConstants.COLUMN_INTERVIEW_ANSWER_INTERVIEW_BASIC_ID + "=?" + " and " + DBConstants.COLUMN_INTERVIEW_ANSWER_QUESTION_CODE + "=?",
				new String[]{interviewBasicId + "",questionCode},
				null,null, DBConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_SEQ_NUM + " asc");
		List<InterviewAnswer> interviewAnswerList = new ArrayList<InterviewAnswer>();
		while(cursor.moveToNext()){
			InterviewAnswer interviewAnswer = fillObjectFromDB(cursor);
			interviewAnswerList.add(interviewAnswer);
		}
		cursor.close();
		return interviewAnswerList;
	}
	
	public static List<InterviewAnswer> selectByInterview(int interviewBasicId){
		Cursor cursor = SysQOpenHelper.getDatabase().query(
				DBConstants.TABLE_INTERVIEW_ANSWER,
				null,
				DBConstants.COLUMN_INTERVIEW_ANSWER_INTERVIEW_BASIC_ID + "=?",
				new String[]{interviewBasicId + ""},
				null,null, DBConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_SEQ_NUM + " asc");
		List<InterviewAnswer> interviewAnswerList = new ArrayList<InterviewAnswer>();
		while(cursor.moveToNext()){
			InterviewAnswer interviewAnswer = fillObjectFromDB(cursor);
			interviewAnswerList.add(interviewAnswer);
		}
		cursor.close();
		return interviewAnswerList;
	}
	
	private static InterviewAnswer fillObjectFromDB(Cursor cursor){
		InterviewAnswer interviewAnswer = new InterviewAnswer();
		interviewAnswer.setId(cursor.getInt(cursor.getColumnIndex("id")));
		interviewAnswer.setInterviewBasicId(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_ANSWER_INTERVIEW_BASIC_ID)));
		interviewAnswer.setQuestionCode(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_ANSWER_QUESTION_CODE)));
		interviewAnswer.setAnswerLabel(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_LABEL)));
		interviewAnswer.setAnswerCode(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_CODE)));
		interviewAnswer.setAnswerValue(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_VALUE)));
		interviewAnswer.setAnswerText(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_TEXT)));
		interviewAnswer.setAnswerSeqNum(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_SEQ_NUM)));
		interviewAnswer.setVersionId(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_ANSWER_VERSION_ID)));
		return interviewAnswer;
	}
	
	private static ContentValues fillDBFromObject(InterviewAnswer interviewAnswer){
		ContentValues values = new ContentValues();
		values.put(DBConstants.COLUMN_INTERVIEW_ANSWER_INTERVIEW_BASIC_ID, interviewAnswer.getInterviewBasicId());
		values.put(DBConstants.COLUMN_INTERVIEW_ANSWER_QUESTION_CODE, interviewAnswer.getQuestionCode());
		values.put(DBConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_LABEL, interviewAnswer.getAnswerLabel());
		values.put(DBConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_CODE, interviewAnswer.getAnswerCode());
		values.put(DBConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_VALUE, interviewAnswer.getAnswerValue());
		values.put(DBConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_TEXT, interviewAnswer.getAnswerText());
		values.put(DBConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_SEQ_NUM, interviewAnswer.getAnswerSeqNum());
		values.put(DBConstants.COLUMN_INTERVIEW_ANSWER_VERSION_ID, interviewAnswer.getVersionId());
		return values;
	}
}
