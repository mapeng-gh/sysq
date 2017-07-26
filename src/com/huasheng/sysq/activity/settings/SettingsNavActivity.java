package com.huasheng.sysq.activity.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;

import com.huasheng.sysq.R;

public class SettingsNavActivity extends Activity implements OnClickListener{
	
	private LinearLayout ftpLL;
	private LinearLayout dbLL;
	private LinearLayout addUserLL;
	private LinearLayout resetPwdLL;
	private LinearLayout repaireLL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_settings_navy);
		
		ftpLL = (LinearLayout)findViewById(R.id.settings_nav_ftp);
		ftpLL.setOnClickListener(this);
		dbLL = (LinearLayout)findViewById(R.id.settings_nav_db);
		dbLL.setOnClickListener(this);
		addUserLL = (LinearLayout)findViewById(R.id.settings_navy_adduserLL);
		addUserLL.setOnClickListener(this);
		resetPwdLL = (LinearLayout)findViewById(R.id.settings_navy_resetpwdLL);
		resetPwdLL.setOnClickListener(this);
		repaireLL = (LinearLayout)findViewById(R.id.settings_navy_repaireLL);
		repaireLL.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.settings_nav_ftp){//FTP设置
			Intent intent = new Intent(this,Settings4FTPActivity.class);
			this.startActivity(intent);
			
		}else if(v.getId() == R.id.settings_nav_db){//数据库设置
			Intent intent = new Intent(this,Settings4DBActivity.class);
			this.startActivity(intent);
			
		}else if(v.getId() == R.id.settings_navy_adduserLL){//注册用户
			Intent intent = new Intent(this,Settings4AddUserActivity.class);
			this.startActivity(intent);
			
		}else if(v.getId() == R.id.settings_navy_resetpwdLL){//重置密码
			Intent intent = new Intent(this,Settings4ResetPwdActivity.class);
			this.startActivity(intent);
			
		}else if(v.getId() == R.id.settings_navy_repaireLL){//数据修复
			Intent intent = new Intent(this,Settings4RepaireIntervieweeListActivity.class);
			this.startActivity(intent);
		}
	}

}
