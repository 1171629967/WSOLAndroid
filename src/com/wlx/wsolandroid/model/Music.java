package com.wlx.wsolandroid.model;



import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Music extends BmobObject
{
	
	
	private BmobFile musicFile;
	private int musicId;
	private String musicName;
	public BmobFile getMusicFile() {
		return musicFile;
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
