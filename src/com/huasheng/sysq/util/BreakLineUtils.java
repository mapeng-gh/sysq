package com.huasheng.sysq.util;

public class BreakLineUtils {

	public static final String PARA_SEPERATOR = "<para>";
	
	/**
	 * 置换成<p>
	 * @param description
	 * @return
	 */
	public static String handleParaInHTML(String description){
		
		if(!description.contains(PARA_SEPERATOR)){//不分段
			return "<p>" + description + "</p>";
		}
		
		StringBuffer sb = new StringBuffer();
		
		String[] paras = description.split(PARA_SEPERATOR);
		for(String para : paras){
			if(!"".equals(para)){
				sb.append("<p>").append(para).append("</p>");
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * 置换成\n
	 * @param description
	 * @return
	 */
	public static String handParaInApp(String description){
		return description.replaceAll(PARA_SEPERATOR, "\n");
	}
}
