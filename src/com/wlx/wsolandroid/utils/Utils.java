package com.wlx.wsolandroid.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import com.wlx.wsolandroid.R;
import com.wlx.wsolandroid.application.WSOLApplication;
import com.wlx.wsolandroid.constant.Constant;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;

public class Utils {

	private static int[] backResourceArray1 = {
			R.drawable.app_backgroundcolor0, R.drawable.app_backgroundcolor1,
			R.drawable.app_backgroundcolor2, R.drawable.app_backgroundcolor3,
			R.drawable.app_backgroundcolor4, R.drawable.app_backgroundcolor5,
			R.drawable.app_backgroundcolor6, R.drawable.app_backgroundcolor7,
			R.drawable.app_backgroundcolor8, R.drawable.app_backgroundcolor9,
			R.drawable.app_backgroundcolor10, R.drawable.app_backgroundcolor11,
			R.drawable.app_backgroundcolor12, R.drawable.app_backgroundcolor13 };

	public static void setAppBackgroundColor(Activity activity, View view) {
		view.setBackgroundResource(backResourceArray1[getSpBackColor(activity)]);
	}

	/** 获取存在SP中的APP主题颜色 */
	public static int getSpBackColor(Activity activity) {
		SharedPreferences sp = activity.getSharedPreferences(Constant.SP_NAME,
				Activity.MODE_PRIVATE);
		return sp.getInt(Constant.SP_PARAM_APPBACKCOLOR, 0);
	}

	/** 随机生成主题颜色 */
	public static void createBackColor(Activity activity) {
		SharedPreferences sp = activity.getSharedPreferences(Constant.SP_NAME,
				Activity.MODE_PRIVATE);
		int currentColor = sp.getInt(Constant.SP_PARAM_APPBACKCOLOR, 0);
		int nextColor = currentColor;
		if (currentColor == Constant.APP_BACK_COLOR_NUM) {
			nextColor = 0;
		} else {
			nextColor++;
		}
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt(Constant.SP_PARAM_APPBACKCOLOR, nextColor).commit();
	}

	public static String getString(InputStream inputStream) {
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(inputStream, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(inputStreamReader);
		StringBuffer sb = new StringBuffer("");
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 获取手机屏幕分辨率，
	 * 
	 * @param activity
	 * @param type
	 *            参数1表示获取屏幕宽像素值，2表示获取屏幕高像素值，3表示分辨率
	 * @return
	 */
	public static int getDisplay(Activity activity, int type) {
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		Display localDisplay = activity.getWindowManager().getDefaultDisplay();
		localDisplay.getMetrics(localDisplayMetrics);
		int i = 0;
		switch (type) {
		case 1:
			i = localDisplay.getWidth();
			break;
		case 2:
			i = localDisplay.getHeight();
			break;
		case 3:
			i = localDisplayMetrics.densityDpi;
			break;
		}
		return i;
	}

	/**
	 * 获取SD卡的绝对路径
	 * 
	 * @return
	 */
	public static String getSDCardDIR() {
		return Environment.getExternalStorageDirectory().getPath();
	}

	public static String getDownLoadMusicDir() {
		String dirString = getSDCardDIR() + "/wsolMuiscs/";
		makesureParentExist(new File(dirString));
		return dirString;
	}

	/**
	 * 确保父级目录存在
	 * 
	 * @param paramFile
	 */
	public static void makesureParentExist(File paramFile) {
		File file = paramFile;
		if (file != null && !file.exists()) {
			file.mkdirs();
		}
	}

	public static String selectImage(Context context, final Uri uri) {
		// Log.e(TAG, selectedImage.toString());
		if (uri != null) {
			String uriStr = uri.toString();
			String path = uriStr.substring(10, uriStr.length());
			if (path.startsWith("com.sec.android.gallery3d")) {
				Log.e("TAG", "It's auto backup pic path:" + uri.toString());
				return null;
			}
		}
		String path = "";
		if (uri.getScheme().equals("file")) {
			path = uri.getPath();
		} else {
			path = getSelectPhotoPath(context, uri);
		}
		return path;
	}

	private static String getSelectPhotoPath(Context context, Uri uri) {
		String[] filePathColumn = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(uri, filePathColumn,
				null, null, null);
		cursor.moveToFirst();
		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String picturePath = cursor.getString(columnIndex);
		cursor.close();

		if (picturePath == null) {
			picturePath = Utils.getDataColumn(context, uri, null, null);
		}
		return picturePath;
	}

	public static String getDataColumn(Context context, Uri uri,
			String selection, String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection,
					selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int column_index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(column_index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	// 以下是获得版本信息的工具方法
	// 版本名
	public static String getVersionName(Context context) {
		return getPackageInfo(context).versionName;
	}

	// 版本号
	public static int getVersionCode(Context context) {
		return getPackageInfo(context).versionCode;
	}

	private static PackageInfo getPackageInfo(Context context) {
		PackageInfo pi = null;

		try {
			PackageManager pm = context.getPackageManager();
			pi = pm.getPackageInfo(context.getPackageName(),
					PackageManager.GET_CONFIGURATIONS);

			return pi;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pi;
	}

	/**
	 * 返回值 -1：没有网络 1：WIFI网络2：wap网络3：net网络
	 * 
	 * @description 必填
	 * @note 可选
	 * @param context
	 * @return
	 */
	public static int GetNetype(Context context) {
		int netType = -1;
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}
		int nType = networkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
				netType = 3;
			} else {
				netType = 2;
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = 1;
		}
		return netType;
	}

}
