package com.wlx.wsolandroid.model;

import cn.bmob.v3.BmobObject;

public class Yijian extends BmobObject{
	private String tiebaName;
	private String gameName;
	private String content;
	private String qq;
	private String fromOS;
	
	
	
	
	public String getFromOS() {
		return fromOS;
	}
	public void setFromOS(String fromOS) {
		this.fromOS = fromOS;
	}
	public String getTiebaName() {
		return tiebaName;
	}
	public void setTiebaName(String tiebaName) {
		this.tiebaName = tiebaName;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	
	
	

}
