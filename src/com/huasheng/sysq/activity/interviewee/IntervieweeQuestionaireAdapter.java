package com.huasheng.sysq.activity.interviewee;

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
		
		//��ȡ����
		InterviewQuestionaireWrap interviewQuestionaireWrap = getItem(position);
		
		//����view
		View view;
		if(convertView == null){
			view = LayoutInflater.from(getContext()).inflate(this.resource, null);
		}else{
			view = convertView;
		}
		
		//������
		TextView titleTV = (TextView)view.findViewById(R.id.tv_interviewee_questionaire_list_item_title);
		titleTV.setText(interviewQuestionaireWrap.getQuestionaire().getCode() + " " + interviewQuestionaireWrap.getQuestionaire().getTitle());
		
		TextView statusTV = (TextView)view.findViewById(R.id.tv_interviewee_questionaire_list_item_status);
		if(interviewQuestionaireWrap.getInterviewQuestionaire().getStatus() == InterviewQuestionaire.STATUS_DOING){
			statusTV.setText("���ڽ���");
		}else if(interviewQuestionaireWrap.getInterviewQuestionaire().getStatus() == InterviewQuestionaire.STATUS_BREAK){
			statusTV.setText("�ѽ���");
		}else if(interviewQuestionaireWrap.getInterviewQuestionaire().getStatus() == InterviewQuestionaire.STATUS_DONE){
			statusTV.setText("�����");
		}
		
		TextView lastModifiedTimeTV = (TextView)view.findViewById(R.id.tv_interviewee_questionaire_list_item_last_modified_time);
		lastModifiedTimeTV.setText(interviewQuestionaireWrap.getInterviewQuestionaire().getLastModifiedTime());
		
		//���¼�
		TextView viewTV = (TextView)view.findViewById(R.id.tv_interviewee_questionaire_list_item_view);
		viewTV.setOnClickListener(this.activity);
		viewTV.setTag(interviewQuestionaireWrap.getQuestionaire().getCode());
		
		return view;
	}

}
