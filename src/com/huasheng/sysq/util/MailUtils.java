package com.huasheng.sysq.util;

import java.io.File;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.HtmlEmail;

public class MailUtils {

	/**
	 * 发送邮件
	 * @param to 收件人
	 * @param subject	主题
	 * @param content 内容
	 */
	public static void send(String to,String subject,String content){
		
		//加载配置
		Map<String,String> mailConfig = CommonUtils.readProperties(new File(PathConstants.getSettingsDir(),"mail.config"),"UTF-8");
		if(mailConfig == null){
			throw new RuntimeException("邮件配置不正确");
		}
		String smtpServer = mailConfig.get("smtpServer");
		String smtpPort = mailConfig.get("smtpPort");
		String sendUsername = mailConfig.get("sendUsername");
		String sendPassword = mailConfig.get("sendPassword");
		if(StringUtils.isEmpty(smtpServer) || StringUtils.isEmpty(smtpPort) || !CommonUtils.test("^[1-9][0-9]*$", smtpPort) || StringUtils.isEmpty(sendUsername) || StringUtils.isEmpty(sendPassword)){
			throw new RuntimeException("邮件配置不正确");
		}
		
		//发送邮件
		try{
			HtmlEmail mail = new HtmlEmail();
			mail.setHostName(smtpServer);
			mail.setSmtpPort(Integer.parseInt(smtpPort));
			mail.setAuthentication(sendUsername,sendPassword);
			mail.setFrom(sendUsername);
			mail.addTo(to);
			mail.setSubject(subject);
			mail.setCharset("UTF-8");
			mail.setHtmlMsg(content);
			mail.send();
		}catch(Exception e){
			throw new RuntimeException("邮件发送失败：" + e.getMessage());
		}
	}
}
