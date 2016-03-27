package com.huasheng.sysq.util;

import java.util.Map;

import android.util.Log;
import android.webkit.JavascriptInterface;

import com.google.gson.reflect.TypeToken;
import com.huasheng.sysq.model.AnswerValue;
import com.huasheng.sysq.model.QuestionWrap;
import com.huasheng.sysq.model.ResultWrap;
import com.huasheng.sysq.service.InterviewService;

public class JSObject {

	@JavascriptInterface
	public void getFirstQuestion(){
		QuestionWrap firstQuesWrap = InterviewService.getFirstQuestion(InterviewContext.getCurrentQuestionaire().getCode());
		RenderUtils.render(TemplateConstants.QUESTION, firstQuesWrap,new String[]{"extra"});
	}
	
	@JavascriptInterface
	public void jumpToNextQuestion(){
		QuestionWrap nextQuestionWrap = InterviewService.getNextQuestion(InterviewContext.getCurrentQuestionaire().getCode(), 
				InterviewContext.getCurrentQuestion().getCode());
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
		Map<String,AnswerValue> answerValueMap = (Map<String,AnswerValue>)RenderUtils.fromJson(answersJS, new TypeToken<Map<String,AnswerValue>>(){}.getType());
		
	}
	
	@JavascriptInterface
	public void showMsg(String msg){
		Log.d("JSObject", msg);
	}
}
