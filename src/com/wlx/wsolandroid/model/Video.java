package com.wlx.wsolandroid.model;



import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Video extends BmobObject
{
	
	
	private String videoUrl;
	private int videoId;
	private StringBuilder videoName;
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public int getVideoId() {
		return videoId;
	}
	public void setVideoId(int videoId) {
		this.videoId = videoId;
	}
	public StringBuilder getVideoName() {
		return videoName;
	}
	public void setVideoName(StringBuilder videoName) {
		this.videoName = videoName;
	}
	
	
	

}
