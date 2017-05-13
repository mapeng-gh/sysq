package com.huasheng.sysq.activity.interview;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.huasheng.sysq.R;

public class InterviewDialogAdapter extends ArrayAdapter<Map<String,String>>{
	
	private int resource;

	public InterviewDialogAdapter(Context context, int resource, List<Map<String,String>> objects) {
		super(context, resource, objects);
		this.resource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if(convertView == null){
			view = LayoutInflater.from(super.getContext()).inflate(this.resource, null);
		}else{
			view = convertView;
		}
		
		Map<String,String> patient = getItem(position);
		
		TextView nameTV = (TextView)view.findViewById(R.id.interviewDialogName);
		nameTV.setText(patient.get("name"));
		TextView provinceTV = (TextView)view.findViewById(R.id.interviewDialogProvince);
		provinceTV.setText(patient.get("province"));
		TextView cityTV = (TextView)view.findViewById(R.id.interviewDialogCity);
		cityTV.setText(patient.get("city"));
		TextView addressTV = (TextView)view.findViewById(R.id.interviewDialogAddress);
		addressTV.setText(patient.get("address"));
		TextView phoneTV = (TextView)view.findViewById(R.id.interviewDialogPhone);
		phoneTV.setText(patient.get("phone"));
				
		return view;
	}
}
