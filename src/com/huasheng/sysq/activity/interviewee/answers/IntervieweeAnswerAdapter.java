package com.huasheng.sysq.activity.interviewee.answers;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.InterviewAnswerWrap;

public class IntervieweeAnswerAdapter extends ArrayAdapter<InterviewAnswerWrap>{
	
	private int resource;
	private IntervieweeAnswerActivity activity;

	public IntervieweeAnswerAdapter(Context context, int resource,List<InterviewAnswerWrap> objects,IntervieweeAnswerActivity activity) {
		super(context, resource, objects);
		this.resource = resource;
		this.activity = activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		//��ȡ����
		InterviewAnswerWrap interviewAnswerWrap = getItem(position);
		
		//ʵ����һ��view
		View view;
		if(convertView == null){
			view = LayoutInflater.from(getContext()).inflate(this.resource, null);
		}else{
			view = convertView;
		}
		
		//������
		TextView answerLabelTV = (TextView)view.findViewById(R.id.tv_interviewee_answers_answer_label);
		answerLabelTV.setText(interviewAnswerWrap.getAnswer().getLabel());
		
		TextView answerTextTV = (TextView)view.findViewById(R.id.tv_interviewee_answers_answer_text);
		answerTextTV.setText(interviewAnswerWrap.getInterviewAnswer().getAnswerText());
		
		return view;
	}

}
