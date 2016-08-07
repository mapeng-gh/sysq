package com.huasheng.sysq.util;

import java.io.File;
import java.io.IOException;

import android.media.MediaRecorder;
import android.os.Environment;
import android.os.StatFs;

public class AudioUtils {

	private static MediaRecorder mediaRecorder;
	private static String audioFilePath;	//正在录音文件
	private static boolean isStarted = false;
	private static final long MIN_STORAGE_CAPABILITY = 200 * 1000 * 1000; // 200M
	private static final String AUDIO_STORAGE_DIR = "sysq" + File.separator + "audio";//录音文件存储目录
	
	
	private static long getStorageCapability(){
		File sdDir = Environment.getExternalStorageDirectory();
		StatFs fs = new StatFs(sdDir.getAbsolutePath());
		long available = fs.getAvailableBytes();
		return available;
	}
	
	private static String genFilePath(String username){
		
		//检测录音目录是否存在
		File audioDir = new File(Environment.getExternalStorageDirectory(),AUDIO_STORAGE_DIR);
		if(!audioDir.exists()){
			audioDir.mkdirs();
		}
		
		//根据规则生成文件名
		String curTime = DateTimeUtils.getCurDateTime();
		String filename = username + "_" + curTime + "_endTime" + ".aac";
		
		//检测文件是否已存在
		File audioFile = new File(audioDir,filename);
		if(audioFile.exists()){
			audioFile.delete();
		}
		try{
			audioFile.createNewFile();
		}catch(IOException e){
			throw new RuntimeException("生成录音文件失败", e);
		}
		
		audioFilePath = audioFile.getAbsolutePath();
		return audioFilePath;
	}
	
	public static void start(String username){
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
				mediaRecorder.setOutputFile(genFilePath(username));
				
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
				
				//文件重命名（添加结束时间）
				File auditFile = new File(audioFilePath);
				audioFilePath = audioFilePath.replace("endTime",DateTimeUtils.getCurDateTime());
				auditFile.renameTo(new File(audioFilePath));
				
				SysqApplication.showMessage("录音停止成功");
			}
			
			
		}catch(Exception e){
			SysqApplication.showMessage("录音停止失败:" + e.toString());
		}
		
	}
}
