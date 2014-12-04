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
import com.wlx.wsolandroid.constant.Constant;

public class MainActivity extends FragmentActivity implements OnClickListener, menuClicklistener {
    public SlidingMenu menu;
    private TextView   tv_wuqi_1, tv_renwu_1, tv_renwu_2,  tv_qita_2,tv_fujiang_1;
    private String     currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initSlidingMenu();
        this.initView();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_fragments, new WeaponJinpaiFragment()).commit();
        currentFragment = Constant.JINPAIWUQI;
    }

    private void initView() {
        tv_wuqi_1 = (TextView) menu.findViewById(R.id.tv_wuqi_1);
        //tv_wuqi_2 = (TextView) menu.findViewById(R.id.tv_wuqi_2);
        tv_renwu_1 = (TextView) menu.findViewById(R.id.tv_renwu_1);
        tv_renwu_2 = (TextView) menu.findViewById(R.id.tv_renwu_2);
        //tv_qita_1 = (TextView) menu.findViewById(R.id.tv_qita_1);
        tv_qita_2 = (TextView) menu.findViewById(R.id.tv_qita_2);
        tv_fujiang_1 = (TextView) menu.findViewById(R.id.tv_fujiang_1);
        tv_wuqi_1.setOnClickListener(this);
        //tv_wuqi_2.setOnClickListener(this);
        tv_renwu_1.setOnClickListener(this);
        tv_renwu_2.setOnClickListener(this);
        //tv_qita_1.setOnClickListener(this);
        tv_qita_2.setOnClickListener(this);
        tv_fujiang_1.setOnClickListener(this);
    }

    private void initSlidingMenu() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
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
        if (v == tv_wuqi_1 && !currentFragment.equals(Constant.JINPAIWUQI)) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_fragments, new WeaponJinpaiFragment()).commit();
            currentFragment = Constant.JINPAIWUQI;
        }
      //武器锻造模拟器----------------------------->
//        if (v == tv_wuqi_2 && !currentFragment.equals(Constant.WUQIDUANZAO)) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fl_fragments, new WeaponDuanzaoFragment()).commit();
//            currentFragment = Constant.WUQIDUANZAO;
//        }
        //任务报酬一览----------------------------->
        else if (v == tv_renwu_1 && !currentFragment.equals(Constant.RENWUBAOCHOU)) {
            LoadhtmlFragment fragment = new LoadhtmlFragment();
            Bundle bundle = new Bundle();
            bundle.putString("htmlName", "renwubaochou.html");
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragments, fragment)
                    .commit();
            currentFragment = Constant.RENWUBAOCHOU;
        }
        //任务等级表----------------------------->
        else if (v == tv_renwu_2 && !currentFragment.equals(Constant.RENWUDENGJI)) {
            LoadhtmlFragment fragment = new LoadhtmlFragment();
            Bundle bundle = new Bundle();
            bundle.putString("htmlName", "renwudengji.html");
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragments, fragment)
                    .commit();
            currentFragment = Constant.RENWUDENGJI;
        }
        //关于----------------------------->
//        else if (v == tv_qita_1 && !currentFragment.equals(Constant.ABOUTAPP)) {
//            LoadtxtFragment fragment = new LoadtxtFragment();
//            Bundle bundle = new Bundle();
//            bundle.putString("txtName", "aboutapp.txt");
//            fragment.setArguments(bundle);
//            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragments, fragment)
//                    .commit();
//            currentFragment = Constant.ABOUTAPP;
//        }
      //每周活动----------------------------->
        else if (v == tv_qita_2 && !currentFragment.equals(Constant.MEIZHOUHUODONG)) {
            MeizhouhuodongFragment fragment = new MeizhouhuodongFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragments, fragment)
                    .commit();
            currentFragment = Constant.MEIZHOUHUODONG;
        }
        //副将技能和属性----------------------------->
        else if (v == tv_fujiang_1 && !currentFragment.equals(Constant.FUJIANG)) {
            LoadtxtFragment fragment = new LoadtxtFragment();
            Bundle bundle = new Bundle();
            bundle.putString("txtName", "fujiang.txt");
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragments, fragment)
                    .commit();
            currentFragment = Constant.FUJIANG;
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
