package com.wlx.wsolandroid;




import com.wlx.wsolandroid.utils.Utils;
import com.wlx.wsolandroid.widget.MyActionBar;

import android.R.bool;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;


public class WebViewActivity extends Activity{
	private String url;
	private boolean mRefreshing = false;
	private WebView webView;
	private String title;
	private MyActionBar actionBar;
	

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_webview);
	initializeData();
	initActionbar();
	init();
}
	
	
	

	private  void initializeData() {
		Bundle bundle = getIntent().getExtras();
		url = bundle.getString("url");	
		title = bundle.getString("title");
	}

	private void initActionbar() {
		actionBar = new MyActionBar(this);
		actionBar.setLeftEnable(true);
		if (!TextUtils.isEmpty(title)) {
			actionBar.setTitle(title);
		}
		
		
		
		actionBar.setLeftClickListenner(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				finish();				
			}
		});
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
		
	}

	private void init() {
		webView = (WebView) findViewById(R.id.webView);
		Utils.setAppBackgroundColor(this,
				findViewById(R.id.linearLayout));
		WebSettings localWebSettings = webView.getSettings();
		localWebSettings.setJavaScriptEnabled(true);
		localWebSettings.setSavePassword(true);
		localWebSettings.setSaveFormData(true);
		localWebSettings.setBlockNetworkImage(false);
		localWebSettings.setAppCacheEnabled(true);
		localWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		//解决页面显示自适应屏幕的问题
		localWebSettings.setUseWideViewPort(true);
		localWebSettings.setLoadWithOverviewMode(true);
		webView.setVerticalScrollBarEnabled(false);
		webView.setHorizontalScrollBarEnabled(false);
		
		
		
		
		
		
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView paramWebView,
					String paramString) {	
				return super
						.shouldOverrideUrlLoading(paramWebView, paramString);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				if (TextUtils.isEmpty(title)) {
					actionBar.setTitle("加载中...");				
				}
				mRefreshing = true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				if (TextUtils.isEmpty(title)) {
					actionBar.setTitle(view.getTitle());
				}
				mRefreshing = false;
				
			}
		});
		
		
		webView.loadUrl(url);	
		
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		onRefresh();
	}
	
	
	@Override
	public void onDestroy() {
		ViewGroup parent = (ViewGroup) webView.getParent();
		if (parent != null) {
			parent.removeView(webView);
		}
		webView.removeAllViews();
		webView.destroy();
		super.onDestroy();
	}
	
	
	private void onRefresh() {
		if (mRefreshing) {
			return;
		}
		webView.reload();
	}
	
	
	protected void onBtnBack() {
		if (webView.canGoBack()) {
			webView.goBack();
		} else {
			finish();
		}
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			onBtnBack();
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	
	
	
	
	

}
