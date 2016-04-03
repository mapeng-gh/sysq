package com.huasheng.sysq.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.huasheng.sysq.db.AnswerDB;
import com.huasheng.sysq.db.InterviewDB;
import com.huasheng.sysq.db.QuestionDB;
import com.huasheng.sysq.db.QuestionaireDB;
import com.huasheng.sysq.db.ResultDB;
import com.huasheng.sysq.model.Answer;
import com.huasheng.sysq.model.AnswerValue;
import com.huasheng.sysq.model.AnswerWrap;
import com.huasheng.sysq.model.Interview;
import com.huasheng.sysq.model.Question;
import com.huasheng.sysq.model.QuestionWrap;
import com.huasheng.sysq.model.Questionaire;
import com.huasheng.sysq.model.Result;
import com.huasheng.sysq.model.ResultWrap;
import com.huasheng.sysq.model.Version;
import com.huasheng.sysq.util.InterviewContext;
import com.huasheng.sysq.util.SysqContext;

public class InterviewService {

	/**
	 * 保存访问
	 * @param interview
	 */
	public static void addInterview(Interview interview){
		
		//记录访问者Id
		interview.setInterviewerId(SysqContext.getInterviewer().getId());
		
		//记录开始时间
		interview.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
		
		//记录访谈状态
		interview.setStatus(Interview.STATUS_DOING);
		
		//记录当前版本
		interview.setVersionId(SysqContext.getCurrentVersion().getId());
		
		interview.setId((int)InterviewDB.save(interview));
		
		//保存到访谈上下文
		InterviewContext.setInterview(interview);
	}
	
	/**
	 * 获取第一个问题
	 * @param questionaireCode
	 * @return
	 */
	public static QuestionWrap getFirstQuestion(){
		
		//获取当前问卷
		Questionaire curQuestionaire = InterviewContext.getCurrentQuestionaire();
		
		//查询第一个问题
		List<Question> questionList = QuestionDB.getList(curQuestionaire.getCode(),SysqContext.getCurrentVersion().getId());
		if(questionList == null || questionList.size() <= 0){
			throw new RuntimeException("问卷[" + curQuestionaire.getCode() + "]没有问题");
		}
		Question firstQuestion = questionList.get(0);
		
		//包装成questionWrap
		QuestionWrap questionWrap = wrap(firstQuestion);
				
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
		Questionaire curQuestionaire = InterviewContext.getCurrentQuestionaire();
		Question curQuestion = InterviewContext.getCurrentQuestion();
		Version curVersion = SysqContext.getCurrentVersion();
		
		//获取下一个question
		Question nextQuestion = null;
		List<Question> questionList = QuestionDB.getList(curQuestionaire.getCode(),curVersion.getId());
		for(int i=0;i<questionList.size();i++){
			if(questionList.get(i).getCode().equals(curQuestion.getCode())){
				if(i == questionList.size() - 1){
					return null;
				}
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
		Questionaire curQuestionaire = InterviewContext.getCurrentQuestionaire();
		Version curVersion = SysqContext.getCurrentVersion();
		
		Question specQuestion = null;
		
		List<Question> questionList = QuestionDB.getList(curQuestionaire.getCode(),curVersion.getId());
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
	
	/**
	 * 获取上一题
	 * @return
	 */
	public static QuestionWrap getPreviousQuestion(){
		//获取当前问卷、当前问题
		Questionaire curQuestionaire = InterviewContext.getCurrentQuestionaire();
		Question curQuestion = InterviewContext.getCurrentQuestion();
		Version curVersion = SysqContext.getCurrentVersion();
		
		//获取上一question
		Question previousQuestion = null;
		List<Question> questionList = QuestionDB.getList(curQuestionaire.getCode(),curVersion.getId());
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
	
	private static QuestionWrap wrap(Question question){
		QuestionWrap questionWrap = new QuestionWrap();
		questionWrap.setQuestionaire(InterviewContext.getCurrentQuestionaire());//设置问卷
		
		questionWrap.setQuestion(question);//设置问题
		
		/**
		 * 设置答案
		 */
		List<Answer> answerList = AnswerDB.getList(question.getCode(),SysqContext.getCurrentVersion().getId());
		if(answerList == null || answerList.size() <= 0){
			throw new RuntimeException("问题[" + question.getCode() + "]没有答案");
		}
		
		List<AnswerWrap> answerWrapList = new ArrayList<AnswerWrap>();
		for(Answer answer : answerList){
			answerWrapList.add(new AnswerWrap(answer));
		}
		questionWrap.setAnswerWrapList(answerWrapList);
		
		return questionWrap;
	}
	
	/**
	 * 获取答案列表
	 * @param answerValueMap
	 * @return
	 */
	public static ResultWrap getAnswerList(Map<String,AnswerValue> answerValueMap){
		
		ResultWrap resultWrap = new ResultWrap();
		resultWrap.setQuestionaire(InterviewContext.getCurrentQuestionaire());//设置问卷
		
		List<Question> questionList = QuestionDB.getList(InterviewContext.getCurrentQuestionaire().getCode(), SysqContext.getCurrentVersion().getId());
		resultWrap.setQuestionList(questionList);//设置所有问题
		
		/**
		 * 设置答案
		 */
		Map<String,List<AnswerValue>> answerOfQuestionMap = new HashMap<String,List<AnswerValue>>();
		for(Question question : questionList){
			String questionCode = question.getCode();
			List<AnswerValue> answerValueList = new ArrayList<AnswerValue>();

			List<Answer> answerList = AnswerDB.getList(questionCode, SysqContext.getCurrentVersion().getId());
			for(Answer answer : answerList){
				answerValueList.add(answerValueMap.get(answer.getCode()));
			}
			
			answerOfQuestionMap.put(questionCode, answerValueList);
		}
		resultWrap.setAnswerOfQuestionMap(answerOfQuestionMap);
		
		return resultWrap;
	}
	
	/**
	 * 获取第一个问卷
	 * @return
	 */
	public static Questionaire getFirstQuestionaire(){
		int type = InterviewContext.getInterview().getType();
		int versionId = SysqContext.getCurrentVersion().getId();
		
		List<Questionaire> questionaireList = QuestionaireDB.getList(versionId, type);
		
		if(questionaireList == null || questionaireList.size() <= 0){
			throw new RuntimeException("版本号：" + versionId + " 问卷类型：" + type + " 下没有问卷");
		}
		
		Questionaire firstQuestionaire = questionaireList.get(0);
		return firstQuestionaire;
	}
	
	/**
	 * 保存答案
	 * @param answerMap
	 */
	public static void saveAnswers(Map<String,AnswerValue> answerMap){
		
		Collection<AnswerValue> answerValues = answerMap.values();
		for(AnswerValue answerValue : answerValues){
			Result result = new Result();
			result.setInterviewId(InterviewContext.getInterview().getId());
			result.setAnswerCode(answerValue.getCode());
			result.setAnswerValue(answerValue.getValue());
			ResultDB.save(result);
		}
	}
	
	/**
	 * 获取下一个问卷
	 * @return
	 */
	public static Questionaire getNextQuestionaire(){
		
		//获取当前访谈、当前问卷、当前版本
		Interview curInterview = InterviewContext.getInterview();
		Questionaire curQuestionaire = InterviewContext.getCurrentQuestionaire();
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
	 * 退出访谈
	 */
	public static void quitInterview(){
		Interview curInterview = InterviewContext.getInterview();
		curInterview.setStatus(Interview.STATUS_BREAK);
		curInterview.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
		InterviewDB.update(curInterview);
	}
	
	/**
	 * 完成访谈
	 */
	public static void finishInterview(){
		Interview curInterview = InterviewContext.getInterview();
		curInterview.setStatus(Interview.STATUS_DONE);
		curInterview.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
		InterviewDB.update(curInterview);
	}
	
	/**
	 * 记录访谈当前位置
	 */
	public static void recordInterviewProgress(){
		
		//获取当前问卷、当前问题
		Questionaire curQuestionaire = InterviewContext.getCurrentQuestionaire();
		Question curQuestion = InterviewContext.getCurrentQuestion();
		
		//更新
		Interview curInterview = InterviewContext.getInterview();
		curInterview.setCurQuestionaireCode(curQuestionaire.getCode());
		curInterview.setNextQuestionCode(curQuestion.getCode());
		InterviewDB.update(curInterview);
	}
}
