package com.huasheng.sysq.activity.interview;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huasheng.sysq.R;
import com.huasheng.sysq.util.InterviewContext;
import com.huasheng.sysq.util.JsonUtils;
import com.huasheng.sysq.util.TemplateUtils;

public class InterviewActivity extends Activity{
	
	private WebView interviewWV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_interview);
		
		interviewWV = (WebView)findViewById(R.id.interview_web_view);
		
		interviewWV.getSettings().setJavaScriptEnabled(true);//����js
		
		interviewWV.setWebViewClient(new WebViewClient(){

			@Override
			public void onPageFinished(WebView view, String url) {//ҳ�������ɻص�
				
				String tpl = TemplateUtils.loadTemplate("questionaire.tpl");
				String data = JsonUtils.toJson(InterviewContext.getCurrentQuestionaire());
				view.loadUrl("javascript:renderContent('"+tpl+"','"+data+"')");
			}
			
		});
		
		InterviewContext.setWebView(interviewWV);//���浽������
		
		interviewWV.addJavascriptInterface(new JSObject(), "appservice");//ע��JSObject
		
		interviewWV.loadUrl("file:///android_asset/interview.html");
	}

}
