package com.huasheng.sysq.service;

import com.huasheng.sysq.db.InterviewerDB;
import com.huasheng.sysq.model.Interviewer;

public class LoginService {

	public static Interviewer login(String loginName,String password){
		
		Interviewer interviewer = InterviewerDB.findByLoginName(loginName);
		
		if(interviewer == null){//用户不存在
			
			return null;
		}
		
		if(interviewer.getPassword().equals(password)){//验证通过
			
			return interviewer;
			
		}else{//密码错误
			
			return null;
		}
	}
}
