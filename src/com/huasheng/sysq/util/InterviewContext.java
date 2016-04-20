package com.huasheng.sysq.util;

import java.util.ArrayList;
import java.util.List;

import android.webkit.WebView;

import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.InterviewQuestionaire;
import com.huasheng.sysq.model.Question;

public class InterviewContext {

	private static InterviewBasic curInterviewBasic;//��ǰ���ʼ�¼
	private static InterviewQuestionaire curInterviewQuestionaire;//��ǰ�����ʾ�
	private static List<Question> questionBackStack = new ArrayList<Question>();
	
	private static WebView webView;
	
	/**
	 * ================�����������Ϣ===================
	 */
	public static void clearInterviewContext(){
		curInterviewBasic = null;
		curInterviewQuestionaire = null;
		questionBackStack.clear();
		webView = null;
	}
	public static void clearQuestionaireContext(){
		curInterviewQuestionaire = null;
		questionBackStack.clear();
	}
	
	/**
	 * ================���ⷵ��ջ����===================
	 */
	public static void pushQuestion(Question question){
		questionBackStack.add(question);
	}
	public static void popQuestion(){
		questionBackStack.remove(questionBackStack.size()-1);
	}
	public static void clearQuestion(){
		questionBackStack.clear();
	}
	public static Question getTopQuestion() {
		if(questionBackStack.size() <= 0){
			return null;
		}
		return questionBackStack.get(questionBackStack.size()-1);
	}
	public static Question findQuestion(String questionCode){
		if(questionBackStack.size() <= 0){
			return null;
		}
		for(Question question : questionBackStack){
			if(question.getCode().equals(questionCode)){
				return question;
			}
		}
		return null;
	}
	public static int questionSize(){
		return questionBackStack.size();
	}
	public static List<Question> getQuestionList(){
		return questionBackStack;
	}
	
	/**
	 * ================����/��ȡ������===================
	 */
	public static InterviewBasic getCurInterviewBasic() {
		return curInterviewBasic;
	}
	public static void setCurInterviewBasic(InterviewBasic interviewBasic) {
		curInterviewBasic = interviewBasic;
	}
	public static InterviewQuestionaire getCurInterviewQuestionaire() {
		return curInterviewQuestionaire;
	}
	public static void setCurInterviewQuestionaire(
			InterviewQuestionaire curInterviewQuestionaire) {
		InterviewContext.curInterviewQuestionaire = curInterviewQuestionaire;
	}
	public static WebView getWebView() {
		return webView;
	}
	public static void setWebView(WebView webView) {
		InterviewContext.webView = webView;
	}
}
