package com.huasheng.sysq.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.provider.DocumentFile;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.Interviewer;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.service.UserCenterService;
import com.huasheng.sysq.util.CommonUtils;
import com.huasheng.sysq.util.DialogUtils;
import com.huasheng.sysq.util.MailUtils;
import com.huasheng.sysq.util.NetworkUtils;
import com.huasheng.sysq.util.RSAUtils;
import com.huasheng.sysq.util.DeviceStorageUtils;
import com.huasheng.sysq.util.SysqApplication;
import com.huasheng.sysq.util.SysqConstants;
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
						Interviewer adminInterviewer = UserCenterService.getById(SysqConstants.ADMIN_ID);
						
						//密码加密
						String adminPwd = adminInterviewer.getPassword();
						String publicKeyStr = "30819f300d06092a864886f70d010101050003818d0030818902818100a5d2892a01182448da82ee00a1ffd95806f082a8d18371969538f8271d4d0f141fe1e301b1b931a8c12a0f4d9790e9bf9e3e9870af9f5d79469c983858a5c826c04d247e10eaa79e0998e2277a4a2fbb69451b48d8c876981afeae80deaf64f441890fbfe546b70bf26bbf7faaad13f4dd58b4f7956de10ba06c839ddfbe72ed0203010001";
						String encryptAdminPwd = RSAUtils.encrypt(adminPwd, publicKeyStr);
						
						//邮件模板
						StringBuffer mailTemp = new StringBuffer();
						mailTemp.append("管理员忘记登录密码，需要您协助！");
						mailTemp.append("<br/><br/>");
						mailTemp.append("登录账号：%s<br/>");
						mailTemp.append("登录密码：%s<br/>");
						mailTemp.append("姓名：%s<br/>");
						mailTemp.append("联系电话：%s<br/>");
						mailTemp.append("电子邮箱：%s<br/>");
						mailTemp.append("工作单位：%s<br/>");
						mailTemp.append("<br/><br/>");
						mailTemp.append("备注：登陆密码已进行加密处理，请安装解密器进行解密，解密器链接：http://o77m1ke38.bkt.clouddn.com/decryptor.apk");
						
						//发送邮件
						try{
							MailUtils.send("找回密码",String.format(mailTemp.toString(), adminInterviewer.getLoginName(),encryptAdminPwd,adminInterviewer.getUsername(),adminInterviewer.getMobile(),adminInterviewer.getEmail(),adminInterviewer.getWorkingPlace()));
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
		
		Interviewer interviewer = UserCenterService.login(loginName, password);
		
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
