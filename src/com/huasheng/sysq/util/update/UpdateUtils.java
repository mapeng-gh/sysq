package com.huasheng.sysq.util.update;

import java.io.File;
import java.util.Date;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.huasheng.sysq.activity.IndexActivity;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.util.CommonUtils;
import com.huasheng.sysq.util.NetworkUtils;
import com.huasheng.sysq.util.PackageUtils;
import com.huasheng.sysq.util.PathConstants;
import com.huasheng.sysq.util.SqliteUtils;
import com.huasheng.sysq.util.ZipUtil;
import com.huasheng.sysq.util.db.SysQOpenHelper;

public class UpdateUtils {
	
	/**
	 * 发送消息
	 * @param handler
	 * @param what
	 * @param obj
	 */
	private static void sendMessage(Handler handler,int what,Object obj){
		Message message = new Message();
		message.what = what;
		message.obj = obj;
		handler.sendMessage(message);
	}
	
	/**
	 * 检查更新
	 */
	public static void update(final Context context,Handler handler){
		
		//检查网络
		sendMessage(handler,UpdateConstants.MSG_UPDATE_SHOW_INFOMATION,"网络检查：正在进行中...");
		if(!NetworkUtils.isNetworkEnable(context)){
			sendMessage(handler,UpdateConstants.MSG_UPDATE_CLOSE,"网络检查：网络连接失败，请您检查您的网络是否可用");
			return;
		}else{
			sendMessage(handler,UpdateConstants.MSG_UPDATE_SHOW_INFOMATION,"网络检查：网络连接正常");
		}
		
		//检查新版本
		sendMessage(handler,UpdateConstants.MSG_UPDATE_SHOW_INFOMATION,"版本检查：正在进行中...");
		FileUtils.deleteQuietly(new File(PathConstants.getTmpDir()));
		File temConfigFile = new File(PathConstants.getTmpDir(),"version.properties"); 
		try{
			NetworkUtils.download(UpdateConstants.CONFIG_ADDRESS+"?v="+new Date().getTime(),temConfigFile);
		}catch(Exception e){
			sendMessage(handler,UpdateConstants.MSG_UPDATE_CLOSE,"版本检查：下载配置文件失败");
			return;
		}
		Map<String,String> versionMap = CommonUtils.readProperties(temConfigFile,"UTF-8");
		if(versionMap == null){
			sendMessage(handler,UpdateConstants.MSG_UPDATE_CLOSE,"版本检查：解析配置文件失败");
			return;
		}
		String interviewVersionCodeStr = versionMap.get("interview_version_code");
		String appVersionCodeStr = versionMap.get("app_version_code");
		String interviewDownloadUrl = versionMap.get("interview_download_url");
		String appDownloadUrl = versionMap.get("app_download_url");
		if(StringUtils.isEmpty(appVersionCodeStr) || !StringUtils.isNumeric(appVersionCodeStr) 
				|| StringUtils.isEmpty(interviewVersionCodeStr) || !StringUtils.isNumeric(interviewVersionCodeStr)
				|| StringUtils.isEmpty(appDownloadUrl) || StringUtils.isEmpty(interviewDownloadUrl)){
			sendMessage(handler,UpdateConstants.MSG_UPDATE_CLOSE,"版本检查：配置文件格式不正确");
			return;
		}
		int appVersionCodeInt = Integer.parseInt(appVersionCodeStr);
		int interviewVersionCodeInt = Integer.parseInt(interviewVersionCodeStr);
		int curAppVersionCode = PackageUtils.getVersionCode(context);
		int curInterviewVersionCode = InterviewService.getCurInterviewVersion().getId();
		if(appVersionCodeInt == curAppVersionCode && interviewVersionCodeInt == curInterviewVersionCode){
			sendMessage(handler,UpdateConstants.MSG_UPDATE_CLOSE,"版本检查：app和问卷都为最新版本");
			return;
		}
		if(interviewVersionCodeInt > curInterviewVersionCode){
			sendMessage(handler,UpdateConstants.MSG_UPDATE_SHOW_INFOMATION,"版本检查：有新的问卷版本");
		}
		if(appVersionCodeInt > curAppVersionCode){
			sendMessage(handler,UpdateConstants.MSG_UPDATE_SHOW_INFOMATION,"版本检查：有新的app版本");
		}
		
		//更新问卷
		if(interviewVersionCodeInt > curInterviewVersionCode){
			sendMessage(handler,UpdateConstants.MSG_UPDATE_SHOW_INFOMATION,"更新问卷：正在进行中...");
			File zipFileTmp = new File(PathConstants.getTmpDir(),"interview.zip");
			try{
				NetworkUtils.download(interviewDownloadUrl+"?v="+new Date().getTime(),zipFileTmp);
			}catch(Exception e){
				sendMessage(handler,UpdateConstants.MSG_UPDATE_CLOSE,"更新问卷：下载问卷包失败");
				return;
			}
			File unzipInterviewDir = new File(PathConstants.getTmpDir(),"unzip");
			try{
				ZipUtil.unzip(zipFileTmp.getAbsolutePath(),unzipInterviewDir.getAbsolutePath());
			}catch(Exception e){
				sendMessage(handler,UpdateConstants.MSG_UPDATE_CLOSE,"更新问卷：解压问卷包失败");
				return;
			}
			File[] sqlFiles = unzipInterviewDir.listFiles();
			if(sqlFiles != null && sqlFiles.length > 0){
				for(File file : sqlFiles){
					try{
						SqliteUtils.execSQL(SysQOpenHelper.getDatabase(), file);
					}catch(Exception e){
						sendMessage(handler,UpdateConstants.MSG_UPDATE_CLOSE,"更新问卷：插入问卷数据失败");
						return;
					}
					
				}
				InterviewService.updateCurInterviewVersion(interviewVersionCodeInt);
				if(appVersionCodeInt <= curAppVersionCode){
					sendMessage(handler,UpdateConstants.MSG_UPDATE_CLOSE,"更新问卷：更新成功，3s后即将重启app");
					try{
						Thread.sleep(3000);
						PackageUtils.restartApp(context);
					}catch(Exception e){
					}
					return;
				}else{
					sendMessage(handler,UpdateConstants.MSG_UPDATE_SHOW_INFOMATION,"更新问卷：更新成功");
				}
				
			}else{
				sendMessage(handler,UpdateConstants.MSG_UPDATE_CLOSE,"更新问卷：问卷解压包为空");
				return;
			}
		}
		
		//更新app
		if(appVersionCodeInt > curAppVersionCode){
			sendMessage(handler,UpdateConstants.MSG_UPDATE_SHOW_INFOMATION,"更新app：正在进行中...");
			File appDownloadFile = new File(PathConstants.getTmpDir(),"sysq.apk");
			try{
				NetworkUtils.download(appDownloadUrl+"?v="+new Date().getTime(),appDownloadFile);
			}catch(Exception e){
				sendMessage(handler,UpdateConstants.MSG_UPDATE_CLOSE,"更新app：下载app失败");
				return;
			}
			sendMessage(handler,UpdateConstants.MSG_UPDATE_CLOSE,"更新app：下载app成功");
			PackageUtils.installApk(context,appDownloadFile);
		}
	}
}
