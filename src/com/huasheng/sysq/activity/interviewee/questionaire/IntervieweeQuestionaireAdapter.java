package com.huasheng.sysq.activity.interviewee.questionaire;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.InterviewQuestionaire;
import com.huasheng.sysq.model.InterviewQuestionaireWrap;

public class IntervieweeQuestionaireAdapter extends ArrayAdapter<InterviewQuestionaireWrap>{
	
	private int resource;
	private IntervieweeQuestionaireActivity activity;

	public IntervieweeQuestionaireAdapter(Context context, int resource,List<InterviewQuestionaireWrap> objects,IntervieweeQuestionaireActivity activity) {
		super(context, resource, objects);
		this.resource = resource;
		this.activity = activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		//获取数据
		InterviewQuestionaireWrap interviewQuestionaireWrap = getItem(position);
		
		//单例view
		View view;
		if(convertView == null){
			view = LayoutInflater.from(getContext()).inflate(this.resource, null);
		}else{
			view = convertView;
		}
		
		//绑定数据
		TextView titleTV = (TextView)view.findViewById(R.id.tv_interviewee_questionaire_list_item_title);
		titleTV.setText(interviewQuestionaireWrap.getQuestionaire().getCode() + " " + interviewQuestionaireWrap.getQuestionaire().getTitle());
		
		TextView statusTV = (TextView)view.findViewById(R.id.tv_interviewee_questionaire_list_item_status);
		if(interviewQuestionaireWrap.getInterviewQuestionaire().getStatus() == InterviewQuestionaire.STATUS_DOING){
			statusTV.setText("正在进行");
		}else if(interviewQuestionaireWrap.getInterviewQuestionaire().getStatus() == InterviewQuestionaire.STATUS_BREAK){
			statusTV.setText("已结束");
		}else if(interviewQuestionaireWrap.getInterviewQuestionaire().getStatus() == InterviewQuestionaire.STATUS_DONE){
			statusTV.setText("已完成");
		}
		
		TextView lastModifiedTimeTV = (TextView)view.findViewById(R.id.tv_interviewee_questionaire_list_item_last_modified_time);
		lastModifiedTimeTV.setText(interviewQuestionaireWrap.getInterviewQuestionaire().getLastModifiedTime());
		
		//绑定事件：查看答案
		TextView viewTV = (TextView)view.findViewById(R.id.tv_interviewee_questionaire_list_item_view);
		viewTV.setOnClickListener(this.activity);
		viewTV.setTag(interviewQuestionaireWrap.getQuestionaire().getCode());
		
		//绑定事件：查看答案
		TextView viewRemarkTV = (TextView)view.findViewById(R.id.tv_interviewee_questionaire_list_item_view_remark);
		viewRemarkTV.setOnClickListener(this.activity);
		viewRemarkTV.setTag(interviewQuestionaireWrap.getInterviewQuestionaire().getRemark());
		
		return view;
	}

}
