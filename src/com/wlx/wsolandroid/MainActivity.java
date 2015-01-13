package com.wlx.wsolandroid;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.BaseFragment.menuClicklistener;
import com.wlx.wsolandroid.constant.Constant;
import com.wlx.wsolandroid.model.Information;
import com.wlx.wsolandroid.model.Pianzi;
import com.wlx.wsolandroid.model.Weilixishu;
import com.wlx.wsolandroid.utils.APIUtils;
import com.wlx.wsolandroid.utils.JsonUtils;
import com.wlx.wsolandroid.widget.MarqueeTextView;

public class MainActivity extends FragmentActivity implements OnClickListener, menuClicklistener {
	public SlidingMenu menu;
	private TextView tv_wuqi_1, tv_renwu_1, tv_renwu_2, tv_qita_2, tv_qita_3, tv_qita_4, tv_qita_5, tv_fujiang_1;
	private String currentFragment;
	/** 公告栏，跑马灯效果 */
	private MarqueeTextView tv_pamadeng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 后台配置
		Bmob.initialize(this, "8763a00a263ee5064e8a55be05f72f3a");

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
		tv_qita_4 = (TextView) menu.findViewById(R.id.tv_qita_4);
		tv_qita_5 = (TextView) menu.findViewById(R.id.tv_qita_5);
		tv_fujiang_1 = (TextView) menu.findViewById(R.id.tv_fujiang_1);
		tv_wuqi_1.setOnClickListener(this);
		// tv_wuqi_2.setOnClickListener(this);
		tv_renwu_1.setOnClickListener(this);
		tv_renwu_2.setOnClickListener(this);
		// tv_qita_1.setOnClickListener(this);
		tv_qita_2.setOnClickListener(this);
		tv_qita_3.setOnClickListener(this);
		tv_qita_4.setOnClickListener(this);
		tv_qita_5.setOnClickListener(this);
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
		// 意见和建议----------------------------->
		else if (v == tv_qita_4 && !currentFragment.equals(Constant.YIJIAN)) {
			YijianFragment fragment = new YijianFragment();
			getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragments, fragment).commit();
			currentFragment = Constant.YIJIAN;
		}
		// 玩家意见一览----------------------------->
		else if (v == tv_qita_5 && !currentFragment.equals(Constant.YIJIAN)) {
			YijianyilanFragment fragment = new YijianyilanFragment();
			getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragments, fragment).commit();
			currentFragment = Constant.YIJIANYILAN;
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
		super.onDestroy();
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

}
