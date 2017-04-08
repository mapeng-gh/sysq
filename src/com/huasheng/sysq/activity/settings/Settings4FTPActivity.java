package com.huasheng.sysq.activity.settings;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.huasheng.sysq.R;
import com.huasheng.sysq.util.CommonUtils;
import com.huasheng.sysq.util.PathConstants;
import com.huasheng.sysq.util.SysqApplication;

/**
 * 设置
 * @author cc
 *
 */
public class Settings4FTPActivity extends Activity implements OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.setContentView(R.layout.activity_settings_ftp);
		
		Button saveBtn = (Button)findViewById(R.id.settings_ftp_submit);
		saveBtn.setOnClickListener(this);
		
		//初始化数据
		this.init();
	}
	
	private void init(){
		
		try{
			Map<String,String> ftpConfigMap = CommonUtils.readProperties(new File(PathConstants.getSettingsDir(),"ftp.config"), "UTF-8");
			
			String ip = ftpConfigMap.get("ip");
			EditText ipET = (EditText)this.findViewById(R.id.settings_ftp_ip);
			if(!StringUtils.isEmpty(ip)){
				ipET.setText(ip);
			}
			
			String port = ftpConfigMap.get("port");
			EditText portET = (EditText)this.findViewById(R.id.settings_ftp_port);
			if(!StringUtils.isEmpty(port)){
				portET.setText(port);
			}
			
			String username = ftpConfigMap.get("username");
			EditText usernameET = (EditText)this.findViewById(R.id.settings_ftp_username);
			if(!StringUtils.isEmpty(username)){
				usernameET.setText(username);
			}
			
			String password = ftpConfigMap.get("password");
			EditText passwordET = (EditText)this.findViewById(R.id.settings_ftp_password);
			if(!StringUtils.isEmpty(password)){
				passwordET.setText(password);
			}
			
		}catch(Exception e){
			Toast.makeText(this, "读取配置文件失败", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onClick(View v) {
		
		List<String> lines = new ArrayList<String>();
		
		//服务器地址
		EditText ipET = (EditText)this.findViewById(R.id.settings_ftp_ip);
		String ip = ipET.getText().toString().trim();
		if(StringUtils.isEmpty(ip)){
			SysqApplication.showMessage("服务器地址不能为空");
			return;
		}
		lines.add("ip="+ip);
		
		//端口
		EditText portET = (EditText)this.findViewById(R.id.settings_ftp_port);
		String port = portET.getText().toString().trim();
		if(StringUtils.isEmpty(port)){
			SysqApplication.showMessage("端口不能为空");
			return;
		}
		lines.add("port="+port);
		
		//用户名密码
		EditText usernameET = (EditText)this.findViewById(R.id.settings_ftp_username);
		String username = usernameET.getText().toString().trim();
		EditText passwordET = (EditText)this.findViewById(R.id.settings_ftp_password);
		String password = passwordET.getText().toString().trim();
		if(!StringUtils.isEmpty(password) && StringUtils.isEmpty(username)){
			SysqApplication.showMessage("用户名不能为空");
			return;
		}
		if(!StringUtils.isEmpty(username)){
			lines.add("username="+username);
		}
		if(!StringUtils.isEmpty(password)){
			lines.add("password="+password);
		}
		
		//保存
		try{
			FileUtils.writeLines(new File(PathConstants.getSettingsDir(),"ftp.config"), "UTF-8",lines,IOUtils.LINE_SEPARATOR_UNIX,false);
			SysqApplication.showMessage("保存成功");
		}catch(Exception e){
			SysqApplication.showMessage("保存失败："+e.getMessage());
		}
	}
}
