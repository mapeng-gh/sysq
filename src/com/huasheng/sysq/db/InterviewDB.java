package com.huasheng.sysq.db;

import android.content.ContentValues;

import com.huasheng.sysq.model.Interview;
import com.huasheng.sysq.util.TableConstants;

public class InterviewDB {

	public static long save(Interview interview){
		ContentValues values = fill(interview);
		return SysQOpenHelper.getDatabase().insert(TableConstants.TABLE_INTERVIEW, null, values);
	}
	
	public static void update(Interview interview){
		ContentValues values = fill(interview);
		SysQOpenHelper.getDatabase().update(TableConstants.TABLE_INTERVIEW, values,"id = ?",new String[]{interview.getId()+""});
	}
	
	private static ContentValues fill(Interview interview){
		ContentValues values = new ContentValues();
		values.put(TableConstants.COLUMN_INTERVIEW_USERNAME, interview.getUsername());
		values.put(TableConstants.COLUMN_INTERVIEW_IDENTITY_CARD, interview.getIdentityCard());
		values.put(TableConstants.COLUMN_INTERVIEW_MOBILE, interview.getMobile());
		values.put(TableConstants.COLUMN_INTERVIEW_PROVINCE, interview.getProvince());
		values.put(TableConstants.COLUMN_INTERVIEW_CITY, interview.getCity());
		values.put(TableConstants.COLUMN_INTERVIEW_ADDRESS, interview.getAddress());
		values.put(TableConstants.COLUMN_INTERVIEW_POST_CODE, interview.getPostCode());
		values.put(TableConstants.COLUMN_INTERVIEW_FAMILY_MOBILE, interview.getFamilyMobile());
		values.put(TableConstants.COLUMN_INTERVIEW_FAMILY_ADDRESS, interview.getFamilyAddress());
		values.put(TableConstants.COLUMN_INTERVIEW_DNA, interview.getDna());
		values.put(TableConstants.COLUMN_INTERVIEW_REMARK, interview.getRemark());
		values.put(TableConstants.COLUMN_INTERVIEW_INTERVIEWER_ID, interview.getInterviewerId());
		values.put(TableConstants.COLUMN_INTERVIEW_TYPE, interview.getType());
		values.put(TableConstants.COLUMN_INTERVIEW_IS_TEST, interview.getIsTest());
		values.put(TableConstants.COLUMN_INTERVIEW_START_TIME, interview.getStartTime());
		values.put(TableConstants.COLUMN_INTERVIEW_STATUS, interview.getStatus());
		values.put(TableConstants.COLUMN_INTERVIEW_CUR_QUESTIONAIRE_CODE, interview.getCurQuestionaireCode());
		values.put(TableConstants.COLUMN_INTERVIEW_NEXT_QUESTION_CODE, interview.getNextQuestionCode());
		values.put(TableConstants.COLUMN_INTERVIEW_VERSION_ID, interview.getVersionId());
		values.put(TableConstants.COLUMN_INTERVIEW_END_TIME, interview.getEndTime());
		return values;
	}
}
