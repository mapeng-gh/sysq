package com.huasheng.sysq.activity.interviewee.answers;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;

import com.huasheng.sysq.R;
import com.huasheng.sysq.activity.interview.InterviewActivity;
import com.huasheng.sysq.model.InterviewQuestionWrap;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.util.FormatUtils;

public class IntervieweeAnswerActivity extends Activity implements OnClickListener{
	
	private int interviewBasicId;
	private String questionaireCode;
	
	private ListView questionLV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_interviewee_answers);
		
		Intent intent = this.getIntent();
		interviewBasicId = intent.getIntExtra("interviewBasicId", -1);
		questionaireCode = intent.getStringExtra("questionaireCode");
		
		questionLV = (ListView)findViewById(R.id.lv_interviewee_answers_question);
		
		List<InterviewQuestionWrap> interviewQuestionWrapList = InterviewService.getWrapInterviewQuestionList(this.interviewBasicId, this.questionaireCode);
		
		//处理问题分段、插值
		for(InterviewQuestionWrap interviewQuestionWrap : interviewQuestionWrapList){
			interviewQuestionWrap.getQuestion().setDescription(this.handleQuestionDescription(interviewQuestionWrap.getQuestion().getDescription()));
		}
		
		IntervieweeQuestionAdapter adapter = new IntervieweeQuestionAdapter(this,R.layout.item_interviewee_answers_question,interviewQuestionWrapList,this);
		questionLV.setAdapter(adapter);
		
	}
	
	private String handleQuestionDescription(String description){
		
		//分段
		description = FormatUtils.handPara4App(description);
		
		//插值
		while(description.indexOf("<span") != -1){
			int spanStart = description.indexOf("<span");
			int spanEnd = description.indexOf("</span>");
			String span = description.substring(spanStart, spanEnd + "</span>".length());
			String answerCode = span.replace("<span name=\"", "").replace("\"></span>", "");
			String answerText = InterviewService.getInterviewAnswer(this.interviewBasicId,answerCode).getAnswerText();
			description = description.replace("<span name=\""+answerCode+"\"></span>",answerText);
		}
		
		return description;
	}

	@Override
	public void onClick(View view) {
		if(view.getId() == R.id.ll_interviewee_answers_question){
			String questionCode = (String)view.getTag();
			Intent intent = new Intent(this,InterviewActivity.class);
			intent.putExtra("operateType", "modify");
			intent.putExtra("interviewBasicId", this.interviewBasicId);
			intent.putExtra("questionaireCode", this.questionaireCode);
			intent.putExtra("questionCode", questionCode);
			this.startActivity(intent);
		}
	}

}
