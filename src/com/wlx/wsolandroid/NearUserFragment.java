package com.wlx.wsolandroid;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.Toast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobGeoPoint;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.DownloadListener;
import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.adapter.MusicListAdapter;
import com.wlx.wsolandroid.adapter.NearUserAdapter;
import com.wlx.wsolandroid.adapter.WeaponListAdapter;
import com.wlx.wsolandroid.constant.Constant;
import com.wlx.wsolandroid.model.Music;
import com.wlx.wsolandroid.model.User;
import com.wlx.wsolandroid.model.WeaponJinpai;
import com.wlx.wsolandroid.model.Weilixishu;
import com.wlx.wsolandroid.model.Yijian;
import com.wlx.wsolandroid.utils.Utils;
import com.wlx.wsolandroid.widget.MyActionBar;
import com.wlx.wsolandroid.widget.NumberProgressBar;

/**
 * 附近的用户
 */
public class NearUserFragment extends BaseFragment implements
		OnItemClickListener, OnRefreshListener {
	private ListView lv;
	private NearUserAdapter adapter;

	private List<User> users = new ArrayList<User>();

	private SwipeRefreshLayout swipeRefreshLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_near_user, null);
		this.initActionBar(view);
		this.initView(view);
		return view;
	}

	private void initView(View view) {
		lv = (ListView) view.findViewById(R.id.lv);
		adapter = new NearUserAdapter(getActivity(), users);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);

		swipeRefreshLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.swipeRefreshLayout);
		// 顶部刷新的样式
		swipeRefreshLayout.setColorScheme(android.R.color.holo_red_light,
				android.R.color.holo_green_light,
				android.R.color.holo_blue_bright,
				android.R.color.holo_orange_light);
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setRefreshing(true);
		loadData();

	}

	private void initActionBar(View view) {
		MyActionBar actionBar = new MyActionBar(getActivity());
		actionBar.setTitle("附近的玩家");
		actionBar.setLeftEnable(true);
		actionBar.setLeftText("菜单");
		actionBar.setLeftClickListenner(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mClicklistener.menuClick();
			}
		});
		RelativeLayout actionbar = (RelativeLayout) view
				.findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	private void loadData() {
		BmobQuery<User> bmobQuery = new BmobQuery<User>();
		//距离10000千米以内玩家
		bmobQuery.addWhereWithinKilometers("lastGeoPoint",
				User.getCurrentUser(getActivity(), User.class)
						.getLastGeoPoint(), 10000);
		bmobQuery.addWhereNotEqualTo("username", User.getCurrentUser(getActivity(), User.class).getUsername());
		bmobQuery.setLimit(10000); // 获取最接近用户地点的10000条数据
		bmobQuery.findObjects(getActivity(), new FindListener<User>() {
			@Override
			public void onSuccess(List<User> queryUsers) {
				swipeRefreshLayout.setRefreshing(false);
				users.addAll(queryUsers);
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onError(int code, String msg) {
				swipeRefreshLayout.setRefreshing(false);
				Toast.makeText(getActivity(), "加载失败：" + msg, Toast.LENGTH_LONG)
						.show();
			}
		});

	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("附近的玩家"); // 统计页面
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("附近的玩家");
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {

		Intent intent = new Intent(getActivity(),
				CompletePersonInfoActivity.class);
		intent.putExtra("fromWhere", CompletePersonInfoActivity.FROM_PERSON_INFO);
		intent.putExtra("user", users.get(position));
		startActivity(intent);

	}

	@Override
	public void onRefresh() {
		users.clear();
		this.loadData();
	}

}
