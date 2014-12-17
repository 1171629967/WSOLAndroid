package com.wlx.wsolandroid;


import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import net.youmi.android.AdManager;
import net.youmi.android.onlineconfig.OnlineConfigCallBack;
import net.youmi.android.spot.SpotManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.BaseFragment.menuClicklistener;
import com.wlx.wsolandroid.constant.Constant;
import com.wlx.wsolandroid.model.Pianzi;
import com.wlx.wsolandroid.utils.APIUtils;
import com.wlx.wsolandroid.utils.JsonUtils;
import com.wlx.wsolandroid.widget.MarqueeTextView;

public class MainActivity extends FragmentActivity implements OnClickListener, menuClicklistener {
	public SlidingMenu menu;
	private TextView tv_wuqi_1, tv_renwu_1, tv_renwu_2, tv_qita_2, tv_qita_3, tv_fujiang_1;
	private String currentFragment;
	/** 公告栏，跑马灯效果 */
	private MarqueeTextView tv_pamadeng;
	private FinalHttp finalHttp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		finalHttp = new FinalHttp();

		// 有米插屏广告预加载
		AdManager.getInstance(this).init("1fd03cb84d3ba452", "b078ce712a256153", false);
		SpotManager.getInstance(this).loadSpotAds();
		SpotManager.getInstance(this).setSpotOrientation(SpotManager.ORIENTATION_PORTRAIT);
		AdManager.getInstance(this).asyncGetOnlineConfig(Constant.AD_INTERVAL_TIME, new OnlineConfigCallBack() {
			@Override
			public void onGetOnlineConfigSuccessful(String key, String value) {
				// 获取在线参数成功
				int adIntervalTime = 30;
				try {
					adIntervalTime = Integer.parseInt(value);
				} catch (Exception e) {
					adIntervalTime = 30;
				}				
				SpotManager.getInstance(MainActivity.this).setShowInterval(adIntervalTime);
			}

			@Override
			public void onGetOnlineConfigFailed(String key) {
				SpotManager.getInstance(MainActivity.this).setShowInterval(30);
			}
		});	


		this.initSlidingMenu();
		this.initView();
		getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragments, new WeaponJinpaiFragment()).commit();
		currentFragment = Constant.JINPAIWUQI;
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				loadPaomadengMessage();
			}
		}, 1000);
		
	}

	private void initView() {
		tv_pamadeng = (MarqueeTextView) menu.findViewById(R.id.tv_pamadeng);
		
		tv_wuqi_1 = (TextView) menu.findViewById(R.id.tv_wuqi_1);
		// tv_wuqi_2 = (TextView) menu.findViewById(R.id.tv_wuqi_2);
		tv_renwu_1 = (TextView) menu.findViewById(R.id.tv_renwu_1);
		tv_renwu_2 = (TextView) menu.findViewById(R.id.tv_renwu_2);
		// tv_qita_1 = (TextView) menu.findViewById(R.id.tv_qita_1);
		tv_qita_2 = (TextView) menu.findViewById(R.id.tv_qita_2);
		tv_qita_3 = (TextView) menu.findViewById(R.id.tv_qita_3);
		tv_fujiang_1 = (TextView) menu.findViewById(R.id.tv_fujiang_1);
		tv_wuqi_1.setOnClickListener(this);
		// tv_wuqi_2.setOnClickListener(this);
		tv_renwu_1.setOnClickListener(this);
		tv_renwu_2.setOnClickListener(this);
		// tv_qita_1.setOnClickListener(this);
		tv_qita_2.setOnClickListener(this);
		tv_qita_3.setOnClickListener(this);
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_MENU:
			menu.toggle(true);
			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		// 金牌武器上升值----------------------------->
		if (v == tv_wuqi_1 && !currentFragment.equals(Constant.JINPAIWUQI)) {
			getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragments, new WeaponJinpaiFragment()).commit();
			currentFragment = Constant.JINPAIWUQI;
		}
		// 武器锻造模拟器----------------------------->
		// if (v == tv_wuqi_2 && !currentFragment.equals(Constant.WUQIDUANZAO))
		// {
		// getSupportFragmentManager().beginTransaction()
		// .replace(R.id.fl_fragments, new WeaponDuanzaoFragment()).commit();
		// currentFragment = Constant.WUQIDUANZAO;
		// }
		// 任务报酬一览----------------------------->
		else if (v == tv_renwu_1 && !currentFragment.equals(Constant.RENWUBAOCHOU)) {
			LoadhtmlFragment fragment = new LoadhtmlFragment();
			Bundle bundle = new Bundle();
			bundle.putString("htmlName", "renwubaochou.html");
			fragment.setArguments(bundle);
			getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragments, fragment).commit();
			currentFragment = Constant.RENWUBAOCHOU;
		}
		// 内政等级表----------------------------->
		else if (v == tv_renwu_2 && !currentFragment.equals(Constant.NEIZHENGDENGJI)) {
			NeizhengDengjiFragment fragment = new NeizhengDengjiFragment();			
			getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragments, fragment).commit();
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
		// 每周活动----------------------------->
		else if (v == tv_qita_2 && !currentFragment.equals(Constant.MEIZHOUHUODONG)) {
			MeizhouhuodongFragment fragment = new MeizhouhuodongFragment();
			getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragments, fragment).commit();
			currentFragment = Constant.MEIZHOUHUODONG;
		}
		// 骗子名单----------------------------->
		else if (v == tv_qita_3 && !currentFragment.equals(Constant.PIANZIMINGDAN)) {
			PianzimingdanFragment fragment = new PianzimingdanFragment();
			getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragments, fragment).commit();
			currentFragment = Constant.PIANZIMINGDAN;
		}
		// 副将技能和属性----------------------------->
		else if (v == tv_fujiang_1 && !currentFragment.equals(Constant.FUJIANG)) {
			LoadtxtFragment fragment = new LoadtxtFragment();
			Bundle bundle = new Bundle();
			bundle.putString("txtName", "fujiang.txt");
			fragment.setArguments(bundle);
			getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragments, fragment).commit();
			currentFragment = Constant.FUJIANG;
		}
		menu.toggle(true);
	}

	@Override
	public void onResume() {
		super.onResume();
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
		SpotManager.getInstance(this).unregisterSceenReceiver();
		super.onDestroy();
	}
	
	private void loadPaomadengMessage(){
		String question = Constant.tulingQuestion3;
		AjaxParams params = APIUtils.getTulingParams(question);
		finalHttp.get(Constant.tulingAPI, params, new AjaxCallBack<Object>() {

			@Override
			public void onStart() {
				super.onStart();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				
				try {
					JSONObject jsonObject = new JSONObject(t.toString());
					String text = (String) jsonObject.get("text");
					//text = text.replaceAll("\\$", "\"");
					if (text.equals("无内容")) {
						tv_pamadeng.setVisibility(View.GONE);
					}
					else {
						tv_pamadeng.setText(text);
						tv_pamadeng.setVisibility(View.VISIBLE);
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
					tv_pamadeng.setVisibility(View.GONE);
				}

			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				tv_pamadeng.setVisibility(View.GONE);
			}

		});
	}

}
