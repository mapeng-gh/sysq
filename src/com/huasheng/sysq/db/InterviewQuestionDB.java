package com.huasheng.sysq.db;

import android.content.ContentValues;

import com.huasheng.sysq.model.InterviewQuestion;
import com.huasheng.sysq.util.ColumnConstants;
import com.huasheng.sysq.util.TableConstants;

public class InterviewQuestionDB {

	public static int insert(InterviewQuestion interviewQuestion){
		ContentValues values = fillDBFromObject(interviewQuestion);
		return (int)SysQOpenHelper.getDatabase().insert(TableConstants.TABLE_INTERVIEW_QUESTION, null, values);
	}
	
	private static ContentValues fillDBFromObject(InterviewQuestion interviewQuestion){
		ContentValues values = new ContentValues();
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTION_INTERVIEW_BASIC_ID, interviewQuestion.getInterviewBasicId());
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTION_QUESTIONAIRE_CODE, interviewQuestion.getQuestionaireCode());
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTION_QUESTION_CODE, interviewQuestion.getQuestionCode());
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTION_VERSION_ID, interviewQuestion.getVersionId());
		return values;
	}
}
