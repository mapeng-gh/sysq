package com.huasheng.sysq.model;

public class InterviewQuestionaire {
	
	private int id;
	private int interviewBasicId;
	private String questionaireCode;
	private String startTime;
	private String lastModifiedTime;
	private int status;
	private int versionId;
	
	public static final int STATUS_DOING = 1;
	public static final int STATUS_BREAK = 2;
	public static final int STATUS_DONE = 3;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInterviewBasicId() {
		return interviewBasicId;
	}
	public void setInterviewBasicId(int interviewBasicId) {
		this.interviewBasicId = interviewBasicId;
	}
	public String getQuestionaireCode() {
		return questionaireCode;
	}
	public void setQuestionaireCode(String questionaireCode) {
		this.questionaireCode = questionaireCode;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public int getVersionId() {
		return versionId;
	}
	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}
	public String getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
