package com.huasheng.sysq.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.huasheng.sysq.model.InterviewAnswer;
import com.huasheng.sysq.util.ColumnConstants;
import com.huasheng.sysq.util.SysQOpenHelper;
import com.huasheng.sysq.util.TableConstants;

public class InterviewAnswerDB {

	public static int insert(InterviewAnswer interviewAnswer){
		ContentValues values = fillDBFromObject(interviewAnswer);
		return (int)SysQOpenHelper.getDatabase().insert(TableConstants.TABLE_INTERVIEW_ANSWER, null, values);
	}
	
	public static List<InterviewAnswer> selectByQuestion(int interviewBasicId,String questionCode){
		Cursor cursor = SysQOpenHelper.getDatabase().query(
				TableConstants.TABLE_INTERVIEW_ANSWER,
				null,
				ColumnConstants.COLUMN_INTERVIEW_ANSWER_INTERVIEW_BASIC_ID + "=?" + " and " + ColumnConstants.COLUMN_INTERVIEW_ANSWER_QUESTION_CODE + "=?",
				new String[]{interviewBasicId + "",questionCode},
				null,null, ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_SEQ_NUM + " asc");
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
		interviewAnswer.setInterviewBasicId(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_ANSWER_INTERVIEW_BASIC_ID)));
		interviewAnswer.setQuestionCode(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_ANSWER_QUESTION_CODE)));
		interviewAnswer.setAnswerLabel(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_LABEL)));
		interviewAnswer.setAnswerCode(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_CODE)));
		interviewAnswer.setAnswerValue(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_VALUE)));
		interviewAnswer.setAnswerText(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_TEXT)));
		interviewAnswer.setAnswerSeqNum(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_SEQ_NUM)));
		interviewAnswer.setVersionId(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_ANSWER_VERSION_ID)));
		return interviewAnswer;
	}
	
	private static ContentValues fillDBFromObject(InterviewAnswer interviewAnswer){
		ContentValues values = new ContentValues();
		values.put(ColumnConstants.COLUMN_INTERVIEW_ANSWER_INTERVIEW_BASIC_ID, interviewAnswer.getInterviewBasicId());
		values.put(ColumnConstants.COLUMN_INTERVIEW_ANSWER_QUESTION_CODE, interviewAnswer.getQuestionCode());
		values.put(ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_LABEL, interviewAnswer.getAnswerLabel());
		values.put(ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_CODE, interviewAnswer.getAnswerCode());
		values.put(ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_VALUE, interviewAnswer.getAnswerValue());
		values.put(ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_TEXT, interviewAnswer.getAnswerText());
		values.put(ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_SEQ_NUM, interviewAnswer.getAnswerSeqNum());
		values.put(ColumnConstants.COLUMN_INTERVIEW_ANSWER_VERSION_ID, interviewAnswer.getVersionId());
		return values;
	}
}
