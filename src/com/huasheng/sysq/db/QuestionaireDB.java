package com.huasheng.sysq.db;

import android.database.Cursor;

import com.huasheng.sysq.model.Questionaire;
import com.huasheng.sysq.util.InterviewConstants;
import com.huasheng.sysq.util.TableConstants;

public class QuestionaireDB {

	public static Questionaire getFirst(int versionId,int type){
		Cursor cursor = SysQOpenHelper.getDatabase().query(
				TableConstants.TABLE_QUESTIONAIRE, 
				null, 
				"version_id = ? and ( type = ? or type = ?)",
				new String[]{versionId+"",type+"",InterviewConstants.TYPE_COMMON+""},
				null,null,"seq_num desc","1");
		Questionaire questionaire = null;
		if(cursor.moveToNext()){
			questionaire = new Questionaire();
			questionaire.setId(cursor.getInt(cursor.getColumnIndex("id")));
			questionaire.setCode(cursor.getString(cursor.getColumnIndex(TableConstants.COLUMN_QUESTIONAIRE_CODE)));
			questionaire.setTitle(cursor.getString(cursor.getColumnIndex(TableConstants.COLUMN_QUESTIONAIRE_TITLE)));
			questionaire.setIntroduction(cursor.getString(cursor.getColumnIndex(TableConstants.COLUMN_QUESTIONAIRE_INTRODUCTION)));
			questionaire.setRemark(cursor.getString(cursor.getColumnIndex(TableConstants.COLUMN_QUESTIONAIRE_REMARK)));
			questionaire.setType(cursor.getInt(cursor.getColumnIndex(TableConstants.COLUMN_QUESTIONAIRE_TYPE)));
			questionaire.setSeqNum(cursor.getInt(cursor.getColumnIndex(TableConstants.COLUMN_QUESTIONAIRE_SEQ_NUM)));
			questionaire.setVersionId(cursor.getInt(cursor.getColumnIndex(TableConstants.COLUMN_QUESTIONAIRE_VERSION_ID)));
		}
		cursor.close();
		return questionaire;
	}
}
