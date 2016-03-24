package com.huasheng.sysq.util;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.huasheng.sysq.model.QuestionWrap;
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
		Toast.makeText(MyApplication.getContext(), answersJS, Toast.LENGTH_LONG).show();
	}
	
	@JavascriptInterface
	public void showMsg(String msg){
		Log.d("JSObject", msg);
	}
}
