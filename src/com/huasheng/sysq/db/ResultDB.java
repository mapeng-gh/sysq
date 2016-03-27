package com.huasheng.sysq.db;

import android.content.ContentValues;

import com.huasheng.sysq.model.Result;
import com.huasheng.sysq.util.TableConstants;

public class ResultDB {

	/**
	 * ±£´æ½á¹û
	 * @param result
	 */
	public static void save(Result result){
		ContentValues values = new ContentValues();
		values.put(TableConstants.COLUMN_RESULT_INTERVIEW_ID, result.getInterviewId());
		values.put(TableConstants.COLUMN_RESULT_INTERVIEW_ANSWER_CODE, result.getAnswerCode());
		values.put(TableConstants.COLUMN_RESULT_INTERVIEW_ANSWER_VALUE, result.getAnswerValue());
		
		SysQOpenHelper.getDatabase().insert(TableConstants.TABLE_RESULT, null, values);
	}
}
