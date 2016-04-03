package com.huasheng.sysq.model;

/**
 * Œ Ã‚
 * @author map
 *
 */
public class Question {
	
	private int id;
	private String code;
	private String description;
	private String remark;
	private int isEnd;
	private int seqNum;
	private String questionaireCode;
	private String entryLogic;
	private String exitLogic;
	private int versionId;
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getIsEnd() {
		return isEnd;
	}
	public void setIsEnd(int isEnd) {
		this.isEnd = isEnd;
	}
	public int getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}
	public String getQuestionaireCode() {
		return questionaireCode;
	}
	public void setQuestionaireCode(String questionaireCode) {
		this.questionaireCode = questionaireCode;
	}
	public String getEntryLogic() {
		return entryLogic;
	}
	public void setEntryLogic(String entryLogic) {
		this.entryLogic = entryLogic;
	}
	public String getExitLogic() {
		return exitLogic;
	}
	public void setExitLogic(String exitLogic) {
		this.exitLogic = exitLogic;
	}
	public int getVersionId() {
		return versionId;
	}
	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}
}
