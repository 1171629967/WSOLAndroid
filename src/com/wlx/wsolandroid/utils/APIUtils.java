package com.wlx.wsolandroid.utils;

import com.wlx.wsolandroid.constant.Constant;

import net.tsz.afinal.http.AjaxParams;


public class APIUtils {
	
	public static AjaxParams getTulingParams(String question){
		AjaxParams params = new AjaxParams();
		params.put("info", question);
		params.put("key", Constant.tulingKEY);
		return params;
	}

}
