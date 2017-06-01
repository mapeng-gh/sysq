package com.huasheng.sysq.activity.interview;

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
import android.widget.RadioButton;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.InterviewBasicWrap;
import com.huasheng.sysq.model.Interviewee;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.util.CommonUtils;
import com.huasheng.sysq.util.DialogUtils;
import com.huasheng.sysq.util.PatientsDataUtils;
import com.huasheng.sysq.util.SysqContext;
import com.huasheng.sysq.util.interview.InterviewConstants;
import com.huasheng.sysq.util.interview.InterviewContext;
import com.huasheng.sysq.util.upload.UploadConstants;

public class IntervieweBasicActivity extends Activity implements OnClickListener{
	
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
	 * 开始访谈
	 */
	private void newInterviewBasic(){
		
		//新建被访问者
		Interviewee interviewee = new Interviewee();
		interviewee.setUsername( ((EditText)findViewById(R.id.interviewer_basic_username)).getText().toString().trim() );
		interviewee.setIdentityCard( ((EditText)findViewById(R.id.interviewer_basic_identity_card)).getText().toString().trim() );
		interviewee.setMobile( ((EditText)findViewById(R.id.interviewer_basic_mobile)).getText().toString().trim() );
		interviewee.setProvince( ((EditText)findViewById(R.id.interviewer_basic_province)).getText().toString().trim() );
		interviewee.setCity( ((EditText)findViewById(R.id.interviewer_basic_city)).getText().toString().trim() );
		interviewee.setAddress(((EditText)findViewById(R.id.interviewer_basic_address)).getText().toString().trim());
		interviewee.setPostCode(((EditText)findViewById(R.id.interviewer_basic_post_code)).getText().toString().trim());
		interviewee.setFamilyMobile(((EditText)findViewById(R.id.interviewer_basic_family_mobile)).getText().toString().trim());
		interviewee.setFamilyAddress(((EditText)findViewById(R.id.interviewer_basic_family_address)).getText().toString().trim());
		interviewee.setRemark(((EditText)findViewById(R.id.interviewer_basic_remark)).getText().toString().trim());
		interviewee.setUploadStatus(UploadConstants.upload_status_not_upload);
		InterviewService.newInterviewee(interviewee);
		
		
		//新建访问记录
		InterviewBasic interviewBasic = new InterviewBasic();
		RadioButton isTrueRB = (RadioButton)findViewById(R.id.interviewer_dna_type_true);
		interviewBasic.setIsTest(isTrueRB.isChecked() ? InterviewBasic.TEST_NO : InterviewBasic.TEST_YES);
		RadioButton isCaseRB = (RadioButton)findViewById(R.id.interviewer_dna_questionaire_type_case);
		interviewBasic.setType(isCaseRB.isChecked() ? InterviewConstants.TYPE_CASE : InterviewConstants.TYPE_CONTRAST);
		interviewBasic.setIntervieweeId(interviewee.getId());
		interviewBasic.setInterviewerId(SysqContext.getInterviewer().getId());
		interviewBasic.setVersionId(SysqContext.getCurrentVersion().getId());
		interviewBasic.setStartTime(CommonUtils.getCurDateTime());
		interviewBasic.setStatus(InterviewBasic.STATUS_DOING);
		interviewBasic.setUploadStatus(UploadConstants.upload_status_not_upload);
		InterviewService.newInterviewBasic(interviewBasic);
		
		//保存到上下文
		InterviewBasicWrap interviewBasicWrap = new InterviewBasicWrap();
		interviewBasicWrap.setInterviewBasic(interviewBasic);
		interviewBasicWrap.setInterviewee(interviewee);
		interviewBasicWrap.setInterviewer(SysqContext.getInterviewer());
		InterviewContext.setCurInterviewBasicWrap(interviewBasicWrap);
		
		//跳转问卷页
		Intent interviewIntent = new Intent(IntervieweBasicActivity.this,InterviewActivity.class);
		IntervieweBasicActivity.this.startActivity(interviewIntent);
	}
	
	/**
	 * 提交
	 */
	private void submitInterivewBasic(){
		
		//姓名
		EditText userNameET = (EditText)findViewById(R.id.interviewer_basic_username);
		String userName = userNameET.getText().toString().trim();
		if(StringUtils.isEmpty(userName)){
			DialogUtils.showLongToast(this, "姓名不能为空");
			return;
		}
		if(userName.length() < 2){
			DialogUtils.showLongToast(this, "姓名至少为两个汉字");
			return;
		}
		
		//身份证：基本格式校验
		EditText identityCardET = (EditText)findViewById(R.id.interviewer_basic_identity_card);
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
		RadioButton isTrueRB = (RadioButton)findViewById(R.id.interviewer_dna_type_true);
		if(isTrueRB.isChecked()){//测试数据不需排重
			
			//本地校验（身份证号码）
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
				View view = LayoutInflater.from(IntervieweBasicActivity.this).inflate(R.layout.interview_dialog, null);
				ListView dialogLV = (ListView)view.findViewById(R.id.interviewDialogLV);
				InterviewDialogAdapter dialogAdapter = new InterviewDialogAdapter(IntervieweBasicActivity.this,R.layout.item_interview_dialog,patientByUsernameList);
				dialogLV.setAdapter(dialogAdapter);
				AlertDialog dialog = DialogUtils.showCustomDialog(IntervieweBasicActivity.this, "请您核对本次访谈对象是否曾经被访谈过",view,"继续",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						//后续校验
						submitInterivewBasicAfterIdentityCard();
					}
				},"取消",null);
				WindowManager.LayoutParams params = dialog.getWindow().getAttributes();    
				params.width = 1500;    
				dialog.getWindow().setAttributes(params);
				
			}else{
				//后续校验
				submitInterivewBasicAfterIdentityCard();
			}
			
		}else{
			//后续校验
			submitInterivewBasicAfterIdentityCard();
		}
	}
	
	private void submitInterivewBasicAfterIdentityCard(){
		
		//省/自治区/直辖市
		EditText provinceET = (EditText)findViewById(R.id.interviewer_basic_province);
		String province = provinceET.getText().toString().trim();
		if(StringUtils.isEmpty(province)){
			DialogUtils.showLongToast(IntervieweBasicActivity.this, "省/自治区/直辖市不能为空");
			return;
		}
		
		//市/县/区
		EditText cityET = (EditText)findViewById(R.id.interviewer_basic_city);
		String city = cityET.getText().toString().trim();
		if(StringUtils.isEmpty(city)){
			DialogUtils.showLongToast(IntervieweBasicActivity.this, "市/县/区不能为空");
			return;
		}
		
		//联系地址
		EditText addressET = (EditText)findViewById(R.id.interviewer_basic_address);
		String address = addressET.getText().toString().trim();
		if(StringUtils.isEmpty(address)){
			DialogUtils.showLongToast(IntervieweBasicActivity.this, "联系地址不能为空");
			return;
		}
		
		//邮政编码
		EditText postCodeET = (EditText)findViewById(R.id.interviewer_basic_post_code);
		String postCode = postCodeET.getText().toString().trim();
		if(StringUtils.isEmpty(postCode)){
			DialogUtils.showLongToast(IntervieweBasicActivity.this, "邮政编码不能为空");
			return;
		}
		if(!CommonUtils.test("[0-9]{6}", postCode)){
			DialogUtils.showLongToast(IntervieweBasicActivity.this, "邮政编码格式不正确");
			return;
		}
		
		//联系电话
		EditText mobileET = (EditText)findViewById(R.id.interviewer_basic_mobile);
		String mobile = mobileET.getText().toString().trim();
		if(StringUtils.isEmpty(mobile)){
			DialogUtils.showLongToast(IntervieweBasicActivity.this, "联系电话不能为空");
			return;
		}
		if(!CommonUtils.test("[0-9]{10,12}", mobile)){
			DialogUtils.showLongToast(IntervieweBasicActivity.this, "联系电话格式不正确");
			return;
		}
		
		//本地亲属联系电话
		EditText familyMobileET = (EditText)findViewById(R.id.interviewer_basic_family_mobile);
		String familyMobile = familyMobileET.getText().toString().trim();
		if(!StringUtils.isEmpty(familyMobile)){
			if(!CommonUtils.test("[0-9]{10,12}", familyMobile)){
				DialogUtils.showLongToast(IntervieweBasicActivity.this, "本地亲属联系电话格式不正确");
				return;
			}
		}
		
		//开始访谈
		newInterviewBasic();
	}
}
