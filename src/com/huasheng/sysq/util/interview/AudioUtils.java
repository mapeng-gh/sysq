package com.huasheng.sysq.util.interview;

import java.io.File;
import java.io.IOException;

import com.huasheng.sysq.model.InterviewBasicWrap;
import com.huasheng.sysq.model.Interviewee;
import com.huasheng.sysq.util.CommonUtils;
import com.huasheng.sysq.util.PathConstants;
import com.huasheng.sysq.util.DeviceStorageUtils;
import com.huasheng.sysq.util.SysqApplication;

import android.media.MediaRecorder;

public class AudioUtils {

	private static MediaRecorder mediaRecorder;
	private static boolean isStarted = false;
	private static final long MIN_STORAGE_CAPABILITY = 500 * 1000 * 1000; // 500M
	
	private static String genFilePath(InterviewBasicWrap interviewBasicWrap){
		
		Interviewee interviewee = interviewBasicWrap.getInterviewee();
		
		File audioDir = new File(PathConstants.getMediaDir(),interviewee.getId()+File.separator+"audio");
		if(!audioDir.exists()){
			audioDir.mkdirs();
		}
		
		File audioFile = new File(audioDir,CommonUtils.getCustomDateTime("yyyyMMddHHmmss") + ".aac");
		try{
			if(audioFile.exists()){
				audioFile.delete();
			}
			audioFile.createNewFile();
		}catch(IOException e){
		}
		return audioFile.getAbsolutePath();
	}
	
	public static void start(InterviewBasicWrap interviewBasicWrap){
		try{
			
			if(!isStarted){
				
				//手机存储容量不得小于200M
				if(DeviceStorageUtils.getStorageAvailableBytes(DeviceStorageUtils.getPrimaryStoragePath()) < MIN_STORAGE_CAPABILITY){
					SysqApplication.showMessage("录音启动失败，手机存储容量不得小于500M");
					isStarted = false;
					return;
				}
				
				mediaRecorder = new MediaRecorder();
				mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
				mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
				mediaRecorder.setOutputFile(genFilePath(interviewBasicWrap));
				
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
