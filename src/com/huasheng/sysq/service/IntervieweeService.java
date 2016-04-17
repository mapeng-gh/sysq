package com.huasheng.sysq.service;

import java.util.ArrayList;
import java.util.List;

import com.huasheng.sysq.db.AnswerDB;
import com.huasheng.sysq.db.InterviewAnswerDB;
import com.huasheng.sysq.db.InterviewBasicDB;
import com.huasheng.sysq.db.InterviewQuestionDB;
import com.huasheng.sysq.db.InterviewQuestionaireDB;
import com.huasheng.sysq.db.QuestionDB;
import com.huasheng.sysq.db.QuestionaireDB;
import com.huasheng.sysq.model.InterviewAnswer;
import com.huasheng.sysq.model.InterviewAnswerWrap;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.InterviewQuestion;
import com.huasheng.sysq.model.InterviewQuestionWrap;
import com.huasheng.sysq.model.InterviewQuestionaire;
import com.huasheng.sysq.model.InterviewQuestionaireWrap;
import com.huasheng.sysq.model.Page;
import com.huasheng.sysq.model.Question;
import com.huasheng.sysq.model.Questionaire;
import com.huasheng.sysq.model.Version;
import com.huasheng.sysq.util.SysqContext;

public class IntervieweeService {

	/**
	 * ������̸��¼
	 * @param searchStr
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public static Page<InterviewBasic> searchInterviewBasic(String searchStr,Integer pageNo,Integer pageSize){
		
		//������������
		InterviewBasic interview = new InterviewBasic();
		interview.setUsername(searchStr);
		interview.setDna(searchStr);
		
		//��ҳ����
		Integer offset = null;
		Integer limit = pageSize;
		offset = (pageNo - 1) * pageSize;
		
		//���ݲ�ѯ
		List<InterviewBasic> data = InterviewBasicDB.search(interview, "or", offset, limit);
		
		//����page
		Page<InterviewBasic> page = new Page<InterviewBasic>();
		page.setData(data);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		
		//������ҳ��
		int size = InterviewBasicDB.size(interview, "or");
		int totalPages = size % pageSize == 0 ? size / pageSize : size / pageSize + 1;
		page.setTotalPages(totalPages);
		
		return page;
	}
	
	/**
	 * �����ܷ��߻�����Ϣ
	 * @param id
	 * @return
	 */
	public static InterviewBasic findById(int id){
		return InterviewBasicDB.selectById(id);
	}
	
	/**
	 * �޸��ܷ��߻�����Ϣ
	 * @param interviewBasic
	 */
	public static void modifyInterviewBasic(InterviewBasic interviewBasic){
		InterviewBasicDB.update(interviewBasic);
	}
	
	/**
	 * ��ѯ�����ʾ���
	 * @param interviewBasicId
	 * @return
	 */
	public static List<InterviewQuestionaireWrap> getInterviewQuestionaireList(int interviewBasicId){
		//��ȡ��ǰ�汾
		Version curVersion = SysqContext.getCurrentVersion();
		
		//��ѯ�����ʾ�
		List<InterviewQuestionaire> interviewQuestionaireList = InterviewQuestionaireDB.selectByInterviewBasicId(interviewBasicId, curVersion.getId());
		
		//��װ�ʾ�
		List<InterviewQuestionaireWrap> interviewQuestionaireWrapList = new ArrayList<InterviewQuestionaireWrap>();
		for(InterviewQuestionaire interviewQuestionaire : interviewQuestionaireList){
			Questionaire questionaire = QuestionaireDB.selectByCode(interviewQuestionaire.getQuestionaireCode(), curVersion.getId());
			interviewQuestionaireWrapList.add(new InterviewQuestionaireWrap(interviewQuestionaire,questionaire));
		}
		
		return interviewQuestionaireWrapList;
	}
	
	/**
	 * ��ȡ�������⼯��
	 * @param interviewBasicId
	 * @param questionaireCode
	 * @return
	 */
	public static List<InterviewQuestionWrap> getInterviewQuestionList(int interviewBasicId,String questionaireCode){
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
}
