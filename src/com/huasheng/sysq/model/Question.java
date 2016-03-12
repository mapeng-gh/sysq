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
	private int type;
	private int showType;
	private int isEnd;
	private int seqNum;
	private int questionaireCode;
	private int parentQuesCode;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getShowType() {
		return showType;
	}
	public void setShowType(int showType) {
		this.showType = showType;
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
	public int getQuestionaireCode() {
		return questionaireCode;
	}
	public void setQuestionaireCode(int questionaireCode) {
		this.questionaireCode = questionaireCode;
	}
	public int getParentQuesCode() {
		return parentQuesCode;
	}
	public void setParentQuesCode(int parentQuesCode) {
		this.parentQuesCode = parentQuesCode;
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
