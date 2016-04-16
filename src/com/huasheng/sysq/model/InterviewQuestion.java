package com.huasheng.sysq.model;

public class InterviewQuestion {

	private int id;
	private int interviewBasicId;
	private String questionaireCode;
	private String questionCode;
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
	
	
}
