package com.wlx.wsolandroid.model;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class Pianzi extends BmobObject{
	private int pianziId;
	private String name;
	private String jietu;
	private String zhengjuurl;
	private String beizhu;

	
	
	
	public int getPianziId() {
		return pianziId;
	}

	public void setPianziId(int pianziId) {
		this.pianziId = pianziId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getJietu() {
		return jietu;
	}

	public void setJietu(String jietu) {
		this.jietu = jietu;
	}

	public String getZhengjuurl() {
		return zhengjuurl;
	}

	public void setZhengjuurl(String zhengjuurl) {
		this.zhengjuurl = zhengjuurl;
	}

	public String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}

	
	

}
