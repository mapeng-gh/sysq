package com.huasheng.sysq.model;

import java.util.List;

public class QuestionWrap {

	private Questionaire questionaire;
	private Question question;
	private List<AnswerWrap> answerWrapList;
	
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public Questionaire getQuestionaire() {
		return questionaire;
	}
	public void setQuestionaire(Questionaire questionaire) {
		this.questionaire = questionaire;
	}
	public List<AnswerWrap> getAnswerWrapList() {
		return answerWrapList;
	}
	public void setAnswerWrapList(List<AnswerWrap> answerWrapList) {
		this.answerWrapList = answerWrapList;
	}
	
}
