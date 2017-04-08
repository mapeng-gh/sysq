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
public class Settings4DBActivity extends Activity implements OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.setContentView(R.layout.activity_settings_db);
		
		Button saveBtn = (Button)findViewById(R.id.settings_db_submit);
		saveBtn.setOnClickListener(this);
		
		//初始化数据
		this.init();
	}
	
	private void init(){
		
		try{
			Map<String,String> dbConfigMap = CommonUtils.readProperties(new File(PathConstants.getSettingsDir(),"db.config"), "UTF-8");
			
			//服务器地址
			String ip = dbConfigMap.get("ip");
			EditText ipET = (EditText)this.findViewById(R.id.settings_db_ip);
			if(!StringUtils.isEmpty(ip)){
				ipET.setText(ip);
			}
			
			//端口
			String port = dbConfigMap.get("port");
			EditText portET = (EditText)this.findViewById(R.id.settings_db_port);
			if(!StringUtils.isEmpty(port)){
				portET.setText(port);
			}
			
			//数据库
			String db = dbConfigMap.get("db");
			EditText dbET = (EditText)this.findViewById(R.id.settings_db_db);
			if(!StringUtils.isEmpty(db)){
				dbET.setText(db);
			}
			
			//用户名
			String username = dbConfigMap.get("username");
			EditText usernameET = (EditText)this.findViewById(R.id.settings_db_username);
			if(!StringUtils.isEmpty(username)){
				usernameET.setText(username);
			}
			
			//密码
			String password = dbConfigMap.get("password");
			EditText passwordET = (EditText)this.findViewById(R.id.settings_db_password);
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
		EditText ipET = (EditText)this.findViewById(R.id.settings_db_ip);
		String ip = ipET.getText().toString().trim();
		if(StringUtils.isEmpty(ip)){
			SysqApplication.showMessage("服务器地址不能为空");
			return;
		}
		lines.add("ip="+ip);
		
		//端口
		EditText portET = (EditText)this.findViewById(R.id.settings_db_port);
		String port = portET.getText().toString().trim();
		if(StringUtils.isEmpty(port)){
			SysqApplication.showMessage("端口不能为空");
			return;
		}
		lines.add("port="+port);
		
		//数据库
		EditText dbET = (EditText)this.findViewById(R.id.settings_db_db);
		String db = dbET.getText().toString().trim();
		if(StringUtils.isEmpty(db)){
			SysqApplication.showMessage("数据库不能为空");
			return;
		}
		lines.add("db="+db);
		
		//用户名
		EditText usernameET = (EditText)this.findViewById(R.id.settings_db_username);
		String username = usernameET.getText().toString().trim();
		if(StringUtils.isEmpty(username)){
			SysqApplication.showMessage("用户名不能为空");
			return;
		}
		lines.add("username="+username);
		
		//密码
		EditText passwordET = (EditText)this.findViewById(R.id.settings_db_password);
		String password = passwordET.getText().toString().trim();
		if(!StringUtils.isEmpty(password)){
			lines.add("password="+password);
		}
		
		//保存
		try{
			FileUtils.writeLines(new File(PathConstants.getSettingsDir(),"db.config"), "UTF-8",lines,IOUtils.LINE_SEPARATOR_UNIX,false);
			SysqApplication.showMessage("保存成功");
		}catch(Exception e){
			SysqApplication.showMessage("保存失败："+e.getMessage());
		}
	}
}
