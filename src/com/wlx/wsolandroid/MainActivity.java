package com.wlx.wsolandroid;

import java.util.List;

import net.tsz.afinal.FinalBitmap;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.bmob.BmobProFile;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.BaseFragment.menuClicklistener;
import com.wlx.wsolandroid.application.WSOLApplication;
import com.wlx.wsolandroid.constant.Constant;
import com.wlx.wsolandroid.model.AppVersion;
import com.wlx.wsolandroid.model.Information;
import com.wlx.wsolandroid.model.User;
import com.wlx.wsolandroid.utils.Utils;
import com.wlx.wsolandroid.widget.MarqueeTextView;

public class MainActivity extends FragmentActivity implements OnClickListener,
		menuClicklistener {
	public SlidingMenu menu;
	private TextView tv_wuqi_1,tv_renwu_1, tv_renwu_2, tv_qita_1, tv_qita_2, tv_qita_3,
			tv_qita_4, tv_qita_5, tv_qita_6, tv_music_1,
			tv_fujiang_1;
	private TextView tv_qita_7;
	private String currentFragment;
	/** 公告栏，跑马灯效果 */
	private MarqueeTextView tv_pamadeng;
	long waitTime = 2000;
	long touchTime = 0;

	private LinearLayout ll;

	private FrameLayout fl_fragments;

	private RelativeLayout rl_me;
	private ImageView iv_avator;
	private TextView tv_nickName;
	
	public final static String  ACTION_FINISH_MAIN_ACTIVITY = "com.wlx.wsolandroid.action_finish_main_activity";

	// 百度定位
	private LocationClient mLocationClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.registerBoradcastReceiver();
		this.initSlidingMenu();
		this.initView();
		this.initLocation();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.fl_fragments, new WeaponJinpaiFragment())
				.commit();
		currentFragment = Constant.JINPAIWUQI;

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				loadPaomadengMessage();
			}
		}, 1000);

		checkVersion();
	}

	private void initView() {
		ll = (LinearLayout) menu.findViewById(R.id.ll);
		fl_fragments = (FrameLayout) findViewById(R.id.fl_fragments);
		Utils.setAppBackgroundColor(MainActivity.this, ll);
		Utils.setAppBackgroundColor(MainActivity.this, fl_fragments);

		tv_pamadeng = (MarqueeTextView) menu.findViewById(R.id.tv_pamadeng);
		rl_me = (RelativeLayout) menu.findViewById(R.id.rl_me);
		rl_me.setOnClickListener(this);
		iv_avator = (ImageView) menu.findViewById(R.id.iv_avator);
		tv_nickName = (TextView) menu.findViewById(R.id.tv_nickName);

		setFaceAndNickname();

		tv_wuqi_1 = (TextView) menu.findViewById(R.id.tv_wuqi_1);
		// tv_wuqi_2 = (TextView) menu.findViewById(R.id.tv_wuqi_2);
		 tv_renwu_1 = (TextView) menu.findViewById(R.id.tv_renwu_1);
		tv_renwu_2 = (TextView) menu.findViewById(R.id.tv_renwu_2);
		tv_qita_1 = (TextView) menu.findViewById(R.id.tv_qita_1);
		tv_qita_2 = (TextView) menu.findViewById(R.id.tv_qita_2);
		tv_qita_3 = (TextView) menu.findViewById(R.id.tv_qita_3);
		tv_qita_4 = (TextView) menu.findViewById(R.id.tv_qita_4);
		tv_qita_5 = (TextView) menu.findViewById(R.id.tv_qita_5);
		tv_qita_6 = (TextView) menu.findViewById(R.id.tv_qita_6);
		tv_qita_7 = (TextView) menu.findViewById(R.id.tv_qita_7);
		tv_music_1 = (TextView) menu.findViewById(R.id.tv_music_1);
		// tv_video_1 = (TextView) menu.findViewById(R.id.tv_video_1);
		tv_fujiang_1 = (TextView) menu.findViewById(R.id.tv_fujiang_1);
		tv_wuqi_1.setOnClickListener(this);
		// tv_wuqi_2.setOnClickListener(this);
		 tv_renwu_1.setOnClickListener(this);
		tv_renwu_2.setOnClickListener(this);
		tv_qita_1.setOnClickListener(this);
		tv_qita_2.setOnClickListener(this);
		tv_qita_3.setOnClickListener(this);
		tv_qita_4.setOnClickListener(this);
		tv_qita_5.setOnClickListener(this);
		tv_qita_6.setOnClickListener(this);
		tv_qita_7.setOnClickListener(this);
		tv_music_1.setOnClickListener(this);
		// tv_video_1.setOnClickListener(this);
		tv_fujiang_1.setOnClickListener(this);
	}

	private void initSlidingMenu() {
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		// menu.setShadowWidthRes(R.dimen.shadow_width);
		// menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.slidingmenu);
	}

	@Override
	public void onClick(View v) {
		// 个人信息----------------------------->
		if (v == rl_me) {
			Intent intent = new Intent(MainActivity.this,
					CompletePersonInfoActivity.class);
			intent.putExtra("fromWhere",
					CompletePersonInfoActivity.FROM_MY_INFO);
			startActivity(intent);
		}
		// 金牌武器上升值----------------------------->
		else if (v == tv_wuqi_1 && !currentFragment.equals(Constant.JINPAIWUQI)) {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fl_fragments, new WeaponJinpaiFragment())
					.commit();
			currentFragment = Constant.JINPAIWUQI;
		}
		// 武器锻造模拟器----------------------------->
		// if (v == tv_wuqi_2 && !currentFragment.equals(Constant.WUQIDUANZAO))
		// {
		// getSupportFragmentManager().beginTransaction()
		// .replace(R.id.fl_fragments, new WeaponDuanzaoFragment()).commit();
		// currentFragment = Constant.WUQIDUANZAO;
		// }
		// 任务列表----------------------------->
		else if (v == tv_renwu_1
				&& !currentFragment.equals(Constant.RENWULIEBIAO)) {
			RenwuListFragment fragment = new RenwuListFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fl_fragments, fragment).commit();
			currentFragment = Constant.RENWULIEBIAO;
		}
		// 内政等级表----------------------------->
		else if (v == tv_renwu_2
				&& !currentFragment.equals(Constant.NEIZHENGDENGJI)) {
			NeizhengDengjiFragment fragment = new NeizhengDengjiFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fl_fragments, fragment).commit();
			currentFragment = Constant.NEIZHENGDENGJI;
		}
		// 关于----------------------------->
		// else if (v == tv_qita_1 &&
		// !currentFragment.equals(Constant.ABOUTAPP)) {
		// LoadtxtFragment fragment = new LoadtxtFragment();
		// Bundle bundle = new Bundle();
		// bundle.putString("txtName", "aboutapp.txt");
		// fragment.setArguments(bundle);
		// getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragments,
		// fragment)
		// .commit();
		// currentFragment = Constant.ABOUTAPP;
		// }
		else if (v == tv_qita_1) {
			Utils.createBackColor(MainActivity.this);
			Utils.setAppBackgroundColor(MainActivity.this, ll);
			Utils.setAppBackgroundColor(MainActivity.this, fl_fragments);
		}
		// 每周活动----------------------------->
		else if (v == tv_qita_2
				&& !currentFragment.equals(Constant.MEIZHOUHUODONG)) {
			MeizhouhuodongFragment fragment = new MeizhouhuodongFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fl_fragments, fragment).commit();
			currentFragment = Constant.MEIZHOUHUODONG;
		}
		// 骗子名单----------------------------->
		else if (v == tv_qita_3
				&& !currentFragment.equals(Constant.PIANZIMINGDAN)) {
			PianzimingdanFragment fragment = new PianzimingdanFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fl_fragments, fragment).commit();
			currentFragment = Constant.PIANZIMINGDAN;
		}
		// 意见和建议----------------------------->
		else if (v == tv_qita_4 && !currentFragment.equals(Constant.YIJIAN)) {
			YijianFragment fragment = new YijianFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fl_fragments, fragment).commit();
			currentFragment = Constant.YIJIAN;
		}
		// 玩家意见一览----------------------------->
		else if (v == tv_qita_5
				&& !currentFragment.equals(Constant.YIJIANYILAN)) {
			YijianyilanFragment fragment = new YijianyilanFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fl_fragments, fragment).commit();
			currentFragment = Constant.YIJIANYILAN;
		}
		// 游戏BMG音乐----------------------------->
		else if (v == tv_music_1 && !currentFragment.equals(Constant.MUSIC)) {
			MusicFragment fragment = new MusicFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fl_fragments, fragment).commit();
			currentFragment = Constant.MUSIC;
		}
		// 附近的玩家----------------------------->
		else if (v == tv_qita_7 && !currentFragment.equals(Constant.NEAR_USER)) {
			NearUserFragment fragment = new NearUserFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fl_fragments, fragment).commit();
			currentFragment = Constant.NEAR_USER;
		}
		// 游戏PK视频----------------------------->
		// else if (v == tv_video_1 && !currentFragment.equals(Constant.VIDEO))
		// {
		// VideoListFragment fragment = new VideoListFragment();
		// getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragments,
		// fragment).commit();
		// currentFragment = Constant.VIDEO;
		// }
		// 吧主担保交易----------------------------->
		else if (v == tv_qita_6 && !currentFragment.equals(Constant.TRANSATION)) {
			TransationFragment fragment = new TransationFragment();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fl_fragments, fragment).commit();
			currentFragment = Constant.TRANSATION;
		}
		// 副将技能和属性----------------------------->
		else if (v == tv_fujiang_1 && !currentFragment.equals(Constant.FUJIANG)) {
			LoadtxtFragment fragment = new LoadtxtFragment();
			Bundle bundle = new Bundle();
			bundle.putString("txtName", "fujiang.txt");
			fragment.setArguments(bundle);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fl_fragments, fragment).commit();
			currentFragment = Constant.FUJIANG;
		}

		if (v != tv_qita_1 || v != rl_me) {
			menu.toggle(true);
		}

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		mLocationClient.stop();
		super.onStop();
	}

	@Override
	public void onResume() {
		super.onResume();
		setFaceAndNickname();
		if (mLocationClient != null) {
			mLocationClient.start();
		}
		MobclickAgent.onResume(this); // 统计时长
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public void menuClick() {
		menu.toggle(true);
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
	}

	/** 设置用户的头像和昵称 */
	private void setFaceAndNickname() {
		// 设置个人信息
		User currentUser = User.getCurrentUser(this, User.class);
		String trueFaceUrl = BmobProFile.getInstance(this).signURL(
				currentUser.getFaceName(), currentUser.getFaceUrl(),
				Constant.BMOB_ACCESSKEY, 0, null);
		FinalBitmap.create(this).display(iv_avator, trueFaceUrl);
		tv_nickName.setText(currentUser.getNickName());
	}
	
	
	
	/** 检查app版本是否需要更新 */
	private void checkVersion(){
		
		
		BmobQuery<AppVersion> bmobQuery = new BmobQuery<AppVersion>();
		bmobQuery.addWhereEqualTo("osType", "android");
		bmobQuery.findObjects(this, new FindListener<AppVersion>() {

			@Override
			public void onSuccess(List<AppVersion> appVersions) {
				AppVersion appVersion = appVersions.get(0);
				int versionCode = appVersion.getVersionCode();
				//有新版本
				if (versionCode > Utils.getVersionCode(MainActivity.this)) {
					AlertDialog.Builder builder = new Builder(MainActivity.this);
					String lastVersionDes = appVersion.getLastVersionDes();
					builder.setMessage(lastVersionDes.replace("$", "\n"));
					builder.setTitle("有新版本可以更新");
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					builder.show();
				}
				
				
				
			}

			@Override
			public void onError(int arg0, String arg1) {
				
			}
		});
		
		
	}
	
	
	

	private void loadPaomadengMessage() {
		BmobQuery<Information> bmobQuery = new BmobQuery<Information>();
		bmobQuery.addWhereEqualTo("type", "gonggao_android");
		bmobQuery.findObjects(this, new FindListener<Information>() {

			@Override
			public void onSuccess(List<Information> infos) {
				Information info = infos.get(0);
				String des = info.getDes();
				if (TextUtils.isEmpty(des)) {
					tv_pamadeng.setVisibility(View.GONE);
				} else {
					tv_pamadeng.setText(des);
					tv_pamadeng.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				tv_pamadeng.setVisibility(View.GONE);
			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& KeyEvent.KEYCODE_BACK == keyCode) {
			if (menu.isMenuShowing()) {
				finish();
			} else {
				menu.toggle(true);
			}
			return true;
		} else if (event.getAction() == KeyEvent.KEYCODE_MENU) {
			menu.toggle(true);
		}

		return super.onKeyDown(keyCode, event);
	}

	/** 初始化百度定位参数 */
	private void initLocation() {
		mLocationClient = ((WSOLApplication) getApplication()).mLocationClient;
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("gcj02");// 返回的定位结果是百度经纬度，默认值gcj02
		int span = 30 * 1000;
		option.setScanSpan(span);// 设置发起定位请求的间隔时间为10*1000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}
	
	
	
	public void registerBoradcastReceiver(){  
        IntentFilter myIntentFilter = new IntentFilter();  
        myIntentFilter.addAction(ACTION_FINISH_MAIN_ACTIVITY);  
        //注册广播        
        registerReceiver(mBroadcastReceiver, myIntentFilter);  
    }  
	
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){  
        @Override  
        public void onReceive(Context context, Intent intent) {  
            String action = intent.getAction();  
            if(action.equals(ACTION_FINISH_MAIN_ACTIVITY)){  
                finish();
            }  
        }           
    };  

}
