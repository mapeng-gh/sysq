package com.huasheng.sysq.activity.usercenter;

import org.apache.commons.lang3.StringUtils;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.Interviewer;
import com.huasheng.sysq.service.UserCenterService;
import com.huasheng.sysq.util.CommonUtils;
import com.huasheng.sysq.util.SysqApplication;
import com.huasheng.sysq.util.SysqContext;
import com.huasheng.sysq.util.upload.UploadConstants;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class Usercenter4BasicActivity extends Activity implements OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_usercenter_basic);
		
		//初始化
		initUsercenter4Basic();
		
		Button submitBtn = (Button)this.findViewById(R.id.usercenter_basic_submit_btn);
		submitBtn.setOnClickListener(this);
	}
	
	/**
	 * 初始化
	 */
	private void initUsercenter4Basic(){
		Interviewer loginUser = UserCenterService.getById(SysqContext.getInterviewer().getId());
		
		EditText mobileET = (EditText)this.findViewById(R.id.usercenter_basic_mobile_et);
		mobileET.setText(loginUser.getMobile());
		
		EditText usernameET = (EditText)this.findViewById(R.id.usercenter_basic_username_et);
		usernameET.setText(loginUser.getUsername());
		
		EditText emailET = (EditText)this.findViewById(R.id.usercenter_basic_email_et);
		emailET.setText(loginUser.getEmail());
		
		EditText workingPlaceET = (EditText)this.findViewById(R.id.usercenter_basic_workingplace_et);
		workingPlaceET.setText(loginUser.getWorkingPlace());
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.usercenter_basic_submit_btn){
			submitUsercenter4Basic();
		}
	}
	
	/**
	 * 保存
	 */
	private void submitUsercenter4Basic(){
		
		//手机号码
		EditText mobileET = (EditText)this.findViewById(R.id.usercenter_basic_mobile_et);
		String mobile = mobileET.getText().toString().trim();
		if(StringUtils.isEmpty(mobile)){
			SysqApplication.showMessage("手机号码不能为空");
			return;
		}
		if(!CommonUtils.test("^1[0-9]{10}$",mobile)){
			SysqApplication.showMessage("手机号码格式不正确");
			return;
		}
		
		//姓名
		EditText usernameET = (EditText)this.findViewById(R.id.usercenter_basic_username_et);
		String username = usernameET.getText().toString().trim();
		if(StringUtils.isEmpty(username)){
			SysqApplication.showMessage("姓名不能为空");
			return;
		}
		
		//邮箱
		EditText emailET = (EditText)this.findViewById(R.id.usercenter_basic_email_et);
		String email = emailET.getText().toString().trim();
		if(StringUtils.isEmpty(email)){
			SysqApplication.showMessage("电子邮箱不能为空");
			return;
		}
		
		//工作单位
		EditText workingPlaceET = (EditText)this.findViewById(R.id.usercenter_basic_workingplace_et);
		String workingPlace = workingPlaceET.getText().toString().trim();
		if(StringUtils.isEmpty(workingPlace)){
			SysqApplication.showMessage("工作单位不能为空");
			return;
		}
		
		//修改
		Interviewer loginUser = UserCenterService.getById(SysqContext.getInterviewer().getId());
		loginUser.setMobile(mobile);
		loginUser.setUsername(username);
		loginUser.setEmail(email);
		loginUser.setWorkingPlace(workingPlace);
		if(loginUser.getUploadStatus() == UploadConstants.upload_status_uploaded){
			loginUser.setUploadStatus(UploadConstants.upload_status_modified);
		}
		UserCenterService.modifyUser(loginUser);
		
		SysqApplication.showMessage("修改成功");
		
		//更改当前登录用户
		SysqContext.setInterviewer(loginUser);
	}
}
