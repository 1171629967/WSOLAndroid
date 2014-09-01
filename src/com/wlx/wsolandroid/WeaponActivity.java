package com.wlx.wsolandroid;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.wlx.wsolandroid.adapter.WeaponListAdapter;
import com.wlx.wsolandroid.constant.Constant;
import com.wlx.wsolandroid.model.Weapon;
import com.wlx.wsolandroid.widget.MyActionBar;

public class WeaponActivity extends Activity {
    private ListView           lv1;
    private WeaponListAdapter  adapter;
    private WeaponListAdapter  searchReultAdapter;
    private View               v_head;
    private EditText           et_search;
    private final List<Weapon> allWeapons          = new ArrayList<Weapon>();
    private final List<Weapon> searchResultWeapons = new ArrayList<Weapon>();
    private int                allWeaponCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon);
        this.initActionBar();
        this.initView();
    }

    private void initView() {
        v_head = LayoutInflater.from(this).inflate(R.layout.weapon_search, null);
        et_search = (EditText) v_head.findViewById(R.id.et_search);
        et_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchResultWeapons.clear();
                String searchString = s.toString().trim();

                //当搜索关键字为空时，显示全部武器
                if (TextUtils.isEmpty(searchString)) {
                    lv1.setAdapter(adapter);
                } else {
                    for (int i = 0; i < allWeaponCount; i++) {
                        if (allWeapons.get(i).getName().contains(searchString)) {
                            searchResultWeapons.add(allWeapons.get(i));
                        }
                    }
                    lv1.setAdapter(searchReultAdapter);
                }
            }
        });

        lv1 = (ListView) findViewById(R.id.lv1);
        lv1.addHeaderView(v_head);

        String[] names = Constant.weaponNameR1.split("\\,");
        String[] gs = Constant.weaponDataR1G.split("\\,");
        String[] ps = Constant.weaponDataR1P.split("\\,");
        String[] fs = Constant.weaponDataR1F.split("\\,");
        String[] ts = Constant.weaponDataR1T.split("\\,");
        String[] ws = Constant.weaponDataR1W.split("\\,");

        allWeaponCount = names.length;
        for (int i = 0; i < allWeaponCount; i++) {
            Weapon weapon = new Weapon();
            weapon.setName(names[i]);
            weapon.setG(Integer.parseInt(gs[i]));
            weapon.setP(Integer.parseInt(ps[i]));
            weapon.setF(Integer.parseInt(fs[i]));
            weapon.setT(Integer.parseInt(ts[i]));
            weapon.setW(Integer.parseInt(ws[i]));
            allWeapons.add(weapon);
        }

        adapter = new WeaponListAdapter(this, allWeapons);
        searchReultAdapter = new WeaponListAdapter(this, searchResultWeapons);
        lv1.setAdapter(adapter);

    }

    private void initActionBar() {
        MyActionBar actionBar = new MyActionBar(this);
        actionBar.setTitle("金牌武器上升值");
        actionBar.setLeftEnable(false);
        RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
        actionbar.addView(actionBar);
    }

}
