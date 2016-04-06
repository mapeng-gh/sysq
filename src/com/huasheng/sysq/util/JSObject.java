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
		
		//获取第一个问卷
		Questionaire firstQuestionaire = InterviewService.getFirstQuestionaire();
		
		//设置上下文
		InterviewContext.setCurrentQuestionaire(firstQuestionaire);
		
		if(StringUtils.isEmpty(StringUtils.trim(firstQuestionaire.getIntroduction()))){//问卷没有介绍
			
			JSObject.jumpToFirstQuestion();//跳转第一个问题
			
		}else{
			
			//渲染页面
			firstQuestionaire.setIntroduction(RenderUtils.handlePara(firstQuestionaire.getIntroduction()));
			RenderUtils.render(TemplateConstants.QUESTIONAIRE, firstQuestionaire,null);
		}
	}

	@JavascriptInterface
	public static void jumpToFirstQuestion(){
		
		//获取第一个问题
		QuestionWrap firstQuesWrap = InterviewService.getFirstQuestion();
		
		//设置上下文
		InterviewContext.pushStack(firstQuesWrap.getQuestion());
		
		//执行进入逻辑代码
		if(!StringUtils.isEmpty(StringUtils.trim(firstQuesWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(firstQuesWrap.getQuestion().getEntryLogic());
		}
		
		//渲染页面
		firstQuesWrap.getQuestion().setDescription(RenderUtils.handlePara(firstQuesWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION, firstQuesWrap,new String[]{"extra"});
	}
	
	@JavascriptInterface
	public void jumpToNextQuestion(){
		
		//查询数据
		QuestionWrap nextQuestionWrap = InterviewService.getNextQuestion();
		
		//保存当前题目到上下文
		InterviewContext.pushStack(nextQuestionWrap.getQuestion());
		
		//执行进入逻辑代码
		if(!StringUtils.isEmpty(StringUtils.trim(nextQuestionWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(nextQuestionWrap.getQuestion().getEntryLogic());
		}
		
		//页面渲染
		nextQuestionWrap.getQuestion().setDescription(RenderUtils.handlePara(nextQuestionWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION, nextQuestionWrap,new String[]{"extra"});
	}
	
	@JavascriptInterface
	public void jumpToAnswerList(String answersJA){
		
		//获取答案参数
		List<AnswerValue> answerValueList = (List<AnswerValue>)RenderUtils.fromJson(answersJA, new TypeToken<List<AnswerValue>>(){}.getType());
		
		//获取答案列表
		ResultWrap resultWrap = InterviewService.getAnswerList(answerValueList);
		
		//渲染页面
		List<Question> questionList = resultWrap.getQuestionList();
		for(Question question : questionList){
			question.setDescription(RenderUtils.handlePara(question.getDescription()));
		}
		RenderUtils.render(TemplateConstants.ANSWERS, resultWrap,null);
	}
	
	@JavascriptInterface
	public void jumpToPartialAnswerList(String answersJA){
		
		//获取答案参数
		List<AnswerValue> answerValueList = (List<AnswerValue>)RenderUtils.fromJson(answersJA, new TypeToken<List<AnswerValue>>(){}.getType());
		
		//获取答案列表
		ResultWrap resultWrap = InterviewService.getAnswerList(answerValueList);
		
		//渲染页面
		List<Question> questionList = resultWrap.getQuestionList();
		for(Question question : questionList){
			question.setDescription(RenderUtils.handlePara(question.getDescription()));
		}
		RenderUtils.render(TemplateConstants.ANSWERS_PARTIAL, resultWrap,null);
	}
	
	@JavascriptInterface
	public void saveQuestionaire(String answersJS){
		
		//保存当前问卷答案
		List<AnswerValue> answerValueMap = (List<AnswerValue>)RenderUtils.fromJson(answersJS, new TypeToken<List<AnswerValue>>(){}.getType());
		InterviewService.saveAnswers(answerValueMap);
		
		//获取下一个问卷
		Questionaire nextQuestionaire = InterviewService.getNextQuestionaire();
		
		//最后一个问卷
		if(nextQuestionaire == null){
			
			//更新访谈状态为完成
			InterviewService.finishInterview();
			
			//跳转首页
			Intent indexActivityIntent = new Intent(MyApplication.getContext(),IndexActivity.class);
			indexActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			MyApplication.getContext().startActivity(indexActivityIntent);
			
			return;
		}
		
		//保存到访谈上下文
		InterviewContext.setCurrentQuestionaire(nextQuestionaire);
		InterviewContext.clearStack();
		
		//页面渲染
		if(StringUtils.isEmpty(StringUtils.trim(nextQuestionaire.getIntroduction()))){//问卷没有介绍
			
			//跳转第一个问题
			JSObject.jumpToFirstQuestion();
		
		}else{
			
			nextQuestionaire.setIntroduction(RenderUtils.handlePara(nextQuestionaire.getIntroduction()));
			RenderUtils.render(TemplateConstants.QUESTIONAIRE, nextQuestionaire, null);
		}
		
	}
	
	@JavascriptInterface
	public void quitInterview(){
		
		//更新访谈状态为结束
		InterviewService.quitInterview();
		
		//跳转主页
		Intent indexActivityIntent = new Intent(MyApplication.getContext(),IndexActivity.class);
		indexActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		MyApplication.getContext().startActivity(indexActivityIntent);
		return;
	}
	
	@JavascriptInterface
	public void jumpToPreviousQuestion(){
		
		//从返回栈获取上一个问题
		Question prevQuestion = InterviewContext.getPrevQuestion();
		
		//已经为第一个题目
		if(prevQuestion == null){
			Toast.makeText(MyApplication.getContext(), "已经是第一题", Toast.LENGTH_SHORT).show();
			return;
		}
		
		//查询上一个问题
		QuestionWrap prevQuestionWrap = InterviewService.wrap(prevQuestion);
		
		//更新问题返回栈
		InterviewContext.popStack();
		
		//执行进入逻辑代码
		if(!StringUtils.isEmpty(StringUtils.trim(prevQuestionWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(prevQuestionWrap.getQuestion().getEntryLogic());
		}
		
		//渲染页面
		prevQuestionWrap.getQuestion().setDescription(RenderUtils.handlePara(prevQuestionWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION, prevQuestionWrap,new String[]{"extra"});
	}
	
	@JavascriptInterface
	public void pauseInterview(String answersJS){
		
		if(!answersJS.equals("{}")){
			
			//保存当前问卷答案
			List<AnswerValue> answerValueList = (List<AnswerValue>)RenderUtils.fromJson(answersJS, new TypeToken<List<AnswerValue>>(){}.getType());
			InterviewService.saveAnswers(answerValueList);
			
			//记录当前位置
			InterviewService.recordInterviewProgress();
		}
		
		//跳转主页
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
		
		//检测跳转方向
		Question specQuestion = InterviewContext.findQuestion(questionCode);
		if(specQuestion == null){//往后
			specQuestionWrap = InterviewService.getSpecQuestion(questionCode);
			
			//保存当前题目到上下文
			InterviewContext.pushStack(specQuestionWrap.getQuestion());
			
		}else{//往前（答案列表跳转）
			specQuestionWrap = InterviewService.wrap(specQuestion);
			
			//更新栈顶
			while(specQuestion != InterviewContext.getCurrentQuestion()){
				InterviewContext.popStack();
			}
		}
		
		//执行进入逻辑代码
		if(!StringUtils.isEmpty(StringUtils.trim(specQuestionWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(specQuestionWrap.getQuestion().getEntryLogic());
		}
		
		//渲染数据
		specQuestionWrap.getQuestion().setDescription(RenderUtils.handlePara(specQuestionWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION, specQuestionWrap,new String[]{"extra"});
	}
	
	@JavascriptInterface
	public void resumeQuestionaire(){
		
		//获取当前问题
		Question curQuestion = InterviewContext.getCurrentQuestion();
		QuestionWrap curQuestionWrap = InterviewService.wrap(curQuestion);
		
		//渲染页面
		curQuestionWrap.getQuestion().setDescription(RenderUtils.handlePara(curQuestionWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION, curQuestionWrap,new String[]{"extra"});
	}
	
	@JavascriptInterface
	public void jumpToEnd(String endQuestionCode){
		
		//获取结束问题
		QuestionWrap endQuestionWrap = InterviewService.getEndQuestion(endQuestionCode);
		
		//渲染页面
		endQuestionWrap.getQuestion().setDescription(RenderUtils.handlePara(endQuestionWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION_END, endQuestionWrap,new String[]{"extra"});
	}
	
	@JavascriptInterface
	public void quitInterviewAndSave(String answers){
		//保存当前问卷答案
		List<AnswerValue> answerValueList = (List<AnswerValue>)RenderUtils.fromJson(answers, new TypeToken<List<AnswerValue>>(){}.getType());
		InterviewService.saveAnswers(answerValueList);
		
		//结束访谈
		this.quitInterview();
	}
	
	@JavascriptInterface
	public void debug(String msg){
		Log.d("JSObject", msg);
	}
}
