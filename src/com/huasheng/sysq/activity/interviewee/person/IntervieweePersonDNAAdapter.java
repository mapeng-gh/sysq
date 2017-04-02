package com.huasheng.sysq.activity.interviewee.person;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.huasheng.sysq.R;

public class IntervieweePersonDNAAdapter extends ArrayAdapter<String>{
	
	private Context context;
	private int resource;
	private IntervieweePerson4DNAActivity activity;

	public IntervieweePersonDNAAdapter(Context context, int resource,List<String> data,IntervieweePerson4DNAActivity activity) {
		super(context,resource,data);
		this.context = context;
		this.resource = resource;
		this.activity = activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		//获取数据
		String dnaStr = getItem(position);
		
		//实例化一个view
		View dnaView;
		if(convertView == null){
			dnaView = LayoutInflater.from(this.context).inflate(this.resource, null);
		}else{
			dnaView = convertView;
		}
		
		//dna条形码
		TextView dnaTV = (TextView)dnaView.findViewById(R.id.interviewee_person_dna_text_tv);
		dnaTV.setText(dnaStr);
		
		//删除
		TextView delTV = (TextView)dnaView.findViewById(R.id.interviewee_person_dna_del_tv);
		delTV.setOnClickListener(this.activity);
		delTV.setTag(dnaStr);
		
		return dnaView;
	}
}
