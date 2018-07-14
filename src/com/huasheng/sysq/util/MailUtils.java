package com.huasheng.sysq.util;

import org.apache.commons.mail.HtmlEmail;

public class MailUtils {
	
	public static final String SMTP_SERVER = "smtp.163.com";
	public static final int SMTP_PORT = 25;
	public static final String SEND_USER = "mapeng_wy@163.com";
	public static final String SEND_PWD = "07158179828_wy";
	public static final String TO_USER = "yuqingcui2012@163.com";

	/**
	 * 发送邮件
	 * @param subject	主题
	 * @param content 内容
	 */
	public static void send(String subject,String content){
		
		try{
			HtmlEmail mail = new HtmlEmail();
			mail.setHostName(SMTP_SERVER);
			mail.setSmtpPort(SMTP_PORT);
			mail.setAuthentication(SEND_USER,SEND_PWD);
			mail.setFrom(SEND_USER);
			mail.addTo(TO_USER);
			mail.setSubject(subject);
			mail.setCharset("UTF-8");
			mail.setHtmlMsg(content);
			mail.send();
		}catch(Exception e){
			throw new RuntimeException("邮件发送失败：" + e.getMessage());
		}
	}
}
