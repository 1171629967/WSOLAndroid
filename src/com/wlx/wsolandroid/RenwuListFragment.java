package com.wlx.wsolandroid;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.adapter.NeizhengdengjiListAdapter;
import com.wlx.wsolandroid.adapter.RenwuListAdapter;
import com.wlx.wsolandroid.adapter.YijianyilanAdapter;
import com.wlx.wsolandroid.constant.Constant;
import com.wlx.wsolandroid.model.Pianzi;
import com.wlx.wsolandroid.model.Renwu;
import com.wlx.wsolandroid.model.Yijian;
import com.wlx.wsolandroid.widget.MyActionBar;

public class RenwuListFragment extends BaseFragment implements OnRefreshListener,OnItemClickListener{

	private ListView lv;
	private RenwuListAdapter adapter;
	private List<Renwu> renwus = new ArrayList<Renwu>();

	private SwipeRefreshLayout swipeRefreshLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_yijianyilan, null);
		this.initActionBar(view);
		this.initView(view);
		return view;
	}

	private void initView(View view) {
		lv = (ListView) view.findViewById(R.id.lv);
		lv.setOnItemClickListener(this);
		adapter = new RenwuListAdapter(getActivity(), renwus);
		lv.setAdapter(adapter);

		swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
		// 顶部刷新的样式
		swipeRefreshLayout.setColorScheme(android.R.color.holo_red_light, android.R.color.holo_green_light, android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setRefreshing(true);
		loadData();
	}

	private void initActionBar(View view) {
		MyActionBar actionBar = new MyActionBar(getActivity());
		actionBar.setTitle("任务列表");
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
		BmobQuery<Renwu> bmobQuery = new BmobQuery<Renwu>();
		bmobQuery.setLimit(1000);
		bmobQuery.order("renwuId");
		bmobQuery.findObjects(getActivity(), new FindListener<Renwu>() {

			@Override
			public void onSuccess(List<Renwu> lists) {
				swipeRefreshLayout.setRefreshing(false);
				renwus.addAll(lists);
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
		MobclickAgent.onPageStart("任务列表"); // 统计页面
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("任务列表");
	}
	
	@Override
	public void onRefresh() {
		renwus.clear();
		this.loadData();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Intent intent = new Intent(getActivity(),RenwuDetailActivity.class);
		intent.putExtra("renwuId", renwus.get(position).getRenwuId());
		startActivity(intent);
	}

}
