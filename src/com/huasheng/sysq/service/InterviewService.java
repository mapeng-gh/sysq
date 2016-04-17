package com.huasheng.sysq.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	 * 新建访问记录
	 * @param interview
	 */
	public static InterviewBasic newInterviewBasic(InterviewBasic interviewBasic){
		
		//记录受访者信息、访问状态信息
		interviewBasic.setInterviewerId(SysqContext.getInterviewer().getId());
		interviewBasic.setVersionId(SysqContext.getCurrentVersion().getId());
		interviewBasic.setStartTime(DateTimeUtils.getCurTime());
		interviewBasic.setStatus(InterviewBasic.STATUS_DOING);
		
		//保存
		int id = InterviewBasicDB.insert(interviewBasic);
		interviewBasic.setId(id);
		
		return interviewBasic;
	}
	
	/**
	 * 更新访问记录
	 * @param interviewBasic
	 */
	public static void updateInterviewBasic(InterviewBasic interviewBasic){
		InterviewBasicDB.update(interviewBasic);
	}
	
	/**
	 * 添加问卷记录
	 * @param questionaire
	 * @return
	 */
	public static InterviewQuestionaire newInterviewQuestionaire(Questionaire questionaire){
		
		InterviewQuestionaire interviewQuestionaire = new InterviewQuestionaire();
		interviewQuestionaire.setInterviewBasicId(InterviewContext.getCurInterviewBasic().getId());
		interviewQuestionaire.setQuestionaireCode(questionaire.getCode());
		interviewQuestionaire.setStartTime(DateTimeUtils.getCurTime());
		interviewQuestionaire.setLastModifiedTime(DateTimeUtils.getCurTime());
		interviewQuestionaire.setStatus(InterviewQuestionaire.STATUS_DOING);
		interviewQuestionaire.setSeqNum(questionaire.getSeqNum());
		interviewQuestionaire.setVersionId(SysqContext.getCurrentVersion().getId());
		int id = InterviewQuestionaireDB.insert(interviewQuestionaire);
		interviewQuestionaire.setId(id);
		
		return interviewQuestionaire;
	}
	
	/**
	 * 更新问卷记录
	 * @param interviewQuestionaire
	 */
	public static void updateInterviewQuestionaire(InterviewQuestionaire interviewQuestionaire){
		InterviewQuestionaireDB.update(interviewQuestionaire);
	}
	
	/**
	 * 获取答案列表
	 * @param answerValueMap
	 * @return
	 */
	public static ResultWrap getAnswerList(List<AnswerValue> answerValueListParam){
		
		//获取当前问卷、当前版本号
		InterviewQuestionaire curInterviewQuestionaire = InterviewContext.getCurInterviewQuestionaire();
		Version curVersion = SysqContext.getCurrentVersion();
		
		ResultWrap resultWrap = new ResultWrap();
		
		//设置问卷
		resultWrap.setQuestionaire(QuestionaireDB.selectByCode(curInterviewQuestionaire.getQuestionaireCode(), curVersion.getId()));
		
		//设置问题列表和对应的答案
		List<Question> showQuestionList = new ArrayList<Question>();
		Map<String,List<AnswerValue>> answerOfQuestionMap = new HashMap<String,List<AnswerValue>>();
		
		List<Question> questionList = QuestionDB.getList(curInterviewQuestionaire.getQuestionaireCode(),curVersion.getId(),Question.QUESTION_NOT_END);
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
	 * 保存答案
	 * @param answerMap
	 */
	public static void saveAnswers(List<AnswerValue> answerValueList){
		
		//保存interviewQuestion（主要记录访谈的问题）
		Set<String> questionCodeSet = new HashSet<String>();
		for(AnswerValue answerValue : answerValueList){
			questionCodeSet.add(answerValue.getQuestionCode());
		}
		for(String questionCode : questionCodeSet){
			InterviewQuestion interviewQuestion = new InterviewQuestion();
			interviewQuestion.setInterviewBasicId(InterviewContext.getCurInterviewBasic().getId());
			interviewQuestion.setQuestionaireCode(InterviewContext.getCurInterviewQuestionaire().getQuestionaireCode());
			interviewQuestion.setQuestionCode(questionCode);
			interviewQuestion.setSeqNum(QuestionDB.selectByCode(questionCode, SysqContext.getCurrentVersion().getId()).getSeqNum());
			interviewQuestion.setVersionId(SysqContext.getCurrentVersion().getId());
			InterviewQuestionDB.insert(interviewQuestion);
		}
		
		//保存interviewAnswer
		for(AnswerValue answerValue : answerValueList){
			InterviewAnswer interviewAnswer = new InterviewAnswer();
			interviewAnswer.setInterviewBasicId(InterviewContext.getCurInterviewBasic().getId());
			interviewAnswer.setQuestionCode(answerValue.getQuestionCode());
			interviewAnswer.setAnswerCode(answerValue.getCode());
			interviewAnswer.setAnswerValue(answerValue.getValue());
			interviewAnswer.setAnswerText(answerValue.getText());
			InterviewAnswerDB.insert(interviewAnswer);
		}
	}
	
	/**
	 * 获取第一个问题
	 * @param questionaireCode
	 * @return
	 */
	public static QuestionWrap getFirstQuestion(){
		
		//获取当前问卷、当前版本号
		InterviewQuestionaire curInterviewQuestionaire = InterviewContext.getCurInterviewQuestionaire();
		Version curVersion = SysqContext.getCurrentVersion();
		
		//查询第一个问题
		List<Question> questionList = QuestionDB.getList(curInterviewQuestionaire.getQuestionaireCode(),curVersion.getId(),Question.QUESTION_NOT_END);
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
		InterviewQuestionaire curInterviewQuestionaire = InterviewContext.getCurInterviewQuestionaire();
		Version curVersion = SysqContext.getCurrentVersion();
		Question curQuestion = InterviewContext.getCurQuestion();
		
		//获取上一question
		Question previousQuestion = null;
		List<Question> questionList = QuestionDB.getList(curInterviewQuestionaire.getQuestionaireCode(),curVersion.getId(),Question.QUESTION_NOT_END);
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
		
		//获取当前问卷、当前版本号、当前问题
		InterviewQuestionaire curInterviewQuestionaire = InterviewContext.getCurInterviewQuestionaire();
		Version curVersion = SysqContext.getCurrentVersion();
		Question curQuestion = InterviewContext.getCurQuestion();
		
		//获取下一个question
		Question nextQuestion = null;
		List<Question> questionList = QuestionDB.getList(curInterviewQuestionaire.getQuestionaireCode(),curVersion.getId(),Question.QUESTION_NOT_END);
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
		InterviewQuestionaire curInterviewQuestionaire = InterviewContext.getCurInterviewQuestionaire();
		Version curVersion = SysqContext.getCurrentVersion();
		
		Question specQuestion = null;
		
		//查询指定问题
		List<Question> questionList = QuestionDB.getList(curInterviewQuestionaire.getQuestionaireCode(),curVersion.getId(),Question.QUESTION_NOT_END);
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
		
		//获取当前问卷、当前版本号
		String curQuestionaireCode = InterviewContext.getCurInterviewQuestionaire().getQuestionaireCode();
		Version curVersion = SysqContext.getCurrentVersion();
		
		QuestionWrap questionWrap = new QuestionWrap();
		
		//设置问卷
		
		Questionaire curQuestionaire = QuestionaireDB.selectByCode(curQuestionaireCode,curVersion.getId());
		questionWrap.setQuestionaire(curQuestionaire);
		
		//设置问题
		questionWrap.setQuestion(question);
		
		//设置答案
		List<AnswerWrap> answerWrapList = new ArrayList<AnswerWrap>();
		List<Answer> answerList = AnswerDB.getList(question.getCode(),curVersion.getId());
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
		
		//获取当前访问记录、当前版本号
		InterviewBasic curInterviewBasic = InterviewContext.getCurInterviewBasic();
		Version curVersion = SysqContext.getCurrentVersion();
		
		//获取第一个问卷
		Questionaire firstQuestionaire = null;
		List<Questionaire> questionaireList = QuestionaireDB.getList(curVersion.getId(),curInterviewBasic.getType());
		if(questionaireList == null || questionaireList.size() <= 0){
			throw new RuntimeException("版本号：" + curVersion.getId() + " 问卷类型：" + curInterviewBasic.getType() + " 下没有问卷");
		}
		firstQuestionaire = questionaireList.get(0);
		
		return firstQuestionaire;
	}
	
	/**
	 * 获取下一个问卷
	 * @return
	 */
	public static Questionaire getNextQuestionaire(){
		
		//获取当前访谈、当前问卷、当前版本
		InterviewBasic curInterviewBasic = InterviewContext.getCurInterviewBasic();
		InterviewQuestionaire curInterviewQuestionaire = InterviewContext.getCurInterviewQuestionaire();
		Version curVersion = SysqContext.getCurrentVersion();
		
		//获取下一个问卷
		Questionaire nextQuestionaire = null;
		List<Questionaire> questionaireList = QuestionaireDB.getList(curVersion.getId(),curInterviewBasic.getType());
		for(int i = 0;i<questionaireList.size();i++){
			Questionaire questionaire = questionaireList.get(i);
			if(questionaire.getCode().equals(curInterviewQuestionaire.getQuestionaireCode())){
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
	 * 获取系统问卷当前版本号
	 * @return
	 */
	public static Version getCurrentVersion(){
		return VersionDB.getCurVersion();
	}
	
}