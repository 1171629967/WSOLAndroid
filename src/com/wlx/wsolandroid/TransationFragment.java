package com.wlx.wsolandroid;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.adapter.PianzimingdanAdapter;
import com.wlx.wsolandroid.adapter.TransationAdapter;
import com.wlx.wsolandroid.model.Information;
import com.wlx.wsolandroid.model.Pianzi;
import com.wlx.wsolandroid.model.Transation;
import com.wlx.wsolandroid.widget.MyActionBar;

public class TransationFragment extends BaseFragment implements OnRefreshListener {
	private SwipeRefreshLayout swipeRefreshLayout;
	private TextView tv_headContent;
	// private ProgressWheel progressBar;
	boolean running;
	int progress = 0;
	private View v_head;
	private List<Transation> transations = new ArrayList<Transation>();
	private TransationAdapter adapter;
	private ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
	}

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_transation, null);
		this.initActionBar(view);
		this.initView(view);		
		return view;
	}

	
	private void initView(View view){
		swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
		// 顶部刷新的样式
		swipeRefreshLayout.setColorScheme(android.R.color.holo_red_light, android.R.color.holo_green_light, android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
		swipeRefreshLayout.setOnRefreshListener(this);		
		// progressBar = (ProgressWheel) view.findViewById(R.id.progressbar);
		swipeRefreshLayout.setRefreshing(true);
		
		v_head = LayoutInflater.from(getActivity()).inflate(R.layout.transation_header, null);
		tv_headContent = (TextView) v_head.findViewById(R.id.tv_headContent);
		listView = (ListView) view.findViewById(R.id.listView);
		listView.addHeaderView(v_head);
		adapter = new TransationAdapter(getActivity(), transations);
		listView.setAdapter(adapter);
		loadDataHeader();
	}
	
	private void initActionBar(View view) {
		MyActionBar actionBar = new MyActionBar(getActivity());
		actionBar.setTitle("吧主担保交易");
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

		MobclickAgent.onPageStart("吧主担保交易");

	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("吧主担保交易");
	}

	@Override
	public void onRefresh() {
		this.loadDataHeader();
	}

	private void loadDataHeader() {
		BmobQuery<Information> bmobQuery = new BmobQuery<Information>();
    	bmobQuery.addWhereEqualTo("type", "transation_android");
    	bmobQuery.findObjects(getActivity(), new FindListener<Information>() {

			@Override
			public void onSuccess(List<Information> infos) {
				
				Information info = infos.get(0);  
				String des = info.getDes();
				tv_headContent.setText(des.replace("$", "\n"));
				loadData();
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				swipeRefreshLayout.setRefreshing(false);
			}
		});
	}
	
	
	
	private void loadData(){
		BmobQuery<Transation> bmobQuery = new BmobQuery<Transation>();
		bmobQuery.setLimit(1000);
		bmobQuery.order("-createdAt");
		bmobQuery.findObjects(getActivity(), new FindListener<Transation>() {

			@Override
			public void onSuccess(List<Transation> datas) {
				swipeRefreshLayout.setRefreshing(false);
				transations.clear();
				transations.addAll(datas);
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onError(int arg0, String arg1) {
				swipeRefreshLayout.setRefreshing(false);
			}
		});
	}
	
}
