package com.huasheng.sysq.activity.interviewee.person;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.InterviewBasicWrap;
import com.huasheng.sysq.model.Interviewee;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.util.CommonUtils;
import com.huasheng.sysq.util.DialogUtils;
import com.huasheng.sysq.util.PathConstants;

public class IntervieweePerson4PhotoActivity extends Activity implements OnClickListener{
	
	private int interviewBasicId;
	private ListView imgLV;
	private Button takePhotoBtn;
	
	public static int RC_PHOTO = 1;	//拍照

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_interviewee_person_photo);
		
		//获取参数
		Intent intent = this.getIntent();
		this.interviewBasicId = intent.getIntExtra("interviewBasicId", -1);
		
		takePhotoBtn = (Button)findViewById(R.id.interviewee_person_photo_add_btn);
		takePhotoBtn.setOnClickListener(this);
		imgLV = (ListView)findViewById(R.id.interviewee_person_photo_lv);
		
		//初始化列表
		this.renderPhotoList();
	}
	
	/**
	 * 渲染图片列表
	 */
	private void renderPhotoList(){
		
		//获取图片数据
		List<File> data = new ArrayList<File>();
		InterviewBasicWrap interviewBasicWrap = InterviewService.findInterviewBasicById(this.interviewBasicId);
		Interviewee interviewee = interviewBasicWrap.getInterviewee();
		File imgDir = new File(PathConstants.getMediaDir(),interviewee.getIdentityCard()+"("+interviewee.getUsername()+")"+File.separator+"photo");
		if(imgDir.exists()){
			File[] imgFiles = imgDir.listFiles();
			if(imgFiles != null && imgFiles.length > 0){
				Arrays.sort(imgFiles, new Comparator<File>() {
					@Override
					public int compare(File lhs, File rhs) {
						return lhs.getName().compareTo(rhs.getName());
					}
				});
				data = Arrays.asList(imgFiles);
			}
		}
		
		//渲染
		IntervieweePersonPhotoAdapter adapter = new IntervieweePersonPhotoAdapter(this, R.layout.item_interviewee_person_photo, data, this);
		imgLV.setAdapter(adapter);
		
	}
	
	/**
	 * 拍摄图片
	 */
	private void takePhoto(){
		
		//设置图片存储路径
		InterviewBasicWrap interviewBasicWrap = InterviewService.findInterviewBasicById(this.interviewBasicId);
		Interviewee interviewee = interviewBasicWrap.getInterviewee();
		File imgDir = new File(PathConstants.getMediaDir(),interviewee.getIdentityCard()+"("+interviewee.getUsername()+")"+File.separator+"photo");
		if(!imgDir.exists()){
			imgDir.mkdirs();
		}
		File imgFile = new File(imgDir,CommonUtils.getCustomDateTime("yyyyMMddHHmmss")+".jpg");
		try{
			if(imgFile.exists()){
				imgFile.delete();
			}
			imgFile.createNewFile();
		}catch(IOException e){
		}
		
		//拍照
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		Uri imgUri = Uri.fromFile(imgFile);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
		this.startActivityForResult(intent,RC_PHOTO);
	}
	
	/**
	 * 删除图片
	 * @param photoFile
	 */
	private void deletePhoto(final File photoFile){
		
		DialogUtils.showConfirmDialog(this, "确定删除该图片吗？", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				if(photoFile.exists()){
					photoFile.delete();
					IntervieweePerson4PhotoActivity.this.renderPhotoList();
				}
			}
		});
	}
	
	/**
	 * 预览图片
	 * @param photoFile
	 */
	private void previewPhoto(File photoFile){
		Intent intent = new Intent(this,IntervieweePerson4PhotoPreviewActivity.class);
		intent.putExtra("photoFilePath", photoFile.getAbsolutePath());
		this.startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == Activity.RESULT_OK){
			if(requestCode == RC_PHOTO){	//刷新列表
				this.renderPhotoList();
			}
		}
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.interviewee_person_photo_add_btn){//拍摄
			takePhoto();
		}else if(v.getId() == R.id.interviewee_person_photo_del_tv){//删除
			File photoFile = (File)v.getTag();
			deletePhoto(photoFile);
		}else if(v.getId() == R.id.interviewee_person_photo_img_iv){//预览
			File photoFile = (File)v.getTag();
			previewPhoto(photoFile);
		}
	}

}
