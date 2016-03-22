package com.huasheng.sysq.model;

/**
 * ´ð°¸
 * @author map
 *
 */
public class Answer {

	private int id;
	private String code;
	private String label;
	private String type;
	private String extra;
	private int isShow;
	private String eventType;
	private String eventExecute;
	private int seqNum;
	private String questionCode;
	private int versionId;
	
	public static final String TYPE_RADIOGROUP = "radiogroup";
	public static final String TYPE_SLIDER = "slider";
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getEventExecute() {
		return eventExecute;
	}
	public void setEventExecute(String eventExecute) {
		this.eventExecute = eventExecute;
	}
	public int getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}
	public String getQuestionCode() {
		return questionCode;
	}
	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}
	public int getVersionId() {
		return versionId;
	}
	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}
	public int getIsShow() {
		return isShow;
	}
	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}
}
