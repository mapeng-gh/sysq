package com.huasheng.sysq.activity.usercenter;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.Interviewer;
import com.huasheng.sysq.service.UserCenterService;
import com.huasheng.sysq.util.SysqApplication;

public class Usercenter4AddUserActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_usercenter_adduser);
		
		Button submitBtn = (Button)this.findViewById(R.id.usercenter_adduser_submit_btn);
		submitBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.usercenter_adduser_submit_btn){
			submitUsercenter4AddUser();
		}
	}
	
	/**
	 * 保存
	 */
	private void submitUsercenter4AddUser(){
		
		//校验
		EditText mobileET = (EditText)this.findViewById(R.id.usercenter_adduser_mobile_et);
		String mobile = mobileET.getText().toString().trim();
		if(StringUtils.isEmpty(mobile)){
			SysqApplication.showMessage("手机号码不能为空");
			return;
		}
		
		EditText passwordET = (EditText)this.findViewById(R.id.usercenter_adduser_password_et);
		String password = passwordET.getText().toString().trim();
		if(StringUtils.isEmpty(password)){
			SysqApplication.showMessage("密码不能为空");
			return;
		}
		
		EditText usernameET = (EditText)this.findViewById(R.id.usercenter_adduser_username_et);
		String username = usernameET.getText().toString().trim();
		if(StringUtils.isEmpty(username)){
			SysqApplication.showMessage("姓名不能为空");
			return;
		}
		
		EditText emailET = (EditText)this.findViewById(R.id.usercenter_adduser_email_et);
		String email = emailET.getText().toString().trim();
		if(StringUtils.isEmpty(email)){
			SysqApplication.showMessage("电子邮箱不能为空");
			return;
		}
		
		EditText workingPlaceET = (EditText)this.findViewById(R.id.usercenter_adduser_workingplace_et);
		String workingPlace = workingPlaceET.getText().toString().trim();
		if(StringUtils.isEmpty(workingPlace)){
			SysqApplication.showMessage("工作单位不能为空");
			return;
		}
		
		//是否已注册
		Interviewer interviewer = UserCenterService.getUser(mobile);
		if(interviewer != null){
			SysqApplication.showMessage("该手机号码已注册");
			return;
		}
		
		//保存
		Interviewer newInterviewer = new Interviewer();
		newInterviewer.setLoginName(mobile);
		newInterviewer.setMobile(mobile);
		newInterviewer.setPassword(password);
		newInterviewer.setUsername(username);
		newInterviewer.setEmail(email);
		newInterviewer.setWorkingPlace(workingPlace);
		UserCenterService.addUser(newInterviewer);
		
		SysqApplication.showMessage("添加成功");
	}

}
