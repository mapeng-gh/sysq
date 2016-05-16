package com.huasheng.sysq.util;

public class TableConstants {

	/**
	 * 表名
	 */
	public static final String TABLE_INTERVIEWER = "interviewer";
	public static final String TABLE_RESERVATION = "reservation";
	public static final String TABLE_VERSION = "version";
	public static final String TABLE_QUESTIONAIRE = "questionaire";
	public static final String TABLE_QUESTION = "question";
	public static final String TABLE_ANSWER = "answer";
	public static final String TABLE_INTERVIEW_BASIC = "interview_basic";
	public static final String TABLE_INTERVIEW_QUESTIONAIRE = "interview_questionaire";
	public static final String TABLE_INTERVIEW_QUESTION = "interview_question";
	public static final String TABLE_INTERVIEW_ANSWER = "interview_answer";
	
	/**
	 * 建表
	 */
	public static final String CREATE_INTERVIEWER = "create table " + TableConstants.TABLE_INTERVIEWER + 
			"(" +
				"id integer primary key autoincrement," +
				ColumnConstants.COLUMN_INTERVIEWER_LOGIN_NAME + " varchar," +
				ColumnConstants.COLUMN_INTERVIEWER_PASSWORD + " varchar," +
				ColumnConstants.COLUMN_INTERVIEWER_USERNAME + " varchar," +
				ColumnConstants.COLUMN_INTERVIEWER_MOBILE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEWER_EMAIL + " varchar," +
				ColumnConstants.COLUMN_INTERVIEWER_WORKING_PLACE + " varchar" +
			")";
	public static final String CRETE_RESERVATION = "create table " + TableConstants.TABLE_RESERVATION + 
			"(" +
				"id integer primary key autoincrement," +
				ColumnConstants.COLUMN_RESERVATION_USERNAME + " varchar," +
				ColumnConstants.COLUMN_RESERVATION_IDENTITY_CARD + " varchar," +
				ColumnConstants.COLUMN_RESERVATION_MOBILE + " varchar," +
				ColumnConstants.COLUMN_RESERVATION_TYPE + " integer," +
				ColumnConstants.COLUMN_RESERVATION_BOOK_DATE + " varchar," +
				ColumnConstants.COLUMN_RESERVATION_FAMILY_MOBILE + " varchar" +
			")";
	public static final String CREATE_VERSION = "create table " + TableConstants.TABLE_VERSION +
			"(" +
				"id integer primary key autoincrement," +
				ColumnConstants.COLUMN_VERSION_NAME + " varchar," +
				ColumnConstants.COLUMN_VERSION_PUBLISH_DATE + " varchar," +
				ColumnConstants.COLUMN_VERSION_REMARK + " varchar," +
				ColumnConstants.COLUMN_VERSION_IS_CURRENT + " integer" +
			")";
	public static final String CREATE_QUESTIONAIRE = "create table " + TableConstants.TABLE_QUESTIONAIRE +
			"(" +
				"id integer primary key autoincrement," +
				ColumnConstants.COLUMN_QUESTIONAIRE_CODE + " varchar," +
				ColumnConstants.COLUMN_QUESTIONAIRE_TITLE + " varchar," +
				ColumnConstants.COLUMN_QUESTIONAIRE_INTRODUCTION + " varchar," +
				ColumnConstants.COLUMN_QUESTIONAIRE_REMARK + " varchar," +
				ColumnConstants.COLUMN_QUESTIONAIRE_TYPE + " integer," +
				ColumnConstants.COLUMN_QUESTIONAIRE_SEQ_NUM + " integer," +
				ColumnConstants.COLUMN_QUESTIONAIRE_VERSION_ID + " integer" +
			")";
	public static final String CREATE_QUESTION = "create table " + TableConstants.TABLE_QUESTION + 
			"(" +
				"id integer primary key autoincrement," +
				ColumnConstants.COLUMN_QUESTION_CODE + " varchar," +
				ColumnConstants.COLUMN_QUESTION_DESCRIPTION + " varchar," +
				ColumnConstants.COLUMN_QUESTION_REMARK + " varchar," +
				ColumnConstants.COLUMN_QUESTION_IS_END + " integer," +
				ColumnConstants.COLUMN_QUESTION_SEQ_NUM + " integer," +
				ColumnConstants.COLUMN_QUESTION_QUESTIONAIRE_CODE + " varchar," +
				ColumnConstants.COLUMN_QUESTION_ENTRY_LOGIC + " varchar," +
				ColumnConstants.COLUMN_QUESTION_EXIT_LOGIC + " varchar," +
				ColumnConstants.COLUMN_QUESTION_VERSION_ID + " integer" +
			")";
	public static final String CREATE_ANSWER = "create table " + TableConstants.TABLE_ANSWER +
			"(" +
				"id integer primary key autoincrement," +
				ColumnConstants.COLUMN_ANSWER_CODE + " varchar," +
				ColumnConstants.COLUMN_ANSWER_LABEL + " varchar," +
				ColumnConstants.COLUMN_ANSWER_TYPE + " varchar," +
				ColumnConstants.COLUMN_ANSWER_EXTRA + " varchar," +
				ColumnConstants.COLUMN_ANSWER_SHOW_TYPE + " varchar," +
				ColumnConstants.COLUMN_ANSWER_IS_SHOW + " integer," +
				ColumnConstants.COLUMN_ANSWER_EVENT_TYPE + " varchar," +
				ColumnConstants.COLUMN_ANSWER_EVENT_EXECUTE + " varchar," +
				ColumnConstants.COLUMN_ANSWER_SEQ_NUM + " integer," +
				ColumnConstants.COLUMN_ANSWER_QUESTION_CODE + " varchar," +
				ColumnConstants.COLUMN_ANSWER_VERSION_ID + " integer" +
			")";
	public static final String CREATE_INTERVIEW_BASIC = "create table " + TableConstants.TABLE_INTERVIEW_BASIC +
			"(" +
				"id integer primary key autoincrement," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_USERNAME + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_IDENTITY_CARD + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_MOBILE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_PROVINCE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_CITY + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_ADDRESS + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_POST_CODE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_FAMILY_MOBILE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_FAMILY_ADDRESS + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_REMARK + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_DNA + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID + " integer," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_TYPE + " integer," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_IS_TEST + " integer," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_START_TIME + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_STATUS + " integer," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_CUR_QUESTIONAIRE_CODE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_NEXT_QUESTION_CODE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_LAST_MODIFYED_TIME + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_BASIC_VERSION_ID + " integer" +
			")";
	
	public static final String CREATE_INTERVIEW_QUESTIONAIRE = "create table " + TableConstants.TABLE_INTERVIEW_QUESTIONAIRE +
			"(" +
				"id integer primary key autoincrement," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_INTERVIEW_BASIC_ID + " integer," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_QUESTIONAIRE_CODE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_START_TIME + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_LAST_MODIFIED_TIME + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_STATUS + " integer," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_SEQ_NUM + " integer," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_VERSION_ID + " integer" +
			")";
	
	public static final String CREATE_INTERVIEW_QUESTION = "create table " + TableConstants.TABLE_INTERVIEW_QUESTION +
			"(" +
				"id integer primary key autoincrement," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTION_INTERVIEW_BASIC_ID + " integer," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTION_QUESTIONAIRE_CODE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTION_QUESTION_CODE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTION_SEQ_NUM + " integer," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTION_VERSION_ID + " integer" +
			")";
	
	public static final String CREATE_INTERVIEW_ANSWER = "create table " + TableConstants.TABLE_INTERVIEW_ANSWER +
			"(" +
				"id integer primary key autoincrement," +
				ColumnConstants.COLUMN_INTERVIEW_ANSWER_INTERVIEW_BASIC_ID + " integer," +
				ColumnConstants.COLUMN_INTERVIEW_ANSWER_QUESTION_CODE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_LABEL + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_CODE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_VALUE + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_TEXT + " varchar," +
				ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_SEQ_NUM + " integer," +
				ColumnConstants.COLUMN_INTERVIEW_QUESTION_VERSION_ID + " integer" +
			")";
}
