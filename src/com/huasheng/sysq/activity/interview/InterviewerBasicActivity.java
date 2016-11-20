package com.huasheng.sysq.activity.interview;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.util.BaseActivity;
import com.huasheng.sysq.util.RegexUtils;
import com.huasheng.sysq.util.SysqApplication;

public class InterviewerBasicActivity extends BaseActivity implements OnClickListener{
	
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
		
		//姓名
		String username = userET.getText().toString().trim();
		if(username.length() < 2){
			SysqApplication.showMessage("姓名不正确");
			return null;
		}
		interviewBasic.setUsername(username);
		
		//身份证
		String identityCard = identityCardET.getText().toString().trim();
		if(!RegexUtils.test("[0-9A-Za-z]{18}", identityCard)){
			SysqApplication.showMessage("身份证号码不正确");
			return null;
		}
		List<InterviewBasic> interviewBasicList = InterviewService.getAllInterviewBasic();
		if(interviewBasicList != null && interviewBasicList.size() > 0){
			for(InterviewBasic existInterviewBasic : interviewBasicList){
				if(identityCard.equals(existInterviewBasic.getIdentityCard())){
					SysqApplication.showMessage("身份证号码已存在");
					return null;
				}
			}
		}
		interviewBasic.setIdentityCard(identityCard);
		
		//省/自治区/直辖市
		String province = provinceET.getText().toString().trim();
		if(province.length() <1){
			SysqApplication.showMessage("省/自治区/直辖市不正确");
			return null;
		}
		interviewBasic.setProvince(province);
		
		//市/县/区
		String city = cityET.getText().toString().trim();
		if(city.length() < 1){
			SysqApplication.showMessage("市/县/区不正确");
			return null;
		}
		interviewBasic.setCity(city);
		
		//联系地址
		String address = addressET.getText().toString().trim();
		if(address.length() < 1){
			SysqApplication.showMessage("联系地址不正确");
			return null;
		}
		interviewBasic.setAddress(address);
		
		//邮政编码
		String postCode = postCodeET.getText().toString().trim();
		if(!RegexUtils.test("[0-9]{6}", postCode)){
			SysqApplication.showMessage("邮政编码不正确");
			return null;
		}
		interviewBasic.setPostCode(postCode);
		
		//联系电话
		String mobile = mobileET.getText().toString().trim();
		if(!RegexUtils.test("[0-9]{10,12}", mobile)){
			SysqApplication.showMessage("联系电话不正确");
			return null;
		}
		interviewBasic.setMobile(mobile);
		
		String familyAddress = familyAddressET.getText().toString().trim();
		interviewBasic.setFamilyAddress(familyAddress);
		
		//亲属联系电话
		String familyMobile = familyMobileET.getText().toString().trim();
		if(familyMobile.length()>0){
			if(!RegexUtils.test("[0-9]{10,12}", familyMobile)){
				SysqApplication.showMessage("亲属联系电话不正确");
				return null;
			}
		}
		interviewBasic.setFamilyMobile(familyMobile);

		String remark = remarkET.getText().toString().trim();
		interviewBasic.setRemark(remark);
		
		return interviewBasic;
	}

	
}
