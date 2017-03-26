package com.huasheng.sysq.activity.interviewee.person;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.huasheng.sysq.R;

public class IntervieweePerson4PhotoPreviewActivity extends Activity{
	
	private ImageView photoPreviewIV;
	private String  photoFilePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_interviewee_person_photo_preview);
		
		//获取参数
		Intent intent = this.getIntent();
		photoFilePath = intent.getStringExtra("photoFilePath");
		
		//显示
		this.photoPreviewIV = (ImageView)findViewById(R.id.interviewee_person_photo_preview_iv);
		photoPreviewIV.setImageURI(Uri.fromFile(new File(this.photoFilePath)));
	}

}
