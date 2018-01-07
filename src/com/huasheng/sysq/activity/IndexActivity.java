package com.huasheng.sysq.activity;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
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
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.InterviewBasicWrap;
import com.huasheng.sysq.model.Interviewee;
import com.huasheng.sysq.model.Interviewer;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.util.CommonUtils;
import com.huasheng.sysq.util.DialogUtils;
import com.huasheng.sysq.util.NetworkUtils;
import com.huasheng.sysq.util.PackageUtils;
import com.huasheng.sysq.util.PathConstants;
import com.huasheng.sysq.util.SysqConstants;
import com.huasheng.sysq.util.SysqContext;
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
	private Handler checkUpdateHandler;

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
		handleCheckUpdateMessage();
		
		//检查更新
		this.checkUpdate();
	}
	
	/**
	 * 检查版本更新
	 */
	private void checkUpdate(){
		
		if(NetworkUtils.isNetworkEnable(this)){//网络连接正常
			
			new Thread(new Runnable() {//开启新线程
				
				@Override
				public void run() {
					
					try{
						
						//下载配置文件
						File temConfigFile = new File(PathConstants.getTmpDir(),"version.properties"); 
						NetworkUtils.download(UpdateConstants.CONFIG_ADDRESS+"?v="+new Date().getTime(),temConfigFile);
						Map<String,String> versionMap = CommonUtils.readProperties(temConfigFile,"UTF-8");
						FileUtils.deleteQuietly(temConfigFile);
						
						//版本比对
						String msg = "";
						int newAppVersion = Integer.parseInt(versionMap.get("app_version_code"));
						int newInterviewVersion = Integer.parseInt(versionMap.get("interview_version_code"));
						int curAppVersion = PackageUtils.getVersionCode(IndexActivity.this);
						int curInterviewVersion = InterviewService.getCurInterviewVersion().getId();
						if(newAppVersion > curAppVersion){
							msg += "app有新版本了，请及时更新；";
						}
						if(newInterviewVersion > curInterviewVersion){
							msg += "问卷有新版本了，请及时更新";
						}
						
						//发送消息
						if(!StringUtils.isEmpty(msg)){
							Message checkMsg = new Message();
							checkMsg.obj = msg;
							IndexActivity.this.checkUpdateHandler.sendMessage(checkMsg);
						}
					}catch(Exception e){//统一异常处理
						DialogUtils.showPromptDialog(IndexActivity.this, "版本检查", "版本检查出错：" + e.getMessage(), "知道了");
					}
				}
			}).start();
		}
	}
	
	/**
	 * 处理检查更新消息
	 */
	private void handleCheckUpdateMessage(){
		this.checkUpdateHandler = new Handler(){
			public void handleMessage(Message msg) {
				DialogUtils.showPromptDialog(IndexActivity.this, "检查新版本",msg.obj.toString(), "知道了");
			}
		};
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
			if(SysqContext.getInterviewer().getId() == SysqConstants.ADMIN_ID){
				DialogUtils.showLongToast(this, "只有医生才能进行该操作");
				return;
			}
			
			Intent intent = new Intent(this,ReservationListActivity.class);
			startActivity(intent);
			
		}else if(view.getId() == R.id.ll_index_statics){//数据统计
			if(SysqContext.getInterviewer().getId() == SysqConstants.ADMIN_ID){
				DialogUtils.showLongToast(this, "只有医生才能进行该操作");
				return;
			}
			
			Intent intent = new Intent(this,ReportActivity.class);
			startActivity(intent);
			
		}else if(view.getId() == R.id.ll_index_system_update){//系统更新
			if(SysqContext.getInterviewer().getId() != SysqConstants.ADMIN_ID){
				DialogUtils.showLongToast(this, "只有管理员才能进行该操作");
				return;
			}
			
			this.update();
			
		}else if(view.getId() == R.id.ll_index_interview){//开始访谈
			if(SysqContext.getInterviewer().getId() == SysqConstants.ADMIN_ID){
				DialogUtils.showLongToast(this, "只有医生才能进行该操作");
				return;
			}
			
			Intent intent = new Intent(this,IntervieweBasicActivity.class);
			startActivity(intent);
			
		}else if(view.getId() == R.id.ll_index_usercenter){//个人中心
			Intent intent = new Intent(this,UserCenterNavActivity.class);
			startActivity(intent);
			
		}else if(view.getId() == R.id.ll_index_interviewee_search){//受访者一览
			if(SysqContext.getInterviewer().getId() == SysqConstants.ADMIN_ID){
				DialogUtils.showLongToast(this, "只有医生才能进行该操作");
				return;
			}
			
			Intent intent = new Intent(this,IntervieweeActivity.class);
			startActivity(intent);
			
		}else if(view.getId() == R.id.ll_index_data_upload){//上传
			if(SysqContext.getInterviewer().getId() == SysqConstants.ADMIN_ID){
				DialogUtils.showLongToast(this, "只有医生才能进行该操作"); 
				return;
			}
			
			this.upload();
			
		}else if(view.getId() == R.id.ll_index_help){//设置
			if(SysqContext.getInterviewer().getId() != SysqConstants.ADMIN_ID){
				DialogUtils.showLongToast(this, "只有管理员才能进行该操作");
				return;
			}
			
			Intent intent = new Intent(this,SettingsNavActivity.class);
			super.startActivity(intent);
		}
	}
	
	/**
	 * 更新系统
	 */
	private void update(){
		
		//检查数据是否同步
		boolean isSync = true;
		List<InterviewBasicWrap> allInterviewBasicWrapList = InterviewService.getAllInterviewBasic();//获取所有访谈
		if(allInterviewBasicWrapList != null && allInterviewBasicWrapList.size() > 0){
			for(InterviewBasicWrap interviewBasicWrap : allInterviewBasicWrapList){
				InterviewBasic interviewBasic = interviewBasicWrap.getInterviewBasic();
				if(interviewBasic.getIsTest() == InterviewBasic.TEST_NO){//真实访谈
					if(interviewBasic.getStatus() == InterviewBasic.STATUS_DOING){//进行中的访谈
						isSync = false;
						break;
					}else if(interviewBasic.getStatus() == InterviewBasic.STATUS_DONE 
							|| interviewBasic.getStatus() == InterviewBasic.STATUS_BREAK){//已完成、已结束的访谈
						if(interviewBasic.getUploadStatus() == UploadConstants.upload_status_not_upload 
							|| interviewBasic.getUploadStatus() == UploadConstants.upload_status_modified){//未上传或上传后修改
								isSync = false;
								break;
						}else{//已上传
							
							//病人数据未同步
							Interviewee interviewee = interviewBasicWrap.getInterviewee();
							if(interviewee.getUploadStatus() == UploadConstants.upload_status_not_upload 
									|| interviewee.getUploadStatus() == UploadConstants.upload_status_modified){
								isSync = false;
								break;
							}
							
							//医生数据未同步
							Interviewer interviewer = interviewBasicWrap.getInterviewer();
							if(interviewer.getUploadStatus() == UploadConstants.upload_status_not_upload 
									|| interviewer.getUploadStatus() == UploadConstants.upload_status_modified){
								isSync = false;
								break;
							}
						}
					}
				}
			}
		}
		
		//系统更新
		if(isSync){
			this.doUpdate();
		}else{
			
			//弹出提示提醒
			String promptMsg = "不能进行系统更新，可能原因如下：";
			promptMsg += "\n1、存在进行中的访谈；";
			promptMsg += "\n2、访谈已完成或终止，但是未上传或者修改后未上传；";
			promptMsg += "\n3、访谈已上传，但是对应的病人或医生数据未上传；";
			DialogUtils.showPromptDialog(this, "系统更新", promptMsg, "知道了");
		}
		
	}
	
	/**
	 * 系统更新
	 */
	private void doUpdate(){
		
		AlertDialog.Builder updateBuilder = new AlertDialog.Builder(IndexActivity.this);
		updateBuilder.setTitle("系统更新");
		TextView tv = new TextView(IndexActivity.this);
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

	public static void main(String[] args) {
		
	}
}
