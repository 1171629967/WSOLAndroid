package com.wlx.wsolandroid.music;

import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.R;
import com.wlx.wsolandroid.model.Music;
import com.wlx.wsolandroid.utils.Utils;
import com.wlx.wsolandroid.widget.MyActionBar;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class PlayMusicActivity extends Activity implements OnClickListener {
	private SeekBar seekBar;

	private Button bt_replay;
	private Button bt_pause;
	private Button bt_stop;
	private TextView tv_currentPlayTime;
	private TextView tv_totalPlayTime;

	private Intent intent;

	private int musicMaxMillon;// 歌曲的时长（秒）

	private PlayMusicInterface myBinder;
	private MyConn myConn;

	private Music music;// 从上个activity传进来的，需要播放的music

	private static final int PLAY = 101;
	private static final int REPLAY = 102;
	private static final int PAUSE = 103;
	private static final int STOP = 104;

	protected static final int SET_SEEKBAR_POSITION = 401;

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			seekBar.setProgress(msg.what);
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_music);
		music = (Music) getIntent().getExtras().getSerializable("music");

		initActionBar();
		initView();

		init();

	}

	private void initActionBar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(music.getMusicName());
		actionBar.setLeftEnable(true);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);

	}

	private void initView() {
		
		Utils.setAppBackgroundColor(this, findViewById(R.id.ll));
		// 初始化控件
		bt_replay = (Button) findViewById(R.id.bt_replay);
		bt_pause = (Button) findViewById(R.id.bt_pause);
		bt_stop = (Button) findViewById(R.id.bt_stop);
		seekBar = (SeekBar) findViewById(R.id.seekbar);
		bt_replay.setOnClickListener(this);
		bt_pause.setOnClickListener(this);
		bt_stop.setOnClickListener(this);
		tv_currentPlayTime = (TextView) findViewById(R.id.tv_currentPlayTime);
		tv_totalPlayTime = (TextView) findViewById(R.id.tv_totalPlayTime);
		
		//音乐进度条被拖动监听
				seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						int stopProgress = seekBar.getProgress();
						myBinder.playTo(stopProgress * 1000);
						int mediaPlayerState = myBinder.getMediaPlayState();
						if (mediaPlayerState == 1) {
							//handler.removeCallbacks(runnable);
						} else if (mediaPlayerState == 0) {
							handler.post(runnable);
							pause();
						}
					}
					
					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onProgressChanged(SeekBar seekBar, int progress,
							boolean fromUser) {
						
						
					}
				});
	}

	protected void init() {
		
		
		
		// 初始化intent
		intent = new Intent(this, PlayMusicService.class);

		// 在当前的activity中注册改变几个按钮控件的广播
		IntentFilter filter = new IntentFilter();
		filter.addAction(PlayMusicService.CHANGE_PLAYMUSIC_BUTTON_STATE);
		registerReceiver(broadcastReceiver, filter);

		musicMaxMillon = music.getDuration();
		seekBar.setMax(musicMaxMillon);
		int maxminute = musicMaxMillon / 60;
		int maxsecond = musicMaxMillon % 60;
		String maxTimeString = String.format("%02d:%02d", maxminute, maxsecond);
		tv_totalPlayTime.setText(maxTimeString);

		// 开启服务
		Intent intent = new Intent(this, PlayMusicService.class);
		startService(intent);
		// 绑定服务
		myConn = new MyConn();
		bindService(intent, myConn, BIND_AUTO_CREATE);

		// 延迟500毫秒去播放音乐（因为上面还在绑定服务，没这么快把MyBinder接口对象［中间人］返回回来）
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				play();
			}
		}, 500);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 点击“重播”按钮---------------------------－－－－---------------------->
		case R.id.bt_replay:
			replay();
			break;
		// 点击“暂停/播放”按钮------------------------------------------------->
		case R.id.bt_pause:
			pause();
			break;
		// 点击“停止”按钮------------------------------------------------->
		case R.id.bt_stop:
			stop();
			break;

		default:
			break;
		}

	}

	/**
	 * 暂停音乐播放
	 */
	private void pause() {
		// 获取播放器状态，并做相应设置
		int mediaPlayerState = myBinder.getMediaPlayState();
		if (mediaPlayerState == 1) {
			handler.removeCallbacks(runnable);
		} else if (mediaPlayerState == 0) {
			handler.post(runnable);
		}

		myBinder.operate(music, PAUSE);
	}

	/**
	 * 重新播放音乐
	 */
	private void replay() {
		myBinder.operate(music, REPLAY);

		seekBar.setProgress(0);
		handler.removeCallbacks(runnable);
		handler.post(runnable);
	}

	/**
	 * 停止播放音乐
	 */
	private void stop() {
		myBinder.operate(music, STOP);
		seekBar.setProgress(0);
		handler.removeCallbacks(runnable);
	}

	/**
	 * 播放音乐
	 */
	private void play() {
		myBinder.operate(music, PLAY);
		handler.post(runnable);
	}

	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			int position = myBinder.getMediaPlayCurrentMillon();
			if (position < seekBar.getMax()) {
				//设置当前歌曲播放的时间
				int curminute = position / 60;
				int cursecond = position % 60;
				String curTimeString = String.format("%02d:%02d", curminute, cursecond);
				tv_currentPlayTime.setText(curTimeString);
				seekBar.setProgress(position);
				handler.postDelayed(runnable, 1000);
			}

		}

	};

	private class MyConn implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			myBinder = (PlayMusicInterface) service;

		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

	}

	// 写一个广播的内部类，当收到动作时，根据相应的动作，对按钮进行操作
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// 操作“播放/暂停”按钮的文本----------------------------------->
			if (intent.getAction().equals(
					PlayMusicService.CHANGE_PLAYMUSIC_BUTTON_STATE)) {
				int buttonState = intent.getIntExtra("buttonState", 0);

				switch (buttonState) {
				case PlayMusicService.CHANGE_PAUSE_BUTTON_TEXT1:
					bt_pause.setText("暂停");
					break;
				case PlayMusicService.CHANGE_PAUSE_BUTTON_TEXT2:
					bt_pause.setText("播放");
					break;
				default:
					break;
				}
			}

		}
	};

	
	
	@Override
	protected void onDestroy() {
		stop();//停止播放音乐
		unregisterReceiver(broadcastReceiver);// 注销广播
		try {
			unbindService(myConn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onDestroy();
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("播放音乐"); // 统计页面
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("播放音乐");
	}

}
