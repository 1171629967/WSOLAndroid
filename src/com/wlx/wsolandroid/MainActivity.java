package com.wlx.wsolandroid;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.BaseFragment.menuClicklistener;

public class MainActivity extends FragmentActivity implements OnClickListener, menuClicklistener {
    public SlidingMenu menu;
    private TextView   tv_wuqi_1, tv_renwu_1, tv_renwu_2, tv_qita_1, tv_fujiang_1;
    private String     currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initSlidingMenu();
        this.initView();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_fragments, new WeaponJinpaiFragment()).commit();
        currentFragment = "wuqi_1";
    }

    private void initView() {
        tv_wuqi_1 = (TextView) menu.findViewById(R.id.tv_wuqi_1);
        tv_renwu_1 = (TextView) menu.findViewById(R.id.tv_renwu_1);
        tv_renwu_2 = (TextView) menu.findViewById(R.id.tv_renwu_2);
        tv_qita_1 = (TextView) menu.findViewById(R.id.tv_qita_1);
        tv_fujiang_1 = (TextView) menu.findViewById(R.id.tv_fujiang_1);
        tv_wuqi_1.setOnClickListener(this);
        tv_renwu_1.setOnClickListener(this);
        tv_renwu_2.setOnClickListener(this);
        tv_qita_1.setOnClickListener(this);
        tv_fujiang_1.setOnClickListener(this);
    }

    private void initSlidingMenu() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        //        menu.setShadowWidthRes(R.dimen.shadow_width);
        //        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.slidingmenu);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                menu.toggle(true);
                break;

            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        //金牌武器上升值----------------------------->
        if (v == tv_wuqi_1 && !currentFragment.equals("wuqi_1")) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_fragments, new WeaponJinpaiFragment()).commit();
            currentFragment = "wuqi_1";
        }
        //任务报酬一览----------------------------->
        else if (v == tv_renwu_1 && !currentFragment.equals("renwu_1")) {
            LoadhtmlFragment fragment = new LoadhtmlFragment();
            Bundle bundle = new Bundle();
            bundle.putString("htmlName", "renwu.html");
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragments, fragment)
                    .commit();
            currentFragment = "renwu_1";
        }
        //任务报酬一览----------------------------->
        else if (v == tv_renwu_2 && !currentFragment.equals("renwu_2")) {
            LoadhtmlFragment fragment = new LoadhtmlFragment();
            Bundle bundle = new Bundle();
            bundle.putString("htmlName", "renwudengji.html");
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragments, fragment)
                    .commit();
            currentFragment = "renwu_2";
        }
        //关于----------------------------->
        else if (v == tv_qita_1 && !currentFragment.equals("qita_1")) {
            LoadtxtFragment fragment = new LoadtxtFragment();
            Bundle bundle = new Bundle();
            bundle.putString("txtName", "aboutapp.txt");
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragments, fragment)
                    .commit();
            currentFragment = "qita_1";
        }
        //副将技能和属性----------------------------->
        else if (v == tv_fujiang_1 && !currentFragment.equals("fujiang_1")) {
            LoadtxtFragment fragment = new LoadtxtFragment();
            Bundle bundle = new Bundle();
            bundle.putString("txtName", "fujiang.txt");
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragments, fragment)
                    .commit();
            currentFragment = "fujiang_1";
        }
        menu.toggle(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void menuClick() {
        menu.toggle(true);
    }

}
