package com.wlx.wsolandroid;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.ResetPasswordListener;

import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.model.Information;
import com.wlx.wsolandroid.model.RenwuDetail;
import com.wlx.wsolandroid.model.Weilixishu;
import com.wlx.wsolandroid.utils.Utils;
import com.wlx.wsolandroid.widget.MyActionBar;

/**
 * 任务详情
 * 
 * @author wanglixin
 */
public class RenwuDetailActivity extends Activity implements OnRefreshListener {
	private TextView tv_renwuName, tv_renwuLevel, tv_renwuPeopleCount,
			tv_renwuJiaofu, tv_renwuMap, tv_renwuZhifa, tv_renwuGetDaoju,
			tv_shangyePoint, tv_liutongPoint, tv_jishuPoint, tv_junshiPoint,
			tv_zhianPoint, tv_junfeiPoint, tv_renwuBeizhu;

	private LinearLayout ll_renwuBeizhu;
	private int renwuId;
	private SwipeRefreshLayout swipeRefreshLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_renwu_detail);

		renwuId = getIntent().getIntExtra("renwuId", 1);

		this.initActionBar();
		this.initView();
		this.loadData();
		
		
		
		
		
	}

	private void initActionBar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle("任务详情");
		actionBar.setLeftEnable(true);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	private void initView() {
		Utils.setAppBackgroundColor(RenwuDetailActivity.this,
				findViewById(R.id.linearLayout));

		swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
		// 顶部刷新的样式
		swipeRefreshLayout.setColorScheme(android.R.color.holo_red_light,
				android.R.color.holo_green_light,
				android.R.color.holo_blue_bright,
				android.R.color.holo_orange_light);
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setRefreshing(true);

		tv_renwuName = (TextView) findViewById(R.id.tv_renwuName);
		tv_renwuLevel = (TextView) findViewById(R.id.tv_renwuLevel);
		tv_renwuPeopleCount = (TextView) findViewById(R.id.tv_renwuPeopleCount);
		tv_renwuJiaofu = (TextView) findViewById(R.id.tv_renwuJiaofu);
		tv_renwuMap = (TextView) findViewById(R.id.tv_renwuMap);
		tv_renwuZhifa = (TextView) findViewById(R.id.tv_renwuZhifa);
		tv_renwuGetDaoju = (TextView) findViewById(R.id.tv_renwuGetDaoju);
		tv_shangyePoint = (TextView) findViewById(R.id.tv_shangyePoint);
		tv_liutongPoint = (TextView) findViewById(R.id.tv_liutongPoint);
		tv_jishuPoint = (TextView) findViewById(R.id.tv_jishuPoint);
		tv_junshiPoint = (TextView) findViewById(R.id.tv_junshiPoint);
		tv_zhianPoint = (TextView) findViewById(R.id.tv_zhianPoint);
		tv_junfeiPoint = (TextView) findViewById(R.id.tv_junfeiPoint);
		
		tv_renwuBeizhu = (TextView) findViewById(R.id.tv_renwuBeizhu);
		ll_renwuBeizhu = (LinearLayout) findViewById(R.id.ll_renwuBeizhu);

	}

	private void loadData() {
		BmobQuery<RenwuDetail> bmobQuery = new BmobQuery<RenwuDetail>();
		bmobQuery.addWhereEqualTo("renwuId", renwuId);
		bmobQuery.findObjects(this, new FindListener<RenwuDetail>() {

			@Override
			public void onSuccess(List<RenwuDetail> renwuDetails) {
				swipeRefreshLayout.setRefreshing(false);
				setData(renwuDetails.get(0));
			}

			@Override
			public void onError(int arg0, String arg1) {
				swipeRefreshLayout.setRefreshing(false);
			}
		});
	}

	/** 设置各个控件的数值 */
	private void setData(RenwuDetail renwuDetail) {
		tv_renwuName.setText(renwuDetail.getRenwuName());
		tv_renwuLevel.setText(renwuDetail.getRenwuLevel());
		tv_renwuJiaofu.setText(renwuDetail.getRenwuJiaofu());
		tv_renwuGetDaoju.setText(renwuDetail.getRenwuGetDaoju());
		tv_renwuMap.setText(renwuDetail.getRenwuMap());
		tv_renwuPeopleCount.setText(renwuDetail.getRenwuPeopleCount() + "");
		tv_renwuZhifa.setText(renwuDetail.getRenwuZhifa());
		
		String renwuBeizhu = renwuDetail.getRenwuBeizhu();
		if (TextUtils.isEmpty(renwuBeizhu)) {
			ll_renwuBeizhu.setVisibility(View.GONE);
		}
		else {
			tv_renwuBeizhu.setText(renwuBeizhu);
			ll_renwuBeizhu.setVisibility(View.VISIBLE);
		}

		tv_shangyePoint.setText(renwuDetail.getShangyePoint() + "");
		tv_liutongPoint.setText(renwuDetail.getLiutongPoint() + "");
		tv_jishuPoint.setText(renwuDetail.getJishuPoint() + "");
		tv_junshiPoint.setText(renwuDetail.getJunshiPoint() + "");
		tv_zhianPoint.setText(renwuDetail.getZhianPoint() + "");
		tv_junfeiPoint.setText(renwuDetail.getJunfeiPoint() + "");
	}

	@Override
	public void onRefresh() {
		loadData();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("任务详情"); // 统计页面
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("任务详情");
	}

}
