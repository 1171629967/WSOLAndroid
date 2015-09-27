package com.wlx.wsolandroid.music;

import com.wlx.wsolandroid.model.Music;


public interface PlayMusicInterface {

	/** 获取目前mediaplay播放到什么位置 */
	public int getMediaPlayCurrentMillon();
	
	/** 操作音乐播放器 */
	public void operate(Music music, int playAction);
	
	/** 播放指定位置 */
	public void playTo(int position);
	
	/**
	 * 获取播放器的状态  1表示正在播放  0表示没有在播放   －1表示播放器为空
	 * @return
	 */
	public int getMediaPlayState();
	
}