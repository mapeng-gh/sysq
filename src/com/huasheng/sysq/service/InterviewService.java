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
		
		//记录访问者Id
		interview.setInterviewerId(SysqContext.getInterviewer().getId());
		
		//记录开始时间
		interview.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
		
		//记录访谈状态
		interview.setStatus(Interview.STATUS_DOING);
		
		//记录当前版本
		interview.setVersionId(SysqContext.getCurrentVersion().getId());
		
		//记录第一个问卷
		Questionaire firstQuestionaire = QuestionaireDB.getFirst(SysqContext.getCurrentVersion().getId(),interview.getType());
		interview.setCurQuestionaireCode(firstQuestionaire.getCode());
		
		interview.setId((int)InterviewDB.save(interview));
		
		//保存到访谈上下文
		InterviewContext.setInterview(interview);
		InterviewContext.setCurrentQuestionaire(firstQuestionaire);
	}
}
