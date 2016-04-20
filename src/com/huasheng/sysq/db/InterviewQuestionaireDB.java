package com.huasheng.sysq.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.huasheng.sysq.model.InterviewQuestionaire;
import com.huasheng.sysq.util.ColumnConstants;
import com.huasheng.sysq.util.SysQOpenHelper;
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
	
	/**
	 * 查询访问记录下所有问卷记录
	 * @param interviewBasicId
	 * @param versionId
	 * @return
	 */
	public static List<InterviewQuestionaire> selectByInterviewBasicId(int interviewBasicId,int versionId){
		Cursor cursor = SysQOpenHelper.getDatabase().query(
				TableConstants.TABLE_INTERVIEW_QUESTIONAIRE,
				null,
				ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_INTERVIEW_BASIC_ID + "=?" + " and " + "version_id=?",
				new String[]{interviewBasicId + "" , versionId + ""},
				null,null,"seq_num asc");
		
		List<InterviewQuestionaire> interviewQuestionaireList = new ArrayList<InterviewQuestionaire>();
		while(cursor.moveToNext()){
			InterviewQuestionaire interviewQuestionaire = fillObjectFromDB(cursor);
			interviewQuestionaireList.add(interviewQuestionaire);
		}
		cursor.close();
		
		return interviewQuestionaireList;
	}
	
	private static InterviewQuestionaire fillObjectFromDB(Cursor cursor){
		InterviewQuestionaire interviewQuestionaire = new InterviewQuestionaire();
		interviewQuestionaire.setId(cursor.getInt(cursor.getColumnIndex("id")));
		interviewQuestionaire.setInterviewBasicId(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_INTERVIEW_BASIC_ID)));
		interviewQuestionaire.setQuestionaireCode(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_QUESTIONAIRE_CODE)));
		interviewQuestionaire.setStartTime(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_START_TIME)));
		interviewQuestionaire.setLastModifiedTime(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_LAST_MODIFIED_TIME)));
		interviewQuestionaire.setStatus(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_STATUS)));
		interviewQuestionaire.setSeqNum(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_SEQ_NUM)));
		interviewQuestionaire.setVersionId(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_VERSION_ID)));
		return interviewQuestionaire;
	}
	
	private static ContentValues fillDBFromObject(InterviewQuestionaire interviewQuestionaire){
		ContentValues values = new ContentValues();
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_INTERVIEW_BASIC_ID, interviewQuestionaire.getInterviewBasicId());
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_QUESTIONAIRE_CODE, interviewQuestionaire.getQuestionaireCode());
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_START_TIME, interviewQuestionaire.getStartTime());
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_LAST_MODIFIED_TIME, interviewQuestionaire.getLastModifiedTime());
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_STATUS, interviewQuestionaire.getStatus());
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_SEQ_NUM, interviewQuestionaire.getSeqNum());
		values.put(ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_VERSION_ID, interviewQuestionaire.getVersionId());
		return values;
	}
}
