package com.wlx.wsolandroid;


import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.adapter.WeaponRankAdapter;
import com.wlx.wsolandroid.adapter.WeaponTypeAdapter;
import com.wlx.wsolandroid.utils.Utils;
import com.wlx.wsolandroid.widget.DuanzaoView;
import com.wlx.wsolandroid.widget.MyActionBar;



import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 武器锻造模拟器
 * WeaponDuanzaoFragment
 * @author wanglixin
 *
 */
public class WeaponDuanzaoFragment extends BaseFragment implements OnItemClickListener{
	private MyActionBar actionBar;
	private PopupWindow pop;
	/** 手机屏幕的宽 */
	private int windowWidth; 
	private ListView lv_weaponType;
	private ListView lv_weaponRank;
	private WeaponTypeAdapter weaponTypeAdapter;
	private WeaponRankAdapter weaponRankAdapter;
	
    /** 基础攻击力 */
    private TextView tv_base_gongjili;
    /** 攻击力上升值 */
    private TextView tv_rise_gongjili;
    /** 攻击力强化位 */
    private TextView tv_slotNumber_gongji;
    /** 攻击条的容器 */
    private LinearLayout ll_gongji2;
    
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weapon_duanzao, null);
        this.initActionBar(view);
        this.initView(view);
        return view;
    }
    
    
    
    private void initView(View view) {
        //初始化攻击力相关控件
        tv_base_gongjili = (TextView) view.findViewById(R.id.tv_base_gongjili);
        tv_rise_gongjili = (TextView) view.findViewById(R.id.tv_rise_gongjili);
        tv_slotNumber_gongji = (TextView) view.findViewById(R.id.tv_slotNumber_gongji);
        ll_gongji2 = (LinearLayout) view.findViewById(R.id.ll_gongji2);
        ll_gongji2.setOrientation(LinearLayout.HORIZONTAL);
        
        new Handler().postDelayed(new Runnable() {            
            @Override
            public void run() {
            	//初始化pop
            	View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_choose_weapon, null);
            	weaponTypeAdapter = new WeaponTypeAdapter(getActivity());
            	weaponRankAdapter = new WeaponRankAdapter(getActivity());
				lv_weaponType = (ListView) view.findViewById(R.id.lv_weaponType);
				lv_weaponType.setAdapter(weaponTypeAdapter);
				lv_weaponType.setOnItemClickListener(WeaponDuanzaoFragment.this);				
				lv_weaponRank = (ListView) view.findViewById(R.id.lv_weaponRank);
				lv_weaponRank.setAdapter(weaponRankAdapter);
				lv_weaponRank.setOnItemClickListener(WeaponDuanzaoFragment.this);
				
				
            	windowWidth = Utils.getDisplay(getActivity(), 1);
            	pop = new PopupWindow(view, windowWidth / 2, LayoutParams.WRAP_CONTENT, true);
				pop.setBackgroundDrawable(new BitmapDrawable()); // 点击手机返回键可以取消掉popupwindow
				pop.setOutsideTouchable(true); // 设置点击窗口外边窗口消失
            	
            	
            	
            	
                int totalWidth = ll_gongji2.getWidth();
                int totalHeight = ll_gongji2.getHeight();
               
                DuanzaoView duanzaoView1 = new DuanzaoView(getActivity(), true, totalWidth, 50, totalHeight, 5);
                DuanzaoView duanzaoView2 = new DuanzaoView(getActivity(), true, totalWidth, 100, totalHeight, 2);
                DuanzaoView duanzaoView3 = new DuanzaoView(getActivity(), true, totalWidth, 150, totalHeight, 3);
                ll_gongji2.addView(duanzaoView1,(totalWidth/400)*50,totalHeight);
                ll_gongji2.addView(duanzaoView2,(totalWidth/400)*100,totalHeight);
                ll_gongji2.addView(duanzaoView3,(totalWidth/400)*150,totalHeight);
            }
        }, 100);
        
    }
    
    private void initActionBar(View view) {
        actionBar = new MyActionBar(getActivity());
        actionBar.setTitle("武器锻造模拟器");
        actionBar.setLeftEnable(true);
        actionBar.setLeftText("菜单");
        actionBar.setRightText("选择武器");
        actionBar.setLeftClickListenner(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mClicklistener.menuClick();
            }
        });
        
        actionBar.setRightClickListenner(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (pop.isShowing()) {
					pop.dismiss();
				} else {
					// 获取popwindow在X,Y轴上的偏移量后，设置popwindow的位置
					int offsetX = windowWidth / 3 * 2;
					int offsetY = actionBar.getHeight();
					pop.showAsDropDown(getActivity().findViewById(R.id.rl_actionbar), offsetX, offsetY);
				}
			}
		});
        
        
        RelativeLayout actionbar = (RelativeLayout) view.findViewById(R.id.rl_actionbar);
        actionbar.addView(actionBar);
    }
    
    
    
    
    
    private void setData(){
        //tv_base_gongjili.setText(text)
    }
    
    
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("武器锻造模拟器"); //统计页面
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("武器锻造模拟器");
    }



	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	}

}
