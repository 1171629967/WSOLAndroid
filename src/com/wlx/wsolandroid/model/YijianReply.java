package com.wlx.wsolandroid.model;

import cn.bmob.v3.BmobObject;

public class YijianReply extends BmobObject{
	private String replyId;
	private String content;
	private int from;//来自玩家留言还是作者留言，1表示作者，0表示玩家
	
	
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
	
	
	
	

}
