package com.huasheng.sysq.activity.interviewee.answers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.huasheng.sysq.R;
import com.huasheng.sysq.activity.IndexActivity;
import com.huasheng.sysq.activity.LoginActivity;
import com.huasheng.sysq.activity.interview.InterviewActivity;
import com.huasheng.sysq.activity.interviewee.questionaire.IntervieweeQuestionaireActivity;
import com.huasheng.sysq.db.InterviewBasicDB;
import com.huasheng.sysq.db.QuestionDB;
import com.huasheng.sysq.model.InterviewAnswer;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.InterviewQuestionWrap;
import com.huasheng.sysq.model.Question;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.util.BaseActivity;
import com.huasheng.sysq.util.FormatUtils;
import com.huasheng.sysq.util.InterviewContext;
import com.huasheng.sysq.util.SysqApplication;
import com.huasheng.sysq.util.SysqContext;

public class IntervieweeAnswerActivity extends BaseActivity implements OnClickListener{
	
	private int interviewBasicId;
	private String questionaireCode;
	
	private ListView questionLV;
	private LinearLayout answersLL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		Intent intent = this.getIntent();
		interviewBasicId = intent.getIntExtra("interviewBasicId", -1);
		questionaireCode = intent.getStringExtra("questionaireCode");
		
		if(questionaireCode.equals("LHC")){//生活日历问卷答案定制
			showLHCAnswers();
		}else{
			showAnswers();
		}
	}
	
	private void showLHCAnswers(){
		
		setContentView(R.layout.activity_interviewee_answers_lhc);
		
		//查询访问答案
		List<InterviewAnswer> interviewAnswerList = InterviewService.getInterviewAnswerList(this.interviewBasicId);
		
		if(interviewAnswerList == null || interviewAnswerList.size() <= 0){//没有任何答案（没开始答题）
			return;
		}
		
		//渲染表格数据
		answersLL = (LinearLayout)findViewById(R.id.ll_interviewee_answers_lhc);
		for(int i=0;i<answersLL.getChildCount();i++){
			View rowView = answersLL.getChildAt(i);
			if(rowView instanceof LinearLayout){//忽略行分割线
				LinearLayout ll = (LinearLayout)rowView;
				for(int j=0;j<ll.getChildCount();j++){
					View cellView = ll.getChildAt(j);
					if(cellView instanceof TextView){//忽略列分割线
						TextView tv = (TextView)cellView;
						if(StringUtils.isEmpty(tv.getText())){//固定值的TextView不处理
							final String tag = (String)tv.getTag();
							String text = searchAnswerValue(tag,interviewAnswerList);
							tv.setText(text);
							
							//绑定事件
							if(!StringUtils.isEmpty(text)){
								
								tv.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View view) {
										
										String answerCode = tag.contains(",") ? tag.split(",")[0] : tag;
										String questionCode = InterviewService.getInterviewAnswer(IntervieweeAnswerActivity.this.interviewBasicId, answerCode).getQuestionCode();
										
										//跳转问题页面
										Intent intent = new Intent(IntervieweeAnswerActivity.this,InterviewActivity.class);
										intent.putExtra("operateType", "modify");
										intent.putExtra("interviewBasicId", IntervieweeAnswerActivity.this.interviewBasicId);
										intent.putExtra("questionaireCode", IntervieweeAnswerActivity.this.questionaireCode);
										intent.putExtra("questionCode", questionCode);
										IntervieweeAnswerActivity.this.startActivity(intent);
										
									}
								});
								
								
							}
						}
					}
				}
			}
		}
		
	}
	
	private String searchAnswerValue(String tag,List<InterviewAnswer> interviewAnswerList){
		
		List<String> answerTexts = new ArrayList<String>();
		String[] answerCodeArray = tag.split(",");
		for(String answerCode : answerCodeArray){
			for(InterviewAnswer interviewAnswer : interviewAnswerList){
				if(answerCode.equals(interviewAnswer.getAnswerCode())){
					answerTexts.add(interviewAnswer.getAnswerValue());
					break;
				}
			}
		}
		
		if(answerTexts.size() == 0){
			return "";
		}
		if(answerTexts.size() == 1){
			return answerTexts.get(0);
		}
		return StringUtils.join(answerTexts,",");
	}
	
	private void showAnswers(){
		
		setContentView(R.layout.activity_interviewee_answers);
		
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
			InterviewAnswer interviewAnswer = InterviewService.getInterviewAnswer(this.interviewBasicId,answerCode);
			String answerText = "";
			if(interviewAnswer != null){
				answerText = interviewAnswer.getAnswerText();
			}
			description = description.replace("<span name=\""+answerCode+"\"></span>",answerText);
		}
		
		return description;
	}

	@Override
	public void onClick(final View view) {
		if(view.getId() == R.id.ll_interviewee_answers_question){
			
			//询问是否确认修改问题
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("确定修改该问题吗？");
			builder.setIcon(android.R.drawable.ic_dialog_info);
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					//检查访谈记录状态（状态为“中断结束”的问题不允许修改答案）
					InterviewBasic interviewBasic = InterviewBasicDB.selectById(IntervieweeAnswerActivity.this.interviewBasicId);
					if(interviewBasic.getStatus() == InterviewBasic.STATUS_BREAK){
						SysqApplication.showMessage("该访谈已中止，不允许修改问题");
						return;
					}
					
					//关联问题检查
					final String questionCode = (String)view.getTag();
					Question modifyQuestion = QuestionDB.selectByCode(questionCode, SysqContext.getCurrentVersion().getId());
					if(StringUtils.isEmpty(StringUtils.trim(modifyQuestion.getAssociateQuestionCode()))){//无关联问题
						jumpToModifySingleQuestionPage(questionCode);
						
					}else{//有关联问题
						
						//询问是否修改后续关联问题
						AlertDialog.Builder isSingleBuilder = new AlertDialog.Builder(IntervieweeAnswerActivity.this);
						isSingleBuilder.setTitle("确定修改该问题后续关联问题吗？");
						isSingleBuilder.setIcon(android.R.drawable.ic_dialog_info);
						isSingleBuilder.setPositiveButton("修改", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								jumpToModifyAssociatedQuestionPage(questionCode);
							}
						});
						isSingleBuilder.setNegativeButton("不修改", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								jumpToModifySingleQuestionPage(questionCode);
							};
						});
						AlertDialog isSingleDialog = isSingleBuilder.create();
						isSingleDialog.setCancelable(false);
						isSingleDialog.setCanceledOnTouchOutside(false);
						isSingleDialog.show();
					}
				}
			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				};
			});
			AlertDialog dialog = builder.create();
			dialog.setCancelable(false);
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
		}
	}
	
	//跳转修改单个问题页面
	private void jumpToModifySingleQuestionPage(String questionCode){
		Intent intent = new Intent(this,InterviewActivity.class);
		intent.putExtra("operateType", "modifySingleQuestion");
		intent.putExtra("interviewBasicId", IntervieweeAnswerActivity.this.interviewBasicId);
		intent.putExtra("questionaireCode", IntervieweeAnswerActivity.this.questionaireCode);
		intent.putExtra("questionCode", questionCode);
		IntervieweeAnswerActivity.this.startActivity(intent);
	}
	
	//跳转修改关联问题页面
	private void jumpToModifyAssociatedQuestionPage(String questionCode){
		Intent intent = new Intent(IntervieweeAnswerActivity.this,InterviewActivity.class);
		intent.putExtra("operateType", "modifyAssociatedQuestion");
		intent.putExtra("interviewBasicId", IntervieweeAnswerActivity.this.interviewBasicId);
		intent.putExtra("questionaireCode", IntervieweeAnswerActivity.this.questionaireCode);
		intent.putExtra("questionCode", questionCode);
		IntervieweeAnswerActivity.this.startActivity(intent);
	}

	@Override
	public void onBackPressed() {//返回到问卷列表页
		Intent intent = new Intent(this,IntervieweeQuestionaireActivity.class);
		intent.putExtra("interviewBasicId", this.interviewBasicId);
		this.startActivity(intent);
	}
}
