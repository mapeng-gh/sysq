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
import com.huasheng.sysq.service.SettingsService;
import com.huasheng.sysq.util.DialogUtils;

/**
 * 设置
 * @author mapeng
 *
 */
public class Settings4ResetPwdActivity extends Activity implements OnClickListener{
	
	private EditText mobileET;
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
		this.mobileET = (EditText)this.findViewById(R.id.settings_resetpwd_mobile_et);
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
		
		String mobile = this.mobileET.getText().toString().trim();
		String pwd = this.pwdET.getText().toString().trim();
		
		//校验
		if(StringUtils.isEmpty(mobile)){
			DialogUtils.showLongToast(this, "手机号码不能为空");
			return;
		}
		if(StringUtils.isEmpty(pwd)){
			DialogUtils.showLongToast(this, "新密码不能为空");
			return;
		}
		
		//重置
		try{
			SettingsService.resetPwd(mobile, pwd);
			DialogUtils.showLongToast(this, "重置密码成功");
		}catch(Exception e){
			DialogUtils.showLongToast(this, e.getMessage());
		}
	}
}
