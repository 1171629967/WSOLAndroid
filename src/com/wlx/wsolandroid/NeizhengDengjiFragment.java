package com.wlx.wsolandroid;

import java.util.ArrayList;
import java.util.List;


import android.R.integer;
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

import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.adapter.NeizhengdengjiListAdapter;
import com.wlx.wsolandroid.adapter.WeaponListAdapter;
import com.wlx.wsolandroid.constant.Constant;
import com.wlx.wsolandroid.model.Weapon;
import com.wlx.wsolandroid.model.Weilixishu;
import com.wlx.wsolandroid.widget.MyActionBar;

public class NeizhengDengjiFragment extends BaseFragment {
	private ListView lv;
	private NeizhengdengjiListAdapter adapter;

	private String[] totalCountArray;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_neizhengdengji, null);
		this.initActionBar(view);
		this.initView(view);
		return view;
	}

	private void initView(View view) {
		lv = (ListView) view.findViewById(R.id.lv);
		totalCountArray = Constant.renwudengjiTotalCountArray.split("\\,");
		adapter = new NeizhengdengjiListAdapter(getActivity(), totalCountArray);
		lv.setAdapter(adapter);
	}

	private void initActionBar(View view) {
		MyActionBar actionBar = new MyActionBar(getActivity());
		actionBar.setTitle("内政等级表");
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

	

	

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("内政等级表"); // 统计页面
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("内政等级表");
	}

}
