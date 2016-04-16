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
	 * ������ʻ�����Ϣ
	 * @param interview
	 */
	public static void addInterviewBasic(InterviewBasic interviewBasic){
		
		//��¼������Id
		interviewBasic.setInterviewerId(SysqContext.getInterviewer().getId());
		
		//��¼��ʼʱ��
		interviewBasic.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
		
		//��¼��̸״̬
		interviewBasic.setStatus(InterviewBasic.STATUS_DOING);
		
		//��¼��ǰ�汾
		interviewBasic.setVersionId(SysqContext.getCurrentVersion().getId());
		
		interviewBasic.setId((int)InterviewBasicDB.save(interviewBasic));
	}
	
	/**
	 * ��ȡ���б�
	 * @param answerValueMap
	 * @return
	 */
	public static ResultWrap getAnswerList(List<AnswerValue> answerValueListParam){
		
		ResultWrap resultWrap = new ResultWrap();
		
		//�����ʾ�
		resultWrap.setQuestionaire(InterviewContext.getCurQuestionaire());
		
		//���������б�Ͷ�Ӧ�Ĵ�
		List<Question> showQuestionList = new ArrayList<Question>();
		Map<String,List<AnswerValue>> answerOfQuestionMap = new HashMap<String,List<AnswerValue>>();
		
		List<Question> questionList = QuestionDB.getList(InterviewContext.getCurQuestionaire().getCode(), SysqContext.getCurrentVersion().getId(),Question.QUESTION_NOT_END);
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
	 * ��������ʾ�
	 * @param answerMap
	 */
	public static void saveInterviewQuestionaire(List<AnswerValue> answerValueList){
		
		//����interviewQuestionaire����Ҫ��¼ʱ�䣩
		InterviewQuestionaire interviewQuestionaire = new InterviewQuestionaire();
		interviewQuestionaire.setInterviewBasicId(InterviewContext.getInterviewBasic().getId());
		interviewQuestionaire.setQuestionaireCode(InterviewContext.getCurQuestionaire().getCode());
		interviewQuestionaire.setStartTime(InterviewContext.getQuestionaireStartTime());
		interviewQuestionaire.setEndTime(DateTimeUtils.getCurTime());
		interviewQuestionaire.setVersionId(SysqContext.getCurrentVersion().getId());
		InterviewQuestionaireDB.insert(interviewQuestionaire);
		
		//����interviewQuestion����Ҫ��¼��̸�����⣩
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
		
		//����interviewAnswer
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
	 * ��ȡ��һ������
	 * @param questionaireCode
	 * @return
	 */
	public static QuestionWrap getFirstQuestion(){
		
		//��ȡ��ǰ�ʾ�
		Questionaire curQuestionaire = InterviewContext.getCurQuestionaire();
		
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
	 * ��ȡ��һ��
	 * @return
	 */
	public static QuestionWrap getPreviousQuestion(){
		
		//��ȡ��ǰ�ʾ���ǰ�汾����ǰ����
		Questionaire curQuestionaire = InterviewContext.getCurQuestionaire();
		Version curVersion = SysqContext.getCurrentVersion();
		Question curQuestion = InterviewContext.getCurQuestion();
		
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
	
	/**
	 * ��ȡ��һ������
	 * @param questionaireCode
	 * @param questionCode
	 * @return
	 */
	public static QuestionWrap getNextQuestion(){
		
		//��ȡ��ǰ�ʾ���ǰ����
		Questionaire curQuestionaire = InterviewContext.getCurQuestionaire();
		Version curVersion = SysqContext.getCurrentVersion();
		Question curQuestion = InterviewContext.getCurQuestion();
		
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
		
		if(specQuestion == null){//ָ�����ⲻ����
			throw new RuntimeException("����[" + questionCode + "]������");
		}
		
		//��װ��questionWrap
		QuestionWrap questionWrap = wrap(specQuestion);
		return questionWrap;
		
	}
	
	public static QuestionWrap wrap(Question question){
		
		//�����ʾ�
		QuestionWrap questionWrap = new QuestionWrap();
		questionWrap.setQuestionaire(InterviewContext.getCurQuestionaire());
		
		//��������
		questionWrap.setQuestion(question);
		
		//���ô�
		List<AnswerWrap> answerWrapList = new ArrayList<AnswerWrap>();
		List<Answer> answerList = AnswerDB.getList(question.getCode(),SysqContext.getCurrentVersion().getId());
		for(Answer answer : answerList){
			answerWrapList.add(new AnswerWrap(answer));
		}
		questionWrap.setAnswerWrapList(answerWrapList);
		
		return questionWrap;
	}
	
	
	
	/**
	 * ��ȡ��һ���ʾ�
	 * @return
	 */
	public static Questionaire getFirstQuestionaire(){
		int type = InterviewContext.getInterviewBasic().getType();
		int versionId = SysqContext.getCurrentVersion().getId();
		
		List<Questionaire> questionaireList = QuestionaireDB.getList(versionId, type);
		
		if(questionaireList == null || questionaireList.size() <= 0){
			throw new RuntimeException("�汾�ţ�" + versionId + " �ʾ����ͣ�" + type + " ��û���ʾ�");
		}
		
		Questionaire firstQuestionaire = questionaireList.get(0);
		return firstQuestionaire;
	}
	
	/**
	 * ��ȡ��һ���ʾ�
	 * @return
	 */
	public static Questionaire getNextQuestionaire(){
		
		//��ȡ��ǰ��̸����ǰ�ʾ���ǰ�汾
		InterviewBasic curInterview = InterviewContext.getInterviewBasic();
		Questionaire curQuestionaire = InterviewContext.getCurQuestionaire();
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
	 * ���·�̸״̬
	 * @param interviewStatus
	 */
	public static void updateInterviewStatus(Integer interviewStatus){
		InterviewBasic curInterview = InterviewContext.getInterviewBasic();
		curInterview.setStatus(interviewStatus);
		curInterview.setLastModifiedTime(DateTimeUtils.getCurTime());
		InterviewBasicDB.update(curInterview);
	}
	
	/**
	 * ��¼��̸��ǰλ��
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
	 * ��ȡϵͳ�ʾ�ǰ�汾��
	 * @return
	 */
	public static Version getCurrentVersion(){
		return VersionDB.getCurVersion();
	}
	
}
