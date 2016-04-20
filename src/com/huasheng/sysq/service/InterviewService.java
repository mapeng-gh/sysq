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
	 * �½����ʼ�¼
	 * @param interview
	 */
	public static InterviewBasic newInterviewBasic(InterviewBasic interviewBasic){
		
		//��¼�ܷ�����Ϣ������״̬��Ϣ
		interviewBasic.setInterviewerId(SysqContext.getInterviewer().getId());
		interviewBasic.setVersionId(SysqContext.getCurrentVersion().getId());
		interviewBasic.setStartTime(DateTimeUtils.getCurTime());
		interviewBasic.setStatus(InterviewBasic.STATUS_DOING);
		
		//����
		int id = InterviewBasicDB.insert(interviewBasic);
		interviewBasic.setId(id);
		
		return interviewBasic;
	}
	
	/**
	 * ���·��ʼ�¼
	 * @param interviewBasic
	 */
	public static void updateInterviewBasic(InterviewBasic interviewBasic){
		InterviewBasicDB.update(interviewBasic);
	}
	
	/**
	 * ����ʾ��¼
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
	 * �����ʾ��¼
	 * @param interviewQuestionaire
	 */
	public static void updateInterviewQuestionaire(InterviewQuestionaire interviewQuestionaire){
		InterviewQuestionaireDB.update(interviewQuestionaire);
	}
	
	/**
	 * ��ѯ�ʾ��¼
	 * @param interviewBasicId
	 * @param questionaireCode
	 * @return
	 */
	public static InterviewQuestionaire findInterviewQuestionaire(int interviewBasicId,String questionaireCode){
		List<InterviewQuestionaire> interviewQuestionaireList = InterviewQuestionaireDB.selectByInterviewBasicId(interviewBasicId, SysqContext.getCurrentVersion().getId());
		for(InterviewQuestionaire interviewQuestionaire : interviewQuestionaireList){
			if(interviewQuestionaire.getQuestionaireCode().equals(questionaireCode)){
				return interviewQuestionaire;
			}
		}
		return null;
	}
	
	/**
	 * ��ѯ�����¼�б�
	 * @param interviewBasicId
	 * @param questionaireCode
	 * @return
	 */
	public static List<InterviewQuestion> getInterviewQuestionList(int interviewBasicId,String questionaireCode){
		return InterviewQuestionDB.selectByQuestionaire(interviewBasicId, questionaireCode);
	}
	
	/**
	 * ��ѯ�𰸼�¼�б�
	 * @param interviewBasicId
	 * @param questionCode
	 * @return
	 */
	public static List<InterviewAnswer> getInterviewAnswerList(int interviewBasicId,String questionCode){
		return InterviewAnswerDB.selectByQuestion(interviewBasicId, questionCode);
	}
	
	/**
	 * ��ȡ���б�
	 * @param answerValueMap
	 * @return
	 */
	public static ResultWrap getAnswerList(List<AnswerValue> answerValueListParam){
		
		//��ȡ��ǰ�ʾ���ǰ�汾��
		InterviewQuestionaire curInterviewQuestionaire = InterviewContext.getCurInterviewQuestionaire();
		Version curVersion = SysqContext.getCurrentVersion();
		
		ResultWrap resultWrap = new ResultWrap();
		
		//�����ʾ�
		resultWrap.setQuestionaire(QuestionaireDB.selectByCode(curInterviewQuestionaire.getQuestionaireCode(), curVersion.getId()));
		
		//���������б�Ͷ�Ӧ�Ĵ�
		List<Question> showQuestionList = new ArrayList<Question>();
		Map<String,List<AnswerValue>> answerOfQuestionMap = new HashMap<String,List<AnswerValue>>();
		
		List<Question> questionList = QuestionDB.getList(curInterviewQuestionaire.getQuestionaireCode(),curVersion.getId(),Question.QUESTION_NOT_END);
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
	 * �����
	 * @param answerMap
	 */
	public static void saveAnswers(List<AnswerValue> answerValueList){
		
		//����interviewQuestion����Ҫ��¼��̸�����⣩
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
		
		//����interviewAnswer
		for(AnswerValue answerValue : answerValueList){
			InterviewAnswer interviewAnswer = new InterviewAnswer();
			interviewAnswer.setInterviewBasicId(InterviewContext.getCurInterviewBasic().getId());
			interviewAnswer.setQuestionCode(answerValue.getQuestionCode());
			interviewAnswer.setAnswerLabel(answerValue.getLabel());
			interviewAnswer.setAnswerCode(answerValue.getCode());
			interviewAnswer.setAnswerValue(answerValue.getValue());
			interviewAnswer.setAnswerText(answerValue.getText());
			interviewAnswer.setAnswerSeqNum(answerValue.getSeqNum());
			InterviewAnswerDB.insert(interviewAnswer);
		}
	}
	
	/**
	 * ��ȡ��һ������
	 * @param questionaireCode
	 * @return
	 */
	public static QuestionWrap getFirstQuestion(){
		
		//��ȡ��ǰ�ʾ���ǰ�汾��
		InterviewQuestionaire curInterviewQuestionaire = InterviewContext.getCurInterviewQuestionaire();
		Version curVersion = SysqContext.getCurrentVersion();
		
		//��ѯ��һ������
		List<Question> questionList = QuestionDB.getList(curInterviewQuestionaire.getQuestionaireCode(),curVersion.getId(),Question.QUESTION_NOT_END);
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
		InterviewQuestionaire curInterviewQuestionaire = InterviewContext.getCurInterviewQuestionaire();
		Version curVersion = SysqContext.getCurrentVersion();
		Question curQuestion = InterviewContext.getTopQuestion();
		
		//��ȡ��һquestion
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
		
		//��ȡ��ǰ�ʾ���ǰ�汾�š���ǰ����
		InterviewQuestionaire curInterviewQuestionaire = InterviewContext.getCurInterviewQuestionaire();
		Version curVersion = SysqContext.getCurrentVersion();
		Question curQuestion = InterviewContext.getTopQuestion();
		
		//��ȡ��һ��question
		Question nextQuestion = null;
		List<Question> questionList = QuestionDB.getList(curInterviewQuestionaire.getQuestionaireCode(),curVersion.getId(),Question.QUESTION_NOT_END);
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
		InterviewQuestionaire curInterviewQuestionaire = InterviewContext.getCurInterviewQuestionaire();
		Version curVersion = SysqContext.getCurrentVersion();
		
		Question specQuestion = null;
		
		//��ѯָ������
		List<Question> questionList = QuestionDB.getList(curInterviewQuestionaire.getQuestionaireCode(),curVersion.getId(),Question.QUESTION_NOT_END);
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
	 * ��ȡָ����������
	 * @param questionCode
	 * @return
	 */
	public static QuestionWrap getSpecEndQuestion(String questionCode){
		
		//��ȡ��ǰ�ʾ���ǰ�汾
		InterviewQuestionaire curInterviewQuestionaire = InterviewContext.getCurInterviewQuestionaire();
		Version curVersion = SysqContext.getCurrentVersion();
		
		Question specQuestion = null;
		
		//��ѯָ������
		List<Question> questionList = QuestionDB.getList(curInterviewQuestionaire.getQuestionaireCode(),curVersion.getId(),Question.QUESTION_END);
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
		
		//��ȡ��ǰ�ʾ���ǰ�汾��
		String curQuestionaireCode = InterviewContext.getCurInterviewQuestionaire().getQuestionaireCode();
		Version curVersion = SysqContext.getCurrentVersion();
		
		QuestionWrap questionWrap = new QuestionWrap();
		
		//�����ʾ�
		
		Questionaire curQuestionaire = QuestionaireDB.selectByCode(curQuestionaireCode,curVersion.getId());
		questionWrap.setQuestionaire(curQuestionaire);
		
		//��������
		questionWrap.setQuestion(question);
		
		//���ô�
		List<AnswerWrap> answerWrapList = new ArrayList<AnswerWrap>();
		List<Answer> answerList = AnswerDB.getList(question.getCode(),curVersion.getId());
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
		
		//��ȡ��ǰ���ʼ�¼����ǰ�汾��
		InterviewBasic curInterviewBasic = InterviewContext.getCurInterviewBasic();
		Version curVersion = SysqContext.getCurrentVersion();
		
		//��ȡ��һ���ʾ�
		Questionaire firstQuestionaire = null;
		List<Questionaire> questionaireList = QuestionaireDB.getList(curVersion.getId(),curInterviewBasic.getType());
		if(questionaireList == null || questionaireList.size() <= 0){
			throw new RuntimeException("�汾�ţ�" + curVersion.getId() + " �ʾ����ͣ�" + curInterviewBasic.getType() + " ��û���ʾ�");
		}
		firstQuestionaire = questionaireList.get(0);
		
		return firstQuestionaire;
	}
	
	/**
	 * ��ȡ��һ���ʾ�
	 * @return
	 */
	public static Questionaire getNextQuestionaire(){
		
		//��ȡ��ǰ��̸����ǰ�ʾ���ǰ�汾
		InterviewBasic curInterviewBasic = InterviewContext.getCurInterviewBasic();
		InterviewQuestionaire curInterviewQuestionaire = InterviewContext.getCurInterviewQuestionaire();
		Version curVersion = SysqContext.getCurrentVersion();
		
		//��ȡ��һ���ʾ�
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
	 * ��ȡָ���ʾ�
	 * @param questionaireCode
	 * @return
	 */
	public static Questionaire getSpecQuestionaire(String questionaireCode){
		return QuestionaireDB.selectByCode(questionaireCode,SysqContext.getCurrentVersion().getId());
	}
	
	/**
	 * ��ȡϵͳ�ʾ�ǰ�汾��
	 * @return
	 */
	public static Version getCurrentVersion(){
		return VersionDB.getCurVersion();
	}
	
}