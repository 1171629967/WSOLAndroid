package com.wlx.wsolandroid.model;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class Transation extends BmobObject{

	private String title;
	private String buy;
	private String sell;
	private String url;

	
	
	


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBuy() {
		return buy;
	}

	public void setBuy(String buy) {
		this.buy = buy;
	}

	public String getSell() {
		return sell;
	}

	public void setSell(String sell) {
		this.sell = sell;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	

}
