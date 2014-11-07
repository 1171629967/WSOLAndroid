package com.wlx.wsolandroid;

import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.adapter.WeaponRankAdapter;
import com.wlx.wsolandroid.adapter.WeaponTypeAdapter;
import com.wlx.wsolandroid.utils.Utils;
import com.wlx.wsolandroid.weapondata.R1WeaponData;
import com.wlx.wsolandroid.weapondata.R3WeaponData;
import com.wlx.wsolandroid.weapondata.R4WeaponData;
import com.wlx.wsolandroid.weapondata.R5WeaponData;
import com.wlx.wsolandroid.weapondata.R6WeaponData;
import com.wlx.wsolandroid.weapondata.R7WeaponData;
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
import android.widget.Toast;

/**
 * 武器锻造模拟器 WeaponDuanzaoFragment
 * 
 * @author wanglixin
 * 
 */
public class WeaponDuanzaoFragment extends BaseFragment implements OnClickListener, OnItemClickListener {
	private MyActionBar actionBar;
	private PopupWindow pop;
	/** 手机屏幕的宽 */
	private int windowWidth;
	private ListView lv_weaponType;
	private ListView lv_weaponRank;
	private WeaponTypeAdapter weaponTypeAdapter;
	private WeaponRankAdapter weaponRankAdapter;

	private int currentWeaponRank = 0;
	private int currentWeaponType = 0;

	/** 武器名字 */
	private TextView tv_weaponName;
	/** 武器级别 */
	private TextView tv_rank;
	/** 改造值 */
	private TextView tv_gaizaoData;
	/** 锻造值 */
	private TextView tv_duanzaoData;

	// 攻击相关控件--------------------------------
	private View include_gongji;
	/** 攻击力字样 */
	private TextView tv_gongjili;
	/** 攻击锻造上升按钮 */
	private TextView tv_gongjiUp;
	/** 攻击锻造下降按钮 */
	private TextView tv_gongjiDown;
	/** 基础攻击力 */
	private TextView tv_base_gongjili;
	/** 攻击力上升值 */
	private TextView tv_rise_gongjili;
	/** 攻击力强化位 */
	private TextView tv_slotNumber_gongji;
	/** 攻击条的容器 */
	private LinearLayout ll_gongjicao;
	// 破坏相关控件--------------------------------
	private View include_pohuai;
	/** 破坏力字样 */
	private TextView tv_pohuaili;
	/** 破坏锻造上升按钮 */
	private TextView tv_pohuaiUp;
	/** 破坏锻造下降按钮 */
	private TextView tv_pohuaiDown;
	/** 基础破坏力 */
	private TextView tv_base_pohuaili;
	/** 破坏力上升值 */
	private TextView tv_rise_pohuaili;
	/** 破坏力强化位 */
	private TextView tv_slotNumber_pohuai;
	/** 破坏条的容器 */
	private LinearLayout ll_pohuaicao;
	// 防御相关控件--------------------------------
	private View include_fangyu;
	/** 防御力字样 */
	private TextView tv_fangyuli;
	/** 防御锻造上升按钮 */
	private TextView tv_fangyuUp;
	/** 防御锻造下降按钮 */
	private TextView tv_fangyuDown;
	/** 基础防御力 */
	private TextView tv_base_fangyuli;
	/** 防御力上升值 */
	private TextView tv_rise_fangyuli;
	/** 防御力强化位 */
	private TextView tv_slotNumber_fangyu;
	/** 防御条的容器 */
	private LinearLayout ll_fangyucao;
	// 体力相关控件--------------------------------
	private View include_tili;
	/** 体力字样 */
	private TextView tv_tili;
	/** 体力锻造上升按钮 */
	private TextView tv_tiliUp;
	/** 体力锻造下降按钮 */
	private TextView tv_tiliDown;
	/** 基础体力 */
	private TextView tv_base_tili;
	/** 体力上升值 */
	private TextView tv_rise_tili;
	/** 体力强化位 */
	private TextView tv_slotNumber_tili;
	/** 体力条的容器 */
	private LinearLayout ll_tilicao;
	// 无双相关控件--------------------------------
	private View include_wushuang;
	/** 无双字样 */
	private TextView tv_wushuang;
	/** 无双锻造上升按钮 */
	private TextView tv_wushuangUp;
	/** 无双锻造下降按钮 */
	private TextView tv_wushuangDown;
	/** 基础无双 */
	private TextView tv_base_wushuang;
	/** 无双上升值 */
	private TextView tv_rise_wushuang;
	/** 无双强化位 */
	private TextView tv_slotNumber_wushuang;
	/** 无双条的容器 */
	private LinearLayout ll_wushuangcao;

	/** 最大锻造值 */
	private int maxDuanzaozhi = 24;
	/** 当前锻造值 */
	private int currentDuanzaozhi = 0;
	/** 当前攻击锻造次数 */
	private int currentGongjiTime = 0;
	/** 当前破坏锻造次数 */
	private int currentPohuaiTime = 0;
	/** 当前防御锻造次数 */
	private int currentFangyuTime = 0;
	/** 当前体力锻造次数 */
	private int currentTiliTime = 0;
	/** 当前无双锻造次数 */
	private int currentWushuangTime = 0;

	// 武器基础
	int baseGongji;
	int basePohuai;
	int baseFangyu;
	int baseTili;
	int baseWushuang;
	// 武器上升值
	int riseGongji;
	int risePohuai;
	int riseFangyu;
	int riseTili;
	int riseWushuang;
	// 每一点数值所占的宽度
	float persentWidth;
	// 上升的长度
	int riseGongjiWidth;
	int risePohuaiWidth;
	int riseFangyuWidth;
	int riseTiliWidth;
	int riseWushuangWidth;
	// 属性槽的总长度
	int totalWidth;
	// 属性槽的高度
	int totalHeight;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_weapon_duanzao, null);
		this.initActionBar(view);
		this.initView(view);
		return view;
	}

	private void initView(View view) {
		tv_weaponName = (TextView) view.findViewById(R.id.tv_weaponName);
		tv_rank = (TextView) view.findViewById(R.id.tv_rank);
		tv_gaizaoData = (TextView) view.findViewById(R.id.tv_gaizaoData);
		tv_duanzaoData = (TextView) view.findViewById(R.id.tv_duanzaoData);

		// 初始化攻击力相关控件----------------------------
		include_gongji = view.findViewById(R.id.include_gongji);
		tv_gongjili = (TextView) include_gongji.findViewById(R.id.tv_gongjili);
		tv_gongjili.setText("攻击力");
		tv_gongjiUp = (TextView) include_gongji.findViewById(R.id.tv_gongjiUp);
		tv_gongjiUp.setOnClickListener(this);
		tv_gongjiDown = (TextView) include_gongji.findViewById(R.id.tv_gongjiDown);
		tv_gongjiDown.setOnClickListener(this);
		tv_base_gongjili = (TextView) include_gongji.findViewById(R.id.tv_base_gongjili);
		tv_rise_gongjili = (TextView) include_gongji.findViewById(R.id.tv_rise_gongjili);
		tv_slotNumber_gongji = (TextView) include_gongji.findViewById(R.id.tv_slotNumber_gongji);
		ll_gongjicao = (LinearLayout) include_gongji.findViewById(R.id.ll_gongjicao);
		ll_gongjicao.setOrientation(LinearLayout.HORIZONTAL);
		// 初始化破坏力相关控件----------------------------
		include_pohuai = view.findViewById(R.id.include_pohuai);
		tv_pohuaili = (TextView) include_pohuai.findViewById(R.id.tv_gongjili);
		tv_pohuaili.setText("破坏力");
		tv_pohuaiUp = (TextView) include_pohuai.findViewById(R.id.tv_gongjiUp);
		tv_pohuaiUp.setOnClickListener(this);
		tv_pohuaiDown = (TextView) include_pohuai.findViewById(R.id.tv_gongjiDown);
		tv_pohuaiDown.setOnClickListener(this);
		tv_base_pohuaili = (TextView) include_pohuai.findViewById(R.id.tv_base_gongjili);
		tv_rise_pohuaili = (TextView) include_pohuai.findViewById(R.id.tv_rise_gongjili);
		tv_slotNumber_pohuai = (TextView) include_pohuai.findViewById(R.id.tv_slotNumber_gongji);
		ll_pohuaicao = (LinearLayout) include_pohuai.findViewById(R.id.ll_gongjicao);
		ll_pohuaicao.setOrientation(LinearLayout.HORIZONTAL);
		// 初始化防御力相关控件----------------------------
		include_fangyu = view.findViewById(R.id.include_fangyu);
		tv_fangyuli = (TextView) include_fangyu.findViewById(R.id.tv_gongjili);
		tv_fangyuli.setText("防御力");
		tv_fangyuUp = (TextView) include_fangyu.findViewById(R.id.tv_gongjiUp);
		tv_fangyuUp.setOnClickListener(this);
		tv_fangyuDown = (TextView) include_fangyu.findViewById(R.id.tv_gongjiDown);
		tv_fangyuDown.setOnClickListener(this);
		tv_base_fangyuli = (TextView) include_fangyu.findViewById(R.id.tv_base_gongjili);
		tv_rise_fangyuli = (TextView) include_fangyu.findViewById(R.id.tv_rise_gongjili);
		tv_slotNumber_fangyu = (TextView) include_fangyu.findViewById(R.id.tv_slotNumber_gongji);
		ll_fangyucao = (LinearLayout) include_fangyu.findViewById(R.id.ll_gongjicao);
		ll_fangyucao.setOrientation(LinearLayout.HORIZONTAL);
		// 初始化体力相关控件----------------------------
		include_tili = view.findViewById(R.id.include_tili);
		tv_tili = (TextView) include_tili.findViewById(R.id.tv_gongjili);
		tv_tili.setText("体力    ");
		tv_tiliUp = (TextView) include_tili.findViewById(R.id.tv_gongjiUp);
		tv_tiliUp.setOnClickListener(this);
		tv_tiliDown = (TextView) include_tili.findViewById(R.id.tv_gongjiDown);
		tv_tiliDown.setOnClickListener(this);
		tv_base_tili = (TextView) include_tili.findViewById(R.id.tv_base_gongjili);
		tv_rise_tili = (TextView) include_tili.findViewById(R.id.tv_rise_gongjili);
		tv_slotNumber_tili = (TextView) include_tili.findViewById(R.id.tv_slotNumber_gongji);
		ll_tilicao = (LinearLayout) include_tili.findViewById(R.id.ll_gongjicao);
		ll_tilicao.setOrientation(LinearLayout.HORIZONTAL);
		// 初始化无双相关控件----------------------------
		include_wushuang = view.findViewById(R.id.include_wushuang);
		tv_wushuang = (TextView) include_wushuang.findViewById(R.id.tv_gongjili);
		tv_wushuang.setText("无双    ");
		tv_wushuangUp = (TextView) include_wushuang.findViewById(R.id.tv_gongjiUp);
		tv_wushuangUp.setOnClickListener(this);
		tv_wushuangDown = (TextView) include_wushuang.findViewById(R.id.tv_gongjiDown);
		tv_wushuangDown.setOnClickListener(this);
		tv_base_wushuang = (TextView) include_wushuang.findViewById(R.id.tv_base_gongjili);
		tv_rise_wushuang = (TextView) include_wushuang.findViewById(R.id.tv_rise_gongjili);
		tv_slotNumber_wushuang = (TextView) include_wushuang.findViewById(R.id.tv_slotNumber_gongji);
		ll_wushuangcao = (LinearLayout) include_wushuang.findViewById(R.id.ll_gongjicao);
		ll_wushuangcao.setOrientation(LinearLayout.HORIZONTAL);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// 初始化pop
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
				pop = new PopupWindow(view, windowWidth / 3 * 2, LayoutParams.WRAP_CONTENT, true);
				pop.setBackgroundDrawable(new BitmapDrawable()); // 点击手机返回键可以取消掉popupwindow
				pop.setOutsideTouchable(true); // 设置点击窗口外边窗口消失

				setData();
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
					int offsetX = windowWidth / 2;
					int offsetY = actionBar.getHeight();
					pop.showAsDropDown(getActivity().findViewById(R.id.rl_actionbar), offsetX, offsetY);
				}
			}
		});

		RelativeLayout actionbar = (RelativeLayout) view.findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	private void setData() {

		// 武器基础值
		baseGongji = R1WeaponData.weaponBaseGongji[currentWeaponType];
		basePohuai = R1WeaponData.weaponBasePohuai[currentWeaponType];
		baseFangyu = R1WeaponData.weaponBaseFangyu[currentWeaponType];
		baseTili = R1WeaponData.weaponBaseTili[currentWeaponType];
		baseWushuang = R1WeaponData.weaponBaseWushuang[currentWeaponType];
		tv_base_gongjili.setText(baseGongji + "");
		tv_base_pohuaili.setText(basePohuai + "");
		tv_base_fangyuli.setText(baseFangyu + "");
		tv_base_tili.setText(baseTili + "");
		tv_base_wushuang.setText(baseWushuang + "");
		// 武器上升值
		riseGongji = R1WeaponData.R1RiseGongji[currentWeaponType] + currentWeaponRank * 3;
		risePohuai = R1WeaponData.R1RisePohuai[currentWeaponType] + currentWeaponRank * 3;
		riseFangyu = R1WeaponData.R1RiseFangyu[currentWeaponType] + currentWeaponRank * 3;
		riseTili = R1WeaponData.R1RiseTili[currentWeaponType] + currentWeaponRank * 3;
		riseWushuang = R1WeaponData.R1RiseWushuang[currentWeaponType] + currentWeaponRank * 3;
		tv_rise_gongjili.setText("( + " + riseGongji + " )");
		tv_rise_fangyuli.setText("( + " + riseFangyu + " )");
		tv_rise_tili.setText("( + " + riseTili + " )");
		tv_rise_wushuang.setText("( + " + riseWushuang + " )");

		// 强化位
		tv_slotNumber_gongji.setText(R1WeaponData.weaponSlotGongji[currentWeaponType] + "");
		tv_slotNumber_pohuai.setText(R1WeaponData.weaponSlotPohuai[currentWeaponType] + "");
		tv_slotNumber_fangyu.setText(R1WeaponData.weaponSlotFangyu[currentWeaponType] + "");
		tv_slotNumber_tili.setText(R1WeaponData.weaponSlotTili[currentWeaponType] + "");
		tv_slotNumber_wushuang.setText(R1WeaponData.weaponSlotWushuang[currentWeaponType] + "");

		// 获取攻击槽的总长度和高度(每个自定义View的高度即是槽的高度)
		totalWidth = ll_gongjicao.getWidth();
		totalHeight = ll_gongjicao.getHeight();

		// 计算出每一点数值所占的宽度
		persentWidth = totalWidth / DuanzaoView.totalPoint;
		// 计算出基础值的长度
		int baseGongjiWidth = (int) (persentWidth * baseGongji);
		int basePohuaiWidth = (int) (persentWidth * basePohuai);
		int baseFangyuWidth = (int) (persentWidth * baseFangyu);
		int baseTiliWidth = (int) (persentWidth * baseTili);
		int baseWushuangWidth = (int) (persentWidth * baseWushuang);
		// 计算出上升值的长度
		riseGongjiWidth = (int) (persentWidth * riseGongji);
		risePohuaiWidth = (int) (persentWidth * risePohuai);
		riseFangyuWidth = (int) (persentWidth * riseFangyu);
		riseTiliWidth = (int) (persentWidth * riseTili);
		riseWushuangWidth = (int) (persentWidth * riseWushuang);
		// 添加基础View
		DuanzaoView baseGongjiView = new DuanzaoView(getActivity(), false, baseGongjiWidth, totalHeight, 1);
		ll_gongjicao.addView(baseGongjiView, baseGongjiWidth, totalHeight);
		DuanzaoView basePohuaiView = new DuanzaoView(getActivity(), false, basePohuaiWidth, totalHeight, 2);
		ll_pohuaicao.addView(basePohuaiView, basePohuaiWidth, totalHeight);
		DuanzaoView baseFangyuiView = new DuanzaoView(getActivity(), false, baseFangyuWidth, totalHeight, 3);
		ll_fangyucao.addView(baseFangyuiView, baseFangyuWidth, totalHeight);
		DuanzaoView baseTiliView = new DuanzaoView(getActivity(), false, baseTiliWidth, totalHeight, 4);
		ll_tilicao.addView(baseTiliView, baseTiliWidth, totalHeight);
		DuanzaoView baseWushuangView = new DuanzaoView(getActivity(), false, baseWushuangWidth, totalHeight, 5);
		ll_wushuangcao.addView(baseWushuangView, baseWushuangWidth, totalHeight);

		//设置当前武器名称
		switch (currentWeaponRank) {
		case 0:
			tv_weaponName.setText(R1WeaponData.R1Names[currentWeaponType]);
			break;
		case 1:
			tv_weaponName.setText(R1WeaponData.R1Names[currentWeaponType]+"·改");
			break;
		case 2:
			tv_weaponName.setText(R3WeaponData.R3Names[currentWeaponType]);
			break;
		case 3:
			tv_weaponName.setText(R4WeaponData.R4Names[currentWeaponType]);
			break;
		case 4:
			tv_weaponName.setText(R5WeaponData.R5Names[currentWeaponType]);
			break;
		case 5:
			tv_weaponName.setText(R6WeaponData.R6Names[currentWeaponType]);
			break;
		case 6:
			tv_weaponName.setText(R7WeaponData.R7Names[currentWeaponType]);
			break;
		}
		//设置当前武器等级
		tv_rank.setText((currentWeaponRank+1)+"");
		//设置当前锻造
		tv_duanzaoData.setText(currentDuanzaozhi+"/"+maxDuanzaozhi);
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("武器锻造模拟器"); // 统计页面
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("武器锻造模拟器");
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
		if (adapterView == lv_weaponRank) {
			if (currentWeaponRank != position) {
				currentWeaponRank = position;
				weaponRankAdapter.setCurrentRank(currentWeaponRank);
				weaponRankAdapter.notifyDataSetChanged();
				this.clearDuanzao();
				this.setData();
			}
		} else if (adapterView == lv_weaponType) {
			if (currentWeaponType != position) {
				currentWeaponType = position;
				weaponTypeAdapter.setCurrentType(currentWeaponType);
				weaponTypeAdapter.notifyDataSetChanged();
				this.clearDuanzao();
				this.setData();
			}
		}

	}

	@Override
	public void onClick(View v) {
		// 攻击锻造上升按钮------------------------------------>
		if (v == tv_gongjiUp) {
			// 预先判断是否超过了最大锻造值
			if (calculateCurrentDuanzaoData() + (currentGongjiTime + 1) > maxDuanzaozhi) {
				Toast.makeText(getActivity(), "超过最大锻造值，不能再锻造了", Toast.LENGTH_LONG).show();
				return;
			}

			// 只有当锻造次数小于5的时候才允许进一步锻造该属性
			if (currentGongjiTime < 5) {
				// 添加攻击锻
				DuanzaoView riseGongjiView = new DuanzaoView(getActivity(), true, riseGongjiWidth, totalHeight, 1);
				ll_gongjicao.addView(riseGongjiView, riseGongjiWidth, totalHeight);
				// 当前攻击锻造次数增加1
				currentGongjiTime++;
			} else {
				Toast.makeText(getActivity(), "最大只能锻造5次", Toast.LENGTH_SHORT).show();
			}
		}
		// 攻击锻造下降按钮------------------------------------>
		else if (v == tv_gongjiDown) {
			// 只有当锻造次数小于5的时候才允许进一步锻造该属性
			if (currentGongjiTime == 0) {

			} else {
				// 移除攻击锻
				int index = ll_gongjicao.getChildCount();
				ll_gongjicao.removeViewAt(index - 1);
				// 当前攻击锻造次数减少1
				currentGongjiTime--;
			}
		}
		// 破坏锻造上升按钮------------------------------------>
		if (v == tv_pohuaiUp) {
			// 预先判断是否超过了最大锻造值
			if (calculateCurrentDuanzaoData() + (currentPohuaiTime + 1) > maxDuanzaozhi) {
				Toast.makeText(getActivity(), "超过最大锻造值，不能再锻造了", Toast.LENGTH_LONG).show();
				return;
			}

			// 只有当锻造次数小于5的时候才允许进一步锻造该属性
			if (currentPohuaiTime < 5) {
				// 添加攻击锻
				DuanzaoView risePohuaiView = new DuanzaoView(getActivity(), true, risePohuaiWidth, totalHeight, 2);
				ll_pohuaicao.addView(risePohuaiView, risePohuaiWidth, totalHeight);
				// 当前破坏锻造次数增加1
				currentPohuaiTime++;
			} else {
				Toast.makeText(getActivity(), "最大只能锻造5次", Toast.LENGTH_SHORT).show();
			}
		}
		// 破坏锻造下降按钮------------------------------------>
		else if (v == tv_pohuaiDown) {
			// 只有当锻造次数小于5的时候才允许进一步锻造该属性
			if (currentPohuaiTime == 0) {

			} else {
				// 移除破坏锻
				int index = ll_pohuaicao.getChildCount();
				ll_pohuaicao.removeViewAt(index - 1);
				// 当前破坏锻造次数减少1
				currentPohuaiTime--;
			}
		}
		// 防御锻造上升按钮------------------------------------>
		if (v == tv_fangyuUp) {
			// 预先判断是否超过了最大锻造值
			if (calculateCurrentDuanzaoData() + (currentFangyuTime + 1) > maxDuanzaozhi) {
				Toast.makeText(getActivity(), "超过最大锻造值，不能再锻造了", Toast.LENGTH_LONG).show();
				return;
			}

			// 只有当锻造次数小于5的时候才允许进一步锻造该属性
			if (currentFangyuTime < 5) {
				// 添加防御锻
				DuanzaoView riseFangyuView = new DuanzaoView(getActivity(), true, riseFangyuWidth, totalHeight, 3);
				ll_fangyucao.addView(riseFangyuView, riseFangyuWidth, totalHeight);
				// 当前防御锻造次数增加1
				currentFangyuTime++;
			} else {
				Toast.makeText(getActivity(), "最大只能锻造5次", Toast.LENGTH_SHORT).show();
			}
		}
		// 防御锻造下降按钮------------------------------------>
		else if (v == tv_fangyuDown) {
			// 只有当锻造次数小于5的时候才允许进一步锻造该属性
			if (currentFangyuTime == 0) {

			} else {
				// 移除防御锻
				int index = ll_fangyucao.getChildCount();
				ll_fangyucao.removeViewAt(index - 1);
				// 当前破坏锻造次数减少1
				currentFangyuTime--;
			}
		}
		// 体力锻造上升按钮------------------------------------>
		if (v == tv_tiliUp) {
			// 预先判断是否超过了最大锻造值
			if (calculateCurrentDuanzaoData() + (currentTiliTime + 1) > maxDuanzaozhi) {
				Toast.makeText(getActivity(), "超过最大锻造值，不能再锻造了", Toast.LENGTH_LONG).show();
				return;
			}

			// 只有当锻造次数小于5的时候才允许进一步锻造该属性
			if (currentTiliTime < 5) {
				// 添加体力锻
				DuanzaoView riseTiliView = new DuanzaoView(getActivity(), true, riseTiliWidth, totalHeight, 4);
				ll_tilicao.addView(riseTiliView, riseTiliWidth, totalHeight);
				// 当前体力锻造次数增加1
				currentTiliTime++;
			} else {
				Toast.makeText(getActivity(), "最大只能锻造5次", Toast.LENGTH_SHORT).show();
			}
		}
		// 体力锻造下降按钮------------------------------------>
		else if (v == tv_tiliDown) {
			// 只有当锻造次数小于5的时候才允许进一步锻造该属性
			if (currentTiliTime == 0) {

			} else {
				// 移除体力锻
				int index = ll_tilicao.getChildCount();
				ll_tilicao.removeViewAt(index - 1);
				// 当前体力锻造次数减少1
				currentTiliTime--;
			}
		}
		// 无双锻造上升按钮------------------------------------>
		if (v == tv_wushuangUp) {
			// 预先判断是否超过了最大锻造值
			if (calculateCurrentDuanzaoData() + (currentWushuangTime + 1) > maxDuanzaozhi) {
				Toast.makeText(getActivity(), "超过最大锻造值，不能再锻造了", Toast.LENGTH_LONG).show();
				return;
			}

			// 只有当锻造次数小于5的时候才允许进一步锻造该属性
			if (currentWushuangTime < 5) {
				// 添加无双锻
				DuanzaoView riseWushuangView = new DuanzaoView(getActivity(), true, riseWushuangWidth, totalHeight, 5);
				ll_wushuangcao.addView(riseWushuangView, riseWushuangWidth, totalHeight);
				// 当前无双锻造次数增加1
				currentWushuangTime++;
			} else {
				Toast.makeText(getActivity(), "最大只能锻造5次", Toast.LENGTH_SHORT).show();
			}
		}
		// 无双锻造下降按钮------------------------------------>
		else if (v == tv_wushuangDown) {
			// 只有当锻造次数小于5的时候才允许进一步锻造该属性
			if (currentWushuangTime == 0) {

			} else {
				// 移除无双锻
				int index = ll_wushuangcao.getChildCount();
				ll_wushuangcao.removeViewAt(index - 1);
				// 当前无双锻造次数减少1
				currentWushuangTime--;
			}
		}

		currentDuanzaozhi = calculateCurrentDuanzaoData();
		//设置当前锻造
		tv_duanzaoData.setText(currentDuanzaozhi+"/"+maxDuanzaozhi);
	}

	/** 计算当前已经达到的锻造数 */
	private int calculateCurrentDuanzaoData() {
		int data = 0;
		for (int i = 0; i <= currentGongjiTime; i++) {
			data += i;
		}
		for (int i = 0; i <= currentPohuaiTime; i++) {
			data += i;
		}
		for (int i = 0; i <= currentFangyuTime; i++) {
			data += i;
		}
		for (int i = 0; i <= currentTiliTime; i++) {
			data += i;
		}
		for (int i = 0; i <= currentWushuangTime; i++) {
			data += i;
		}
		System.out.println("当前总锻造值-------------》" + data);
		return data;
	}

	private void clearDuanzao() {
		// 把槽里的View都移除掉
		ll_gongjicao.removeAllViews();
		ll_pohuaicao.removeAllViews();
		ll_fangyucao.removeAllViews();
		ll_tilicao.removeAllViews();
		ll_wushuangcao.removeAllViews();
		// 恢复锻造相关数据
		currentDuanzaozhi = 0;
		currentGongjiTime = 0;
		currentPohuaiTime = 0;
		currentFangyuTime = 0;
		currentTiliTime = 0;
		currentWushuangTime = 0;
	}

}
