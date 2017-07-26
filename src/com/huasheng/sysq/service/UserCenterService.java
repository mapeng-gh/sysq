package com.huasheng.sysq.service;

import java.util.ArrayList;
import java.util.List;

import com.huasheng.sysq.db.InterviewerDB;
import com.huasheng.sysq.model.Interviewer;

public class UserCenterService {

	public static void modifyUser(Interviewer interviewer){
		InterviewerDB.update(interviewer);
	}
	
	public static Interviewer getUser(String loginUsername){
		return InterviewerDB.findByLoginName(loginUsername);
	}
	
	public static Interviewer getById(int id){
		return InterviewerDB.selectById(id);
	}
	
	public static void addUser(Interviewer interviewer){
		InterviewerDB.insert(interviewer);
	}
	
	/**
	 * 获取所有医生
	 * @return
	 */
	public static List<Interviewer> getDockerList(){
		List<Interviewer> interviewerList = InterviewerDB.selectAll();
		if(interviewerList == null || interviewerList.size() == 0){
			return null;
		}
		
		List<Interviewer> dockerList = new ArrayList<Interviewer>();
		for(Interviewer interviewer : interviewerList){
			if(!"admin".equals(interviewer.getLoginName())){
				dockerList.add(interviewer);
			}
		}
		return dockerList;
	}
}
