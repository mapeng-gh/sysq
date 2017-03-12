package com.huasheng.sysq.activity.interviewee.person;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.util.RegexUtils;
import com.huasheng.sysq.util.SysqApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Visibility;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class IntervieweePerson4BasicActivity extends Activity implements OnClickListener{
	
	private int interviewBasicId;
	private Button saveBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_interviewee_person_basic);
		
		Intent intent = this.getIntent();
		this.interviewBasicId = intent.getIntExtra("interviewBasicId", -1);
		
		//终止的访谈不允许修改
		saveBtn = (Button)this.findViewById(R.id.btn_interviewee_questionaire_basic_submit);
		InterviewBasic interviewBasic = InterviewService.findInterviewBasicById(this.interviewBasicId);
		if(interviewBasic.getStatus() == InterviewBasic.STATUS_BREAK){
			saveBtn.setVisibility(View.GONE);
		}else{
			saveBtn.setOnClickListener(this);
		}
		
		//恢复表单值
		resume4IntervieweePersonBasic();
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btn_interviewee_questionaire_basic_submit){
			saveIntervieweePerson4Basic();
		}
	}
	/**
	 * 恢复表单值
	 */
	private void resume4IntervieweePersonBasic(){
		
		InterviewBasic interviewBasic = InterviewService.findInterviewBasicById(this.interviewBasicId);
		
		EditText userNameET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_username);
		userNameET.setText(interviewBasic.getUsername());
		
		EditText identityCardET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_identity_card);
		identityCardET.setText(interviewBasic.getIdentityCard());
		
		EditText provinceET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_province);
		provinceET.setText(interviewBasic.getProvince());
		
		EditText cityET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_city);
		cityET.setText(interviewBasic.getCity());
		
		EditText addressET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_address);
		addressET.setText(interviewBasic.getAddress());
		
		EditText postCodeET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_post_code);
		postCodeET.setText(interviewBasic.getPostCode());
		
		EditText mobileET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_mobile);
		mobileET.setText(interviewBasic.getMobile());
		
		EditText familyAddressET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_family_address);
		familyAddressET.setText(interviewBasic.getFamilyAddress());
		
		EditText familyMobileET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_family_mobile);
		familyMobileET.setText(interviewBasic.getFamilyMobile());
		
		EditText remarkET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_remark);
		remarkET.setText(interviewBasic.getRemark());
	}
	
	/**
	 * 保存基本信息
	 */
	private void saveIntervieweePerson4Basic(){
		
		InterviewBasic interviewBasic = InterviewService.findInterviewBasicById(this.interviewBasicId);
		
		//姓名
		EditText userNameET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_username);
		String userName = userNameET.getText().toString().trim();
		if(StringUtils.isEmpty(userName)){
			SysqApplication.showMessage("姓名不能为空");
			return;
		}
		if(userName.length() < 2){
			SysqApplication.showMessage("姓名至少为两个汉字");
			return;
		}
		interviewBasic.setUsername(userName);
		
		//身份证号码
		EditText identityCardET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_identity_card);
		String identityCard = identityCardET.getText().toString().trim();
		if(StringUtils.isEmpty(identityCard)){
			SysqApplication.showMessage("身份证号码不能为空");
			return;
		}
		if(!RegexUtils.test("[0-9A-Za-z]{18}", identityCard)){
			SysqApplication.showMessage("身份证号码格式不正确");
			return;
		}
		List<InterviewBasic> interviewBasicList = InterviewService.getAllInterviewBasic();
		if(interviewBasicList != null && interviewBasicList.size() > 0){
			for(InterviewBasic existInterviewBasic : interviewBasicList){
				if(existInterviewBasic.getId() != this.interviewBasicId){
					if(existInterviewBasic.getIdentityCard().equals(identityCard)){
						SysqApplication.showMessage("身份证号码已存在");
						return;
					}
				}
			}
		}
		interviewBasic.setIdentityCard(identityCard);
		
		//省/自治区/直辖市
		EditText provinceET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_province);
		String province = provinceET.getText().toString().trim();
		if(StringUtils.isEmpty(province)){
			SysqApplication.showMessage("省/自治区/直辖市不能为空");
			return;
		}
		interviewBasic.setProvince(province);
		
		//市/县/区
		EditText cityET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_city);
		String city = cityET.getText().toString().trim();
		if(StringUtils.isEmpty(city)){
			SysqApplication.showMessage("市/县/区不能为空");
			return;
		}
		interviewBasic.setCity(city);
		
		//联系地址
		EditText addressET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_address);
		String address = addressET.getText().toString().trim();
		if(StringUtils.isEmpty(address)){
			SysqApplication.showMessage("联系地址不能为空");
			return;
		}
		interviewBasic.setAddress(address);
		
		//邮政编码
		EditText postCodeET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_post_code);
		String postCode = postCodeET.getText().toString().trim();
		if(StringUtils.isEmpty(postCode)){
			SysqApplication.showMessage("邮政编码不能为空");
			return;
		}
		if(!RegexUtils.test("[0-9]{6}", postCode)){
			SysqApplication.showMessage("邮政编码格式不正确");
			return;
		}
		interviewBasic.setPostCode(postCode);
		
		//联系电话
		EditText mobileET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_mobile);
		String mobile = mobileET.getText().toString().trim();
		if(StringUtils.isEmpty(mobile)){
			SysqApplication.showMessage("联系电话不能为空");
			return;
		}
		if(!RegexUtils.test("[0-9]{10,12}", mobile)){
			SysqApplication.showMessage("联系电话格式不正确");
			return;
		}
		interviewBasic.setMobile(mobile);
		
		//本地亲属联系地址
		EditText familyAddressET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_family_address);
		String familyAddress = familyAddressET.getText().toString().trim();
		interviewBasic.setFamilyAddress(familyAddress);
		
		//本地亲属联系电话
		EditText familyMobileET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_family_mobile);
		String familyMobile = familyMobileET.getText().toString().trim();
		if(!StringUtils.isEmpty(familyMobile)){
			if(!RegexUtils.test("[0-9]{10,12}", familyMobile)){
				SysqApplication.showMessage("本地亲属联系电话格式不正确");
				return;
			}
		}
		interviewBasic.setFamilyMobile(familyMobile);
		
		//备注
		EditText remarkET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_remark);
		String remark = remarkET.getText().toString().trim();
		interviewBasic.setRemark(remark);
		
		InterviewService.updateInterviewBasic(interviewBasic);
		SysqApplication.showMessage("保存成功");
	}

}
