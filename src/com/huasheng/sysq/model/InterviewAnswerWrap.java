package com.huasheng.sysq.model;

public class InterviewAnswerWrap {

	private InterviewAnswer interviewAnswer;
	private Answer answer;
	
	public InterviewAnswerWrap(){
		
	}
	
	public InterviewAnswerWrap(InterviewAnswer interviewAnswer,Answer answer){
		this.interviewAnswer = interviewAnswer;
		this.answer = answer;
	}

	public InterviewAnswer getInterviewAnswer() {
		return interviewAnswer;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setInterviewAnswer(InterviewAnswer interviewAnswer) {
		this.interviewAnswer = interviewAnswer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
}
