package com.huasheng.sysq.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import android.content.ContentValues;
import android.database.Cursor;

import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.util.ColumnConstants;
import com.huasheng.sysq.util.SysQOpenHelper;
import com.huasheng.sysq.util.TableConstants;

public class InterviewBasicDB {

	public static int insert(InterviewBasic interviewBasic){
		ContentValues values = fill(interviewBasic);
		return (int)SysQOpenHelper.getDatabase().insert(TableConstants.TABLE_INTERVIEW_BASIC, null, values);
	}
	
	public static void update(InterviewBasic interviewBasic){
		ContentValues values = fill(interviewBasic);
		SysQOpenHelper.getDatabase().update(TableConstants.TABLE_INTERVIEW_BASIC, values,"id = ?",new String[]{interviewBasic.getId()+""});
	}
	
	public static InterviewBasic selectById(int id){
		Cursor cursor = SysQOpenHelper.getDatabase().query(TableConstants.TABLE_INTERVIEW_BASIC, null, "id = ?", new String[]{id + ""}, null, null, null);
		InterviewBasic interviewBasic = null;
		if(cursor.moveToNext()){
			interviewBasic = fillObjectFromDB(cursor);
		}
		return interviewBasic;
	}
	
	public static List<InterviewBasic> search(InterviewBasic interview,String searchType,Integer offset,Integer limit){
		
		//处理过滤条件
		String selection = (String)whereSql(interview,searchType).get("selection");
		String[] selectionArgs = (String[])whereSql(interview,searchType).get("selectionArgs");
		
		//处理分页
		String limitStr = null;
		if(offset != null && limit != null){
			limitStr = offset + "," + limit;
		}
		
		//遍历数据
		Cursor cursor = SysQOpenHelper.getDatabase().query(TableConstants.TABLE_INTERVIEW_BASIC, null, selection, selectionArgs, null, null,null,limitStr);
		List<InterviewBasic> data = new ArrayList<InterviewBasic>();
		if(cursor.moveToFirst()){
			do{
				data.add(fillObjectFromDB(cursor));//填充数据
			}while(cursor.moveToNext());
		}
		cursor.close();
		
		return data;
	}
	
	private static InterviewBasic fillObjectFromDB(Cursor cursor){
		
		InterviewBasic interviewBasic = new InterviewBasic();
		interviewBasic.setId(cursor.getInt(cursor.getColumnIndex("id")));
		interviewBasic.setUsername(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_BASIC_USERNAME)));
		interviewBasic.setIdentityCard(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_BASIC_IDENTITY_CARD)));
		interviewBasic.setMobile(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_BASIC_MOBILE)));
		interviewBasic.setProvince(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_BASIC_PROVINCE)));
		interviewBasic.setCity(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_BASIC_CITY)));
		interviewBasic.setAddress(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_BASIC_ADDRESS)));
		interviewBasic.setPostCode(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_BASIC_POST_CODE)));
		interviewBasic.setFamilyMobile(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_BASIC_FAMILY_MOBILE)));
		interviewBasic.setFamilyAddress(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_BASIC_FAMILY_ADDRESS)));
		interviewBasic.setDna(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_BASIC_DNA)));
		interviewBasic.setRemark(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_BASIC_REMARK)));
		interviewBasic.setInterviewerId(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID)));
		interviewBasic.setType(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_BASIC_TYPE)));
		interviewBasic.setIsTest(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_BASIC_IS_TEST)));
		interviewBasic.setStartTime(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_BASIC_START_TIME)));
		interviewBasic.setStatus(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_BASIC_STATUS)));
		interviewBasic.setCurQuestionaireCode(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_BASIC_CUR_QUESTIONAIRE_CODE)));
		interviewBasic.setNextQuestionCode(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_BASIC_NEXT_QUESTION_CODE)));
		interviewBasic.setLastModifiedTime(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_BASIC_LAST_MODIFYED_TIME)));
		interviewBasic.setVersionId(cursor.getInt(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_BASIC_VERSION_ID)));
		interviewBasic.setQuitReason(cursor.getString(cursor.getColumnIndex(ColumnConstants.COLUMN_INTERVIEW_BASIC_QUIT_REASON)));
		
		return interviewBasic;
	}
	
	/**
	 * 动态生成selection和selectionArgs
	 * 
	 * 目前只处理了name
	 * 
	 * @param reservation
	 * @return
	 */
	private static Map<String,Object> whereSql(InterviewBasic interview,String searchType){
		
		String selection = null;
		String[] selectionArgs = null;
		
		List<String> selectionList = new ArrayList<String>();
		List<String> selectionArgsList = new ArrayList<String>();
		
		String username = interview.getUsername();
		if(!StringUtils.isEmpty(StringUtils.trim(username))){
			selectionList.add(ColumnConstants.COLUMN_INTERVIEW_BASIC_USERNAME + " like ?");
			selectionArgsList.add("%"+username+"%");
		}
		
		String dna = interview.getDna();
		if(!StringUtils.isEmpty(StringUtils.trim(dna))){
			selectionList.add(ColumnConstants.COLUMN_INTERVIEW_BASIC_DNA + " like ?");
			selectionArgsList.add("%"+dna+"%");
		}
		
		if(selectionList.size() > 0){
			selection = StringUtils.join(selectionList, " " + searchType + " ");
		}
		if(selectionArgsList.size() > 0){
			selectionArgs = selectionArgsList.toArray(new String[selectionArgsList.size()]);
		}
		
		Map<String,Object> selectionMap = new HashMap<String,Object>();
		selectionMap.put("selection", selection);
		selectionMap.put("selectionArgs", selectionArgs);
		
		return selectionMap;
	}
	
	public static int size(InterviewBasic interview,String searchType){
		
		//处理过滤条件
		String selection = (String)whereSql(interview,searchType).get("selection");
		String[] selectionArgs = (String[])whereSql(interview,searchType).get("selectionArgs");
		
		Cursor cursor = SysQOpenHelper.getDatabase().query(TableConstants.TABLE_INTERVIEW_BASIC, null, selection, selectionArgs, null, null, null);
		return cursor.getCount();
	}
	
	private static ContentValues fill(InterviewBasic interviewBasic){
		ContentValues values = new ContentValues();
		values.put(ColumnConstants.COLUMN_INTERVIEW_BASIC_USERNAME, interviewBasic.getUsername());
		values.put(ColumnConstants.COLUMN_INTERVIEW_BASIC_IDENTITY_CARD, interviewBasic.getIdentityCard());
		values.put(ColumnConstants.COLUMN_INTERVIEW_BASIC_MOBILE, interviewBasic.getMobile());
		values.put(ColumnConstants.COLUMN_INTERVIEW_BASIC_PROVINCE, interviewBasic.getProvince());
		values.put(ColumnConstants.COLUMN_INTERVIEW_BASIC_CITY, interviewBasic.getCity());
		values.put(ColumnConstants.COLUMN_INTERVIEW_BASIC_ADDRESS, interviewBasic.getAddress());
		values.put(ColumnConstants.COLUMN_INTERVIEW_BASIC_POST_CODE, interviewBasic.getPostCode());
		values.put(ColumnConstants.COLUMN_INTERVIEW_BASIC_FAMILY_MOBILE, interviewBasic.getFamilyMobile());
		values.put(ColumnConstants.COLUMN_INTERVIEW_BASIC_FAMILY_ADDRESS, interviewBasic.getFamilyAddress());
		values.put(ColumnConstants.COLUMN_INTERVIEW_BASIC_DNA, interviewBasic.getDna());
		values.put(ColumnConstants.COLUMN_INTERVIEW_BASIC_REMARK, interviewBasic.getRemark());
		values.put(ColumnConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID, interviewBasic.getInterviewerId());
		values.put(ColumnConstants.COLUMN_INTERVIEW_BASIC_TYPE, interviewBasic.getType());
		values.put(ColumnConstants.COLUMN_INTERVIEW_BASIC_IS_TEST, interviewBasic.getIsTest());
		values.put(ColumnConstants.COLUMN_INTERVIEW_BASIC_START_TIME, interviewBasic.getStartTime());
		values.put(ColumnConstants.COLUMN_INTERVIEW_BASIC_LAST_MODIFYED_TIME, interviewBasic.getLastModifiedTime());
		values.put(ColumnConstants.COLUMN_INTERVIEW_BASIC_STATUS, interviewBasic.getStatus());
		values.put(ColumnConstants.COLUMN_INTERVIEW_BASIC_CUR_QUESTIONAIRE_CODE, interviewBasic.getCurQuestionaireCode());
		values.put(ColumnConstants.COLUMN_INTERVIEW_BASIC_NEXT_QUESTION_CODE, interviewBasic.getNextQuestionCode());
		values.put(ColumnConstants.COLUMN_INTERVIEW_BASIC_VERSION_ID, interviewBasic.getVersionId());
		values.put(ColumnConstants.COLUMN_INTERVIEW_BASIC_QUIT_REASON, interviewBasic.getQuitReason());
		return values;
	}
}
