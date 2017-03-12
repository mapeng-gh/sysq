package com.huasheng.sysq.activity.interviewee.person;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.huasheng.sysq.R;
import com.huasheng.sysq.activity.interviewee.IntervieweeActivity;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.util.ScanConstants;
import com.huasheng.sysq.util.SysqApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class IntervieweePerson4DNAActivity extends Activity implements OnClickListener{
	
	private int interviewBasicId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_interviewee_person_dna);
		
		Intent intent = this.getIntent();
		this.interviewBasicId = intent.getIntExtra("interviewBasicId", -1);
		
		ImageButton sample1ImgBtn = (ImageButton)findViewById(R.id.btn_interview_dna_sample1);
		ImageButton sample2ImgBtn = (ImageButton)findViewById(R.id.btn_interview_dna_sample2);
		ImageButton sample3ImgBtn = (ImageButton)findViewById(R.id.btn_interview_dna_sample3);
		ImageButton sample4ImgBtn = (ImageButton)findViewById(R.id.btn_interview_dna_sample4);
		sample1ImgBtn.setOnClickListener(this);
		sample2ImgBtn.setOnClickListener(this);
		sample3ImgBtn.setOnClickListener(this);
		sample4ImgBtn.setOnClickListener(this);
		
		Button submitInterviewBtn = (Button)findViewById(R.id.interviewee_person_dna_submit);
		submitInterviewBtn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		
		String tag = v.getTag().toString();
		if(tag.equals("scan")){//扫描DNA
			this.scanDNA(v);
			
		}else{
			if(v.getId() == R.id.interviewee_person_dna_submit){//保存DNA
				this.saveDNA();
			}
		}
	}
	
	/**
	 * 保存DNA
	 */
	private void saveDNA(){
		
		//校验
		List<String> dnaList = new ArrayList<String>();
		
		EditText sample1ET = (EditText)findViewById(R.id.et_interview_dna_sample1);
		String sample1 = sample1ET.getText().toString().trim();
		if(!StringUtils.isEmpty(sample1)){
			dnaList.add(sample1);
		}
		
		EditText sample2ET = (EditText)findViewById(R.id.et_interview_dna_sample2);
		String sample2 = sample2ET.getText().toString().trim();
		if(!StringUtils.isEmpty(sample2)){
			dnaList.add(sample2);
		}
		
		EditText sample3ET = (EditText)findViewById(R.id.et_interview_dna_sample3);
		String sample3 = sample3ET.getText().toString().trim();
		if(!StringUtils.isEmpty(sample3)){
			dnaList.add(sample3);
		}
		
		EditText sample4ET = (EditText)findViewById(R.id.et_interview_dna_sample4);
		String sample4 = sample4ET.getText().toString().trim();
		if(!StringUtils.isEmpty(sample4)){
			dnaList.add(sample4);
		}
		
		if(dnaList.size() == 0){
			SysqApplication.showMessage("请先扫描DNA样本");
			return;
		}
		
		//保存
		InterviewBasic interviewBasic = InterviewService.findInterviewBasicById(this.interviewBasicId);
		interviewBasic.setDna(StringUtils.join(dnaList, ","));
		InterviewService.updateInterviewBasic(interviewBasic);
		SysqApplication.showMessage("保存成功");
	}
	
	
	/**
	 * 扫描DNA
	 * @param v
	 */
	private void scanDNA(View v){
		
		 if(v.getId() == R.id.btn_interview_dna_sample1){
				
				Intent intent = new Intent(ScanConstants.INTENT_ACTION_SCAN);
				this.startActivityForResult(intent, 1);
				
			}else if(v.getId() == R.id.btn_interview_dna_sample2){
				
				Intent intent = new Intent(ScanConstants.INTENT_ACTION_SCAN);
				this.startActivityForResult(intent, 2);
				
			}else if(v.getId() == R.id.btn_interview_dna_sample3){
				
				Intent intent = new Intent(ScanConstants.INTENT_ACTION_SCAN);
				this.startActivityForResult(intent, 3);
				
			}else if(v.getId() == R.id.btn_interview_dna_sample4){
				
				Intent intent = new Intent(ScanConstants.INTENT_ACTION_SCAN);
				this.startActivityForResult(intent, 4);
			}
	}
	
	/**
	 * 扫描回调
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(resultCode == Activity.RESULT_OK){
			
			if(requestCode == 1){
				
				String scanResult = data.getStringExtra(ScanConstants.ACTION_SCAN_KEY);
				EditText sample1ET = (EditText)findViewById(R.id.et_interview_dna_sample1);
				sample1ET.setText(scanResult);
				
			}else if(requestCode == 2){
				
				String scanResult = data.getStringExtra(ScanConstants.ACTION_SCAN_KEY);
				EditText sample2ET = (EditText)findViewById(R.id.et_interview_dna_sample2);
				sample2ET.setText(scanResult);
				
			}else if(requestCode == 3){
				
				String scanResult = data.getStringExtra(ScanConstants.ACTION_SCAN_KEY);
				EditText sample3ET = (EditText)findViewById(R.id.et_interview_dna_sample3);
				sample3ET.setText(scanResult);
				
			}else if(requestCode == 4){
				
				String scanResult = data.getStringExtra(ScanConstants.ACTION_SCAN_KEY);
				EditText sample4ET = (EditText)findViewById(R.id.et_interview_dna_sample4);
				sample4ET.setText(scanResult);
				
			}
		}
	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this,IntervieweePersonNavActivity.class);
		intent.putExtra("interviewBasicId", this.interviewBasicId);
		this.startActivity(intent);
	}
}
