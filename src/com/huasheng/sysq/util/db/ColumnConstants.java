package com.huasheng.sysq.util.db;

public class ColumnConstants {

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
	public static final String COLUMN_INTERVIEW_BASIC_IS_UPLOAD = "is_upload";
	
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
}