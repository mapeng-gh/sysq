package com.huasheng.sysq.activity.interview;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.util.RegexUtils;
import com.huasheng.sysq.util.SysqApplication;

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
			
			//表单校验
			InterviewBasic interview= collectData();
			if(interview == null){
				return;
			}
			
			//跳转
			Intent intent = new Intent(this,InterviewerDNAActivity.class);
			intent.putExtra("interview", interview);
			startActivity(intent);
		}
	}
	
	private InterviewBasic collectData(){
		InterviewBasic interviewBasic = new InterviewBasic();
		
		String username = userET.getText().toString();
		if(StringUtils.isEmpty(StringUtils.trim(username))){
			SysqApplication.showMessage("姓名不能为空");
			return null;
		}
		interviewBasic.setUsername(username);
		
		String identityCard = identityCardET.getText().toString();
		if(!RegexUtils.test("[0-9A-Za-z]{18}", identityCard)){
			SysqApplication.showMessage("身份证必须是18位数字和字母组合");
			return null;
		}
		interviewBasic.setIdentityCard(identityCard);
		
		String province = provinceET.getText().toString();
		if(StringUtils.isEmpty(StringUtils.trim(province))){
			SysqApplication.showMessage("省/自治区/直辖市不能为空");
			return null;
		}
		interviewBasic.setProvince(province);
		
		String city = cityET.getText().toString();
		if(StringUtils.isEmpty(StringUtils.trim(city))){
			SysqApplication.showMessage("市/县/区不能为空");
			return null;
		}
		interviewBasic.setCity(city);
		
		String address = addressET.getText().toString();
		if(StringUtils.isEmpty(StringUtils.trim(address))){
			SysqApplication.showMessage("联系地址不能为空");
			return null;
		}
		interviewBasic.setAddress(address);
		
		String postCode = postCodeET.getText().toString();
		if(!RegexUtils.test("[0-9]{6}", postCode)){
			SysqApplication.showMessage("邮编必须是6位数字");
			return null;
		}
		interviewBasic.setPostCode(postCode);
		
		String mobile = mobileET.getText().toString();
		if(!RegexUtils.test("1[0-9]{10}", mobile)){
			SysqApplication.showMessage("联系电话必须是以1开头的11位数字");
			return null;
		}
		interviewBasic.setMobile(mobile);
		
		String familyAddress = familyAddressET.getText().toString();
		interviewBasic.setFamilyAddress(StringUtils.trim(familyAddress));
		
		String familyMobile = familyMobileET.getText().toString();
		if(!StringUtils.isEmpty(familyMobile)){
			if(!RegexUtils.test("1[0-9]{10}", familyMobile)){
				SysqApplication.showMessage("亲属联系电话必须是以1开头的11位数字");
				return null;
			}
		}
		interviewBasic.setFamilyMobile(familyMobile);

		String remark = remarkET.getText().toString();
		interviewBasic.setRemark(StringUtils.trim(remark));
		
		return interviewBasic;
	}

	
}
