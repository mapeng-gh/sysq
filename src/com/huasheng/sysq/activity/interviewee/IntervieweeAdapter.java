package com.huasheng.sysq.activity.interviewee;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.util.InterviewConstants;

public class IntervieweeAdapter extends ArrayAdapter<InterviewBasic>{
	
	private int resource;
	private IntervieweeActivity activity;

	public IntervieweeAdapter(Context context, int resource,List<InterviewBasic> objects,IntervieweeActivity activity) {
		super(context, resource, objects);
		this.resource = resource;
		this.activity = activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		//获取数据
		InterviewBasic interview = getItem(position);
		
		//实例化一个view
		View interviewView;
		if(convertView == null){
			interviewView = LayoutInflater.from(getContext()).inflate(this.resource, null);
		}else{
			interviewView = convertView;
		}
		
		//绑定数据
		TextView idTV = (TextView)interviewView.findViewById(R.id.tv_interviewee_list_item_id);
		idTV.setText(interview.getId()+"");
		
		TextView usernameTV = (TextView)interviewView.findViewById(R.id.tv_interviewee_list_item_username);
		usernameTV.setText(interview.getUsername());
		
		TextView addressTV = (TextView)interviewView.findViewById(R.id.tv_interviewee_list_item_address);
		addressTV.setText(interview.getAddress());
		
		TextView mobileTV = (TextView)interviewView.findViewById(R.id.tv_interviewee_list_item_mobile);
		mobileTV.setText(interview.getMobile());
		
		TextView typeTV = (TextView)interviewView.findViewById(R.id.tv_interviewee_list_item_type);
		if(interview.getType() == InterviewConstants.TYPE_CASE){
			typeTV.setText("病例");
		}else if(interview.getType() == InterviewConstants.TYPE_CONTRAST){
			typeTV.setText("对照");
		}
		
		//绑定事件
		TextView viewTV = (TextView)interviewView.findViewWithTag("view");
		viewTV.setOnClickListener(this.activity);
		((LinearLayout)viewTV.getParent().getParent().getParent()).setId(interview.getId());
		
		return interviewView;
	}

}
