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
		
		//��ȡ��һ���ʾ�
		Questionaire firstQuestionaire = InterviewService.getFirstQuestionaire();
		
		//����������
		InterviewContext.setCurrentQuestionaire(firstQuestionaire);
		
		if(StringUtils.isEmpty(StringUtils.trim(firstQuestionaire.getIntroduction()))){//�ʾ�û�н���
			
			JSObject.jumpToFirstQuestion();//��ת��һ������
			
		}else{
			
			RenderUtils.render(TemplateConstants.QUESTIONAIRE, firstQuestionaire,null);
		}
	}

	@JavascriptInterface
	public static void jumpToFirstQuestion(){
		
		//��ȡ��һ������
		QuestionWrap firstQuesWrap = InterviewService.getFirstQuestion();
		
		//����������
		InterviewContext.pushStack(firstQuesWrap.getQuestion());
		
		//ִ�н����߼�����
		if(StringUtils.isEmpty(StringUtils.trim(firstQuesWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(firstQuesWrap.getQuestion().getEntryLogic());
		}
		
		//��Ⱦҳ��
		RenderUtils.render(TemplateConstants.QUESTION, firstQuesWrap,new String[]{"extra"});
	}
	
	@JavascriptInterface
	public void jumpToNextQuestion(){
		
		//��ѯ����
		QuestionWrap nextQuestionWrap = InterviewService.getNextQuestion();
		
		//���浱ǰ��Ŀ��������
		InterviewContext.pushStack(nextQuestionWrap.getQuestion());
		
		//ִ�н����߼�����
		if(StringUtils.isEmpty(StringUtils.trim(nextQuestionWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(nextQuestionWrap.getQuestion().getEntryLogic());
		}
		
		//ҳ����Ⱦ
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
		InterviewService.saveAnswers(answerValueMap);
		
		//��ȡ��һ���ʾ�
		Questionaire nextQuestionaire = InterviewService.getNextQuestionaire();
		
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
		InterviewContext.clearStack();
		
		//ҳ����Ⱦ
		if(StringUtils.isEmpty(StringUtils.trim(nextQuestionaire.getIntroduction()))){//�ʾ�û�н���
			
			//��ת��һ������
			JSObject.jumpToFirstQuestion();
		
		}else{
			RenderUtils.render(TemplateConstants.QUESTIONAIRE, nextQuestionaire, null);
		}
		
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
		
		//�ӷ���ջ��ȡ��һ������
		Question prevQuestion = InterviewContext.getPrevQuestion();
		
		//�Ѿ�Ϊ��һ����Ŀ
		if(prevQuestion == null){
			Toast.makeText(MyApplication.getContext(), "�Ѿ��ǵ�һ��", Toast.LENGTH_SHORT).show();
			return;
		}
		
		//��ѯ��һ������
		QuestionWrap prevQuestionWrap = InterviewService.wrap(prevQuestion);
		
		//�������ⷵ��ջ
		InterviewContext.popStack();
		
		//ִ�н����߼�����
		if(StringUtils.isEmpty(StringUtils.trim(prevQuestionWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(prevQuestionWrap.getQuestion().getEntryLogic());
		}
		
		//��Ⱦҳ��
		RenderUtils.render(TemplateConstants.QUESTION, prevQuestionWrap,new String[]{"extra"});
	}
	
	@JavascriptInterface
	public void pauseInterview(String answersJS){
		
		if(!answersJS.equals("{}")){
			
			//���浱ǰ�ʾ��
			Map<String,AnswerValue> answerValueMap = (Map<String,AnswerValue>)RenderUtils.fromJson(answersJS, new TypeToken<Map<String,AnswerValue>>(){}.getType());
			InterviewService.saveAnswers(answerValueMap);
			
			//��¼��ǰλ��
			InterviewService.recordInterviewProgress();
		}
		
		//��ת��ҳ
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
		
		//��ѯָ������
		QuestionWrap specQuestionWrap = InterviewService.getSpecQuestion(questionCode);
		
		//���浱ǰ��Ŀ��������
		InterviewContext.pushStack(specQuestionWrap.getQuestion());
		
		//ִ�н����߼�����
		if(StringUtils.isEmpty(StringUtils.trim(specQuestionWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(specQuestionWrap.getQuestion().getEntryLogic());
		}
		
		//��Ⱦ����
		RenderUtils.render(TemplateConstants.QUESTION, specQuestionWrap,new String[]{"extra"});
	}
	
	@JavascriptInterface
	public void debug(String msg){
		Log.d("JSObject", msg);
	}
}
