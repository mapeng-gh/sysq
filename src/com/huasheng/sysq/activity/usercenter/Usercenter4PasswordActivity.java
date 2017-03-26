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
import com.huasheng.sysq.util.SysqContext;

public class Usercenter4PasswordActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_usercenter_password);
		
		Button submitBtn = (Button)this.findViewById(R.id.usercenter_password_submit_btn);
		submitBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.usercenter_password_submit_btn){
			submitUsercenter4Password();
		}
	}

	/**
	 * 修改密码
	 */
	private void submitUsercenter4Password(){
		
		//校验
		EditText oldPasswordET = (EditText)this.findViewById(R.id.usercenter_password_old_et);
		String oldPassword = oldPasswordET.getText().toString().trim();
		if(StringUtils.isEmpty(oldPassword)){
			SysqApplication.showMessage("原密码不能为空");
			return;
		}
		EditText newPasswordET = (EditText)this.findViewById(R.id.usercenter_password_new_et);
		String newPassword = newPasswordET.getText().toString().trim();
		if(StringUtils.isEmpty(newPassword)){
			SysqApplication.showMessage("新密码不能为空");
			return;
		}
		
		//校验原密码是否正确
		Interviewer interviewer = SysqContext.getInterviewer();
		if(!interviewer.getPassword().equals(oldPassword)){
			SysqApplication.showMessage("原密码不正确");
			return;
		}
		if(interviewer.getPassword().equals(newPassword)){
			SysqApplication.showMessage("新密码与原密码一致，请重新修改");
			return;
		}
		
		//修改
		interviewer.setPassword(newPassword);
		UserCenterService.modifyUser(interviewer);
		
		SysqApplication.showMessage("修改成功");
	}
}
