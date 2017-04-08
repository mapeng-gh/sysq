package com.huasheng.sysq.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.huasheng.sysq.model.InterviewQuestionaire;
import com.huasheng.sysq.util.db.DBConstants;
import com.huasheng.sysq.util.db.SysQOpenHelper;

public class InterviewQuestionaireDB {

	public static int insert(InterviewQuestionaire interviewQuestionaire){
		ContentValues values = fillDBFromObject(interviewQuestionaire);
		return (int)SysQOpenHelper.getDatabase().insert(DBConstants.TABLE_INTERVIEW_QUESTIONAIRE, null, values);
	}
	
	public static void update(InterviewQuestionaire interviewQuestionaire){
		ContentValues values = fillDBFromObject(interviewQuestionaire);
		SysQOpenHelper.getDatabase().update(DBConstants.TABLE_INTERVIEW_QUESTIONAIRE, values, "id=?", new String[]{interviewQuestionaire.getId()+""});
	}
	
	public static void delete(int interviewBasicId,String questionaireCode){
		SysQOpenHelper.getDatabase().delete(
				DBConstants.TABLE_INTERVIEW_QUESTIONAIRE, 
				DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_INTERVIEW_BASIC_ID + "=?" + " and " + DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_QUESTIONAIRE_CODE + "=?", 
				new String[]{interviewBasicId + "",questionaireCode});
	}
	
	/**
	 * 查询访问记录下所有问卷记录
	 * @param interviewBasicId
	 * @param versionId
	 * @return
	 */
	public static List<InterviewQuestionaire> selectByInterviewBasicId(int interviewBasicId){
		Cursor cursor = SysQOpenHelper.getDatabase().query(
				DBConstants.TABLE_INTERVIEW_QUESTIONAIRE,
				null,
				DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_INTERVIEW_BASIC_ID + "=?",
				new String[]{interviewBasicId + ""},
				null,null,"seq_num asc");
		
		List<InterviewQuestionaire> interviewQuestionaireList = new ArrayList<InterviewQuestionaire>();
		while(cursor.moveToNext()){
			InterviewQuestionaire interviewQuestionaire = fillObjectFromDB(cursor);
			interviewQuestionaireList.add(interviewQuestionaire);
		}
		cursor.close();
		
		return interviewQuestionaireList;
	}
	
	/**
	 * 查询访谈记录下某个访谈问卷
	 * @param interviewBasicId
	 * @param questionaireCode
	 * @return
	 */
	public static InterviewQuestionaire select(int interviewBasicId,String questionaireCode){
		Cursor cursor = SysQOpenHelper.getDatabase().query(
				DBConstants.TABLE_INTERVIEW_QUESTIONAIRE,
				null,
				DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_INTERVIEW_BASIC_ID + "=?" + " and " + DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_QUESTIONAIRE_CODE + "=?",
				new String[]{interviewBasicId + "",questionaireCode},
				null,null,"seq_num asc");
		InterviewQuestionaire interviewQuestionaire = null;
		if(cursor.moveToNext()){
			interviewQuestionaire = fillObjectFromDB(cursor);
		}
		cursor.close();
		return interviewQuestionaire;
	}
	
	private static InterviewQuestionaire fillObjectFromDB(Cursor cursor){
		InterviewQuestionaire interviewQuestionaire = new InterviewQuestionaire();
		interviewQuestionaire.setId(cursor.getInt(cursor.getColumnIndex("id")));
		interviewQuestionaire.setInterviewBasicId(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_INTERVIEW_BASIC_ID)));
		interviewQuestionaire.setQuestionaireCode(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_QUESTIONAIRE_CODE)));
		interviewQuestionaire.setStartTime(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_START_TIME)));
		interviewQuestionaire.setLastModifiedTime(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_LAST_MODIFIED_TIME)));
		interviewQuestionaire.setStatus(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_STATUS)));
		interviewQuestionaire.setSeqNum(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_SEQ_NUM)));
		interviewQuestionaire.setVersionId(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_VERSION_ID)));
		interviewQuestionaire.setRemark(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_REMARK)));
		return interviewQuestionaire;
	}
	
	private static ContentValues fillDBFromObject(InterviewQuestionaire interviewQuestionaire){
		ContentValues values = new ContentValues();
		values.put(DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_INTERVIEW_BASIC_ID, interviewQuestionaire.getInterviewBasicId());
		values.put(DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_QUESTIONAIRE_CODE, interviewQuestionaire.getQuestionaireCode());
		values.put(DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_START_TIME, interviewQuestionaire.getStartTime());
		values.put(DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_LAST_MODIFIED_TIME, interviewQuestionaire.getLastModifiedTime());
		values.put(DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_STATUS, interviewQuestionaire.getStatus());
		values.put(DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_SEQ_NUM, interviewQuestionaire.getSeqNum());
		values.put(DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_VERSION_ID, interviewQuestionaire.getVersionId());
		values.put(DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_REMARK, interviewQuestionaire.getRemark());
		return values;
	}
}
