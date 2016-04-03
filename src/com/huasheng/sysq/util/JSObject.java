package com.huasheng.sysq.util;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.huasheng.sysq.activity.IndexActivity;
import com.huasheng.sysq.model.AnswerValue;
import com.huasheng.sysq.model.Question;
import com.huasheng.sysq.model.QuestionWrap;
import com.huasheng.sysq.model.Questionaire;
import com.huasheng.sysq.model.ResultWrap;
import com.huasheng.sysq.service.InterviewService;

public class JSObject {
	
	@JavascriptInterface
	public static void jumpToFirstQuestionaire(){
		
		//获取第一个问卷
		Questionaire firstQuestionaire = InterviewService.getFirstQuestionaire();
		
		//设置上下文
		InterviewContext.setCurrentQuestionaire(firstQuestionaire);
		
		if(StringUtils.isEmpty(StringUtils.trim(firstQuestionaire.getIntroduction()))){//问卷没有介绍
			
			JSObject.jumpToFirstQuestion();//跳转第一个问题
			
		}else{
			
			RenderUtils.render(TemplateConstants.QUESTIONAIRE, firstQuestionaire,null);
		}
	}

	@JavascriptInterface
	public static void jumpToFirstQuestion(){
		
		//获取第一个问题
		QuestionWrap firstQuesWrap = InterviewService.getFirstQuestion();
		
		//设置上下文
		InterviewContext.pushStack(firstQuesWrap.getQuestion());
		
		//执行进入逻辑代码
		if(StringUtils.isEmpty(StringUtils.trim(firstQuesWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(firstQuesWrap.getQuestion().getEntryLogic());
		}
		
		//渲染页面
		RenderUtils.render(TemplateConstants.QUESTION, firstQuesWrap,new String[]{"extra"});
	}
	
	@JavascriptInterface
	public void jumpToNextQuestion(){
		
		//查询数据
		QuestionWrap nextQuestionWrap = InterviewService.getNextQuestion();
		
		//保存当前题目到上下文
		InterviewContext.pushStack(nextQuestionWrap.getQuestion());
		
		//执行进入逻辑代码
		if(StringUtils.isEmpty(StringUtils.trim(nextQuestionWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(nextQuestionWrap.getQuestion().getEntryLogic());
		}
		
		//页面渲染
		RenderUtils.render(TemplateConstants.QUESTION, nextQuestionWrap,new String[]{"extra"});
	}
	
	@JavascriptInterface
	public void jumpToAnswerList(String answersJS){
		Map<String,AnswerValue> answerValueMap = (Map<String,AnswerValue>)RenderUtils.fromJson(answersJS, new TypeToken<Map<String,AnswerValue>>(){}.getType());
		ResultWrap resultWrap = InterviewService.getAnswerList(answerValueMap);
		RenderUtils.render(TemplateConstants.ANSWERS, resultWrap,null);
	}
	
	@JavascriptInterface
	public void saveQuestionaire(String answersJS){
		
		//保存当前问卷答案
		Map<String,AnswerValue> answerValueMap = (Map<String,AnswerValue>)RenderUtils.fromJson(answersJS, new TypeToken<Map<String,AnswerValue>>(){}.getType());
		InterviewService.saveAnswers(answerValueMap);
		
		//获取下一个问卷
		Questionaire nextQuestionaire = InterviewService.getNextQuestionaire();
		
		//最后一个问卷
		if(nextQuestionaire == null){
			
			//更新访谈状态为完成
			InterviewService.finishInterview();
			
			//跳转首页
			Intent indexActivityIntent = new Intent(MyApplication.getContext(),IndexActivity.class);
			indexActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			MyApplication.getContext().startActivity(indexActivityIntent);
			
			return;
		}
		
		//保存到访谈上下文
		InterviewContext.setCurrentQuestionaire(nextQuestionaire);
		InterviewContext.clearStack();
		
		//页面渲染
		if(StringUtils.isEmpty(StringUtils.trim(nextQuestionaire.getIntroduction()))){//问卷没有介绍
			
			//跳转第一个问题
			JSObject.jumpToFirstQuestion();
		
		}else{
			RenderUtils.render(TemplateConstants.QUESTIONAIRE, nextQuestionaire, null);
		}
		
	}
	
	@JavascriptInterface
	public void quitInterview(){
		
		//更新访谈状态为结束
		InterviewService.quitInterview();
		
		//跳转主页
		Intent indexActivityIntent = new Intent(MyApplication.getContext(),IndexActivity.class);
		indexActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		MyApplication.getContext().startActivity(indexActivityIntent);
		return;
	}
	
	@JavascriptInterface
	public void jumpToPreviousQuestion(){
		
		//从返回栈获取上一个问题
		Question prevQuestion = InterviewContext.getPrevQuestion();
		
		//已经为第一个题目
		if(prevQuestion == null){
			Toast.makeText(MyApplication.getContext(), "已经是第一题", Toast.LENGTH_SHORT).show();
			return;
		}
		
		//查询上一个问题
		QuestionWrap prevQuestionWrap = InterviewService.wrap(prevQuestion);
		
		//更新问题返回栈
		InterviewContext.popStack();
		
		//执行进入逻辑代码
		if(StringUtils.isEmpty(StringUtils.trim(prevQuestionWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(prevQuestionWrap.getQuestion().getEntryLogic());
		}
		
		//渲染页面
		RenderUtils.render(TemplateConstants.QUESTION, prevQuestionWrap,new String[]{"extra"});
	}
	
	@JavascriptInterface
	public void pauseInterview(String answersJS){
		
		if(!answersJS.equals("{}")){
			
			//保存当前问卷答案
			Map<String,AnswerValue> answerValueMap = (Map<String,AnswerValue>)RenderUtils.fromJson(answersJS, new TypeToken<Map<String,AnswerValue>>(){}.getType());
			InterviewService.saveAnswers(answerValueMap);
			
			//记录当前位置
			InterviewService.recordInterviewProgress();
		}
		
		//跳转主页
		Intent indexActivityIntent = new Intent(MyApplication.getContext(),IndexActivity.class);
		indexActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		MyApplication.getContext().startActivity(indexActivityIntent);
	}
	
	@JavascriptInterface
	public void showMsg(String msg){
		Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
	}
	
	@JavascriptInterface
	public void jumpToSpecQuestion(String questionCode){
		
		//查询指定问题
		QuestionWrap specQuestionWrap = InterviewService.getSpecQuestion(questionCode);
		
		//保存当前题目到上下文
		InterviewContext.pushStack(specQuestionWrap.getQuestion());
		
		//执行进入逻辑代码
		if(StringUtils.isEmpty(StringUtils.trim(specQuestionWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(specQuestionWrap.getQuestion().getEntryLogic());
		}
		
		//渲染数据
		RenderUtils.render(TemplateConstants.QUESTION, specQuestionWrap,new String[]{"extra"});
	}
	
	@JavascriptInterface
	public void debug(String msg){
		Log.d("JSObject", msg);
	}
}
