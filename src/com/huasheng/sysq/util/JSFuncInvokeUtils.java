package com.huasheng.sysq.util;

public class JSFuncInvokeUtils {

	public static void invoke(final String funcStr){
		InterviewContext.getWebView().post(new Runnable() {
			@Override
			public void run() {
				InterviewContext.getWebView().loadUrl("javascript:entry('"+funcStr+"')");
			}
		});
	}
}
