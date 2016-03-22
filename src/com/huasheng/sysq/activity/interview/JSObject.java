package com.huasheng.sysq.activity.interview;

import android.util.Log;
import android.webkit.JavascriptInterface;

import com.huasheng.sysq.model.QuestionWrap;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.util.InterviewContext;
import com.huasheng.sysq.util.JsonUtils;
import com.huasheng.sysq.util.TemplateUtils;

public class JSObject {

	@JavascriptInterface
	public void getFirstQuestion(){
		QuestionWrap firstQuesWrap = InterviewService.getFirstQuestion(InterviewContext.getCurrentQuestionaire().getCode());
		
		final String data = JsonUtils.toJson(firstQuesWrap);
		final String tpl = TemplateUtils.loadTemplate("question.tpl");
		
		InterviewContext.getWebView().post(new Runnable() {
			@Override
			public void run() {
				InterviewContext.getWebView().loadUrl("javascript:renderContent('"+tpl+"','"+data+"')");
			}
		});
	}
	
	@JavascriptInterface
	public void showMsg(String msg){
		Log.d("JSObject", msg);
	}
}
