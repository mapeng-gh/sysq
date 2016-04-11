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
		if(view.getId() == R.id.ll_usercenter_userinfo){//������Ϣ
			this.loadUserInfoPage();
			
		}else if(view.getId() == R.id.ll_usercenter_modify_password){//�޸�����
			this.loadModifyPasswordPage();
			
		}else if(view.getId() == R.id.ll_usercenter_new_user){//ע���û�
			this.loadNewUserPage();
			
		}else if(view.getId() == R.id.ll_usercenter_logout){//�˳�
			this.logout();
			
		}else if(view.getId() == R.id.btn_usercenter_userinfo_submit){//���������Ϣ
			this.modifyUserInfo();
		}else if(view.getId() == R.id.btn_usercenter_password_submit){//�޸�����
			this.modifyPassword();
		}else if(view.getId() == R.id.btn_usercenter_new_submit){//ע���û�
			this.newUser();
		}
	}
	
	private void newUser(){
		//��ȡ��������
		EditText loginNameET = (EditText)this.containerLL.findViewById(R.id.et_usercenter_new_loginname);
		EditText passwordET = (EditText)this.containerLL.findViewById(R.id.et_usercenter_new_password);
		EditText passwordAgainET = (EditText)this.containerLL.findViewById(R.id.et_usercenter_new_password_again);
		EditText validatePasswordET = (EditText)this.containerLL.findViewById(R.id.et_usercenter_new_validate_password);
		
		//�û����Ƿ�Ϊ��
		if(StringUtils.isEmpty(StringUtils.trim(loginNameET.getText().toString()))){
			Toast.makeText(MyApplication.getContext(), "�û�������Ϊ��", Toast.LENGTH_SHORT).show();
			return;
		}
		
		//У���û��Ƿ����
		Interviewer user = UserCenterService.getUser(loginNameET.getText().toString());
		if(user != null){
			Toast.makeText(MyApplication.getContext(), "�û��Ѵ���", Toast.LENGTH_SHORT).show();
			return;
		}
		
		//�����벻��Ϊ��
		if(StringUtils.isEmpty(StringUtils.trim(passwordET.getText().toString())) || StringUtils.isEmpty(StringUtils.trim(passwordAgainET.getText().toString()))){
			Toast.makeText(MyApplication.getContext(), "�����벻��Ϊ��", Toast.LENGTH_SHORT).show();
			return;
		}
		
		//�����������벻һ��
		if(!passwordET.getText().toString().equals(passwordAgainET.getText().toString())){
			Toast.makeText(MyApplication.getContext(), "�����������벻һ��", Toast.LENGTH_SHORT).show();
			return;
		}
		
		//����Ա���벻��ȷ
		if(!SysqContext.getInterviewer().getPassword().equals(validatePasswordET.getText().toString())){
			Toast.makeText(MyApplication.getContext(), "����ǰ���벻��ȷ", Toast.LENGTH_SHORT).show();
			return;
		}
		
		//����û�
		Interviewer newUser = new Interviewer();
		newUser.setLoginName(loginNameET.getText().toString());
		newUser.setPassword(passwordET.getText().toString());
		UserCenterService.addUser(newUser);
		
		Toast.makeText(MyApplication.getContext(), "�û���ӳɹ�", Toast.LENGTH_SHORT).show();
	}
	
	private void modifyPassword(){
		//��ȡ��������
		EditText oldPasswordET = (EditText)this.containerLL.findViewById(R.id.et_usercenter_password_old);
		EditText newPasswordET = (EditText)this.containerLL.findViewById(R.id.et_usercenter_password_new);
		EditText newAgainPasswordET = (EditText)this.containerLL.findViewById(R.id.et_usercenter_password_new_again);
		
		//��ǰ�������벻��ȷ
		if(!SysqContext.getInterviewer().getPassword().equals(oldPasswordET.getText().toString())){
			Toast.makeText(MyApplication.getContext(), "��ǰ���벻��ȷ", Toast.LENGTH_SHORT).show();
			return;
		}
		
		//�������������벻��ȷ
		if(!newPasswordET.getText().toString().equals(newAgainPasswordET.getText().toString())){
			Toast.makeText(MyApplication.getContext(),"�������������벻һ��",Toast.LENGTH_SHORT).show();
			return;
		}
		
		Interviewer curUser = SysqContext.getInterviewer();
		curUser.setPassword(newPasswordET.getText().toString());
		UserCenterService.modifyUser(curUser);
		
		Toast.makeText(MyApplication.getContext(), "�����޸ĳɹ�", Toast.LENGTH_SHORT).show();
	}
	
	private void logout(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("ȷ���˳�ϵͳ��");
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				//���������
				SysqContext.clearContext();
				InterviewContext.clearContext();
				
				//��ת��¼
				Intent loginIntent = new Intent(UserCenterActivity.this,LoginActivity.class);
				UserCenterActivity.this.startActivity(loginIntent);
			}
		});
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			};
		});
		builder.show();
	}
	
	private void modifyUserInfo(){
		
		//��ȡ��������
		EditText usernameET = (EditText)this.containerLL.findViewById(R.id.et_usercenter_userinfo_username);
		EditText emailET = (EditText)this.containerLL.findViewById(R.id.et_usercenter_userinfo_email);
		EditText mobileET = (EditText)this.containerLL.findViewById(R.id.et_usercenter_userinfo_mobile);
		RadioButton workingPlaceUKRB = (RadioButton)this.containerLL.findViewById(R.id.rb_usercenter_userinfo_working_place_uk);
		RadioButton workingPlaceChinaRB = (RadioButton)this.containerLL.findViewById(R.id.rb_usercenter_userinfo_working_place_china);
		
		//����
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
		
		Toast.makeText(MyApplication.getContext(), "�޸ĳɹ�", Toast.LENGTH_SHORT).show();
		
	}
	
	private void loadUserInfoPage(){
		
		//���ؾ�̬ҳ��
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.usercenter_userinfo, null);
		
		//��������
		Interviewer loginUser = SysqContext.getInterviewer();
		
		//���ݰ�
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
		
		//��Ⱦ
		this.containerLL.removeAllViews();
		this.containerLL.addView(view,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		
		//���¼�
		Button submitBtn = (Button)view.findViewById(R.id.btn_usercenter_userinfo_submit);
		submitBtn.setOnClickListener(this);
	}
	
	private void loadModifyPasswordPage(){
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.usercenter_modify_password, null);
		
		this.containerLL.removeAllViews();
		this.containerLL.addView(view,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		
		//���¼�
		Button submitBtn = (Button)view.findViewById(R.id.btn_usercenter_password_submit);
		submitBtn.setOnClickListener(this);
	}
	
	private void loadNewUserPage(){
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.usercenter_new_user, null);
		
		this.containerLL.removeAllViews();
		this.containerLL.addView(view,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		
		//���¼�
		Button submitBtn = (Button)view.findViewById(R.id.btn_usercenter_new_submit);
		submitBtn.setOnClickListener(this);
	}
}
