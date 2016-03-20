package com.huasheng.sysq.service;

import com.huasheng.sysq.db.InterviewerDB;
import com.huasheng.sysq.db.VersionDB;
import com.huasheng.sysq.model.Interviewer;
import com.huasheng.sysq.util.SysqContext;

public class LoginService {

	public static boolean login(String loginName,String password){
		
		Interviewer interviewer = InterviewerDB.findByLoginName(loginName);
		if(interviewer == null){
			return false;
		}
		if(interviewer.getPassword().equals(password)){
			SysqContext.setInterviewer(interviewer);//���浱ǰ�����û�
			SysqContext.setCurrentVersion(VersionDB.getCurVersion());//���浱ǰ�汾��
			return true;
		}else{
			return false;
		}
	}
}
