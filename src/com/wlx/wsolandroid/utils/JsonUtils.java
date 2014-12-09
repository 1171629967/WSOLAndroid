package com.wlx.wsolandroid.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wlx.wsolandroid.model.Pianzi;

public class JsonUtils {
	
	public static ArrayList<Pianzi> getPianzisFromJson(String string) throws JSONException{
		ArrayList<Pianzi> pianzis = new ArrayList<Pianzi>();
		JSONObject jsonObject = new JSONObject(string);
		JSONArray array = jsonObject.getJSONArray("pianzis");
		
		int size = array.length();
		for (int i = 0; i < size; i++) {
			Pianzi pianzi = new Pianzi();
			JSONObject jb = array.getJSONObject(i);
			//获取名字
			String name = jb.optString("name");
			pianzi.setName(name);
			//获取截图数组
			String jietu = jb.optString("jietu");
			pianzi.setJietu(jietu);
			//获取证据url
			String zhengjuurl = jb.optString("zhengjuurl");
			pianzi.setZhengjuurl(zhengjuurl);
			//获取备注
			String beizhu = jb.optString("beizhu");
			pianzi.setBeizhu(beizhu);
			
			//最后把骗子模型放进数组
			pianzis.add(pianzi);
		}
		return pianzis;
	}

}
