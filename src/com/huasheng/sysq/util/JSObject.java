package com.huasheng.sysq.util;

import java.lang.reflect.Type;
import java.util.Map;

import android.util.Log;
import android.webkit.JavascriptInterface;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huasheng.sysq.model.AnswerValue;
import com.huasheng.sysq.model.QuestionWrap;
import com.huasheng.sysq.model.ResultWrap;
import com.huasheng.sysq.service.InterviewService;

public class JSObject {

	@JavascriptInterface
	public void getFirstQuestion(){
		QuestionWrap firstQuesWrap = InterviewService.getFirstQuestion(InterviewContext.getCurrentQuestionaire().getCode());
		String data = RenderUtils.toJson(firstQuesWrap,new String[]{"extra"});
		
		RenderUtils.render(TemplateConstants.QUESTION, data);
	}
	
	@JavascriptInterface
	public void jumpToNextQuestion(){
		QuestionWrap nextQuestionWrap = InterviewService.getNextQuestion(InterviewContext.getCurrentQuestionaire().getCode(), 
				InterviewContext.getCurrentQuestion().getCode());
		String data = RenderUtils.toJson(nextQuestionWrap,new String[]{"extra"});
		
		RenderUtils.render(TemplateConstants.QUESTION, data);
	}
	
	@JavascriptInterface
	public void jumpToAnswerList(String answersJS){
		Type type = new TypeToken<Map<String,AnswerValue>>(){}.getType();
		Gson gson = new Gson();
		Map<String,AnswerValue> answerValueMap = gson.fromJson(answersJS, type);
		
		ResultWrap resultWrap = InterviewService.getAnswerList(answerValueMap);
		RenderUtils.render(TemplateConstants.ANSWERS, RenderUtils.toJson(resultWrap, null));
	}
	
	@JavascriptInterface
	public void showMsg(String msg){
		Log.d("JSObject", msg);
	}
}
