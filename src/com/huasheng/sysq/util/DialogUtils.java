package com.huasheng.sysq.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.Toast;

public class DialogUtils {
	
	/**
	 * 对话框：单回调
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
	 * 对话框：双回调
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
	
	/**
	 * 对话框：自定义视图
	 * @param context
	 * @param title
	 * @param customView
	 * @param onOkClickListener
	 * @return
	 */
	public static AlertDialog showCustomDialog(Context context,String title,View customView,OnClickListener onOkClickListener){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setView(customView);
		builder.setPositiveButton("确定", onOkClickListener);
		builder.setNegativeButton("取消", null);
		AlertDialog dialog = builder.create();
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		return dialog;
	}
	
	/**
	 * 对话框：提示信息
	 * @param context
	 * @param title
	 * @param message
	 * @param btnText
	 */
	public static void showPromptDialog(Context context,String title,String message,String btnText){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setNegativeButton(btnText,null);
		AlertDialog dialog = builder.create();
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}
	
	/**
	 * 长Toast
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void showLongToast(Context context,String message){
		Toast.makeText(context, message,Toast.LENGTH_LONG).show();
	}
	
	/**
	 * 短Toast
	 * @param context
	 * @param message
	 */
	public static void showShortToast(Context context,String message){
		Toast.makeText(context, message,Toast.LENGTH_SHORT).show();
	}
}
