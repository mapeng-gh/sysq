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
	private String username;
	private String identityCard;
	private String mobile;
	private String province;
	private String city;
	private String address;
	private String postCode;
	private String familyMobile;
	private String familyAddress;
	private String remark;
	private String dna;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getFamilyMobile() {
		return familyMobile;
	}
	public void setFamilyMobile(String familyMobile) {
		this.familyMobile = familyMobile;
	}
	public String getFamilyAddress() {
		return familyAddress;
	}
	public void setFamilyAddress(String familyAddress) {
		this.familyAddress = familyAddress;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDna() {
		return dna;
	}
	public String getLastModifiedTime() {
		return lastModifiedTime;
	}
	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public void setDna(String dna) {
		this.dna = dna;
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
	
}
