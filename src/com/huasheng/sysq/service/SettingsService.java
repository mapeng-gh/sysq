package com.huasheng.sysq.service;

import com.huasheng.sysq.db.InterviewerDB;
import com.huasheng.sysq.model.Interviewer;

public class SettingsService {

	/**
	 * 重置密码
	 * @param mobile 手机号码
	 * @param password 新密码
	 */
	public static void resetPwd(String mobile,String newPwd){
		
		Interviewer interviewer = InterviewerDB.findByLoginName(mobile);
		if(interviewer == null){
			throw new RuntimeException(String.format("帐号[%s]不存在",mobile));
		}
		
		interviewer.setPassword(newPwd);
		InterviewerDB.update(interviewer);
	}
}
