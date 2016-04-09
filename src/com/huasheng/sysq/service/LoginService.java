package com.huasheng.sysq.service;

import com.huasheng.sysq.db.InterviewerDB;
import com.huasheng.sysq.model.Interviewer;

public class LoginService {

	public static Interviewer login(String loginName,String password){
		
		Interviewer interviewer = InterviewerDB.findByLoginName(loginName);
		
		if(interviewer == null){//�û�������
			
			return null;
		}
		
		if(interviewer.getPassword().equals(password)){//��֤ͨ��
			
			return interviewer;
			
		}else{//�������
			
			return null;
		}
	}
}
