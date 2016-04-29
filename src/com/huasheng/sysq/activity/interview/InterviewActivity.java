package com.huasheng.sysq.activity.interview;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huasheng.sysq.R;
import com.huasheng.sysq.db.InterviewQuestionDB;
import com.huasheng.sysq.model.AnswerValue;
import com.huasheng.sysq.model.InterviewAnswer;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.InterviewQuestion;
import com.huasheng.sysq.model.InterviewQuestionaire;
import com.huasheng.sysq.model.Question;
import com.huasheng.sysq.model.QuestionWrap;
import com.huasheng.sysq.model.Questionaire;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.util.AudioUtils;
import com.huasheng.sysq.util.DateTimeUtils;
import com.huasheng.sysq.util.FormatUtils;
import com.huasheng.sysq.util.InterviewContext;
import com.huasheng.sysq.util.JSFuncInvokeUtils;
import com.huasheng.sysq.util.JSObject;
import com.huasheng.sysq.util.JsonUtils;
import com.huasheng.sysq.util.RenderUtils;
import com.huasheng.sysq.util.TemplateConstants;

public class InterviewActivity extends Activity{
	
	private WebView interviewWV;
	
	private boolean isRequestFromOutside = false;
	private String operateType;
	private int interviewBasicId;
	private String questionaireCode;
	private String questionCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_interview);
		
		//��ʼ���ⲿ����
		this.initParam();
		
		interviewWV = (WebView)findViewById(R.id.interview_web_view);
		
		interviewWV.getSettings().setJavaScriptEnabled(true);//����js
		
		interviewWV.setWebViewClient(new WebViewClient(){

			@Override
			public void onPageFinished(WebView view, String url) {//ҳ�������ɻص�
				
				if(InterviewActivity.this.isRequestFromOutside == false){
					
					//��ʼ¼��
					AudioUtils.start(InterviewContext.getCurInterviewBasic().getUsername());
					
					jumpToFirstQuestionaire();
					
				}else if("continue".equals(InterviewActivity.this.operateType)){
					
					//��ʼ¼��
					AudioUtils.start(InterviewContext.getCurInterviewBasic().getUsername());
					
					//������ʼ�¼��������
					InterviewBasic interviewBasic = InterviewService.findInterviewBasicById(InterviewActivity.this.interviewBasicId);
					InterviewContext.setCurInterviewBasic(interviewBasic);
					
					if(StringUtils.isEmpty(interviewBasic.getNextQuestionCode())){//��ǰλ�����ʾ����ҳ
						
						resumeQuestionaire(interviewBasic);
						
					}else{//��ǰλ����ĳ��������
						
						resumeQuestion(interviewBasic);
					}
				}else if("modify".equals(InterviewActivity.this.operateType)){
					
					//��ʼ¼��
					AudioUtils.start(InterviewContext.getCurInterviewBasic().getUsername());
					
					modifyQuestion();
				}
			}
		});
		
		InterviewContext.setWebView(interviewWV);//���浽������
		
		interviewWV.addJavascriptInterface(new JSObject(), "appservice");//ע��JSObject
		
		interviewWV.loadUrl("file:///android_asset/interview.html");
	}
	
	private void modifyQuestion(){
		
		//������ʼ�¼��������
		InterviewBasic interviewBasic = InterviewService.findInterviewBasicById(this.interviewBasicId);
		InterviewContext.setCurInterviewBasic(interviewBasic);
		
		//�޸ķ��ʼ�¼״̬
		interviewBasic.setStatus(InterviewBasic.STATUS_DOING);
		interviewBasic.setCurQuestionaireCode(this.questionaireCode);
		interviewBasic.setNextQuestionCode(this.questionCode);
		interviewBasic.setLastModifiedTime(DateTimeUtils.getCurTime());
		InterviewService.updateInterviewBasic(interviewBasic);
		
		//�����ʾ��¼��������
		InterviewQuestionaire interviewQuestionaire = InterviewService.findInterviewQuestionaire(interviewBasic.getId(),this.questionaireCode);
		InterviewContext.setCurInterviewQuestionaire(interviewQuestionaire);
		
		//�޸��ʾ��¼״̬
		interviewQuestionaire.setStatus(InterviewQuestionaire.STATUS_DOING);
		interviewQuestionaire.setLastModifiedTime(DateTimeUtils.getCurTime());
		InterviewService.updateInterviewQuestionaire(interviewQuestionaire);
		
		//�ָ����ⷵ��ջ
		List<Question> questionList = new ArrayList<Question>();
		List<InterviewQuestion> interviewQuestionList = InterviewService.getInterviewQuestionList(interviewBasic.getId(), questionaireCode);
		for(InterviewQuestion interviewQuestion : interviewQuestionList){
			questionList.add(InterviewService.getSpecQuestion(interviewQuestion.getQuestionCode()).getQuestion());
			if(interviewQuestion.getQuestionCode().equals(this.questionCode)){
				break;
			}
		}
		InterviewContext.setQuestionList(questionList);
		
		//ɾ��֮����ʾ��¼�������¼���𰸼�¼
		InterviewService.clearInterviewAfterQuestion(this.interviewBasicId, this.questionaireCode, this.questionCode);
		
		//�ָ���
		List<AnswerValue> answerValueList = new ArrayList<AnswerValue>();
		List<InterviewQuestion> existInterviewQuestionList = InterviewQuestionDB.selectByQuestionaire(interviewBasicId, questionaireCode);
		for(InterviewQuestion interviewQuestion : existInterviewQuestionList){
			List<InterviewAnswer> interviewAnswerList = InterviewService.getInterviewAnswerList(interviewBasic.getId(), interviewQuestion.getQuestionCode());
			for(InterviewAnswer interviewAnswer : interviewAnswerList){
				AnswerValue answerValue = new AnswerValue();
				answerValue.setLabel(interviewAnswer.getAnswerLabel());
				answerValue.setCode(interviewAnswer.getAnswerCode());
				answerValue.setValue(interviewAnswer.getAnswerValue());
				answerValue.setText(interviewAnswer.getAnswerText());
				answerValue.setSeqNum(interviewAnswer.getAnswerSeqNum());
				answerValue.setQuestionCode(interviewAnswer.getQuestionCode());
				answerValueList.add(answerValue);
			}
		}
		String jsStr = "answers=JSON.parse(\\'" + JsonUtils.toJson(answerValueList, null) + "\\')";
		JSFuncInvokeUtils.invoke(jsStr);
		
		//��Ⱦҳ��
		QuestionWrap specQuestionWrap = InterviewService.getSpecQuestion(questionCode);
		QuestionWrap formattedQuestionWrap = specQuestionWrap.format();
		RenderUtils.render(TemplateConstants.QUESTION, formattedQuestionWrap,new String[]{"extra","entryLogic"});
		
		//��ԭ�ֳ�
		JSFuncInvokeUtils.invoke("isReplay=true;");
		List<Question> backQuestionList = InterviewContext.getQuestionList();
		for(Question question : backQuestionList){
			JSFuncInvokeUtils.invoke(question.getEntryLogic());
			if(!question.getCode().equals(questionCode)){//��һ�����ⲻ��ִ���˳��߼�
				JSFuncInvokeUtils.invoke(question.getExitLogic());
			}
		}
		JSFuncInvokeUtils.invoke("isReplay=false;");
		
		//����������̬��ֵ
		JSFuncInvokeUtils.invoke("insertQuestionFragment();");
		
	}
	
	private void initParam(){
		
		Intent intent = getIntent();
		String operateType = intent.getStringExtra("operateType");
		
		if(!StringUtils.isEmpty(operateType)){
			
			this.isRequestFromOutside = true;
			this.operateType = operateType;
			
			if("continue".equals(operateType)){
				
				this.interviewBasicId = intent.getIntExtra("interviewBasicId", -1);
				
			}else if("modify".equals(operateType)){
				
				this.interviewBasicId = intent.getIntExtra("interviewBasicId", -1);
				this.questionaireCode = intent.getStringExtra("questionaireCode");
				this.questionCode = intent.getStringExtra("questionCode");
			}
			
		}
	}
	
	/**
	 * �ָ�����
	 * @param interviewBasic
	 */
	private void resumeQuestion(InterviewBasic interviewBasic){
		
		String questionaireCode = interviewBasic.getCurQuestionaireCode();
		String questionCode = interviewBasic.getNextQuestionCode();
		
		//�����ʾ��¼��������
		InterviewQuestionaire interviewQuestionaire = InterviewService.findInterviewQuestionaire(interviewBasic.getId(), questionaireCode);
		InterviewContext.setCurInterviewQuestionaire(interviewQuestionaire);
		
		//�ָ����ⷵ��ջ
		List<Question> questionList = new ArrayList<Question>();
		List<InterviewQuestion> interviewQuestionList = InterviewService.getInterviewQuestionList(interviewBasic.getId(), questionaireCode);
		for(InterviewQuestion interviewQuestion : interviewQuestionList){
			questionList.add(InterviewService.getSpecQuestion(interviewQuestion.getQuestionCode()).getQuestion());
		}
		questionList.add(InterviewService.getSpecQuestion(questionCode).getQuestion());
		InterviewContext.setQuestionList(questionList);
		
		//��Ⱦҳ��
		QuestionWrap specQuestionWrap = InterviewService.getSpecQuestion(questionCode);
		QuestionWrap formattedQuestionWrap = specQuestionWrap.format();
		RenderUtils.render(TemplateConstants.QUESTION, formattedQuestionWrap,new String[]{"extra","entryLogic"});
		
		//�ָ���
		List<AnswerValue> answerValueList = new ArrayList<AnswerValue>();
		for(InterviewQuestion interviewQuestion : interviewQuestionList){
			List<InterviewAnswer> interviewAnswerList = InterviewService.getInterviewAnswerList(interviewBasic.getId(), interviewQuestion.getQuestionCode());
			for(InterviewAnswer interviewAnswer : interviewAnswerList){
				AnswerValue answerValue = new AnswerValue();
				answerValue.setLabel(interviewAnswer.getAnswerLabel());
				answerValue.setCode(interviewAnswer.getAnswerCode());
				answerValue.setValue(interviewAnswer.getAnswerValue());
				answerValue.setText(interviewAnswer.getAnswerText());
				answerValue.setSeqNum(interviewAnswer.getAnswerSeqNum());
				answerValue.setQuestionCode(interviewAnswer.getQuestionCode());
				answerValueList.add(answerValue);
			}
		}
		String jsStr = "answers=JSON.parse(\\'" + JsonUtils.toJson(answerValueList, null) + "\\')";
		JSFuncInvokeUtils.invoke(jsStr);
		
		//��ԭ�ֳ�
		JSFuncInvokeUtils.invoke("isReplay=true;");
		List<Question> backQuestionList = InterviewContext.getQuestionList();
		for(Question question : backQuestionList){
			JSFuncInvokeUtils.invoke(question.getEntryLogic());
			if(!question.getCode().equals(questionCode)){//��һ�����ⲻ��ִ���˳��߼�
				JSFuncInvokeUtils.invoke(question.getExitLogic());
			}
		}
		JSFuncInvokeUtils.invoke("isReplay=false;");
		
		//����������̬��ֵ
		JSFuncInvokeUtils.invoke("insertQuestionFragment();");
	
	}
	
	/**
	 * �ָ��ʾ�
	 * @param interviewBasic
	 */
	private void resumeQuestionaire(InterviewBasic interviewBasic){
		
		//�����ʾ��¼��������
		InterviewQuestionaire interviewQuestionaire = InterviewService.findInterviewQuestionaire(interviewBasic.getId(), interviewBasic.getCurQuestionaireCode());
		InterviewContext.setCurInterviewQuestionaire(interviewQuestionaire);
		
		//��Ⱦҳ��
		Questionaire questionaire = InterviewService.getSpecQuestionaire(interviewBasic.getCurQuestionaireCode());
		questionaire.setIntroduction(FormatUtils.handleParaInHTML(questionaire.getIntroduction()));
		RenderUtils.render(TemplateConstants.QUESTIONAIRE, questionaire,null);
	}
	
	/**
	 * ��̸��ʼ��ת��һ���ʾ�
	 */
	private void jumpToFirstQuestionaire(){
		
		//��ѯ��һ���ʾ�
		Questionaire firstQuestionaire = InterviewService.getFirstQuestionaire();
		
		//�½��ʾ��¼������������
		InterviewQuestionaire interviewQuestionaire = InterviewService.newInterviewQuestionaire(firstQuestionaire);
		InterviewContext.setCurInterviewQuestionaire(interviewQuestionaire);
		
		//���·��ʼ�¼
		InterviewBasic curInterviewBasic = InterviewContext.getCurInterviewBasic();
		curInterviewBasic.setCurQuestionaireCode(firstQuestionaire.getCode());
		curInterviewBasic.setNextQuestionCode("");
		curInterviewBasic.setLastModifiedTime(DateTimeUtils.getCurTime());
		InterviewService.updateInterviewBasic(curInterviewBasic);
		
		//��Ⱦҳ��
		firstQuestionaire.setIntroduction(FormatUtils.handleParaInHTML(firstQuestionaire.getIntroduction()));
		RenderUtils.render(TemplateConstants.QUESTIONAIRE, firstQuestionaire,null);
	}
	
	@Override
	public void onBackPressed() {
	}

}
