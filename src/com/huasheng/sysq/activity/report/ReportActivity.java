package com.huasheng.sysq.activity.report;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.Interviewer;
import com.huasheng.sysq.service.StaticsService;

public class ReportActivity extends Activity{
	
	private ListView interviewLV;
	private ListView dnaLV;
	private ListView questionaireLV;
	private ListView interviewerLV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_report);
		
		interviewLV = (ListView)findViewById(R.id.lv_report_interview);
		List<Map<String,String>> data = StaticsService.reportInterview();
		InterviewAdapter adapter = new InterviewAdapter(this,R.layout.item_report_interview,data,this);
		interviewLV.setAdapter(adapter);
		
		dnaLV = (ListView)findViewById(R.id.lv_report_dna);
		List<Map<String,String>> dnaData = StaticsService.reportDNA();
		DNAAdapter dnaAdapter = new DNAAdapter(this,R.layout.item_report_dna,dnaData,this);
		dnaLV.setAdapter(dnaAdapter);
		
		questionaireLV = (ListView)findViewById(R.id.lv_report_questionaire);
		List<Map<String,String>> questionaireData = StaticsService.reportQuestionaire();
		QuestionaireAdapter questionaireAdapter = new QuestionaireAdapter(this,R.layout.item_report_questionaire,questionaireData,this);
		questionaireLV.setAdapter(questionaireAdapter);
		
		interviewerLV = (ListView)findViewById(R.id.lv_report_interviewer);
		List<Interviewer> interviewerData = StaticsService.reportInterviewer();
		InterviewerAdapter interviewerAdapter = new InterviewerAdapter(this,R.layout.item_report_interviewer,interviewerData,this);
		interviewerLV.setAdapter(interviewerAdapter);
	}
}
