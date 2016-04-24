package com.huasheng.sysq.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import android.webkit.JavascriptInterface;

import com.google.gson.reflect.TypeToken;
import com.huasheng.sysq.activity.IndexActivity;
import com.huasheng.sysq.model.AnswerValue;
import com.huasheng.sysq.model.InterviewAnswer;
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
		
		//��Ⱦҳ��
		QuestionWrap formattedQuestionWrap = firstQuesWrap.format();
		RenderUtils.render(TemplateConstants.QUESTION, formattedQuestionWrap,new String[]{"extra","entryLogic"});
		
		//ִ�н����߼�����
		if(!StringUtils.isEmpty(StringUtils.trim(firstQuesWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(firstQuesWrap.getQuestion().getEntryLogic());
		}
		
		//����������̬��ֵ
		JSFuncInvokeUtils.invoke("insertQuestionFragment();");
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
		
		//ҳ����Ⱦ
		QuestionWrap formattedQuestionWrap = nextQuestionWrap.format();
		RenderUtils.render(TemplateConstants.QUESTION, formattedQuestionWrap,new String[]{"extra","entryLogic"});
		
		//ִ�н����߼�����
		if(!StringUtils.isEmpty(StringUtils.trim(nextQuestionWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(nextQuestionWrap.getQuestion().getEntryLogic());
		}
		
		//����������̬��ֵ
		JSFuncInvokeUtils.invoke("insertQuestionFragment();");
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
		QuestionWrap formattedQuestionWrap = prevQuestionWrap.format();
		RenderUtils.render(TemplateConstants.QUESTION, formattedQuestionWrap,new String[]{"extra","entryLogic"});
		
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
		
		//����������̬��ֵ
		JSFuncInvokeUtils.invoke("insertQuestionFragment();");
		
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
			QuestionWrap formattedQuestionWrap = specQuestionWrap.format();
			RenderUtils.render(TemplateConstants.QUESTION, formattedQuestionWrap,new String[]{"extra","entryLogic"});
			
			//ִ�н����߼�
			JSFuncInvokeUtils.invoke(specQuestionWrap.getQuestion().getEntryLogic());
			
			//����������̬��ֵ
			JSFuncInvokeUtils.invoke("insertQuestionFragment();");
			
		}else{//��ǰ�����б���ת��
			
			//���·���ջ
			while(specQuestion != InterviewContext.getTopQuestion()){
				InterviewContext.popQuestion();
			}
			
			//��Ⱦ����
			QuestionWrap specQuestionWrap = InterviewService.wrap(specQuestion);
			QuestionWrap formattedQuestionWrap = specQuestionWrap.format();
			RenderUtils.render(TemplateConstants.QUESTION, formattedQuestionWrap,new String[]{"extra","entryLogic"});
			
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
			
			//����������̬��ֵ
			JSFuncInvokeUtils.invoke("insertQuestionFragment();");
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
		QuestionWrap formattedQuestionWrap = endQuestionWrap.format();
		RenderUtils.render(TemplateConstants.QUESTION_END, formattedQuestionWrap,new String[]{"extra","entryLogic"});
		
		//����������̬��ֵ
		JSFuncInvokeUtils.invoke("insertQuestionFragment();");
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
		QuestionWrap formattedQuestionWrap = curQuestionWrap.format();
		RenderUtils.render(TemplateConstants.QUESTION, formattedQuestionWrap,new String[]{"extra","entryLogic"});
		
		//����������̬��ֵ
		JSFuncInvokeUtils.invoke("insertQuestionFragment();");
	}
	
	/**
	 * ��ת���б�
	 * @param answersJA
	 */
	@JavascriptInterface
	public void jumpToAnswerList(String answersJA,String type){
		
		InterviewQuestionaire interviewQuestionaire = InterviewContext.getCurInterviewQuestionaire();
		
		if(interviewQuestionaire.getQuestionaireCode().equals("LHC")){//���������ʾ���
			
			//��ȡ�𰸲���
			List<AnswerValue> answerValueList = (List<AnswerValue>)JsonUtils.fromJson(answersJA, new TypeToken<List<AnswerValue>>(){}.getType());
			
			//��װ��
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("questionaireTitle", InterviewService.getSpecQuestionaire(interviewQuestionaire.getQuestionaireCode()).getTitle());
			
			Map<String,AnswerValue> answerMap = new HashMap<String,AnswerValue>();
			for(AnswerValue answerValue : answerValueList){
				answerMap.put(answerValue.getCode(), answerValue);
			}
			result.put("answerList",answerMap);
			
			//��Ⱦҳ��
			if("all".equals(type)){
				RenderUtils.render(TemplateConstants.ANSWERS_LHC, result,null);
			}else if("part".equals(type)){
				RenderUtils.render(TemplateConstants.ANSWERS_LHC_PARTIAL, result,null);
			}
			
		}else{
			
			//��װ������
			List<AnswerValue> answerValueList = (List<AnswerValue>)JsonUtils.fromJson(answersJA, new TypeToken<List<AnswerValue>>(){}.getType());
			ResultWrap resultWrap = InterviewService.getAnswerList(answerValueList);
			
			//�����������⴦��
			List<Question> questionList = resultWrap.getQuestionList();
			for(Question question : questionList){
				
				String description = question.getDescription();
				description = FormatUtils.handleParaInHTML(description);//�ֶ�
				description = FormatUtils.escapeQuote4JS(description);//˫����ת��
				question.setDescription(description);
			}
			
			//��Ⱦҳ��
			if("all".equals(type)){
				RenderUtils.render(TemplateConstants.ANSWERS, resultWrap,new String[]{"entryLogic","exitLogic"});
			}else if("part".equals(type)){
				RenderUtils.render(TemplateConstants.ANSWERS_PARTIAL, resultWrap,new String[]{"entryLogic","exitLogic"});
			}
			
			//����������̬��ֵ
			JSFuncInvokeUtils.invoke("insertQuestionFragment();");
		}
		
	}
	
	/**
	 * ��ȡ���ʴ�
	 * @param answerCode
	 * @return
	 */
	@JavascriptInterface
	public String getInterviewAnswer(String answerCode){
		InterviewBasic curInterviewBasic = InterviewContext.getCurInterviewBasic();
		InterviewAnswer interviewAnswer = InterviewService.getInterviewAnswer(curInterviewBasic.getId(),answerCode);
		Map<String,String> map = new HashMap<String,String>();
		map.put("value", interviewAnswer.getAnswerValue());
		map.put("text", interviewAnswer.getAnswerText());
		String interviewAnswerJS = JsonUtils.toJson(map,null);
		return interviewAnswerJS;
	}
	
	/**
	 * �����
	 * @param answersJS
	 */
	@JavascriptInterface
	public void saveAnswers(String answersJS){
		
		//���浱ǰ�ʾ��
		List<AnswerValue> answerValueMap = (List<AnswerValue>)JsonUtils.fromJson(answersJS, new TypeToken<List<AnswerValue>>(){}.getType());
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
			nextQuestionaire.setIntroduction(FormatUtils.handleParaInHTML(nextQuestionaire.getIntroduction()));
			RenderUtils.render(TemplateConstants.QUESTIONAIRE, nextQuestionaire,null);
		}
	}
	
	/**
	 * ������̸
	 */
	@JavascriptInterface
	public void quitInterview(String answersJS){
		
		//�����ʾ��
		List<AnswerValue> answerValueMap = (List<AnswerValue>)JsonUtils.fromJson(answersJS, new TypeToken<List<AnswerValue>>(){}.getType());
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
		List<AnswerValue> answerValueMap = (List<AnswerValue>)JsonUtils.fromJson(answersJS, new TypeToken<List<AnswerValue>>(){}.getType());
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
