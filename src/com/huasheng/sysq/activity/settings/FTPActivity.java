package com.huasheng.sysq.activity.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.huasheng.sysq.R;
import com.huasheng.sysq.util.PathConstants;
import com.huasheng.sysq.util.SysqApplication;

/**
 * 设置
 * @author cc
 *
 */
public class FTPActivity extends Activity implements OnClickListener{
	
	private TextView ipTV;
	private TextView portTV;
	private TextView usernameTV;
	private TextView passwordTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		super.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.setContentView(R.layout.activity_settings_ftp);
		
		ipTV = (TextView)findViewById(R.id.et_ftp_ip);
		portTV = (TextView)findViewById(R.id.et_ftp_port);
		usernameTV = (TextView)findViewById(R.id.et_ftp_username);
		passwordTV = (TextView)findViewById(R.id.et_ftp_password);
		
		Button saveBtn = (Button)findViewById(R.id.btn_ft_submit);
		saveBtn.setOnClickListener(this);
		
		//初始化数据
		this.loadFtpConfig();
	}
	
	private void loadFtpConfig(){
		
		Properties ftpProps = new Properties();
		
		//open inputstream
		InputStream is = null;
		try{
			is = new FileInputStream(new File(PathConstants.getFTPConfigPath()));
		}catch(IOException openIOE){
			SysqApplication.showMessage("初始化配置信息失败：" + openIOE.getMessage());
			return;
		}
		
		//read by utf-8
		Reader reader = null;
		try{
			reader = new InputStreamReader(is,"utf-8");
		}catch(UnsupportedEncodingException uee){
			if(is != null){
				try{
					is.close();
				}catch(IOException closeIOE){
					//ignore
				}
			}
			SysqApplication.showMessage("初始化配置信息失败：" + uee.getMessage());
			return;
		}
		
		//load config
		try{
			ftpProps.load(reader);
		}catch(IOException loadIOE){
			if(reader != null){
				try{
					reader.close();
				}catch(IOException closeIOE){
					//ignore
				}
			}
			SysqApplication.showMessage("初始化配置信息失败：" + loadIOE.getMessage());
			return;
		}
		
		//view
		String ip = ftpProps.getProperty("ip");
		if(!StringUtils.isEmpty(ip)){
			ipTV.setText(ip);
		}
		String port = ftpProps.getProperty("port");
		if(!StringUtils.isEmpty(port)){
			portTV.setText(port);
		}
		String username = ftpProps.getProperty("username");
		if(!StringUtils.isEmpty(username)){
			usernameTV.setText(username);
		}
		String password  = ftpProps.getProperty("password");
		if(!StringUtils.isEmpty(password)){
			passwordTV.setText(password);
		}
		
		//close
		if(reader != null){
			try{
				reader.close();
			}catch(IOException closeIOE){
				//ignore
			}
		}
	}

	@Override
	public void onClick(View v) {
		
		//数据校验
		if(StringUtils.isEmpty(ipTV.getText())){
			SysqApplication.showMessage("服务器IP不能为空");
			return;
		}
		if(StringUtils.isEmpty(portTV.getText())){
			SysqApplication.showMessage("端口不能为空");
			return;
		}
		if(!NumberUtils.isDigits(portTV.getText().toString())){
			SysqApplication.showMessage("端口只能为数字");
			return;
		}
		if(StringUtils.isEmpty(usernameTV.getText())){
			SysqApplication.showMessage("用户名不能为空");
			return;
		}
		if(StringUtils.isEmpty(passwordTV.getText())){
			SysqApplication.showMessage("密码不能为空");
			return;
		}
		
		//保存配置
		Properties ftpProps = new Properties();
		ftpProps.setProperty("ip", ipTV.getText().toString());
		ftpProps.setProperty("port", portTV.getText().toString());
		ftpProps.setProperty("username", usernameTV.getText().toString());
		ftpProps.setProperty("password", passwordTV.getText().toString());
		
		Writer writer = null;
		try{
			FileOutputStream fos = new FileOutputStream(PathConstants.getFTPConfigPath());
			writer = new OutputStreamWriter(fos, "utf-8");
			ftpProps.store(writer, null);
			SysqApplication.showMessage("保存成功");
		}catch(Exception e){
			SysqApplication.showMessage("保存失败："+e.getMessage());
		}finally{
			if(writer != null){
				try{
					writer.close();
				}catch(IOException closeIOE){
					//ignore
				}
			}
		}
	}
}
