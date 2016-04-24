package com.huasheng.sysq.activity.report;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.huasheng.sysq.R;

public class InterviewAdapter extends ArrayAdapter<Map<String,String>>{
	
	private int resource;
	private ReportActivity activity;

	public InterviewAdapter(Context context, int resource,List<Map<String,String>> objects,ReportActivity activity) {
		super(context, resource, objects);
		this.resource = resource;
		this.activity = activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Map<String,String> row = getItem(position);
		
		View view;
		if(convertView == null){
			view = LayoutInflater.from(getContext()).inflate(this.resource, null);
		}else{
			view = convertView;
		}
		
		TextView titleTV = (TextView)view.findViewById(R.id.tv_report_interview_title);
		titleTV.setText(row.get("title"));
		
		TextView allTV = (TextView)view.findViewById(R.id.tv_report_interview_all);
		allTV.setText(row.get("all"));
		
		TextView todayCaseTV = (TextView)view.findViewById(R.id.tv_report_interview_today_case);
		todayCaseTV.setText(row.get("todayCase"));
		
		TextView allCaseTV = (TextView)view.findViewById(R.id.tv_report_interview_all_case);
		allCaseTV.setText(row.get("allCase"));
		
		TextView todayContrastTV = (TextView)view.findViewById(R.id.tv_report_interview_today_contrast);
		todayContrastTV.setText(row.get("todayContrast"));
		
		TextView allContrastTV = (TextView)view.findViewById(R.id.tv_report_interview_all_contrast);
		allContrastTV.setText(row.get("allContrast"));
		
		return view;
	}

}
