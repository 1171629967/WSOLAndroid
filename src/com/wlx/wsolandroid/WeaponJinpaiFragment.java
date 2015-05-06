package com.wlx.wsolandroid;

import java.util.ArrayList;
import java.util.List;



import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.adapter.WeaponListAdapter;
import com.wlx.wsolandroid.constant.Constant;
import com.wlx.wsolandroid.model.Pianzi;
import com.wlx.wsolandroid.model.WeaponJinpai;
import com.wlx.wsolandroid.model.Weilixishu;
import com.wlx.wsolandroid.utils.Utils;
import com.wlx.wsolandroid.widget.MyActionBar;

public class WeaponJinpaiFragment extends BaseFragment implements OnItemClickListener, OnRefreshListener{
	private ListView lv1;
	private WeaponListAdapter adapter;
	private View v_head;
	private EditText et_search;
	private List<WeaponJinpai> allWeapons = new ArrayList<WeaponJinpai>();
	private List<WeaponJinpai> searchResultWeapons = new ArrayList<WeaponJinpai>();

	private SwipeRefreshLayout swipeRefreshLayout;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_weapon, null);
		this.initActionBar(view);
		this.initView(view);
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
					int weaponCount = allWeapons.size();
					for (int i = 0; i < weaponCount; i++) {
						if (allWeapons.get(i).getName().contains(searchString) || allWeapons.get(i).getPinyin().contains(searchString.toLowerCase())) {
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

//		String[] names = Constant.weaponNameR1.split("\\,");
//		String[] pinyins = Constant.weaponPinyin.split("\\,");
//		String[] gs = Constant.weaponDataR1G.split("\\,");
//		String[] ps = Constant.weaponDataR1P.split("\\,");
//		String[] fs = Constant.weaponDataR1F.split("\\,");
//		String[] ts = Constant.weaponDataR1T.split("\\,");
//		String[] ws = Constant.weaponDataR1W.split("\\,");
//
//		allWeaponCount = names.length;
//		for (int i = 0; i < allWeaponCount; i++) {
//			Weapon weapon = new Weapon();
//			weapon.setName(names[i]);
//			weapon.setPinyin(pinyins[i]);
//			weapon.setG(Integer.parseInt(gs[i]));
//			weapon.setP(Integer.parseInt(ps[i]));
//			weapon.setF(Integer.parseInt(fs[i]));
//			weapon.setT(Integer.parseInt(ts[i]));
//			weapon.setW(Integer.parseInt(ws[i]));
//			allWeapons.add(weapon);
//		}

		adapter = new WeaponListAdapter(getActivity(), allWeapons);

		lv1.setAdapter(adapter);
		lv1.setOnItemClickListener(this);
		
		swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
		// 顶部刷新的样式
		swipeRefreshLayout.setColorScheme(android.R.color.holo_red_light, android.R.color.holo_green_light, android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setRefreshing(true);
		
		this.loadData();
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

	

	private void loadData() {
		BmobQuery<WeaponJinpai> bmobQuery = new BmobQuery<WeaponJinpai>();
		//bmobQuery.setLimit(1000);
		bmobQuery.order("weaponId");
		bmobQuery.findObjects(getActivity(), new FindListener<WeaponJinpai>() {

			@Override
			public void onSuccess(List<WeaponJinpai> weapons) {
				swipeRefreshLayout.setRefreshing(false);
				allWeapons.addAll(weapons);
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onError(int arg0, String arg1) {
				swipeRefreshLayout.setRefreshing(false);
			}
		});

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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Intent intent = new Intent(getActivity(),WeaponXishuActivity.class);
		intent.putExtra("weaponName", adapter.getDate().get(position-1).getName());
		startActivity(intent);
	}

	@Override
	public void onRefresh() {
		allWeapons.clear();
		searchResultWeapons.clear();
		et_search.setText("");
		this.loadData();	
	}

}
