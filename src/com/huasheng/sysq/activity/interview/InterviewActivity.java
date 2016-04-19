package com.huasheng.sysq.activity.interview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.InterviewQuestionaire;
import com.huasheng.sysq.model.Questionaire;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.service.IntervieweeService;
import com.huasheng.sysq.util.BreakLineUtils;
import com.huasheng.sysq.util.DateTimeUtils;
import com.huasheng.sysq.util.InterviewContext;
import com.huasheng.sysq.util.JSObject;
import com.huasheng.sysq.util.RenderUtils;
import com.huasheng.sysq.util.SysqApplication;
import com.huasheng.sysq.util.TemplateConstants;

public class InterviewActivity extends Activity{
	
	private WebView interviewWV;
	
	private boolean isRequestFromOutside = false;
	private int interviewBasicId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_interview);
		
		//��ȡ�ⲿ����
		Intent intent = getIntent();
		interviewBasicId = intent.getIntExtra("interviewBasicId", -1);
		if(interviewBasicId != -1){
			isRequestFromOutside = true;
		}
		
		interviewWV = (WebView)findViewById(R.id.interview_web_view);
		
		interviewWV.getSettings().setJavaScriptEnabled(true);//����js
		
		interviewWV.setWebViewClient(new WebViewClient(){

			@Override
			public void onPageFinished(WebView view, String url) {//ҳ�������ɻص�
				
				if(InterviewActivity.this.isRequestFromOutside == false){
					
					//��ȡ��һ���ʾ�
					Questionaire firstQuestionaire = InterviewService.getFirstQuestionaire();
					
					//�½��ʾ��¼
					InterviewQuestionaire interviewQuestionaire = InterviewService.newInterviewQuestionaire(firstQuestionaire);
					
					//���·��ʼ�¼
					InterviewBasic curInterviewBasic = InterviewContext.getCurInterviewBasic();
					curInterviewBasic.setCurQuestionaireCode(firstQuestionaire.getCode());
					curInterviewBasic.setNextQuestionCode("");
					curInterviewBasic.setLastModifiedTime(DateTimeUtils.getCurTime());
					InterviewService.updateInterviewBasic(curInterviewBasic);
					
					//����������
					InterviewContext.setCurInterviewQuestionaire(interviewQuestionaire);
					
					//��Ⱦҳ��
					firstQuestionaire.setIntroduction(BreakLineUtils.handleParaInHTML(firstQuestionaire.getIntroduction()));
					RenderUtils.render(TemplateConstants.QUESTIONAIRE, firstQuestionaire,null);
				
				}else{
					
					InterviewBasic interviewBasic = IntervieweeService.findById(InterviewActivity.this.interviewBasicId);
					
				}
				
				
			}
		});
		
		InterviewContext.setWebView(interviewWV);//���浽������
		
		interviewWV.addJavascriptInterface(new JSObject(), "appservice");//ע��JSObject
		
		interviewWV.loadUrl("file:///android_asset/interview.html");
	}

}
