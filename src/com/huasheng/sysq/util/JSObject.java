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
	 * 跳转第一个问题
	 */
	@JavascriptInterface
	public void jumpToFirstQuestion(){
		
		//获取第一个问题
		QuestionWrap firstQuesWrap = InterviewService.getFirstQuestion();
		
		//设置上下文
		InterviewContext.pushQuestion(firstQuesWrap.getQuestion());
		
		//执行进入逻辑代码
		if(!StringUtils.isEmpty(StringUtils.trim(firstQuesWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(firstQuesWrap.getQuestion().getEntryLogic());
		}
		
		//渲染页面
		firstQuesWrap.getQuestion().setDescription(RenderUtils.handlePara(firstQuesWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION, firstQuesWrap,new String[]{"extra"});
	}
	
	/**
	 * 跳转下一个问题
	 */
	@JavascriptInterface
	public void jumpToNextQuestion(){
		
		//查询数据
		QuestionWrap nextQuestionWrap = InterviewService.getNextQuestion();
		
		//保存当前题目到上下文
		InterviewContext.pushQuestion(nextQuestionWrap.getQuestion());
		
		//执行进入逻辑代码
		if(!StringUtils.isEmpty(StringUtils.trim(nextQuestionWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(nextQuestionWrap.getQuestion().getEntryLogic());
		}
		
		//页面渲染
		nextQuestionWrap.getQuestion().setDescription(RenderUtils.handlePara(nextQuestionWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION, nextQuestionWrap,new String[]{"extra"});
	}
	
	/**
	 * 跳转上一个问题
	 */
	@JavascriptInterface
	public void jumpToPreviousQuestion(){
		
		//从返回栈获取上一个问题
		Question prevQuestion = InterviewContext.getPrevQuestion();
		
		//已经为第一个题目
		if(prevQuestion == null){
			SysqApplication.showMessage("已经是第一题");
			return;
		}
		
		//包装上一个问题
		QuestionWrap prevQuestionWrap = InterviewService.wrap(prevQuestion);
		
		//更新问题返回栈
		InterviewContext.popQuestion();
		
		//执行进入逻辑代码
		if(!StringUtils.isEmpty(StringUtils.trim(prevQuestionWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(prevQuestionWrap.getQuestion().getEntryLogic());
		}
		
		//渲染页面
		prevQuestionWrap.getQuestion().setDescription(RenderUtils.handlePara(prevQuestionWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION, prevQuestionWrap,new String[]{"extra"});
	}
	
	/**
	 * 跳转到指定问题
	 * @param questionCode
	 */
	@JavascriptInterface
	public void jumpToSpecQuestion(String questionCode){
		
		//查询指定问题
		QuestionWrap specQuestionWrap = null;
		specQuestionWrap = InterviewService.getSpecQuestion(questionCode);
		
		//更新问题返回栈
		Question specQuestion = InterviewContext.findQuestion(questionCode);
		if(specQuestion == null){//往后
			InterviewContext.pushQuestion(specQuestionWrap.getQuestion());
			
		}else{//往前（答案列表跳转）
			while(specQuestion != InterviewContext.getCurQuestion()){
				InterviewContext.popQuestion();
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
	
	/**
	 * 跳转到结束问题
	 * @param endQuestionCode
	 */
	@JavascriptInterface
	public void jumpToEndQuestion(String endQuestionCode){
		
		//获取结束问题
		QuestionWrap endQuestionWrap = InterviewService.getSpecQuestion(endQuestionCode);
		
		//渲染页面
		endQuestionWrap.getQuestion().setDescription(RenderUtils.handlePara(endQuestionWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION_END, endQuestionWrap,new String[]{"extra"});
	}
	
	/**
	 * 返回当前问题（从临时答案列表继续）
	 */
	@JavascriptInterface
	public void resumeQuestionaire(){
		
		//获取当前问题
		Question curQuestion = InterviewContext.getCurQuestion();
		QuestionWrap curQuestionWrap = InterviewService.wrap(curQuestion);
		
		//渲染页面
		curQuestionWrap.getQuestion().setDescription(RenderUtils.handlePara(curQuestionWrap.getQuestion().getDescription()));
		RenderUtils.render(TemplateConstants.QUESTION, curQuestionWrap,new String[]{"extra"});
	}
	
	/**
	 * 跳转答案列表
	 * @param answersJA
	 */
	@JavascriptInterface
	public void jumpToAnswerList(String answersJA,String type){
		
		//获取答案参数
		List<AnswerValue> answerValueList = (List<AnswerValue>)RenderUtils.fromJson(answersJA, new TypeToken<List<AnswerValue>>(){}.getType());
		
		//获取答案列表
		ResultWrap resultWrap = InterviewService.getAnswerList(answerValueList);
		
		//问题介绍分段处理
		List<Question> questionList = resultWrap.getQuestionList();
		for(Question question : questionList){
			question.setDescription(RenderUtils.handlePara(question.getDescription()));
		}
		
		//渲染页面
		if("all".equals(type)){
			RenderUtils.render(TemplateConstants.ANSWERS, resultWrap,null);
		}else if("part".equals(type)){
			RenderUtils.render(TemplateConstants.ANSWERS_PARTIAL, resultWrap,null);
		}
		
	}
	
	/**
	 * 保存答案
	 * @param answersJS
	 */
	@JavascriptInterface
	public void saveAnswers(String answersJS){
		
		//保存当前问卷答案
		List<AnswerValue> answerValueMap = (List<AnswerValue>)RenderUtils.fromJson(answersJS, new TypeToken<List<AnswerValue>>(){}.getType());
		InterviewService.saveAnswers(answerValueMap);
		
		//更新当前问卷记录
		InterviewQuestionaire interviewQuestionaire = InterviewContext.getCurInterviewQuestionaire();
		interviewQuestionaire.setStatus(InterviewQuestionaire.STATUS_DONE);
		interviewQuestionaire.setLastModifiedTime(DateTimeUtils.getCurTime());
		InterviewService.updateInterviewQuestionaire(interviewQuestionaire);
		
		//获取下一个问卷
		Questionaire nextQuestionaire = InterviewService.getNextQuestionaire();
		if(nextQuestionaire == null){
			
			//更新访谈记录
			InterviewBasic curInterviewBasic = InterviewContext.getCurInterviewBasic();
			curInterviewBasic.setStatus(InterviewBasic.STATUS_DONE);
			curInterviewBasic.setLastModifiedTime(DateTimeUtils.getCurTime());
			InterviewService.updateInterviewBasic(curInterviewBasic);
			
			//跳转首页
			SysqApplication.jumpToActivity(IndexActivity.class);
			
		}else{
			
			//新建下一个问卷记录
			InterviewQuestionaire nextInterviewQuestionaire = InterviewService.newInterviewQuestionaire(nextQuestionaire);
			
			//设置上下文
			InterviewContext.setCurInterviewQuestionaire(nextInterviewQuestionaire);
			
			//清空问题返回栈
			InterviewContext.clearQuestionStack();
			
			//更新访谈记录
			InterviewBasic curInterviewBasic = InterviewContext.getCurInterviewBasic();
			curInterviewBasic.setCurQuestionaireCode(nextQuestionaire.getCode());
			curInterviewBasic.setLastModifiedTime(DateTimeUtils.getCurTime());
			InterviewService.updateInterviewBasic(curInterviewBasic);
			
			//渲染页面
			nextQuestionaire.setIntroduction(RenderUtils.handlePara(nextQuestionaire.getIntroduction()));
			RenderUtils.render(TemplateConstants.QUESTIONAIRE, nextQuestionaire,null);
		}
	}
	
	/**
	 * 结束访谈
	 */
	@JavascriptInterface
	public void quitInterview(String answersJS){
		
		//保存问卷答案
		List<AnswerValue> answerValueMap = (List<AnswerValue>)RenderUtils.fromJson(answersJS, new TypeToken<List<AnswerValue>>(){}.getType());
		InterviewService.saveAnswers(answerValueMap);
		
		//更新访问记录
		InterviewBasic curInterviewBasic = InterviewContext.getCurInterviewBasic();
		curInterviewBasic.setStatus(InterviewBasic.STATUS_BREAK);
		curInterviewBasic.setLastModifiedTime(DateTimeUtils.getCurTime());
		InterviewService.updateInterviewBasic(curInterviewBasic);
		
		//更新问卷记录
		InterviewQuestionaire curInterviewQuestionaire = InterviewContext.getCurInterviewQuestionaire();
		curInterviewQuestionaire.setStatus(InterviewQuestionaire.STATUS_BREAK);
		curInterviewQuestionaire.setLastModifiedTime(DateTimeUtils.getCurTime());
		InterviewService.updateInterviewQuestionaire(curInterviewQuestionaire);
		
		//跳转主页
		SysqApplication.jumpToActivity(IndexActivity.class);
	}
	
	/**
	 * 暂停访谈
	 * @param answersJS
	 */
	@JavascriptInterface
	public void pauseInterview(String answersJS){
		
		//保存当前问卷答案
		List<AnswerValue> answerValueMap = (List<AnswerValue>)RenderUtils.fromJson(answersJS, new TypeToken<List<AnswerValue>>(){}.getType());
		InterviewService.saveAnswers(answerValueMap);
		
		//更新访问记录
		InterviewBasic curInterviewBasic = InterviewContext.getCurInterviewBasic();
		curInterviewBasic.setNextQuestionCode(InterviewContext.getCurQuestion().getCode());
		InterviewService.updateInterviewBasic(curInterviewBasic);
		
		//跳转主页
		SysqApplication.jumpToActivity(IndexActivity.class);
	}
	
	/**
	 * 重做
	 */
	@JavascriptInterface
	public void redoQuestionaire(){
		
		//清空问题返回栈
		InterviewContext.clearQuestionStack();
		
		//跳转第一个问题
		this.jumpToFirstQuestion();
	}
	
	/**
	 * 弹出提示框
	 * @param msg
	 */
	@JavascriptInterface
	public void showMsg(String msg){
		SysqApplication.showMessage(msg);
	}
	
	/**
	 * 页面调试
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
