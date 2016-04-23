package com.huasheng.sysq.model;

import java.util.ArrayList;
import java.util.List;

import com.huasheng.sysq.util.FormatUtils;

public class QuestionWrap {

	private Questionaire questionaire;
	private Question question;
	private List<AnswerWrap> answerWrapList;
	
	public QuestionWrap clone(){
		QuestionWrap newQuestionWrap = new QuestionWrap();
		
		//�ʾ�ǳ����
		newQuestionWrap.setQuestionaire(this.questionaire);
		
		//�������
		Question newQuestion = new Question();
		newQuestion.setId(this.question.getId());
		newQuestion.setCode(this.question.getCode());
		newQuestion.setDescription(this.question.getDescription());
		newQuestion.setEntryLogic(this.question.getEntryLogic());
		newQuestion.setExitLogic(this.question.getExitLogic());
		newQuestion.setIsEnd(this.question.getIsEnd());
		newQuestion.setRemark(this.question.getRemark());
		newQuestion.setQuestionaireCode(this.question.getQuestionaireCode());
		newQuestion.setSeqNum(this.question.getSeqNum());
		newQuestion.setVersionId(this.question.getVersionId());
		newQuestionWrap.setQuestion(newQuestion);
		
		//�𰸲������
		List<AnswerWrap> newAnswerWrapList = new ArrayList<AnswerWrap>();
		for(AnswerWrap answerWrap : this.answerWrapList){
			AnswerWrap newAnswerWrap  = new AnswerWrap();
			
			//answer���
			Answer newAnswer = new Answer();
			newAnswer.setId(answerWrap.getAnswer().getId());
			newAnswer.setLabel(answerWrap.getAnswer().getLabel());
			newAnswer.setCode(answerWrap.getAnswer().getCode());
			newAnswer.setType(answerWrap.getAnswer().getType());
			newAnswer.setExtra(answerWrap.getAnswer().getExtra());
			newAnswer.setShowType(answerWrap.getAnswer().getShowType());
			newAnswer.setEventType(answerWrap.getAnswer().getEventType());
			newAnswer.setEventExecute(answerWrap.getAnswer().getEventExecute());
			newAnswer.setIsShow(answerWrap.getAnswer().getIsShow());
			newAnswer.setSeqNum(answerWrap.getAnswer().getSeqNum());
			newAnswer.setQuestionCode(answerWrap.getAnswer().getQuestionCode());
			newAnswer.setVersionId(answerWrap.getAnswer().getVersionId());
			newAnswerWrap.setAnswer(newAnswer);
			
			//optionsǳ����
			newAnswerWrap.setRadioOptions(answerWrap.getRadioOptions());
			newAnswerWrap.setSliderOption(answerWrap.getSliderOption());
			newAnswerWrap.setDropDownListOptions(answerWrap.getDropDownListOptions());
			
			newAnswerWrapList.add(newAnswerWrap);
		}
		
		newQuestionWrap.setAnswerWrapList(newAnswerWrapList);
		
		return newQuestionWrap;
	}
	
	public QuestionWrap format(){
		
		QuestionWrap newQuestionWrap = this.clone();
		
		//�����������˳��߼�
		Question newQuestion = newQuestionWrap.getQuestion();
		newQuestion.setDescription(FormatUtils.handleParaInHTML(newQuestion.getDescription()));
		newQuestion.setDescription(FormatUtils.escapeQuote4JS(newQuestion.getDescription()));
		newQuestion.setExitLogic(FormatUtils.escapeQuote4HTML(newQuestion.getExitLogic()));
		
		//�𰸰��¼�
		List<AnswerWrap> newAnswerWrapList = newQuestionWrap.getAnswerWrapList();
		for(AnswerWrap newAnswerWrap : newAnswerWrapList){
			Answer newAnswer = newAnswerWrap.getAnswer();
			newAnswer.setEventExecute(FormatUtils.escapeQuote4HTML(newAnswer.getEventExecute()));
		}
		
		return newQuestionWrap;
	}
	
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public Questionaire getQuestionaire() {
		return questionaire;
	}
	public void setQuestionaire(Questionaire questionaire) {
		this.questionaire = questionaire;
	}
	public List<AnswerWrap> getAnswerWrapList() {
		return answerWrapList;
	}
	public void setAnswerWrapList(List<AnswerWrap> answerWrapList) {
		this.answerWrapList = answerWrapList;
	}
	
}
