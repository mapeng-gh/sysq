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
import android.widget.RadioButton;
import android.widget.Toast;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.util.InterviewConstants;
import com.huasheng.sysq.util.InterviewContext;
import com.huasheng.sysq.util.SysqApplication;

public class InterviewerDNAActivity extends Activity implements OnClickListener{
	
	private Button submitInterviewBtn;
	private EditText sample1ET;
	private EditText sample2ET;
	private EditText sample3ET;
	private EditText sample4ET;
	private RadioButton testRB;
	private RadioButton caseRB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_interview_dna);
		
		sample1ET = (EditText)findViewById(R.id.interviewer_dna_sample1);
		sample2ET = (EditText)findViewById(R.id.interviewer_dna_sample2);
		sample3ET = (EditText)findViewById(R.id.interviewer_dna_sample3);
		sample4ET = (EditText)findViewById(R.id.interviewer_dna_sample4);
		testRB = (RadioButton)findViewById(R.id.interviewer_dna_type_test);
		caseRB = (RadioButton)findViewById(R.id.interviewer_dna_questionaire_type_case);
		
		submitInterviewBtn = (Button)findViewById(R.id.interviewer_dna_submit_button);
		submitInterviewBtn.setOnClickListener(this);
	}


	@Override
	public void onClick(View view) {
		if(view.getId() == R.id.interviewer_dna_submit_button){
			
			//新建访问记录
			InterviewBasic interviewBasic = (InterviewBasic)getIntent().getSerializableExtra("interview");
			collectData(interviewBasic);
			InterviewService.newInterviewBasic(interviewBasic);
			SysqApplication.showMessage("保存成功");
			
			//保存到上下文
			InterviewContext.setCurInterviewBasic(interviewBasic);
			
			//跳转问卷页
			Intent intent = new Intent(this,InterviewActivity.class);
			startActivity(intent);
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
	
	

}
