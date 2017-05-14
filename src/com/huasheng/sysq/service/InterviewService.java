package com.huasheng.sysq.service;

import java.util.ArrayList;
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
import com.huasheng.sysq.db.IntervieweeDB;
import com.huasheng.sysq.db.InterviewerDB;
import com.huasheng.sysq.db.QuestionDB;
import com.huasheng.sysq.db.QuestionaireDB;
import com.huasheng.sysq.db.VersionDB;
import com.huasheng.sysq.model.Answer;
import com.huasheng.sysq.model.AnswerValue;
import com.huasheng.sysq.model.AnswerWrap;
import com.huasheng.sysq.model.InterviewAnswer;
import com.huasheng.sysq.model.InterviewAnswerWrap;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.InterviewBasicWrap;
import com.huasheng.sysq.model.InterviewQuestion;
import com.huasheng.sysq.model.InterviewQuestionWrap;
import com.huasheng.sysq.model.InterviewQuestionaire;
import com.huasheng.sysq.model.InterviewQuestionaireWrap;
import com.huasheng.sysq.model.Interviewee;
import com.huasheng.sysq.model.Interviewer;
import com.huasheng.sysq.model.Page;
import com.huasheng.sysq.model.Question;
import com.huasheng.sysq.model.QuestionWrap;
import com.huasheng.sysq.model.Questionaire;
import com.huasheng.sysq.model.ResultWrap;
import com.huasheng.sysq.model.Version;
import com.huasheng.sysq.util.CommonUtils;
import com.huasheng.sysq.util.SysqContext;
import com.huasheng.sysq.util.interview.InterviewContext;
import com.huasheng.sysq.util.upload.UploadConstants;

public class InterviewService {
	
	/**
	 * 新建被访问者
	 * @param interviewee
	 * @return
	 */
	public static Interviewee newInterviewee(Interviewee interviewee){
		IntervieweeDB.insert(interviewee);
		return interviewee;
	}
	
	/**
	 * 更新被访问者
	 * @param interviewee
	 */
	public static void updateInterviewee(Interviewee interviewee){
		IntervieweeDB.update(interviewee);
	}
	
	/**
	 * 查询所有被访问者
	 * @return
	 */
	public static  List<Interviewee> getAllInterviewee(){
		List<Interviewee> intervieweeList = new ArrayList<Interviewee>();
		intervieweeList = IntervieweeDB.getList();
		return intervieweeList;
	}

	/**
	 * 新建访问记录
	 * @param interview
	 */
	public static InterviewBasic newInterviewBasic(InterviewBasic interviewBasic){
		InterviewBasicDB.insert(interviewBasic);
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
	 * 获取唯一访问记录
	 * @param id
	 * @return
	 */
	public static InterviewBasicWrap findInterviewBasicById(int id){
		
		InterviewBasic interviewBasic = InterviewBasicDB.selectById(id);
		Interviewee interviewee = IntervieweeDB.selectById(interviewBasic.getIntervieweeId());
		Interviewer interviewer = InterviewerDB.selectById(interviewBasic.getInterviewerId());
		
		InterviewBasicWrap interviewBasicWrap = new InterviewBasicWrap();
		interviewBasicWrap.setInterviewBasic(interviewBasic);
		interviewBasicWrap.setInterviewee(interviewee);
		interviewBasicWrap.setInterviewer(interviewer);
		
		return interviewBasicWrap;
	}
	
	/**
	 * 获取所有访谈记录
	 * @return
	 */
	public static List<InterviewBasicWrap> getAllInterviewBasic(){
		List<InterviewBasic> interviewBasicList = InterviewBasicDB.getList(SysqContext.getCurrentVersion().getId());
		List<InterviewBasicWrap> interviewBasicWrapList = new ArrayList<InterviewBasicWrap>();
		if(interviewBasicList != null && interviewBasicList.size() > 0){
			for(InterviewBasic interviewBasic : interviewBasicList){
				InterviewBasicWrap interviewBasicWrap = new InterviewBasicWrap();
				Interviewee interviewee = IntervieweeDB.selectById(interviewBasic.getIntervieweeId());
				Interviewer interviewer = InterviewerDB.selectById(interviewBasic.getInterviewerId());
				interviewBasicWrap.setInterviewBasic(interviewBasic);
				interviewBasicWrap.setInterviewee(interviewee);
				interviewBasicWrap.setInterviewer(interviewer);
				interviewBasicWrapList.add(interviewBasicWrap);
			}
		}
		return interviewBasicWrapList;
	}
	
	/**
	 * 搜索访谈记录
	 * @param searchStr
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public static Page<InterviewBasicWrap> searchInterviewBasic(String searchStr,Integer pageNo,Integer pageSize){
		
		//分页对象
		Page<InterviewBasicWrap> page = new Page<InterviewBasicWrap>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		
		List<InterviewBasic> interviewBasicList = InterviewBasicDB.getList(SysqContext.getCurrentVersion().getId());
		
		//无数据
		if(interviewBasicList == null || interviewBasicList.size() <= 0){
			page.setData(null);
			page.setTotalPages(0);
			return page;
		}
		
		//转化wrap
		List<InterviewBasicWrap> interviewBasicWrapList = new ArrayList<InterviewBasicWrap>();
		for(InterviewBasic interviewBasic : interviewBasicList){
			InterviewBasicWrap interviewBasicWrap = new InterviewBasicWrap();
			interviewBasicWrap.setInterviewBasic(interviewBasic);
			interviewBasicWrap.setInterviewee(IntervieweeDB.selectById(interviewBasic.getIntervieweeId()));
			interviewBasicWrap.setInterviewer(InterviewerDB.selectById(interviewBasic.getInterviewerId()));
			interviewBasicWrapList.add(interviewBasicWrap);
		}
		
		//过滤
		List<InterviewBasicWrap> data =  new ArrayList<InterviewBasicWrap>();
		if(StringUtils.isEmpty(searchStr)){
			data.addAll(interviewBasicWrapList);
		}else{
			for(InterviewBasicWrap interviewBasicWrap : interviewBasicWrapList){
				if(interviewBasicWrap.getInterviewee().getUsername().contains(searchStr)){//姓名
					data.add(interviewBasicWrap);
					continue;
				}
				String dnas = interviewBasicWrap.getInterviewee().getDna();//DNA
				if(!StringUtils.isEmpty(dnas)){
					if(dnas.contains(",")){
						String[] dnaArray = dnas.split(",");
						for(String dna : dnaArray){
							if(dna.contains(searchStr)){
								data.add(interviewBasicWrap);
								break;
							}
						}
					}else{
						if(dnas.contains(searchStr)){
							data.add(interviewBasicWrap);
							continue;
						}
					}
				}
			}
		}
		
		//分页
		int start =  (pageNo - 1) * pageSize;
		int end = pageNo * pageSize > data.size() ? data.size() : (start + pageSize);
		int totalPages = data.size() % pageSize == 0 ? data.size() / pageSize : (data.size() / pageSize) + 1;
		data = data.subList(start, end);
		page.setData(data);
		page.setTotalPages(totalPages);
		
		return page;
	}
	
	/**
	 * 添加问卷记录
	 * @param questionaire
	 * @return
	 */
	public static InterviewQuestionaire newInterviewQuestionaire(Questionaire questionaire){
		
		InterviewQuestionaire interviewQuestionaire = new InterviewQuestionaire();
		interviewQuestionaire.setInterviewBasicId(InterviewContext.getCurInterviewBasicWrap().getInterviewBasic().getId());
		interviewQuestionaire.setQuestionaireCode(questionaire.getCode());
		interviewQuestionaire.setStartTime(CommonUtils.getCurDateTime());
		interviewQuestionaire.setLastModifiedTime(CommonUtils.getCurDateTime());
		interviewQuestionaire.setStatus(InterviewQuestionaire.STATUS_DOING);
		interviewQuestionaire.setSeqNum(questionaire.getSeqNum());
		interviewQuestionaire.setVersionId(SysqContext.getCurrentVersion().getId());
		int id = InterviewQuestionaireDB.insert(interviewQuestionaire);
		interviewQuestionaire.setId(id);
		
		return interviewQuestionaire;
	}
	
	/**
	 * 删除访问记录
	 * @param questionaireCode
	 */
	public static void deleteInterviewQuestionaire(String questionaireCode){
		InterviewQuestionaireDB.delete(InterviewContext.getCurInterviewBasicWrap().getInterviewBasic().getId(), questionaireCode);
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
		List<InterviewQuestionaire> interviewQuestionaireList = InterviewQuestionaireDB.selectByInterviewBasicId(interviewBasicId);
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
		List<InterviewQuestionaire> interviewQuestionaireList = InterviewQuestionaireDB.selectByInterviewBasicId(interviewBasicId);
		
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
	 * 获取答案记录列表
	 * @param interviewBasicId
	 * @return
	 */
	public static List<InterviewAnswer> getInterviewAnswerList(int interviewBasicId){
		return InterviewAnswerDB.selectByInterview(interviewBasicId);
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
	public static InterviewAnswer getInterviewAnswer(int interviewBasicId,String answerCode){
		
		//查询
		List<InterviewAnswer> interviewAnswerList = InterviewAnswerDB.selectByInterview(interviewBasicId);
		for(InterviewAnswer interviewAnswer : interviewAnswerList){
			if(interviewAnswer.getAnswerCode().equals(answerCode)){
				return interviewAnswer;
			}
		}
		return null;
	}
	
	/**
	 * 删除指定访问问题之后所有问卷记录、问题记录、答案记录
	 * @param interviewBasicId
	 * @param questionaireCode
	 * @param questionCode
	 */
	public static void clearInterviewAfterQuestion(int interviewBasicId,String questionaireCode,String questionCode){
		
		//删除本问卷之后所有访问记录
		boolean isStart = false;
		List<InterviewQuestionaire> interviewQuestionaireList = InterviewQuestionaireDB.selectByInterviewBasicId(interviewBasicId);
		for(InterviewQuestionaire interviewQuestionaire : interviewQuestionaireList){
			if(interviewQuestionaire.getQuestionaireCode().equals(questionaireCode)){
				isStart = true;
			}else{
				if(isStart){//开始删除
					List<InterviewQuestion> interviewQuestionList = InterviewQuestionDB.selectByQuestionaire(interviewBasicId, interviewQuestionaire.getQuestionaireCode());
					for(InterviewQuestion interviewQuestion : interviewQuestionList){
						InterviewAnswerDB.deleteByInterviewQuestion(interviewBasicId, interviewQuestion.getQuestionCode());//批量删除访问答案
					}
					InterviewQuestionDB.deleteByInterviewQuestionaire(interviewBasicId, interviewQuestionaire.getQuestionaireCode());//批量删除访问问题
					InterviewQuestionaireDB.delete(interviewBasicId, interviewQuestionaire.getQuestionaireCode());//删除问卷记录
				}
			}
		}
		
		//删除本问卷该问题之后（包括本问题）的访问问题、访问答案
		isStart = false;
		List<InterviewQuestion> interviewQuestionList = InterviewQuestionDB.selectByQuestionaire(interviewBasicId, questionaireCode);
		for(InterviewQuestion interviewQuestion : interviewQuestionList){
			if(interviewQuestion.getQuestionCode().equals(questionCode)){
				isStart = true;
			}
			if(isStart){
				InterviewAnswerDB.deleteByInterviewQuestion(interviewBasicId, interviewQuestion.getQuestionCode());//批量删除访问答案
				InterviewQuestionDB.delete(interviewBasicId, interviewQuestion.getQuestionCode());//删除访问答案
			}
		}
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
		InterviewBasic interviewBasic = InterviewContext.getCurInterviewBasicWrap().getInterviewBasic();
		InterviewQuestionaire interviewQuestionaire = InterviewContext.getCurInterviewQuestionaire();
		
		List<InterviewQuestion> interviewQuestionList = InterviewQuestionDB.selectByQuestionaire(interviewBasic.getId(), interviewQuestionaire.getQuestionaireCode());
		InterviewQuestionDB.deleteByInterviewQuestionaire(interviewBasic.getId(),interviewQuestionaire.getQuestionaireCode());
		for(InterviewQuestion interviewQuestion : interviewQuestionList){
			InterviewAnswerDB.deleteByInterviewQuestion(interviewBasic.getId(),interviewQuestion.getQuestionCode());
		}
		
		//保存interviewQuestion（主要记录访谈的问题）
		Set<String> questionCodeSet = new HashSet<String>();
		for(AnswerValue answerValue : answerValueList){
			questionCodeSet.add(answerValue.getQuestionCode());
		}
		for(String questionCode : questionCodeSet){
			InterviewQuestion interviewQuestion = new InterviewQuestion();
			interviewQuestion.setInterviewBasicId(InterviewContext.getCurInterviewBasicWrap().getInterviewBasic().getId());
			interviewQuestion.setQuestionaireCode(InterviewContext.getCurInterviewQuestionaire().getQuestionaireCode());
			interviewQuestion.setQuestionCode(questionCode);
			interviewQuestion.setSeqNum(QuestionDB.selectByCode(questionCode, SysqContext.getCurrentVersion().getId()).getSeqNum());
			interviewQuestion.setVersionId(SysqContext.getCurrentVersion().getId());
			InterviewQuestionDB.insert(interviewQuestion);
		}
		
		//保存interviewAnswer
		for(AnswerValue answerValue : answerValueList){
			InterviewAnswer interviewAnswer = new InterviewAnswer();
			interviewAnswer.setInterviewBasicId(InterviewContext.getCurInterviewBasicWrap().getInterviewBasic().getId());
			interviewAnswer.setQuestionCode(answerValue.getQuestionCode());
			interviewAnswer.setAnswerLabel(answerValue.getLabel());
			interviewAnswer.setAnswerCode(answerValue.getCode());
			interviewAnswer.setAnswerValue(answerValue.getValue());
			interviewAnswer.setAnswerText(answerValue.getText());
			interviewAnswer.setAnswerSeqNum(answerValue.getSeqNum());
			interviewAnswer.setVersionId(SysqContext.getCurrentVersion().getId());
			InterviewAnswerDB.insert(interviewAnswer);
		}
	}
	
	/**
	 * 更新单个问题答案（修改无关联问题）
	 * @param answerValue
	 */
	public static void updateSingleQuestionAnswers(List<AnswerValue> answerValueList,String remark){
		
		//先删除答案
		String questionCode = answerValueList.get(0).getQuestionCode();
		InterviewAnswerDB.deleteByInterviewQuestion(InterviewContext.getCurInterviewBasicWrap().getInterviewBasic().getId(), questionCode);
		
		//后添加答案
		for(AnswerValue answerValue : answerValueList){
			InterviewAnswer interviewAnswer = new InterviewAnswer();
			interviewAnswer.setInterviewBasicId(InterviewContext.getCurInterviewBasicWrap().getInterviewBasic().getId());
			interviewAnswer.setQuestionCode(answerValue.getQuestionCode());
			interviewAnswer.setAnswerLabel(answerValue.getLabel());
			interviewAnswer.setAnswerCode(answerValue.getCode());
			interviewAnswer.setAnswerValue(answerValue.getValue());
			interviewAnswer.setAnswerText(answerValue.getText());
			interviewAnswer.setAnswerSeqNum(answerValue.getSeqNum());
			interviewAnswer.setVersionId(SysqContext.getCurrentVersion().getId());
			InterviewAnswerDB.insert(interviewAnswer);
		}
		
		//插入问卷备注信息
		InterviewQuestionaire interviewQuestionaire = InterviewQuestionaireDB.select(InterviewContext.getCurInterviewBasicWrap().getInterviewBasic().getId(), InterviewContext.getCurInterviewQuestionaire().getQuestionaireCode());
		interviewQuestionaire.setRemark(remark);
		interviewQuestionaire.setLastModifiedTime(CommonUtils.getCurDateTime());
		InterviewQuestionaireDB.update(interviewQuestionaire);
		
		//更新上传状态
		InterviewBasic interviewBasic = InterviewContext.getCurInterviewBasicWrap().getInterviewBasic();
		if(interviewBasic.getUploadStatus() == UploadConstants.upload_status_uploaded){
			interviewBasic.setUploadStatus(UploadConstants.upload_status_modified);
			InterviewService.updateInterviewBasic(interviewBasic);
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
	
	/**
	 * 获取问题列表
	 * @return
	 */
	public static List<Question> getQuestionList(){
		
		List<Question> questionList = QuestionDB.getList(InterviewContext.getCurInterviewQuestionaire().getQuestionaireCode(),SysqContext.getCurrentVersion().getId(),Question.QUESTION_NOT_END);
		return questionList;
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
		InterviewBasic curInterviewBasic = InterviewContext.getCurInterviewBasicWrap().getInterviewBasic();
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
		InterviewBasic curInterviewBasic = InterviewContext.getCurInterviewBasicWrap().getInterviewBasic();
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
	 * 获取当前问卷版本
	 * @return
	 */
	public static Version getCurInterviewVersion(){
		List<Version> versionList = VersionDB.select();
		if(versionList == null || versionList.size() <= 0){
			return null;
		}
		for(Version version : versionList){
			if(version.getIsCurrent() == 1){
				return version;
			}
		}
		return null;
	}
	
	/**
	 * 更新当前版本
	 * @param newVersionId
	 */
	public static void updateCurInterviewVersion(int newVersionId){
		List<Version> versionList = VersionDB.select();
		if(versionList != null && versionList.size() > 0){
			for(Version version : versionList){
				if(version.getIsCurrent() == 1){
					version.setIsCurrent(0);
					VersionDB.update(version);
				}
				if(version.getId() == newVersionId){
					version.setIsCurrent(1);
					VersionDB.update(version);
				}
			}
		}
	}
}