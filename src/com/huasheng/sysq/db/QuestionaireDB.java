package com.huasheng.sysq.db;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.huasheng.sysq.model.Questionaire;
import com.huasheng.sysq.util.ColumnConstants;
import com.huasheng.sysq.util.InterviewConstants;
import com.huasheng.sysq.util.TableConstants;

public class QuestionaireDB {

	public static List<Questionaire> getList(int versionId,int type){
		Cursor cursor = SysQOpenHelper.getDatabase().query(
				TableConstants.TABLE_QUESTIONAIRE, 
				null, 
				"version_id = ? and type in (?,?)",
				new String[]{versionId+"",type+"",InterviewConstants.TYPE_COMMON+""},
				null,null,"seq_num asc");
		
		List<Questionaire> questionaireList = new ArrayList<Questionaire>();
		while(cursor.moveToNext()){
			Questionaire questionaire = new Questionaire();
			questionaire.setId(cursor.getInt(cursor.getColumnIndex("id")));
			questionaire.setCode(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_QUESTIONAIRE_CODE)));
			questionaire.setTitle(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_QUESTIONAIRE_TITLE)));
			questionaire.setIntroduction(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_QUESTIONAIRE_INTRODUCTION)));
			questionaire.setRemark(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_QUESTIONAIRE_REMARK)));
			questionaire.setType(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_QUESTIONAIRE_TYPE)));
			questionaire.setSeqNum(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_QUESTIONAIRE_SEQ_NUM)));
			questionaire.setVersionId(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_QUESTIONAIRE_VERSION_ID)));
			questionaireList.add(questionaire);
		}
		
		cursor.close();
		
		return questionaireList;
	}
}
