package com.huasheng.sysq.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.huasheng.sysq.db.InterviewDB;
import com.huasheng.sysq.db.QuestionaireDB;
import com.huasheng.sysq.model.Interview;
import com.huasheng.sysq.model.Questionaire;
import com.huasheng.sysq.util.InterviewContext;
import com.huasheng.sysq.util.SysqContext;

public class InterviewService {

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
}
