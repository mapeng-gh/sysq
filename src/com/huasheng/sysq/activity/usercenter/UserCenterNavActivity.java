package com.huasheng.sysq.activity.usercenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;

import com.huasheng.sysq.R;
import com.huasheng.sysq.activity.LoginActivity;

public class UserCenterNavActivity extends Activity implements OnClickListener{
	
	private LinearLayout basicLL;
	private LinearLayout passwordLL;
	private LinearLayout addUserLL;
	private LinearLayout logoutLL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_usercenter_navy);
		
		basicLL = (LinearLayout)findViewById(R.id.usercenter_navy_basicLL);
		basicLL.setOnClickListener(this);
		passwordLL = (LinearLayout)findViewById(R.id.usercenter_navy_passwordLL);
		passwordLL.setOnClickListener(this);
		addUserLL = (LinearLayout)findViewById(R.id.usercenter_navy_adduserLL);
		addUserLL.setOnClickListener(this);
		logoutLL = (LinearLayout)findViewById(R.id.usercenter_navy_logoutLL);
		logoutLL.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.usercenter_navy_basicLL){//个人信息
			Intent intent = new Intent(this,Usercenter4BasicActivity.class);
			this.startActivity(intent);
			
		}else if(v.getId() == R.id.usercenter_navy_passwordLL){//修改密码
			Intent intent = new Intent(this,Usercenter4PasswordActivity.class);
			this.startActivity(intent);
			
		}else if(v.getId() == R.id.usercenter_navy_adduserLL){//注册用户
			Intent intent = new Intent(this,Usercenter4AddUserActivity.class);
			this.startActivity(intent);
			
		}else if(v.getId() == R.id.usercenter_navy_logoutLL){//退出系统
			logout();
		}
	}
	
	/**
	 * 退出系统
	 */
	private void logout(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("确定退出系统吗？");
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent  = new Intent(UserCenterNavActivity.this,LoginActivity.class);
				UserCenterNavActivity.this.startActivity(intent);
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			};
		});
		AlertDialog dialog = builder.create();
		dialog.show();
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
	}

}