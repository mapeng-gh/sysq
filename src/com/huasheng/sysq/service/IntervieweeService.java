package com.huasheng.sysq.service;

import java.util.ArrayList;
import java.util.List;

import com.huasheng.sysq.db.InterviewBasicDB;
import com.huasheng.sysq.db.InterviewQuestionaireDB;
import com.huasheng.sysq.db.QuestionaireDB;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.InterviewQuestionaire;
import com.huasheng.sysq.model.InterviewQuestionaireWrap;
import com.huasheng.sysq.model.Page;
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
}
