package com.huasheng.sysq.activity.usercenter;

import com.huasheng.sysq.R;
import com.huasheng.sysq.util.PackageUtils;
import com.huasheng.sysq.util.SysqContext;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class Usercenter4AboutActivity extends Activity{
	
	private TextView appVersionTV;
	private TextView questionaireVersionTV;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.setContentView(R.layout.activity_usercenter_about);
		
		this.appVersionTV = (TextView)super.findViewById(R.id.usercenter_about_app_version);
		this.questionaireVersionTV = (TextView)super.findViewById(R.id.usercenter_about_questionaire_version);
		
		this.init();
	}
	
	private void init() {
		appVersionTV.setText("app版本：v" + PackageUtils.getVersionName(this));
		questionaireVersionTV.setText("问卷版本：v" + SysqContext.getCurrentVersion().getId()+"");
	}

}
