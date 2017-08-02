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
import com.huasheng.sysq.util.CommonUtils;
import com.huasheng.sysq.util.DialogUtils;
import com.huasheng.sysq.util.MailUtils;
import com.huasheng.sysq.util.NetworkUtils;
import com.huasheng.sysq.util.RSAUtils;
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
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		this.userET.setText("");
		this.pwdET.setText("");
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
						String publicKeyStr = "30819f300d06092a864886f70d010101050003818d0030818902818100a5d2892a01182448da82ee00a1ffd95806f082a8d18371969538f8271d4d0f141fe1e301b1b931a8c12a0f4d9790e9bf9e3e9870af9f5d79469c983858a5c826c04d247e10eaa79e0998e2277a4a2fbb69451b48d8c876981afeae80deaf64f441890fbfe546b70bf26bbf7faaad13f4dd58b4f7956de10ba06c839ddfbe72ed0203010001";
						String encryptAdminPwd = RSAUtils.encrypt(adminPwd, publicKeyStr);
						
						//发送邮件
						try{
							//崔老师邮箱：yuqingcui2012@163.com
							MailUtils.send("yuqingcui2012@163.com", "找回密码",String.format("设备号：%s<br/>管理员密码：%s<br/>请使用解码app进行解密", CommonUtils.getMacAddress(LoginActivity.this),encryptAdminPwd));
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
