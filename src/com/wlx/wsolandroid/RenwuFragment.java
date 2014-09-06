package com.wlx.wsolandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.widget.MyActionBar;

public class RenwuFragment extends Fragment {
    private WebView wb_content;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_renwu, null);
        wb_content = (WebView) view.findViewById(R.id.wb_content);
        this.initActionBar(view);
        this.loadData();
        return view;
    }

    private void initActionBar(View view) {
        MyActionBar actionBar = new MyActionBar(getActivity());
        actionBar.setTitle("任务");
        actionBar.setLeftEnable(false);
        RelativeLayout actionbar = (RelativeLayout) view.findViewById(R.id.rl_actionbar);
        actionbar.addView(actionBar);
    }

    private void loadData() {
        wb_content.loadUrl(" file:///android_asset/renwu.html ");
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("任务报酬一览"); //统计页面
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("任务报酬一览");
    }

}
