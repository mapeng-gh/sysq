package com.huasheng.sysq.util;

import java.util.ArrayList;
import java.util.List;

import android.webkit.WebView;

import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.Question;
import com.huasheng.sysq.model.Questionaire;

public class InterviewContext {

	private static InterviewBasic interviewBasic;//访问记录
	private static Questionaire curQuestionaire;//当前问卷
	private static String questionaireStartTime;//问卷开始时间
	private static List<Question> questionBackStack = new ArrayList<Question>();
	
	private static WebView webView;
	
	/**
	 * ================清空上下文信息===================
	 */
	public static void clearInterviewContext(){
		interviewBasic = null;
		curQuestionaire = null;
		questionBackStack.clear();
		questionaireStartTime = null;
		webView = null;
	}
	public static void clearQuestionaireContext(){
		curQuestionaire = null;
		questionBackStack.clear();
		questionaireStartTime = null;
	}
	
	/**
	 * ================问题返回栈操作===================
	 */
	public static void pushQuestion(Question question){
		questionBackStack.add(question);
	}
	public static void popQuestion(){
		questionBackStack.remove(questionBackStack.size()-1);
	}
	public static void clearQuestionStack(){
		questionBackStack.clear();
	}
	public static Question getCurQuestion() {
		if(questionBackStack.size() <= 0){
			return null;
		}
		return questionBackStack.get(questionBackStack.size()-1);
	}
	public static Question getPrevQuestion(){
		if(questionBackStack.size() <= 1){
			return null;
		}
		return questionBackStack.get(questionBackStack.size()-2);
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
	
	/**
	 * ================设置/获取上下文===================
	 */
	public static InterviewBasic getInterviewBasic() {
		return interviewBasic;
	}
	public static void setInterview(InterviewBasic interviewBasic) {
		InterviewContext.interviewBasic = interviewBasic;
	}
	
	public static Questionaire getCurQuestionaire() {
		return curQuestionaire;
	}
	public static void setCurQuestionaire(Questionaire curQuestionaire) {
		InterviewContext.curQuestionaire = curQuestionaire;
	}
	public static WebView getWebView() {
		return webView;
	}
	public static void setWebView(WebView webView) {
		InterviewContext.webView = webView;
	}

	public static String getQuestionaireStartTime() {
		return questionaireStartTime;
	}

	public static void setQuestionaireStartTime(String questionaireStartTime) {
		InterviewContext.questionaireStartTime = questionaireStartTime;
	}
}
