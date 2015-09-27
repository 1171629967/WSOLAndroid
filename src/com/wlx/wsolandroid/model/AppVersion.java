package com.wlx.wsolandroid.model;

import cn.bmob.v3.BmobObject;

public class AppVersion extends BmobObject{
	private String osType;
	private int versionCode;
	private String versionName;
	private boolean mustUpDate;
	private int lastVersionCode;
	private String lastVersionDes;
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public int getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public boolean isMustUpDate() {
		return mustUpDate;
	}
	public void setMustUpDate(boolean mustUpDate) {
		this.mustUpDate = mustUpDate;
	}
	public int getLastVersionCode() {
		return lastVersionCode;
	}
	public void setLastVersionCode(int lastVersionCode) {
		this.lastVersionCode = lastVersionCode;
	}
	public String getLastVersionDes() {
		return lastVersionDes;
	}
	public void setLastVersionDes(String lastVersionDes) {
		this.lastVersionDes = lastVersionDes;
	}
	
	
	
	
}
