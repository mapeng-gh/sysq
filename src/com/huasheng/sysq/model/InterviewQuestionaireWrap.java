package com.huasheng.sysq.model;

public class InterviewQuestionaireWrap {

	private InterviewQuestionaire interviewQuestionaire;
	private Questionaire questionaire;
	
	public InterviewQuestionaireWrap(InterviewQuestionaire interviewQuestionaire,Questionaire questionaire){
		this.interviewQuestionaire = interviewQuestionaire;
		this.questionaire = questionaire;
	}

	public InterviewQuestionaire getInterviewQuestionaire() {
		return interviewQuestionaire;
	}

	public Questionaire getQuestionaire() {
		return questionaire;
	}
	
}
