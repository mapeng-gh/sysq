package com.huasheng.sysq.util.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;

import com.huasheng.sysq.model.InterviewAnswer;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.InterviewBasicWrap;
import com.huasheng.sysq.model.InterviewQuestion;
import com.huasheng.sysq.model.InterviewQuestionaireWrap;
import com.huasheng.sysq.model.Interviewee;
import com.huasheng.sysq.model.Interviewer;
import com.huasheng.sysq.service.InterviewService;
import com.huasheng.sysq.service.UserCenterService;
import com.huasheng.sysq.util.CommonUtils;
import com.huasheng.sysq.util.PathConstants;
import com.huasheng.sysq.util.SysqApplication;
import com.huasheng.sysq.util.SysqContext;

public class UploadUtils {
	
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
	 * 检查网络
	 * @return
	 */
	private static boolean checkNetwork(){
		Context context = SysqApplication.getContext();
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni == null ? false : true;
	}
	
	/**
	 * 检查FTP服务
	 * @return
	 */
	private static FTPClient checkFTP(){
		
		//读取配置
		Map<String,String> configMap  = CommonUtils.readProperties(new File(PathConstants.getSettingsDir(),"ftp.config"),"UTF-8");
		if(configMap == null)	return null;
		String ip,port,username,password;
		ip = configMap.get("ip");
		port = configMap.get("port");
		username = configMap.get("username");
		password = configMap.get("password");
		if(StringUtils.isEmpty(ip) || StringUtils.isEmpty(port) || 
				(StringUtils.isEmpty(username) && !StringUtils.isEmpty(password))
				|| (!StringUtils.isEmpty(username) && StringUtils.isEmpty(password))){
			return null;
		}
		
		//获取连接
		FTPClient ftpClient = new FTPClient();
		try{
			ftpClient.connect(ip, Integer.parseInt(port));
			if(StringUtils.isEmpty(username)){
				username = "anonymous";
			}
			if(!ftpClient.login(username,password)){
				return null;
			}
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			if(FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))){
				ftpClient.setControlEncoding("UTF-8");
			}
			return ftpClient;
		}catch(Exception e){
			if(ftpClient != null){
				try{
					ftpClient.logout();
					ftpClient.disconnect();
				}catch(Exception closeE){
				}
			}
			return null;
		}
	}
	
	/**
	 * 检查数据库
	 */
	private static Connection checkDB(){
		
		//读取配置
		Map<String,String> configMap  = CommonUtils.readProperties(new File(PathConstants.getSettingsDir(),"db.config"),"UTF-8");
		if(configMap == null)	return null;
		String ip,port,db,username,password;
		ip = configMap.get("ip");
		port = configMap.get("port");
		db = configMap.get("db");
		username = configMap.get("username");
		password = configMap.get("password");
		if(StringUtils.isEmpty(ip) || StringUtils.isEmpty(port) || StringUtils.isEmpty(db) || StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
			return null;
		}
		
		//获取连接
		Connection conn = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://"+ip+":"+port+"/"+db, username, password);
			return conn;
		}catch(Exception e){
			try{
				if(conn != null){
					conn.close();
				}
			}catch(Exception sqle){
			}
			return null;
		}
	}
	
	/**
	 * 统计访谈数据
	 * @return
	 */
	private static Map<String,Integer> reportInterview(){
		
		int interviewCount = 0;
		int interviewerCount = 0;
		int intervieweeCount = 0;
		
		List<InterviewBasicWrap> interviewBasicWrapList = InterviewService.getAllInterviewBasic();
		if(interviewBasicWrapList != null && interviewBasicWrapList.size() > 0){
			for(InterviewBasicWrap interviewBasicWrap : interviewBasicWrapList){
				if(interviewBasicWrap.getInterviewBasic().getIsTest() == InterviewBasic.TEST_NO && interviewBasicWrap.getInterviewBasic().getStatus() == InterviewBasic.STATUS_DONE){
					if(interviewBasicWrap.getInterviewBasic().getUploadStatus() == UploadConstants.upload_status_not_upload 
							|| interviewBasicWrap.getInterviewBasic().getUploadStatus() == UploadConstants.upload_status_modified){//访问表
						interviewCount++;
					}
					if(interviewBasicWrap.getInterviewer().getUploadStatus() == UploadConstants.upload_status_not_upload
							|| interviewBasicWrap.getInterviewer().getUploadStatus() == UploadConstants.upload_status_modified){//访问者表
						interviewerCount++;
					}
					if(interviewBasicWrap.getInterviewee().getUploadStatus() == UploadConstants.upload_status_not_upload
							|| interviewBasicWrap.getInterviewee().getUploadStatus() == UploadConstants.upload_status_modified){//被访问者表
						intervieweeCount++;
					}
				}
			}
		}
		
		Map<String,Integer> reportMap = new HashMap<String,Integer>();
		reportMap.put("interviewCount", interviewCount);
		reportMap.put("interviewerCount", interviewerCount);
		reportMap.put("intervieweeCount", intervieweeCount);
		return reportMap;
	}
	
	/**
	 * 统计多媒体数据
	 * @return
	 */
	private static int reportMedia(){
		
		int mediaCount = 0 ;
		
		List<InterviewBasicWrap> interviewBasicWrapList = InterviewService.getAllInterviewBasic();
		if(interviewBasicWrapList != null && interviewBasicWrapList.size() > 0){
			for(InterviewBasicWrap interviewBasicWrap : interviewBasicWrapList){
				if(interviewBasicWrap.getInterviewBasic().getIsTest() == InterviewBasic.TEST_NO && interviewBasicWrap.getInterviewBasic().getStatus() == InterviewBasic.STATUS_DONE){
					File mediaDir = new File(PathConstants.getMediaDir(),interviewBasicWrap.getInterviewBasic().getId()+"");
					if(mediaDir.exists()){
						File audioDir = new File(mediaDir,"audio");
						File photoDir = new File(mediaDir,"photo");
						if( (audioDir.exists() && audioDir.listFiles().length > 0) || (photoDir.exists() && photoDir.listFiles().length > 0) ){
							mediaCount++;
						}
					}
				}
			}
		}
		
		return mediaCount;
	}
	
	/**
	 * 处理访问表
	 * @param pst
	 */
	private static void handleInterviewBasic(Connection conn,InterviewBasicWrap interviewBasicWrap){
		
		PreparedStatement insertInterviewPst = null;
		PreparedStatement insertResultPst = null;
		PreparedStatement delInterviewPst = null;
		PreparedStatement delResultPst = null;
		
		try{
			
			InterviewBasic interviewBasic = interviewBasicWrap.getInterviewBasic();
			if(interviewBasic.getUploadStatus() == UploadConstants.upload_status_not_upload){//添加
				
				//添加访谈记录
				insertInterviewPst = conn.prepareStatement("insert into sysq_interview(id,doctor_id,patient_id,start_time,end_time,type,version_id) values(?,?,?,?,?,?,?);");
				insertInterviewPst.setInt(1,interviewBasic.getId());
				insertInterviewPst.setInt(2, interviewBasic.getInterviewerId());
				insertInterviewPst.setInt(3, interviewBasic.getIntervieweeId());
				insertInterviewPst.setString(4, interviewBasic.getStartTime());
				insertInterviewPst.setString(5, interviewBasic.getLastModifiedTime());
				insertInterviewPst.setInt(6, interviewBasic.getType());
				insertInterviewPst.setInt(7, interviewBasic.getVersionId());
				insertInterviewPst.execute();
				
				//添加答案
				insertResultPst = conn.prepareStatement("insert into sysq_result(questionaire_code,question_code,answer_code,answer_value,interview_id) values(?,?,?,?,?);");
				List<InterviewQuestionaireWrap> interviewQuestionaireWrapList = InterviewService.getInterviewQuestionaireList(interviewBasic.getId());
				if(interviewQuestionaireWrapList != null && interviewQuestionaireWrapList.size() > 0){
					for(InterviewQuestionaireWrap interviewQuestionaireWrap : interviewQuestionaireWrapList){
						List<InterviewQuestion> interviewQuestionList = InterviewService.getInterviewQuestionList(interviewBasic.getId(), interviewQuestionaireWrap.getInterviewQuestionaire().getQuestionaireCode());
						if(interviewQuestionList != null && interviewQuestionList.size() > 0){
							for(InterviewQuestion interviewQuestion : interviewQuestionList){
								List<InterviewAnswer> interviewAnswerList = InterviewService.getInterviewAnswerList(interviewBasic.getId(), interviewQuestion.getQuestionCode());
								if(interviewAnswerList != null && interviewAnswerList.size() > 0){
									for(InterviewAnswer interviewAnswer : interviewAnswerList){
										insertResultPst.setString(1, interviewQuestionaireWrap.getInterviewQuestionaire().getQuestionaireCode());
										insertResultPst.setString(2, interviewQuestion.getQuestionCode());
										insertResultPst.setString(3, interviewAnswer.getAnswerCode());
										insertResultPst.setString(4, interviewAnswer.getAnswerValue());
										insertResultPst.setInt(5, interviewBasic.getId());
										insertResultPst.execute();
									}
								}
							}
						}
					}
				}
				
			}else if(interviewBasic.getUploadStatus() == UploadConstants.upload_status_modified){//更新
				
				//删除旧记录
				delResultPst = conn.prepareStatement("delete from sysq_result where interview_id = ?");
				delResultPst.setInt(1, interviewBasic.getId());
				delResultPst.execute();
				delInterviewPst = conn.prepareStatement("delete from sysq_interview where id = ?");
				delInterviewPst.setInt(1, interviewBasic.getId());
				delInterviewPst.execute();
				
				//添加新记录：添加访谈记录
				insertInterviewPst = conn.prepareStatement("insert into sysq_interview(id,doctor_id,patient_id,start_time,end_time,type,version_id) values(?,?,?,?,?,?,?);");
				insertInterviewPst.setInt(1,interviewBasic.getId());
				insertInterviewPst.setInt(2, interviewBasic.getInterviewerId());
				insertInterviewPst.setInt(3, interviewBasic.getIntervieweeId());
				insertInterviewPst.setString(4, interviewBasic.getStartTime());
				insertInterviewPst.setString(5, interviewBasic.getLastModifiedTime());
				insertInterviewPst.setInt(6, interviewBasic.getType());
				insertInterviewPst.setInt(7, interviewBasic.getVersionId());
				insertInterviewPst.execute();
				
				//添加新记录：添加答案
				insertResultPst = conn.prepareStatement("insert into sysq_result(questionaire_code,question_code,answer_code,answer_value,interview_id) values(?,?,?,?,?);");
				List<InterviewQuestionaireWrap> interviewQuestionaireWrapList = InterviewService.getInterviewQuestionaireList(interviewBasic.getId());
				if(interviewQuestionaireWrapList != null && interviewQuestionaireWrapList.size() > 0){
					for(InterviewQuestionaireWrap interviewQuestionaireWrap : interviewQuestionaireWrapList){
						List<InterviewQuestion> interviewQuestionList = InterviewService.getInterviewQuestionList(interviewBasic.getId(), interviewQuestionaireWrap.getInterviewQuestionaire().getQuestionaireCode());
						if(interviewQuestionList != null && interviewQuestionList.size() > 0){
							for(InterviewQuestion interviewQuestion : interviewQuestionList){
								List<InterviewAnswer> interviewAnswerList = InterviewService.getInterviewAnswerList(interviewBasic.getId(), interviewQuestion.getQuestionCode());
								if(interviewAnswerList != null && interviewAnswerList.size() > 0){
									for(InterviewAnswer interviewAnswer : interviewAnswerList){
										insertResultPst.setString(1, interviewQuestionaireWrap.getInterviewQuestionaire().getQuestionaireCode());
										insertResultPst.setString(2, interviewQuestion.getQuestionCode());
										insertResultPst.setString(3, interviewAnswer.getAnswerCode());
										insertResultPst.setString(4, interviewAnswer.getAnswerValue());
										insertResultPst.setInt(5, interviewBasic.getId());
										insertResultPst.execute();
									}
								}
							}
						}
					}
				}
			}
			
		}catch(Exception e){
			throw new RuntimeException("上传访问表失败",e);
		}finally{
			try{
				if(insertInterviewPst != null){
					insertInterviewPst.close();
				}
				if(insertResultPst != null){
					insertResultPst.close();
				}
				if(delInterviewPst != null){
					delInterviewPst.close();
				}
				if(delResultPst != null){
					delResultPst.close();
				}
			}catch(Exception e){
			}
		}
	}
	
	/**
	 * 处理访问者表
	 * @param pst
	 */
	private static void handleInterviewer(Connection conn,InterviewBasicWrap interviewBasicWrap){
		
		PreparedStatement pst = null;
		
		try{
			Interviewer interviewer = interviewBasicWrap.getInterviewer();
			if(interviewer.getUploadStatus() == UploadConstants.upload_status_not_upload){
				pst = conn.prepareStatement("insert into sysq_doctor(id,username,mobile,email,working_place) values(?,?,?,?,?)");
				pst.setInt(1, interviewer.getId());
				pst.setString(2, interviewer.getUsername());
				pst.setString(3, interviewer.getMobile());
				pst.setString(4, interviewer.getEmail());
				pst.setString(5, interviewer.getWorkingPlace());
				pst.execute();
				
			}else if(interviewer.getUploadStatus() == UploadConstants.upload_status_modified){
				pst = conn.prepareStatement("update sysq_doctor set username = ?,mobile = ?,email = ?,working_place = ? where id = ?");
				pst.setString(1, interviewer.getUsername());
				pst.setString(2, interviewer.getMobile());
				pst.setString(3, interviewer.getEmail());
				pst.setString(4, interviewer.getWorkingPlace());
				pst.setInt(5, interviewer.getId());
				pst.execute();
			}
		}catch(Exception e){
			throw new RuntimeException("上传访问者表失败",e);
		}finally{
			try{
				if(pst != null){
					pst.close();
				}
			}catch(Exception e){
			}
		}
	}
	
	/**
	 * 处理被访问者表
	 * @param pst
	 */
	private static void handleInterviewee(Connection conn,InterviewBasicWrap interviewBasicWrap){
		
		PreparedStatement pst = null;
		try{
			Interviewee interviewee  = interviewBasicWrap.getInterviewee();
			if(interviewee.getUploadStatus() == UploadConstants.upload_status_not_upload){
				pst = conn.prepareStatement("insert into sysq_patient(id,username,identity_card,mobile,province,city,address,post_code,family_mobile,family_address,dna,remark) values(?,?,?,?,?,?,?,?,?,?,?,?)");
				pst.setInt(1, interviewee.getId());
				pst.setString(2, interviewee.getUsername());
				pst.setString(3, interviewee.getIdentityCard());
				pst.setString(4, interviewee.getMobile());
				pst.setString(5, interviewee.getProvince());
				pst.setString(6, interviewee.getCity());
				pst.setString(7, interviewee.getAddress());
				pst.setString(8, interviewee.getPostCode());
				pst.setString(9, interviewee.getFamilyMobile());
				pst.setString(10, interviewee.getFamilyAddress());
				pst.setString(11, interviewee.getDna());
				pst.setString(12, interviewee.getRemark());
				pst.execute();
				
			}else if(interviewee.getUploadStatus() == UploadConstants.upload_status_modified){
				pst = conn.prepareStatement("update sysq_patient set username = ?,identity_card = ?,mobile = ?,province = ?,city = ?,address = ?,post_code = ?,family_mobile = ?,family_address = ?,dna = ?,remark = ? where id = ?");
				pst.setString(1, interviewee.getUsername());
				pst.setString(2, interviewee.getIdentityCard());
				pst.setString(3, interviewee.getMobile());
				pst.setString(4, interviewee.getProvince());
				pst.setString(5, interviewee.getCity());
				pst.setString(6, interviewee.getAddress());
				pst.setString(7, interviewee.getPostCode());
				pst.setString(8, interviewee.getFamilyMobile());
				pst.setString(9, interviewee.getFamilyAddress());
				pst.setString(10, interviewee.getDna());
				pst.setString(11, interviewee.getRemark());
				pst.setInt(12, interviewee.getId());
				pst.execute();
			}
		}catch(Exception e){
			throw new RuntimeException("上传访问者表失败",e);
		}finally{
			try{
				if(pst != null){
					pst.close();
				}
			}catch(Exception e){
			}
		}
	}
	
	/**
	 * 上传数据库
	 * @param conn
	 * @return
	 */
	private static boolean uploadDBData(Connection conn){
		
		boolean isSuccess = false;
		
		try{
			conn.setAutoCommit(false);
			
			List<InterviewBasicWrap> interviewBasicWrapList = InterviewService.getAllInterviewBasic();
			if(interviewBasicWrapList != null && interviewBasicWrapList.size() > 0){
				for(InterviewBasicWrap interviewBasicWrap : interviewBasicWrapList){
					if(interviewBasicWrap.getInterviewBasic().getIsTest() == InterviewBasic.TEST_NO && interviewBasicWrap.getInterviewBasic().getStatus() == InterviewBasic.STATUS_DONE){
						
						//处理访问者表
						handleInterviewer(conn,interviewBasicWrap);
						
						//处理被访问者表
						handleInterviewee(conn,interviewBasicWrap);
						
						//处理访问表
						handleInterviewBasic(conn,interviewBasicWrap);
					}
				}
				
				conn.commit();
				
				isSuccess = true;
			}
			
		}catch(Exception e1){
			
			isSuccess = false;
			
			try{
				conn.rollback();
			}catch(Exception e2){
			}
			
		}finally{
			
			try{
				if(conn != null){
					conn.close();
				}
			}catch(Exception e){
			}
		}
		
		return isSuccess;
	}
	
	/**
	 * 修改上传状态
	 */
	public static void modifyUploadStatus(){
		
		List<InterviewBasicWrap> interviewBasicWrapList = InterviewService.getAllInterviewBasic();
		if(interviewBasicWrapList != null && interviewBasicWrapList.size() > 0){
			for(InterviewBasicWrap interviewBasicWrap : interviewBasicWrapList){
				if(interviewBasicWrap.getInterviewBasic().getIsTest() == InterviewBasic.TEST_NO && interviewBasicWrap.getInterviewBasic().getStatus() == InterviewBasic.STATUS_DONE){
					
					//访问表
					InterviewBasic interviewBasic = interviewBasicWrap.getInterviewBasic();
					if(interviewBasic.getUploadStatus() == UploadConstants.upload_status_not_upload || interviewBasic.getUploadStatus() == UploadConstants.upload_status_modified){
						interviewBasic.setUploadStatus(UploadConstants.upload_status_uploaded);
						InterviewService.updateInterviewBasic(interviewBasic);
					}
					
					//访问者表
					Interviewer interviewer = interviewBasicWrap.getInterviewer();
					if(interviewer.getUploadStatus() == UploadConstants.upload_status_not_upload || interviewer.getUploadStatus() == UploadConstants.upload_status_modified){
						interviewer.setUploadStatus(UploadConstants.upload_status_uploaded);
						UserCenterService.modifyUser(interviewer);
						if(interviewer.getId() == SysqContext.getInterviewer().getId()){//更新当前登录用户
							SysqContext.setInterviewer(interviewer);
						}
					}
					
					//被访问者表
					Interviewee interviewee = interviewBasicWrap.getInterviewee();
					if(interviewee.getUploadStatus() == UploadConstants.upload_status_not_upload || interviewee.getUploadStatus() == UploadConstants.upload_status_modified){
						interviewee.setUploadStatus(UploadConstants.upload_status_uploaded);
						InterviewService.updateInterviewee(interviewee);
					}
				}
			}
		}
	}
	
	/**
	 * 上传多媒体数据
	 * @param ftpClient
	 * @return
	 */
	private static boolean uploadMediaData(FTPClient ftpClient){
		
		try{
			List<InterviewBasicWrap> interviewBasicWrapList = InterviewService.getAllInterviewBasic();
			if(interviewBasicWrapList != null && interviewBasicWrapList.size() > 0){
				for(InterviewBasicWrap interviewBasicWrap : interviewBasicWrapList){
					if(interviewBasicWrap.getInterviewBasic().getIsTest() == InterviewBasic.TEST_NO && interviewBasicWrap.getInterviewBasic().getStatus() == InterviewBasic.STATUS_DONE){
						File mediaDir = new File(PathConstants.getMediaDir(),interviewBasicWrap.getInterviewBasic().getId()+"");
						if(mediaDir.exists()){
							File audioDir = new File(mediaDir,"audio");
							File photoDir = new File(mediaDir,"photo");
							if( (audioDir.exists() && audioDir.listFiles().length > 0) || (photoDir.exists() && photoDir.listFiles().length > 0) ){
								
								//打包
								File zipFile = new File(PathConstants.getTmpDir(),mediaDir.getName()+".zip");
								ZipUtil.zip(mediaDir.getPath(), zipFile.getPath());
								
								//上传
								InputStream zipIS = new FileInputStream(zipFile);
								String filename = mediaDir.getName() + "_" + CommonUtils.getCustomDateTime("yyyyMMddHHmmss") + ".zip";
								boolean isSuccess = ftpClient.storeFile(new String(filename.getBytes("UTF-8"),"ISO8859-1"),zipIS);
								
								//清空临时打包文件
								zipFile.delete();
								
								//关闭文件流
								try{
									if(zipIS != null){
										zipIS.close();
									}
								}catch(Exception e){
								}
								
								if(isSuccess){
									//删除多媒体文件
									FileUtils.deleteQuietly(mediaDir);
									
								}else{
									return false;
								}
							}
						}
					}
				}
			}
			
		}catch(Exception e){
			return false;
			
		}finally{
			try{
				if(ftpClient != null){
					ftpClient.logout();
					ftpClient.disconnect();
				}
			}catch(Exception e){
			}
		}
		
		return true;
	}
	
	/**
	 * 上传
	 * @param files
	 * @throws FileNotFoundException 
	 */
	public static void upload(Handler handler){
		
		//检查网络是否通
		sendMessage(handler,UploadConstants.upload_progress_handling,"网络检查：正在进行中...");
		if(!checkNetwork()){
			sendMessage(handler,UploadConstants.upload_progress_finished,"网络检查：网络连接失败，请您检查您的网络是否可用");
			return;
		}else{
			sendMessage(handler,UploadConstants.upload_progress_handling,"网络检查：网络连接正常");
		}
		
		//检查FTP
		sendMessage(handler,UploadConstants.upload_progress_handling,"FTP检查：正在进行中...");
		FTPClient ftpClient = checkFTP();
		if(ftpClient == null){
			sendMessage(handler,UploadConstants.upload_progress_finished,"FTP检查：连接FTP服务器失败，请检查您的配置是否正确");
			return;
		}else{
			sendMessage(handler,UploadConstants.upload_progress_handling,"FTP检查：服务器连接正常");
		}
		
		//检查数据库
		sendMessage(handler,UploadConstants.upload_progress_handling,"数据库检查：正在进行中...");
		Connection conn = checkDB();
		if(conn == null){
			sendMessage(handler,UploadConstants.upload_progress_finished,"数据库检查：连接数据库服务器失败，请检查您的配置是否正确");
			return;
		}else{
			sendMessage(handler,UploadConstants.upload_progress_handling,"数据库检查：服务器连接正常");
		}
		
		//统计访谈数据
		sendMessage(handler,UploadConstants.upload_progress_handling,"统计访谈数据：正在进行中...");
		Map<String,Integer> dbReportMap = reportInterview();
		int interviewCount = dbReportMap.get("interviewCount");
		int interviewerCount = dbReportMap.get("interviewerCount");
		int intervieweeCount = dbReportMap.get("intervieweeCount");
		if(interviewCount == 0 && interviewerCount == 0 && intervieweeCount == 0){
			sendMessage(handler,UploadConstants.upload_progress_handling,"统计访谈数据：没有可上传的访谈数据");
		}else{
			sendMessage(handler,UploadConstants.upload_progress_handling,String.format("统计访谈数据：%s条访谈数据，%s条医生数据，%s条病人数据可上传",interviewCount,interviewerCount,intervieweeCount));
		}
		
		//上传数据库
		if(interviewCount >0 || interviewerCount > 0 || intervieweeCount > 0){
			sendMessage(handler,UploadConstants.upload_progress_handling,"上传数据库：正在进行中...");
			boolean isDBSuccess = uploadDBData(conn);
			if(isDBSuccess){
				sendMessage(handler,UploadConstants.upload_progress_handling,"上传数据库：上传完成");
				
				//修改上传状态
				modifyUploadStatus();
				
			}else{
				sendMessage(handler,UploadConstants.upload_progress_finished,"上传数据库：上传失败，请稍候重试");
				return;
			}
		}
		
		//统计多媒体数据
		sendMessage(handler,UploadConstants.upload_progress_handling,"统计多媒体包数据：正在进行中...");
		int mediaCount = reportMedia();
		if(mediaCount == 0){
			sendMessage(handler,UploadConstants.upload_progress_finished,"统计多媒体包数据：没有可上传的多媒体数据");
			return;
		}
		sendMessage(handler,UploadConstants.upload_progress_handling,String.format("统计多媒体包数据：%s个多媒体包可上传",mediaCount));
		
		//上传多媒体数据
		sendMessage(handler,UploadConstants.upload_progress_handling,"上传多媒体数据：正在进行中...");
		boolean isMediaSuccess = uploadMediaData(ftpClient);
		if(isMediaSuccess){
			sendMessage(handler,UploadConstants.upload_progress_finished,"上传多媒体数据：上传完成");
		}else{
			sendMessage(handler,UploadConstants.upload_progress_finished,"上传多媒体数据：上传失败，请稍候重试");
		}
		
	}
}
