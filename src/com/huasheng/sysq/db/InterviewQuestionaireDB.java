package com.huasheng.sysq.db;

import android.content.ContentValues;

import com.huasheng.sysq.model.InterviewQuestionaire;
import com.huasheng.sysq.util.ColumnConstants;
import com.huasheng.sysq.util.TableConstants;

public class InterviewQuestionaireDB {

	public static int insert(InterviewQuestionaire interviewQuestionaire){
		ContentValues values = fillDBFromObject(interviewQuestionaire);
		return (int)SysQOpenHelper.getDatabase().insert(TableConstants.TABLE_INTERVIEW_QUESTIONAIRE, null, values);
	}
	
	private static ContentValues fillDBFromObject(InterviewQuestionaire interviewQuestionaire){
		ContentValues values = new ContentValues();
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_INTERVIEW_BASIC_ID, interviewQuestionaire.getInterviewBasicId());
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_QUESTIONAIRE_CODE, interviewQuestionaire.getQuestionaireCode());
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_START_TIME, interviewQuestionaire.getStartTime());
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_END_TIME, interviewQuestionaire.getEndTime());
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_VERSION_ID, interviewQuestionaire.getVersionId());
		return values;
	}
}
