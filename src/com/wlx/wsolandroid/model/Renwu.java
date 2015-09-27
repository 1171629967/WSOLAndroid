package com.wlx.wsolandroid.model;

import cn.bmob.v3.BmobObject;

public class Renwu extends BmobObject{
	private int renwuId;
	private String renwuLevel;
	private String renwuName;
	public int getRenwuId() {
		return renwuId;
	}
	public void setRenwuId(int renwuId) {
		this.renwuId = renwuId;
	}
	public String getRenwuLevel() {
		return renwuLevel;
	}
	public void setRenwuLevel(String renwuLevel) {
		this.renwuLevel = renwuLevel;
	}
	public String getRenwuName() {
		return renwuName;
	}
	public void setRenwuName(String renwuName) {
		this.renwuName = renwuName;
	}
}
