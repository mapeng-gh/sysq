package com.huasheng.sysq.util;

import android.webkit.JavascriptInterface;

public class JSObject4Log {

	@JavascriptInterface
	public void debug(String tag,String msg){
		LogUtils.debug(tag, msg);
	}
	
	@JavascriptInterface
	public void error(String tag,String msg){
		LogUtils.error(tag, msg);
	}
}
