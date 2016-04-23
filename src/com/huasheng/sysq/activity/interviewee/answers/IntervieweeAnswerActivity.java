package com.huasheng.sysq.activity.interviewee.answers;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.InterviewQuestionWrap;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.util.BreakLineUtils;

public class IntervieweeAnswerActivity extends Activity{
	
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
		
		//∂Œ¬‰ªª––¥¶¿Ì
		for(InterviewQuestionWrap interviewQuestionWrap : interviewQuestionWrapList){
			interviewQuestionWrap.getQuestion().setDescription(BreakLineUtils.handParaInApp(interviewQuestionWrap.getQuestion().getDescription()));
		}
		
		IntervieweeQuestionAdapter adapter = new IntervieweeQuestionAdapter(this,R.layout.item_interviewee_answers_question,interviewQuestionWrapList,this);
		questionLV.setAdapter(adapter);
		
	}

}
