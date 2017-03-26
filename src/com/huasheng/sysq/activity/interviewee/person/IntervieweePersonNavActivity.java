package com.huasheng.sysq.activity.interviewee.person;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;

import com.huasheng.sysq.R;
import com.huasheng.sysq.activity.interviewee.IntervieweeActivity;

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
			Intent intent = new Intent(this,IntervieweePerson4DNAActivity.class);
			intent.putExtra("interviewBasicId", this.interviewBasicId);
			this.startActivity(intent);
			
		}else if(v.getId() == R.id.interviewee_person_navy_photoLL){//拍照
			Intent intent = new Intent(this,IntervieweePerson4PhotoActivity.class);
			intent.putExtra("interviewBasicId", this.interviewBasicId);
			this.startActivity(intent);
		}
	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this,IntervieweeActivity.class);
		this.startActivity(intent);
	}

}
