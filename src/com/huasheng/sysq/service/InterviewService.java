package com.huasheng.sysq.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huasheng.sysq.db.AnswerDB;
import com.huasheng.sysq.db.InterviewDB;
import com.huasheng.sysq.db.QuestionDB;
import com.huasheng.sysq.db.QuestionaireDB;
import com.huasheng.sysq.db.ResultDB;
import com.huasheng.sysq.db.VersionDB;
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
		List<Question> questionList = QuestionDB.getList(curQuestionaire.getCode(),SysqContext.getCurrentVersion().getId(),Question.QUESTION_NOT_END);
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
		List<Question> questionList = QuestionDB.getList(curQuestionaire.getCode(),curVersion.getId(),Question.QUESTION_NOT_END);
		for(int i=0;i<questionList.size();i++){
			if(questionList.get(i).getCode().equals(curQuestion.getCode())){
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
		
		List<Question> questionList = QuestionDB.getList(curQuestionaire.getCode(),curVersion.getId(),Question.QUESTION_NOT_END);
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
		
		//��װ��questionWrap
		QuestionWrap questionWrap = wrap(previousQuestion);
		return questionWrap;
	}
	
	public static QuestionWrap wrap(Question question){
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
	public static ResultWrap getAnswerList(List<AnswerValue> answerValueListParam){
		
		ResultWrap resultWrap = new ResultWrap();
		
		//�����ʾ�
		resultWrap.setQuestionaire(InterviewContext.getCurrentQuestionaire());
		
		//���������б�Ͷ�Ӧ�Ĵ�
		List<Question> showQuestionList = new ArrayList<Question>();
		Map<String,List<AnswerValue>> answerOfQuestionMap = new HashMap<String,List<AnswerValue>>();
		
		List<Question> questionList = QuestionDB.getList(InterviewContext.getCurrentQuestionaire().getCode(), SysqContext.getCurrentVersion().getId(),Question.QUESTION_NOT_END);
		for(Question question : questionList){
			
			//���������Ӧ�����д�
			List<AnswerValue> answerValueList = new ArrayList<AnswerValue>();
			for(AnswerValue answerValueParam : answerValueListParam){
				if(answerValueParam.getQuestionCode().equals(question.getCode())){
					answerValueList.add(answerValueParam);
				}
			}
			
			//���������ⲻ��ʾ�ڴ��б�
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
	public static void saveAnswers(List<AnswerValue> answerValueList){
		
		for(AnswerValue answerValue : answerValueList){
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
	
	/**
	 * ��ȡ��������
	 * @param endQuestionCode
	 * @return
	 */
	public static QuestionWrap getEndQuestion(String endQuestionCode){
		
		//��ȡ��ǰ�汾���ʾ�
		Version curVersion = SysqContext.getCurrentVersion();
		Questionaire curQuestionaire = InterviewContext.getCurrentQuestionaire();
		
		//��ѯ��������
		Question endQuestion = null;
		List<Question> endQuestionList = QuestionDB.getList(curQuestionaire.getCode(), curVersion.getId(),Question.QUESTION_END);
		if(endQuestionList == null || endQuestionList.size() <= 0){
			throw new RuntimeException("�ʾ�[" + curQuestionaire.getCode() + "]û�н�������");
		}
		for(Question question : endQuestionList){
			if(question.getCode().equals(endQuestionCode)){
				endQuestion = question;
				break;
			}
		}
		
		//��װ����
		QuestionWrap endQuestionWrap = new QuestionWrap();
		endQuestionWrap.setQuestionaire(curQuestionaire);
		endQuestionWrap.setQuestion(endQuestion);
		
		return endQuestionWrap;
	}
	
	/**
	 * ��ȡϵͳ�ʾ�ǰ�汾��
	 * @return
	 */
	public static Version getCurrentVersion(){
		return VersionDB.getCurVersion();
	}
}
