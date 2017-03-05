package com.huasheng.sysq.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;

import com.huasheng.sysq.db.InterviewAnswerDB;
import com.huasheng.sysq.db.InterviewQuestionDB;
import com.huasheng.sysq.db.InterviewQuestionaireDB;
import com.huasheng.sysq.model.InterviewAnswer;
import com.huasheng.sysq.model.InterviewBasic;
import com.huasheng.sysq.model.InterviewQuestion;
import com.huasheng.sysq.model.InterviewQuestionaire;
import com.huasheng.sysq.service.InterviewService;

public class UploadUtils {
	
	/**
	 * 读取配置信息
	 * @return
	 * @throws IOException 
	 */
	private  static Properties loadConfig(){
		
		Properties props = new Properties();
		
		InputStream is = null; 
		try{
			is = new FileInputStream(new File(PathConstants.getFTPConfigPath()));
		}catch(FileNotFoundException fnfe){
			throw new RuntimeException("读取配置文件失败：ftp.properties文件不存在");
		}
		
		Reader reader = null;
		
		try{
			reader = new InputStreamReader(is, "utf-8");
		}catch(UnsupportedEncodingException e){
			try{
				is.close();
			}catch(IOException ioe){
				//ignore
			}
			throw new RuntimeException("读取配置文件失败：" + e.getMessage());
		}
		
		try{
			props.load(is);
		}catch(IOException ioe){
			throw new RuntimeException("读取配置文件失败：" + ioe.getMessage());
		}finally{
			try{
				reader.close();
			}catch(IOException e){
				//ignore
			}
		}
		
		String ip = props.getProperty("ip");
		if(ip == null || ip.trim().equals("")){
			throw new RuntimeException("读取配置文件失败：ip地址为空");
		}
		
		String port = props.getProperty("port");
		if(port == null || port.trim().equals("")){
			throw new RuntimeException("读取配置文件失败：端口为空");
		}
		
		String username = props.getProperty("username");
		if(username == null || username.trim().equals("")){
			throw new RuntimeException("读取配置文件失败：用户名为空");
		}
		
		String password = props.getProperty("password");
		if(password == null || password.trim().equals("")){
			throw new RuntimeException("读取配置文件失败：密码为空");
		}
		
		return props;
	}
	
	/**
	 * 连接服务器
	 * @param ip
	 * @param port
	 * @param username
	 * @param password
	 * @return
	 * @throws IOException 
	 */
	private static FTPClient openConnection(){
		
		//加载配置信息
		Properties props = null;
		try{
			props = loadConfig();
		}catch(Exception e){
			throw e;
		}
		
		FTPClient ftpClient = new FTPClient();
		
		//连接服务器
		try{
			ftpClient.connect(props.getProperty("ip"), Integer.parseInt(props.getProperty("port")));
		}catch(SocketException  se){
			throw new RuntimeException("连接服务器失败：请检查ip地址、端口是否正确以及FTP服务是否正常启动");
		}catch(IOException ioe){
			throw new RuntimeException("连接服务器失败：" + ioe.getMessage());
		}
		
		//登录服务器
		try{
			boolean loginResult = ftpClient.login(props.getProperty("username"), props.getProperty("password"));  
			if(!loginResult){
				try{
					ftpClient.disconnect();
				}catch(IOException e){
					//ignore
				}
				throw new RuntimeException("登录服务器失败：请检查用户名和密码是否正确");
			}
			
		}catch(IOException ioe){
			try{
				ftpClient.disconnect();
			}catch(IOException e){
				//ignore
			}
			throw new RuntimeException("登录服务器失败：" + ioe.getMessage());
		}
		
		//设置传输编码
		try{
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		}catch(IOException e){
			try{
				ftpClient.disconnect();
			}catch(IOException ioe){
				//ignore
			}
			throw new RuntimeException("设置传输编码失败：" + e.getMessage());
		}
		
		return ftpClient;
	}
	
	/**
	 * 获取录音文件集合
	 * @return
	 */
	private static List<File> getAudioFiles(){
		
		List<InterviewBasic> uploadedInterviewBasicList = InterviewService.getUnUploadInterviewBasic();
		
		//没有新访问记录
		if(uploadedInterviewBasicList == null || uploadedInterviewBasicList.size() <= 0){
			return null;
		}
		
		//过滤录音文件（根据身份证）
		List<File> uploadAudioFiles = new ArrayList<File>();
		for(InterviewBasic interviewBasic : uploadedInterviewBasicList){
			String identityCard = interviewBasic.getIdentityCard();
			File[] allAudioFiles = new File(PathConstants.getAudioDir()).listFiles();
			if(allAudioFiles != null && allAudioFiles.length > 0){
				for(File audioFile : allAudioFiles){
					if(audioFile.getName().startsWith(identityCard)){
						uploadAudioFiles.add(audioFile);
					}
				}
			}
			
		}
		
		return uploadAudioFiles;
	}
	
	/**
	 * 获取访谈数据文件
	 * @return
	 * @throws  
	 */
	private static File getDBDataFile(){
		List<InterviewBasic> unUploadInterviewBasicList = InterviewService.getUnUploadInterviewBasic();
		
		//无上传数据
		if(unUploadInterviewBasicList == null || unUploadInterviewBasicList.size() <= 0){
			return null;
		}
		
		//拼接sql
		List<String> sqls = new ArrayList<String>();
		//访谈基本信息
		for(InterviewBasic interviewBasic : unUploadInterviewBasicList){
			sqls.add(String.format("insert into %s(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s) " +
					"values(%d,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s',%d,%d,%d,'%s',%d,'%s','%s','%s',%d,'%s',%d);",
					TableConstants.TABLE_INTERVIEW_BASIC,
					"id",ColumnConstants.COLUMN_INTERVIEW_BASIC_USERNAME,ColumnConstants.COLUMN_INTERVIEW_BASIC_IDENTITY_CARD,ColumnConstants.COLUMN_INTERVIEW_BASIC_MOBILE,ColumnConstants.COLUMN_INTERVIEW_BASIC_PROVINCE,ColumnConstants.COLUMN_INTERVIEW_BASIC_CITY,ColumnConstants.COLUMN_INTERVIEW_BASIC_ADDRESS,ColumnConstants.COLUMN_INTERVIEW_BASIC_POST_CODE,ColumnConstants.COLUMN_INTERVIEW_BASIC_FAMILY_MOBILE,ColumnConstants.COLUMN_INTERVIEW_BASIC_FAMILY_ADDRESS,ColumnConstants.COLUMN_INTERVIEW_BASIC_REMARK,ColumnConstants.COLUMN_INTERVIEW_BASIC_DNA,ColumnConstants.COLUMN_INTERVIEW_BASIC_INTERVIEWER_ID,ColumnConstants.COLUMN_INTERVIEW_BASIC_TYPE,ColumnConstants.COLUMN_INTERVIEW_BASIC_IS_TEST,ColumnConstants.COLUMN_INTERVIEW_BASIC_START_TIME,ColumnConstants.COLUMN_INTERVIEW_BASIC_STATUS,ColumnConstants.COLUMN_INTERVIEW_BASIC_CUR_QUESTIONAIRE_CODE,ColumnConstants.COLUMN_INTERVIEW_BASIC_NEXT_QUESTION_CODE,ColumnConstants.COLUMN_INTERVIEW_BASIC_LAST_MODIFYED_TIME,ColumnConstants.COLUMN_INTERVIEW_BASIC_VERSION_ID,ColumnConstants.COLUMN_INTERVIEW_BASIC_QUIT_REASON,ColumnConstants.COLUMN_INTERVIEW_BASIC_IS_UPLOAD,
					interviewBasic.getId(),interviewBasic.getUsername() == null?"":interviewBasic.getUsername(),interviewBasic.getIdentityCard() == null ? "" : interviewBasic.getIdentityCard(),interviewBasic.getMobile() == null ? "" : interviewBasic.getMobile(),interviewBasic.getProvince() == null ? "" : interviewBasic.getProvince(),interviewBasic.getCity() == null ? "" : interviewBasic.getCity(),interviewBasic.getAddress() == null ? "" : interviewBasic.getAddress(),interviewBasic.getPostCode() == null ? "" : interviewBasic.getPostCode() ,interviewBasic.getFamilyMobile() == null ? "" : interviewBasic.getFamilyMobile(),interviewBasic.getFamilyAddress() == null ? "" : interviewBasic.getFamilyAddress(),interviewBasic.getRemark() == null ? "" : interviewBasic.getRemark(),interviewBasic.getDna() == null ? "" : interviewBasic.getDna(),interviewBasic.getInterviewerId(),interviewBasic.getType(),interviewBasic.getIsTest(),interviewBasic.getStartTime() == null ? "" : interviewBasic.getStartTime(),interviewBasic.getStatus(),interviewBasic.getCurQuestionaireCode() == null ? "" : interviewBasic.getCurQuestionaireCode(),interviewBasic.getNextQuestionCode() == null ? "" : interviewBasic.getNextQuestionCode(),interviewBasic.getLastModifiedTime() == null ? "" : interviewBasic.getLastModifiedTime(),interviewBasic.getVersionId(),interviewBasic.getQuitReason() == null ? "" : interviewBasic.getQuitReason(),interviewBasic.getIsUpload()
					));
			
			//访谈问卷
			List<InterviewQuestionaire> interviewQuestionaireList = InterviewQuestionaireDB.selectByInterviewBasicId(interviewBasic.getId());
			if(interviewQuestionaireList != null && interviewQuestionaireList.size() > 0){
				for(InterviewQuestionaire interviewQuestionaire : interviewQuestionaireList){
					sqls.add(String.format("insert into %s(%s,%s,%s,%s,%s,%s,%s,%s,%s) values(%d,%d,'%s','%s','%s',%d,%d,%d,'%s');", 
							TableConstants.TABLE_INTERVIEW_QUESTIONAIRE,
							"id",ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_INTERVIEW_BASIC_ID,ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_QUESTIONAIRE_CODE,ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_START_TIME,ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_LAST_MODIFIED_TIME,ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_STATUS,ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_SEQ_NUM,ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_VERSION_ID,ColumnConstants.COLUMN_INTERVIEW_QUESTIONAIRE_REMARK,
							interviewQuestionaire.getId(),interviewQuestionaire.getInterviewBasicId(),interviewQuestionaire.getQuestionaireCode() == null ? "" : interviewQuestionaire.getQuestionaireCode(),interviewQuestionaire.getStartTime() == null ? "" : interviewQuestionaire.getStartTime(),interviewQuestionaire.getLastModifiedTime() == null ? "" : interviewQuestionaire.getLastModifiedTime(),interviewQuestionaire.getStatus(),interviewQuestionaire.getSeqNum(),interviewQuestionaire.getVersionId(),interviewQuestionaire.getRemark() == null ? "" : interviewQuestionaire.getRemark()));
					
					//访谈问题
					List<InterviewQuestion> interviewQuestionList = InterviewQuestionDB.selectByQuestionaire(interviewBasic.getId(), interviewQuestionaire.getQuestionaireCode());
					if(interviewQuestionList != null && interviewQuestionList.size() > 0){
						for(InterviewQuestion interviewQuestion : interviewQuestionList){
							sqls.add(String.format("insert into %s(%s,%s,%s,%s,%s,%s) values(%d,%d,'%s','%s',%d,%d);", 
									TableConstants.TABLE_INTERVIEW_QUESTION,
									"id",ColumnConstants.COLUMN_INTERVIEW_QUESTION_INTERVIEW_BASIC_ID,ColumnConstants.COLUMN_INTERVIEW_QUESTION_QUESTIONAIRE_CODE,ColumnConstants.COLUMN_INTERVIEW_QUESTION_QUESTION_CODE,ColumnConstants.COLUMN_INTERVIEW_QUESTION_SEQ_NUM,ColumnConstants.COLUMN_INTERVIEW_QUESTION_VERSION_ID,
									interviewQuestion.getId(),interviewQuestion.getInterviewBasicId(),interviewQuestion.getQuestionaireCode() == null ? "" : interviewQuestion.getQuestionaireCode(),interviewQuestion.getQuestionCode() == null ? "" : interviewQuestion.getQuestionCode(),interviewQuestion.getSeqNum(),interviewQuestion.getVersionId()));
							
							//访谈答案
							List<InterviewAnswer> interviewAnswerList = InterviewAnswerDB.selectByQuestion(interviewBasic.getId(), interviewQuestion.getQuestionCode());
							if(interviewAnswerList != null && interviewAnswerList.size() > 0){
								for(InterviewAnswer interviewAnswer : interviewAnswerList){
									sqls.add(String.format("insert into %s(%s,%s,%s,%s,%s,%s,%s,%s,%s) values(%d,%d,'%s','%s','%s','%s','%s',%d,%d);",
											TableConstants.TABLE_INTERVIEW_ANSWER,
											"id",ColumnConstants.COLUMN_INTERVIEW_ANSWER_INTERVIEW_BASIC_ID,ColumnConstants.COLUMN_INTERVIEW_ANSWER_QUESTION_CODE,ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_LABEL,ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_CODE,ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_VALUE,ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_TEXT,ColumnConstants.COLUMN_INTERVIEW_ANSWER_ANSWER_SEQ_NUM,ColumnConstants.COLUMN_INTERVIEW_ANSWER_VERSION_ID,
											interviewAnswer.getId(),interviewAnswer.getInterviewBasicId(),interviewAnswer.getQuestionCode() == null ? "" : interviewAnswer.getQuestionCode(),interviewAnswer.getAnswerLabel() == null ? "" : interviewAnswer.getAnswerLabel(),interviewAnswer.getAnswerCode() == null ? "" : interviewAnswer.getAnswerCode(),interviewAnswer.getAnswerValue() == null ? "" : interviewAnswer.getAnswerValue(),interviewAnswer.getAnswerText() == null ? "" : interviewAnswer.getAnswerText(),interviewAnswer.getAnswerSeqNum(),interviewAnswer.getVersionId()));
								}
							}
						}
					}
				}
			}
		}
		
		//保存文件
		File interviewFile = new File(PathConstants.getTmpDir(),"data.sql");
		try{
			FileUtils.writeLines(interviewFile	,"utf-8",sqls,IOUtils.LINE_SEPARATOR_WINDOWS,false);
		}catch(IOException e){
			throw new RuntimeException("导出访谈数据失败",e);
		}
		
		return interviewFile;
	}
	
	/**
	 * 文件压缩
	 * @param fileList
	 * @param zipFile
	 */
	private static void zipFile(List<File> fileList,File zipFile){
		ZipOutputStream zos = null;
		List<InputStream> isList = new ArrayList<InputStream>();
		try{
			zos = new ZipOutputStream(new FileOutputStream(zipFile));
			for(File file : fileList){
				zos.putNextEntry(new ZipEntry(file.getName()));
				InputStream is = new FileInputStream(file);
				IOUtils.copy(is, zos);
				isList.add(is);
			}
		}catch(Exception e){
			throw new RuntimeException("压缩文件失败", e);
		}finally{
			try{
				if(zos != null){
					zos.close();
				}
				if(isList != null && isList.size() > 0){
					for(InputStream is : isList){
						if(is != null){
							is.close();
						}
					}
				}
			}catch(Exception e){
				//ignore
			}
		}
	}
	
	/**
	 * 检查环境
	 * @return
	 */
	public static void checkEnv(){
		
		FTPClient ftpClient = openConnection();
		try{
			ftpClient.logout();
			ftpClient.disconnect();
		}catch(IOException closeIOE){
			//ignore
		}
	}
	
	/**
	 * 上传文件
	 * @param files
	 * @throws FileNotFoundException 
	 */
	public static void upload(){
		
		FTPClient ftpClient = null;
		
		//连接服务器
		try{
			ftpClient = openConnection();
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
		
		//获取访谈数据文件
		File interviewDataFile = getDBDataFile();
		
		if(interviewDataFile == null){
			throw new RuntimeException("没有需要上传的数据");
		}
		
		//获取录音文件
		List<File> audioFiles = getAudioFiles();
		
		//压缩文件
		List<File> uploadFiles = new ArrayList<File>();
		if(audioFiles != null && audioFiles.size() > 0)	uploadFiles.addAll(audioFiles);
		if(interviewDataFile != null)	uploadFiles.add(interviewDataFile);
		File zipFile = new File(PathConstants.getTmpDir(),DateTimeUtils.getCustomDateTime("yyyyMMddHHmmss")+".zip");
		zipFile(uploadFiles, zipFile);
		
		//上传文件
		InputStream is = null;
		try{
			is = new FileInputStream(zipFile);
			boolean isUploadSuccess = ftpClient.storeFile(zipFile.getName(),is);
			if(isUploadSuccess){//上传成功
				
				//标记访谈记录“已上传”
				markUploaded4InterviewBasic();
				
				//删除上传录音文件
				clearAudioFiles(audioFiles);
				
				//删除临时文件
				interviewDataFile.delete();
				zipFile.delete();
				
			}else{//上传失败
				throw new RuntimeException("上传文件失败："+ftpClient.getReplyString());
			}
		}catch(IOException e){
			throw new RuntimeException("上传文件失败");
		}catch(RuntimeException e){
			throw e;
		}finally{
			try{
				if(is != null){
					is.close();
				}
				ftpClient.logout();
				ftpClient.disconnect();
			}catch(Exception e){
				//ignore
			}
		}
	}
	
	/**
	 * 标记访谈记录“已上传”
	 */
	private static void markUploaded4InterviewBasic(){
		List<InterviewBasic> uploadedInterviewBasicList = InterviewService.getUnUploadInterviewBasic();
		if(uploadedInterviewBasicList != null && uploadedInterviewBasicList.size() > 0){
			for(InterviewBasic interviewBasic : uploadedInterviewBasicList){
				interviewBasic.setIsUpload(InterviewBasic.UPLOAD_YES);
				InterviewService.updateInterviewBasic(interviewBasic);
			}
		}
	}
	
	/**
	 * 删除上传录音文件
	 * @param audioFiles
	 */
	private static void clearAudioFiles(List<File> audioFiles){
		if(audioFiles != null && audioFiles.size() > 0){
			for(File uploadAudioFile :  audioFiles){
				uploadAudioFile.delete();
			}
		}
	}
}
