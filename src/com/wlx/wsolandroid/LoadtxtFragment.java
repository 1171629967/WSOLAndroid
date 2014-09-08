package com.wlx.wsolandroid;

import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.utils.Utils;
import com.wlx.wsolandroid.widget.MyActionBar;

public class LoadtxtFragment extends BaseFragment {
    private TextView tv_content;
    private String   loadTXTName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey("txtName")) {
            loadTXTName = getArguments().getString("txtName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loadtxt, null);
        tv_content = (TextView) view.findViewById(R.id.tv_content);

        InputStream inputStream;
        try {

            inputStream = getActivity().getAssets().open(loadTXTName);
            String contentString = Utils.getString(inputStream);
            tv_content.setText(contentString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.initActionBar(view);
        return view;
    }

    private void initActionBar(View view) {
        MyActionBar actionBar = new MyActionBar(getActivity());
        if (loadTXTName.equals("aboutapp.txt")) {
            actionBar.setTitle("关于APP");
        } else if (loadTXTName.equals("fujiang.txt")) {
            actionBar.setTitle("副将技能和属性");
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

    @Override
    public void onResume() {
        super.onResume();
        if (loadTXTName.equals("aboutapp.txt")) {
            MobclickAgent.onPageStart("关于APP");
        } else if (loadTXTName.equals("fujiang.txt")) {
            MobclickAgent.onPageStart("副将技能和属性");
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (loadTXTName.equals("aboutapp.txt")) {
            MobclickAgent.onPageEnd("关于APP");
        } else if (loadTXTName.equals("fujiang.txt")) {
            MobclickAgent.onPageEnd("副将技能和属性");
        }
    }

}
