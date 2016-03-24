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
	 * �������
	 * @param interview
	 */
	public static void addInterview(Interview interview){
		
		//��¼������Id
		interview.setInterviewerId(SysqContext.getInterviewer().getId());
		
		//��¼��ʼʱ��
		interview.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
		
		//��¼��̸״̬
		interview.setStatus(Interview.STATUS_DOING);
		
		//��¼��ǰ�汾
		interview.setVersionId(SysqContext.getCurrentVersion().getId());
		
		//��¼��һ���ʾ�
		Questionaire firstQuestionaire = QuestionaireDB.getFirst(SysqContext.getCurrentVersion().getId(),interview.getType());
		interview.setCurQuestionaireCode(firstQuestionaire.getCode());
		
		interview.setId((int)InterviewDB.save(interview));
		
		//���浽��̸������
		InterviewContext.setInterview(interview);
		InterviewContext.setCurrentQuestionaire(firstQuestionaire);
	}
	
	/**
	 * ��ȡ��һ������
	 * @param questionaireCode
	 * @return
	 */
	public static QuestionWrap getFirstQuestion(String questionaireCode){
		
		List<Question> questionList = QuestionDB.getList(questionaireCode,SysqContext.getCurrentVersion().getId());
		if(questionList == null || questionList.size() <= 0){
			throw new RuntimeException("�ʾ�[" + questionaireCode + "]û������");
		}
		
		//��װ��questionWrap
		Question firstQuestion = questionList.get(0);
		QuestionWrap questionWrap = wrap(firstQuestion);
		
		//���浱ǰ��Ŀ��������
		InterviewContext.setCurrentQuestion(firstQuestion);
				
		return questionWrap;
	}
	
	/**
	 * ��ȡ��һ������
	 * @param questionaireCode
	 * @param questionCode
	 * @return
	 */
	public static QuestionWrap getNextQuestion(String questionaireCode,String questionCode){
		List<Question> questionList = QuestionDB.getList(questionaireCode,SysqContext.getCurrentVersion().getId());
		if(questionList == null || questionList.size() <= 0){
			throw new RuntimeException("�ʾ�[" + questionaireCode + "]û������");
		}
		
		//��ȡ��һ��question
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
		
		//��װ��questionWrap
		QuestionWrap questionWrap = wrap(nextQuestion);
		
		//���浱ǰ��Ŀ��������
		InterviewContext.setCurrentQuestion(nextQuestion);
		
		return questionWrap;
	}
	
	private static QuestionWrap wrap(Question question){
		QuestionWrap questionWrap = new QuestionWrap();
		questionWrap.setQuestionaire(InterviewContext.getCurrentQuestionaire());//�����ʾ�
		
		questionWrap.setQuestion(question);//��������
		
		if(question.getType().equals(Question.TYPE_SIMPLE)){//������
			List<Answer> answerList = AnswerDB.getList(question.getCode(),SysqContext.getCurrentVersion().getId());
			if(answerList == null || answerList.size() <= 0){
				throw new RuntimeException("����[" + question.getCode() + "]û�д�");
			}
			
			List<AnswerWrap> answerWrapList = new ArrayList<AnswerWrap>();
			for(Answer answer : answerList){
				answerWrapList.add(new AnswerWrap(answer));
			}
			questionWrap.setAnswerWrapList(answerWrapList);//���ô�
			
		}else if(question.getType().equals(Question.TYPE_COMPLEX)){//�������⣨���������⣩
			List<QuestionWrap> subQuesWrapList = new ArrayList<QuestionWrap>();
			
			List<Question> subQuesList = QuestionDB.getSubList(question.getCode(),SysqContext.getCurrentVersion().getId());
			if(subQuesList == null || subQuesList.size() <= 0){
				throw new RuntimeException("����[" + question.getCode() + "]û��������");
			}
			
			for(Question subQuestion : subQuesList){
				QuestionWrap subQuesWrap = new QuestionWrap();
				subQuesWrap.setQuestion(subQuestion);
				
				List<Answer> subAnswerList = AnswerDB.getList(subQuestion.getCode(),SysqContext.getCurrentVersion().getId());
				if(subAnswerList == null || subAnswerList.size() <= 0){
					throw new RuntimeException("������[" + subQuestion.getCode() + "]û�д�");
				}
				
				List<AnswerWrap> subAnswerWrapList = new ArrayList<AnswerWrap>();
				for(Answer subAnswer : subAnswerList){
					subAnswerWrapList.add(new AnswerWrap(subAnswer));
				}
				subQuesWrap.setAnswerWrapList(subAnswerWrapList);
				
				subQuesWrapList.add(subQuesWrap);
			}
			
			questionWrap.setSubQuesWrapList(subQuesWrapList);//����������
		}
		
		return questionWrap;
	}
}
