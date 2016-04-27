package com.huasheng.sysq.activity.interviewee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huasheng.sysq.R;
import com.huasheng.sysq.activity.interview.InterviewActivity;
import com.huasheng.sysq.activity.interviewee.questionaire.IntervieweeQuestionaireActivity;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.Page;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.util.ScanConstants;

public class IntervieweeActivity extends Activity implements OnClickListener{
	
	private ListView listView;
	private IntervieweeAdapter adapter;
	
	private LinearLayout bodyLL;
	private TextView noDataTV;
	
	private RelativeLayout paginationRL;
	private int currentPage;
	private int totalPage;
	private TextView previousTV;
	private TextView nextTV;
	private TextView currentTV;
	private TextView totalTV;
	
	private EditText searchET;
	private Button searchBtn;
	private String searchStr;
	
	private ImageButton scanImgBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_interviewee);
		
		listView = (ListView)findViewById(R.id.lv_interviewee_list);
		
		bodyLL = (LinearLayout)findViewById(R.id.ll_interviewee_list_body);
		noDataTV = (TextView)findViewById(R.id.tv_interviewee_list_no_data);
		
		paginationRL = (RelativeLayout)findViewById(R.id.rl_interviewee_list_pagination);
		currentTV = (TextView)findViewById(R.id.tv_interviewee_list_pagination_current);
		totalTV = (TextView)findViewById(R.id.tv_interviewee_list_pagination_total);
		previousTV = (TextView)findViewById(R.id.tv_interviewee_list_pagination_previous);
		nextTV = (TextView)findViewById(R.id.tv_interviewee_list_pagination_next);
		
		searchET = (EditText)findViewById(R.id.et_interviewee_search);
		searchBtn = (Button)findViewById(R.id.btn_interviewee_search);
		
		scanImgBtn = (ImageButton)findViewById(R.id.btn_interviewee_barcode);
		scanImgBtn.setOnClickListener(this);
		
		Page<InterviewBasic> page = InterviewService.searchInterviewBasic("", 1,Page.PAGE_SIZE);
		this.refreshListView(page);
		
		previousTV.setOnClickListener(this);
		nextTV.setOnClickListener(this);
		
		searchBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		
		if(view.getId() == R.id.tv_interviewee_list_pagination_previous){//上一页
			
			this.jumpToPreviousPage();
			
		}else if(view.getId() == R.id.tv_interviewee_list_pagination_next){//下一页
			
			this.jumpToNextPage();
			
		}else if(view.getId() == R.id.btn_interviewee_search){//搜索
			
			this.search();
			
		}else if(view.getId() == R.id.btn_interviewee_barcode){//二维码
			
			this.scan();
			
		}else if(view.getId() == R.id.tv_interviewee_item_view){//查看问卷
			
			int interviewBasicId = (Integer)view.getTag();
			this.viewQuestionaire(interviewBasicId);
			
		}else if(view.getId() == R.id.tv_interviewee_item_continue){//继续
			
			int interviewBasicId = (Integer)view.getTag();
			this.continueInterview(interviewBasicId);
			
		}
	}
	
	private void continueInterview(int interviewBasicId){
		
		Intent intent = new Intent(this,InterviewActivity.class);
		intent.putExtra("operateType", "continue");
		intent.putExtra("interviewBasicId", interviewBasicId);
		this.startActivity(intent);
	}
	
	private void viewQuestionaire(int interviewBasicId){
		Intent intent = new Intent(this,IntervieweeQuestionaireActivity.class);
		intent.putExtra("interviewBasicId", interviewBasicId);
		this.startActivity(intent);
	}
	
	private void scan(){
		
		Intent intent = new Intent(ScanConstants.INTENT_ACTION_SCAN);
		this.startActivityForResult(intent, 1);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(resultCode == Activity.RESULT_OK){
			
			if(requestCode == 1){
				
				String scanResult = data.getStringExtra(ScanConstants.ACTION_SCAN_KEY);
				this.searchET.setText(scanResult);
			}
		}
	}

	private void search(){
		searchStr = searchET.getText().toString();
		Page<InterviewBasic> page = InterviewService.searchInterviewBasic(searchStr, 1, Page.PAGE_SIZE);;
		this.refreshListView(page);
	}
	
	private void jumpToNextPage(){
		if(this.currentPage == this.totalPage){
			Toast.makeText(this, "已经到最后一页",Toast.LENGTH_SHORT).show();
		}else{
			Page<InterviewBasic> page = InterviewService.searchInterviewBasic(searchStr,this.currentPage+1,Page.PAGE_SIZE);
			this.refreshListView(page);
		}
	}
	
	private void jumpToPreviousPage(){
		if(this.currentPage == 1){
			Toast.makeText(this, "已经到第一页",Toast.LENGTH_SHORT).show();
		}else{
			Page<InterviewBasic> page = InterviewService.searchInterviewBasic(searchStr,this.currentPage-1,Page.PAGE_SIZE);;
			this.refreshListView(page);
		}
	}
	
	private void refreshListView(Page<InterviewBasic> page){
		if(page.getData() == null || page.getData().size() == 0){
			bodyLL.setVisibility(View.GONE);
			paginationRL.setVisibility(View.GONE);
			noDataTV.setVisibility(View.VISIBLE);
		}else{
			if(adapter ==null){
				adapter = new IntervieweeAdapter(this,R.layout.item_interviewee, page.getData(),this);
				listView.setAdapter(adapter);
			}else{
				adapter.clear();
				adapter.addAll(page.getData());
			}
			
			currentPage = page.getPageNo();
			totalPage = page.getTotalPages();
			
			currentTV.setText(page.getPageNo()+"");
			totalTV.setText(page.getTotalPages()+"");
		}
	}

}
