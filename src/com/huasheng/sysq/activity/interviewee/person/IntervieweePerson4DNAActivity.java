package com.huasheng.sysq.activity.interviewee.person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.util.ScanConstants;
import com.huasheng.sysq.util.SysqApplication;

public class IntervieweePerson4DNAActivity extends Activity implements OnClickListener{
	
	private int interviewBasicId;
	private ListView dnaLV;
	private Button addBtn;
	
	private static final int REQUEST_CODE_DNA = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_interviewee_person_dna);
		
		//获取参数
		Intent intent = this.getIntent();
		this.interviewBasicId = intent.getIntExtra("interviewBasicId", -1);
		
		addBtn = (Button)findViewById(R.id.interviewee_person_dna_add_btn);
		addBtn.setOnClickListener(this);
		dnaLV = (ListView)this.findViewById(R.id.interviewee_person_dna_container_lv);
		
		//初始化列表
		this.renderDNAList();
	}
	
	/**
	 * 渲染列表
	 */
	private void renderDNAList(){
		
		List<String> data = new ArrayList<String>();
		InterviewBasic interviewBasic = InterviewService.findInterviewBasicById(this.interviewBasicId);
		String dnas = interviewBasic.getDna();
		if(!StringUtils.isEmpty(dnas)){
			if(dnas.contains(",")){
				String[] dnaArray = dnas.split(",");
				data.addAll(Arrays.asList(dnaArray));
			}else{
				data.add(dnas);
			}
		}
		
		//渲染
		IntervieweePersonDNAAdapter adapter = new IntervieweePersonDNAAdapter(this, R.layout.item_interviewee_person_dna, data, this);
		dnaLV.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.interviewee_person_dna_add_btn){//添加
			this.addDNA();
		}else if(v.getId() == R.id.interviewee_person_dna_del_tv){//删除
			String delDna = (String)v.getTag();
			this.deleteDNA(delDna);
		}
	}
	
	/**
	 * 删除DNA
	 */
	private void deleteDNA(final String delDna){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("确认");
		builder.setMessage("确定删除该DNA吗？");
		builder.setPositiveButton("确定", new Dialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				//删除
				InterviewBasic interviewBasic = InterviewService.findInterviewBasicById(IntervieweePerson4DNAActivity.this.interviewBasicId);
				String dnas = interviewBasic.getDna();
				
				if(!dnas.contains(",")){
					interviewBasic.setDna("");
				}else{
					String[] dnaArray = dnas.split(",");
					List<String> dnaList = new ArrayList<String>();
					for(String dna : dnaArray){
						if(!dna.equals(delDna)){
							dnaList.add(dna);
						}
					}
					interviewBasic.setDna(StringUtils.join(dnaList,","));
				}
				
				InterviewService.updateInterviewBasic(interviewBasic);
				
				//刷新
				SysqApplication.showMessage("删除成功");
				IntervieweePerson4DNAActivity.this.renderDNAList();
			}
		});
		builder.setNegativeButton("取消", new Dialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		AlertDialog dialog = builder.create();
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}
	
	/**
	 * 添加DNA
	 */
	private void addDNA(){
		Intent intent = new Intent(ScanConstants.INTENT_ACTION_SCAN);
		this.startActivityForResult(intent,REQUEST_CODE_DNA);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == Activity.RESULT_OK){
			if(requestCode == REQUEST_CODE_DNA){
				String dna = data.getStringExtra(ScanConstants.ACTION_SCAN_KEY);
				this.saveDNA(dna);
			}
		}
	}
	
	/**
	 * 保存DNA
	 * @param dna
	 */
	private void saveDNA(String dna){
		
		InterviewBasic interviewBasic = InterviewService.findInterviewBasicById(this.interviewBasicId);
		String dnas = interviewBasic.getDna();
		
		//检查DNA是否重复
		/*if(!StringUtils.isEmpty(dnas)){
			if(!dnas.contains(",")){
				if(dnas.equals(dna)){
					SysqApplication.showMessage("该DNA已存在");
					return;
				}
			}else{
				List<String> dnaList = Arrays.asList(dnas.split(","));
				if(dnaList.contains(dna)){
					SysqApplication.showMessage("该DNA已存在");
					return;
				}
			}
		}*/
		
		//保存DNa
		if(StringUtils.isEmpty(dnas)){
			dnas = dna;
		}else{
			dnas = dnas + "," + dna;
		}
		interviewBasic.setDna(dnas);
		InterviewService.updateInterviewBasic(interviewBasic);
		
		//刷新列表
		SysqApplication.showMessage("保存成功");
		this.renderDNAList();
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this,IntervieweePersonNavActivity.class);
		intent.putExtra("interviewBasicId", this.interviewBasicId);
		this.startActivity(intent);
	}
}
