package com.huasheng.sysq.activity.interviewee.questionaire;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.huasheng.sysq.R;
import com.huasheng.sysq.activity.interviewee.answers.IntervieweeAnswerActivity;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.InterviewQuestionaireWrap;
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
			
			this.intervieweeDNAPage();
			
		}else if(view.getId() == R.id.btn_interviewee_questionaire_basic_submit){//修改受访者信息
			
			this.saveIntervieweeBasic();
			
		}else if(view.getId() == R.id.btn_interviewee_questionaire_dna_submit){//保存DNA信息
			
			this.saveIntervieweeDNA();
			
		}else if(view.getId() == R.id.tv_interviewee_questionaire_list_item_view){//查看答案列表
			
			String questionaireCode = (String)view.getTag();
			this.viewAnswers(questionaireCode);
		}
	}
	
	private void viewAnswers(String questionaireCode){
		Intent intent = new Intent(this,IntervieweeAnswerActivity.class);
		intent.putExtra("interviewBasicId", this.interviewBasicId);
		intent.putExtra("questionaireCode", questionaireCode);
		this.startActivity(intent);
	}
	
	private void saveIntervieweeDNA(){
		
		InterviewBasic interviewBasic = IntervieweeService.findById(this.interviewBasicId);
		
		EditText sample1ET = (EditText)this.containerLL.findViewById(R.id.et_interviewee_questionaire_dna_sample1);
		EditText sample2ET = (EditText)this.containerLL.findViewById(R.id.et_interviewee_questionaire_dna_sample2);
		EditText sample3ET = (EditText)this.containerLL.findViewById(R.id.et_interviewee_questionaire_dna_sample3);
		EditText sample4ET = (EditText)this.containerLL.findViewById(R.id.et_interviewee_questionaire_dna_sample4);
		
		List<String> dnaList = new ArrayList<String>();
		if(!StringUtils.isEmpty(sample1ET.getText())){
			dnaList.add(sample1ET.getText().toString());
		}
		if(!StringUtils.isEmpty(sample2ET.getText())){
			dnaList.add(sample2ET.getText().toString());
		}
		if(!StringUtils.isEmpty(sample3ET.getText())){
			dnaList.add(sample3ET.getText().toString());
		}
		if(!StringUtils.isEmpty(sample4ET.getText())){
			dnaList.add(sample4ET.getText().toString());
		}
		
		if(dnaList.size() > 0){
			String dnas = StringUtils.join(dnaList,",");
			interviewBasic.setDna(dnas);
			IntervieweeService.modifyInterviewBasic(interviewBasic);
			SysqApplication.showMessage("修改成功");
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
		
		//加载静态页面
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.interviewee_questionaire_list, null);
		
		//加载数据
		List<InterviewQuestionaireWrap> interviewQuestionaireWrapList = IntervieweeService.getInterviewQuestionaireList(this.interviewBasicId);
		
		//数据绑定
		ListView interviewQuestionaireListView = (ListView)view.findViewById(R.id.lv_interviewee_questionaire_list);
		IntervieweeQuestionaireAdapter adapter = new IntervieweeQuestionaireAdapter(this,R.layout.item_interviewee_questionaire,interviewQuestionaireWrapList,this);
		interviewQuestionaireListView.setAdapter(adapter);
		
		//渲染
		this.containerLL.removeAllViews();
		this.containerLL.addView(view,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
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
	
	private void intervieweeDNAPage(){
		
		//加载静态页面
		LayoutInflater inflater = getLayoutInflater();
		View view = inflater.inflate(R.layout.interviewee_questionaire_dna, null);
		
		//加载数据
		InterviewBasic interviewBasic = IntervieweeService.findById(this.interviewBasicId);
		
		//数据绑定
		if(!StringUtils.isEmpty(interviewBasic.getDna())){
			String[] dnaArray = interviewBasic.getDna().split(",");
			if(dnaArray != null && dnaArray.length > 0){
				EditText sample1ET = (EditText)view.findViewById(R.id.et_interviewee_questionaire_dna_sample1);
				EditText sample2ET = (EditText)view.findViewById(R.id.et_interviewee_questionaire_dna_sample2);
				EditText sample3ET = (EditText)view.findViewById(R.id.et_interviewee_questionaire_dna_sample3);
				EditText sample4ET = (EditText)view.findViewById(R.id.et_interviewee_questionaire_dna_sample4);
				EditText[] sampleETArray = new EditText[]{sample1ET,sample2ET,sample3ET,sample4ET};
				for(int i = 0 ; i < dnaArray.length; i++){
					sampleETArray[i].setText(dnaArray[i]);
				}
			}
		}
		
		//渲染
		this.containerLL.removeAllViews();
		this.containerLL.addView(view,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		
		//绑定事件
		Button submitBtn = (Button)view.findViewById(R.id.btn_interviewee_questionaire_dna_submit);
		submitBtn.setOnClickListener(this);
	}

}
