package com.wlx.wsolandroid.music;

import java.io.File;

import com.wlx.wsolandroid.model.Music;


import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class PlayMusicService extends Service {

	private MediaPlayer mediaPlayer;

	private static final int PLAY = 101;
	private static final int REPLAY = 102;
	private static final int PAUSE = 103;
	private static final int STOP = 104;

	/** 广播过滤器的过滤动作 */
	public static final String CHANGE_PLAYMUSIC_BUTTON_STATE = "com.huahuaban.changePlayMusicButtonState";

	/** 把暂停按钮的内容改成“暂停” */
	public static final int CHANGE_PAUSE_BUTTON_TEXT1 = 200;
	/** 把暂停按钮的内容改成“播放” */
	public static final int CHANGE_PAUSE_BUTTON_TEXT2 = 201;

	private String musicPath;// 需要播放的音乐路径

	@Override
	public IBinder onBind(Intent intent) {
		return new MyBinder();
	}

	private class MyBinder extends Binder implements PlayMusicInterface {

		/**
		 * 获取目前音乐播放到什么位置(播放到第几秒)
		 * 
		 * @return
		 */
		public int getMediaPlayCurrentMillon() {
			if (mediaPlayer != null) {
				return mediaPlayer.getCurrentPosition() / 1000;
			} else {
				return 0;
			}
		}

		/**
		 * 获取音乐播放器的状态
		 */
		@Override
		public int getMediaPlayState() {
			int state = -1;
			if (mediaPlayer != null) {
				if (mediaPlayer.isPlaying()) {
					state = 1;
				} else {
					state = 0;
				}
			}
			return state;
		}

		/**
		 * 操作音乐
		 */
		@Override
		public void operate(Music music, int playAction) {
			switch (playAction) {
			case PLAY:
				// 如果musicPath为空，说明service还没有播放过音乐，所以直接播放音乐即可
				if (musicPath == null) {
					musicPath = music.getData();
					play();
				}
				// 如果muiscPath不为空,说明service正在播放音乐或者已经至少播放过一次音乐。这种情况下需要判断需要播放的是否是同一首音乐，并做处理
				else {
					// 如果需要播放的音乐相等，并且mediaPlayer为空，那就执行播放音乐操作,
					if (music.getData().equals(musicPath)
							&& mediaPlayer == null) {
						play();
					}
					// 如果需要播放的音乐相等,并且mediaPlayer不为空，什么都不需要做
					else if (music.getData().equals(musicPath)
							&& mediaPlayer != null) {

					}
					// 如果需要播放的音乐不相等,并且mediaPlayer不为空，则停止正在播放的音乐后，播放新的音乐
					else if (!music.getData().equals(musicPath)
							&& mediaPlayer != null) {
						stop();
						musicPath = music.getData();
						play();
					}
					// 如果需要播放的音乐不相等，并且并且mediaPlayer为空，则播放新的音乐
					else if (!music.getData().equals(musicPath)
							&& mediaPlayer == null) {
						musicPath = music.getData();
						play();
					}
				}
				break;
			case REPLAY:
				replay();
				break;
			case PAUSE:
				if (mediaPlayer == null) {
					play();
				} else {
					pause();
				}
				break;
			case STOP:
				stop();
				break;
			}

		}

		/**
		 * 播放指定位置的音乐
		 */
		@Override
		public void playTo(int position) {
			if (mediaPlayer != null) {
				mediaPlayer.seekTo(position);// 把播放位置定位到指定位置
//				if (mediaPlayer.isPlaying()) {
//					
//				} else {
//					pause();
//				}
			}

		}

	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {

		super.onDestroy();
	}

	/**
	 * 暂停音乐播放
	 */
	private void pause() {
		// 如果mediaPlay为空，直接返回
		if (mediaPlayer == null) {
			return;
		}
		// 如果正在播放音乐
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
			// 发广播，把暂停按钮的内容改称“继续”
			sendMusicPlayBroadCast(CHANGE_PAUSE_BUTTON_TEXT2);
		} else {
			mediaPlayer.start();
			// 发广播，把暂停按钮的内容改称“暂停”
			sendMusicPlayBroadCast(CHANGE_PAUSE_BUTTON_TEXT1);
		}
	}

	/**
	 * 重新播放音乐
	 */
	private void replay() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.seekTo(0);// 把播放位置定位到0，即音乐文件的头部
			return;
		}
		play();
	}

	/**
	 * 停止播放音乐
	 */
	private void stop() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;

			// 发广播，把暂停按钮的内容改称“播放”
			sendMusicPlayBroadCast(CHANGE_PAUSE_BUTTON_TEXT2);
		}
	}

	/**
	 * 播放音乐
	 */
	private void play() {

		File file = new File(musicPath);
		// 音乐文件地址存在，并且文件长度大于0，才有可能是合法音乐文件
		if (file.exists() && file.length() > 0) {
			try {
				mediaPlayer = new MediaPlayer();
				mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设定播放的是音乐类型的
				mediaPlayer.setDataSource(musicPath);// 把需要播放的音乐文件数据源放进来
				// mediaPlayer.setLooping(true);循环播放
				mediaPlayer.prepareAsync();// 异步准备好音乐文件的播放（如果是同步准备，则有可能音乐文件比较大，准备需要很长时间，会阻塞主线程）

				// 准备好播放音乐文件的回调函数
				mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
					@Override
					public void onPrepared(MediaPlayer mp) {
						mediaPlayer.start();
					}
				});

				// 音乐播放完毕后的回调函数
				mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						Toast.makeText(PlayMusicService.this, "音乐播放完毕",
								Toast.LENGTH_SHORT).show();
						stop();
						// 发广播，把暂停按钮的内容改称“播放”
						sendMusicPlayBroadCast(CHANGE_PAUSE_BUTTON_TEXT2);

					}
				});

				// 音乐播放器播放错误的时候的回调函数
				mediaPlayer.setOnErrorListener(new OnErrorListener() {
					@Override
					public boolean onError(MediaPlayer mp, int what, int extra) {
						Toast.makeText(PlayMusicService.this, "音乐文件损坏，播放音乐失败",
								Toast.LENGTH_SHORT).show();
						stop();
						return false;
					}
				});

				// 发广播，把暂停按钮的内容改称“暂停”
				sendMusicPlayBroadCast(CHANGE_PAUSE_BUTTON_TEXT1);

			} catch (Exception e) {
				Toast.makeText(this, "播放音乐失败", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
		} else {
			Toast.makeText(this, "音乐文件不存在", Toast.LENGTH_SHORT).show();
		}

	}

	private void sendMusicPlayBroadCast(int buttonState) {
		Intent intent = new Intent();
		intent.setAction(CHANGE_PLAYMUSIC_BUTTON_STATE);
		intent.putExtra("buttonState", buttonState);
		sendBroadcast(intent);
	}

}
