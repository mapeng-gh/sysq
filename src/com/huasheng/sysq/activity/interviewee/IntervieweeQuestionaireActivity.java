package com.huasheng.sysq.activity.interviewee;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.service.IntervieweeService;
import com.huasheng.sysq.util.SysqApplication;

public class IntervieweeQuestionaireActivity extends Activity implements OnClickListener{
	
	private int interviewBasicId;
	
	private LinearLayout containerLL;
	
	private LinearLayout questionaireListLL;
	private LinearLayout interviewBasicLL;
	private LinearLayout interviewDNALL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_interviewee_questionaire);
		
		//获取传递参数interviewBasicId
		this.interviewBasicId = this.getIntent().getIntExtra("interviewBasicId", -1);
		
		containerLL = (LinearLayout)findViewById(R.id.ll_interviewee_questionaire_container);
		
		questionaireListLL = (LinearLayout)findViewById(R.id.ll_interviewee_questionaire_list);
		interviewBasicLL = (LinearLayout)findViewById(R.id.ll_interviewee_questionaire_basic);
		interviewDNALL = (LinearLayout)findViewById(R.id.ll_interviewee_questionaire_dna);
		
		questionaireListLL.setOnClickListener(this);
		interviewBasicLL.setOnClickListener(this);
		interviewDNALL.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View view) {
		
		if(view.getId() == R.id.ll_interviewee_questionaire_list){//问卷列表
			
			this.questionaireListPage();
			
		}else if(view.getId() == R.id.ll_interviewee_questionaire_basic){//受访者信息
			
			this.intervieweeBasicPage();
			
		}else if(view.getId() == R.id.ll_interviewee_questionaire_dna){//DNA样本	
			
			this.intervieweeDNA();
			
		}else if(view.getId() == R.id.btn_interviewee_questionaire_basic_submit){//修改受访者信息
			
			this.saveIntervieweeBasic();
		}
	}
	
	private void saveIntervieweeBasic(){
		
		InterviewBasic interviewBasic = IntervieweeService.findById(this.interviewBasicId);
		
		EditText userNameET = (EditText)this.containerLL.findViewById(R.id.et_interviewee_questionaire_basic_username);
		interviewBasic.setUsername(userNameET.getText().toString());
		
		EditText identityCardET = (EditText)this.containerLL.findViewById(R.id.et_interviewee_questionaire_basic_identity_card);
		interviewBasic.setIdentityCard(identityCardET.getText().toString());
		
		EditText provinceET = (EditText)this.containerLL.findViewById(R.id.et_interviewee_questionaire_basic_province);
		interviewBasic.setProvince(provinceET.getText().toString());
		
		EditText cityET = (EditText)this.containerLL.findViewById(R.id.et_interviewee_questionaire_basic_city);
		interviewBasic.setCity(cityET.getText().toString());
		
		EditText addressET = (EditText)this.containerLL.findViewById(R.id.et_interviewee_questionaire_basic_address);
		interviewBasic.setAddress(addressET.getText().toString());
		
		EditText postCodeET = (EditText)this.containerLL.findViewById(R.id.et_interviewee_questionaire_basic_post_code);
		interviewBasic.setPostCode(postCodeET.getText().toString());
		
		EditText mobileET = (EditText)this.containerLL.findViewById(R.id.et_interviewee_questionaire_basic_mobile);
		interviewBasic.setMobile(mobileET.getText().toString());
		
		EditText familyAddressET = (EditText)this.containerLL.findViewById(R.id.et_interviewee_questionaire_basic_family_address);
		interviewBasic.setFamilyAddress(familyAddressET.getText().toString());
		
		EditText familyMobileET = (EditText)this.containerLL.findViewById(R.id.et_interviewee_questionaire_basic_family_mobile);
		interviewBasic.setFamilyMobile(familyMobileET.getText().toString());
		
		EditText remarkET = (EditText)this.containerLL.findViewById(R.id.et_interviewee_questionaire_basic_remark);
		interviewBasic.setRemark(remarkET.getText().toString());
		
		IntervieweeService.modifyInterviewBasic(interviewBasic);
		SysqApplication.showMessage("修改成功");
	}
	
	private void questionaireListPage(){
		
	}
	
	private void intervieweeBasicPage(){
		
		//加载静态页面
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.interviewee_questionaire_basic, null);
		
		//加载数据
		InterviewBasic interviewBasic = IntervieweeService.findById(this.interviewBasicId);
		
		//数据绑定
		EditText userNameET = (EditText)view.findViewById(R.id.et_interviewee_questionaire_basic_username);
		userNameET.setText(interviewBasic.getUsername());
		
		EditText identityCardET = (EditText)view.findViewById(R.id.et_interviewee_questionaire_basic_identity_card);
		identityCardET.setText(interviewBasic.getIdentityCard());
		
		EditText provinceET = (EditText)view.findViewById(R.id.et_interviewee_questionaire_basic_province);
		provinceET.setText(interviewBasic.getProvince());
		
		EditText cityET = (EditText)view.findViewById(R.id.et_interviewee_questionaire_basic_city);
		cityET.setText(interviewBasic.getCity());
		
		EditText addressET = (EditText)view.findViewById(R.id.et_interviewee_questionaire_basic_address);
		addressET.setText(interviewBasic.getAddress());
		
		EditText postCodeET = (EditText)view.findViewById(R.id.et_interviewee_questionaire_basic_post_code);
		postCodeET.setText(interviewBasic.getPostCode());
		
		EditText mobileET = (EditText)view.findViewById(R.id.et_interviewee_questionaire_basic_mobile);
		mobileET.setText(interviewBasic.getMobile());
		
		EditText familyAddressET = (EditText)view.findViewById(R.id.et_interviewee_questionaire_basic_family_address);
		familyAddressET.setText(interviewBasic.getFamilyAddress());
		
		EditText familyMobileET = (EditText)view.findViewById(R.id.et_interviewee_questionaire_basic_family_mobile);
		familyMobileET.setText(interviewBasic.getFamilyMobile());
		
		EditText remarkET = (EditText)view.findViewById(R.id.et_interviewee_questionaire_basic_remark);
		remarkET.setText(interviewBasic.getRemark());
		
		//渲染
		this.containerLL.removeAllViews();
		this.containerLL.addView(view,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		
		//绑定事件
		Button submitBtn = (Button)view.findViewById(R.id.btn_interviewee_questionaire_basic_submit);
		submitBtn.setOnClickListener(this);
	}
	
	private void intervieweeDNA(){
		
	}

}
