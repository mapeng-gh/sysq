package com.huasheng.sysq.util;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

	/**
	 * 网络是否可用
	 * @param context
	 * @return
	 */
	public static boolean isNetworkEnable(Context context){
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni == null ? false : true;
	}
	
	/**
	 * 获取httpclient单例
	 */
	private static OkHttpClient httpClient = null;
	private static OkHttpClient getHttpClient(){
		if(httpClient == null){
			httpClient = new OkHttpClient();
		}
		return httpClient;
	}
	
	/**
	 * 执行get请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String execGetRequest(String url , Map<String,String> params){
		
		OkHttpClient client = getHttpClient();
		
		//拼接参数
		HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
		if(params != null){
			Set<String> keys = params.keySet();
			if(keys != null && keys.size() > 0){
				for(String key : keys){
					urlBuilder.addQueryParameter(key, params.get(key));
				}
			}
		}
		HttpUrl httpUrl = urlBuilder.build();
		
		//构造请求
		Request.Builder reqBuilder = new Request.Builder();
		Request req = reqBuilder.url(httpUrl).build();
		
		//执行请求
		Call call = client.newCall(req);
		Response response = null;
		try{
			response = call.execute();
		}catch(Exception e){
			throw new RuntimeException("执行请求失败：" + e.getMessage(), e);
		}
		
		//读取响应体
		if(response.isSuccessful()){
			ResponseBody responseBody = response.body();
			try{
				return responseBody.string();
			}catch(Exception e){
				throw new RuntimeException("读取流失败：" + e.getMessage(), e);
			}finally{
				if(responseBody != null){
					responseBody.close();
				}
			}
		}else{
			throw new RuntimeException("执行请求非200");
		}
	}
	
	/**
	 * 下载文件
	 * @param url
	 * @param saveToFile
	 */
	public static void download(String url , File saveToFile){
		
		OkHttpClient client = getHttpClient();
		
		//构造请求
		Request.Builder reqBuilder = new Request.Builder();
		Request req = reqBuilder.url(url).build();
		
		//执行请求
		Call call = client.newCall(req);
		Response response = null;
		try{
			response = call.execute();
		}catch(Exception e){
			throw new RuntimeException("执行请求失败：" + e.getMessage(), e);
		}
		
		//读取响应体
		if(response.isSuccessful()){
			ResponseBody responseBody = response.body();
			try{
				InputStream is = responseBody.byteStream();
				FileUtils.copyInputStreamToFile(is, saveToFile);
				
			}catch(Exception e){
				throw new RuntimeException("读取流失败：" + e.getMessage(), e);
			}finally{
				if(responseBody != null){
					responseBody.close();
				}
			}
		}else{
			throw new RuntimeException("执行请求非200");
		}
	}
}
