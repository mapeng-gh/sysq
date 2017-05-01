package com.huasheng.sysq.util;

import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;

public class AssetUtils {

	public static InputStream openAsStream(Context context,String path){
		AssetManager assetManager = context.getAssets();
		InputStream is = null;
		try{
			is = assetManager.open(path);
			return is;
		}catch(Exception e){
			throw new RuntimeException("打开asset失败：" + path, e);
		}
	}
}
