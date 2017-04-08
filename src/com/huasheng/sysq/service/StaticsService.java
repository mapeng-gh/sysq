package com.huasheng.sysq.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.huasheng.sysq.db.InterviewerDB;
import com.huasheng.sysq.db.StaticsDB;
import com.huasheng.sysq.model.InterviewQuestionaire;
import com.huasheng.sysq.model.Interviewer;
import com.huasheng.sysq.util.CommonUtils;
import com.huasheng.sysq.util.SysqContext;
import com.huasheng.sysq.util.db.DBConstants;
import com.huasheng.sysq.util.interview.InterviewConstants;

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
								" from " + DBConstants.TABLE_INTERVIEW_BASIC +
								" where " + 
								DBConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID + "=?" +
								" and " +
								DBConstants.COLUMN_INTERVIEW_BASIC_START_TIME + " like ?" +
								" and " +
								DBConstants.COLUMN_INTERVIEW_BASIC_TYPE + "=" + InterviewConstants.TYPE_CASE;
		String[] myTodayCaseSQLArgs = new String[]{SysqContext.getInterviewer().getId()+"",CommonUtils.getCurDate()+"%"};
		myTodayCase = Integer.parseInt(StaticsDB.execSQL(myTodayCaseSQL,myTodayCaseSQLArgs).get(0).get("myTodayCase"));
		
		String myTodayContrastSQL = "select count(*) as myTodayContrast" +
									" from " + DBConstants.TABLE_INTERVIEW_BASIC +
									" where " + 
									DBConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID + "=?" +
									" and " +
									DBConstants.COLUMN_INTERVIEW_BASIC_START_TIME + " like ?" +
									" and " +
									DBConstants.COLUMN_INTERVIEW_BASIC_TYPE + "=" + InterviewConstants.TYPE_CONTRAST;
		
		String[] myTodayContrastSQLArgs = new String[]{SysqContext.getInterviewer().getId()+"",CommonUtils.getCurDate()+"%"};
		myTodayContrast = Integer.parseInt(StaticsDB.execSQL(myTodayContrastSQL,myTodayContrastSQLArgs).get(0).get("myTodayContrast"));
		
		String myAllCaseSQL = "select count(*) as myAllCase" +
								" from " + DBConstants.TABLE_INTERVIEW_BASIC +
								" where " + 
								DBConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID + "=?" +
								" and " +
								DBConstants.COLUMN_INTERVIEW_BASIC_TYPE + "=" + InterviewConstants.TYPE_CASE;
		String[] myAllCaseSQLArgs = new String[]{SysqContext.getInterviewer().getId()+""};
		myAllCase = Integer.parseInt(StaticsDB.execSQL(myAllCaseSQL,myAllCaseSQLArgs).get(0).get("myAllCase"));
		
		String myAllContrastSQL = "select count(*) as myAllContrast" +
									" from " + DBConstants.TABLE_INTERVIEW_BASIC +
									" where " + 
									DBConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID + "=?" +
									" and " +
									DBConstants.COLUMN_INTERVIEW_BASIC_TYPE + "=" + InterviewConstants.TYPE_CONTRAST;
		String[] myAllContrastSQLArgs = new String[]{SysqContext.getInterviewer().getId()+""};
		myAllContrast = Integer.parseInt(StaticsDB.execSQL(myAllContrastSQL,myAllContrastSQLArgs).get(0).get("myAllContrast"));
		
		
		//其他医生
		int othersTodayCase = 0;
		int othersTodayContrast = 0;
		int othersAllCase = 0;
		int othersAllContrast = 0;
		
		String othersTodayCaseSQL = "select count(*) as othersTodayCase" +
									" from " + DBConstants.TABLE_INTERVIEW_BASIC +
									" where " + 
									DBConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID + "!=?" +
									" and " +
									DBConstants.COLUMN_INTERVIEW_BASIC_START_TIME + " like ?" +
									" and " +
									DBConstants.COLUMN_INTERVIEW_BASIC_TYPE + "=" + InterviewConstants.TYPE_CASE;
		String[] othersTodayCaseSQLArgs = new String[]{SysqContext.getInterviewer().getId()+"",CommonUtils.getCurDate()+"%"};
		othersTodayCase = Integer.parseInt(StaticsDB.execSQL(othersTodayCaseSQL,othersTodayCaseSQLArgs).get(0).get("othersTodayCase"));
		
		String othersTodayContrastSQL = "select count(*) as othersTodayContrast" +
										" from " + DBConstants.TABLE_INTERVIEW_BASIC +
										" where " + 
										DBConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID + "!=?" +
										" and " +
										DBConstants.COLUMN_INTERVIEW_BASIC_START_TIME + " like ?" +
										" and " +
										DBConstants.COLUMN_INTERVIEW_BASIC_TYPE + "=" + InterviewConstants.TYPE_CONTRAST;
		
		String[] othersTodayContrastSQLArgs = new String[]{SysqContext.getInterviewer().getId()+"",CommonUtils.getCurDate()+"%"};
		othersTodayContrast = Integer.parseInt(StaticsDB.execSQL(othersTodayContrastSQL,othersTodayContrastSQLArgs).get(0).get("othersTodayContrast"));
		
		String othersAllCaseSQL = "select count(*) as othersAllCase" +
								" from " + DBConstants.TABLE_INTERVIEW_BASIC +
								" where " + 
								DBConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID + "!=?" +
								" and " +
								DBConstants.COLUMN_INTERVIEW_BASIC_TYPE + "=" + InterviewConstants.TYPE_CASE;
		String[] othersAllCaseSQLArgs = new String[]{SysqContext.getInterviewer().getId()+""};
		othersAllCase = Integer.parseInt(StaticsDB.execSQL(othersAllCaseSQL,othersAllCaseSQLArgs).get(0).get("othersAllCase"));
		
		String othersAllContrastSQL = "select count(*) as othersAllContrast" +
									" from " + DBConstants.TABLE_INTERVIEW_BASIC +
									" where " + 
									DBConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID + "!=?" +
									" and " +
									DBConstants.COLUMN_INTERVIEW_BASIC_TYPE + "=" + InterviewConstants.TYPE_CONTRAST;
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
		
		List<Map<String,String>> data = new ArrayList<Map<String,String>>();
		
		int today = 0;
		int all = 0;
		
		//today
		String todaySQL = "select dna" +
						  " from " + DBConstants.TABLE_INTERVIEW_BASIC + 
						  " where " + DBConstants.COLUMN_INTERVIEW_BASIC_START_TIME + " like ?";
		String[] todaySQLArgs = new String[]{CommonUtils.getCurDate()+"%"};
		List<Map<String,String>> todayResult = StaticsDB.execSQL(todaySQL, todaySQLArgs);
		
		if(todayResult != null && todayResult.size() > 0){
			
			Set<String> dnaSet = new HashSet<String>();
			
			for(Map<String,String> map : todayResult){
				String dnas = map.get("dna");
				
				if(!StringUtils.isEmpty(dnas)){
					String[] dnaArray = dnas.split(",");
					
					for(String dna : dnaArray){
						dnaSet.add(dna);
					}
				}
			}
			
			today = dnaSet.size();
		}
		
		//all
		String allSQL = "select dna" +
				  " from " + DBConstants.TABLE_INTERVIEW_BASIC;
		List<Map<String,String>> allResult = StaticsDB.execSQL(allSQL,null);

		if(allResult != null && allResult.size() > 0){
			
			Set<String> dnaSet = new HashSet<String>();
			
			for(Map<String,String> map : allResult){
				String dnas = map.get("dna");
				
				if(!StringUtils.isEmpty(dnas)){
					String[] dnaArray = dnas.split(",");
					
					for(String dna : dnaArray){
						dnaSet.add(dna);
					}
				}
			}
			
			all = dnaSet.size();
		}
		
		
		Map<String,String> row = new HashMap<String,String>();
		
		row.put("title", "总计");
		row.put("all",Integer.toString(all));
		row.put("today",Integer.toString(today));
		row.put("before",Integer.toString(all - today));
		
		data.add(row);
		
		return data;
	}
	
	/**
	 * 问卷统计
	 * @return
	 */
	public static List<Map<String,String>> reportQuestionaire(){
		
		List<Map<String,String>> data = new ArrayList<Map<String,String>>();
		
		//本人
		int myAll = 0;
		int myDone = 0;
		
		String myAllSQL = "select count(*) as myAll" + 
						  " from " + DBConstants.TABLE_INTERVIEW_BASIC + " interviewBasic " +
						  " inner join " + DBConstants.TABLE_INTERVIEW_QUESTIONAIRE + " interviewQuestionaire " +
						  " on interviewBasic.id = interviewQuestionaire." + DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_INTERVIEW_BASIC_ID +
						  " where interviewBasic." + DBConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID + " = ?";
		String[] myAllSQLArgs = new String[]{Integer.toString(SysqContext.getInterviewer().getId())};
		myAll = Integer.parseInt(StaticsDB.execSQL(myAllSQL, myAllSQLArgs).get(0).get("myAll"));
		
		String myDoneSQL = "select count(*) as myDone" + 
						  " from " + DBConstants.TABLE_INTERVIEW_BASIC + " interviewBasic " +
						  " inner join " + DBConstants.TABLE_INTERVIEW_QUESTIONAIRE + " interviewQuestionaire " +
						  " on interviewBasic.id = interviewQuestionaire." + DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_INTERVIEW_BASIC_ID +
						  " where interviewBasic." + DBConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID + " = ?" + 
						  " and interviewQuestionaire." + DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_STATUS + " = " + InterviewQuestionaire.STATUS_DONE;
		String[] myDoneSQLArgs = new String[]{Integer.toString(SysqContext.getInterviewer().getId())};
		myDone = Integer.parseInt(StaticsDB.execSQL(myDoneSQL, myDoneSQLArgs).get(0).get("myDone"));
		
		//其他
		int othersAll = 0;
		int othersDone = 0;
		
		String othersAllSQL = "select count(*) as othersAll" + 
				  " from " + DBConstants.TABLE_INTERVIEW_BASIC + " interviewBasic " +
				  " inner join " + DBConstants.TABLE_INTERVIEW_QUESTIONAIRE + " interviewQuestionaire " +
				  " on interviewBasic.id = interviewQuestionaire." + DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_INTERVIEW_BASIC_ID +
				  " where interviewBasic." + DBConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID + " != ?";
		String[] othersAllSQLArgs = new String[]{Integer.toString(SysqContext.getInterviewer().getId())};
		othersAll = Integer.parseInt(StaticsDB.execSQL(othersAllSQL, othersAllSQLArgs).get(0).get("othersAll"));

		String othersDoneSQL = "select count(*) as othersDone" + 
				  " from " + DBConstants.TABLE_INTERVIEW_BASIC + " interviewBasic " +
				  " inner join " + DBConstants.TABLE_INTERVIEW_QUESTIONAIRE + " interviewQuestionaire " +
				  " on interviewBasic.id = interviewQuestionaire." + DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_INTERVIEW_BASIC_ID +
				  " where interviewBasic." + DBConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID + " != ?" + 
				  " and interviewQuestionaire." + DBConstants.COLUMN_INTERVIEW_QUESTIONAIRE_STATUS + " = " + InterviewQuestionaire.STATUS_DONE;
		String[] othersDoneSQLArgs = new String[]{Integer.toString(SysqContext.getInterviewer().getId())};
		othersDone = Integer.parseInt(StaticsDB.execSQL(othersDoneSQL, othersDoneSQLArgs).get(0).get("othersDone"));
		
		Map<String,String> totalRow = new HashMap<String,String>();
		totalRow.put("title","总计");
		totalRow.put("all",Integer.toString(myAll + othersAll));
		totalRow.put("done", Integer.toString(myDone + othersDone));
		data.add(totalRow);
		
		Map<String,String> myRow = new HashMap<String,String>();
		myRow.put("title", "您");
		myRow.put("all", Integer.toString(myAll));
		myRow.put("done", Integer.toString(myDone));
		data.add(myRow);
		
		Map<String,String> othersRow = new HashMap<String,String>();
		othersRow.put("title", "其他医生");
		othersRow.put("all", Integer.toString(othersAll));
		othersRow.put("done", Integer.toString(othersDone));
		data.add(othersRow);
		
		return data;
	}
	
	/**
	 * 采访者统计
	 * @return
	 */
	public static List<Interviewer> reportInterviewer(){
		return InterviewerDB.selectAll();
	}
}
