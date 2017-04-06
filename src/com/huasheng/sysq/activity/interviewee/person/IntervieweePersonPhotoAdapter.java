package com.huasheng.sysq.activity.interviewee.person;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.util.interview.InterviewConstants;

public class IntervieweePersonPhotoAdapter extends ArrayAdapter<File>{
	
	private int resource;
	private IntervieweePerson4PhotoActivity activity;

	public IntervieweePersonPhotoAdapter(Context context, int resource,List<File> objects,IntervieweePerson4PhotoActivity activity) {
		super(context, resource, objects);
		this.resource = resource;
		this.activity = activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		//获取数据
		File photoItem = getItem(position);
		
		//实例化一个view
		View photoView;
		if(convertView == null){
			photoView = LayoutInflater.from(getContext()).inflate(this.resource, null);
		}else{
			photoView = convertView;
		}
		
		//图片
		ImageView imgIV = (ImageView)photoView.findViewById(R.id.interviewee_person_photo_img_iv);
		imgIV.setImageURI(Uri.fromFile(photoItem));
		imgIV.setTag(photoItem);
		imgIV.setOnClickListener(this.activity);
		
		//拍摄时间
		TextView timeTV = (TextView)photoView.findViewById(R.id.interviewee_person_photo_time_tv);
		timeTV.setText(photoItem.getName());
		
		//删除
		TextView delTV = (TextView)photoView.findViewById(R.id.interviewee_person_photo_del_tv);
		delTV.setOnClickListener(this.activity);
		delTV.setTag(photoItem);
		
		return photoView;
	}
}
