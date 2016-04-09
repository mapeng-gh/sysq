package com.huasheng.sysq.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.Interviewer;
import com.huasheng.sysq.service.UserCenterService;
import com.huasheng.sysq.util.MyApplication;
import com.huasheng.sysq.util.SysqContext;

public class UserCenterActivity extends Activity implements OnClickListener{
	
	private LinearLayout containerLL;
	
	private LinearLayout userInfoLL;
	private LinearLayout modifyPasswordLL;
	private LinearLayout newUserLL;
	private LinearLayout logoutLL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_usercenter);
		
		containerLL = (LinearLayout)findViewById(R.id.usercenter_layout_container);
		
		userInfoLL = (LinearLayout)findViewById(R.id.ll_usercenter_userinfo);
		modifyPasswordLL = (LinearLayout)findViewById(R.id.ll_usercenter_modify_password);
		newUserLL = (LinearLayout)findViewById(R.id.ll_usercenter_new_user);
		logoutLL = (LinearLayout)findViewById(R.id.ll_usercenter_logout);
		
		userInfoLL.setOnClickListener(this);
		modifyPasswordLL.setOnClickListener(this);
		newUserLL.setOnClickListener(this);
		logoutLL.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View view) {
		if(view.getId() == R.id.ll_usercenter_userinfo){//个人信息
			this.loadUserInfoPage();
			
		}else if(view.getId() == R.id.ll_usercenter_modify_password){//修改密码
			this.loadModifyPasswordPage();
			
		}else if(view.getId() == R.id.ll_usercenter_new_user){//注册用户
			this.loadNewUserPage();
			
		}else if(view.getId() == R.id.ll_usercenter_logout){//退出
			
		}else if(view.getId() == R.id.btn_usercenter_userinfo_submit){//保存个人信息
			this.modifyUserInfo();
		}
	}
	
	private void modifyUserInfo(){
		
		//获取输入数据
		EditText usernameET = (EditText)this.containerLL.findViewById(R.id.et_usercenter_userinfo_username);
		EditText emailET = (EditText)this.containerLL.findViewById(R.id.et_usercenter_userinfo_email);
		EditText mobileET = (EditText)this.containerLL.findViewById(R.id.et_usercenter_userinfo_mobile);
		RadioButton workingPlaceUKRB = (RadioButton)this.containerLL.findViewById(R.id.rb_usercenter_userinfo_working_place_uk);
		RadioButton workingPlaceChinaRB = (RadioButton)this.containerLL.findViewById(R.id.rb_usercenter_userinfo_working_place_china);
		
		//保存
		Interviewer loginUser = SysqContext.getInterviewer();
		loginUser.setUsername(usernameET.getText().toString());
		loginUser.setEmail(emailET.getText().toString());
		loginUser.setMobile(mobileET.getText().toString());
		if(workingPlaceUKRB.isChecked()){
			loginUser.setWorkingPlace(workingPlaceUKRB.getText().toString());
		}else if(workingPlaceChinaRB.isChecked()){
			loginUser.setWorkingPlace(workingPlaceChinaRB.getText().toString());
		}
		UserCenterService.modifyUserInfo(loginUser);
		
		Toast.makeText(MyApplication.getContext(), "修改成功", Toast.LENGTH_SHORT).show();
		
	}
	
	private void loadUserInfoPage(){
		
		//加载静态页面
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.usercenter_userinfo, null);
		
		//加载数据
		Interviewer loginUser = SysqContext.getInterviewer();
		
		//数据绑定
		TextView loginNameTV = (TextView)view.findViewById(R.id.tv_usercenter_userinfo_login_name);
		EditText userNameET = (EditText)view.findViewById(R.id.et_usercenter_userinfo_username);
		EditText emailET = (EditText)view.findViewById(R.id.et_usercenter_userinfo_email);
		EditText mobileET = (EditText)view.findViewById(R.id.et_usercenter_userinfo_mobile);
		RadioButton workingPlaceUKRB = (RadioButton)view.findViewById(R.id.rb_usercenter_userinfo_working_place_uk);
		RadioButton workingPlaceChinaRB = (RadioButton)view.findViewById(R.id.rb_usercenter_userinfo_working_place_china);
		loginNameTV.setText(loginUser.getLoginName());
		userNameET.setText(loginUser.getUsername());
		emailET.setText(loginUser.getEmail());
		mobileET.setText(loginUser.getMobile());
		if(workingPlaceUKRB.getText().toString().equals(loginUser.getWorkingPlace())){
			workingPlaceUKRB.setChecked(true);
		}
		if(workingPlaceChinaRB.getText().toString().equals(loginUser.getWorkingPlace())){
			workingPlaceChinaRB.setChecked(true);
		}
		
		//渲染
		this.containerLL.removeAllViews();
		this.containerLL.addView(view);
		
		//绑定事件
		Button submitBtn = (Button)findViewById(R.id.btn_usercenter_userinfo_submit);
		submitBtn.setOnClickListener(this);
	}
	
	private void loadModifyPasswordPage(){
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.usercenter_modify_password, null);
		
		this.containerLL.removeAllViews();
		this.containerLL.addView(view);
	}
	
	private void loadNewUserPage(){
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.usercenter_new_user, null);
		
		this.containerLL.removeAllViews();
		this.containerLL.addView(view);
	}
}
