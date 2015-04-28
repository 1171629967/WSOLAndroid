package com.wlx.wsolandroid.model;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobGeoPoint;

public class User extends BmobUser{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 保存在服务器的头像名称 */
	private String faceName;
	private String faceUrl;
	private String qq;
	private String city;
	private String tiebaName;
	private String nickName;
	private String gameName;
	private BmobGeoPoint lastGeoPoint;
	private String lastAddress;
	
	
	
	
	
	
	public String getLastAddress() {
		return lastAddress;
	}
	public void setLastAddress(String lastAddress) {
		this.lastAddress = lastAddress;
	}
	public BmobGeoPoint getLastGeoPoint() {
		return lastGeoPoint;
	}
	public void setLastGeoPoint(BmobGeoPoint lastGeoPoint) {
		this.lastGeoPoint = lastGeoPoint;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getFaceName() {
		return faceName;
	}
	public void setFaceName(String faceName) {
		this.faceName = faceName;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTiebaName() {
		return tiebaName;
	}
	public void setTiebaName(String tiebaName) {
		this.tiebaName = tiebaName;
	}
	public String getFaceUrl() {
		return faceUrl;
	}
	public void setFaceUrl(String faceUrl) {
		this.faceUrl = faceUrl;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	

}
