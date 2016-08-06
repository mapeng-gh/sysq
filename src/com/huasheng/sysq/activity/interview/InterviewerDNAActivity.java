package com.huasheng.sysq.activity.interview;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.util.BaseActivity;
import com.huasheng.sysq.util.InterviewConstants;
import com.huasheng.sysq.util.InterviewContext;
import com.huasheng.sysq.util.ScanConstants;
import com.huasheng.sysq.util.SysqApplication;

public class InterviewerDNAActivity extends BaseActivity implements OnClickListener{
	
	
	private EditText sample1ET;
	private EditText sample2ET;
	private EditText sample3ET;
	private EditText sample4ET;
	
	private ImageButton sample1ImgBtn;
	private ImageButton sample2ImgBtn;
	private ImageButton sample3ImgBtn;
	private ImageButton sample4ImgBtn;
	
	private RadioButton testRB;
	private RadioButton caseRB;
	
	private Button submitInterviewBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_interview_dna);
		
		sample1ET = (EditText)findViewById(R.id.et_interview_dna_sample1);
		sample2ET = (EditText)findViewById(R.id.et_interview_dna_sample2);
		sample3ET = (EditText)findViewById(R.id.et_interview_dna_sample3);
		sample4ET = (EditText)findViewById(R.id.et_interview_dna_sample4);
		
		sample1ImgBtn = (ImageButton)findViewById(R.id.btn_interview_dna_sample1);
		sample2ImgBtn = (ImageButton)findViewById(R.id.btn_interview_dna_sample2);
		sample3ImgBtn = (ImageButton)findViewById(R.id.btn_interview_dna_sample3);
		sample4ImgBtn = (ImageButton)findViewById(R.id.btn_interview_dna_sample4);
		sample1ImgBtn.setOnClickListener(this);
		sample2ImgBtn.setOnClickListener(this);
		sample3ImgBtn.setOnClickListener(this);
		sample4ImgBtn.setOnClickListener(this);
		
		testRB = (RadioButton)findViewById(R.id.interviewer_dna_type_test);
		caseRB = (RadioButton)findViewById(R.id.interviewer_dna_questionaire_type_case);
		
		submitInterviewBtn = (Button)findViewById(R.id.interviewer_dna_submit_button);
		submitInterviewBtn.setOnClickListener(this);
	}


	@Override
	public void onClick(View view) {
		
		if(view.getId() == R.id.interviewer_dna_submit_button){//提交
			
			//新建访问记录
			InterviewBasic interviewBasic = (InterviewBasic)getIntent().getSerializableExtra("interview");
			collectData(interviewBasic);
			InterviewService.newInterviewBasic(interviewBasic);
			
			//保存到上下文
			InterviewContext.setCurInterviewBasic(interviewBasic);
			
			//跳转问卷页
			SysqApplication.jumpToActivity(InterviewActivity.class);
			
		}else if(view.getId() == R.id.btn_interview_dna_sample1){
			
			Intent intent = new Intent(ScanConstants.INTENT_ACTION_SCAN);
			this.startActivityForResult(intent, 1);
			
		}else if(view.getId() == R.id.btn_interview_dna_sample2){
			
			Intent intent = new Intent(ScanConstants.INTENT_ACTION_SCAN);
			this.startActivityForResult(intent, 2);
			
		}else if(view.getId() == R.id.btn_interview_dna_sample3){
			
			Intent intent = new Intent(ScanConstants.INTENT_ACTION_SCAN);
			this.startActivityForResult(intent, 3);
			
		}else if(view.getId() == R.id.btn_interview_dna_sample4){
			
			Intent intent = new Intent(ScanConstants.INTENT_ACTION_SCAN);
			this.startActivityForResult(intent, 4);
		}
	}
	
	private void collectData(InterviewBasic interview){
		
		List<String> dnaList = new ArrayList<String>();
		
		if(sample1ET.getText().toString() != null && !sample1ET.getText().toString().equals("")){
			dnaList.add(sample1ET.getText().toString());
		}
		if(sample2ET.getText().toString() != null && !sample2ET.getText().toString().equals("")){
			dnaList.add(sample2ET.getText().toString());
		}
		if(sample3ET.getText().toString() != null && !sample3ET.getText().toString().equals("")){
			dnaList.add(sample3ET.getText().toString());
		}
		if(sample4ET.getText().toString() != null && !sample4ET.getText().toString().equals("")){
			dnaList.add(sample4ET.getText().toString());
		}
		if(dnaList.size() > 0){
			interview.setDna(StringUtils.join(dnaList, ","));
		}
		interview.setIsTest(testRB.isChecked() ? InterviewBasic.TEST_YES : InterviewBasic.TEST_NO);
		interview.setType(caseRB.isChecked() ? InterviewConstants.TYPE_CASE : InterviewConstants.TYPE_CONTRAST);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(resultCode == Activity.RESULT_OK){
			
			if(requestCode == 1){
				
				String scanResult = data.getStringExtra(ScanConstants.ACTION_SCAN_KEY);
				this.sample1ET.setText(scanResult);
				
			}else if(requestCode == 2){
				
				String scanResult = data.getStringExtra(ScanConstants.ACTION_SCAN_KEY);
				this.sample2ET.setText(scanResult);
				
			}else if(requestCode == 3){
				
				String scanResult = data.getStringExtra(ScanConstants.ACTION_SCAN_KEY);
				this.sample3ET.setText(scanResult);
				
			}else if(requestCode == 4){
				
				String scanResult = data.getStringExtra(ScanConstants.ACTION_SCAN_KEY);
				this.sample4ET.setText(scanResult);
				
			}
		}
	}

}
