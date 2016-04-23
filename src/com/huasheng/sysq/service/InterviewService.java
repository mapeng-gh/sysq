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
import com.huasheng.sysq.model.InterviewAnswerWrap;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.InterviewQuestion;
import com.huasheng.sysq.model.InterviewQuestionWrap;
import com.huasheng.sysq.model.InterviewQuestionaire;
import com.huasheng.sysq.model.InterviewQuestionaireWrap;
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
	 * 搜索访谈记录
	 * @param searchStr
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public static Page<InterviewBasic> searchInterviewBasic(String searchStr,Integer pageNo,Integer pageSize){
		
		//构造搜索对象
		InterviewBasic interview = new InterviewBasic();
		interview.setUsername(searchStr);
		interview.setDna(searchStr);
		
		//分页计算
		Integer offset = null;
		Integer limit = pageSize;
		offset = (pageNo - 1) * pageSize;
		
		//数据查询
		List<InterviewBasic> data = InterviewBasicDB.search(interview, "or", offset, limit);
		
		//构造page
		Page<InterviewBasic> page = new Page<InterviewBasic>();
		page.setData(data);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		
		//计算总页数
		int size = InterviewBasicDB.size(interview, "or");
		int totalPages = size % pageSize == 0 ? size / pageSize : size / pageSize + 1;
		page.setTotalPages(totalPages);
		
		return page;
	}
	
	/**
	 * 获取唯一访问记录
	 * @param id
	 * @return
	 */
	public static InterviewBasic findInterviewBasicById(int id){
		return InterviewBasicDB.selectById(id);
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
	 * 查询唯一问卷记录
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
	 * 查询问卷记录列表
	 * @param interviewBasicId
	 * @return
	 */
	public static List<InterviewQuestionaireWrap> getInterviewQuestionaireList(int interviewBasicId){
		//获取当前版本
		Version curVersion = SysqContext.getCurrentVersion();
		
		//查询访问问卷
		List<InterviewQuestionaire> interviewQuestionaireList = InterviewQuestionaireDB.selectByInterviewBasicId(interviewBasicId, curVersion.getId());
		
		//包装问卷
		List<InterviewQuestionaireWrap> interviewQuestionaireWrapList = new ArrayList<InterviewQuestionaireWrap>();
		for(InterviewQuestionaire interviewQuestionaire : interviewQuestionaireList){
			Questionaire questionaire = QuestionaireDB.selectByCode(interviewQuestionaire.getQuestionaireCode(), curVersion.getId());
			interviewQuestionaireWrapList.add(new InterviewQuestionaireWrap(interviewQuestionaire,questionaire));
		}
		
		return interviewQuestionaireWrapList;
	}
	
	/**
	 * 查询问题记录列表
	 * @param interviewBasicId
	 * @param questionaireCode
	 * @return
	 */
	public static List<InterviewQuestion> getInterviewQuestionList(int interviewBasicId,String questionaireCode){
		return InterviewQuestionDB.selectByQuestionaire(interviewBasicId, questionaireCode);
	}
	
	/**
	 * 查询问题记录列表（包装）
	 * @param interviewBasicId
	 * @param questionaireCode
	 * @return
	 */
	public static List<InterviewQuestionWrap> getWrapInterviewQuestionList(int interviewBasicId,String questionaireCode){
		List<InterviewQuestionWrap> interviewQuestionWrapList = new ArrayList<InterviewQuestionWrap>();
		
		List<InterviewQuestion> interviewQuestionList = InterviewQuestionDB.selectByQuestionaire(interviewBasicId, questionaireCode);
		for(InterviewQuestion interviewQuestion : interviewQuestionList){
			InterviewQuestionWrap interviewQuestionWrap = new InterviewQuestionWrap();
			
			Question question = QuestionDB.selectByCode(interviewQuestion.getQuestionCode(), SysqContext.getCurrentVersion().getId());
			interviewQuestionWrap.setQuestion(question);
			
			List<InterviewAnswerWrap> interviewAnswerWrapList = new ArrayList<InterviewAnswerWrap>();
			List<InterviewAnswer> interviewAnswerList = InterviewAnswerDB.selectByQuestion(interviewBasicId, interviewQuestion.getQuestionCode());
			for(InterviewAnswer interviewAnswer : interviewAnswerList){
				InterviewAnswerWrap interviewAnswerWrap = new InterviewAnswerWrap();
				interviewAnswerWrap.setInterviewAnswer(interviewAnswer);
				interviewAnswerWrap.setAnswer(AnswerDB.selectByCode(interviewAnswer.getAnswerCode(), SysqContext.getCurrentVersion().getId()));
				interviewAnswerWrapList.add(interviewAnswerWrap);
			}
			interviewQuestionWrap.setAnswerWrapList(interviewAnswerWrapList);
			
			interviewQuestionWrapList.add(interviewQuestionWrap);
		}
		
		return interviewQuestionWrapList;
		
	}
	
	/**
	 * 查询答案记录列表
	 * @param interviewBasicId
	 * @param questionCode
	 * @return
	 */
	public static List<InterviewAnswer> getInterviewAnswerList(int interviewBasicId,String questionCode){
		return InterviewAnswerDB.selectByQuestion(interviewBasicId, questionCode);
	}
	
	/**
	 * 获取答案记录
	 * @param answerCode
	 * @return
	 */
	public static InterviewAnswer getInterviewAnswer(String answerCode){
		//获取当前访问记录
		InterviewBasic curInterviewBasic = InterviewContext.getCurInterviewBasic();
		
		//查询
		List<InterviewAnswer> interviewAnswerList = InterviewAnswerDB.selectByInterview(curInterviewBasic.getId());
		for(InterviewAnswer interviewAnswer : interviewAnswerList){
			if(interviewAnswer.getAnswerCode().equals(answerCode)){
				return interviewAnswer;
			}
		}
		return null;
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
		
		//清除访问问卷下所有访问问题、访问答案
		InterviewBasic interviewBasic = InterviewContext.getCurInterviewBasic();
		InterviewQuestionDB.deleteByInterviewBasic(interviewBasic.getId());
		InterviewAnswerDB.deleteByInterviewBasic(interviewBasic.getId());
		
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
			interviewAnswer.setAnswerLabel(answerValue.getLabel());
			interviewAnswer.setAnswerCode(answerValue.getCode());
			interviewAnswer.setAnswerValue(answerValue.getValue());
			interviewAnswer.setAnswerText(answerValue.getText());
			interviewAnswer.setAnswerSeqNum(answerValue.getSeqNum());
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
		Question curQuestion = InterviewContext.getTopQuestion();
		
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
		Question curQuestion = InterviewContext.getTopQuestion();
		
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
	
	/**
	 * 获取指定结束问题
	 * @param questionCode
	 * @return
	 */
	public static QuestionWrap getSpecEndQuestion(String questionCode){
		
		//获取当前问卷、当前版本
		InterviewQuestionaire curInterviewQuestionaire = InterviewContext.getCurInterviewQuestionaire();
		Version curVersion = SysqContext.getCurrentVersion();
		
		Question specQuestion = null;
		
		//查询指定问题
		List<Question> questionList = QuestionDB.getList(curInterviewQuestionaire.getQuestionaireCode(),curVersion.getId(),Question.QUESTION_END);
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
	 * 获取指定问卷
	 * @param questionaireCode
	 * @return
	 */
	public static Questionaire getSpecQuestionaire(String questionaireCode){
		return QuestionaireDB.selectByCode(questionaireCode,SysqContext.getCurrentVersion().getId());
	}
	
	/**
	 * 获取系统问卷当前版本号
	 * @return
	 */
	public static Version getCurrentVersion(){
		return VersionDB.getCurVersion();
	}
	
}