package com.huasheng.sysq.util;

import java.io.File;
import java.io.IOException;

import android.media.MediaRecorder;
import android.os.Environment;
import android.os.StatFs;

public class AudioUtils {

	private static MediaRecorder mediaRecorder;
	private static String audioFilePath;	//����¼���ļ�
	private static boolean isStarted = false;
	private static final long MIN_STORAGE_CAPABILITY = 200 * 1000 * 1000; // 200M
	private static final String AUDIO_STORAGE_DIR = "sysq" + File.separator + "audio";//¼���ļ��洢Ŀ¼
	
	
	private static long getStorageCapability(){
		File sdDir = Environment.getExternalStorageDirectory();
		StatFs fs = new StatFs(sdDir.getAbsolutePath());
		long available = fs.getAvailableBytes();
		return available;
	}
	
	private static String genFilePath(String username){
		
		//���¼��Ŀ¼�Ƿ����
		File audioDir = new File(Environment.getExternalStorageDirectory(),AUDIO_STORAGE_DIR);
		if(!audioDir.exists()){
			audioDir.mkdirs();
		}
		
		//���ݹ��������ļ���
		String curTime = DateTimeUtils.getCurTime();
		String filename = username + "_" + curTime + "_endTime" + ".aac";
		
		//����ļ��Ƿ��Ѵ���
		File audioFile = new File(audioDir,filename);
		if(audioFile.exists()){
			audioFile.delete();
		}
		try{
			audioFile.createNewFile();
		}catch(IOException e){
			throw new RuntimeException("����¼���ļ�ʧ��", e);
		}
		
		audioFilePath = audioFile.getAbsolutePath();
		return audioFilePath;
	}
	
	public static void start(String username){
		try{
			
			if(!isStarted){
				
				//�ֻ��洢��������С��200M
				if(getStorageCapability() < MIN_STORAGE_CAPABILITY){
					SysqApplication.showMessage("¼������ʧ�ܣ��ֻ��洢��������С��200M");
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
				
				SysqApplication.showMessage("¼�������ɹ�");
				
			}
			
		}catch(Exception e){
			isStarted = false;
			SysqApplication.showMessage("¼������ʧ��:" + e.toString());
		}
	}
	
	public static void stop(){
		
		try{
			if(isStarted){
				
				mediaRecorder.stop();  
				mediaRecorder.release(); 
				mediaRecorder = null;
				
				isStarted = false;
				
				//�ļ�����������ӽ���ʱ�䣩
				File auditFile = new File(audioFilePath);
				audioFilePath = audioFilePath.replace("endTime",DateTimeUtils.getCurTime());
				auditFile.renameTo(new File(audioFilePath));
				
				SysqApplication.showMessage("¼��ֹͣ�ɹ�");
			}
			
			
		}catch(Exception e){
			SysqApplication.showMessage("¼��ֹͣʧ��:" + e.toString());
		}
		
	}
}
