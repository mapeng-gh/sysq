package com.huasheng.sysq.activity.interviewee.answers;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.InterviewQuestionWrap;

public class IntervieweeQuestionAdapter extends ArrayAdapter<InterviewQuestionWrap>{
	
	private int resource;
	private IntervieweeAnswerActivity activity;

	public IntervieweeQuestionAdapter(Context context, int resource,List<InterviewQuestionWrap> objects,IntervieweeAnswerActivity activity) {
		super(context, resource, objects);
		this.resource = resource;
		this.activity = activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		//获取数据
		InterviewQuestionWrap interviewQuestionWrap = getItem(position);
		
		//实例化一个view
		View view;
		if(convertView == null){
			view = LayoutInflater.from(getContext()).inflate(this.resource, null);
		}else{
			view = convertView;
		}
		
		//绑定问题
		TextView questionDescTV = (TextView)view.findViewById(R.id.tv_interviewee_answers_qustion_description);
		questionDescTV.setText(interviewQuestionWrap.getQuestion().getDescription());
		
		//遍历答案
		ListView answerLV = (ListView)view.findViewById(R.id.lv_interviewee_answers_answer);
		IntervieweeAnswerAdapter adapter = new IntervieweeAnswerAdapter(this.activity,R.layout.item_interviewee_answers_answer,interviewQuestionWrap.getAnswerWrapList(),this.activity);
		answerLV.setAdapter(adapter);
		
		//绑定修改问题事件
		view.setTag(interviewQuestionWrap.getQuestion().getCode());
		view.setOnClickListener(this.activity);
		
		return view;
	}

}
