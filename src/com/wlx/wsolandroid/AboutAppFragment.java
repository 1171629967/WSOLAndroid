package com.wlx.wsolandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.widget.MyActionBar;

public class AboutAppFragment extends Fragment {
    private TextView tv_content;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aboutapp, null);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_content
                .setText("        大家好，我是《无双ol资料》app的开发者，在游戏中名字叫——HUASUI.会稽，是电信的玩家。此APP是为了方便游戏玩家能在手机上查询无双ol的游戏资料，利用业余时间开发的。由于本人白天还要工作，搞IT的程序员，相信大家都懂的，平时加班挺凶残的，所以开发这款app可能不会很快，毕竟业余时间确实非常有限。在此向大家先说声抱歉呵呵，不过本人会持续完善该APP，所以请大家放心，支持。\n\n        第一个版本的APP上线后，获得了广大游戏玩家的大力支持，本人非常高兴开心，在此要向大家说声非常感谢。当第一个安卓版本上线后，苹果版本迟迟没上线，是因为苹果应用商店（app store）的审核确实比较耗时间，其实我是先开发的苹果版本，提交app store审核两天后，才着手开发安卓版本的，都一个多星期过去了，苹果版本还在等待审核，唉。。。。因为安卓版本的审核一般2天就好了，所以以后的版本更新，楼主会首先让苹果版本审核通过后再提交安卓版本。以免安卓版本都上线一周了苹果还没审核完的情况发生。\n\n        本人开发这个应用，游戏资料都是从17173论坛和百度贴吧中的精华帖中找来的，在此非常感谢提供游戏资料的玩家。\n\n        考虑到百度贴吧发了贴后，帖子经常被淹没的情况，为了方便大家交流，方便有版本更新的时候及时通知到大家。我建了一个QQ群，大家可以加入这个群335924948，一起讨论游戏，一起给我意见和建议去改进这款应用。进群时请及时更改群名片，改成游戏中的ID，谢谢大家。");
        this.initActionBar(view);
        return view;
    }

    private void initActionBar(View view) {
        MyActionBar actionBar = new MyActionBar(getActivity());
        actionBar.setTitle("关于APP");
        actionBar.setLeftEnable(false);
        RelativeLayout actionbar = (RelativeLayout) view.findViewById(R.id.rl_actionbar);
        actionbar.addView(actionBar);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("关于APP"); //统计页面
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("关于APP");
    }

}
