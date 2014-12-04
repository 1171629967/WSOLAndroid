package com.wlx.wsolandroid;

import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.constant.Constant;
import com.wlx.wsolandroid.utils.APIUtils;
import com.wlx.wsolandroid.widget.MyActionBar;
import com.wlx.wsolandroid.widget.ProgressWheel;

public class MeizhouhuodongFragment extends BaseFragment implements OnRefreshListener {
	private SwipeRefreshLayout swipeRefreshLayout;
	private TextView tv_content;
	private FinalHttp finalHttp;
	// private ProgressWheel progressBar;
	boolean running;
	int progress = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		finalHttp = new FinalHttp();
	}

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_meizhouhuodong, null);
		swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
		// 顶部刷新的样式
		swipeRefreshLayout.setColorScheme(android.R.color.holo_red_light, android.R.color.holo_green_light, android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
		swipeRefreshLayout.setOnRefreshListener(this);
		tv_content = (TextView) view.findViewById(R.id.tv_content);
		// progressBar = (ProgressWheel) view.findViewById(R.id.progressbar);
		swipeRefreshLayout.setRefreshing(true);
		loadData();
		this.initActionBar(view);
		return view;
	}

	private void initActionBar(View view) {
		MyActionBar actionBar = new MyActionBar(getActivity());
		actionBar.setTitle("每周活动");
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

		MobclickAgent.onPageStart("每周活动");

	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("每周活动");
	}

	@Override
	public void onRefresh() {
		this.loadData();
	}

	private void loadData() {
		AjaxParams params = APIUtils.getTulingParams(Constant.tulingQuestion1);
		finalHttp.get(Constant.tulingAPI, params, new AjaxCallBack<Object>() {

			@Override
			public void onStart() {
				super.onStart();

				// progressBar.setVisibility(View.VISIBLE);
				// startLoading(progressBar);
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				swipeRefreshLayout.setRefreshing(false);
				// progressBar.setVisibility(View.GONE);
				try {
					JSONObject jsonObject = new JSONObject(t.toString());
					String text = (String) jsonObject.get("text");
					text = text.replaceAll("\\$", "\n");
					tv_content.setText(text);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				swipeRefreshLayout.setRefreshing(false);
				// progressBar.setVisibility(View.GONE);
			}

		});
	}
}
