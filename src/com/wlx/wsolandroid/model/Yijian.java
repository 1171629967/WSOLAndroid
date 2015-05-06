package com.wlx.wsolandroid.model;

import cn.bmob.v3.BmobObject;

public class Yijian extends BmobObject{
	
	private String content;
	private String fromOS;
	private String username;
	
	
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFromOS() {
		return fromOS;
	}
	public void setFromOS(String fromOS) {
		this.fromOS = fromOS;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	

}
