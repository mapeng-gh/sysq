package com.huasheng.sysq.activity.interviewee.person;

import java.io.File;
import java.io.IOException;

import com.huasheng.sysq.R;
import com.huasheng.sysq.activity.interviewee.IntervieweeActivity;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.util.DateTimeUtils;
import com.huasheng.sysq.util.PathConstants;
import com.huasheng.sysq.util.SysqApplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;

public class IntervieweePersonNavActivity extends Activity implements OnClickListener{
	
	private int interviewBasicId;
	private LinearLayout basicLL;
	private LinearLayout dnaLL;
	private LinearLayout photoLL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_interviewee_person_navy);
		
		Intent intent = this.getIntent();
		this.interviewBasicId = intent.getIntExtra("interviewBasicId", -1);
		
		this.basicLL = (LinearLayout)this.findViewById(R.id.interviewee_person_navy_basicLL);
		this.basicLL.setOnClickListener(this);
		this.dnaLL = (LinearLayout)this.findViewById(R.id.interviewee_person_navy_dnaLL);
		this.dnaLL.setOnClickListener(this);
		this.photoLL = (LinearLayout)this.findViewById(R.id.interviewee_person_navy_photoLL);
		this.photoLL.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.interviewee_person_navy_basicLL){//基本信息
			Intent intent = new Intent(this,IntervieweePerson4BasicActivity.class);
			intent.putExtra("interviewBasicId", this.interviewBasicId);
			this.startActivity(intent);
			
		}else if(v.getId() == R.id.interviewee_person_navy_dnaLL){//dna采集
			
			//终止的访谈不允许DNA采集
			InterviewBasic interviewBasic = InterviewService.findInterviewBasicById(this.interviewBasicId);
			if(interviewBasic.getStatus() == InterviewBasic.STATUS_BREAK){
				SysqApplication.showMessage("访谈已结束不能进行DNA采集");
				return;
			}
			
			Intent intent = new Intent(this,IntervieweePerson4DNAActivity.class);
			intent.putExtra("interviewBasicId", this.interviewBasicId);
			this.startActivity(intent);
			
		}else if(v.getId() == R.id.interviewee_person_navy_photoLL){//拍照
			
			//终止的访谈不允许拍照
			InterviewBasic interviewBasic = InterviewService.findInterviewBasicById(this.interviewBasicId);
			if(interviewBasic.getStatus() == InterviewBasic.STATUS_BREAK){
				SysqApplication.showMessage("访谈已结束不能进行拍照");
				return;
			}
			
			this.takePhoto4Interviewee();
		}
	}
	
	/**
	 * 拍照
	 */
	private void takePhoto4Interviewee(){
		
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		
		InterviewBasic interviewBasic = InterviewService.findInterviewBasicById(this.interviewBasicId);
		File imgDir = new File(PathConstants.getAudioDir(),interviewBasic.getUsername());
		if(!imgDir.exists()){
			imgDir.mkdirs();
		}
		File imgFile = new File(imgDir,DateTimeUtils.getCustomDateTime("yyyyMMddHHmmss")+".jpg");
		try{
			if(imgFile.exists()){
				imgFile.delete();
			}
			imgFile.createNewFile();
		}catch(IOException e){
		}
		Uri imgUri = Uri.fromFile(imgFile);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
		this.startActivity(intent);
	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this,IntervieweeActivity.class);
		this.startActivity(intent);
	}

}
