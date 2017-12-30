package com.huasheng.sysq.activity.settings;

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
import com.huasheng.sysq.util.CommonUtils;
import com.huasheng.sysq.util.SysqApplication;
import com.huasheng.sysq.util.upload.UploadConstants;

public class Settings4AddUserActivity extends Activity implements OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_settings_adduser);
		
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
		
		//登录帐号
		EditText loginNameET = (EditText)this.findViewById(R.id.usercenter_adduser_login_name_et);
		final String loginName = loginNameET.getText().toString().trim();
		if(StringUtils.isEmpty(loginName)){
			SysqApplication.showMessage("登录帐号不能为空");
			return;
		}
		if(!CommonUtils.test("^[A-Za-z0-9_]{10,15}$",loginName)){
			SysqApplication.showMessage("登录帐号格式不正确");
			return;
		}
		Interviewer interviewer = UserCenterService.getUser(loginName);
		if(interviewer != null){
			SysqApplication.showMessage("帐号已注册");
			return;
		}
		
		//登录密码
		EditText passwordET = (EditText)this.findViewById(R.id.usercenter_adduser_password_et);
		final String password = passwordET.getText().toString().trim();
		if(StringUtils.isEmpty(password)){
			SysqApplication.showMessage("密码不能为空");
			return;
		}
		
		//手机号
		EditText mobileET = (EditText)this.findViewById(R.id.usercenter_adduser_mobile_et);
		final String mobile = mobileET.getText().toString().trim();
		if(StringUtils.isEmpty(mobile)){
			SysqApplication.showMessage("手机号码不能为空");
			return;
		}
		if(!CommonUtils.test("^1[0-9]{10}$",mobile)){
			SysqApplication.showMessage("手机号码格式不正确");
			return;
		}
		
		//姓名
		EditText usernameET = (EditText)this.findViewById(R.id.usercenter_adduser_username_et);
		final String username = usernameET.getText().toString().trim();
		if(StringUtils.isEmpty(username)){
			SysqApplication.showMessage("姓名不能为空");
			return;
		}
		
		//邮箱
		EditText emailET = (EditText)this.findViewById(R.id.usercenter_adduser_email_et);
		final String email = emailET.getText().toString().trim();
		if(StringUtils.isEmpty(email)){
			SysqApplication.showMessage("电子邮箱不能为空");
			return;
		}
		
		//工作单位
		EditText workingPlaceET = (EditText)this.findViewById(R.id.usercenter_adduser_workingplace_et);
		final String workingPlace = workingPlaceET.getText().toString().trim();
		if(StringUtils.isEmpty(workingPlace)){
			SysqApplication.showMessage("工作单位不能为空");
			return;
		}
		
		//保存
		Interviewer newInterviewer = new Interviewer();
		newInterviewer.setLoginName(loginName);
		newInterviewer.setMobile(mobile);
		newInterviewer.setPassword(password);
		newInterviewer.setUsername(username);
		newInterviewer.setEmail(email);
		newInterviewer.setWorkingPlace(workingPlace);
		newInterviewer.setUploadStatus(UploadConstants.upload_status_not_upload);
		UserCenterService.addUser(newInterviewer);
		
		SysqApplication.showMessage("添加成功");
	}
}
