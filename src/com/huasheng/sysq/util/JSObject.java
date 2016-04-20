package com.huasheng.sysq.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.util.Log;
import android.webkit.JavascriptInterface;

import com.google.gson.reflect.TypeToken;
import com.huasheng.sysq.activity.IndexActivity;
import com.huasheng.sysq.model.AnswerValue;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.InterviewQuestionaire;
import com.huasheng.sysq.model.Question;
import com.huasheng.sysq.model.QuestionWrap;
import com.huasheng.sysq.model.Questionaire;
import com.huasheng.sysq.model.ResultWrap;
import com.huasheng.sysq.service.InterviewService;

public class JSObject {
	
	/**
	 * ��ת��һ������
	 */
	@JavascriptInterface
	public void jumpToFirstQuestion(){
		
		//��ȡ��һ������
		QuestionWrap firstQuesWrap = InterviewService.getFirstQuestion();
		
		//����������
		InterviewContext.pushQuestion(firstQuesWrap.getQuestion());
		
		//ִ�н����߼�����
		if(!StringUtils.isEmpty(StringUtils.trim(firstQuesWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(firstQuesWrap.getQuestion().getEntryLogic());
		}
		
		//��Ⱦҳ��
		firstQuesWrap.getQuestion().setDescription(BreakLineUtils.handleParaInHTML(firstQuesWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION, firstQuesWrap,new String[]{"extra"});
	}
	
	/**
	 * ��ת��һ������
	 */
	@JavascriptInterface
	public void jumpToNextQuestion(){
		
		//��ѯ����
		QuestionWrap nextQuestionWrap = InterviewService.getNextQuestion();
		
		//���浱ǰ��Ŀ��������
		InterviewContext.pushQuestion(nextQuestionWrap.getQuestion());
		
		//ִ�н����߼�����
		if(!StringUtils.isEmpty(StringUtils.trim(nextQuestionWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(nextQuestionWrap.getQuestion().getEntryLogic());
		}
		
		//ҳ����Ⱦ
		nextQuestionWrap.getQuestion().setDescription(BreakLineUtils.handleParaInHTML(nextQuestionWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION, nextQuestionWrap,new String[]{"extra"});
	}
	
	/**
	 * ��ת��һ������
	 */
	@JavascriptInterface
	public void jumpToPreviousQuestion(){
		
		//�ӷ���ջ��ȡ��һ������
		InterviewContext.popQuestion();
		Question prevQuestion = InterviewContext.getTopQuestion();
		
		//��װ��һ������
		QuestionWrap prevQuestionWrap = InterviewService.wrap(prevQuestion);
		
		//��Ⱦҳ��
		prevQuestionWrap.getQuestion().setDescription(BreakLineUtils.handleParaInHTML(prevQuestionWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION, prevQuestionWrap,new String[]{"extra"});
		
		//��ԭ�ֳ�
		JSFuncInvokeUtils.invoke("isReplay=true;");
		List<Question> questionList = InterviewContext.getQuestionList();
		for(Question question : questionList){
			JSFuncInvokeUtils.invoke(question.getEntryLogic());
			if(!question.getCode().equals(prevQuestion.getCode())){//��һ�����ⲻ��ִ���˳��߼�
				JSFuncInvokeUtils.invoke(question.getExitLogic());
			}
		}
		JSFuncInvokeUtils.invoke("isReplay=false;");
		
	}
	
	/**
	 * ��ת��ָ������
	 * @param questionCode
	 */
	@JavascriptInterface
	public void jumpToSpecQuestion(String questionCode){
		
		//�ӷ���ջ����ָ������
		Question specQuestion = InterviewContext.findQuestion(questionCode);
		
		if(specQuestion == null){//����
			
			//���
			QuestionWrap specQuestionWrap = InterviewService.getSpecQuestion(questionCode);
			
			//���ջ
			InterviewContext.pushQuestion(specQuestionWrap.getQuestion());
			
			//��Ⱦ����
			specQuestionWrap.getQuestion().setDescription(BreakLineUtils.handleParaInHTML(specQuestionWrap.getQuestion().getDescription()));
			RenderUtils.render(TemplateConstants.QUESTION, specQuestionWrap,new String[]{"extra"});
			
			//ִ�н����߼�
			JSFuncInvokeUtils.invoke(specQuestionWrap.getQuestion().getEntryLogic());
			
		}else{//��ǰ�����б���ת��
			
			//���·���ջ
			while(specQuestion != InterviewContext.getTopQuestion()){
				InterviewContext.popQuestion();
			}
			
			//��Ⱦ����
			QuestionWrap specQuestionWrap = InterviewService.wrap(specQuestion);
			specQuestionWrap.getQuestion().setDescription(BreakLineUtils.handleParaInHTML(specQuestionWrap.getQuestion().getDescription()));
			RenderUtils.render(TemplateConstants.QUESTION, specQuestionWrap,new String[]{"extra"});
			
			//��ԭ�ֳ�
			JSFuncInvokeUtils.invoke("isReplay=true;");
			List<Question> questionList = InterviewContext.getQuestionList();
			for(Question question : questionList){
				JSFuncInvokeUtils.invoke(question.getEntryLogic());
				if(!question.getCode().equals(specQuestion.getCode())){//��һ�����ⲻ��ִ���˳��߼�
					JSFuncInvokeUtils.invoke(question.getExitLogic());
				}
			}
			JSFuncInvokeUtils.invoke("isReplay=false;");
		}
		
	}
	
	/**
	 * ��ת����������
	 * @param endQuestionCode
	 */
	@JavascriptInterface
	public void jumpToEndQuestion(String endQuestionCode){
		
		//��ȡ��������
		QuestionWrap endQuestionWrap = InterviewService.getSpecEndQuestion(endQuestionCode);
		
		//��Ⱦҳ��
		endQuestionWrap.getQuestion().setDescription(BreakLineUtils.handleParaInHTML(endQuestionWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION_END, endQuestionWrap,new String[]{"extra"});
	}
	
	/**
	 * ���ص�ǰ���⣨����ʱ���б������
	 */
	@JavascriptInterface
	public void resumeQuestionaire(){
		
		//��ȡ��ǰ����
		Question curQuestion = InterviewContext.getTopQuestion();
		QuestionWrap curQuestionWrap = InterviewService.wrap(curQuestion);
		
		//��Ⱦҳ��
		curQuestionWrap.getQuestion().setDescription(BreakLineUtils.handleParaInHTML(curQuestionWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION, curQuestionWrap,new String[]{"extra"});
	}
	
	/**
	 * ��ת���б�
	 * @param answersJA
	 */
	@JavascriptInterface
	public void jumpToAnswerList(String answersJA,String type){
		
		//��ȡ�𰸲���
		List<AnswerValue> answerValueList = (List<AnswerValue>)RenderUtils.fromJson(answersJA, new TypeToken<List<AnswerValue>>(){}.getType());
		
		//��ȡ���б�
		ResultWrap resultWrap = InterviewService.getAnswerList(answerValueList);
		
		//������ֶܷδ���
		List<Question> questionList = resultWrap.getQuestionList();
		for(Question question : questionList){
			question.setDescription(BreakLineUtils.handleParaInHTML(question.getDescription()));
		}
		
		//��Ⱦҳ��
		if("all".equals(type)){
			RenderUtils.render(TemplateConstants.ANSWERS, resultWrap,null);
		}else if("part".equals(type)){
			RenderUtils.render(TemplateConstants.ANSWERS_PARTIAL, resultWrap,null);
		}
		
	}
	
	/**
	 * �����
	 * @param answersJS
	 */
	@JavascriptInterface
	public void saveAnswers(String answersJS){
		
		//���浱ǰ�ʾ��
		List<AnswerValue> answerValueMap = (List<AnswerValue>)RenderUtils.fromJson(answersJS, new TypeToken<List<AnswerValue>>(){}.getType());
		InterviewService.saveAnswers(answerValueMap);
		
		//���µ�ǰ�ʾ��¼
		InterviewQuestionaire interviewQuestionaire = InterviewContext.getCurInterviewQuestionaire();
		interviewQuestionaire.setStatus(InterviewQuestionaire.STATUS_DONE);
		interviewQuestionaire.setLastModifiedTime(DateTimeUtils.getCurTime());
		InterviewService.updateInterviewQuestionaire(interviewQuestionaire);
		
		//��ȡ��һ���ʾ�
		Questionaire nextQuestionaire = InterviewService.getNextQuestionaire();
		if(nextQuestionaire == null){
			
			//���·�̸��¼
			InterviewBasic curInterviewBasic = InterviewContext.getCurInterviewBasic();
			curInterviewBasic.setStatus(InterviewBasic.STATUS_DONE);
			curInterviewBasic.setLastModifiedTime(DateTimeUtils.getCurTime());
			InterviewService.updateInterviewBasic(curInterviewBasic);
			
			//��ת��ҳ
			SysqApplication.jumpToActivity(IndexActivity.class);
			
		}else{
			
			//�½���һ���ʾ��¼
			InterviewQuestionaire nextInterviewQuestionaire = InterviewService.newInterviewQuestionaire(nextQuestionaire);
			
			//����������
			InterviewContext.setCurInterviewQuestionaire(nextInterviewQuestionaire);
			
			//������ⷵ��ջ
			InterviewContext.clearQuestion();
			
			//���·�̸��¼
			InterviewBasic curInterviewBasic = InterviewContext.getCurInterviewBasic();
			curInterviewBasic.setCurQuestionaireCode(nextQuestionaire.getCode());
			curInterviewBasic.setNextQuestionCode("");
			curInterviewBasic.setLastModifiedTime(DateTimeUtils.getCurTime());
			InterviewService.updateInterviewBasic(curInterviewBasic);
			
			//��Ⱦҳ��
			nextQuestionaire.setIntroduction(BreakLineUtils.handleParaInHTML(nextQuestionaire.getIntroduction()));
			RenderUtils.render(TemplateConstants.QUESTIONAIRE, nextQuestionaire,null);
		}
	}
	
	/**
	 * ������̸
	 */
	@JavascriptInterface
	public void quitInterview(String answersJS){
		
		//�����ʾ��
		List<AnswerValue> answerValueMap = (List<AnswerValue>)RenderUtils.fromJson(answersJS, new TypeToken<List<AnswerValue>>(){}.getType());
		InterviewService.saveAnswers(answerValueMap);
		
		//���·��ʼ�¼
		InterviewBasic curInterviewBasic = InterviewContext.getCurInterviewBasic();
		curInterviewBasic.setStatus(InterviewBasic.STATUS_BREAK);
		curInterviewBasic.setLastModifiedTime(DateTimeUtils.getCurTime());
		InterviewService.updateInterviewBasic(curInterviewBasic);
		
		//�����ʾ��¼
		InterviewQuestionaire curInterviewQuestionaire = InterviewContext.getCurInterviewQuestionaire();
		curInterviewQuestionaire.setStatus(InterviewQuestionaire.STATUS_BREAK);
		curInterviewQuestionaire.setLastModifiedTime(DateTimeUtils.getCurTime());
		InterviewService.updateInterviewQuestionaire(curInterviewQuestionaire);
		
		//��ת��ҳ
		SysqApplication.jumpToActivity(IndexActivity.class);
	}
	
	/**
	 * ��ͣ��̸
	 * @param answersJS
	 */
	@JavascriptInterface
	public void pauseInterview(String answersJS){
		
		//���浱ǰ�ʾ��
		List<AnswerValue> answerValueMap = (List<AnswerValue>)RenderUtils.fromJson(answersJS, new TypeToken<List<AnswerValue>>(){}.getType());
		InterviewService.saveAnswers(answerValueMap);
		
		//���·��ʼ�¼
		InterviewBasic curInterviewBasic = InterviewContext.getCurInterviewBasic();
		curInterviewBasic.setNextQuestionCode(InterviewContext.getTopQuestion().getCode());
		InterviewService.updateInterviewBasic(curInterviewBasic);
		
		//��ת��ҳ
		SysqApplication.jumpToActivity(IndexActivity.class);
	}
	
	/**
	 * ����
	 */
	@JavascriptInterface
	public void redoQuestionaire(){
		
		//������ⷵ��ջ
		InterviewContext.clearQuestion();
		
		//��ת��һ������
		this.jumpToFirstQuestion();
	}
	
	/**
	 * ������ʾ��
	 * @param msg
	 */
	@JavascriptInterface
	public void showMsg(String msg){
		SysqApplication.showMessage(msg);
	}
	
	/**
	 * ҳ�����
	 * @param msg
	 */
	@JavascriptInterface
	public void debug(String tag,String msg){
		LogUtils.debug(tag, msg);
	}
}
