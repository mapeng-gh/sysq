package com.huasheng.sysq.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huasheng.sysq.db.StaticsDB;
import com.huasheng.sysq.util.ColumnConstants;
import com.huasheng.sysq.util.DateTimeUtils;
import com.huasheng.sysq.util.InterviewConstants;
import com.huasheng.sysq.util.SysqContext;
import com.huasheng.sysq.util.TableConstants;

public class StaticsService {

	/**
	 * 病例统计
	 * @return
	 */
	public static List<Map<String,String>> reportInterview(){
		
		List<Map<String,String>> data = new ArrayList<Map<String,String>>();
		
		//本人
		int myTodayCase = 0;
		int myTodayContrast = 0;
		int myAllCase = 0;
		int myAllContrast = 0;
		
		String myTodayCaseSQL = "select count(*) as myTodayCase" +
								" from " + TableConstants.TABLE_INTERVIEW_BASIC +
								" where " + 
								ColumnConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID + "=?" +
								" and " +
								ColumnConstants.COLUMN_INTERVIEW_BASIC_START_TIME + " like ?" +
								" and " +
								ColumnConstants.COLUMN_INTERVIEW_BASIC_TYPE + "=" + InterviewConstants.TYPE_CASE;
		String[] myTodayCaseSQLArgs = new String[]{SysqContext.getInterviewer().getId()+"",DateTimeUtils.getCurDate()+"%"};
		myTodayCase = Integer.parseInt(StaticsDB.execSQL(myTodayCaseSQL,myTodayCaseSQLArgs).get(0).get("myTodayCase"));
		
		String myTodayContrastSQL = "select count(*) as myTodayContrast" +
									" from " + TableConstants.TABLE_INTERVIEW_BASIC +
									" where " + 
									ColumnConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID + "=?" +
									" and " +
									ColumnConstants.COLUMN_INTERVIEW_BASIC_START_TIME + " like ?" +
									" and " +
									ColumnConstants.COLUMN_INTERVIEW_BASIC_TYPE + "=" + InterviewConstants.TYPE_CONTRAST;
		
		String[] myTodayContrastSQLArgs = new String[]{SysqContext.getInterviewer().getId()+"",DateTimeUtils.getCurDate()+"%"};
		myTodayContrast = Integer.parseInt(StaticsDB.execSQL(myTodayContrastSQL,myTodayContrastSQLArgs).get(0).get("myTodayContrast"));
		
		String myAllCaseSQL = "select count(*) as myAllCase" +
								" from " + TableConstants.TABLE_INTERVIEW_BASIC +
								" where " + 
								ColumnConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID + "=?" +
								" and " +
								ColumnConstants.COLUMN_INTERVIEW_BASIC_TYPE + "=" + InterviewConstants.TYPE_CASE;
		String[] myAllCaseSQLArgs = new String[]{SysqContext.getInterviewer().getId()+""};
		myAllCase = Integer.parseInt(StaticsDB.execSQL(myAllCaseSQL,myAllCaseSQLArgs).get(0).get("myAllCase"));
		
		String myAllContrastSQL = "select count(*) as myAllContrast" +
									" from " + TableConstants.TABLE_INTERVIEW_BASIC +
									" where " + 
									ColumnConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID + "=?" +
									" and " +
									ColumnConstants.COLUMN_INTERVIEW_BASIC_TYPE + "=" + InterviewConstants.TYPE_CONTRAST;
		String[] myAllContrastSQLArgs = new String[]{SysqContext.getInterviewer().getId()+""};
		myAllContrast = Integer.parseInt(StaticsDB.execSQL(myAllContrastSQL,myAllContrastSQLArgs).get(0).get("myAllContrast"));
		
		
		//其他医生
		int othersTodayCase = 0;
		int othersTodayContrast = 0;
		int othersAllCase = 0;
		int othersAllContrast = 0;
		
		String othersTodayCaseSQL = "select count(*) as othersTodayCase" +
									" from " + TableConstants.TABLE_INTERVIEW_BASIC +
									" where " + 
									ColumnConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID + "!=?" +
									" and " +
									ColumnConstants.COLUMN_INTERVIEW_BASIC_START_TIME + " like ?" +
									" and " +
									ColumnConstants.COLUMN_INTERVIEW_BASIC_TYPE + "=" + InterviewConstants.TYPE_CASE;
		String[] othersTodayCaseSQLArgs = new String[]{SysqContext.getInterviewer().getId()+"",DateTimeUtils.getCurDate()+"%"};
		othersTodayCase = Integer.parseInt(StaticsDB.execSQL(othersTodayCaseSQL,othersTodayCaseSQLArgs).get(0).get("othersTodayCase"));
		
		String othersTodayContrastSQL = "select count(*) as othersTodayContrast" +
										" from " + TableConstants.TABLE_INTERVIEW_BASIC +
										" where " + 
										ColumnConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID + "!=?" +
										" and " +
										ColumnConstants.COLUMN_INTERVIEW_BASIC_START_TIME + " like ?" +
										" and " +
										ColumnConstants.COLUMN_INTERVIEW_BASIC_TYPE + "=" + InterviewConstants.TYPE_CONTRAST;
		
		String[] othersTodayContrastSQLArgs = new String[]{SysqContext.getInterviewer().getId()+"",DateTimeUtils.getCurDate()+"%"};
		othersTodayContrast = Integer.parseInt(StaticsDB.execSQL(othersTodayContrastSQL,othersTodayContrastSQLArgs).get(0).get("othersTodayContrast"));
		
		String othersAllCaseSQL = "select count(*) as othersAllCase" +
								" from " + TableConstants.TABLE_INTERVIEW_BASIC +
								" where " + 
								ColumnConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID + "!=?" +
								" and " +
								ColumnConstants.COLUMN_INTERVIEW_BASIC_TYPE + "=" + InterviewConstants.TYPE_CASE;
		String[] othersAllCaseSQLArgs = new String[]{SysqContext.getInterviewer().getId()+""};
		othersAllCase = Integer.parseInt(StaticsDB.execSQL(othersAllCaseSQL,othersAllCaseSQLArgs).get(0).get("othersAllCase"));
		
		String othersAllContrastSQL = "select count(*) as othersAllContrast" +
									" from " + TableConstants.TABLE_INTERVIEW_BASIC +
									" where " + 
									ColumnConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID + "!=?" +
									" and " +
									ColumnConstants.COLUMN_INTERVIEW_BASIC_TYPE + "=" + InterviewConstants.TYPE_CONTRAST;
		String[] othersAllContrastSQLArgs = new String[]{SysqContext.getInterviewer().getId()+""};
		othersAllContrast = Integer.parseInt(StaticsDB.execSQL(othersAllContrastSQL,othersAllContrastSQLArgs).get(0).get("othersAllContrast"));
		
		Map<String,String> totalRow = new HashMap<String,String>();
		totalRow.put("title", "总计");
		totalRow.put("all",Integer.toString(myAllCase + myAllContrast + othersAllCase + othersAllContrast));
		totalRow.put("todayCase",Integer.toString(myTodayCase + othersTodayCase));
		totalRow.put("allCase",Integer.toString(myAllCase + othersAllCase));
		totalRow.put("todayContrast",Integer.toString(myTodayContrast + othersTodayContrast));
		totalRow.put("allContrast", Integer.toString(myAllContrast + othersAllContrast));
		data.add(totalRow);
		
		Map<String,String> myRow = new HashMap<String,String>();
		myRow.put("title", "您");
		myRow.put("all", Integer.toString(myAllCase + myAllContrast));
		myRow.put("todayCase", Integer.toString(myTodayCase));
		myRow.put("allCase", Integer.toString(myAllCase));
		myRow.put("todayContrast", Integer.toString(myTodayContrast));
		myRow.put("allContrast", Integer.toString(myAllContrast));
		data.add(myRow);
		
		Map<String,String> othersRow = new HashMap<String,String>();
		othersRow.put("title", "其他医生");
		othersRow.put("all", Integer.toString(othersAllCase + othersAllContrast));
		othersRow.put("todayCase", Integer.toString(othersTodayCase));
		othersRow.put("allCase", Integer.toString(othersAllCase));
		othersRow.put("todayContrast", Integer.toString(othersTodayContrast));
		othersRow.put("allContrast", Integer.toString(othersAllContrast));
		data.add(othersRow);
		
		return data;
	}
	
	/**
	 * DNA统计
	 * @return
	 */
	public static List<Map<String,String>> reportDNA(){
		return null;
	}
	
	/**
	 * 问卷统计
	 * @return
	 */
	public static List<Map<String,String>> reportQuestionaire(){
		return null;
	}
	
	/**
	 * 采访者统计
	 * @return
	 */
	public static List<Map<String,String>> reportInterviewer(){
		return null;
	}
}
