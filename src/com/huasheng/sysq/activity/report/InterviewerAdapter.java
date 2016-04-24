package com.huasheng.sysq.activity.report;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.Interviewer;

public class InterviewerAdapter extends ArrayAdapter<Interviewer>{
	
	private int resource;
	private ReportActivity activity;

	public InterviewerAdapter(Context context, int resource,List<Interviewer> objects,ReportActivity activity) {
		super(context, resource, objects);
		this.resource = resource;
		this.activity = activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Interviewer interviewer = getItem(position);
		
		View view;
		if(convertView == null){
			view = LayoutInflater.from(getContext()).inflate(this.resource, null);
		}else{
			view = convertView;
		}
		
		TextView loginNameTV = (TextView)view.findViewById(R.id.tv_report_interviewer_login_name);
		loginNameTV.setText(interviewer.getLoginName());
		
		TextView userNameTV = (TextView)view.findViewById(R.id.tv_report_interviewer_username);
		userNameTV.setText(interviewer.getUsername());
		
		TextView mobileTV = (TextView)view.findViewById(R.id.tv_report_interviewer_mobile);
		mobileTV.setText(interviewer.getMobile());
		
		TextView emailTV = (TextView)view.findViewById(R.id.tv_report_interviewer_email);
		emailTV.setText(interviewer.getEmail());
		
		return view;
	}

}
