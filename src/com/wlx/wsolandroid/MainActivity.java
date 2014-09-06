package com.wlx.wsolandroid;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.umeng.analytics.MobclickAgent;

public class MainActivity extends FragmentActivity implements OnClickListener {
    private SlidingMenu menu;
    private TextView    tv_1_1, tv_2_1;
    private String      currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initSlidingMenu();
        this.initView();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_fragments, new WeaponJinpaiFragment()).commit();
        currentFragment = "1_1";
    }

    private void initView() {
        tv_1_1 = (TextView) menu.findViewById(R.id.tv_1_1);
        tv_2_1 = (TextView) menu.findViewById(R.id.tv_2_1);
        tv_1_1.setOnClickListener(this);
        tv_2_1.setOnClickListener(this);
    }

    private void initSlidingMenu() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
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
        if (v == tv_1_1 && !currentFragment.equals("1_1")) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_fragments, new WeaponJinpaiFragment()).commit();
            currentFragment = "1_1";
        }
        //任务报酬一览----------------------------->
        else if (v == tv_2_1 && !currentFragment.equals("2_1")) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_fragments, new RenwuFragment()).commit();
            currentFragment = "2_1";
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

}
