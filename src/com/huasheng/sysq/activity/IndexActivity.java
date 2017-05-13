package com.huasheng.sysq.activity;

import org.apache.commons.lang3.StringUtils;

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
import android.widget.TextView;

import com.huasheng.sysq.R;
import com.huasheng.sysq.activity.interview.IntervieweBasicActivity;
import com.huasheng.sysq.activity.interviewee.IntervieweeActivity;
import com.huasheng.sysq.activity.report.ReportActivity;
import com.huasheng.sysq.activity.reservation.ReservationListActivity;
import com.huasheng.sysq.activity.settings.SettingsNavActivity;
import com.huasheng.sysq.activity.usercenter.UserCenterNavActivity;
import com.huasheng.sysq.util.DialogUtils;
import com.huasheng.sysq.util.update.UpdateConstants;
import com.huasheng.sysq.util.update.UpdateUtils;
import com.huasheng.sysq.util.upload.UploadConstants;
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
	
	//数据上传
	private Handler uploadHandler;
	private AlertDialog uploadDialog;
	private String uploadMsg;
	
	//系统更新
	private Handler updateHandler;
	private AlertDialog updateDialog;
	private String updateMsg;

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
		handleUploadMessage();
		handleUpdateMessage();
	}
	
	/**
	 * 处理上传消息
	 */
	private void handleUploadMessage(){
		
		this.uploadHandler = new Handler(){
			public void handleMessage(Message msg) {
				if(StringUtils.isEmpty(uploadMsg)){
					uploadMsg = msg.obj.toString();
				}else{
					uploadMsg = uploadMsg + "\n" + msg.obj.toString();
				}
				TextView tv = (TextView)uploadDialog.findViewById(1);
				tv.setText(uploadMsg);
				if(msg.what == UploadConstants.upload_progress_finished){
					uploadMsg = "";
					uploadDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(true);
				}
			}
		};
	}
	
	/**
	 * 处理系统更新消息
	 */
	private void handleUpdateMessage(){
		updateHandler = new Handler(){
			public void handleMessage(Message msg) {
				if(msg.obj != null && !StringUtils.isEmpty(msg.obj.toString())){
					if(StringUtils.isEmpty(updateMsg)){
						updateMsg = msg.obj.toString();
					}else{
						updateMsg = updateMsg + "\n" + msg.obj.toString();
					}
					TextView tv = (TextView)updateDialog.findViewById(1);
					tv.setText(updateMsg);
				}
				if(msg.what == UpdateConstants.MSG_UPDATE_CLOSE){//关闭对话框
					updateMsg = "";
					updateDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(true);
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
			this.update();
			
		}else if(view.getId() == R.id.ll_index_interview){//开始访谈
			Intent intent = new Intent(this,IntervieweBasicActivity.class);
			startActivity(intent);
			
		}else if(view.getId() == R.id.ll_index_usercenter){//个人中心
			Intent intent = new Intent(this,UserCenterNavActivity.class);
			startActivity(intent);
			
		}else if(view.getId() == R.id.ll_index_interviewee_search){//受访者一览
			Intent intent = new Intent(this,IntervieweeActivity.class);
			startActivity(intent);
			
		}else if(view.getId() == R.id.ll_index_data_upload){//上传
			this.upload();
			
		}else if(view.getId() == R.id.ll_index_help){//设置
			Intent intent = new Intent(this,SettingsNavActivity.class);
			super.startActivity(intent);
		}
	}
	
	/**
	 * 更新系统
	 */
	private void update(){
		
		AlertDialog.Builder updateBuilder = new AlertDialog.Builder(this);
		updateBuilder.setTitle("系统更新");
		TextView tv = new TextView(this);
		tv.setId(1);
		tv.setTextSize(20);
		updateBuilder.setView(tv);
		updateBuilder.setPositiveButton("关闭", null);
		updateDialog = updateBuilder.create();
		updateDialog.setCancelable(false);
		updateDialog.setCanceledOnTouchOutside(false);
		updateDialog.show();
		updateDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				UpdateUtils.update(IndexActivity.this,updateHandler);
			}
		}).start();
	}
	
	/**
	 * 上传文件
	 */
	private void upload(){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("数据上传");
		TextView tv = new TextView(this);
		tv.setId(1);
		tv.setTextSize(20);
		builder.setView(tv);
		builder.setPositiveButton("关闭", null);
		uploadDialog = builder.create();
		uploadDialog.setCancelable(false);
		uploadDialog.setCanceledOnTouchOutside(false);
		uploadDialog.show();
		uploadDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				UploadUtils.upload(uploadHandler);
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
