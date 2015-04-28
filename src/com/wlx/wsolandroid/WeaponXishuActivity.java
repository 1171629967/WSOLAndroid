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
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;

import com.wlx.wsolandroid.model.Information;
import com.wlx.wsolandroid.model.Weilixishu;
import com.wlx.wsolandroid.utils.Utils;
import com.wlx.wsolandroid.widget.MyActionBar;

/**
 * 武器威力系数
 * 
 * @author wanglixin
 */
public class WeaponXishuActivity extends Activity implements OnRefreshListener, OnClickListener {
	private TextView tv_N1, tv_N2, tv_N3, tv_N4, tv_N5, tv_N6, tv_E6, tv_E7, tv_E8, tv_E9, tv_D, tv_JA, tv_JC, tv_C2, tv_C3, tv_C4, tv_C5, tv_tu, tv_dun, tv_sui, tv_zhen, tv_wei,
			tv_ba, tv_puwuAndZhenwu, tv_pumo, tv_zhenmo;

	// private Weilixishu weilixishu;
	private String weaponName;
	private SwipeRefreshLayout swipeRefreshLayout;
	private TextView tv_weaponName, tv_explain, tv_explainClick, tv_beizhu;
	private LinearLayout ll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weapon_xishu);

		weaponName = getIntent().getStringExtra("weaponName");

		this.initActionBar();
		this.initView();
		this.loadData();
	}

	private void initActionBar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle("武器威力系数");
		actionBar.setLeftEnable(true);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	private void initView() {
		Utils.setAppBackgroundColor(WeaponXishuActivity.this, 1, findViewById(R.id.linearLayout));
		
		swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
		// 顶部刷新的样式
		swipeRefreshLayout.setColorScheme(android.R.color.holo_red_light, android.R.color.holo_green_light, android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setRefreshing(true);

		ll = (LinearLayout) findViewById(R.id.ll);
		tv_beizhu = (TextView) findViewById(R.id.tv_beizhu);
		tv_explain = (TextView) findViewById(R.id.tv_explain);
		tv_explainClick = (TextView) findViewById(R.id.tv_explainClick);
		tv_explainClick.setOnClickListener(this);

		tv_weaponName = (TextView) findViewById(R.id.tv_weaponName);
		tv_weaponName.setText(weaponName);

		tv_N1 = (TextView) findViewById(R.id.tv_N1);
		tv_N2 = (TextView) findViewById(R.id.tv_N2);
		tv_N3 = (TextView) findViewById(R.id.tv_N3);
		tv_N4 = (TextView) findViewById(R.id.tv_N4);
		tv_N5 = (TextView) findViewById(R.id.tv_N5);
		tv_N6 = (TextView) findViewById(R.id.tv_N6);
		tv_E6 = (TextView) findViewById(R.id.tv_E6);
		tv_E7 = (TextView) findViewById(R.id.tv_E7);
		tv_E8 = (TextView) findViewById(R.id.tv_E8);
		tv_E9 = (TextView) findViewById(R.id.tv_E9);

		tv_D = (TextView) findViewById(R.id.tv_D);
		tv_JA = (TextView) findViewById(R.id.tv_JA);
		tv_JC = (TextView) findViewById(R.id.tv_JC);
		tv_C2 = (TextView) findViewById(R.id.tv_C2);
		tv_C3 = (TextView) findViewById(R.id.tv_C3);
		tv_C4 = (TextView) findViewById(R.id.tv_C4);
		tv_C5 = (TextView) findViewById(R.id.tv_C5);

		tv_tu = (TextView) findViewById(R.id.tv_tu);
		tv_dun = (TextView) findViewById(R.id.tv_dun);
		tv_sui = (TextView) findViewById(R.id.tv_sui);
		tv_zhen = (TextView) findViewById(R.id.tv_zhen);
		tv_wei = (TextView) findViewById(R.id.tv_wei);
		tv_ba = (TextView) findViewById(R.id.tv_ba);

		tv_puwuAndZhenwu = (TextView) findViewById(R.id.tv_puwuAndZhenwu);
		tv_pumo = (TextView) findViewById(R.id.tv_pumo);
		tv_zhenmo = (TextView) findViewById(R.id.tv_zhenmo);
	}

	private void loadData() {
		BmobQuery<Weilixishu> bmobQuery = new BmobQuery<Weilixishu>();
		bmobQuery.addWhereEqualTo("weaponName", weaponName);
		bmobQuery.findObjects(this, new FindListener<Weilixishu>() {

			@Override
			public void onSuccess(List<Weilixishu> weilixishus) {
				swipeRefreshLayout.setRefreshing(false);
				setData(weilixishus.get(0));
			}

			@Override
			public void onError(int arg0, String arg1) {
				swipeRefreshLayout.setRefreshing(false);
			}
		});
	}

	private void loadExplain() {
		tv_explain.setVisibility(View.VISIBLE);
		BmobQuery<Information> bmobQuery = new BmobQuery<Information>();
		bmobQuery.addWhereEqualTo("type", "weilixishu_explain");
		bmobQuery.findObjects(this, new FindListener<Information>() {

			@Override
			public void onSuccess(List<Information> infos) {
				Information info = infos.get(0);
				String des = info.getDes();
				tv_explain.setText(des.replace("$", "\n"));
			}

			@Override
			public void onError(int arg0, String arg1) {
				tv_explain.setText("获取威力系数说明失败，请重新点击获取");
			}
		});
	}

	/** 设置各个控件的数值 */
	private void setData(Weilixishu weilixishu) {
		// 设置备注
		if (!TextUtils.isEmpty(weilixishu.getBeizhu())) {
			tv_beizhu.setText(weilixishu.getBeizhu());
			tv_beizhu.setVisibility(View.VISIBLE);
		}
		else {
			tv_beizhu.setVisibility(View.GONE);
		}

		if (weilixishu.getHaveData() == 0) {
			ll.setVisibility(View.GONE);
			return;
		}

		ll.setVisibility(View.VISIBLE);

		setColor(tv_N1, weilixishu.getN1(), weilixishu.getN1color());
		setColor(tv_N2, weilixishu.getN2(), weilixishu.getN2color());
		setColor(tv_N3, weilixishu.getN3(), weilixishu.getN3color());
		setColor(tv_N4, weilixishu.getN4(), weilixishu.getN4color());
		setColor(tv_N5, weilixishu.getN5(), weilixishu.getN5color());
		setColor(tv_N6, weilixishu.getN6(), weilixishu.getN6color());
		setColor(tv_E6, weilixishu.getE6(), weilixishu.getE6color());
		setColor(tv_E7, weilixishu.getE7(), weilixishu.getE7color());
		setColor(tv_E8, weilixishu.getE8(), weilixishu.getE8color());
		setColor(tv_E9, weilixishu.getE9(), weilixishu.getE9color());

		setColor(tv_D, weilixishu.getD(), weilixishu.getDcolor());
		setColor(tv_JA, weilixishu.getJA(), weilixishu.getJAcolor());
		setColor(tv_JC, weilixishu.getJC(), weilixishu.getJCcolor());
		setColor(tv_C2, weilixishu.getC2(), weilixishu.getC2color());
		setColor(tv_C3, weilixishu.getC3(), weilixishu.getC3color());
		setColor(tv_C4, weilixishu.getC4(), weilixishu.getC4color());
		setColor(tv_C5, weilixishu.getC5(), weilixishu.getC5color());

		setColor(tv_tu, weilixishu.getTu(), weilixishu.getTucolor());
		setColor(tv_dun, weilixishu.getDun(), weilixishu.getDuncolor());
		setColor(tv_sui, weilixishu.getSui(), weilixishu.getSuicolor());
		setColor(tv_zhen, weilixishu.getZhen(), weilixishu.getZhencolor());
		setColor(tv_wei, weilixishu.getWei(), weilixishu.getWeicolor());
		setColor(tv_ba, weilixishu.getBa(), weilixishu.getBacolor());

		setColor(tv_puwuAndZhenwu, weilixishu.getPuwuAndZhenwu(), weilixishu.getPuwuAndZhenwucolor());
		setColor(tv_pumo, weilixishu.getPumo(), weilixishu.getPumocolor());
		setColor(tv_zhenmo, weilixishu.getZhenmo(), weilixishu.getZhenmocolor());

	}

	private void setColor(TextView tv, String xishuString, String colorString) {
		if (TextUtils.isEmpty(colorString) || TextUtils.isEmpty(xishuString)) {
			return;
		}
		
		String[] colors = colorString.split("\\|");
		int colorSize = colors.length;
		SpannableStringBuilder builder = new SpannableStringBuilder(xishuString);

		for (int i = 0; i < colorSize; i++) {
			String[] stringValues = colors[i].split(",");
			int[] intValues = new int[3];
			for (int j = 0; j < 3; j++) {
				int value = Integer.parseInt(stringValues[j]);
				intValues[j] = value;
			}

			ForegroundColorSpan redSpan = null;
			switch (intValues[2]) {
			// 白色（无属性）
			case 0:
				redSpan = new ForegroundColorSpan(Color.WHITE);
				break;
			// 蓝色 （带属性）
			case 1:
				redSpan = new ForegroundColorSpan(getResources().getColor(R.color.hava_shuxing_color));
				break;
			// 红色 （自带炎属性）
			case 2:
				redSpan = new ForegroundColorSpan(Color.RED);
				break;
			// 青色 （自带冰属性）
			case 3:
				redSpan = new ForegroundColorSpan(Color.CYAN);
				break;
			// 紫色 （自带斩属性）
			case 4:
				redSpan = new ForegroundColorSpan(getResources().getColor(R.color.purple));
				break;
			// 绿色 （自带风属性）
			case 5:
				redSpan = new ForegroundColorSpan(Color.GREEN);
				break;
			// 黄色 （自带雷属性）
			case 6:
				redSpan = new ForegroundColorSpan(Color.YELLOW);
				break;
			}
			builder.setSpan(redSpan, intValues[0], intValues[0] + intValues[1], Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}

		tv.setText(builder);
	}

	@Override
	public void onRefresh() {
		loadData();
	}

	@Override
	public void onClick(View v) {
		loadExplain();
	}

}
