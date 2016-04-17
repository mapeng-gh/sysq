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
		
		//��ȡ����
		InterviewBasic interviewBasic = getItem(position);
		
		//ʵ����һ��view
		View interviewView;
		if(convertView == null){
			interviewView = LayoutInflater.from(getContext()).inflate(this.resource, null);
		}else{
			interviewView = convertView;
		}
		
		//������
		TextView idTV = (TextView)interviewView.findViewById(R.id.tv_interviewee_list_item_id);
		idTV.setText(interviewBasic.getId()+"");
		
		TextView usernameTV = (TextView)interviewView.findViewById(R.id.tv_interviewee_list_item_username);
		usernameTV.setText(interviewBasic.getUsername());
		
		TextView addressTV = (TextView)interviewView.findViewById(R.id.tv_interviewee_list_item_address);
		addressTV.setText(interviewBasic.getAddress());
		
		TextView mobileTV = (TextView)interviewView.findViewById(R.id.tv_interviewee_list_item_mobile);
		mobileTV.setText(interviewBasic.getMobile());
		
		TextView typeTV = (TextView)interviewView.findViewById(R.id.tv_interviewee_list_item_type);
		if(interviewBasic.getType() == InterviewConstants.TYPE_CASE){
			typeTV.setText("����");
		}else if(interviewBasic.getType() == InterviewConstants.TYPE_CONTRAST){
			typeTV.setText("����");
		}
		
		TextView statusTV = (TextView)interviewView.findViewById(R.id.tv_interviewee_list_item_status);
		if(interviewBasic.getStatus() == InterviewBasic.STATUS_DOING){
			statusTV.setText("���ڽ���");
		}else if(interviewBasic.getStatus() == InterviewBasic.STATUS_BREAK){
			statusTV.setText("�ѽ���");
		}else if(interviewBasic.getStatus() == InterviewBasic.STATUS_DONE){
			statusTV.setText("�����");
		}
		
		//���¼�
		TextView viewTV = (TextView)interviewView.findViewById(R.id.tv_interviewee_item_view);
		viewTV.setOnClickListener(this.activity);
		viewTV.setTag(interviewBasic.getId());
		
		TextView continueTV = (TextView)interviewView.findViewById(R.id.tv_interviewee_item_continue);
		if(interviewBasic.getStatus() == InterviewBasic.STATUS_DOING){//���ڽ�����ʾ��������
			continueTV.setVisibility(View.VISIBLE);
			continueTV.setOnClickListener(this.activity);
			continueTV.setTag(interviewBasic.getId());
		}else{
			continueTV.setVisibility(View.GONE);
		}
		
		return interviewView;
	}

}
