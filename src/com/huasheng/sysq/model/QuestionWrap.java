package com.huasheng.sysq.model;

import java.util.List;

public class QuestionWrap {

	private Questionaire questionaire;
	private Question question;
	private List<AnswerWrap> answerWrapList;
	private List<QuestionWrap> subQuesWrapList;
	
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public List<QuestionWrap> getSubQuesWrapList() {
		return subQuesWrapList;
	}
	public void setSubQuesWrapList(List<QuestionWrap> subQuesWrapList) {
		this.subQuesWrapList = subQuesWrapList;
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
