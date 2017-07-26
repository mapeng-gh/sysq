package com.huasheng.sysq.activity.settings;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;

import com.huasheng.sysq.R;
import com.huasheng.sysq.activity.interviewee.person.IntervieweePersonNavActivity;
import com.huasheng.sysq.model.InterviewBasicWrap;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.util.SysqContext;

/**
 * 设置
 * @author mapeng
 *
 */
public class Settings4RepaireIntervieweeListActivity extends Activity implements OnClickListener{
	
	private ListView repaireLV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.setContentView(R.layout.activity_settings_repaire_intervieweelist);
		
		initComponents();
	}
	
	//初始化组件
	private void initComponents(){
		repaireLV = (ListView)super.findViewById(R.id.lv_settings_repaire_intervieweelist);
		List<InterviewBasicWrap> data = InterviewService.getAllRepaireIntervieweeList(SysqContext.getCurrentVersion().getId());
		Settings4RepaireIntervieweeListAdapter adapter = new Settings4RepaireIntervieweeListAdapter(this,R.layout.item_settings_repaire_intervieweelist,data,this);
		repaireLV.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.tv_settings_repaire_intervieweelist_repaire){//数据修复
			int interviewBasicId = (Integer)v.getTag();
			Intent intent = new Intent(this,Settings4RepaireIntervieweeDetailActivity.class);
			intent.putExtra("interviewBasicId", interviewBasicId);
			this.startActivity(intent);
		}
	}
}
