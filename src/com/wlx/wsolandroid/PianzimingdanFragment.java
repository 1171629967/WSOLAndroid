package com.wlx.wsolandroid;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import net.youmi.android.spot.SpotManager;

import org.json.JSONException;
import org.json.JSONObject;



import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.adapter.PianzimingdanAdapter;
import com.wlx.wsolandroid.constant.Constant;
import com.wlx.wsolandroid.model.Pianzi;
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
public class PianzimingdanFragment extends BaseFragment implements OnRefreshListener{
	private ListView listView;
	private PianzimingdanAdapter adapter;
	private View v_head;
	private EditText et_search;
	private List<Pianzi> allPianzis = new ArrayList<Pianzi>();;
	private List<Pianzi> searchResultPianzis = new ArrayList<Pianzi>();
	private SwipeRefreshLayout swipeRefreshLayout;
	private FinalHttp finalHttp;
	
	private int totalPianziPage;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		finalHttp = new FinalHttp();
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
		loadData(0);
		
		// 添加多盟广告
		//this.addAd(view);
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

	
	
	private void loadData(final int page) {
		String question = Constant.tulingQuestion2+page;
		AjaxParams params = APIUtils.getTulingParams(question);
		finalHttp.get(Constant.tulingAPI, params, new AjaxCallBack<Object>() {

			@Override
			public void onStart() {
				super.onStart();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				swipeRefreshLayout.setRefreshing(false);
				try {
					JSONObject jsonObject = new JSONObject(t.toString());
					String text = (String) jsonObject.get("text");
					text = text.replaceAll("\\$", "\"");
					//System.out.println("骗子名单json---------》"+page+"-------->"+text);
					ArrayList<Pianzi> pianzis = JsonUtils.getPianzisFromJson(text);
					allPianzis.addAll(pianzis);
					adapter.notifyDataSetChanged();		
					
					if (page == 0) {
						getTotalPages(text);
						for (int i = 1; i < totalPianziPage; i++) {
							loadData(i);
						}
					}
					
					
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				swipeRefreshLayout.setRefreshing(false);
			}

		});
	
	}
	
	
	
	private int getTotalPages(String text){
		try {
			JSONObject jb = new JSONObject(text);
			totalPianziPage = jb.optInt("totalPianziPage");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return totalPianziPage;
	}
	
	
	private void addAd() {
		SpotManager.getInstance(getActivity()).setSpotOrientation(SpotManager.ORIENTATION_PORTRAIT);
		SpotManager.getInstance(getActivity()).showSpotAds(getActivity());
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		this.addAd();
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
		this.loadData(0);
	}

}
