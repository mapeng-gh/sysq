package com.huasheng.sysq.activity.interview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.Interview;

public class InterviewerBasicActivity extends Activity implements OnClickListener{
	
	private Button interviewBasicSubmitBtn;
	private EditText userET;
	private EditText identityCardET;
	private EditText provinceET;
	private EditText cityET;
	private EditText addressET;
	private EditText postCodeET;
	private EditText mobileET;
	private EditText familyAddressET;
	private EditText familyMobileET;
	private EditText remarkET;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_interview_basic);
		
		userET = (EditText)findViewById(R.id.interviewer_basic_username);
		identityCardET = (EditText)findViewById(R.id.interviewer_basic_identity_card);
		provinceET = (EditText)findViewById(R.id.interviewer_basic_province);
		cityET = (EditText)findViewById(R.id.interviewer_basic_city);
		addressET = (EditText)findViewById(R.id.interviewer_basic_address);
		postCodeET = (EditText)findViewById(R.id.interviewer_basic_post_code);
		mobileET = (EditText)findViewById(R.id.interviewer_basic_mobile);
		familyAddressET = (EditText)findViewById(R.id.interviewer_basic_family_address);
		familyMobileET = (EditText)findViewById(R.id.interviewer_basic_family_mobile);
		remarkET = (EditText)findViewById(R.id.interviewer_basic_remark);
		
		interviewBasicSubmitBtn = (Button)findViewById(R.id.interviewer_basic_submit_button);
		interviewBasicSubmitBtn.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View view) {
		if(view.getId() == R.id.interviewer_basic_submit_button){
			Intent intent = new Intent(this,InterviewerDNAActivity.class);
			Interview interview= collectData();
			intent.putExtra("interview", interview);
			startActivity(intent);
		}
	}
	
	private Interview collectData(){
		Interview interview = new Interview();
		interview.setUsername(userET.getText().toString());
		interview.setIdentityCard(identityCardET.getText().toString());
		interview.setProvince(provinceET.getText().toString());
		interview.setCity(cityET.getText().toString());
		interview.setAddress(addressET.getText().toString());
		interview.setPostCode(postCodeET.getText().toString());
		interview.setMobile(mobileET.getText().toString());
		interview.setFamilyAddress(familyAddressET.getText().toString());
		interview.setFamilyMobile(familyMobileET.getText().toString());
		interview.setRemark(remarkET.getText().toString());
		return interview;
	}

	
}
