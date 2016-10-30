package com.itcast.zbc.customwidge.toastutils;

import com.itcast.zbc.customwidge.mAppAplication;


import android.widget.Toast;

public class ToastUtil {
	private static Toast toast;
	/**
	 * 创建单例的，强大的，能够连续弹的吐司
	 * @param text
	 */
	public static void showToast_Short(String text){
		if(toast==null){
			//如果为空，则创建
			toast = Toast.makeText(mAppAplication.mAppcontext, text, Toast.LENGTH_SHORT);
		}
		//如果toast不为空，则直接更改当前吐司的文本
		toast.setText(text);
		//显示
		toast.show();
	}
	/**
	 * 创建单例的，强大的，能够连续弹的吐司
	 * @param text
	 */
	public static void showToast_Long(String text){
		if(toast==null){
			//如果为空，则创建
			toast = Toast.makeText(mAppAplication.mAppcontext, text, Toast.LENGTH_LONG);
		}
		//如果toast不为空，则直接更改当前吐司的文本
		toast.setText(text);
		//显示
		toast.show();
	}
}
