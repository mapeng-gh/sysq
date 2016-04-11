package com.huasheng.sysq.activity;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
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
import com.huasheng.sysq.util.InterviewContext;
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
			this.logout();
			
		}else if(view.getId() == R.id.btn_usercenter_userinfo_submit){//保存个人信息
			this.modifyUserInfo();
		}else if(view.getId() == R.id.btn_usercenter_password_submit){//修改密码
			this.modifyPassword();
		}else if(view.getId() == R.id.btn_usercenter_new_submit){//注册用户
			this.newUser();
		}
	}
	
	private void newUser(){
		//获取输入数据
		EditText loginNameET = (EditText)this.containerLL.findViewById(R.id.et_usercenter_new_loginname);
		EditText passwordET = (EditText)this.containerLL.findViewById(R.id.et_usercenter_new_password);
		EditText passwordAgainET = (EditText)this.containerLL.findViewById(R.id.et_usercenter_new_password_again);
		EditText validatePasswordET = (EditText)this.containerLL.findViewById(R.id.et_usercenter_new_validate_password);
		
		//用户名是否为空
		if(StringUtils.isEmpty(StringUtils.trim(loginNameET.getText().toString()))){
			Toast.makeText(MyApplication.getContext(), "用户名不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		
		//校验用户是否存在
		Interviewer user = UserCenterService.getUser(loginNameET.getText().toString());
		if(user != null){
			Toast.makeText(MyApplication.getContext(), "用户已存在", Toast.LENGTH_SHORT).show();
			return;
		}
		
		//新密码不能为空
		if(StringUtils.isEmpty(StringUtils.trim(passwordET.getText().toString())) || StringUtils.isEmpty(StringUtils.trim(passwordAgainET.getText().toString()))){
			Toast.makeText(MyApplication.getContext(), "新密码不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		
		//两次输入密码不一致
		if(!passwordET.getText().toString().equals(passwordAgainET.getText().toString())){
			Toast.makeText(MyApplication.getContext(), "两次密码输入不一致", Toast.LENGTH_SHORT).show();
			return;
		}
		
		//管理员密码不正确
		if(!SysqContext.getInterviewer().getPassword().equals(validatePasswordET.getText().toString())){
			Toast.makeText(MyApplication.getContext(), "您当前密码不正确", Toast.LENGTH_SHORT).show();
			return;
		}
		
		//添加用户
		Interviewer newUser = new Interviewer();
		newUser.setLoginName(loginNameET.getText().toString());
		newUser.setPassword(passwordET.getText().toString());
		UserCenterService.addUser(newUser);
		
		Toast.makeText(MyApplication.getContext(), "用户添加成功", Toast.LENGTH_SHORT).show();
	}
	
	private void modifyPassword(){
		//获取输入数据
		EditText oldPasswordET = (EditText)this.containerLL.findViewById(R.id.et_usercenter_password_old);
		EditText newPasswordET = (EditText)this.containerLL.findViewById(R.id.et_usercenter_password_new);
		EditText newAgainPasswordET = (EditText)this.containerLL.findViewById(R.id.et_usercenter_password_new_again);
		
		//当前密码输入不正确
		if(!SysqContext.getInterviewer().getPassword().equals(oldPasswordET.getText().toString())){
			Toast.makeText(MyApplication.getContext(), "当前密码不正确", Toast.LENGTH_SHORT).show();
			return;
		}
		
		//两次输入新密码不正确
		if(!newPasswordET.getText().toString().equals(newAgainPasswordET.getText().toString())){
			Toast.makeText(MyApplication.getContext(),"新密码两次输入不一致",Toast.LENGTH_SHORT).show();
			return;
		}
		
		Interviewer curUser = SysqContext.getInterviewer();
		curUser.setPassword(newPasswordET.getText().toString());
		UserCenterService.modifyUser(curUser);
		
		Toast.makeText(MyApplication.getContext(), "密码修改成功", Toast.LENGTH_SHORT).show();
	}
	
	private void logout(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("确定退出系统吗？");
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				//清除上下文
				SysqContext.clearContext();
				InterviewContext.clearContext();
				
				//跳转登录
				Intent loginIntent = new Intent(UserCenterActivity.this,LoginActivity.class);
				UserCenterActivity.this.startActivity(loginIntent);
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			};
		});
		builder.show();
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
		UserCenterService.modifyUser(loginUser);
		
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
		this.containerLL.addView(view,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		
		//绑定事件
		Button submitBtn = (Button)view.findViewById(R.id.btn_usercenter_userinfo_submit);
		submitBtn.setOnClickListener(this);
	}
	
	private void loadModifyPasswordPage(){
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.usercenter_modify_password, null);
		
		this.containerLL.removeAllViews();
		this.containerLL.addView(view,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		
		//绑定事件
		Button submitBtn = (Button)view.findViewById(R.id.btn_usercenter_password_submit);
		submitBtn.setOnClickListener(this);
	}
	
	private void loadNewUserPage(){
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.usercenter_new_user, null);
		
		this.containerLL.removeAllViews();
		this.containerLL.addView(view,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		
		//绑定事件
		Button submitBtn = (Button)view.findViewById(R.id.btn_usercenter_new_submit);
		submitBtn.setOnClickListener(this);
	}
}
