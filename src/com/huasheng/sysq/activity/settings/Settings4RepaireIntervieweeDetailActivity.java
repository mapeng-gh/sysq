package com.huasheng.sysq.activity.settings;

import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.InterviewBasicWrap;
import com.huasheng.sysq.model.Interviewer;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.service.UserCenterService;
import com.huasheng.sysq.util.DialogUtils;
import com.huasheng.sysq.util.upload.UploadConstants;

/**
 * 设置
 * @author mapeng
 *
 */
public class Settings4RepaireIntervieweeDetailActivity extends Activity implements OnClickListener{
	
	private int interviewBasicId;
	
	private TextView idTv;
	private TextView nameTv;
	private Spinner dockerSpin;
	private Button submitBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.setContentView(R.layout.activity_settings_repaire_intervieweedetail);
		
		//获取参数
		Intent intent = this.getIntent();
		this.interviewBasicId = intent.getIntExtra("interviewBasicId", -1);
		
		initComponents();
		
		render();
	}
	
	/**
	 * 初始化组件
	 */
	private void initComponents(){
		this.idTv = (TextView)super.findViewById(R.id.settings_repaire_intervieweedetail_id_tv);
		this.nameTv = (TextView)super.findViewById(R.id.settings_repaire_intervieweedetail_name_tv);
		this.dockerSpin = (Spinner)super.findViewById(R.id.settings_repaire_intervieweedetail_docker_spin);
		this.submitBtn = (Button)super.findViewById(R.id.settings_repaire_intervieweedetail_submit_btn);
		this.submitBtn.setOnClickListener(this);
	}
	
	/**
	 *  渲染
	 * @param interviewBasicWrap
	 */
	private void render(){
		
		InterviewBasicWrap interviewBasicWrap = InterviewService.findInterviewBasicById(this.interviewBasicId);
		this.idTv.setText(interviewBasicWrap.getInterviewBasic().getId()+"");
		this.nameTv.setText(interviewBasicWrap.getInterviewee().getUsername());
		
		//下拉框
		List<Interviewer> dockerList = UserCenterService.getDockerList();
		if(dockerList != null && dockerList.size() > 0){
			ArrayAdapter<Interviewer> adapter = new ArrayAdapter<Interviewer>(this, android.R.layout.simple_spinner_dropdown_item,dockerList);
			this.dockerSpin.setAdapter(adapter);
		}
	}
	
	/**
	 * 修改访谈医生
	 */
	private void modifyInterviewer(){
		
		//获取选中医生
		Interviewer interviewer = (Interviewer)this.dockerSpin.getSelectedItem();
		if(interviewer == null){
			DialogUtils.showLongToast(this, "请您先注册医生信息");
			return;
		}
		final int interviewerId = interviewer.getId();
		
		//确认提示
		DialogUtils.showConfirmDialog(this, "确认", "您确定修改该受访者的访谈医生吗？","确定",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				//修改
				InterviewBasic interviewBasic = InterviewService.findInterviewBasicById(Settings4RepaireIntervieweeDetailActivity.this.interviewBasicId).getInterviewBasic();
				interviewBasic.setInterviewerId(interviewerId);
				if(interviewBasic.getUploadStatus() == UploadConstants.upload_status_uploaded){
					interviewBasic.setUploadStatus(UploadConstants.upload_status_modified);
				}
				InterviewService.updateInterviewBasic(interviewBasic);
				DialogUtils.showLongToast(Settings4RepaireIntervieweeDetailActivity.this, "修改成功");
			}
		}, "取消", null);
	}
	

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.settings_repaire_intervieweedetail_submit_btn){//保存
			this.modifyInterviewer();
		}
	}
}
