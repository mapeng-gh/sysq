package com.huasheng.sysq.db;

import android.content.ContentValues;

import com.huasheng.sysq.model.InterviewAnswer;
import com.huasheng.sysq.model.InterviewQuestion;
import com.huasheng.sysq.util.ColumnConstants;
import com.huasheng.sysq.util.TableConstants;

public class InterviewAnswerDB {

	public static int insert(InterviewAnswer interviewAnswer){
		ContentValues values = fillDBFromObject(interviewAnswer);
		return (int)SysQOpenHelper.getDatabase().insert(TableConstants.TABLE_INTERVIEW_ANSWER, null, values);
	}
	
	private static ContentValues fillDBFromObject(InterviewAnswer interviewAnswer){
		ContentValues values = new ContentValues();
		values.put(ColumnConstants.COLUMN_INTERVIEW_ANSWER_INTERVIEW_BASIC_ID, interviewAnswer.getInterviewBasicId());
		values.put(ColumnConstants.COLUMN_INTERVIEW_ANSWER_QUESTION_CODE, interviewAnswer.getQuestionCode());
		values.put(ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_CODE, interviewAnswer.getAnswerCode());
		values.put(ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_VALUE, interviewAnswer.getAnswerValue());
		values.put(ColumnConstants.COLUMN_INTERVIEW_ANSWER_VERSION_ID, interviewAnswer.getVersionId());
		return values;
	}
}
