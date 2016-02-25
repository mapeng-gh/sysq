package com.huasheng.sysq.activity;

import com.huasheng.sysq.R;
import com.huasheng.sysq.db.InterviewerDB;
import com.huasheng.sysq.model.Interviewer;
import com.huasheng.sysq.service.LoginService;
import com.huasheng.sysq.util.MyApplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
			
			if(LoginService.login(loginName, password)){
				Log.d("LoginActivity", "jump to index");
			}else{
				Toast.makeText(MyApplication.getContext(), "�ʺŻ����벻��ȷ", Toast.LENGTH_SHORT).show();
			}
		}
	}

}
