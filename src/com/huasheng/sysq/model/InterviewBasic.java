package com.huasheng.sysq.model;

import java.io.Serializable;

/**
 * 访问记录表
 * @author map
 *
 */
public class InterviewBasic implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int intervieweeId;
	private int interviewerId;
	private int type;
	private int isTest;
	private String startTime;
	private int status;
	private String curQuestionaireCode;
	private String nextQuestionCode;
	private String lastModifiedTime;
	private int versionId;
	private String quitReason;
	private int uploadStatus;
	
	public static final int STATUS_DOING = 1;
	public static final int STATUS_BREAK = 2;
	public static final int STATUS_DONE = 3;
	
	public static final int TEST_YES = 1;
	public static final int TEST_NO = 0;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public int getInterviewerId() {
		return interviewerId;
	}
	public void setInterviewerId(int interviewerId) {
		this.interviewerId = interviewerId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getIsTest() {
		return isTest;
	}
	public void setIsTest(int isTest) {
		this.isTest = isTest;
	}
	public String getStartTime() {
		return startTime;
	}
	public int getVersionId() {
		return versionId;
	}
	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCurQuestionaireCode() {
		return curQuestionaireCode;
	}
	public void setCurQuestionaireCode(String curQuestionaireCode) {
		this.curQuestionaireCode = curQuestionaireCode;
	}
	public String getNextQuestionCode() {
		return nextQuestionCode;
	}
	public void setNextQuestionCode(String nextQuestionCode) {
		this.nextQuestionCode = nextQuestionCode;
	}
	public String getQuitReason() {
		return quitReason;
	}
	public void setQuitReason(String quitReason) {
		this.quitReason = quitReason;
	}
	public int getIntervieweeId() {
		return intervieweeId;
	}
	public void setIntervieweeId(int intervieweeId) {
		this.intervieweeId = intervieweeId;
	}
	public int getUploadStatus() {
		return uploadStatus;
	}
	public void setUploadStatus(int uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
}
