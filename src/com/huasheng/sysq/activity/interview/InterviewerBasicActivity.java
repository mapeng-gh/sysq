package com.huasheng.sysq.activity.interview;

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

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.util.InterviewConstants;
import com.huasheng.sysq.util.InterviewContext;
import com.huasheng.sysq.util.RegexUtils;
import com.huasheng.sysq.util.SysqApplication;

public class InterviewerBasicActivity extends Activity implements OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_interview_basic);
		
		Button interviewBasicSubmitBtn = (Button)findViewById(R.id.interviewer_basic_submit_button);
		interviewBasicSubmitBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		if(view.getId() == R.id.interviewer_basic_submit_button){
			submitInterivewBasic();
		}
	}
	
	/**
	 *  提交访谈基本信息
	 */
	private void submitInterivewBasic(){
		
		//表单校验
		InterviewBasic interviewBasic= checkInterviewBasicFormData();
		if(interviewBasic == null){
			return;
		}
		
		//新建访问记录
		InterviewService.newInterviewBasic(interviewBasic);
		
		//保存到上下文
		InterviewContext.setCurInterviewBasic(interviewBasic);
		
		//跳转问卷页
		Intent interviewIntent = new Intent(this,InterviewActivity.class);
		this.startActivity(interviewIntent);
	}
	
	/**
	 * 表单校验
	 * @return
	 */
	private InterviewBasic checkInterviewBasicFormData(){
		
		InterviewBasic interviewBasic = new InterviewBasic();
		
		//姓名
		EditText userNameET = (EditText)findViewById(R.id.interviewer_basic_username);
		String userName = userNameET.getText().toString().trim();
		if(StringUtils.isEmpty(userName)){
			SysqApplication.showMessage("姓名不能为空");
			return null;
		}
		if(userName.length() < 2){
			SysqApplication.showMessage("姓名至少为两个汉字");
			return null;
		}
		interviewBasic.setUsername(userName);
		
		//身份证
		EditText identityCardET = (EditText)findViewById(R.id.interviewer_basic_identity_card);
		String identityCard = identityCardET.getText().toString().trim();
		if(StringUtils.isEmpty(identityCard)){
			SysqApplication.showMessage("身份证号码不能为空");
			return null;
		}
		if(!RegexUtils.test("[0-9A-Za-z]{18}", identityCard)){
			SysqApplication.showMessage("身份证号码格式不正确");
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
		EditText provinceET = (EditText)findViewById(R.id.interviewer_basic_province);
		String province = provinceET.getText().toString().trim();
		if(StringUtils.isEmpty(province)){
			SysqApplication.showMessage("省/自治区/直辖市不能为空");
			return null;
		}
		interviewBasic.setProvince(province);
		
		//市/县/区
		EditText cityET = (EditText)findViewById(R.id.interviewer_basic_city);
		String city = cityET.getText().toString().trim();
		if(StringUtils.isEmpty(city)){
			SysqApplication.showMessage("市/县/区不能为空");
			return null;
		}
		interviewBasic.setCity(city);
		
		//联系地址
		EditText addressET = (EditText)findViewById(R.id.interviewer_basic_address);
		String address = addressET.getText().toString().trim();
		if(StringUtils.isEmpty(address)){
			SysqApplication.showMessage("联系地址不能为空");
			return null;
		}
		interviewBasic.setAddress(address);
		
		//邮政编码
		EditText postCodeET = (EditText)findViewById(R.id.interviewer_basic_post_code);
		String postCode = postCodeET.getText().toString().trim();
		if(StringUtils.isEmpty(postCode)){
			SysqApplication.showMessage("邮政编码不能为空");
			return null;
		}
		if(!RegexUtils.test("[0-9]{6}", postCode)){
			SysqApplication.showMessage("邮政编码格式不正确");
			return null;
		}
		interviewBasic.setPostCode(postCode);
		
		//联系电话
		EditText mobileET = (EditText)findViewById(R.id.interviewer_basic_mobile);
		String mobile = mobileET.getText().toString().trim();
		if(StringUtils.isEmpty(mobile)){
			SysqApplication.showMessage("联系电话不能为空");
			return null;
		}
		if(!RegexUtils.test("[0-9]{10,12}", mobile)){
			SysqApplication.showMessage("联系电话格式不正确");
			return null;
		}
		interviewBasic.setMobile(mobile);
		
		//本地亲属联系地址
		EditText familyAddressET = (EditText)findViewById(R.id.interviewer_basic_family_address);
		String familyAddress = familyAddressET.getText().toString().trim();
		interviewBasic.setFamilyAddress(familyAddress);
		
		//本地亲属联系电话
		EditText familyMobileET = (EditText)findViewById(R.id.interviewer_basic_family_mobile);
		String familyMobile = familyMobileET.getText().toString().trim();
		if(!StringUtils.isEmpty(familyMobile)){
			if(!RegexUtils.test("[0-9]{10,12}", familyMobile)){
				SysqApplication.showMessage("本地亲属联系电话格式不正确");
				return null;
			}
		}
		interviewBasic.setFamilyMobile(familyMobile);
		
		//访谈类型
		RadioButton isTrueRB = (RadioButton)findViewById(R.id.interviewer_dna_type_true);
		interviewBasic.setIsTest(isTrueRB.isChecked() ? InterviewBasic.TEST_NO : InterviewBasic.TEST_YES);
		
		//问卷类型
		RadioButton isCaseRB = (RadioButton)findViewById(R.id.interviewer_dna_questionaire_type_case);
		interviewBasic.setType(isCaseRB.isChecked() ? InterviewConstants.TYPE_CASE : InterviewConstants.TYPE_CONTRAST);
		
		//备注
		EditText remarkET = (EditText)findViewById(R.id.interviewer_basic_remark);
		String remark = remarkET.getText().toString().trim();
		interviewBasic.setRemark(remark);
		
		return interviewBasic;
	}

	
}
