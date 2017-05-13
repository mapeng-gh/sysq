package com.huasheng.sysq.activity.interviewee.person;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.InterviewBasicWrap;
import com.huasheng.sysq.model.Interviewee;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.util.CommonUtils;
import com.huasheng.sysq.util.DialogUtils;
import com.huasheng.sysq.util.MysqlUtils;
import com.huasheng.sysq.util.SysqApplication;
import com.huasheng.sysq.util.upload.UploadConstants;

public class IntervieweePerson4BasicActivity extends Activity implements OnClickListener{
	
	private int interviewBasicId;
	private Button saveBtn;
	
	private Handler handler;
	private static final int MESSAGE_TOAST = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_interviewee_person_basic);
		
		Intent intent = this.getIntent();
		this.interviewBasicId = intent.getIntExtra("interviewBasicId", -1);
		
		//终止的访谈不允许修改
		saveBtn = (Button)this.findViewById(R.id.btn_interviewee_questionaire_basic_submit);
		InterviewBasicWrap interviewBasicWrap = InterviewService.findInterviewBasicById(this.interviewBasicId);
		if(interviewBasicWrap.getInterviewBasic().getStatus() == InterviewBasic.STATUS_BREAK){
			saveBtn.setVisibility(View.GONE);
		}else{
			saveBtn.setOnClickListener(this);
		}
		
		//恢复表单值
		resume4IntervieweePersonBasic();
		
		this.handleMessage();
	}
	
	/**
	 * 消息处理
	 */
	private void handleMessage(){
		
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what == MESSAGE_TOAST){
					DialogUtils.showLongToast(IntervieweePerson4BasicActivity.this, msg.obj.toString());
				}
			}
		};
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
		
		InterviewBasicWrap interviewBasicWrap = InterviewService.findInterviewBasicById(this.interviewBasicId);
		Interviewee interviewee = interviewBasicWrap.getInterviewee();
		
		EditText userNameET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_username);
		userNameET.setText(interviewee.getUsername());
		
		EditText identityCardET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_identity_card);
		identityCardET.setText(interviewee.getIdentityCard());
		
		EditText provinceET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_province);
		provinceET.setText(interviewee.getProvince());
		
		EditText cityET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_city);
		cityET.setText(interviewee.getCity());
		
		EditText addressET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_address);
		addressET.setText(interviewee.getAddress());
		
		EditText postCodeET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_post_code);
		postCodeET.setText(interviewee.getPostCode());
		
		EditText mobileET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_mobile);
		mobileET.setText(interviewee.getMobile());
		
		EditText familyAddressET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_family_address);
		familyAddressET.setText(interviewee.getFamilyAddress());
		
		EditText familyMobileET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_family_mobile);
		familyMobileET.setText(interviewee.getFamilyMobile());
		
		EditText remarkET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_remark);
		remarkET.setText(interviewee.getRemark());
	}
	
	/**
	 * 保存基本信息
	 */
	private void saveIntervieweePerson4Basic(){
		
		//身份证校验需要远程校验，所以新开线程
		new Thread(new Runnable() {
			@Override
			public void run() {
				doSaveIntervieweePerson4Basic();
			}
		}).start();
		
		
	}
	
	private void doSaveIntervieweePerson4Basic(){
		
		InterviewBasicWrap interviewBasicWrap = InterviewService.findInterviewBasicById(this.interviewBasicId);
		Interviewee interviewee = interviewBasicWrap.getInterviewee();
		
		//姓名
		EditText userNameET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_username);
		String userName = userNameET.getText().toString().trim();
		if(StringUtils.isEmpty(userName)){
			CommonUtils.sendMessage(handler, MESSAGE_TOAST, "姓名不能为空");
			return;
		}
		if(userName.length() < 2){
			CommonUtils.sendMessage(handler, MESSAGE_TOAST, "姓名至少为两个汉字");
			return;
		}
		interviewee.setUsername(userName);
		
		//身份证号码
		EditText identityCardET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_identity_card);
		String identityCard = identityCardET.getText().toString().trim();
		if(StringUtils.isEmpty(identityCard)){
			CommonUtils.sendMessage(handler, MESSAGE_TOAST, "身份证号码不能为空");
			return;
		}
		if(!CommonUtils.checkIdentityCard(identityCard)){
			CommonUtils.sendMessage(handler, MESSAGE_TOAST, "身份证号码格式不正确");
			return;
		}
		
		//身份证：本地校验
		List<Interviewee> intervieweeList = InterviewService.getAllInterviewee();
		if(intervieweeList != null && intervieweeList.size() > 0){
			for(Interviewee existInterviewee : intervieweeList){
				if(identityCard.equals(existInterviewee.getIdentityCard()) && !identityCard.equals(interviewee.getIdentityCard())){
					CommonUtils.sendMessage(handler, MESSAGE_TOAST, "身份证号码在本地已存在");
					return;
				}
			}
		}
		
		//身份证：远程校验
		if(!identityCard.equals(interviewee.getIdentityCard())){
			List<Map<String,String>> patient4Old = MysqlUtils.selectPatientByIdentityCard(identityCard);
			if(patient4Old != null && patient4Old.size() > 0){
				CommonUtils.sendMessage(handler, MESSAGE_TOAST, "身份证号码在本期远程库里已存在");
				return;
			}
		}
		
		//身份证：一期校验
		if(!identityCard.equals(interviewee.getIdentityCard())){
			List<Map<String,String>> patient4Old = MysqlUtils.selectPatientByIdentityCard4Old(identityCard);
			if(patient4Old != null && patient4Old.size() > 0){
				CommonUtils.sendMessage(handler, MESSAGE_TOAST, "身份证号码在一期库里已存在");
				return;
			}
		}
		
		interviewee.setIdentityCard(identityCard);
		
		//省/自治区/直辖市
		EditText provinceET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_province);
		String province = provinceET.getText().toString().trim();
		if(StringUtils.isEmpty(province)){
			CommonUtils.sendMessage(handler, MESSAGE_TOAST, "省/自治区/直辖市不能为空");
			return;
		}
		interviewee.setProvince(province);
		
		//市/县/区
		EditText cityET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_city);
		String city = cityET.getText().toString().trim();
		if(StringUtils.isEmpty(city)){
			CommonUtils.sendMessage(handler, MESSAGE_TOAST, "市/县/区不能为空");
			return;
		}
		interviewee.setCity(city);
		
		//联系地址
		EditText addressET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_address);
		String address = addressET.getText().toString().trim();
		if(StringUtils.isEmpty(address)){
			CommonUtils.sendMessage(handler, MESSAGE_TOAST, "联系地址不能为空");
			return;
		}
		interviewee.setAddress(address);
		
		//邮政编码
		EditText postCodeET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_post_code);
		String postCode = postCodeET.getText().toString().trim();
		if(StringUtils.isEmpty(postCode)){
			CommonUtils.sendMessage(handler, MESSAGE_TOAST, "邮政编码不能为空");
			return;
		}
		if(!CommonUtils.test("[0-9]{6}", postCode)){
			CommonUtils.sendMessage(handler, MESSAGE_TOAST, "邮政编码格式不正确");
			return;
		}
		interviewee.setPostCode(postCode);
		
		//联系电话
		EditText mobileET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_mobile);
		String mobile = mobileET.getText().toString().trim();
		if(StringUtils.isEmpty(mobile)){
			CommonUtils.sendMessage(handler, MESSAGE_TOAST, "联系电话不能为空");
			return;
		}
		if(!CommonUtils.test("[0-9]{10,12}", mobile)){
			CommonUtils.sendMessage(handler, MESSAGE_TOAST, "联系电话格式不正确");
			return;
		}
		interviewee.setMobile(mobile);
		
		//本地亲属联系地址
		EditText familyAddressET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_family_address);
		String familyAddress = familyAddressET.getText().toString().trim();
		interviewee.setFamilyAddress(familyAddress);
		
		//本地亲属联系电话
		EditText familyMobileET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_family_mobile);
		String familyMobile = familyMobileET.getText().toString().trim();
		if(!StringUtils.isEmpty(familyMobile)){
			if(!CommonUtils.test("[0-9]{10,12}", familyMobile)){
				CommonUtils.sendMessage(handler, MESSAGE_TOAST, "本地亲属联系电话格式不正确");
				return;
			}
		}
		interviewee.setFamilyMobile(familyMobile);
		
		//备注
		EditText remarkET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_remark);
		String remark = remarkET.getText().toString().trim();
		interviewee.setRemark(remark);
		
		if(interviewee.getUploadStatus() == UploadConstants.upload_status_uploaded){
			interviewee.setUploadStatus(UploadConstants.upload_status_modified);
		}
		
		//保存
		InterviewService.updateInterviewee(interviewee);
		CommonUtils.sendMessage(handler, MESSAGE_TOAST, "保存成功");
	}

}
