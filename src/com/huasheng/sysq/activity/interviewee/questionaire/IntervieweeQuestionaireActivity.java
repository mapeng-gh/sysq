package com.huasheng.sysq.activity.interviewee.questionaire;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;

import com.huasheng.sysq.R;
import com.huasheng.sysq.activity.interviewee.IntervieweeActivity;
import com.huasheng.sysq.model.InterviewQuestionaireWrap;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.util.BaseActivity;

public class IntervieweeQuestionaireActivity extends BaseActivity implements OnClickListener{
	
	private int interviewBasicId;
	private ListView questionaireListLV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_interviewee_questionaire);
		
		//加载数据
		this.interviewBasicId = this.getIntent().getIntExtra("interviewBasicId", -1);
		List<InterviewQuestionaireWrap> interviewQuestionaireWrapList = InterviewService.getInterviewQuestionaireList(this.interviewBasicId);
		
		//渲染页面
		this.questionaireListLV = (ListView)findViewById(R.id.lv_interviewee_questionaire_list);
		IntervieweeQuestionaireAdapter adapter = new IntervieweeQuestionaireAdapter(this,R.layout.item_interviewee_questionaire,interviewQuestionaireWrapList,this);
		this.questionaireListLV.setAdapter(adapter);
	}

	@Override
	public void onClick(View view) {
		
		if(view.getId() == R.id.tv_interviewee_questionaire_list_item_view){//查看答案列表
			String questionaireCode = (String)view.getTag();
			this.viewAnswers(questionaireCode);
			
		}else if(view.getId() == R.id.tv_interviewee_questionaire_list_item_view_remark){//查看备注
			String remark = (String)view.getTag();
			this.viewRemark(remark);
		}
	}
	
	/**
	 * 查看备注
	 * @param remark
	 */
	private void viewRemark(String remark){
		AlertDialog.Builder quitReasonBuilder = new AlertDialog.Builder(this);
		quitReasonBuilder.setTitle("备注");
		quitReasonBuilder.setMessage(remark);
		quitReasonBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//close
			}
		});
		AlertDialog quitReasonDialog = quitReasonBuilder.create();
		quitReasonDialog.setCancelable(false);
		quitReasonDialog.setCanceledOnTouchOutside(false);
		quitReasonDialog.show();
	}
	
	/**
	 * 查看答案列表
	 * @param questionaireCode
	 */
	private void viewAnswers(String questionaireCode){
		Intent intent = new Intent(this,IntervieweeAnswerActivity.class);
		intent.putExtra("interviewBasicId", this.interviewBasicId);
		intent.putExtra("questionaireCode", questionaireCode);
		this.startActivity(intent);
	}
	
	@Override
	public void onBackPressed() {//返回到受访者一览
		Intent intent = new Intent(this,IntervieweeActivity.class);
		this.startActivity(intent);
	}

}
