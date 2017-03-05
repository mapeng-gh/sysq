package com.huasheng.sysq.util;

import java.io.File;
import java.io.IOException;

import com.huasheng.sysq.model.InterviewBasic;

import android.media.MediaRecorder;
import android.os.Environment;
import android.os.StatFs;

public class AudioUtils {

	private static MediaRecorder mediaRecorder;
	private static boolean isStarted = false;
	private static final long MIN_STORAGE_CAPABILITY = 200 * 1000 * 1000; // 200M
	
	
	private static long getStorageCapability(){
		File sdDir = Environment.getExternalStorageDirectory();
		StatFs fs = new StatFs(sdDir.getAbsolutePath());
		long available = fs.getAvailableBytes();
		return available;
	}
	
	private static String genFilePath(InterviewBasic interviewBasic){
		
		//文件名规则
		String curTime = DateTimeUtils.getCustomDateTime("yyyyMMddHHmmss");
		String filename = interviewBasic.getIdentityCard() + "_" + interviewBasic.getUsername() + "_" + curTime + ".aac";
		
		//新建文件
		File audioFile = new File(PathConstants.getAudioDir(),filename);
		try{
			audioFile.createNewFile();
		}catch(IOException e){
			throw new RuntimeException("生成录音文件失败", e);
		}
		
		return audioFile.getAbsolutePath();
	}
	
	public static void start(InterviewBasic interviewBasic){
		try{
			
			if(!isStarted){
				
				//手机存储容量不得小于200M
				if(getStorageCapability() < MIN_STORAGE_CAPABILITY){
					SysqApplication.showMessage("录音启动失败，手机存储容量不得小于200M");
					isStarted = false;
					return;
				}
				
				mediaRecorder = new MediaRecorder();
				mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
				mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
				mediaRecorder.setOutputFile(genFilePath(interviewBasic));
				
				mediaRecorder.prepare();
				mediaRecorder.start();
				
				isStarted = true;
				
				SysqApplication.showMessage("录音启动成功");
			}
			
		}catch(Exception e){
			isStarted = false;
			SysqApplication.showMessage("录音启动失败:" + e.toString());
		}
	}
	
	public static void stop(){
		
		try{
			if(isStarted){
				mediaRecorder.stop();  
				mediaRecorder.release(); 
				mediaRecorder = null;
				isStarted = false;
				SysqApplication.showMessage("录音停止成功");
			}
		}catch(Exception e){
			SysqApplication.showMessage("录音停止失败:" + e.toString());
		}
		
	}
}
