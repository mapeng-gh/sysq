package com.huasheng.sysq.model;

import java.util.List;
import java.util.Map;

public class ResultWrap {

	private Questionaire questionaire;
	private List<Question> questionList;
	private Map<String,List<Question>> subQuesListMap;
	private Map<String,String> answerMap;
	
	public Questionaire getQuestionaire() {
		return questionaire;
	}
	public void setQuestionaire(Questionaire questionaire) {
		this.questionaire = questionaire;
	}
	public List<Question> getQuestionList() {
		return questionList;
	}
	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}
	public Map<String, List<Question>> getSubQuesListMap() {
		return subQuesListMap;
	}
	public void setSubQuesListMap(Map<String, List<Question>> subQuesListMap) {
		this.subQuesListMap = subQuesListMap;
	}
	public Map<String, String> getAnswerMap() {
		return answerMap;
	}
	public void setAnswerMap(Map<String, String> answerMap) {
		this.answerMap = answerMap;
	}
	
}
