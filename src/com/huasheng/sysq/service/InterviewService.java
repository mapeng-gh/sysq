package com.huasheng.sysq.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.huasheng.sysq.db.AnswerDB;
import com.huasheng.sysq.db.InterviewDB;
import com.huasheng.sysq.db.QuestionDB;
import com.huasheng.sysq.db.QuestionaireDB;
import com.huasheng.sysq.model.Answer;
import com.huasheng.sysq.model.AnswerWrap;
import com.huasheng.sysq.model.Interview;
import com.huasheng.sysq.model.Question;
import com.huasheng.sysq.model.QuestionWrap;
import com.huasheng.sysq.model.Questionaire;
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
		
		//记录第一个问卷
		Questionaire firstQuestionaire = QuestionaireDB.getFirst(SysqContext.getCurrentVersion().getId(),interview.getType());
		interview.setCurQuestionaireCode(firstQuestionaire.getCode());
		
		interview.setId((int)InterviewDB.save(interview));
		
		//保存到访谈上下文
		InterviewContext.setInterview(interview);
		InterviewContext.setCurrentQuestionaire(firstQuestionaire);
	}
	
	/**
	 * 获取第一个问题
	 * @param questionaireCode
	 * @return
	 */
	public static QuestionWrap getFirstQuestion(String questionaireCode){
		
		List<Question> questionList = QuestionDB.getList(questionaireCode,SysqContext.getCurrentVersion().getId());
		if(questionList == null || questionList.size() <= 0){
			throw new RuntimeException("问卷[" + questionaireCode + "]没有问题");
		}
		
		//包装成questionWrap
		Question firstQuestion = questionList.get(0);
		QuestionWrap questionWrap = wrap(firstQuestion);
		
		//保存当前题目到上下文
		InterviewContext.setCurrentQuestion(firstQuestion);
				
		return questionWrap;
	}
	
	/**
	 * 获取下一个问题
	 * @param questionaireCode
	 * @param questionCode
	 * @return
	 */
	public static QuestionWrap getNextQuestion(String questionaireCode,String questionCode){
		List<Question> questionList = QuestionDB.getList(questionaireCode,SysqContext.getCurrentVersion().getId());
		if(questionList == null || questionList.size() <= 0){
			throw new RuntimeException("问卷[" + questionaireCode + "]没有问题");
		}
		
		//获取下一个question
		Question nextQuestion = null;
		for(int i=0;i<questionList.size();i++){
			if(questionList.get(i).getCode().equals(questionCode)){
				if(i == questionList.size() - 1){
					return null;
				}
				nextQuestion = questionList.get(i + 1);
				break;
			}
		}
		
		//包装成questionWrap
		QuestionWrap questionWrap = wrap(nextQuestion);
		
		//保存当前题目到上下文
		InterviewContext.setCurrentQuestion(nextQuestion);
		
		return questionWrap;
	}
	
	private static QuestionWrap wrap(Question question){
		QuestionWrap questionWrap = new QuestionWrap();
		questionWrap.setQuestionaire(InterviewContext.getCurrentQuestionaire());//设置问卷
		
		questionWrap.setQuestion(question);//设置问题
		
		if(question.getType().equals(Question.TYPE_SIMPLE)){//简单问题
			List<Answer> answerList = AnswerDB.getList(question.getCode(),SysqContext.getCurrentVersion().getId());
			if(answerList == null || answerList.size() <= 0){
				throw new RuntimeException("问题[" + question.getCode() + "]没有答案");
			}
			
			List<AnswerWrap> answerWrapList = new ArrayList<AnswerWrap>();
			for(Answer answer : answerList){
				answerWrapList.add(new AnswerWrap(answer));
			}
			questionWrap.setAnswerWrapList(answerWrapList);//设置答案
			
		}else if(question.getType().equals(Question.TYPE_COMPLEX)){//复杂问题（包含子问题）
			List<QuestionWrap> subQuesWrapList = new ArrayList<QuestionWrap>();
			
			List<Question> subQuesList = QuestionDB.getSubList(question.getCode(),SysqContext.getCurrentVersion().getId());
			if(subQuesList == null || subQuesList.size() <= 0){
				throw new RuntimeException("问题[" + question.getCode() + "]没有子问题");
			}
			
			for(Question subQuestion : subQuesList){
				QuestionWrap subQuesWrap = new QuestionWrap();
				subQuesWrap.setQuestion(subQuestion);
				
				List<Answer> subAnswerList = AnswerDB.getList(subQuestion.getCode(),SysqContext.getCurrentVersion().getId());
				if(subAnswerList == null || subAnswerList.size() <= 0){
					throw new RuntimeException("子问题[" + subQuestion.getCode() + "]没有答案");
				}
				
				List<AnswerWrap> subAnswerWrapList = new ArrayList<AnswerWrap>();
				for(Answer subAnswer : subAnswerList){
					subAnswerWrapList.add(new AnswerWrap(subAnswer));
				}
				subQuesWrap.setAnswerWrapList(subAnswerWrapList);
				
				subQuesWrapList.add(subQuesWrap);
			}
			
			questionWrap.setSubQuesWrapList(subQuesWrapList);//设置子问题
		}
		
		return questionWrap;
	}
}
