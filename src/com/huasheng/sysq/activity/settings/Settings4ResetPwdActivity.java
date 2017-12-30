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
import com.huasheng.sysq.util.DialogUtils;
import com.huasheng.sysq.util.SysqConstants;

/**
 * 设置
 * @author mapeng
 *
 */
public class Settings4ResetPwdActivity extends Activity implements OnClickListener{
	
	private EditText loginNameET;
	private EditText pwdET;
	private Button submitBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.setContentView(R.layout.activity_settings_resetpwd);
		
		initComponents();
	}
	
	//初始化组件
	private void initComponents(){
		this.loginNameET = (EditText)this.findViewById(R.id.settings_resetpwd_login_name_et);
		this.pwdET = (EditText)this.findViewById(R.id.settings_resetpwd_pwd_et);
		this.submitBtn = (Button)this.findViewById(R.id.settings_resetpwd_submit);
		this.submitBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.settings_resetpwd_submit){
			this.submit();
		}
	}
	
	/**
	 * 重置密码
	 */
	private void submit(){
		
		String loginName = this.loginNameET.getText().toString().trim();
		String newPwd = this.pwdET.getText().toString().trim();
		
		//登录帐号
		if(StringUtils.isEmpty(loginName)){
			DialogUtils.showLongToast(this, "登录帐号不能为空");
			return;
		}
		if(SysqConstants.ADMIN_LOGIN_NAME.equals(loginName)){
			DialogUtils.showLongToast(this, "请访问【修改密码】功能");
			return;
		}
		Interviewer interviewer = UserCenterService.getUser(loginName);
		if(interviewer == null){
			DialogUtils.showLongToast(this, "登录帐号不存在");
			return;
		}
		
		//新密码
		if(StringUtils.isEmpty(newPwd)){
			DialogUtils.showLongToast(this, "新登录密码不能为空");
			return;
		}
		
		//重置
		UserCenterService.resetPwd(loginName, newPwd);
		DialogUtils.showLongToast(this, "重置密码成功");
	}
}
