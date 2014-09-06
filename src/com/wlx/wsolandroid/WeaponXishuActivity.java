package com.wlx.wsolandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wlx.wsolandroid.model.Xishu;
import com.wlx.wsolandroid.widget.MyActionBar;

/**
 * 武器威力系数
 * 
 * @author wanglixin
 */
public class WeaponXishuActivity extends Activity {
    private TextView tv_N1, tv_N2, tv_N3, tv_N4, tv_N5, tv_N6, tv_E6, tv_E7, tv_E8, tv_E9, tv_D,
            tv_JA, tv_JC, tv_C2, tv_C3, tv_C4, tv_C5;

    private Xishu    xishu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon_xishu);

        xishu = (Xishu) getIntent().getExtras().get("xishu");
        this.initActionBar();
        this.initView();
        this.setData();
    }

    private void initActionBar() {
        MyActionBar actionBar = new MyActionBar(this);
        actionBar.setTitle("武器威力系数");
        actionBar.setLeftEnable(true);
        RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
        actionbar.addView(actionBar);
    }

    private void initView() {
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
    }

    /** 设置各个控件的数值 */
    private void setData() {
        tv_N1.setText("N1\n" + xishu.getN1());
        tv_N2.setText("N2\n" + xishu.getN2());
        tv_N3.setText("N3\n" + xishu.getN3());
        tv_N4.setText("N4\n" + xishu.getN4());
        tv_N5.setText("N5\n" + xishu.getN5());
        tv_N6.setText("N6\n" + xishu.getN6());
        tv_E6.setText("E6\n" + xishu.getE6());
        tv_E7.setText("E7\n" + xishu.getE7());
        tv_E8.setText("E8\n" + xishu.getE8());
        tv_E9.setText("E9\n" + xishu.getE9());

        tv_D.setText("D\n" + xishu.getD());
        tv_JA.setText("JA\n" + xishu.getJA());
        tv_JC.setText("JC\n" + xishu.getJC());
        tv_C2.setText("C2\n" + xishu.getC2());
        tv_C3.setText("C3\n" + xishu.getC3());
        tv_C4.setText("C4\n" + xishu.getC4());
        tv_C5.setText("C5\n" + xishu.getC5());
    }

}
