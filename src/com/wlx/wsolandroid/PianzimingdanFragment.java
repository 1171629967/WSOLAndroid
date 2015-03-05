package com.wlx.wsolandroid;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.adapter.PianzimingdanAdapter;
import com.wlx.wsolandroid.constant.Constant;
import com.wlx.wsolandroid.model.Pianzi;
import com.wlx.wsolandroid.model.Weilixishu;
import com.wlx.wsolandroid.utils.APIUtils;
import com.wlx.wsolandroid.utils.JsonUtils;
import com.wlx.wsolandroid.widget.MyActionBar;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

/**
 * 骗子名单页面 PianzimingdanFragment
 * 
 * @author wanglixin
 * 
 */
public class PianzimingdanFragment extends BaseFragment implements OnRefreshListener {
	private ListView listView;
	private PianzimingdanAdapter adapter;
	private View v_head;
	private EditText et_search;
	private List<Pianzi> allPianzis = new ArrayList<Pianzi>();
	private List<Pianzi> searchResultPianzis = new ArrayList<Pianzi>();
	private SwipeRefreshLayout swipeRefreshLayout;

	private int totalPianziPage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_pianzimingdan, null);
		this.initActionBar(view);
		this.initView(view);
		return view;
	}

	private void initView(View view) {
		v_head = LayoutInflater.from(getActivity()).inflate(R.layout.weapon_search, null);
		et_search = (EditText) v_head.findViewById(R.id.et_search);
		et_search.setHint("请输入关键字搜索骗子姓名");
		et_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				searchResultPianzis.clear();
				String searchString = s.toString().trim();

				// 当搜索关键字为空时，显示全部武器
				if (TextUtils.isEmpty(searchString)) {
					adapter.setData(allPianzis);
				} else {
					for (int i = 0; i < allPianzis.size(); i++) {
						if (allPianzis.get(i).getName().contains(searchString)) {
							searchResultPianzis.add(allPianzis.get(i));
						}
					}
					adapter.setData(searchResultPianzis);
				}
				adapter.notifyDataSetChanged();
			}
		});

		listView = (ListView) view.findViewById(R.id.listView);
		listView.addHeaderView(v_head);
		adapter = new PianzimingdanAdapter(getActivity(), allPianzis);
		listView.setAdapter(adapter);

		swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
		// 顶部刷新的样式
		swipeRefreshLayout.setColorScheme(android.R.color.holo_red_light, android.R.color.holo_green_light, android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setRefreshing(true);
		loadData();

	}

	private void initActionBar(View view) {
		MyActionBar actionBar = new MyActionBar(getActivity());
		actionBar.setTitle("骗子名单");
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
		BmobQuery<Pianzi> bmobQuery = new BmobQuery<Pianzi>();
		bmobQuery.setLimit(1000);
		bmobQuery.order("pianziId");
		bmobQuery.findObjects(getActivity(), new FindListener<Pianzi>() {

			@Override
			public void onSuccess(List<Pianzi> pianzis) {
				swipeRefreshLayout.setRefreshing(false);
				allPianzis.addAll(pianzis);
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onError(int arg0, String arg1) {
				swipeRefreshLayout.setRefreshing(false);
			}
		});

	}

	private int getTotalPages(String text) {
		try {
			JSONObject jb = new JSONObject(text);
			totalPianziPage = jb.optInt("totalPianziPage");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return totalPianziPage;
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("骗子名单"); // 统计页面
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("骗子名单");
	}

	@Override
	public void onRefresh() {
		allPianzis.clear();
		this.loadData();
	}

}
