package com.huasheng.sysq.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.util.Log;
import android.webkit.JavascriptInterface;

import com.google.gson.reflect.TypeToken;
import com.huasheng.sysq.activity.IndexActivity;
import com.huasheng.sysq.model.AnswerValue;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.Question;
import com.huasheng.sysq.model.QuestionWrap;
import com.huasheng.sysq.model.Questionaire;
import com.huasheng.sysq.model.ResultWrap;
import com.huasheng.sysq.service.InterviewService;

public class JSObject {
	
	/**
	 * ��ת��һ���ʾ�
	 */
	@JavascriptInterface
	public static void jumpToFirstQuestionaire(){
		
		//��ȡ��һ���ʾ�
		Questionaire firstQuestionaire = InterviewService.getFirstQuestionaire();
		
		//����������
		InterviewContext.setCurQuestionaire(firstQuestionaire);
		InterviewContext.setQuestionaireStartTime(DateTimeUtils.getCurTime());
		
		//��Ⱦҳ��
		firstQuestionaire.setIntroduction(RenderUtils.handlePara(firstQuestionaire.getIntroduction()));
		RenderUtils.render(TemplateConstants.QUESTIONAIRE, firstQuestionaire,null);
		
	}
	
	/**
	 * ��ת��һ���ʾ�
	 */
	public void jumpToNextQuestionaire(){
		
		//��ȡ��һ���ʾ�
		Questionaire nextQuestionaire = InterviewService.getNextQuestionaire();
		
		//���һ���ʾ�
		if(nextQuestionaire == null){
			
			//���·�̸״̬Ϊ���
			InterviewService.updateInterviewStatus(InterviewBasic.STATUS_DONE);
			
			//��ת��ҳ
			SysqApplication.jumpToActivity(IndexActivity.class);
			return;
		}
		
		//��¼��̸����λ�ã���һ���ʾ�
		InterviewService.updateInterviewPosition(nextQuestionaire.getCode(), null);
		InterviewService.updateInterviewStatus(InterviewBasic.STATUS_DOING);
		
		//���浽��̸������
		InterviewContext.clearQuestionaireContext();
		InterviewContext.setCurQuestionaire(nextQuestionaire);
		InterviewContext.setQuestionaireStartTime(DateTimeUtils.getCurTime());
		
		//��Ⱦҳ��
		nextQuestionaire.setIntroduction(RenderUtils.handlePara(nextQuestionaire.getIntroduction()));
		RenderUtils.render(TemplateConstants.QUESTIONAIRE, nextQuestionaire, null);
	}
	
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
		firstQuesWrap.getQuestion().setDescription(RenderUtils.handlePara(firstQuesWrap.getQuestion().getDescription()));
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
		nextQuestionWrap.getQuestion().setDescription(RenderUtils.handlePara(nextQuestionWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION, nextQuestionWrap,new String[]{"extra"});
	}
	
	/**
	 * ��ת��һ������
	 */
	@JavascriptInterface
	public void jumpToPreviousQuestion(){
		
		//�ӷ���ջ��ȡ��һ������
		Question prevQuestion = InterviewContext.getPrevQuestion();
		
		//�Ѿ�Ϊ��һ����Ŀ
		if(prevQuestion == null){
			SysqApplication.showMessage("�Ѿ��ǵ�һ��");
			return;
		}
		
		//��װ��һ������
		QuestionWrap prevQuestionWrap = InterviewService.wrap(prevQuestion);
		
		//�������ⷵ��ջ
		InterviewContext.popQuestion();
		
		//ִ�н����߼�����
		if(!StringUtils.isEmpty(StringUtils.trim(prevQuestionWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(prevQuestionWrap.getQuestion().getEntryLogic());
		}
		
		//��Ⱦҳ��
		prevQuestionWrap.getQuestion().setDescription(RenderUtils.handlePara(prevQuestionWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION, prevQuestionWrap,new String[]{"extra"});
	}
	
	/**
	 * ��ת��ָ������
	 * @param questionCode
	 */
	@JavascriptInterface
	public void jumpToSpecQuestion(String questionCode){
		
		//��ѯָ������
		QuestionWrap specQuestionWrap = null;
		specQuestionWrap = InterviewService.getSpecQuestion(questionCode);
		
		//�������ⷵ��ջ
		Question specQuestion = InterviewContext.findQuestion(questionCode);
		if(specQuestion == null){//����
			InterviewContext.pushQuestion(specQuestionWrap.getQuestion());
			
		}else{//��ǰ�����б���ת��
			while(specQuestion != InterviewContext.getCurQuestion()){
				InterviewContext.popQuestion();
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
	
	/**
	 * ������̸����ʱ���б�ҳ��
	 */
	@JavascriptInterface
	public void resumeQuestionaire(){
		
		//��ȡ��ǰ����
		Question curQuestion = InterviewContext.getCurQuestion();
		QuestionWrap curQuestionWrap = InterviewService.wrap(curQuestion);
		
		//��Ⱦҳ��
		curQuestionWrap.getQuestion().setDescription(RenderUtils.handlePara(curQuestionWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION, curQuestionWrap,new String[]{"extra"});
	}
	
	/**
	 * ��ת����������
	 * @param endQuestionCode
	 */
	@JavascriptInterface
	public void jumpToEndQuestion(String endQuestionCode){
		
		//��ȡ��������
		QuestionWrap endQuestionWrap = InterviewService.getSpecQuestion(endQuestionCode);
		
		//��Ⱦҳ��
		endQuestionWrap.getQuestion().setDescription(RenderUtils.handlePara(endQuestionWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION_END, endQuestionWrap,new String[]{"extra"});
	}
	
	/**
	 * ��ת���б�
	 * @param answersJA
	 */
	@JavascriptInterface
	public void jumpToAnswerList(String answersJA){
		
		//��ȡ�𰸲���
		List<AnswerValue> answerValueList = (List<AnswerValue>)RenderUtils.fromJson(answersJA, new TypeToken<List<AnswerValue>>(){}.getType());
		
		//��ȡ���б�
		ResultWrap resultWrap = InterviewService.getAnswerList(answerValueList);
		
		//������ֶܷδ���
		List<Question> questionList = resultWrap.getQuestionList();
		for(Question question : questionList){
			question.setDescription(RenderUtils.handlePara(question.getDescription()));
		}
		
		//��Ⱦҳ��
		RenderUtils.render(TemplateConstants.ANSWERS, resultWrap,null);
	}
	
	/**
	 * ��ת���ִ�ҳ��
	 * @param answersJA
	 */
	@JavascriptInterface
	public void jumpToPartialAnswerList(String answersJA){
		
		//��ȡ�𰸲���
		List<AnswerValue> answerValueList = (List<AnswerValue>)RenderUtils.fromJson(answersJA, new TypeToken<List<AnswerValue>>(){}.getType());
		
		//��ȡ���б�
		ResultWrap resultWrap = InterviewService.getAnswerList(answerValueList);
		
		//������ֶܷδ���
		List<Question> questionList = resultWrap.getQuestionList();
		for(Question question : questionList){
			question.setDescription(RenderUtils.handlePara(question.getDescription()));
		}
		
		//��Ⱦҳ��
		RenderUtils.render(TemplateConstants.ANSWERS_PARTIAL, resultWrap,null);
	}
	
	/**
	 * �����ʾ�
	 * @param answersJS
	 */
	@JavascriptInterface
	public void saveQuestionaire(String answersJS){
		
		//���浱ǰ�ʾ��
		List<AnswerValue> answerValueMap = (List<AnswerValue>)RenderUtils.fromJson(answersJS, new TypeToken<List<AnswerValue>>(){}.getType());
		InterviewService.saveInterviewQuestionaire(answerValueMap);
		
		//��ת��һ���ʾ�
		this.jumpToNextQuestionaire();
	}
	
	/**
	 * ������̸
	 */
	@JavascriptInterface
	public void quitInterview(String answersJS){
		
		//���浱ǰ�ʾ��
		List<AnswerValue> answerValueMap = (List<AnswerValue>)RenderUtils.fromJson(answersJS, new TypeToken<List<AnswerValue>>(){}.getType());
		InterviewService.saveInterviewQuestionaire(answerValueMap);
		
		//���·�̸״̬Ϊ����
		InterviewService.updateInterviewStatus(InterviewBasic.STATUS_BREAK);
		
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
		InterviewService.saveInterviewQuestionaire(answerValueMap);
		
		//��¼��ǰλ��
		InterviewService.updateInterviewPosition(null,InterviewContext.getCurQuestion().getCode());
		
		//��ת��ҳ
		SysqApplication.jumpToActivity(IndexActivity.class);
	}
	
	/**
	 * ����
	 */
	@JavascriptInterface
	public void redoQuestionaire(){
		
		//������ⷵ��ջ
		InterviewContext.clearQuestionStack();
		
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
	public static boolean isDebug = true;
	@JavascriptInterface
	public void debug(String tag,String msg){
		
		if(isDebug){
			Log.d(tag,msg);
		}
	}
}
