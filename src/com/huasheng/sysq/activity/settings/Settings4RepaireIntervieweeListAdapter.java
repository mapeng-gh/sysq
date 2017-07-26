package com.huasheng.sysq.activity.settings;

import java.util.List;

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

public class Settings4RepaireIntervieweeListAdapter extends ArrayAdapter<InterviewBasicWrap>{
	
	private int resource;
	private Settings4RepaireIntervieweeListActivity activity;

	public Settings4RepaireIntervieweeListAdapter(Context context, int resource,List<InterviewBasicWrap> objects,Settings4RepaireIntervieweeListActivity activity) {
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
		TextView idTV = (TextView)interviewView.findViewById(R.id.tv_settings_repaire_intervieweelist_id);
		idTV.setText(interviewBasic.getId()+"");
		
		TextView usernameTV = (TextView)interviewView.findViewById(R.id.tv_settings_repaire_intervieweelist_username);
		usernameTV.setText(interviewee.getUsername());
		
		TextView addressTV = (TextView)interviewView.findViewById(R.id.tv_settings_repaire_intervieweelist_address);
		addressTV.setText(interviewee.getAddress());
		
		TextView mobileTV = (TextView)interviewView.findViewById(R.id.tv_settings_repaire_intervieweelist_mobile);
		mobileTV.setText(interviewee.getMobile());
		
		TextView typeTV = (TextView)interviewView.findViewById(R.id.tv_settings_repaire_intervieweelist_type);
		if(interviewBasic.getType() == InterviewConstants.TYPE_CASE){
			typeTV.setText("病例");
		}else if(interviewBasic.getType() == InterviewConstants.TYPE_CONTRAST){
			typeTV.setText("对照");
		}
		
		TextView statusTV = (TextView)interviewView.findViewById(R.id.tv_settings_repaire_intervieweelist_status);
		if(interviewBasic.getStatus() == InterviewBasic.STATUS_DOING){
			statusTV.setText("正在进行");
		}else if(interviewBasic.getStatus() == InterviewBasic.STATUS_BREAK){
			statusTV.setText("已结束");
		}else if(interviewBasic.getStatus() == InterviewBasic.STATUS_DONE){
			statusTV.setText("已完成");
		}
		
		TextView interviewerNameTV = (TextView)interviewView.findViewById(R.id.tv_settings_repaire_intervieweelist_interviewer_name);
		interviewerNameTV.setText(interviewer.getUsername());
		
		//绑定事件
		TextView repaireTV = (TextView)interviewView.findViewById(R.id.tv_settings_repaire_intervieweelist_repaire);
		repaireTV.setOnClickListener(this.activity);
		repaireTV.setTag(interviewBasic.getId());
		
		return interviewView;
	}

}
