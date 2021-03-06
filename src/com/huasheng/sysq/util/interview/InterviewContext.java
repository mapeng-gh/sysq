package com.huasheng.sysq.util.interview;

import java.util.ArrayList;
import java.util.List;

import android.webkit.WebView;

import com.huasheng.sysq.model.InterviewBasicWrap;
import com.huasheng.sysq.model.InterviewQuestionaire;
import com.huasheng.sysq.model.Question;

public class InterviewContext {

	private static int operateType;
	private static InterviewBasicWrap curInterviewBasicWrap;//当前访问记录
	private static InterviewQuestionaire curInterviewQuestionaire;//当前访问问卷
	private static List<Question> questionBackStack = new ArrayList<Question>();
	
	private static WebView webView;
	
	public static final int OPERATE_TYPE_NORMAL = 1;
	public static final int OPERATE_TYPE_MODIFY= 2;
	
	public static void setOperateType(int operateType){
		InterviewContext.operateType = operateType;
	}
	
	public static int getOperateType(){
		return InterviewContext.operateType;
	}
	
	/**
	 * ================清空上下文信息===================
	 */
	public static void clearInterviewContext(){
		curInterviewBasicWrap = null;
		curInterviewQuestionaire = null;
		questionBackStack.clear();
		webView = null;
	}
	public static void clearQuestionaireContext(){
		curInterviewQuestionaire = null;
		questionBackStack.clear();
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
	public static void setQuestionList(List<Question> questionList){
		InterviewContext.questionBackStack = questionList;
	}
	
	/**
	 * ================设置/获取上下文===================
	 */
	public static InterviewBasicWrap getCurInterviewBasicWrap() {
		return curInterviewBasicWrap;
	}
	public static void setCurInterviewBasicWrap(InterviewBasicWrap interviewBasicWrap) {
		curInterviewBasicWrap = interviewBasicWrap;
	}
	public static InterviewQuestionaire getCurInterviewQuestionaire() {
		return curInterviewQuestionaire;
	}
	public static void setCurInterviewQuestionaire(InterviewQuestionaire curInterviewQuestionaire) {
		InterviewContext.curInterviewQuestionaire = curInterviewQuestionaire;
	}
	public static WebView getWebView() {
		return webView;
	}
	public static void setWebView(WebView webView) {
		InterviewContext.webView = webView;
	}
}
