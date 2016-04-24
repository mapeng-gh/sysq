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

public class QuestionaireAdapter extends ArrayAdapter<Map<String,String>>{

	private int resource;
	private ReportActivity activity;

	public QuestionaireAdapter(Context context, int resource,List<Map<String,String>> objects,ReportActivity activity) {
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
		
		TextView titleTV = (TextView)view.findViewById(R.id.tv_report_questionaire_title);
		titleTV.setText(row.get("title"));
		
		TextView allTV = (TextView)view.findViewById(R.id.tv_report_questionaire_all);
		allTV.setText(row.get("all"));
		
		TextView doneTV = (TextView)view.findViewById(R.id.tv_report_questionaire_done);
		doneTV.setText(row.get("done"));
		
		return view;
	}

}
