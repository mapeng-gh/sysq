package com.huasheng.sysq.activity;

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
import com.huasheng.sysq.activity.interview.InterviewerBasicActivity;
import com.huasheng.sysq.activity.interviewee.IntervieweeActivity;
import com.huasheng.sysq.activity.report.ReportActivity;
import com.huasheng.sysq.activity.reservation.ReservationListActivity;
import com.huasheng.sysq.util.InterviewContext;
import com.huasheng.sysq.util.SysqContext;

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
		
		reservationLL = (LinearLayout)findViewById(R.id.ll_index_reservation);
		reservationLL.setOnClickListener(this);
		
		dataStaticsLL = (LinearLayout)findViewById(R.id.ll_index_statics);
		dataStaticsLL.setOnClickListener(this);
		
		systemUpdateLL = (LinearLayout)findViewById(R.id.ll_index_system_update);
		systemUpdateLL.setOnClickListener(this);
		
		interviewStartLL = (LinearLayout)findViewById(R.id.ll_index_interview);
		interviewStartLL.setOnClickListener(this);
		
		userCenterLL = (LinearLayout)findViewById(R.id.ll_index_usercenter);
		userCenterLL.setOnClickListener(this);
		
		interviewerSearchLL = (LinearLayout)findViewById(R.id.ll_index_interviewee_search);
		interviewerSearchLL.setOnClickListener(this);
		
		dataUploadLL = (LinearLayout)findViewById(R.id.ll_index_data_upload);
		dataUploadLL.setOnClickListener(this);
		
		helpLL = (LinearLayout)findViewById(R.id.ll_index_help);
		helpLL.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View view) {
		if(view.getId() == R.id.ll_index_reservation){//ԤԼ����
			
			startActivity(new Intent(this,ReservationListActivity.class));
			
		}else if(view.getId() == R.id.ll_index_statics){
			
			Intent intent = new Intent(this,ReportActivity.class);
			startActivity(intent);
			
		}else if(view.getId() == R.id.ll_index_system_update){
			
		}else if(view.getId() == R.id.ll_index_interview){//��ʼ��̸
			
			Intent intent = new Intent(this,InterviewerBasicActivity.class);
			startActivity(intent);
			
		}else if(view.getId() == R.id.ll_index_usercenter){//��������
			
			Intent intent = new Intent(this,UserCenterActivity.class);
			startActivity(intent);
			
		}else if(view.getId() == R.id.ll_index_interviewee_search){//�ܷ���һ��
			
			Intent intent = new Intent(this,IntervieweeActivity.class);
			startActivity(intent);
			
		}else if(view.getId() == R.id.ll_index_data_upload){
			
		}else if(view.getId() == R.id.ll_index_help){
			
		}
	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("ȷ���˳�ϵͳ��");
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				//���������
				SysqContext.clearContext();
				InterviewContext.clearInterviewContext();
				
				//��ת��¼
				Intent loginIntent = new Intent(IndexActivity.this,LoginActivity.class);
				loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				IndexActivity.this.startActivity(loginIntent);
			}
		});
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			};
		});
		builder.show();
	}
	
	

}
