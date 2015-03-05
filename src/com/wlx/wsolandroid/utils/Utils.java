package com.wlx.wsolandroid.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;

public class Utils {

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
     * @param activity
     * @param type 参数1表示获取屏幕宽像素值，2表示获取屏幕高像素值，3表示分辨率
     * @return
     */
    public static int getDisplay(Activity activity,int type){
    	DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    	Display localDisplay = activity.getWindowManager().getDefaultDisplay();
    	localDisplay.getMetrics(localDisplayMetrics);
    	int i=0;
    	switch (type) {
		case 1:
			i=localDisplay.getWidth();
			break;
		case 2:
			i=localDisplay.getHeight();
			break;
		case 3:
			i=localDisplayMetrics.densityDpi;
			break;		
		}
    	return i;
    }
    
    /**
	 * 获取SD卡的绝对路径
	 * @return
	 */
	public static String getSDCardDIR(){
		return Environment.getExternalStorageDirectory().getPath();
	}
	
	public static String getDownLoadMusicDir(){
		String dirString =  getSDCardDIR()+"/wsolMuiscs/";
		makesureParentExist(new File(dirString));
		return dirString;
	}
	
	/**
	 * 确保父级目录存在
	 * @param paramFile
	 */
	public static void makesureParentExist(File paramFile)
	{
		File file = paramFile;
		if ( file != null && !file.exists()) {
			file.mkdirs();
		}
	}

}
