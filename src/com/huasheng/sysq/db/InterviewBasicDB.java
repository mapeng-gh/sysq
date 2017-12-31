package com.huasheng.sysq.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.util.CommonUtils;
import com.huasheng.sysq.util.db.DBConstants;
import com.huasheng.sysq.util.db.SysQOpenHelper;

public class InterviewBasicDB {

	public static void insert(InterviewBasic interviewBasic){
		interviewBasic.setId(CommonUtils.getCurrentSeconds());
		ContentValues values = fillDBFromObject(interviewBasic);
		SysQOpenHelper.getDatabase().insert(DBConstants.TABLE_INTERVIEW_BASIC, null, values);
	}
	
	public static void update(InterviewBasic interviewBasic){
		ContentValues values = fillDBFromObject(interviewBasic);
		SysQOpenHelper.getDatabase().update(DBConstants.TABLE_INTERVIEW_BASIC, values,"id = ?",new String[]{interviewBasic.getId()+""});
	}
	
	/**
	 * 删除：主键ID
	 * @param id
	 */
	public static void deleteById(int id){
		SysQOpenHelper.getDatabase().delete(DBConstants.TABLE_INTERVIEW_BASIC,"id=?",new String[]{id+""});
	}
	
	public static InterviewBasic selectById(int id){
		Cursor cursor = SysQOpenHelper.getDatabase().query(DBConstants.TABLE_INTERVIEW_BASIC, null, "id = ?", new String[]{id + ""}, null, null, null);
		InterviewBasic interviewBasic = null;
		if(cursor.moveToNext()){
			interviewBasic = fillObjectFromDB(cursor);
		}
		return interviewBasic;
	}
	
	public static List<InterviewBasic> getList(int versionId){
		List<InterviewBasic> interviewBasicList = new ArrayList<InterviewBasic>();
		Cursor cursor = SysQOpenHelper.getDatabase().query(DBConstants.TABLE_INTERVIEW_BASIC, null, "version_id = ?", new String[]{versionId + ""}, null, null,null);
		while(cursor.moveToNext()){
			InterviewBasic interviewBasic = fillObjectFromDB(cursor);
			interviewBasicList.add(interviewBasic);
		}
		cursor.close();
		return interviewBasicList;
	}
	
	public static List<InterviewBasic> getList(int versionId,int interviewerId){
		List<InterviewBasic> interviewBasicList = new ArrayList<InterviewBasic>();
		Cursor cursor = SysQOpenHelper.getDatabase().query(DBConstants.TABLE_INTERVIEW_BASIC, null, DBConstants.COLUMN_INTERVIEW_BASIC_VERSION_ID + " = ? and " + DBConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID + " = ?", new String[]{versionId + "",interviewerId + ""}, null, null,null);
		while(cursor.moveToNext()){
			InterviewBasic interviewBasic = fillObjectFromDB(cursor);
			interviewBasicList.add(interviewBasic);
		}
		cursor.close();
		return interviewBasicList;
	}
	
	private static InterviewBasic fillObjectFromDB(Cursor cursor){
		
		InterviewBasic interviewBasic = new InterviewBasic();
		interviewBasic.setId(cursor.getInt(cursor.getColumnIndex("id")));
		interviewBasic.setIntervieweeId(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWEE_ID)));
		interviewBasic.setInterviewerId(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID)));
		interviewBasic.setType(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_BASIC_TYPE)));
		interviewBasic.setIsTest(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_BASIC_IS_TEST)));
		interviewBasic.setStartTime(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_BASIC_START_TIME)));
		interviewBasic.setStatus(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_BASIC_STATUS)));
		interviewBasic.setCurQuestionaireCode(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_BASIC_CUR_QUESTIONAIRE_CODE)));
		interviewBasic.setNextQuestionCode(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_BASIC_NEXT_QUESTION_CODE)));
		interviewBasic.setLastModifiedTime(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_BASIC_LAST_MODIFYED_TIME)));
		interviewBasic.setVersionId(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_BASIC_VERSION_ID)));
		interviewBasic.setQuitReason(cursor.getString(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_BASIC_QUIT_REASON)));
		interviewBasic.setUploadStatus(cursor.getInt(cursor.getColumnIndex(DBConstants.COLUMN_INTERVIEW_BASIC_UPLOAD_STATUS)));
		return interviewBasic;
	}
	
	private static ContentValues fillDBFromObject(InterviewBasic interviewBasic){
		ContentValues values = new ContentValues();
		values.put("id",interviewBasic.getId());
		values.put(DBConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWEE_ID, interviewBasic.getIntervieweeId());
		values.put(DBConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID, interviewBasic.getInterviewerId());
		values.put(DBConstants.COLUMN_INTERVIEW_BASIC_TYPE, interviewBasic.getType());
		values.put(DBConstants.COLUMN_INTERVIEW_BASIC_IS_TEST, interviewBasic.getIsTest());
		values.put(DBConstants.COLUMN_INTERVIEW_BASIC_START_TIME, interviewBasic.getStartTime());
		values.put(DBConstants.COLUMN_INTERVIEW_BASIC_LAST_MODIFYED_TIME, interviewBasic.getLastModifiedTime());
		values.put(DBConstants.COLUMN_INTERVIEW_BASIC_STATUS, interviewBasic.getStatus());
		values.put(DBConstants.COLUMN_INTERVIEW_BASIC_CUR_QUESTIONAIRE_CODE, interviewBasic.getCurQuestionaireCode());
		values.put(DBConstants.COLUMN_INTERVIEW_BASIC_NEXT_QUESTION_CODE, interviewBasic.getNextQuestionCode());
		values.put(DBConstants.COLUMN_INTERVIEW_BASIC_VERSION_ID, interviewBasic.getVersionId());
		values.put(DBConstants.COLUMN_INTERVIEW_BASIC_QUIT_REASON, interviewBasic.getQuitReason());
		values.put(DBConstants.COLUMN_INTERVIEW_BASIC_UPLOAD_STATUS, interviewBasic.getUploadStatus());
		return values;
	}
}
