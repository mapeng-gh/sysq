package com.huasheng.sysq.activity.interviewee.person;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.huasheng.sysq.R;
import com.huasheng.sysq.activity.interview.InterviewDialogAdapter;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.InterviewBasicWrap;
import com.huasheng.sysq.model.Interviewee;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.util.CommonUtils;
import com.huasheng.sysq.util.DialogUtils;
import com.huasheng.sysq.util.PatientsDataUtils;
import com.huasheng.sysq.util.upload.UploadConstants;

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
		InterviewBasicWrap interviewBasicWrap = InterviewService.findInterviewBasicById(this.interviewBasicId);
		if(interviewBasicWrap.getInterviewBasic().getStatus() == InterviewBasic.STATUS_BREAK){
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
			submitIntervieweePerson4Basic();
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
	 * 提交
	 */
	private void submitIntervieweePerson4Basic(){
		
		//姓名
		EditText userNameET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_username);
		String userName = userNameET.getText().toString().trim();
		if(StringUtils.isEmpty(userName)){
			DialogUtils.showLongToast(this, "姓名不能为空");
			return;
		}
		if(userName.length() < 2){
			DialogUtils.showLongToast(this, "姓名至少为两个汉字");
			return;
		}
		
		//身份证号码
		EditText identityCardET = (EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_identity_card);
		String identityCard = identityCardET.getText().toString().trim();
		if(StringUtils.isEmpty(identityCard)){
			DialogUtils.showLongToast(this, "身份证号码不能为空");
			return;
		}
		if(!CommonUtils.checkIdentityCard(identityCard)){
			DialogUtils.showLongToast(this, "身份证号码格式不正确");
			return;
		}
		
		//身份证排重
		InterviewBasicWrap interviewBasicWrap = InterviewService.findInterviewBasicById(this.interviewBasicId);
		if(interviewBasicWrap.getInterviewBasic().getIsTest() == InterviewBasic.TEST_NO &&
				!identityCard.equals(interviewBasicWrap.getInterviewee().getIdentityCard())){//测试数据或未修改不进行排重
			
			//本地校验
			List<InterviewBasicWrap> interviewBasicWrapList = InterviewService.getAllInterviewBasic();
			if(interviewBasicWrapList != null && interviewBasicWrapList.size() > 0){
				for(InterviewBasicWrap existInterviewBasicWrap : interviewBasicWrapList){
					if(existInterviewBasicWrap.getInterviewBasic().getIsTest() == InterviewBasic.TEST_NO && identityCard.equals(existInterviewBasicWrap.getInterviewee().getIdentityCard())){
						DialogUtils.showLongToast(this, "身份证号码在本地已存在");
						return;
					}
				}
			}
			
			//一期数据校验（身份证）
			List<Map<String,String>> patientByIdentityCardList = PatientsDataUtils.getPatientListByIdentityCard(this, identityCard);
			if(patientByIdentityCardList != null && patientByIdentityCardList.size() > 0){
				DialogUtils.showLongToast(this, "身份证号码在一期库里已存在");
				return;
			}
			
			//一期数据校验（姓名）
			List<Map<String,String>> patientByUsernameList = PatientsDataUtils.getPatientListByUsername(this, userName);
			if(patientByUsernameList != null && patientByUsernameList.size() > 0){
				View view = LayoutInflater.from(IntervieweePerson4BasicActivity.this).inflate(R.layout.interview_dialog, null);
				ListView dialogLV = (ListView)view.findViewById(R.id.interviewDialogLV);
				InterviewDialogAdapter dialogAdapter = new InterviewDialogAdapter(IntervieweePerson4BasicActivity.this,R.layout.item_interview_dialog,patientByUsernameList);
				dialogLV.setAdapter(dialogAdapter);
				AlertDialog dialog = DialogUtils.showCustomDialog(IntervieweePerson4BasicActivity.this, "请您核对本次访谈对象是否曾经被访谈过",view,"继续",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						//后续校验
						submitIntervieweePerson4BasicAfterIdentityCard();
					}
				},"取消",null);
				WindowManager.LayoutParams params = dialog.getWindow().getAttributes();    
				params.width = 1500;    
				dialog.getWindow().setAttributes(params);
				
			}else{
				//后续校验
				submitIntervieweePerson4BasicAfterIdentityCard();
			}
		}else{
			//后续校验
			submitIntervieweePerson4BasicAfterIdentityCard();
		}
	}
	
	private void submitIntervieweePerson4BasicAfterIdentityCard(){
		
		//省/自治区/直辖市
		EditText provinceET = (EditText)findViewById(R.id.et_interviewee_questionaire_basic_province);
		String province = provinceET.getText().toString().trim();
		if(StringUtils.isEmpty(province)){
			DialogUtils.showLongToast(IntervieweePerson4BasicActivity.this, "省/自治区/直辖市不能为空");
			return;
		}
		
		//市/县/区
		EditText cityET = (EditText)findViewById(R.id.et_interviewee_questionaire_basic_city);
		String city = cityET.getText().toString().trim();
		if(StringUtils.isEmpty(city)){
			DialogUtils.showLongToast(IntervieweePerson4BasicActivity.this, "市/县/区不能为空");
			return;
		}
		
		//联系地址
		EditText addressET = (EditText)findViewById(R.id.et_interviewee_questionaire_basic_address);
		String address = addressET.getText().toString().trim();
		if(StringUtils.isEmpty(address)){
			DialogUtils.showLongToast(IntervieweePerson4BasicActivity.this, "联系地址不能为空");
			return;
		}
		
		//邮政编码
		EditText postCodeET = (EditText)findViewById(R.id.et_interviewee_questionaire_basic_post_code);
		String postCode = postCodeET.getText().toString().trim();
		if(StringUtils.isEmpty(postCode)){
			DialogUtils.showLongToast(IntervieweePerson4BasicActivity.this, "邮政编码不能为空");
			return;
		}
		if(!CommonUtils.test("[0-9]{6}", postCode)){
			DialogUtils.showLongToast(IntervieweePerson4BasicActivity.this, "邮政编码格式不正确");
			return;
		}
		
		//联系电话
		EditText mobileET = (EditText)findViewById(R.id.et_interviewee_questionaire_basic_mobile);
		String mobile = mobileET.getText().toString().trim();
		if(StringUtils.isEmpty(mobile)){
			DialogUtils.showLongToast(IntervieweePerson4BasicActivity.this, "联系电话不能为空");
			return;
		}
		if(!CommonUtils.test("[0-9]{10,12}", mobile)){
			DialogUtils.showLongToast(IntervieweePerson4BasicActivity.this, "联系电话格式不正确");
			return;
		}
		
		//本地亲属联系电话
		EditText familyMobileET = (EditText)findViewById(R.id.et_interviewee_questionaire_basic_family_mobile);
		String familyMobile = familyMobileET.getText().toString().trim();
		if(!StringUtils.isEmpty(familyMobile)){
			if(!CommonUtils.test("[0-9]{10,12}", familyMobile)){
				DialogUtils.showLongToast(IntervieweePerson4BasicActivity.this, "本地亲属联系电话格式不正确");
				return;
			}
		}
		
		//保存
		saveIntervieweePerson4Basic();
	}
	
	/**
	 * 保存
	 */
	private void saveIntervieweePerson4Basic(){
		
		InterviewBasicWrap interviewBasicWrap = InterviewService.findInterviewBasicById(this.interviewBasicId);
		Interviewee interviewee = interviewBasicWrap.getInterviewee();
		interviewee.setUsername(((EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_username)).getText().toString().trim());
		interviewee.setIdentityCard(((EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_identity_card)).getText().toString().trim());
		interviewee.setProvince(((EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_province)).getText().toString().trim());
		interviewee.setCity(((EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_city)).getText().toString().trim());
		interviewee.setAddress(((EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_address)).getText().toString().trim());
		interviewee.setPostCode(((EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_post_code)).getText().toString().trim());
		interviewee.setMobile(((EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_mobile)).getText().toString().trim());
		interviewee.setFamilyAddress(((EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_family_address)).getText().toString().trim());
		interviewee.setFamilyMobile(((EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_family_mobile)).getText().toString().trim());
		interviewee.setRemark(((EditText)this.findViewById(R.id.et_interviewee_questionaire_basic_remark)).getText().toString().trim());
		if(interviewee.getUploadStatus() == UploadConstants.upload_status_uploaded){
			interviewee.setUploadStatus(UploadConstants.upload_status_modified);
		}
		InterviewService.updateInterviewee(interviewee);
		DialogUtils.showLongToast(this, "保存成功");
	}

}
