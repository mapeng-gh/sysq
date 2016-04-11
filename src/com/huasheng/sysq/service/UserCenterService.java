package com.huasheng.sysq.service;

import com.huasheng.sysq.db.InterviewerDB;
import com.huasheng.sysq.model.Interviewer;

public class UserCenterService {

	public static void modifyUser(Interviewer interviewer){
		InterviewerDB.update(interviewer);
	}
	
	public static Interviewer getUser(String loginUsername){
		return InterviewerDB.findByLoginName(loginUsername);
	}
	
	public static void addUser(Interviewer interviewer){
		InterviewerDB.insert(interviewer);
	}
}
