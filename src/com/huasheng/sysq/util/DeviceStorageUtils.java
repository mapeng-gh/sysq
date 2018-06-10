package com.huasheng.sysq.util;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;

public class DeviceStorageUtils {

	/**
	 * 获取主存储根路径
	 * @return
	 */
	public static String getPrimaryStoragePath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}
	
	/**
	 * 获取外置SD卡根路径
	 * @return
	 */
	public static String getExtStoragePath(Context context) {
		StorageManager mStorageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (true == removable) {
                    return path;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
	}
	
	/**
	 * 检测外部SD卡是否可用
	 * @param context
	 * @param mountPoint
	 * @return
	 */
	public static boolean checkExtStorageAvailable(Context context,String mountPoint) {
		if (mountPoint == null) {
	           return false;
	    }
       StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
       try {
           Method getVolumeState = storageManager.getClass().getMethod("getVolumeState", String.class);
           String state = (String) getVolumeState.invoke(storageManager,mountPoint);
           return Environment.MEDIA_MOUNTED.equals(state);
       } catch (Exception e) {
       }
       return false;
	}
	
	/**
	 * 获取存剩余容量
	 * @return
	 */
	public static long getStorageAvailableBytes(String path) {
		return new StatFs(path).getAvailableBytes();
	}
}
