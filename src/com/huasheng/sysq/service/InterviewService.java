package com.huasheng.sysq.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.huasheng.sysq.db.AnswerDB;
import com.huasheng.sysq.db.InterviewAnswerDB;
import com.huasheng.sysq.db.InterviewBasicDB;
import com.huasheng.sysq.db.InterviewQuestionDB;
import com.huasheng.sysq.db.InterviewQuestionaireDB;
import com.huasheng.sysq.db.QuestionDB;
import com.huasheng.sysq.db.QuestionaireDB;
import com.huasheng.sysq.db.VersionDB;
import com.huasheng.sysq.model.Answer;
import com.huasheng.sysq.model.AnswerValue;
import com.huasheng.sysq.model.AnswerWrap;
import com.huasheng.sysq.model.InterviewAnswer;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.InterviewQuestion;
import com.huasheng.sysq.model.InterviewQuestionaire;
import com.huasheng.sysq.model.Page;
import com.huasheng.sysq.model.Question;
import com.huasheng.sysq.model.QuestionWrap;
import com.huasheng.sysq.model.Questionaire;
import com.huasheng.sysq.model.ResultWrap;
import com.huasheng.sysq.model.Version;
import com.huasheng.sysq.util.DateTimeUtils;
import com.huasheng.sysq.util.InterviewContext;
import com.huasheng.sysq.util.SysqContext;

public class InterviewService {

	/**
	 * 保存访问基本信息
	 * @param interview
	 */
	public static void addInterviewBasic(InterviewBasic interviewBasic){
		
		//记录访问者Id
		interviewBasic.setInterviewerId(SysqContext.getInterviewer().getId());
		
		//记录开始时间
		interviewBasic.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
		
		//记录访谈状态
		interviewBasic.setStatus(InterviewBasic.STATUS_DOING);
		
		//记录当前版本
		interviewBasic.setVersionId(SysqContext.getCurrentVersion().getId());
		
		interviewBasic.setId((int)InterviewBasicDB.save(interviewBasic));
	}
	
	/**
	 * 获取答案列表
	 * @param answerValueMap
	 * @return
	 */
	public static ResultWrap getAnswerList(List<AnswerValue> answerValueListParam){
		
		ResultWrap resultWrap = new ResultWrap();
		
		//设置问卷
		resultWrap.setQuestionaire(InterviewContext.getCurQuestionaire());
		
		//设置问题列表和对应的答案
		List<Question> showQuestionList = new ArrayList<Question>();
		Map<String,List<AnswerValue>> answerOfQuestionMap = new HashMap<String,List<AnswerValue>>();
		
		List<Question> questionList = QuestionDB.getList(InterviewContext.getCurQuestionaire().getCode(), SysqContext.getCurrentVersion().getId(),Question.QUESTION_NOT_END);
		for(Question question : questionList){
			
			//挑出问题对应的所有答案
			List<AnswerValue> answerValueList = new ArrayList<AnswerValue>();
			for(AnswerValue answerValueParam : answerValueListParam){
				if(answerValueParam.getQuestionCode().equals(question.getCode())){
					answerValueList.add(answerValueParam);
				}
			}
			
			//跳过的问题不显示在答案列表
			if(answerValueList.size() > 0){
				showQuestionList.add(question);
				answerOfQuestionMap.put(question.getCode(), answerValueList);
			}
			
		}
		
		resultWrap.setQuestionList(showQuestionList);
		resultWrap.setAnswerOfQuestionMap(answerOfQuestionMap);
		
		return resultWrap;
	}
	
	/**
	 * 保存访问问卷
	 * @param answerMap
	 */
	public static void saveInterviewQuestionaire(List<AnswerValue> answerValueList){
		
		//保存interviewQuestionaire（主要记录时间）
		InterviewQuestionaire interviewQuestionaire = new InterviewQuestionaire();
		interviewQuestionaire.setInterviewBasicId(InterviewContext.getInterviewBasic().getId());
		interviewQuestionaire.setQuestionaireCode(InterviewContext.getCurQuestionaire().getCode());
		interviewQuestionaire.setStartTime(InterviewContext.getQuestionaireStartTime());
		interviewQuestionaire.setEndTime(DateTimeUtils.getCurTime());
		interviewQuestionaire.setVersionId(SysqContext.getCurrentVersion().getId());
		InterviewQuestionaireDB.insert(interviewQuestionaire);
		
		//保存interviewQuestion（主要记录访谈的问题）
		Set<String> questionCodeSet = new HashSet<String>();
		for(AnswerValue answerValue : answerValueList){
			questionCodeSet.add(answerValue.getQuestionCode());
		}
		for(String questionCode : questionCodeSet){
			InterviewQuestion interviewQuestion = new InterviewQuestion();
			interviewQuestion.setInterviewBasicId(InterviewContext.getInterviewBasic().getId());
			interviewQuestion.setQuestionaireCode(InterviewContext.getCurQuestionaire().getCode());
			interviewQuestion.setQuestionCode(questionCode);
			interviewQuestion.setVersionId(SysqContext.getCurrentVersion().getId());
			InterviewQuestionDB.insert(interviewQuestion);
		}
		
		//保存interviewAnswer
		for(AnswerValue answerValue : answerValueList){
			InterviewAnswer interviewAnswer = new InterviewAnswer();
			interviewAnswer.setInterviewBasicId(InterviewContext.getInterviewBasic().getId());
			interviewAnswer.setQuestionCode(answerValue.getQuestionCode());
			interviewAnswer.setAnswerCode(answerValue.getCode());
			interviewAnswer.setAnswerValue(answerValue.getValue());
			InterviewAnswerDB.insert(interviewAnswer);
		}
	}
	
	/**
	 * 获取第一个问题
	 * @param questionaireCode
	 * @return
	 */
	public static QuestionWrap getFirstQuestion(){
		
		//获取当前问卷
		Questionaire curQuestionaire = InterviewContext.getCurQuestionaire();
		
		//查询第一个问题
		List<Question> questionList = QuestionDB.getList(curQuestionaire.getCode(),SysqContext.getCurrentVersion().getId(),Question.QUESTION_NOT_END);
		if(questionList == null || questionList.size() <= 0){
			throw new RuntimeException("问卷[" + curQuestionaire.getCode() + "]没有问题");
		}
		Question firstQuestion = questionList.get(0);
		
		//包装成questionWrap
		QuestionWrap questionWrap = wrap(firstQuestion);
				
		return questionWrap;
	}
	
	/**
	 * 获取上一题
	 * @return
	 */
	public static QuestionWrap getPreviousQuestion(){
		
		//获取当前问卷、当前版本、当前问题
		Questionaire curQuestionaire = InterviewContext.getCurQuestionaire();
		Version curVersion = SysqContext.getCurrentVersion();
		Question curQuestion = InterviewContext.getCurQuestion();
		
		//获取上一question
		Question previousQuestion = null;
		List<Question> questionList = QuestionDB.getList(curQuestionaire.getCode(),curVersion.getId(),Question.QUESTION_NOT_END);
		for(int i=0;i<questionList.size();i++){
			if(questionList.get(i).getCode().equals(curQuestion.getCode())){
				if(i == 0){
					return null;
				}
				previousQuestion = questionList.get(i - 1);
				break;
			}
		}
		
		//包装成questionWrap
		QuestionWrap questionWrap = wrap(previousQuestion);
		return questionWrap;
	}
	
	/**
	 * 获取下一个问题
	 * @param questionaireCode
	 * @param questionCode
	 * @return
	 */
	public static QuestionWrap getNextQuestion(){
		
		//获取当前问卷、当前问题
		Questionaire curQuestionaire = InterviewContext.getCurQuestionaire();
		Version curVersion = SysqContext.getCurrentVersion();
		Question curQuestion = InterviewContext.getCurQuestion();
		
		//获取下一个question
		Question nextQuestion = null;
		List<Question> questionList = QuestionDB.getList(curQuestionaire.getCode(),curVersion.getId(),Question.QUESTION_NOT_END);
		for(int i=0;i<questionList.size();i++){
			if(questionList.get(i).getCode().equals(curQuestion.getCode())){
				nextQuestion = questionList.get(i + 1);
				break;
			}
		}
		
		//包装成questionWrap
		QuestionWrap questionWrap = wrap(nextQuestion);
		return questionWrap;
	}
	
	/**
	 * 获取指定问题
	 * @param questionCode
	 * @return
	 */
	public static QuestionWrap getSpecQuestion(String questionCode){
		
		//获取当前问卷、当前版本
		Questionaire curQuestionaire = InterviewContext.getCurQuestionaire();
		Version curVersion = SysqContext.getCurrentVersion();
		
		Question specQuestion = null;
		
		List<Question> questionList = QuestionDB.getList(curQuestionaire.getCode(),curVersion.getId(),Question.QUESTION_NOT_END);
		for(Question question : questionList){
			if(question.getCode().equals(questionCode)){
				specQuestion = question;
				break;
			}
		}
		
		if(specQuestion == null){//指定问题不存在
			throw new RuntimeException("问题[" + questionCode + "]不存在");
		}
		
		//包装成questionWrap
		QuestionWrap questionWrap = wrap(specQuestion);
		return questionWrap;
		
	}
	
	public static QuestionWrap wrap(Question question){
		
		//设置问卷
		QuestionWrap questionWrap = new QuestionWrap();
		questionWrap.setQuestionaire(InterviewContext.getCurQuestionaire());
		
		//设置问题
		questionWrap.setQuestion(question);
		
		//设置答案
		List<AnswerWrap> answerWrapList = new ArrayList<AnswerWrap>();
		List<Answer> answerList = AnswerDB.getList(question.getCode(),SysqContext.getCurrentVersion().getId());
		for(Answer answer : answerList){
			answerWrapList.add(new AnswerWrap(answer));
		}
		questionWrap.setAnswerWrapList(answerWrapList);
		
		return questionWrap;
	}
	
	
	
	/**
	 * 获取第一个问卷
	 * @return
	 */
	public static Questionaire getFirstQuestionaire(){
		int type = InterviewContext.getInterviewBasic().getType();
		int versionId = SysqContext.getCurrentVersion().getId();
		
		List<Questionaire> questionaireList = QuestionaireDB.getList(versionId, type);
		
		if(questionaireList == null || questionaireList.size() <= 0){
			throw new RuntimeException("版本号：" + versionId + " 问卷类型：" + type + " 下没有问卷");
		}
		
		Questionaire firstQuestionaire = questionaireList.get(0);
		return firstQuestionaire;
	}
	
	/**
	 * 获取下一个问卷
	 * @return
	 */
	public static Questionaire getNextQuestionaire(){
		
		//获取当前访谈、当前问卷、当前版本
		InterviewBasic curInterview = InterviewContext.getInterviewBasic();
		Questionaire curQuestionaire = InterviewContext.getCurQuestionaire();
		Version curVersion = SysqContext.getCurrentVersion();
		
		//获取下一个问卷
		Questionaire nextQuestionaire = null;
		List<Questionaire> questionaireList = QuestionaireDB.getList(curVersion.getId(),curInterview.getType());
		for(int i = 0;i<questionaireList.size();i++){
			Questionaire questionaire = questionaireList.get(i);
			if(questionaire.getCode().equals(curQuestionaire.getCode())){
				if(i == questionaireList.size()-1){
					return null;
				}
				nextQuestionaire = questionaireList.get(i+1);
				break;
			}
		}
		
		return nextQuestionaire;
	}
	
	/**
	 * 更新访谈状态
	 * @param interviewStatus
	 */
	public static void updateInterviewStatus(Integer interviewStatus){
		InterviewBasic curInterview = InterviewContext.getInterviewBasic();
		curInterview.setStatus(interviewStatus);
		curInterview.setLastModifiedTime(DateTimeUtils.getCurTime());
		InterviewBasicDB.update(curInterview);
	}
	
	/**
	 * 记录访谈当前位置
	 * @param questionaireCode
	 * @param questionCode
	 */
	public static void updateInterviewPosition(String questionaireCode,String questionCode){
		InterviewBasic curInterview = InterviewContext.getInterviewBasic();
		if(!StringUtils.isEmpty(questionaireCode)){
			curInterview.setCurQuestionaireCode(questionaireCode);
		}
		curInterview.setCurQuestionaireCode(questionaireCode);
		if(!StringUtils.isEmpty(questionCode)){
			curInterview.setNextQuestionCode(questionCode);
		}
		InterviewBasicDB.update(curInterview);
	}
	
	/**
	 * 获取系统问卷当前版本号
	 * @return
	 */
	public static Version getCurrentVersion(){
		return VersionDB.getCurVersion();
	}
	
}
