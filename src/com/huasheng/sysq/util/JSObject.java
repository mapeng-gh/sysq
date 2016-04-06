package com.huasheng.sysq.util;

import java.util.List;
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
			
			//��Ⱦҳ��
			firstQuestionaire.setIntroduction(RenderUtils.handlePara(firstQuestionaire.getIntroduction()));
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
		if(!StringUtils.isEmpty(StringUtils.trim(firstQuesWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(firstQuesWrap.getQuestion().getEntryLogic());
		}
		
		//��Ⱦҳ��
		firstQuesWrap.getQuestion().setDescription(RenderUtils.handlePara(firstQuesWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION, firstQuesWrap,new String[]{"extra"});
	}
	
	@JavascriptInterface
	public void jumpToNextQuestion(){
		
		//��ѯ����
		QuestionWrap nextQuestionWrap = InterviewService.getNextQuestion();
		
		//���浱ǰ��Ŀ��������
		InterviewContext.pushStack(nextQuestionWrap.getQuestion());
		
		//ִ�н����߼�����
		if(!StringUtils.isEmpty(StringUtils.trim(nextQuestionWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(nextQuestionWrap.getQuestion().getEntryLogic());
		}
		
		//ҳ����Ⱦ
		nextQuestionWrap.getQuestion().setDescription(RenderUtils.handlePara(nextQuestionWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION, nextQuestionWrap,new String[]{"extra"});
	}
	
	@JavascriptInterface
	public void jumpToAnswerList(String answersJA){
		
		//��ȡ�𰸲���
		List<AnswerValue> answerValueList = (List<AnswerValue>)RenderUtils.fromJson(answersJA, new TypeToken<List<AnswerValue>>(){}.getType());
		
		//��ȡ���б�
		ResultWrap resultWrap = InterviewService.getAnswerList(answerValueList);
		
		//��Ⱦҳ��
		List<Question> questionList = resultWrap.getQuestionList();
		for(Question question : questionList){
			question.setDescription(RenderUtils.handlePara(question.getDescription()));
		}
		RenderUtils.render(TemplateConstants.ANSWERS, resultWrap,null);
	}
	
	@JavascriptInterface
	public void jumpToPartialAnswerList(String answersJA){
		
		//��ȡ�𰸲���
		List<AnswerValue> answerValueList = (List<AnswerValue>)RenderUtils.fromJson(answersJA, new TypeToken<List<AnswerValue>>(){}.getType());
		
		//��ȡ���б�
		ResultWrap resultWrap = InterviewService.getAnswerList(answerValueList);
		
		//��Ⱦҳ��
		List<Question> questionList = resultWrap.getQuestionList();
		for(Question question : questionList){
			question.setDescription(RenderUtils.handlePara(question.getDescription()));
		}
		RenderUtils.render(TemplateConstants.ANSWERS_PARTIAL, resultWrap,null);
	}
	
	@JavascriptInterface
	public void saveQuestionaire(String answersJS){
		
		//���浱ǰ�ʾ��
		List<AnswerValue> answerValueMap = (List<AnswerValue>)RenderUtils.fromJson(answersJS, new TypeToken<List<AnswerValue>>(){}.getType());
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
			
			nextQuestionaire.setIntroduction(RenderUtils.handlePara(nextQuestionaire.getIntroduction()));
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
		if(!StringUtils.isEmpty(StringUtils.trim(prevQuestionWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(prevQuestionWrap.getQuestion().getEntryLogic());
		}
		
		//��Ⱦҳ��
		prevQuestionWrap.getQuestion().setDescription(RenderUtils.handlePara(prevQuestionWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION, prevQuestionWrap,new String[]{"extra"});
	}
	
	@JavascriptInterface
	public void pauseInterview(String answersJS){
		
		if(!answersJS.equals("{}")){
			
			//���浱ǰ�ʾ��
			List<AnswerValue> answerValueList = (List<AnswerValue>)RenderUtils.fromJson(answersJS, new TypeToken<List<AnswerValue>>(){}.getType());
			InterviewService.saveAnswers(answerValueList);
			
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
		
		QuestionWrap specQuestionWrap = null;
		
		//�����ת����
		Question specQuestion = InterviewContext.findQuestion(questionCode);
		if(specQuestion == null){//����
			specQuestionWrap = InterviewService.getSpecQuestion(questionCode);
			
			//���浱ǰ��Ŀ��������
			InterviewContext.pushStack(specQuestionWrap.getQuestion());
			
		}else{//��ǰ�����б���ת��
			specQuestionWrap = InterviewService.wrap(specQuestion);
			
			//����ջ��
			while(specQuestion != InterviewContext.getCurrentQuestion()){
				InterviewContext.popStack();
			}
		}
		
		//ִ�н����߼�����
		if(!StringUtils.isEmpty(StringUtils.trim(specQuestionWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(specQuestionWrap.getQuestion().getEntryLogic());
		}
		
		//��Ⱦ����
		specQuestionWrap.getQuestion().setDescription(RenderUtils.handlePara(specQuestionWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION, specQuestionWrap,new String[]{"extra"});
	}
	
	@JavascriptInterface
	public void resumeQuestionaire(){
		
		//��ȡ��ǰ����
		Question curQuestion = InterviewContext.getCurrentQuestion();
		QuestionWrap curQuestionWrap = InterviewService.wrap(curQuestion);
		
		//��Ⱦҳ��
		curQuestionWrap.getQuestion().setDescription(RenderUtils.handlePara(curQuestionWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION, curQuestionWrap,new String[]{"extra"});
	}
	
	@JavascriptInterface
	public void jumpToEnd(String endQuestionCode){
		
		//��ȡ��������
		QuestionWrap endQuestionWrap = InterviewService.getEndQuestion(endQuestionCode);
		
		//��Ⱦҳ��
		endQuestionWrap.getQuestion().setDescription(RenderUtils.handlePara(endQuestionWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION_END, endQuestionWrap,new String[]{"extra"});
	}
	
	@JavascriptInterface
	public void quitInterviewAndSave(String answers){
		//���浱ǰ�ʾ��
		List<AnswerValue> answerValueList = (List<AnswerValue>)RenderUtils.fromJson(answers, new TypeToken<List<AnswerValue>>(){}.getType());
		InterviewService.saveAnswers(answerValueList);
		
		//������̸
		this.quitInterview();
	}
	
	@JavascriptInterface
	public void debug(String msg){
		Log.d("JSObject", msg);
	}
}
