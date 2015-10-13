package com.wlx.wsolandroid.model;

import cn.bmob.v3.BmobObject;

public class YijianReply extends BmobObject{
	private String replyId;
	private String content;
	private int from;//来自玩家留言还是作者留言，1表示作者，0表示玩家
	private String username;
	private String fromOS;
	
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReplyId() {
		return replyId;
	}
	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}
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
	
	
	
	

}
