package com.huasheng.sysq.activity;

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
import com.huasheng.sysq.activity.settings.FTPActivity;
import com.huasheng.sysq.util.BaseActivity;
import com.huasheng.sysq.util.InterviewContext;
import com.huasheng.sysq.util.SysqApplication;
import com.huasheng.sysq.util.SysqContext;
import com.huasheng.sysq.util.SystemUpdateUtils;
import com.huasheng.sysq.util.UploadUtils;

public class IndexActivity extends BaseActivity implements OnClickListener{
	
	private LinearLayout reservationLL;
	private LinearLayout dataStaticsLL;
	private LinearLayout systemUpdateLL;
	private LinearLayout interviewStartLL;
	private LinearLayout userCenterLL;
	private LinearLayout interviewerSearchLL;
	private LinearLayout dataUploadLL;
	private LinearLayout helpLL;
	
	private Handler handler;
	private String toastMsg;

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
				if(msg.what == 1){
					Toast.makeText(IndexActivity.this,toastMsg, Toast.LENGTH_LONG).show();
				}
				super.handleMessage(msg);
			}
		};
	}

	@Override
	public void onClick(View view) {
		
		if(view.getId() == R.id.ll_index_reservation){//预约管理
			
			startActivity(new Intent(this,ReservationListActivity.class));
			
		}else if(view.getId() == R.id.ll_index_statics){
			
			Intent intent = new Intent(this,ReportActivity.class);
			startActivity(intent);
			
		}else if(view.getId() == R.id.ll_index_system_update){//系统更新
			
			SystemUpdateUtils.checkUpdate(this,true);
			
		}else if(view.getId() == R.id.ll_index_interview){//开始访谈
			
			Intent intent = new Intent(this,InterviewerBasicActivity.class);
			startActivity(intent);
			
		}else if(view.getId() == R.id.ll_index_usercenter){//个人中心
			
			Intent intent = new Intent(this,UserCenterActivity.class);
			startActivity(intent);
			
		}else if(view.getId() == R.id.ll_index_interviewee_search){//受访者一览
			
			Intent intent = new Intent(this,IntervieweeActivity.class);
			startActivity(intent);
			
		}else if(view.getId() == R.id.ll_index_data_upload){//上传
			
			this.upload();
			
		}else if(view.getId() == R.id.ll_index_help){
			
			Intent ftpIntent = new Intent(this,FTPActivity.class);
			super.startActivity(ftpIntent);
		}
	}
	
	/**
	 * 上传文件
	 */
	private void upload(){
		
		//主线程不允许进行网络访问
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try{
					
					//检查配置信息
					UploadUtils.checkEnv();
					
					//显示进度条
					AlertDialog.Builder uploadDialogBuilder = new AlertDialog.Builder(IndexActivity.this);
					uploadDialogBuilder.setTitle("上传");
					uploadDialogBuilder.setMessage("正在上传中，请稍候...");
					uploadDialogBuilder.setCancelable(false);
					final AlertDialog uploadDialog = uploadDialogBuilder.create();
					uploadDialog.show();
					
					//上传文件
					try{
						UploadUtils.upload();
						SysqApplication.showMessage("上传成功");
						
					}catch(Exception e){
						
						//给主线程发送消息显示toast错误信息
						toastMsg = e.getMessage();
						Message msg = new Message();
						msg.what = 1;
						handler.sendMessage(msg);
						
					}finally{
						uploadDialog.dismiss();
					}
					
				}catch(Exception e){
					
					//给主线程发送消息显示toast错误信息
					toastMsg = e.getMessage();
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				}
			}
		}).start();
		
	}

	@Override
	public void onBackPressed() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("确定退出系统吗？");
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				//清除上下文
				SysqContext.clearContext();
				InterviewContext.clearInterviewContext();
				
				//跳转登录
				Intent loginIntent = new Intent(IndexActivity.this,LoginActivity.class);
				IndexActivity.this.startActivity(loginIntent);
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			};
		});
		builder.show();
		
		
	}
	
}
