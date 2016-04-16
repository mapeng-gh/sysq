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
	 * 跳转第一个问卷
	 */
	@JavascriptInterface
	public static void jumpToFirstQuestionaire(){
		
		//获取第一个问卷
		Questionaire firstQuestionaire = InterviewService.getFirstQuestionaire();
		
		//设置上下文
		InterviewContext.setCurQuestionaire(firstQuestionaire);
		InterviewContext.setQuestionaireStartTime(DateTimeUtils.getCurTime());
		
		//渲染页面
		firstQuestionaire.setIntroduction(RenderUtils.handlePara(firstQuestionaire.getIntroduction()));
		RenderUtils.render(TemplateConstants.QUESTIONAIRE, firstQuestionaire,null);
		
	}
	
	/**
	 * 跳转下一个问卷
	 */
	public void jumpToNextQuestionaire(){
		
		//获取下一个问卷
		Questionaire nextQuestionaire = InterviewService.getNextQuestionaire();
		
		//最后一个问卷
		if(nextQuestionaire == null){
			
			//更新访谈状态为完成
			InterviewService.updateInterviewStatus(InterviewBasic.STATUS_DONE);
			
			//跳转首页
			SysqApplication.jumpToActivity(IndexActivity.class);
			return;
		}
		
		//记录访谈所处位置（下一个问卷）
		InterviewService.updateInterviewPosition(nextQuestionaire.getCode(), null);
		InterviewService.updateInterviewStatus(InterviewBasic.STATUS_DOING);
		
		//保存到访谈上下文
		InterviewContext.clearQuestionaireContext();
		InterviewContext.setCurQuestionaire(nextQuestionaire);
		InterviewContext.setQuestionaireStartTime(DateTimeUtils.getCurTime());
		
		//渲染页面
		nextQuestionaire.setIntroduction(RenderUtils.handlePara(nextQuestionaire.getIntroduction()));
		RenderUtils.render(TemplateConstants.QUESTIONAIRE, nextQuestionaire, null);
	}
	
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
	 * 继续访谈（临时答案列表页）
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
	 * 跳转答案列表
	 * @param answersJA
	 */
	@JavascriptInterface
	public void jumpToAnswerList(String answersJA){
		
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
		RenderUtils.render(TemplateConstants.ANSWERS, resultWrap,null);
	}
	
	/**
	 * 跳转部分答案页面
	 * @param answersJA
	 */
	@JavascriptInterface
	public void jumpToPartialAnswerList(String answersJA){
		
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
		RenderUtils.render(TemplateConstants.ANSWERS_PARTIAL, resultWrap,null);
	}
	
	/**
	 * 保存问卷
	 * @param answersJS
	 */
	@JavascriptInterface
	public void saveQuestionaire(String answersJS){
		
		//保存当前问卷答案
		List<AnswerValue> answerValueMap = (List<AnswerValue>)RenderUtils.fromJson(answersJS, new TypeToken<List<AnswerValue>>(){}.getType());
		InterviewService.saveInterviewQuestionaire(answerValueMap);
		
		//跳转下一个问卷
		this.jumpToNextQuestionaire();
	}
	
	/**
	 * 结束访谈
	 */
	@JavascriptInterface
	public void quitInterview(String answersJS){
		
		//保存当前问卷答案
		List<AnswerValue> answerValueMap = (List<AnswerValue>)RenderUtils.fromJson(answersJS, new TypeToken<List<AnswerValue>>(){}.getType());
		InterviewService.saveInterviewQuestionaire(answerValueMap);
		
		//更新访谈状态为结束
		InterviewService.updateInterviewStatus(InterviewBasic.STATUS_BREAK);
		
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
		InterviewService.saveInterviewQuestionaire(answerValueMap);
		
		//记录当前位置
		InterviewService.updateInterviewPosition(null,InterviewContext.getCurQuestion().getCode());
		
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
