package com.wlx.wsolandroid;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import cn.domob.android.ads.AdEventListener;
import cn.domob.android.ads.AdView;
import cn.domob.android.ads.AdManager.ErrorCode;

import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.adapter.WeaponListAdapter;
import com.wlx.wsolandroid.constant.Constant;
import com.wlx.wsolandroid.model.Weapon;
import com.wlx.wsolandroid.model.Xishu;
import com.wlx.wsolandroid.widget.MyActionBar;

public class WeaponJinpaiFragment extends BaseFragment {
	// 多盟广告栏容器
	private RelativeLayout adContainer;
	private AdView adView;
	private ListView lv1;
	private WeaponListAdapter adapter;
	private View v_head;
	private EditText et_search;
	private final List<Weapon> allWeapons = new ArrayList<Weapon>();
	private final List<Weapon> searchResultWeapons = new ArrayList<Weapon>();
	private int allWeaponCount;

	private final List<Xishu> xishus = new ArrayList<Xishu>();
	private boolean isLoadXishuFinish = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_weapon, null);
		this.initActionBar(view);
		this.initView(view);
		this.loadXishu();
		return view;
	}

	private void initView(View view) {
		v_head = LayoutInflater.from(getActivity()).inflate(R.layout.weapon_search, null);
		et_search = (EditText) v_head.findViewById(R.id.et_search);
		et_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				searchResultWeapons.clear();
				String searchString = s.toString().trim();

				// 当搜索关键字为空时，显示全部武器
				if (TextUtils.isEmpty(searchString)) {
					adapter.setData(allWeapons);
				} else {
					for (int i = 0; i < allWeaponCount; i++) {
						if (allWeapons.get(i).getName().contains(searchString)) {
							searchResultWeapons.add(allWeapons.get(i));
						}
					}
					adapter.setData(searchResultWeapons);
				}
				adapter.notifyDataSetChanged();

			}
		});

		lv1 = (ListView) view.findViewById(R.id.lv1);

		lv1.addHeaderView(v_head);

		String[] names = Constant.weaponNameR1.split("\\,");
		String[] gs = Constant.weaponDataR1G.split("\\,");
		String[] ps = Constant.weaponDataR1P.split("\\,");
		String[] fs = Constant.weaponDataR1F.split("\\,");
		String[] ts = Constant.weaponDataR1T.split("\\,");
		String[] ws = Constant.weaponDataR1W.split("\\,");

		allWeaponCount = names.length;
		for (int i = 0; i < allWeaponCount; i++) {
			Weapon weapon = new Weapon();
			weapon.setName(names[i]);
			weapon.setG(Integer.parseInt(gs[i]));
			weapon.setP(Integer.parseInt(ps[i]));
			weapon.setF(Integer.parseInt(fs[i]));
			weapon.setT(Integer.parseInt(ts[i]));
			weapon.setW(Integer.parseInt(ws[i]));
			allWeapons.add(weapon);
		}

		adapter = new WeaponListAdapter(getActivity(), allWeapons);

		lv1.setAdapter(adapter);

		// 添加多盟广告
		this.addAd(view);
	}

	private void initActionBar(View view) {
		MyActionBar actionBar = new MyActionBar(getActivity());
		actionBar.setTitle("金牌武器上升值");
		actionBar.setLeftEnable(true);
		actionBar.setLeftText("菜单");
		actionBar.setLeftClickListenner(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mClicklistener.menuClick();
			}
		});
		RelativeLayout actionbar = (RelativeLayout) view.findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	/** 异步线程解析武器威力系数 */
	private void loadXishu() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				String[] N1s = Constant.xishuN1.split("\\,");
				String[] N2s = Constant.xishuN2.split("\\,");
				String[] N3s = Constant.xishuN3.split("\\,");
				String[] N4s = Constant.xishuN4.split("\\,");
				String[] N5s = Constant.xishuN5.split("\\,");
				String[] N6s = Constant.xishuN6.split("\\,");
				String[] E6s = Constant.xishuE6.split("\\,");
				String[] E7s = Constant.xishuE7.split("\\,");
				String[] E8s = Constant.xishuE8.split("\\,");
				String[] E9s = Constant.xishuE9.split("\\,");

				String[] Ds = Constant.xishuD.split("\\,");
				String[] JAs = Constant.xishuJA.split("\\,");
				String[] JCs = Constant.xishuJC.split("\\,");
				String[] C2s = Constant.xishuC2.split("\\,");
				String[] C3s = Constant.xishuC3.split("\\,");
				String[] C4s = Constant.xishuC4.split("\\,");
				String[] C5s = Constant.xishuC5.split("\\,");

				int count = N1s.length;
				for (int i = 0; i < count; i++) {
					Xishu xishu = new Xishu();

					xishu.setN1(N1s[i]);
					xishu.setN2(N2s[i]);
					xishu.setN3(N3s[i]);
					xishu.setN4(N4s[i]);
					xishu.setN5(N5s[i]);
					xishu.setN6(N6s[i]);
					xishu.setE6(E6s[i]);
					xishu.setE7(E7s[i]);
					xishu.setE8(E8s[i]);
					xishu.setE9(E9s[i]);

					xishu.setD(Ds[i]);
					xishu.setJA(JAs[i]);
					xishu.setJC(JCs[i]);
					xishu.setC2(C2s[i]);
					xishu.setC3(C3s[i]);
					xishu.setC4(C4s[i]);
					xishu.setC5(C5s[i]);

					xishus.add(xishu);
				}
				isLoadXishuFinish = true;
			}
		}).start();
	}

	private void addAd(View view) {
		adContainer = (RelativeLayout) view.findViewById(R.id.adContainer);
		adView = new AdView(getActivity(), "56OJzt4YuN/GvEjtN6", "16TLmhwaAp6uWNUOjw4wCyFk");
		adView.setAdEventListener(new AdEventListener() {

			@Override
			public void onLeaveApplication(AdView arg0) {
			}

			@Override
			public void onEventAdReturned(AdView arg0) {
			}

			@Override
			public Context onAdRequiresCurrentContext() {
				return getActivity();
			}

			@Override
			public void onAdOverlayPresented(AdView arg0) {
			}

			@Override
			public void onAdOverlayDismissed(AdView arg0) {
			}

			@Override
			public void onAdFailed(AdView arg0, ErrorCode arg1) {
			}

			@Override
			public void onAdClicked(AdView arg0) {
			}
		});

		RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		layout.addRule(RelativeLayout.CENTER_HORIZONTAL);
		adView.setLayoutParams(layout);
		adContainer.addView(adView);
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("金牌武器上升值"); // 统计页面
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("金牌武器上升值");
	}

}
