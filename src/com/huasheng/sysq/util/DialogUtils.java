package com.huasheng.sysq.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

public class DialogUtils {
	
	/**
	 * 确认对话框
	 * @param context
	 * @param message
	 * @param onOkClickListener
	 */
	public static void showConfirmDialog(Context context,String message,OnClickListener onOkClickListener){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("确认");
		builder.setMessage(message);
		builder.setPositiveButton("确定", onOkClickListener);
		builder.setNegativeButton("取消", null);
		AlertDialog dialog = builder.create();
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}
	
	/**
	 * 确认提示框
	 * @param context
	 * @param message
	 * @param onOkClickListener
	 * @param onCancelClickListener
	 */
	public static void showConfirmDialog(Context context,String message,OnClickListener onOkClickListener,OnClickListener onCancelClickListener){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("确认");
		builder.setMessage(message);
		builder.setPositiveButton("确定", onOkClickListener);
		builder.setNegativeButton("取消", onCancelClickListener);
		AlertDialog dialog = builder.create();
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}
}
