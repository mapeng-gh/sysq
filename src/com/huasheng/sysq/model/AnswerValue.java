package com.huasheng.sysq.model;

public class AnswerValue {

	private String code;//用于保存
	private String label;//用于答案列表显示
	private String text;//用于答案列表显示
	private String value;//用于保存
	
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
