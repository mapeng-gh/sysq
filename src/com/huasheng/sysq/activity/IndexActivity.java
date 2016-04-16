package com.huasheng.sysq.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;

import com.huasheng.sysq.R;
import com.huasheng.sysq.activity.interview.InterviewerBasicActivity;
import com.huasheng.sysq.activity.interviewee.IntervieweeActivity;
import com.huasheng.sysq.activity.reservation.ReservationListActivity;

public class IndexActivity extends Activity implements OnClickListener{
	
	private LinearLayout reservationLL;
	private LinearLayout dataStaticsLL;
	private LinearLayout systemUpdateLL;
	private LinearLayout interviewStartLL;
	private LinearLayout userCenterLL;
	private LinearLayout interviewerSearchLL;
	private LinearLayout dataUploadLL;
	private LinearLayout helpLL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_index);
		
		reservationLL = (LinearLayout)findViewById(R.id.reservation_admin);
		reservationLL.setOnClickListener(this);
		
		dataStaticsLL = (LinearLayout)findViewById(R.id.data_statics);
		dataStaticsLL.setOnClickListener(this);
		
		systemUpdateLL = (LinearLayout)findViewById(R.id.system_update);
		systemUpdateLL.setOnClickListener(this);
		
		interviewStartLL = (LinearLayout)findViewById(R.id.interview_start);
		interviewStartLL.setOnClickListener(this);
		
		userCenterLL = (LinearLayout)findViewById(R.id.user_center);
		userCenterLL.setOnClickListener(this);
		
		interviewerSearchLL = (LinearLayout)findViewById(R.id.interviewer_search);
		interviewerSearchLL.setOnClickListener(this);
		
		dataUploadLL = (LinearLayout)findViewById(R.id.data_upload);
		dataUploadLL.setOnClickListener(this);
		
		helpLL = (LinearLayout)findViewById(R.id.help);
		helpLL.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View view) {
		if(view.getId() == R.id.reservation_admin){//预约管理
			startActivity(new Intent(this,ReservationListActivity.class));
		}else if(view.getId() == R.id.data_statics){
			
		}else if(view.getId() == R.id.system_update){
			
		}else if(view.getId() == R.id.interview_start){//开始访谈
			Intent intent = new Intent(this,InterviewerBasicActivity.class);
			startActivity(intent);
		}else if(view.getId() == R.id.user_center){//个人中心
			Intent intent = new Intent(this,UserCenterActivity.class);
			startActivity(intent);
		}else if(view.getId() == R.id.interviewer_search){//受访者一览
			Intent intent = new Intent(this,IntervieweeActivity.class);
			startActivity(intent);
		}else if(view.getId() == R.id.data_upload){
			
		}else if(view.getId() == R.id.help){
			
		}
	}

}
