package com.wlx.wsolandroid.model;



import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Music extends BmobObject implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int duration;
	private BmobFile musicFile;
	private int musicId;
	private String musicName;
	
	private String data;//本地播放地址
	public BmobFile getMusicFile() {
		return musicFile;
	}
	
	
	
	public String getData() {
		return data;
	}



	public void setData(String data) {
		this.data = data;
	}



	public int getDuration() {
		return duration;
	}



	public void setDuration(int duration) {
		this.duration = duration;
	}



	public void setMusicFile(BmobFile musicFile) {
		this.musicFile = musicFile;
	}
	
	public int getMusicId() {
		return musicId;
	}
	public void setMusicId(int musicId) {
		this.musicId = musicId;
	}
	public String getMusicName() {
		return musicName;
	}
	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}
	
	

}
