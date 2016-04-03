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
		
		interview.setId((int)InterviewDB.save(interview));
		
		//���浽��̸������
		InterviewContext.setInterview(interview);
	}
	
	/**
	 * ��ȡ��һ������
	 * @param questionaireCode
	 * @return
	 */
	public static QuestionWrap getFirstQuestion(){
		
		//��ȡ��ǰ�ʾ�
		Questionaire curQuestionaire = InterviewContext.getCurrentQuestionaire();
		
		//��ѯ��һ������
		List<Question> questionList = QuestionDB.getList(curQuestionaire.getCode(),SysqContext.getCurrentVersion().getId());
		if(questionList == null || questionList.size() <= 0){
			throw new RuntimeException("�ʾ�[" + curQuestionaire.getCode() + "]û������");
		}
		Question firstQuestion = questionList.get(0);
		
		//��װ��questionWrap
		QuestionWrap questionWrap = wrap(firstQuestion);
				
		return questionWrap;
	}
	
	/**
	 * ��ȡ��һ������
	 * @param questionaireCode
	 * @param questionCode
	 * @return
	 */
	public static QuestionWrap getNextQuestion(){
		
		//��ȡ��ǰ�ʾ���ǰ����
		Questionaire curQuestionaire = InterviewContext.getCurrentQuestionaire();
		Question curQuestion = InterviewContext.getCurrentQuestion();
		Version curVersion = SysqContext.getCurrentVersion();
		
		//��ȡ��һ��question
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
		
		//��װ��questionWrap
		QuestionWrap questionWrap = wrap(nextQuestion);
		return questionWrap;
	}
	
	/**
	 * ��ȡָ������
	 * @param questionCode
	 * @return
	 */
	public static QuestionWrap getSpecQuestion(String questionCode){
		//��ȡ��ǰ�ʾ���ǰ�汾
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
		
		if(specQuestion == null){//ָ�����ⲻ����
			throw new RuntimeException("����[" + questionCode + "]������");
		}
		
		//��װ��questionWrap
		QuestionWrap questionWrap = wrap(specQuestion);
		return questionWrap;
		
	}
	
	/**
	 * ��ȡ��һ��
	 * @return
	 */
	public static QuestionWrap getPreviousQuestion(){
		//��ȡ��ǰ�ʾ���ǰ����
		Questionaire curQuestionaire = InterviewContext.getCurrentQuestionaire();
		Question curQuestion = InterviewContext.getCurrentQuestion();
		Version curVersion = SysqContext.getCurrentVersion();
		
		//��ȡ��һquestion
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
		
		//��װ��questionWrap
		QuestionWrap questionWrap = wrap(previousQuestion);
		return questionWrap;
	}
	
	private static QuestionWrap wrap(Question question){
		QuestionWrap questionWrap = new QuestionWrap();
		questionWrap.setQuestionaire(InterviewContext.getCurrentQuestionaire());//�����ʾ�
		
		questionWrap.setQuestion(question);//��������
		
		/**
		 * ���ô�
		 */
		List<Answer> answerList = AnswerDB.getList(question.getCode(),SysqContext.getCurrentVersion().getId());
		if(answerList == null || answerList.size() <= 0){
			throw new RuntimeException("����[" + question.getCode() + "]û�д�");
		}
		
		List<AnswerWrap> answerWrapList = new ArrayList<AnswerWrap>();
		for(Answer answer : answerList){
			answerWrapList.add(new AnswerWrap(answer));
		}
		questionWrap.setAnswerWrapList(answerWrapList);
		
		return questionWrap;
	}
	
	/**
	 * ��ȡ���б�
	 * @param answerValueMap
	 * @return
	 */
	public static ResultWrap getAnswerList(Map<String,AnswerValue> answerValueMap){
		
		ResultWrap resultWrap = new ResultWrap();
		resultWrap.setQuestionaire(InterviewContext.getCurrentQuestionaire());//�����ʾ�
		
		List<Question> questionList = QuestionDB.getList(InterviewContext.getCurrentQuestionaire().getCode(), SysqContext.getCurrentVersion().getId());
		resultWrap.setQuestionList(questionList);//������������
		
		/**
		 * ���ô�
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
	 * ��ȡ��һ���ʾ�
	 * @return
	 */
	public static Questionaire getFirstQuestionaire(){
		int type = InterviewContext.getInterview().getType();
		int versionId = SysqContext.getCurrentVersion().getId();
		
		List<Questionaire> questionaireList = QuestionaireDB.getList(versionId, type);
		
		if(questionaireList == null || questionaireList.size() <= 0){
			throw new RuntimeException("�汾�ţ�" + versionId + " �ʾ����ͣ�" + type + " ��û���ʾ�");
		}
		
		Questionaire firstQuestionaire = questionaireList.get(0);
		return firstQuestionaire;
	}
	
	/**
	 * �����
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
	 * ��ȡ��һ���ʾ�
	 * @return
	 */
	public static Questionaire getNextQuestionaire(){
		
		//��ȡ��ǰ��̸����ǰ�ʾ���ǰ�汾
		Interview curInterview = InterviewContext.getInterview();
		Questionaire curQuestionaire = InterviewContext.getCurrentQuestionaire();
		Version curVersion = SysqContext.getCurrentVersion();
		
		//��ȡ��һ���ʾ�
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
	 * �˳���̸
	 */
	public static void quitInterview(){
		Interview curInterview = InterviewContext.getInterview();
		curInterview.setStatus(Interview.STATUS_BREAK);
		curInterview.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
		InterviewDB.update(curInterview);
	}
	
	/**
	 * ��ɷ�̸
	 */
	public static void finishInterview(){
		Interview curInterview = InterviewContext.getInterview();
		curInterview.setStatus(Interview.STATUS_DONE);
		curInterview.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
		InterviewDB.update(curInterview);
	}
	
	/**
	 * ��¼��̸��ǰλ��
	 */
	public static void recordInterviewProgress(){
		
		//��ȡ��ǰ�ʾ���ǰ����
		Questionaire curQuestionaire = InterviewContext.getCurrentQuestionaire();
		Question curQuestion = InterviewContext.getCurrentQuestion();
		
		//����
		Interview curInterview = InterviewContext.getInterview();
		curInterview.setCurQuestionaireCode(curQuestionaire.getCode());
		curInterview.setNextQuestionCode(curQuestion.getCode());
		InterviewDB.update(curInterview);
	}
}
