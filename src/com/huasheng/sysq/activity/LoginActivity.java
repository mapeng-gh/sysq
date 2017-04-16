package com.huasheng.sysq.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.Interviewer;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.service.LoginService;
import com.huasheng.sysq.util.PathConstants;
import com.huasheng.sysq.util.SysqApplication;
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
		
		//系统初始化
		this.init();
	}
	
	private void init(){
		
		//初始化ftp配置
		File ftpConfigFile = new File(PathConstants.getSettingsDir(),"ftp.config");
		if(!ftpConfigFile.exists()){
			try{
				ftpConfigFile.createNewFile();
				List<String> ftpInfos = new ArrayList<String>();
				ftpInfos.add("ip=ccpl.psych.ac.cn");
				ftpInfos.add("port=20020");
				FileUtils.writeLines(ftpConfigFile,"utf-8",ftpInfos, IOUtils.LINE_SEPARATOR_UNIX, false);
			}catch(IOException e){
			}
		}
		
		//初始化数据库配置
		File dbConfigFile = new File(PathConstants.getSettingsDir(),"db.config");
		if(!dbConfigFile.exists()){
			try{
				dbConfigFile.createNewFile();
				List<String> dbInfos = new ArrayList<String>();
				dbInfos.add("ip=ccpl.psych.ac.cn");
				dbInfos.add("port=20039");
				dbInfos.add("username=root");
				dbInfos.add("password=ccpl_817");
				dbInfos.add("db=doc_patient");
				FileUtils.writeLines(dbConfigFile,"utf-8",dbInfos, IOUtils.LINE_SEPARATOR_UNIX, false);
			}catch(IOException e){
			}
		}
		
	}

	@Override
	public void onClick(View view) {
		
		if(view.getId() == R.id.login){
			
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
	
	
}
