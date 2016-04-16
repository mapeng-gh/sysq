package com.huasheng.sysq.model;

public class InterviewQuestionaire {
	
	private int id;
	private int interviewBasicId;
	private String questionaireCode;
	private String startTime;
	private String endTime;
	private int versionId;
	
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
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getVersionId() {
		return versionId;
	}
	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}
	
}
