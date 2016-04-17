package com.huasheng.sysq.model;

public class InterviewAnswer {

	private int id;
	private int interviewBasicId;
	private String questionCode;
	private String answerCode;
	private String answerValue;
	private String answerText;
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
	public String getQuestionCode() {
		return questionCode;
	}
	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}
	public String getAnswerCode() {
		return answerCode;
	}
	public void setAnswerCode(String answerCode) {
		this.answerCode = answerCode;
	}
	public String getAnswerValue() {
		return answerValue;
	}
	public void setAnswerValue(String answerValue) {
		this.answerValue = answerValue;
	}
	public int getVersionId() {
		return versionId;
	}
	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}
	public String getAnswerText() {
		return answerText;
	}
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
}
