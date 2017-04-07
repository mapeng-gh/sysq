package com.huasheng.sysq.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.huasheng.sysq.R;
import com.huasheng.sysq.activity.interview.InterviewerBasicActivity;
import com.huasheng.sysq.activity.interviewee.IntervieweeActivity;
import com.huasheng.sysq.activity.report.ReportActivity;
import com.huasheng.sysq.activity.reservation.ReservationListActivity;
import com.huasheng.sysq.activity.settings.SettingsNavActivity;
import com.huasheng.sysq.activity.usercenter.UserCenterNavActivity;
import com.huasheng.sysq.util.DialogUtils;
import com.huasheng.sysq.util.SysqApplication;
import com.huasheng.sysq.util.update.SystemUpdateUtils;
import com.huasheng.sysq.util.upload.UploadUtils;

public class IndexActivity extends Activity implements OnClickListener{
	
	private LinearLayout reservationLL;
	private LinearLayout dataStaticsLL;
	private LinearLayout systemUpdateLL;
	private LinearLayout interviewStartLL;
	private LinearLayout userCenterLL;
	private LinearLayout interviewerSearchLL;
	private LinearLayout dataUploadLL;
	private LinearLayout helpLL;
	
	private Handler handler;
	private AlertDialog uploadDialog;

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
		
		//处理消息
		handler = new Handler(){
			public void handleMessage(Message msg) {
				if(msg.what == UploadUtils.UPLOAD_PROGRESS){
					uploadDialog.setMessage(msg.obj.toString());
				}else{
					uploadDialog.dismiss();
					Toast.makeText(IndexActivity.this, msg.obj.toString(), Toast.LENGTH_LONG).show();
				}
			}
		};
	}

	@Override
	public void onClick(View view) {
		
		if(view.getId() == R.id.ll_index_reservation){//预约管理
			Intent intent = new Intent(this,ReservationListActivity.class);
			startActivity(intent);
			
		}else if(view.getId() == R.id.ll_index_statics){//数据统计
			Intent intent = new Intent(this,ReportActivity.class);
			startActivity(intent);
			
		}else if(view.getId() == R.id.ll_index_system_update){//系统更新
			SystemUpdateUtils.checkUpdate(this,true);
			
		}else if(view.getId() == R.id.ll_index_interview){//开始访谈
			Intent intent = new Intent(this,InterviewerBasicActivity.class);
			startActivity(intent);
			
		}else if(view.getId() == R.id.ll_index_usercenter){//个人中心
			Intent intent = new Intent(this,UserCenterNavActivity.class);
			startActivity(intent);
			
		}else if(view.getId() == R.id.ll_index_interviewee_search){//受访者一览
			Intent intent = new Intent(this,IntervieweeActivity.class);
			startActivity(intent);
			
		}else if(view.getId() == R.id.ll_index_data_upload){//上传
//			this.upload();
			SysqApplication.showMessage("该功能正在开发中");
			
		}else if(view.getId() == R.id.ll_index_help){//设置
			Intent intent = new Intent(this,SettingsNavActivity.class);
			super.startActivity(intent);
		}
	}
	
	/**
	 * 上传文件
	 */
	private void upload(){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("数据上传");
		builder.setMessage("数据正在上传中，请稍候...");
		this.uploadDialog = builder.create();
		uploadDialog.setCancelable(false);
		uploadDialog.setCanceledOnTouchOutside(false);
		uploadDialog.show();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				UploadUtils.upload(handler);
			}
		}).start();
	}
		
	@Override
	public void onBackPressed() {
		
		DialogUtils.showConfirmDialog(this, "确定退出系统吗？", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent loginIntent = new Intent(IndexActivity.this,LoginActivity.class);
				IndexActivity.this.startActivity(loginIntent);
			}
		});
	}
}
