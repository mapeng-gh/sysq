package com.huasheng.sysq.util;

import com.huasheng.sysq.model.Interview;
import com.huasheng.sysq.model.Question;
import com.huasheng.sysq.model.Questionaire;

public class InterviewContext {

	private static Interview interview;//���ʼ�¼
	public static Questionaire currentQuestionaire;//��ǰ�ʾ�
	public static Question currentQuestion;//��ǰ����
	
	public static Interview getInterview() {
		return interview;
	}
	public static void setInterview(Interview interview) {
		InterviewContext.interview = interview;
	}
	public static Questionaire getCurrentQuestionaire() {
		return currentQuestionaire;
	}
	public static void setCurrentQuestionaire(Questionaire currentQuestionaire) {
		InterviewContext.currentQuestionaire = currentQuestionaire;
	}
	public static Question getCurrentQuestion() {
		return currentQuestion;
	}
	public static void setCurrentQuestion(Question currentQuestion) {
		InterviewContext.currentQuestion = currentQuestion;
	}
}
