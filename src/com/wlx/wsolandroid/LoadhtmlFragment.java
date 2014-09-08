package com.wlx.wsolandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.widget.MyActionBar;

public class LoadhtmlFragment extends BaseFragment {
    private WebView wb_content;
    private String  loadHTMLName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey("htmlName")) {
            loadHTMLName = getArguments().getString("htmlName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loadhtml, null);
        wb_content = (WebView) view.findViewById(R.id.wb_content);
        this.initActionBar(view);
        this.loadData();
        return view;
    }

    private void initActionBar(View view) {
        MyActionBar actionBar = new MyActionBar(getActivity());
        if (loadHTMLName.equals("renwu.html")) {
            actionBar.setTitle("任务报酬一览");
        } else if (loadHTMLName.equals("renwudengji.html")) {
            actionBar.setTitle("任务等级表");
        }
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
        String urlPre = "file:///android_asset/";
        wb_content.loadUrl(urlPre + loadHTMLName);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (loadHTMLName.equals("renwu.html")) {
            MobclickAgent.onPageStart("任务报酬一览");
        } else if (loadHTMLName.equals("renwudengji.html")) {
            MobclickAgent.onPageStart("任务等级表");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (loadHTMLName.equals("renwu.html")) {
            MobclickAgent.onPageEnd("任务报酬一览");
        } else if (loadHTMLName.equals("renwudengji.html")) {
            MobclickAgent.onPageEnd("任务等级表");
        }
    }

}
