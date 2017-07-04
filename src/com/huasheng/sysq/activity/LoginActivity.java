package com.huasheng.sysq.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.Interviewer;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.service.LoginService;
import com.huasheng.sysq.service.UserCenterService;
import com.huasheng.sysq.util.DialogUtils;
import com.huasheng.sysq.util.MailUtils;
import com.huasheng.sysq.util.NetworkUtils;
import com.huasheng.sysq.util.SysqApplication;
import com.huasheng.sysq.util.SysqContext;

public class LoginActivity extends Activity implements OnClickListener{
	
	private EditText userET;
	private EditText pwdET;
	private Button loginBtn;
	private TextView forgetPwdTV;
	
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		
		initComponents();
	}
	
	/**
	 * 初始化组件
	 */
	private void initComponents(){
		userET = (EditText)findViewById(R.id.username);
		pwdET = (EditText)findViewById(R.id.password);
		loginBtn = (Button)findViewById(R.id.login);
		this.forgetPwdTV = (TextView)this.findViewById(R.id.login_forgetpwd_tv);
		
		loginBtn.setOnClickListener(this);
		this.forgetPwdTV.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		
		if(view.getId() == R.id.login){//登录
			this.login();
			
		}else if(view.getId() == R.id.login_forgetpwd_tv){//忘记密码
			this.forgetPwd();
		}
	}
	
	/**
	 * 忘记密码
	 */
	private void forgetPwd(){
		
		DialogUtils.showConfirmDialog(this, "找回管理员密码", "管理员密码将以邮件的形式通知相关负责人", "发送", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						
						//网络检查
						if(!NetworkUtils.isNetworkEnable(LoginActivity.this)){
							handler.post(new Runnable() {
								@Override
								public void run() {
									DialogUtils.showLongToast(LoginActivity.this, "请您检查网络是否连通");
								}
							});
							return;
						}
						
						//获取管理员密码
						Interviewer adminInterviewer = UserCenterService.getUser("admin");
						String adminPwd = adminInterviewer.getPassword();
						
						//加密
						
						//发送邮件
						try{
							MailUtils.sendForgetPwd(adminPwd);
							handler.post(new Runnable() {
								@Override
								public void run() {
									DialogUtils.showLongToast(LoginActivity.this, "管理员密码已发送至相关负责人，请耐心等待");
								}
							});
						}catch(final Exception e){
							handler.post(new Runnable() {
								@Override
								public void run() {
									DialogUtils.showLongToast(LoginActivity.this, e.getMessage());
								}
							});
						}
					}
				}).start();
				
			}
		}, "取消",null);
	}
	
	/**
	 * 登录
	 */
	private void login(){
		
		String loginName = userET.getText().toString();
		String password = pwdET.getText().toString();
		
		Interviewer interviewer = LoginService.login(loginName, password);
		
		if(interviewer == null){
			Toast.makeText(SysqApplication.getContext(), "帐号或密码不正确", Toast.LENGTH_SHORT).show();
		}else{
			
			//保存上下文（当前用户、版本号）
			SysqContext.setInterviewer(interviewer);
			SysqContext.setCurrentVersion(InterviewService.getCurInterviewVersion());
			
			//跳转首页
			Intent indexIntent = new Intent(this,IndexActivity.class);
			this.startActivity(indexIntent);
		}
	}
}
