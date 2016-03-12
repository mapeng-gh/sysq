package com.huasheng.sysq.util;

public class TableConstants {

	/**
	 * 所有表名
	 */
	public static final String TABLE_INTERVIEWER = "interviewer";
	public static final String TABLE_RESERVATION = "reservation";
	public static final String TABLE_VERSION = "version";
	public static final String TABLE_QUESTIONAIRE = "questionaire";
	public static final String TABLE_QUESTION = "question";
	public static final String TABLE_ANSWER = "answer";
	public static final String TABLE_INTERVIEW = "interview";
	public static final String TABLE_RESULT = "result";
	
	/**
	 * 访问者列名
	 */
	public static final String COLUMN_INTERVIEWER_LOGIN_NAME = "login_name";
	public static final String COLUMN_INTERVIEWER_PASSWORD = "password";
	public static final String COLUMN_INTERVIEWER_USERNAME = "username";
	public static final String COLUMN_INTERVIEWER_MOBILE = "mobile";
	public static final String COLUMN_INTERVIEWER_EMAIL = "email";
	public static final String COLUMN_INTERVIEWER_WORKING_PLACE = "working_place";
	
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
	public static final String COLUMN_QUESTION_TYPE = "type";
	public static final String COLUMN_QUESTION_SHOW_TYPE = "show_type";
	public static final String COLUMN_QUESTION_IS_END = "is_end";
	public static final String COLUMN_QUESTION_SEQ_NUM = "seq_num";
	public static final String COLUMN_QUESTION_QUESTIONAIRE_CODE = "questionaire_code";
	public static final String COLUMN_QUESTION_P_QUESTION_CODE = "p_question_code";
	public static final String COLUMN_QUESTION_ENTRY_LOGIC = "entry_logic";
	public static final String COLUMN_QUESTION_EXIT_LOGIC = "exit_logic";
	public static final String COLUMN_QUESTION_VERSION_ID = "version_id";
	
	/**
	 * 答案表列名
	 */
	public static final String COLUMN_ANSWER_CODE = "code";
	public static final String COLUMN_ANSWER_LABEL = "label";
	public static final String COLUMN_ANSWER_TYPE = "type";
	public static final String COLUMN_ANSWER_EXTRA = "extra";
	public static final String COLUMN_ANSWER_IS_SHOW = "is_show";
	public static final String COLUMN_ANSWER_EVENT_TYPE = "event_type";
	public static final String COLUMN_ANSWER_EVENT_EXECUTE = "event_execute";
	public static final String COLUMN_ANSWER_SEQ_NUM = "seq_num";
	public static final String COLUMN_ANSWER_QUESTION_CODE = "question_code";
	public static final String COLUMN_ANSWER_VERSION_ID = "version_id";
	
	/**
	 * 访问表列名
	 */
	public static final String COLUMN_INTERVIEW_USERNAME = "username";
	public static final String COLUMN_INTERVIEW_IDENTITY_CARD = "identity_card";
	public static final String COLUMN_INTERVIEW_MOBILE = "mobile";
	public static final String COLUMN_INTERVIEW_PROVINCE = "province";
	public static final String COLUMN_INTERVIEW_CITY = "city";
	public static final String COLUMN_INTERVIEW_ADDRESS = "address";
	public static final String COLUMN_INTERVIEW_POST_CODE = "post_code";
	public static final String COLUMN_INTERVIEW_FAMILY_MOBILE = "family_mobile";
	public static final String COLUMN_INTERVIEW_FAMILY_ADDRESS = "family_address";
	public static final String COLUMN_INTERVIEW_DNA = "dna";
	public static final String COLUMN_INTERVIEW_INTERVIEWER_ID = "interviewer_id";
	public static final String COLUMN_INTERVIEW_TYPE = "type";
	public static final String COLUMN_INTERVIEW_IS_TEST = "is_test";
	public static final String COLUMN_INTERVIEW_START_TIME = "start_time";
	public static final String COLUMN_INTERVIEW_STATUS = "status";
	public static final String COLUMN_INTERVIEW_CUR_QUESTIONAIRE_CODE = "cur_questionaire_code";
	public static final String COLUMN_INTERVIEW_NEXT_QUESTION_CODE = "next_question_code";
	public static final String COLUMN_INTERVIEW_END_TIME = "end_time";
	public static final String COLUMN_INTERVIEW_VERSION_ID = "version_id";
	
	/**
	 * 结果表列名
	 */
	public static final String COLUMN_RESULT_INTERVIEW_ID = "interview_id";
	public static final String COLUMN_RESULT_INTERVIEW_QUESTION_CODE = "question_code";
	public static final String COLUMN_RESULT_INTERVIEW_QUESTION_VALUE = "question_value";
	
}
