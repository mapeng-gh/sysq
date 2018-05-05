package com.huasheng.sysq.activity.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.huasheng.sysq.R;
import com.huasheng.sysq.model.Interviewer;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.service.UserCenterService;
import com.huasheng.sysq.util.DeviceStorageUtils;
import com.huasheng.sysq.util.DialogUtils;
import com.huasheng.sysq.util.PathConstants;
import com.huasheng.sysq.util.SysqConstants;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.provider.DocumentFile;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;

public class SettingsNavActivity extends Activity implements OnClickListener{
	
	private LinearLayout ftpLL;
	private LinearLayout dbLL;
	private LinearLayout addUserLL;
	private LinearLayout resetPwdLL;
	private LinearLayout repaireLL;
	private LinearLayout adminRepaireLL;
	private LinearLayout backupLL;
	
	private static int REQUEST_CODE_OPEN_TREE = 42;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_settings_navy);
		
		ftpLL = (LinearLayout)findViewById(R.id.settings_nav_ftp);
		ftpLL.setOnClickListener(this);
		dbLL = (LinearLayout)findViewById(R.id.settings_nav_db);
		dbLL.setOnClickListener(this);
		addUserLL = (LinearLayout)findViewById(R.id.settings_navy_adduserLL);
		addUserLL.setOnClickListener(this);
		resetPwdLL = (LinearLayout)findViewById(R.id.settings_navy_resetpwdLL);
		resetPwdLL.setOnClickListener(this);
		repaireLL = (LinearLayout)findViewById(R.id.settings_navy_repaireLL);
		repaireLL.setOnClickListener(this);
		adminRepaireLL = (LinearLayout)findViewById(R.id.settings_navy_admin_repaireLL);
		adminRepaireLL.setOnClickListener(this);
		backupLL = (LinearLayout)findViewById(R.id.settings_navy_backupLL);
		backupLL.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.settings_nav_ftp){//FTP设置
			Intent intent = new Intent(this,Settings4FTPActivity.class);
			this.startActivity(intent);
			
		}else if(v.getId() == R.id.settings_nav_db){//数据库设置
			Intent intent = new Intent(this,Settings4DBActivity.class);
			this.startActivity(intent);
			
		}else if(v.getId() == R.id.settings_navy_adduserLL){//注册用户
			Intent intent = new Intent(this,Settings4AddUserActivity.class);
			this.startActivity(intent);
			
		}else if(v.getId() == R.id.settings_navy_resetpwdLL){//重置密码
			Intent intent = new Intent(this,Settings4ResetPwdActivity.class);
			this.startActivity(intent);
			
		}else if(v.getId() == R.id.settings_navy_repaireLL){//访谈修复
			Intent intent = new Intent(this,Settings4RepaireIntervieweeListActivity.class);
			this.startActivity(intent);
			
		}else if(v.getId() == R.id.settings_navy_admin_repaireLL){//帐号修复
			
			Interviewer adminInterviewer = UserCenterService.getUser(SysqConstants.ADMIN_LOGIN_NAME);
			if(adminInterviewer == null){
				InterviewService.repaireAdminAccountData();
				DialogUtils.showLongToast(this, "帐号修复成功");
			}else{
				DialogUtils.showLongToast(this, "帐号正常，无需修复");
			}
			
		}else if(v.getId() == R.id.settings_navy_backupLL) {//多媒体数据备份
			this.backup();
		}
	}
	
	/**
	 * 多媒体数据备份
	 */
	private void backup() {
		
		//文件检查
		File backupDir = new File(PathConstants.getBackupDir());
		if(!backupDir.exists() || backupDir.listFiles() == null || backupDir.listFiles().length == 0) {
			DialogUtils.showLongToast(this, "没有可备份的数据");
			return;
		}
		
		//检查是否插入sd卡
		String sdPath = DeviceStorageUtils.getExtStoragePath(this);
		if(StringUtils.isEmpty(sdPath) || !DeviceStorageUtils.checkExtStorageAvailable(this, sdPath)) {
			DialogUtils.showLongToast(this, "请先插入外部SD卡");
			return;
		}
		
		//sd卡容量检查
		long sdAvailableBytes = DeviceStorageUtils.getStorageAvailableBytes(sdPath);
		long backupSize = FileUtils.sizeOfDirectory(new File(PathConstants.getBackupDir()));
		if(sdAvailableBytes < backupSize + 10 * 1024 * 1024) {//预留10M
			DialogUtils.showLongToast(this, "外部SD卡存储容量不足");
			return;
		}
		
		//数据迁移
		Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE); 
		super.startActivityForResult(intent, REQUEST_CODE_OPEN_TREE); 
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK ) {
			if(requestCode == REQUEST_CODE_OPEN_TREE) {
				Uri selectedUri = data.getData(); 
				Log.d("backup", selectedUri.toString());
				
				//限制存储位置
				if(selectedUri.toString().contains("primary")) {
					DialogUtils.showLongToast(this, "请选择外置SD卡");
					return;
				}
				if(!selectedUri.toString().endsWith("%3A")) {
					DialogUtils.showLongToast(this, "请选择外置SD卡根目录");
					
					return;
				}
				
				DocumentFile selectedDir = DocumentFile.fromTreeUri(this, selectedUri); 
				
				//创建目录
				DocumentFile sysqDir = selectedDir.findFile("sysq");
				if(sysqDir == null || !sysqDir.exists()) {
					sysqDir = selectedDir.createDirectory("sysq");
				}
				
				DialogUtils.showLongToast(this, "数据备份已开始，请稍后...");
				
				//备份数据
				File backupDir = new File(PathConstants.getBackupDir());
				for(File backupFile : backupDir.listFiles()) {
					this.backupFile(sysqDir, backupFile);
					
					//删除文件
					FileUtils.deleteQuietly(backupFile);
				}
				
				DialogUtils.showLongToast(this, "数据备份成功");
			}
		}
	}
	
	//文件拷贝
	private void backupFile(DocumentFile destDir,File srcFile) {
		DocumentFile destFile = destDir.createFile(null, srcFile.getName());
		
		OutputStream output = null;
		InputStream input = null;
		try {
			input = new FileInputStream(srcFile);
			output = this.getContentResolver().openOutputStream(destFile.getUri());
			IOUtils.copy(input, output);
		} catch (Exception e) {
		}finally {
			try {
				if(input != null) {
					input.close();
				}
				if(output != null) {
					output.close();
				}
			}catch(Exception e) {
			}
		}
	}
}
