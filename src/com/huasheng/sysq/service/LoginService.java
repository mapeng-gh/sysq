package com.huasheng.sysq.service;

import com.huasheng.sysq.db.InterviewerDB;
import com.huasheng.sysq.model.Interviewer;

public class LoginService {

	public static boolean login(String loginName,String password){
		
		Interviewer interviewer = InterviewerDB.findByLoginName(loginName);
		if(interviewer == null){
			return false;
		}
		if(interviewer.getPassword().equals(password)){
			return true;
		}else{
			return false;
		}
	}
}
