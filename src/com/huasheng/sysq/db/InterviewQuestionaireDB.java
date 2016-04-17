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
	
	public static void update(InterviewQuestionaire interviewQuestionaire){
		ContentValues values = fillDBFromObject(interviewQuestionaire);
		SysQOpenHelper.getDatabase().update(TableConstants.TABLE_INTERVIEW_QUESTIONAIRE, values, "id=?", new String[]{interviewQuestionaire.getId()+""});
	}
	
	private static ContentValues fillDBFromObject(InterviewQuestionaire interviewQuestionaire){
		ContentValues values = new ContentValues();
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_INTERVIEW_BASIC_ID, interviewQuestionaire.getInterviewBasicId());
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_QUESTIONAIRE_CODE, interviewQuestionaire.getQuestionaireCode());
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_START_TIME, interviewQuestionaire.getStartTime());
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_LAST_MODIFIED_TIME, interviewQuestionaire.getLastModifiedTime());
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_STATUS, interviewQuestionaire.getStatus());
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_VERSION_ID, interviewQuestionaire.getVersionId());
		return values;
	}
}
