package com.huasheng.sysq.activity.report;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import com.huasheng.sysq.R;
import com.huasheng.sysq.service.StaticsService;

public class ReportActivity extends Activity{
	
	private ListView interviewLV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_report);
		
		interviewLV = (ListView)findViewById(R.id.lv_report_interview);
		List<Map<String,String>> data = StaticsService.reportInterview();
		InterviewAdapter adapter = new InterviewAdapter(this,R.layout.item_report_interview,data,this);
		interviewLV.setAdapter(adapter);
		
	}

	
}
