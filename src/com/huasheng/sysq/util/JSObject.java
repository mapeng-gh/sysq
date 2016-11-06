package com.huasheng.sysq.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.Gravity;
import android.webkit.JavascriptInterface;

import com.google.gson.reflect.TypeToken;
import com.huasheng.sysq.activity.IndexActivity;
import com.huasheng.sysq.activity.interview.InterviewActivity;
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
	 * 跳转第一个问题
	 */
	@JavascriptInterface
	public void jumpToFirstQuestion(){
		
		//获取第一个问题
		QuestionWrap firstQuesWrap = InterviewService.getFirstQuestion();
		
		//设置上下文
		InterviewContext.pushQuestion(firstQuesWrap.getQuestion());
		
		//渲染页面
		QuestionWrap formattedQuestionWrap = firstQuesWrap.format();
		RenderUtils.render(TemplateConstants.QUESTION, formattedQuestionWrap,new String[]{"extra","entryLogic"});
		
		//执行进入逻辑代码
		if(!StringUtils.isEmpty(StringUtils.trim(firstQuesWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(firstQuesWrap.getQuestion().getEntryLogic());
		}
		
		//问题描述动态插值
		JSFuncInvokeUtils.invoke("insertQuestionFragment();");
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
		
		//页面渲染
		QuestionWrap formattedQuestionWrap = nextQuestionWrap.format();
		RenderUtils.render(TemplateConstants.QUESTION, formattedQuestionWrap,new String[]{"extra","entryLogic"});
		
		//执行进入逻辑代码
		if(!StringUtils.isEmpty(StringUtils.trim(nextQuestionWrap.getQuestion().getEntryLogic()))){
			JSFuncInvokeUtils.invoke(nextQuestionWrap.getQuestion().getEntryLogic());
		}
		
		//问题描述动态插值
		JSFuncInvokeUtils.invoke("insertQuestionFragment();");
	}
	
	/**
	 * 跳转上一个问题
	 */
	@JavascriptInterface
	public void jumpToPreviousQuestion(String answersJS){
		
		//从返回栈获取上一个问题
		InterviewContext.popQuestion();
		Question prevQuestion = InterviewContext.getTopQuestion();
		
		//包装上一个问题
		QuestionWrap prevQuestionWrap = InterviewService.wrap(prevQuestion);
		
		//渲染页面
		QuestionWrap formattedQuestionWrap = prevQuestionWrap.format();
		RenderUtils.render(TemplateConstants.QUESTION, formattedQuestionWrap,new String[]{"extra","entryLogic"});
		
		//还原现场
		JSFuncInvokeUtils.invoke("isReplay=true;");
		List<Question> questionList = InterviewContext.getQuestionList();
		for(Question question : questionList){
			if(question.getCode().equals(prevQuestion.getCode())){
				JSFuncInvokeUtils.invoke("isLastQuestion=true;");
			}
			JSFuncInvokeUtils.invoke(question.getEntryLogic());
			if(!question.getCode().equals(prevQuestion.getCode())){//上一个问题不需执行退出逻辑
				JSFuncInvokeUtils.invoke(question.getExitLogic());
			}
		}
		JSFuncInvokeUtils.invoke("isLastQuestion=false;");
		JSFuncInvokeUtils.invoke("isReplay=false;");
		
		//问题描述动态插值
		JSFuncInvokeUtils.invoke("insertQuestionFragment();");
		
		//恢复本题答案
		JSFuncInvokeUtils.invokeFunction("resumeAnswers('"+answersJS+"')");
		
	}
	
	/**
	 * 跳转到指定问题
	 * @param questionCode
	 */
	@JavascriptInterface
	public void jumpToSpecQuestion(String questionCode){
		
		//从返回栈查找指定问题
		Question specQuestion = InterviewContext.findQuestion(questionCode);
		
		if(specQuestion == null){//往后
			
			//查库
			QuestionWrap specQuestionWrap = InterviewService.getSpecQuestion(questionCode);
			
			//添加栈
			InterviewContext.pushQuestion(specQuestionWrap.getQuestion());
			
			//渲染数据
			QuestionWrap formattedQuestionWrap = specQuestionWrap.format();
			RenderUtils.render(TemplateConstants.QUESTION, formattedQuestionWrap,new String[]{"extra","entryLogic"});
			
			//执行进入逻辑
			JSFuncInvokeUtils.invoke(specQuestionWrap.getQuestion().getEntryLogic());
			
			//问题描述动态插值
			JSFuncInvokeUtils.invoke("insertQuestionFragment();");
			
		}else{//往前（答案列表跳转）
			
			//更新返回栈
			while(specQuestion != InterviewContext.getTopQuestion()){
				InterviewContext.popQuestion();
			}
			
			//渲染数据
			QuestionWrap specQuestionWrap = InterviewService.wrap(specQuestion);
			QuestionWrap formattedQuestionWrap = specQuestionWrap.format();
			RenderUtils.render(TemplateConstants.QUESTION, formattedQuestionWrap,new String[]{"extra","entryLogic"});
			
			//还原现场
			JSFuncInvokeUtils.invoke("isReplay=true;");
			List<Question> questionList = InterviewContext.getQuestionList();
			for(Question question : questionList){
				if(question.getCode().equals(specQuestion.getCode())){
					JSFuncInvokeUtils.invoke("isLastQuestion=true;");
				}
				JSFuncInvokeUtils.invoke(question.getEntryLogic());
				if(!question.getCode().equals(specQuestion.getCode())){//上一个问题不需执行退出逻辑
					JSFuncInvokeUtils.invoke(question.getExitLogic());
				}
			}
			JSFuncInvokeUtils.invoke("isLastQuestion=false;");
			JSFuncInvokeUtils.invoke("isReplay=false;");
			
			//问题描述动态插值
			JSFuncInvokeUtils.invoke("insertQuestionFragment();");
		}
		
	}
	
	/**
	 * 跳转到结束问题
	 * @param endQuestionCode
	 */
	@JavascriptInterface
	public void jumpToEndQuestion(String endQuestionCode){
		
		//获取结束问题
		QuestionWrap endQuestionWrap = InterviewService.getSpecEndQuestion(endQuestionCode);
		
		//渲染页面
		QuestionWrap formattedQuestionWrap = endQuestionWrap.format();
		RenderUtils.render(TemplateConstants.QUESTION_END, formattedQuestionWrap,new String[]{"extra","entryLogic"});
		
		//问题描述动态插值
		JSFuncInvokeUtils.invoke("insertQuestionFragment();");
	}
	
	/**
	 * 返回当前问题（从临时答案列表继续）
	 */
	@JavascriptInterface
	public void resumeQuestionaire(){
		
		//获取当前问题
		Question curQuestion = InterviewContext.getTopQuestion();
		QuestionWrap curQuestionWrap = InterviewService.wrap(curQuestion);
		
		//渲染页面
		QuestionWrap formattedQuestionWrap = curQuestionWrap.format();
		RenderUtils.render(TemplateConstants.QUESTION, formattedQuestionWrap,new String[]{"extra","entryLogic"});
		
		//问题描述动态插值
		JSFuncInvokeUtils.invoke("insertQuestionFragment();");
	}
	
	/**
	 * 跳转答案列表
	 * @param answersJA
	 */
	@JavascriptInterface
	public void jumpToAnswerList(String answersJA,String type){
		
		InterviewQuestionaire interviewQuestionaire = InterviewContext.getCurInterviewQuestionaire();
		
		if(interviewQuestionaire.getQuestionaireCode().equals("LHC")){//生活日历问卷定制
			
			//获取答案参数
			List<AnswerValue> answerValueList = (List<AnswerValue>)JsonUtils.fromJson(answersJA, new TypeToken<List<AnswerValue>>(){}.getType());
			
			//封装答案
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("questionaireTitle", InterviewService.getSpecQuestionaire(interviewQuestionaire.getQuestionaireCode()).getTitle());
			
			Map<String,AnswerValue> answerMap = new HashMap<String,AnswerValue>();
			for(AnswerValue answerValue : answerValueList){
				answerMap.put(answerValue.getCode(), answerValue);
			}
			result.put("answerList",answerMap);
			
			//渲染页面
			if("all".equals(type)){
				RenderUtils.render(TemplateConstants.ANSWERS_LHC, result,null);
			}else if("part".equals(type)){
				RenderUtils.render(TemplateConstants.ANSWERS_LHC_PARTIAL, result,null);
			}
			
		}else{
			
			//封装答案数据
			List<AnswerValue> answerValueList = (List<AnswerValue>)JsonUtils.fromJson(answersJA, new TypeToken<List<AnswerValue>>(){}.getType());
			ResultWrap resultWrap = InterviewService.getAnswerList(answerValueList);
			
			//问题描述特殊处理
			List<Question> questionList = resultWrap.getQuestionList();
			for(Question question : questionList){
				
				String description = question.getDescription();
				description = FormatUtils.handleParaInHTML(description);//分段
				description = FormatUtils.escapeQuote4JS(description);//双引号转义
				question.setDescription(description);
			}
			
			//渲染页面
			if("all".equals(type)){
				RenderUtils.render(TemplateConstants.ANSWERS, resultWrap,new String[]{"entryLogic","exitLogic"});
			}else if("part".equals(type)){
				RenderUtils.render(TemplateConstants.ANSWERS_PARTIAL, resultWrap,new String[]{"entryLogic","exitLogic"});
			}
			
			//问题描述动态插值
			JSFuncInvokeUtils.invoke("insertQuestionFragment();");
		}
		
	}
	
	/**
	 * 获取访问答案
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
	 * 保存答案
	 * @param answersJS
	 */
	@JavascriptInterface
	public void saveAnswers(String answersJS){
		
		//保存当前问卷答案
		List<AnswerValue> answerValueMap = (List<AnswerValue>)JsonUtils.fromJson(answersJS, new TypeToken<List<AnswerValue>>(){}.getType());
		InterviewService.saveAnswers(answerValueMap);
		
		//更新当前问卷记录
		InterviewQuestionaire interviewQuestionaire = InterviewContext.getCurInterviewQuestionaire();
		interviewQuestionaire.setStatus(InterviewQuestionaire.STATUS_DONE);
		interviewQuestionaire.setLastModifiedTime(DateTimeUtils.getCurDateTime());
		InterviewService.updateInterviewQuestionaire(interviewQuestionaire);
		
		//获取下一个问卷
		Questionaire nextQuestionaire = InterviewService.getNextQuestionaire();
		if(nextQuestionaire == null){
			
			//停止录音
			AudioUtils.stop();
			
			//更新访谈记录
			InterviewBasic curInterviewBasic = InterviewContext.getCurInterviewBasic();
			curInterviewBasic.setStatus(InterviewBasic.STATUS_DONE);
			curInterviewBasic.setLastModifiedTime(DateTimeUtils.getCurDateTime());
			InterviewService.updateInterviewBasic(curInterviewBasic);
			
			//跳转首页
			Intent indexIntent = new Intent(SysqApplication.getContext(),IndexActivity.class);
			indexIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			SysqApplication.getContext().startActivity(indexIntent);
			
		}else{
			
			//新建下一个问卷记录
			InterviewQuestionaire nextInterviewQuestionaire = InterviewService.newInterviewQuestionaire(nextQuestionaire);
			
			//设置上下文
			InterviewContext.setCurInterviewQuestionaire(nextInterviewQuestionaire);
			
			//清空问题返回栈
			InterviewContext.clearQuestion();
			
			//更新访谈记录
			InterviewBasic curInterviewBasic = InterviewContext.getCurInterviewBasic();
			curInterviewBasic.setCurQuestionaireCode(nextQuestionaire.getCode());
			curInterviewBasic.setNextQuestionCode("");
			curInterviewBasic.setLastModifiedTime(DateTimeUtils.getCurDateTime());
			InterviewService.updateInterviewBasic(curInterviewBasic);
			
			//渲染页面
			nextQuestionaire.setIntroduction(FormatUtils.handleParaInHTML(nextQuestionaire.getIntroduction()));
			RenderUtils.render(TemplateConstants.QUESTIONAIRE, nextQuestionaire,null);
		}
	}
	
	/**
	 * 结束访谈
	 */
	@JavascriptInterface
	public void quitInterview(String answersJS,String quitReason){
		
		//停止录音
		AudioUtils.stop();
		
		if(!StringUtils.isEmpty(answersJS)){//保存问卷答案
			
			List<AnswerValue> answerValueMap = (List<AnswerValue>)JsonUtils.fromJson(answersJS, new TypeToken<List<AnswerValue>>(){}.getType());
			InterviewService.saveAnswers(answerValueMap);
			
			//更新问卷记录
			InterviewQuestionaire curInterviewQuestionaire = InterviewContext.getCurInterviewQuestionaire();
			curInterviewQuestionaire.setStatus(InterviewQuestionaire.STATUS_BREAK);
			curInterviewQuestionaire.setLastModifiedTime(DateTimeUtils.getCurDateTime());
			InterviewService.updateInterviewQuestionaire(curInterviewQuestionaire);
			
		}else{//删除问卷记录
			InterviewService.deleteInterviewQuestionaire(InterviewContext.getCurInterviewQuestionaire().getQuestionaireCode());
		}
		
		//更新访问记录
		InterviewBasic curInterviewBasic = InterviewContext.getCurInterviewBasic();
		curInterviewBasic.setStatus(InterviewBasic.STATUS_BREAK);
		curInterviewBasic.setQuitReason(quitReason);
		curInterviewBasic.setLastModifiedTime(DateTimeUtils.getCurDateTime());
		InterviewService.updateInterviewBasic(curInterviewBasic);
		
		//跳转主页
		Intent indexIntent = new Intent(SysqApplication.getContext(),IndexActivity.class);
		indexIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		SysqApplication.getContext().startActivity(indexIntent);
	}
	
	/**
	 * 暂停访谈
	 * @param answersJS
	 */
	@JavascriptInterface
	public void pauseInterview(String answersJS){
		
		//停止录音
		AudioUtils.stop();
		
		if(!StringUtils.isEmpty(answersJS)){//保存当前问卷答案
			List<AnswerValue> answerValueMap = (List<AnswerValue>)JsonUtils.fromJson(answersJS, new TypeToken<List<AnswerValue>>(){}.getType());
			InterviewService.saveAnswers(answerValueMap);
		}
		
		//更新访问记录
		InterviewBasic curInterviewBasic = InterviewContext.getCurInterviewBasic();
		curInterviewBasic.setNextQuestionCode(InterviewContext.getTopQuestion().getCode());
		InterviewService.updateInterviewBasic(curInterviewBasic);
		
		//跳转主页
		Intent indexIntent = new Intent(SysqApplication.getContext(),IndexActivity.class);
		indexIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		SysqApplication.getContext().startActivity(indexIntent);
	}
	
	/**
	 * 重做
	 */
	@JavascriptInterface
	public void redoQuestionaire(){
		
		//清空问题返回栈
		InterviewContext.clearQuestion();
		
		//跳转第一个问题
		this.jumpToFirstQuestion();
	}
	
	/**
	 * 返回主页
	 */
	@JavascriptInterface
	public void jumpToIndex(){
		
		//停止录音
		AudioUtils.stop();
		
		Intent indexIntent = new Intent(SysqApplication.getContext(),IndexActivity.class);
		indexIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		SysqApplication.getContext().startActivity(indexIntent);
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
	 * 弹出对话框
	 * @param title
	 * @param content
	 */
	@JavascriptInterface
	public void showDialog(String title,String content){
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(InterviewActivity.instance);
		dialogBuilder.setTitle(title);
		content = FormatUtils.handPara4App(content);//将标签<para>换成\n
		dialogBuilder.setMessage(content);
		dialogBuilder.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		AlertDialog dialog = dialogBuilder.create();
		dialog.getWindow().setLayout(500, 500);
		dialog.getWindow().setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
		dialog.show();
	}
	
}
