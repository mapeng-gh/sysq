package com.huasheng.sysq.activity.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;

import com.huasheng.sysq.R;
import com.huasheng.sysq.activity.usercenter.Usercenter4AddUserActivity;

public class SettingsNavActivity extends Activity implements OnClickListener{
	
	private LinearLayout ftpLL;
	private LinearLayout dbLL;
	private LinearLayout addUserLL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_settings_navy);
		
		ftpLL = (LinearLayout)findViewById(R.id.settings_nav_ftp);
		ftpLL.setOnClickListener(this);
		dbLL = (LinearLayout)findViewById(R.id.settings_nav_db);
		dbLL.setOnClickListener(this);
		addUserLL = (LinearLayout)findViewById(R.id.usercenter_navy_adduserLL);
		addUserLL.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.settings_nav_ftp){//FTP设置
			Intent intent = new Intent(this,Settings4FTPActivity.class);
			this.startActivity(intent);
			
		}else if(v.getId() == R.id.settings_nav_db){//数据库设置
			Intent intent = new Intent(this,Settings4DBActivity.class);
			this.startActivity(intent);
			
		}else if(v.getId() == R.id.usercenter_navy_adduserLL){//注册用户
			Intent intent = new Intent(this,Usercenter4AddUserActivity.class);
			this.startActivity(intent);
		}
	}

}
