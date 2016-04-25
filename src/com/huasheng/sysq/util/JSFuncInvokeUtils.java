package com.huasheng.sysq.util;

import org.apache.commons.lang3.StringUtils;

public class JSFuncInvokeUtils {

	public static void invoke(final String funcStr){
		
		if(!StringUtils.isEmpty(StringUtils.trim(funcStr))){
			
			InterviewContext.getWebView().post(new Runnable() {
				@Override
				public void run() {
					InterviewContext.getWebView().loadUrl("javascript:eval('"+funcStr+"')");
				}
			});
		}
	}
	
	public static void invokeFunction(final String func){
		InterviewContext.getWebView().post(new Runnable() {
			@Override
			public void run() {
				InterviewContext.getWebView().loadUrl("javascript:"+func);
			}
		});
	}
}
