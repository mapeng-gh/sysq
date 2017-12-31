package com.huasheng.sysq.activity.interviewee;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.InterviewBasicWrap;
import com.huasheng.sysq.model.Interviewee;
import com.huasheng.sysq.model.Interviewer;
import com.huasheng.sysq.util.interview.InterviewConstants;

public class IntervieweeAdapter extends ArrayAdapter<InterviewBasicWrap>{
	
	private int resource;
	private IntervieweeActivity activity;

	public IntervieweeAdapter(Context context, int resource,List<InterviewBasicWrap> objects,IntervieweeActivity activity) {
		super(context, resource, objects);
		this.resource = resource;
		this.activity = activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		//获取数据
		InterviewBasicWrap interviewBasicWrap = getItem(position);
		InterviewBasic interviewBasic = interviewBasicWrap.getInterviewBasic();
		Interviewee interviewee = interviewBasicWrap.getInterviewee();
		Interviewer interviewer = interviewBasicWrap.getInterviewer();
		
		//实例化一个view
		View interviewView;
		if(convertView == null){
			interviewView = LayoutInflater.from(getContext()).inflate(this.resource, null);
		}else{
			interviewView = convertView;
		}
		
		//绑定数据
		TextView idTV = (TextView)interviewView.findViewById(R.id.tv_interviewee_list_item_id);
		idTV.setText(interviewBasic.getId()+"");
		
		TextView usernameTV = (TextView)interviewView.findViewById(R.id.tv_interviewee_list_item_username);
		usernameTV.setText(interviewee.getUsername());
		
		TextView addressTV = (TextView)interviewView.findViewById(R.id.tv_interviewee_list_item_address);
		addressTV.setText(interviewee.getAddress());
		
		TextView mobileTV = (TextView)interviewView.findViewById(R.id.tv_interviewee_list_item_mobile);
		mobileTV.setText(interviewee.getMobile());
		
		TextView typeTV = (TextView)interviewView.findViewById(R.id.tv_interviewee_list_item_type);
		if(interviewBasic.getType() == InterviewConstants.TYPE_CASE){
			typeTV.setText("病例");
		}else if(interviewBasic.getType() == InterviewConstants.TYPE_CONTRAST){
			typeTV.setText("对照");
		}
		
		TextView statusTV = (TextView)interviewView.findViewById(R.id.tv_interviewee_list_item_status);
		if(interviewBasic.getStatus() == InterviewBasic.STATUS_DOING){
			statusTV.setText("正在进行");
		}else if(interviewBasic.getStatus() == InterviewBasic.STATUS_BREAK){
			statusTV.setText("已结束");
		}else if(interviewBasic.getStatus() == InterviewBasic.STATUS_DONE){
			statusTV.setText("已完成");
		}
		
		TextView dnaTV = (TextView)interviewView.findViewById(R.id.tv_interviewee_list_item_dna);
		if(StringUtils.isEmpty(interviewee.getDna())){
			dnaTV.setText("未上传");
		}else{
			dnaTV.setText("已上传");
		}
		
		//绑定事件
		TextView viewTV = (TextView)interviewView.findViewById(R.id.tv_interviewee_item_questionaire);
		viewTV.setOnClickListener(this.activity);
		viewTV.setTag(interviewBasic.getId());
		
		//个人信息
		TextView personTV = (TextView)interviewView.findViewById(R.id.tv_interviewee_item_person);
		personTV.setOnClickListener(this.activity);
		personTV.setTag(interviewBasic.getId());
		
		//控制“继续”显示
		TextView continueTV = (TextView)interviewView.findViewById(R.id.tv_interviewee_item_continue);
		if(interviewBasic.getStatus() == InterviewBasic.STATUS_DOING){
			continueTV.setVisibility(View.VISIBLE);
			
			//绑定“继续”事件
			continueTV.setOnClickListener(this.activity);
			continueTV.setTag(interviewBasic.getId());
			
		}else{
			continueTV.setVisibility(View.GONE);
		}
		
		//结束原因
		TextView quitReasonTV = (TextView)interviewView.findViewById(R.id.tv_interviewee_item_quit_reason);
		if(interviewBasic.getStatus() == InterviewBasic.STATUS_BREAK && !StringUtils.isEmpty(interviewBasic.getQuitReason())){
			quitReasonTV.setVisibility(View.VISIBLE);
			quitReasonTV.setOnClickListener(this.activity);
			quitReasonTV.setTag(interviewBasic.getQuitReason());
		}else{
			quitReasonTV.setVisibility(View.GONE);
		}
		
		return interviewView;
	}

}
