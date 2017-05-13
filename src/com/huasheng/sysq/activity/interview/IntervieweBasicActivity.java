package com.huasheng.sysq.activity.interview;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
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
import com.huasheng.sysq.util.MysqlUtils;
import com.huasheng.sysq.util.SysqContext;
import com.huasheng.sysq.util.interview.InterviewConstants;
import com.huasheng.sysq.util.interview.InterviewContext;
import com.huasheng.sysq.util.upload.UploadConstants;

public class IntervieweBasicActivity extends Activity implements OnClickListener{
	
	private Handler handler;
	private static final int MESSAGE_TOAST = 1;
	private static final int MESSAGE_DIALOG = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_interview_basic);
		
		Button interviewBasicSubmitBtn = (Button)findViewById(R.id.interviewer_basic_submit_button);
		interviewBasicSubmitBtn.setOnClickListener(this);
		
		handleMessage();
	}
	
	private void handleMessage(){
		handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				if(msg.what == MESSAGE_TOAST){
					DialogUtils.showLongToast(IntervieweBasicActivity.this, msg.obj.toString());
				}else if(msg.what == MESSAGE_DIALOG){
					AlertDialog dialog = DialogUtils.showCustomDialog(IntervieweBasicActivity.this, "请您核对本次访谈对象是否曾经被访谈过",(View)msg.obj,new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							newInterviewBasic();
						}
					});
					WindowManager.LayoutParams params = dialog.getWindow().getAttributes();    
					params.width = 1500;    
					dialog.getWindow().setAttributes(params);
				}
			}
		};
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
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				//表单校验
				if(checkInterviewFormData() == false){
					return;
				}
				
				//身份证：mysql库排重（一期）
				EditText identityCardET = (EditText)findViewById(R.id.interviewer_basic_identity_card);
				String identityCard = identityCardET.getText().toString().trim();
				List<Map<String,String>> patient4Old = MysqlUtils.selectPatientByIdentityCard4Old(identityCard);
				if(patient4Old != null && patient4Old.size() > 0){//身份证
					CommonUtils.sendMessage(handler, MESSAGE_TOAST, "身份证号码在一期库里已存在");
					return;
				}
				
				EditText userNameET = (EditText)findViewById(R.id.interviewer_basic_username);
				String userName = userNameET.getText().toString().trim();
				patient4Old = MysqlUtils.selectPatientByName4Old(userName);
				if(patient4Old == null || patient4Old.size() == 0){//姓名
					newInterviewBasic();
				}else{
					View view = LayoutInflater.from(IntervieweBasicActivity.this).inflate(R.layout.interview_dialog, null);
					ListView dialogLV = (ListView)view.findViewById(R.id.interviewDialogLV);
					InterviewDialogAdapter dialogAdapter = new InterviewDialogAdapter(IntervieweBasicActivity.this,R.layout.item_interview_dialog,patient4Old);
					dialogLV.setAdapter(dialogAdapter);
					CommonUtils.sendMessage(handler, MESSAGE_DIALOG,view);
				}
			}
		}).start();
	}
	
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
	 * 表单校验
	 * @return
	 */
	private boolean checkInterviewFormData(){
		
		//姓名
		EditText userNameET = (EditText)findViewById(R.id.interviewer_basic_username);
		String userName = userNameET.getText().toString().trim();
		if(StringUtils.isEmpty(userName)){
			CommonUtils.sendMessage(handler, MESSAGE_TOAST, "姓名不能为空");
			return false;
		}
		if(userName.length() < 2){
			CommonUtils.sendMessage(handler, MESSAGE_TOAST, "姓名至少为两个汉字");
			return false;
		}
		
		//身份证：基本格式校验
		EditText identityCardET = (EditText)findViewById(R.id.interviewer_basic_identity_card);
		String identityCard = identityCardET.getText().toString().trim();
		if(StringUtils.isEmpty(identityCard)){
			CommonUtils.sendMessage(handler, MESSAGE_TOAST, "身份证号码不能为空");
			return false;
		}
		if(!CommonUtils.checkIdentityCard(identityCard)){
			CommonUtils.sendMessage(handler, MESSAGE_TOAST, "身份证号码格式不正确");
			return false;
		}
		
		//身份证：本地库排重
		List<Interviewee> intervieweeList = InterviewService.getAllInterviewee();
		if(intervieweeList != null && intervieweeList.size() > 0){
			for(Interviewee existInterviewee : intervieweeList){
				if(identityCard.equals(existInterviewee.getIdentityCard())){
					CommonUtils.sendMessage(handler, MESSAGE_TOAST, "身份证号码在本地已存在");
					return false;
				}
			}
		}
		
		//身份证：mysql库排重
		List<Map<String,String>> patient4Old = MysqlUtils.selectPatientByIdentityCard(identityCard);
		if(patient4Old != null && patient4Old.size() > 0){
			CommonUtils.sendMessage(handler, MESSAGE_TOAST, "身份证号码在本期远程库里已存在");
			return false;
		}
		
		//省/自治区/直辖市
		EditText provinceET = (EditText)findViewById(R.id.interviewer_basic_province);
		String province = provinceET.getText().toString().trim();
		if(StringUtils.isEmpty(province)){
			CommonUtils.sendMessage(handler, MESSAGE_TOAST, "省/自治区/直辖市不能为空");
			return false;
		}
		
		//市/县/区
		EditText cityET = (EditText)findViewById(R.id.interviewer_basic_city);
		String city = cityET.getText().toString().trim();
		if(StringUtils.isEmpty(city)){
			CommonUtils.sendMessage(handler, MESSAGE_TOAST, "市/县/区不能为空");
			return false;
		}
		
		//联系地址
		EditText addressET = (EditText)findViewById(R.id.interviewer_basic_address);
		String address = addressET.getText().toString().trim();
		if(StringUtils.isEmpty(address)){
			CommonUtils.sendMessage(handler, MESSAGE_TOAST, "联系地址不能为空");
			return false;
		}
		
		//邮政编码
		EditText postCodeET = (EditText)findViewById(R.id.interviewer_basic_post_code);
		String postCode = postCodeET.getText().toString().trim();
		if(StringUtils.isEmpty(postCode)){
			CommonUtils.sendMessage(handler, MESSAGE_TOAST, "邮政编码不能为空");
			return false;
		}
		if(!CommonUtils.test("[0-9]{6}", postCode)){
			CommonUtils.sendMessage(handler, MESSAGE_TOAST, "邮政编码格式不正确");
			return false;
		}
		
		//联系电话
		EditText mobileET = (EditText)findViewById(R.id.interviewer_basic_mobile);
		String mobile = mobileET.getText().toString().trim();
		if(StringUtils.isEmpty(mobile)){
			CommonUtils.sendMessage(handler, MESSAGE_TOAST, "联系电话不能为空");
			return false;
		}
		if(!CommonUtils.test("[0-9]{10,12}", mobile)){
			CommonUtils.sendMessage(handler, MESSAGE_TOAST, "联系电话格式不正确");
			return false;
		}
		
		//本地亲属联系电话
		EditText familyMobileET = (EditText)findViewById(R.id.interviewer_basic_family_mobile);
		String familyMobile = familyMobileET.getText().toString().trim();
		if(!StringUtils.isEmpty(familyMobile)){
			if(!CommonUtils.test("[0-9]{10,12}", familyMobile)){
				CommonUtils.sendMessage(handler, MESSAGE_TOAST, "本地亲属联系电话格式不正确");
				return false;
			}
		}
		
		return true;
	}
}
