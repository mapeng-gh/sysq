package com.huasheng.sysq.service;

import java.util.ArrayList;
import java.util.List;

import com.huasheng.sysq.db.InterviewerDB;
import com.huasheng.sysq.model.Interviewer;
import com.huasheng.sysq.util.SysqConstants;

public class UserCenterService {
	
	/**
	 * 用户登录
	 * @param loginName
	 * @param password
	 * @return
	 */
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

	/**
	 * 修改用户
	 * @param interviewer
	 */
	public static void modifyUser(Interviewer interviewer){
		InterviewerDB.update(interviewer);
	}
	
	/**
	 * 查询用户
	 * @param loginUsername
	 * @return
	 */
	public static Interviewer getUser(String loginUsername){
		return InterviewerDB.findByLoginName(loginUsername);
	}
	
	/**
	 * 查询用户
	 * @param id
	 * @return
	 */
	public static Interviewer getById(int id){
		return InterviewerDB.selectById(id);
	}
	
	/**
	 * 添加用户
	 * @param interviewer
	 */
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
			if(!SysqConstants.ADMIN_LOGIN_NAME.equals(interviewer.getLoginName())){
				dockerList.add(interviewer);
			}
		}
		return dockerList;
	}
	
	/**
	 * 重置密码
	 * @param loginName 登录帐号
	 * @param password 新密码
	 */
	public static void resetPwd(String loginName,String newPwd){
		
		Interviewer interviewer = InterviewerDB.findByLoginName(loginName);
		interviewer.setPassword(newPwd);
		InterviewerDB.update(interviewer);
	}
}
