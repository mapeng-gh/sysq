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
		
		//���浱ǰ��Ŀ��������
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
		
		//���浱ǰ�ʾ��
		Map<String,AnswerValue> answerValueMap = (Map<String,AnswerValue>)RenderUtils.fromJson(answersJS, new TypeToken<Map<String,AnswerValue>>(){}.getType());
		Questionaire nextQuestionaire = InterviewService.saveQuestionaire(answerValueMap);
		
		//���һ���ʾ�
		if(nextQuestionaire == null){
			
			//���·�̸״̬Ϊ���
			InterviewService.finishInterview();
			
			//��ת��ҳ
			Intent indexActivityIntent = new Intent(MyApplication.getContext(),IndexActivity.class);
			indexActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			MyApplication.getContext().startActivity(indexActivityIntent);
			
			return;
		}
		
		//���浽��̸������
		InterviewContext.setCurrentQuestionaire(nextQuestionaire);
		InterviewContext.setCurrentQuestion(null);
		
		//ҳ����Ⱦ
		RenderUtils.render(TemplateConstants.QUESTIONAIRE, nextQuestionaire, null);
	}
	
	@JavascriptInterface
	public void quitInterview(){
		
		//���·�̸״̬Ϊ����
		InterviewService.quitInterview();
		
		//��ת��ҳ
		Intent indexActivityIntent = new Intent(MyApplication.getContext(),IndexActivity.class);
		indexActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		MyApplication.getContext().startActivity(indexActivityIntent);
		return;
	}
	
	@JavascriptInterface
	public void jumpToPreviousQuestion(){
		
		//��ѯ��һ������
		QuestionWrap previousQuestionWrap = InterviewService.getPreviousQuestion();
		
		//�Ѿ�Ϊ��һ����Ŀ
		if(previousQuestionWrap == null){
			Toast.makeText(MyApplication.getContext(), "�Ѿ��ǵ�һ��", Toast.LENGTH_SHORT).show();
			return;
		}
		
		//���浱ǰ��Ŀ��������
		InterviewContext.setCurrentQuestion(previousQuestionWrap.getQuestion());
		
		//��Ⱦҳ��
		RenderUtils.render(TemplateConstants.QUESTION, previousQuestionWrap,new String[]{"extra"});
	}
	
	@JavascriptInterface
	public void showMsg(String msg){
		Log.d("JSObject", msg);
	}
}
