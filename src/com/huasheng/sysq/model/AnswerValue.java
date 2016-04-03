package com.huasheng.sysq.model;

public class AnswerValue {

	private String answerCode;//用于保存
	private String answerLabel;//用于答案列表显示
	private String answerText;//用于答案列表显示
	private String answerValue;//用于保存
	
	public String getAnswerCode() {
		return answerCode;
	}
	public void setAnswerCode(String answerCode) {
		this.answerCode = answerCode;
	}
	public String getAnswerText() {
		return answerText;
	}
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
	public String getAnswerLabel() {
		return answerLabel;
	}
	public void setAnswerLabel(String answerLabel) {
		this.answerLabel = answerLabel;
	}
	public String getAnswerValue() {
		return answerValue;
	}
	public void setAnswerValue(String answerValue) {
		this.answerValue = answerValue;
	}
}
