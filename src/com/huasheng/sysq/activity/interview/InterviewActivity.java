package com.huasheng.sysq.activity.interview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.InterviewQuestionaire;
import com.huasheng.sysq.model.Questionaire;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.util.BreakLineUtils;
import com.huasheng.sysq.util.DateTimeUtils;
import com.huasheng.sysq.util.InterviewContext;
import com.huasheng.sysq.util.JSObject;
import com.huasheng.sysq.util.RenderUtils;
import com.huasheng.sysq.util.TemplateConstants;

public class InterviewActivity extends Activity{
	
	private WebView interviewWV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_interview);
		
		interviewWV = (WebView)findViewById(R.id.interview_web_view);
		
		interviewWV.getSettings().setJavaScriptEnabled(true);//启用js
		
		interviewWV.setWebViewClient(new WebViewClient(){

			@Override
			public void onPageFinished(WebView view, String url) {//页面加载完成回调
				
				//获取第一个问卷
				Questionaire firstQuestionaire = InterviewService.getFirstQuestionaire();
				
				//新建问卷记录
				InterviewQuestionaire interviewQuestionaire = InterviewService.newInterviewQuestionaire(firstQuestionaire);
				
				//更新访问记录
				InterviewBasic curInterviewBasic = InterviewContext.getCurInterviewBasic();
				curInterviewBasic.setCurQuestionaireCode(firstQuestionaire.getCode());
				curInterviewBasic.setLastModifiedTime(DateTimeUtils.getCurTime());
				
				//保存上下文
				InterviewContext.setCurInterviewQuestionaire(interviewQuestionaire);
				
				//渲染页面
				firstQuestionaire.setIntroduction(BreakLineUtils.handleParaInHTML(firstQuestionaire.getIntroduction()));
				RenderUtils.render(TemplateConstants.QUESTIONAIRE, firstQuestionaire,null);
			}
		});
		
		InterviewContext.setWebView(interviewWV);//保存到上下文
		
		interviewWV.addJavascriptInterface(new JSObject(), "appservice");//注册JSObject
		
		interviewWV.loadUrl("file:///android_asset/interview.html");
	}

}
