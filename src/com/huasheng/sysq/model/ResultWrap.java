package com.huasheng.sysq.model;

import java.util.List;
import java.util.Map;

public class ResultWrap {

	private Questionaire questionaire;
	private List<Question> questionList;
	private Map<String,List<AnswerValue>> answerOfQuestionMap;
	
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
	public Map<String, List<AnswerValue>> getAnswerOfQuestionMap() {
		return answerOfQuestionMap;
	}
	public void setAnswerOfQuestionMap(
			Map<String, List<AnswerValue>> answerOfQuestionMap) {
		this.answerOfQuestionMap = answerOfQuestionMap;
	}
}
