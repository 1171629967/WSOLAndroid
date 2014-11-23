package com.wlx.wsolandroid;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.constant.Constant;
import com.wlx.wsolandroid.utils.Utils;
import com.wlx.wsolandroid.widget.MyActionBar;

public class MeizhouhuodongFragment extends BaseFragment {
	private TextView tv_content;
	private FinalHttp finalHttp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		finalHttp = new FinalHttp();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_loadtxt, null);
		tv_content = (TextView) view.findViewById(R.id.tv_content);

		AjaxParams params = new AjaxParams();
		params.put("info", Constant.tulingQuestion1);
		params.put("key", "9eef2ee631432b08710c555d673bf5a2");
		finalHttp.get(Constant.tulingAPI, params, new AjaxCallBack<Object>() {
			
			@Override
			public void onSuccess(Object t) {				
				super.onSuccess(t);
				try {
					JSONObject jsonObject = new JSONObject(t.toString());
					String text = (String) jsonObject.get("text");
					text = text.replaceAll("\\$", "\n");
					tv_content.setText(text);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				
				super.onFailure(t, errorNo, strMsg);
			}
			
		});
		
		
		

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

}
