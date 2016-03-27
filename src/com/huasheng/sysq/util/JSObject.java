package com.huasheng.sysq.util;

import java.util.Map;

import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.huasheng.sysq.activity.IndexActivity;
import com.huasheng.sysq.model.AnswerValue;
import com.huasheng.sysq.model.QuestionWrap;
import com.huasheng.sysq.model.Questionaire;
import com.huasheng.sysq.model.ResultWrap;
import com.huasheng.sysq.service.InterviewService;

public class JSObject {

	@JavascriptInterface
	public void getFirstQuestion(){
		QuestionWrap firstQuesWrap = InterviewService.getFirstQuestion();
		RenderUtils.render(TemplateConstants.QUESTION, firstQuesWrap,new String[]{"extra"});
		InterviewContext.setCurrentQuestion(firstQuesWrap.getQuestion());
	}
	
	@JavascriptInterface
	public void jumpToNextQuestion(){
		QuestionWrap nextQuestionWrap = InterviewService.getNextQuestion();
		
		//保存当前题目到上下文
		InterviewContext.setCurrentQuestion(nextQuestionWrap.getQuestion());
		
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
		Questionaire nextQuestionaire = InterviewService.saveQuestionaire(answerValueMap);
		
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
		InterviewContext.setCurrentQuestion(null);
		
		//页面渲染
		RenderUtils.render(TemplateConstants.QUESTIONAIRE, nextQuestionaire, null);
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
		
		//查询上一个问题
		QuestionWrap previousQuestionWrap = InterviewService.getPreviousQuestion();
		
		//已经为第一个题目
		if(previousQuestionWrap == null){
			Toast.makeText(MyApplication.getContext(), "已经是第一题", Toast.LENGTH_SHORT).show();
			return;
		}
		
		//保存当前题目到上下文
		InterviewContext.setCurrentQuestion(previousQuestionWrap.getQuestion());
		
		//渲染页面
		RenderUtils.render(TemplateConstants.QUESTION, previousQuestionWrap,new String[]{"extra"});
	}
	
	@JavascriptInterface
	public void showMsg(String msg){
		Log.d("JSObject", msg);
	}
}
