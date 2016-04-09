package com.huasheng.sysq.service;

import com.huasheng.sysq.db.InterviewerDB;
import com.huasheng.sysq.model.Interviewer;

public class UserCenterService {

	public static void modifyUserInfo(Interviewer interviewer){
		InterviewerDB.update(interviewer);
	}
}
