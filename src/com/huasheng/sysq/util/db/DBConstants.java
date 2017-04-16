package com.huasheng.sysq.util.db;

public class DBConstants {
	
	//数据库版本
	public static final int VERSION = 1;
	
	/**
	 * 表名
	 */
	public static final String TABLE_INTERVIEWER = "interviewer";
	public static final String TABLE_RESERVATION = "reservation";
	public static final String TABLE_VERSION = "version";
	public static final String TABLE_QUESTIONAIRE = "questionaire";
	public static final String TABLE_QUESTION = "question";
	public static final String TABLE_ANSWER = "answer";
	public static final String TABLE_INTERVIEWEE = "interviewee";
	public static final String TABLE_INTERVIEW_BASIC = "interview_basic";
	public static final String TABLE_INTERVIEW_QUESTIONAIRE = "interview_questionaire";
	public static final String TABLE_INTERVIEW_QUESTION = "interview_question";
	public static final String TABLE_INTERVIEW_ANSWER = "interview_answer";
	
	/**
	 * 访问者列名
	 */
	public static final String COLUMN_INTERVIEWER_LOGIN_NAME = "login_name";
	public static final String COLUMN_INTERVIEWER_PASSWORD = "password";
	public static final String COLUMN_INTERVIEWER_USERNAME = "username";
	public static final String COLUMN_INTERVIEWER_MOBILE = "mobile";
	public static final String COLUMN_INTERVIEWER_EMAIL = "email";
	public static final String COLUMN_INTERVIEWER_WORKING_PLACE = "working_place";
	public static final String COLUMN_INTERVIEWER_UPLOAD_STATUS = "upload_status";
	
	/**
	 * 预约表列名
	 */
	public static final String COLUMN_RESERVATION_USERNAME = "username";
	public static final String COLUMN_RESERVATION_IDENTITY_CARD = "identity_card";
	public static final String COLUMN_RESERVATION_MOBILE = "mobile";
	public static final String COLUMN_RESERVATION_TYPE = "type";
	public static final String COLUMN_RESERVATION_BOOK_DATE = "book_date";
	public static final String COLUMN_RESERVATION_FAMILY_MOBILE = "family_mobile";
	public static final String COLUMN_RESERVATION_STATUS = "status";
	
	/**
	 * 版本表列名
	 */
	public static final String COLUMN_VERSION_NAME = "name";
	public static final String COLUMN_VERSION_PUBLISH_DATE = "publish_date";
	public static final String COLUMN_VERSION_REMARK = "remark";
	public static final String COLUMN_VERSION_IS_CURRENT = "is_current";
	
	/**
	 * 问卷表列名
	 */
	public static final String COLUMN_QUESTIONAIRE_CODE = "code";
	public static final String COLUMN_QUESTIONAIRE_TITLE = "title";
	public static final String COLUMN_QUESTIONAIRE_INTRODUCTION = "introduction";
	public static final String COLUMN_QUESTIONAIRE_REMARK = "remark";
	public static final String COLUMN_QUESTIONAIRE_TYPE = "type";
	public static final String COLUMN_QUESTIONAIRE_SEQ_NUM = "seq_num";
	public static final String COLUMN_QUESTIONAIRE_VERSION_ID = "version_id";
	
	/**
	 * 问题表列名
	 */
	public static final String COLUMN_QUESTION_CODE = "code";
	public static final String COLUMN_QUESTION_DESCRIPTION = "description";
	public static final String COLUMN_QUESTION_REMARK = "remark";
	public static final String COLUMN_QUESTION_IS_END = "is_end";
	public static final String COLUMN_QUESTION_SEQ_NUM = "seq_num";
	public static final String COLUMN_QUESTION_QUESTIONAIRE_CODE = "questionaire_code";
	public static final String COLUMN_QUESTION_ENTRY_LOGIC = "entry_logic";
	public static final String COLUMN_QUESTION_EXIT_LOGIC = "exit_logic";
	public static final String COLUMN_QUESTION_VERSION_ID = "version_id";
	public static final String COLUMN_QUESTION_ASSOCIATE_QUESTION_CODE = "associate_question_code";
	
	/**
	 * 答案表列名
	 */
	public static final String COLUMN_ANSWER_CODE = "code";
	public static final String COLUMN_ANSWER_LABEL = "label";
	public static final String COLUMN_ANSWER_TYPE = "type";
	public static final String COLUMN_ANSWER_EXTRA = "extra";
	public static final String COLUMN_ANSWER_SHOW_TYPE = "show_type";
	public static final String COLUMN_ANSWER_IS_SHOW = "is_show";
	public static final String COLUMN_ANSWER_EVENT_TYPE = "event_type";
	public static final String COLUMN_ANSWER_EVENT_EXECUTE = "event_execute";
	public static final String COLUMN_ANSWER_SEQ_NUM = "seq_num";
	public static final String COLUMN_ANSWER_QUESTION_CODE = "question_code";
	public static final String COLUMN_ANSWER_VERSION_ID = "version_id";
	
	/**
	 * 被访问者表
	 */
	public static final String COLUMN_INTERVIEWEE_USERNAME = "username";
	public static final String COLUMN_INTERVIEWEE_IDENTITY_CARD = "identity_card";
	public static final String COLUMN_INTERVIEWEE_MOBILE = "mobile";
	public static final String COLUMN_INTERVIEWEE_PROVINCE = "province";
	public static final String COLUMN_INTERVIEWEE_CITY = "city";
	public static final String COLUMN_INTERVIEWEE_ADDRESS = "address";
	public static final String COLUMN_INTERVIEWEE_POST_CODE = "post_code";
	public static final String COLUMN_INTERVIEWEE_FAMILY_MOBILE = "family_mobile";
	public static final String COLUMN_INTERVIEWEE_FAMILY_ADDRESS = "family_address";
	public static final String COLUMN_INTERVIEWEE_REMARK = "remark";
	public static final String COLUMN_INTERVIEWEE_DNA = "dna";
	public static final String COLUMN_INTERVIEWEE_UPLOAD_STATUS = "upload_status";
	
	/**
	 * 访谈基本表列名
	 */
	public static final String COLUMN_INTERVIEW_BASIC_INTERVIEWEE_ID = "interviewee_id";
	public static final String COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID = "interviewer_id";
	public static final String COLUMN_INTERVIEW_BASIC_TYPE = "type";
	public static final String COLUMN_INTERVIEW_BASIC_IS_TEST = "is_test";
	public static final String COLUMN_INTERVIEW_BASIC_START_TIME = "start_time";
	public static final String COLUMN_INTERVIEW_BASIC_STATUS = "status";
	public static final String COLUMN_INTERVIEW_BASIC_CUR_QUESTIONAIRE_CODE = "cur_questionaire_code";
	public static final String COLUMN_INTERVIEW_BASIC_NEXT_QUESTION_CODE = "next_question_code";
	public static final String COLUMN_INTERVIEW_BASIC_LAST_MODIFYED_TIME = "last_modified_time";
	public static final String COLUMN_INTERVIEW_BASIC_VERSION_ID = "version_id";
	public static final String COLUMN_INTERVIEW_BASIC_QUIT_REASON = "quit_reason";
	public static final String COLUMN_INTERVIEW_BASIC_UPLOAD_STATUS = "upload_status";
	
	/**
	 * 访谈问卷表
	 */
	public static final String COLUMN_INTERVIEW_QUESTIONAIRE_INTERVIEW_BASIC_ID = "interview_basic_id";
	public static final String COLUMN_INTERVIEW_QUESTIONAIRE_QUESTIONAIRE_CODE = "questionaire_code";
	public static final String COLUMN_INTERVIEW_QUESTIONAIRE_START_TIME = "start_time";
	public static final String COLUMN_INTERVIEW_QUESTIONAIRE_LAST_MODIFIED_TIME = "last_modified_time";
	public static final String COLUMN_INTERVIEW_QUESTIONAIRE_STATUS = "status";
	public static final String COLUMN_INTERVIEW_QUESTIONAIRE_SEQ_NUM = "seq_num";
	public static final String COLUMN_INTERVIEW_QUESTIONAIRE_VERSION_ID = "version_id";
	public static final String COLUMN_INTERVIEW_QUESTIONAIRE_REMARK = "remark";
	
	/**
	 * 访谈问题表
	 */
	public static final String COLUMN_INTERVIEW_QUESTION_INTERVIEW_BASIC_ID = "interview_basic_id";
	public static final String COLUMN_INTERVIEW_QUESTION_QUESTIONAIRE_CODE = "questionaire_code";
	public static final String COLUMN_INTERVIEW_QUESTION_QUESTION_CODE = "question_code";
	public static final String COLUMN_INTERVIEW_QUESTION_SEQ_NUM = "seq_num";
	public static final String COLUMN_INTERVIEW_QUESTION_VERSION_ID = "version_id";
	
	/**
	 * 访谈答案表
	 */
	public static final String COLUMN_INTERVIEW_ANSWER_INTERVIEW_BASIC_ID = "interview_basic_id";
	public static final String COLUMN_INTERVIEW_ANSWER_QUESTION_CODE = "question_code";
	public static final String COLUMN_INTERVIEW_ANSWER_ANSWER_LABEL = "answer_label";
	public static final String COLUMN_INTERVIEW_ANSWER_ANSWER_CODE = "answer_code";
	public static final String COLUMN_INTERVIEW_ANSWER_ANSWER_VALUE = "answer_value";
	public static final String COLUMN_INTERVIEW_ANSWER_ANSWER_TEXT = "answer_text";
	public static final String COLUMN_INTERVIEW_ANSWER_ANSWER_SEQ_NUM = "answer_seq_num";
	public static final String COLUMN_INTERVIEW_ANSWER_VERSION_ID = "version_id";
	
	/**
	 * 建表
	 */
	public static final String CREATE_INTERVIEWER = "create table " + TABLE_INTERVIEWER + 
			"(" +
				"id integer primary key," +
				COLUMN_INTERVIEWER_LOGIN_NAME + " varchar," +
				COLUMN_INTERVIEWER_PASSWORD + " varchar," +
				COLUMN_INTERVIEWER_USERNAME + " varchar," +
				COLUMN_INTERVIEWER_MOBILE + " varchar," +
				COLUMN_INTERVIEWER_EMAIL + " varchar," +
				COLUMN_INTERVIEWER_WORKING_PLACE + " varchar," +
				COLUMN_INTERVIEWER_UPLOAD_STATUS + " integer" +
			")";
	public static final String CRETE_RESERVATION = "create table " + TABLE_RESERVATION + 
			"(" +
				"id integer primary key autoincrement," +
				COLUMN_RESERVATION_USERNAME + " varchar," +
				COLUMN_RESERVATION_IDENTITY_CARD + " varchar," +
				COLUMN_RESERVATION_MOBILE + " varchar," +
				COLUMN_RESERVATION_TYPE + " integer," +
				COLUMN_RESERVATION_BOOK_DATE + " varchar," +
				COLUMN_RESERVATION_FAMILY_MOBILE + " varchar" +
			")";
	public static final String CREATE_VERSION = "create table " + TABLE_VERSION +
			"(" +
				"id integer primary key autoincrement," +
				COLUMN_VERSION_NAME + " varchar," +
				COLUMN_VERSION_PUBLISH_DATE + " varchar," +
				COLUMN_VERSION_REMARK + " varchar," +
				COLUMN_VERSION_IS_CURRENT + " integer" +
			")";
	public static final String CREATE_QUESTIONAIRE = "create table " + TABLE_QUESTIONAIRE +
			"(" +
				"id integer primary key autoincrement," +
				COLUMN_QUESTIONAIRE_CODE + " varchar," +
				COLUMN_QUESTIONAIRE_TITLE + " varchar," +
				COLUMN_QUESTIONAIRE_INTRODUCTION + " varchar," +
				COLUMN_QUESTIONAIRE_REMARK + " varchar," +
				COLUMN_QUESTIONAIRE_TYPE + " integer," +
				COLUMN_QUESTIONAIRE_SEQ_NUM + " integer," +
				COLUMN_QUESTIONAIRE_VERSION_ID + " integer" +
			")";
	public static final String CREATE_QUESTION = "create table " + TABLE_QUESTION + 
			"(" +
				"id integer primary key autoincrement," +
				COLUMN_QUESTION_CODE + " varchar," +
				COLUMN_QUESTION_DESCRIPTION + " varchar," +
				COLUMN_QUESTION_REMARK + " varchar," +
				COLUMN_QUESTION_IS_END + " integer," +
				COLUMN_QUESTION_SEQ_NUM + " integer," +
				COLUMN_QUESTION_QUESTIONAIRE_CODE + " varchar," +
				COLUMN_QUESTION_ENTRY_LOGIC + " varchar," +
				COLUMN_QUESTION_EXIT_LOGIC + " varchar," +
				COLUMN_QUESTION_VERSION_ID + " integer," +
				COLUMN_QUESTION_ASSOCIATE_QUESTION_CODE + " varchar" +
			")";
	public static final String CREATE_ANSWER = "create table " + TABLE_ANSWER +
			"(" +
				"id integer primary key autoincrement," +
				COLUMN_ANSWER_CODE + " varchar," +
				COLUMN_ANSWER_LABEL + " varchar," +
				COLUMN_ANSWER_TYPE + " varchar," +
				COLUMN_ANSWER_EXTRA + " varchar," +
				COLUMN_ANSWER_SHOW_TYPE + " varchar," +
				COLUMN_ANSWER_IS_SHOW + " integer," +
				COLUMN_ANSWER_EVENT_TYPE + " varchar," +
				COLUMN_ANSWER_EVENT_EXECUTE + " varchar," +
				COLUMN_ANSWER_SEQ_NUM + " integer," +
				COLUMN_ANSWER_QUESTION_CODE + " varchar," +
				COLUMN_ANSWER_VERSION_ID + " integer" +
			")";
	public static final String CREATE_INTERVIEWEE = "create table " + TABLE_INTERVIEWEE +
			"(" +
				"id integer primary key," +
				COLUMN_INTERVIEWEE_USERNAME + " varchar," +
				COLUMN_INTERVIEWEE_IDENTITY_CARD + " varchar," +
				COLUMN_INTERVIEWEE_MOBILE + " varchar," +
				COLUMN_INTERVIEWEE_PROVINCE + " varchar," +
				COLUMN_INTERVIEWEE_CITY + " varchar," +
				COLUMN_INTERVIEWEE_ADDRESS + " varchar," +
				COLUMN_INTERVIEWEE_POST_CODE + " varchar," +
				COLUMN_INTERVIEWEE_FAMILY_MOBILE + " varchar," +
				COLUMN_INTERVIEWEE_FAMILY_ADDRESS + " varchar," +
				COLUMN_INTERVIEWEE_REMARK + " varchar," +
				COLUMN_INTERVIEWEE_DNA + " varchar," +
				COLUMN_INTERVIEWEE_UPLOAD_STATUS + " integer" +
			")";
	
	public static final String CREATE_INTERVIEW_BASIC = "create table " + TABLE_INTERVIEW_BASIC +
			"(" +
				"id integer primary key," +
				COLUMN_INTERVIEW_BASIC_INTERVIEWEE_ID + " integer," +
				COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID + " integer," +
				COLUMN_INTERVIEW_BASIC_TYPE + " integer," +
				COLUMN_INTERVIEW_BASIC_IS_TEST + " integer," +
				COLUMN_INTERVIEW_BASIC_START_TIME + " varchar," +
				COLUMN_INTERVIEW_BASIC_STATUS + " integer," +
				COLUMN_INTERVIEW_BASIC_CUR_QUESTIONAIRE_CODE + " varchar," +
				COLUMN_INTERVIEW_BASIC_NEXT_QUESTION_CODE + " varchar," +
				COLUMN_INTERVIEW_BASIC_LAST_MODIFYED_TIME + " varchar," +
				COLUMN_INTERVIEW_BASIC_VERSION_ID + " integer," +
				COLUMN_INTERVIEW_BASIC_QUIT_REASON + " varchar," +
				COLUMN_INTERVIEW_BASIC_UPLOAD_STATUS + " integer" +
			")";
	
	public static final String CREATE_INTERVIEW_QUESTIONAIRE = "create table " + TABLE_INTERVIEW_QUESTIONAIRE +
			"(" +
				"id integer primary key autoincrement," +
				COLUMN_INTERVIEW_QUESTIONAIRE_INTERVIEW_BASIC_ID + " integer," +
				COLUMN_INTERVIEW_QUESTIONAIRE_QUESTIONAIRE_CODE + " varchar," +
				COLUMN_INTERVIEW_QUESTIONAIRE_START_TIME + " varchar," +
				COLUMN_INTERVIEW_QUESTIONAIRE_LAST_MODIFIED_TIME + " varchar," +
				COLUMN_INTERVIEW_QUESTIONAIRE_STATUS + " integer," +
				COLUMN_INTERVIEW_QUESTIONAIRE_SEQ_NUM + " integer," +
				COLUMN_INTERVIEW_QUESTIONAIRE_VERSION_ID + " integer," +
				COLUMN_INTERVIEW_QUESTIONAIRE_REMARK + " varchar" +
				
			")";
	
	public static final String CREATE_INTERVIEW_QUESTION = "create table " + TABLE_INTERVIEW_QUESTION +
			"(" +
				"id integer primary key autoincrement," +
				COLUMN_INTERVIEW_QUESTION_INTERVIEW_BASIC_ID + " integer," +
				COLUMN_INTERVIEW_QUESTION_QUESTIONAIRE_CODE + " varchar," +
				COLUMN_INTERVIEW_QUESTION_QUESTION_CODE + " varchar," +
				COLUMN_INTERVIEW_QUESTION_SEQ_NUM + " integer," +
				COLUMN_INTERVIEW_QUESTION_VERSION_ID + " integer" +
			")";
	
	public static final String CREATE_INTERVIEW_ANSWER = "create table " + TABLE_INTERVIEW_ANSWER +
			"(" +
				"id integer primary key autoincrement," +
				COLUMN_INTERVIEW_ANSWER_INTERVIEW_BASIC_ID + " integer," +
				COLUMN_INTERVIEW_ANSWER_QUESTION_CODE + " varchar," +
				COLUMN_INTERVIEW_ANSWER_ANSWER_LABEL + " varchar," +
				COLUMN_INTERVIEW_ANSWER_ANSWER_CODE + " varchar," +
				COLUMN_INTERVIEW_ANSWER_ANSWER_VALUE + " varchar," +
				COLUMN_INTERVIEW_ANSWER_ANSWER_TEXT + " varchar," +
				COLUMN_INTERVIEW_ANSWER_ANSWER_SEQ_NUM + " integer," +
				COLUMN_INTERVIEW_QUESTION_VERSION_ID + " integer" +
			")";
}
