package com.huasheng.sysq.model;

import java.util.List;

public class InterviewQuestionWrap {

	private Question question;
	private List<InterviewAnswerWrap> answerWrapList;
	
	public InterviewQuestionWrap(){
		
	}
	
	public InterviewQuestionWrap(Question question,List<InterviewAnswerWrap> answerWrapList){
		this.question = question;
		this.answerWrapList = answerWrapList;
	}

	public Question getQuestion() {
		return question;
	}

	public List<InterviewAnswerWrap> getAnswerWrapList() {
		return answerWrapList;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public void setAnswerWrapList(List<InterviewAnswerWrap> answerWrapList) {
		this.answerWrapList = answerWrapList;
	}
}
