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
import com.huasheng.sysq.model.AnswerValue;
import com.huasheng.sysq.model.InterviewAnswer;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.InterviewQuestion;
import com.huasheng.sysq.model.InterviewQuestionaire;
import com.huasheng.sysq.model.Question;
import com.huasheng.sysq.model.QuestionWrap;
import com.huasheng.sysq.model.Questionaire;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.util.BreakLineUtils;
import com.huasheng.sysq.util.DateTimeUtils;
import com.huasheng.sysq.util.InterviewContext;
import com.huasheng.sysq.util.JSFuncInvokeUtils;
import com.huasheng.sysq.util.JSObject;
import com.huasheng.sysq.util.JsonUtils;
import com.huasheng.sysq.util.RenderUtils;
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
		
		//获取外部参数
		Intent intent = getIntent();
		interviewBasicId = intent.getIntExtra("interviewBasicId", -1);
		if(interviewBasicId != -1){
			isRequestFromOutside = true;
		}
		
		interviewWV = (WebView)findViewById(R.id.interview_web_view);
		
		interviewWV.getSettings().setJavaScriptEnabled(true);//启用js
		
		interviewWV.setWebViewClient(new WebViewClient(){

			@Override
			public void onPageFinished(WebView view, String url) {//页面加载完成回调
				
				if(InterviewActivity.this.isRequestFromOutside == false){
					
					jumpToFirstQuestionaire();
					
				}else{
					
					//保存访问记录到上下文
					InterviewBasic interviewBasic = InterviewService.findInterviewBasicById(InterviewActivity.this.interviewBasicId);
					InterviewContext.setCurInterviewBasic(interviewBasic);
					
					if(StringUtils.isEmpty(interviewBasic.getNextQuestionCode())){//当前位置在问卷介绍页
						
						resumeQuestionaire(interviewBasic);
						
					}else{//当前位置在某个问题上
						
						resumeQuestion(interviewBasic);
					}
				}
			}
		});
		
		InterviewContext.setWebView(interviewWV);//保存到上下文
		
		interviewWV.addJavascriptInterface(new JSObject(), "appservice");//注册JSObject
		
		interviewWV.loadUrl("file:///android_asset/interview.html");
	}
	
	/**
	 * 恢复问题
	 * @param interviewBasic
	 */
	private void resumeQuestion(InterviewBasic interviewBasic){
		
		String questionaireCode = interviewBasic.getCurQuestionaireCode();
		String questionCode = interviewBasic.getNextQuestionCode();
		
		//保存问卷记录到上下文
		InterviewQuestionaire interviewQuestionaire = InterviewService.findInterviewQuestionaire(interviewBasic.getId(), questionaireCode);
		InterviewContext.setCurInterviewQuestionaire(interviewQuestionaire);
		
		//恢复问题返回栈
		List<Question> questionList = new ArrayList<Question>();
		List<InterviewQuestion> interviewQuestionList = InterviewService.getInterviewQuestionList(interviewBasic.getId(), questionaireCode);
		for(InterviewQuestion interviewQuestion : interviewQuestionList){
			questionList.add(InterviewService.getSpecQuestion(interviewQuestion.getQuestionCode()).getQuestion());
		}
		questionList.add(InterviewService.getSpecQuestion(questionCode).getQuestion());
		InterviewContext.setQuestionList(questionList);
		
		//渲染页面
		QuestionWrap specQuestionWrap = InterviewService.getSpecQuestion(questionCode);
		specQuestionWrap.getQuestion().setDescription(BreakLineUtils.handleParaInHTML(specQuestionWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION, specQuestionWrap,new String[]{"extra"});
		
		//恢复答案
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
		
		//还原现场
		JSFuncInvokeUtils.invoke("isReplay=true;");
		List<Question> backQuestionList = InterviewContext.getQuestionList();
		for(Question question : backQuestionList){
			JSFuncInvokeUtils.invoke(question.getEntryLogic());
			if(!question.getCode().equals(questionCode)){//上一个问题不需执行退出逻辑
				JSFuncInvokeUtils.invoke(question.getExitLogic());
			}
		}
		JSFuncInvokeUtils.invoke("isReplay=false;");
	
	}
	
	/**
	 * 恢复问卷
	 * @param interviewBasic
	 */
	private void resumeQuestionaire(InterviewBasic interviewBasic){
		
		//保存问卷记录到上下文
		InterviewQuestionaire interviewQuestionaire = InterviewService.findInterviewQuestionaire(interviewBasic.getId(), interviewBasic.getCurQuestionaireCode());
		InterviewContext.setCurInterviewQuestionaire(interviewQuestionaire);
		
		//渲染页面
		Questionaire questionaire = InterviewService.getSpecQuestionaire(interviewBasic.getCurQuestionaireCode());
		questionaire.setIntroduction(BreakLineUtils.handleParaInHTML(questionaire.getIntroduction()));
		RenderUtils.render(TemplateConstants.QUESTIONAIRE, questionaire,null);
	}
	
	/**
	 * 访谈开始跳转第一个问卷
	 */
	private void jumpToFirstQuestionaire(){
		
		//查询第一个问卷
		Questionaire firstQuestionaire = InterviewService.getFirstQuestionaire();
		
		//新建问卷记录并保存上下文
		InterviewQuestionaire interviewQuestionaire = InterviewService.newInterviewQuestionaire(firstQuestionaire);
		InterviewContext.setCurInterviewQuestionaire(interviewQuestionaire);
		
		//更新访问记录
		InterviewBasic curInterviewBasic = InterviewContext.getCurInterviewBasic();
		curInterviewBasic.setCurQuestionaireCode(firstQuestionaire.getCode());
		curInterviewBasic.setNextQuestionCode("");
		curInterviewBasic.setLastModifiedTime(DateTimeUtils.getCurTime());
		InterviewService.updateInterviewBasic(curInterviewBasic);
		
		//渲染页面
		firstQuestionaire.setIntroduction(BreakLineUtils.handleParaInHTML(firstQuestionaire.getIntroduction()));
		RenderUtils.render(TemplateConstants.QUESTIONAIRE, firstQuestionaire,null);
	}

}
