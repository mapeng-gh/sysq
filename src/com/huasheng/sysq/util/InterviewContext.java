package com.huasheng.sysq.util;

import java.util.ArrayList;
import java.util.List;

import android.webkit.WebView;

import com.huasheng.sysq.model.Interview;
import com.huasheng.sysq.model.Question;
import com.huasheng.sysq.model.Questionaire;

public class InterviewContext {

	private static Interview interview;//访问记录
	private static Questionaire currentQuestionaire;//当前问卷
	private static List<Question> questionStack = new ArrayList<Question>();
	
	private static WebView webView;
	
	public static void clearContext(){
		interview = null;
		currentQuestionaire = null;
		webView = null;
		questionStack.clear();
	}
	
	public static void pushStack(Question question){
		questionStack.add(question);
	}
	public static Question getCurrentQuestion() {
		return questionStack.get(questionStack.size()-1);
	}
	public static Question getPrevQuestion(){
		if(questionStack.size() <= 1){
			return null;
		}
		return questionStack.get(questionStack.size()-2);
	}
	public static void popStack(){
		questionStack.remove(questionStack.size()-1);
	}
	public static Question findQuestion(String questionCode){
		if(questionStack.size() <= 0){
			return null;
		}
		for(Question question : questionStack){
			if(question.getCode().equals(questionCode)){
				return question;
			}
		}
		return null;
	}
	
	public static void clearStack(){
		questionStack.clear();
	}
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
	public static WebView getWebView() {
		return webView;
	}
	public static void setWebView(WebView webView) {
		InterviewContext.webView = webView;
	}
}
