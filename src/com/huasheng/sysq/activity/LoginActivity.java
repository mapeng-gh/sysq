package com.huasheng.sysq.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.huasheng.sysq.R;
import com.huasheng.sysq.db.VersionDB;
import com.huasheng.sysq.model.Interviewer;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.service.LoginService;
import com.huasheng.sysq.util.MyApplication;
import com.huasheng.sysq.util.SysqContext;

public class LoginActivity extends Activity implements OnClickListener{
	
	private EditText userET;
	private EditText pwdET;
	private Button loginBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		
		userET = (EditText)findViewById(R.id.username);
		pwdET = (EditText)findViewById(R.id.password);
		loginBtn = (Button)findViewById(R.id.login);
		
		loginBtn.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View view) {
		
		if(view.getId() == R.id.login){
			
			String loginName = userET.getText().toString();
			String password = pwdET.getText().toString();
			
			Interviewer interviewer = LoginService.login(loginName, password);
			
			if(interviewer == null){
				Toast.makeText(MyApplication.getContext(), "帐号或密码不正确", Toast.LENGTH_SHORT).show();
			}else{
				
				//保存上下文（当前用户、版本号）
				SysqContext.setInterviewer(interviewer);
				SysqContext.setCurrentVersion(InterviewService.getCurrentVersion());
				
				Intent intent = new Intent(MyApplication.getContext(), IndexActivity.class);
				startActivity(intent);
			}
		}
	}

}
