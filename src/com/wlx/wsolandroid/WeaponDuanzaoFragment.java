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
import com.wlx.wsolandroid.widget.TesuqianghuaView;

import android.R.integer;
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
import android.widget.Button;
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
	/** 模式切换按钮 */
	private Button bt_moshi;
	/** 显示当前模式的文字控件 */
	private TextView tv_currentMoshi;
	private static final int CURRENT_MOSHI_DUANZAO = 1001;
	private static final int CURRENT_MOSHI_TESUDUANZAO = 1002;
	/** 当前的模式 */
	private int currentMoshi = CURRENT_MOSHI_DUANZAO;

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
	/** 特殊攻击的容器 */
	private LinearLayout ll_tesucao_gongji;
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
	/** 特殊破坏的容器 */
	private LinearLayout ll_tesucao_pohuai;
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
	/** 特殊防御的容器 */
	private LinearLayout ll_tesucao_fangyu;
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
	/** 特殊体力的容器 */
	private LinearLayout ll_tesucao_tili;
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
	/** 特殊无双的容器 */
	private LinearLayout ll_tesucao_wushuang;
	// 移动相关控件--------------------------------
	private View include_yidong;
	/** 移动字样 */
	private TextView tv_yidong;
	/** 移动特殊强化上升按钮 */
	private TextView tv_yidongUp;
	/** 移动特殊强化下降按钮 */
	private TextView tv_yidongDown;
	/** 基础移动 */
	private TextView tv_base_yidong;
	/** 特殊移动 */
	private TextView tv_tesu_yidong;
	/** 移动条的容器 */
	private LinearLayout ll_yidongcao;
	// 跳跃相关控件--------------------------------
	private View include_tiaoyue;
	/** 跳跃字样 */
	private TextView tv_tiaoyue;
	/** 跳跃特殊强化上升按钮 */
	private TextView tv_tiaoyueUp;
	/** 跳跃特殊强化下降按钮 */
	private TextView tv_tiaoyueDown;
	/** 基础跳跃 */
	private TextView tv_base_tiaoyue;
	/** 特殊跳跃 */
	private TextView tv_tesu_tiaoyue;
	/** 跳跃条的容器 */
	private LinearLayout ll_tiaoyuecao;

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

	/** 当前攻击特殊强化次数 */
	private int currentTesuGongjiTime = 0;
	/** 当前破坏特殊强化次数 */
	private int currentTesuPohuaiTime = 0;
	/** 当前防御特殊强化次数 */
	private int currentTesuFangyuTime = 0;
	/** 当前体力特殊强化次数 */
	private int currentTesuTiliTime = 0;
	/** 当前无双特殊强化次数 */
	private int currentTesuWushuangTime = 0;
	/** 当前移动特殊强化次数 */
	private int currentTesuYidongTime = 0;
	/** 当前跳跃特殊强化次数 */
	private int currentTesuTiaoyueTime = 0;

	private int tesuWidth;
	private int tesuWidth2;
	private int tesuHeight;

	// 武器基础
	int baseGongji;
	int basePohuai;
	int baseFangyu;
	int baseTili;
	int baseWushuang;
	int baseYidong;
	int baseTiaoyue;
	// 武器上升值
	int riseGongji;
	int risePohuai;
	int riseFangyu;
	int riseTili;
	int riseWushuang;
	// 每一点数值所占的宽度
	float persentWidth;
	float persentWidthYidong;
	// 上升的长度
	int riseGongjiWidth;
	int risePohuaiWidth;
	int riseFangyuWidth;
	int riseTiliWidth;
	int riseWushuangWidth;
	// 属性槽的总长度
	int totalWidth;
	int totalWidthYidong;
	// 属性槽的高度
	int totalHeight;
	int totalHeightYidong;

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
		bt_moshi = (Button) view.findViewById(R.id.bt_changeMoshi);
		bt_moshi.setOnClickListener(this);
		tv_currentMoshi = (TextView) view.findViewById(R.id.tv_currentMoshi);
		tv_currentMoshi.setText("当前为锻造模式");

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
		// ll_gongjicao.setOrientation(LinearLayout.HORIZONTAL);
		ll_tesucao_gongji = (LinearLayout) include_gongji.findViewById(R.id.ll_teshuqianghua);
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
		ll_tesucao_pohuai = (LinearLayout) include_pohuai.findViewById(R.id.ll_teshuqianghua);
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
		ll_tesucao_fangyu = (LinearLayout) include_fangyu.findViewById(R.id.ll_teshuqianghua);
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
		ll_tesucao_tili = (LinearLayout) include_tili.findViewById(R.id.ll_teshuqianghua);
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
		ll_tesucao_wushuang = (LinearLayout) include_wushuang.findViewById(R.id.ll_teshuqianghua);
		// 初始化移动相关控件----------------------------
		include_yidong = view.findViewById(R.id.include_yidong);
		tv_yidong = (TextView) include_yidong.findViewById(R.id.tv_yidongli);
		tv_yidong.setText("移动");
		tv_yidongUp = (TextView) include_yidong.findViewById(R.id.tv_yidongUp);
		tv_yidongUp.setOnClickListener(this);
		tv_yidongDown = (TextView) include_yidong.findViewById(R.id.tv_yidongDown);
		tv_yidongDown.setOnClickListener(this);
		tv_base_yidong = (TextView) include_yidong.findViewById(R.id.tv_base_yidongli);
		tv_tesu_yidong = (TextView) include_yidong.findViewById(R.id.tv_tesu_yidongli);
		ll_yidongcao = (LinearLayout) include_yidong.findViewById(R.id.ll_yidongcao);
		// 初始化跳跃相关控件----------------------------
		include_tiaoyue = view.findViewById(R.id.include_tiaoyue);
		tv_tiaoyue = (TextView) include_tiaoyue.findViewById(R.id.tv_yidongli);
		tv_tiaoyue.setText("跳跃");
		tv_tiaoyueUp = (TextView) include_tiaoyue.findViewById(R.id.tv_yidongUp);
		tv_tiaoyueUp.setOnClickListener(this);
		tv_tiaoyueDown = (TextView) include_tiaoyue.findViewById(R.id.tv_yidongDown);
		tv_tiaoyueDown.setOnClickListener(this);
		tv_base_tiaoyue = (TextView) include_tiaoyue.findViewById(R.id.tv_base_yidongli);
		tv_tesu_tiaoyue = (TextView) include_tiaoyue.findViewById(R.id.tv_tesu_yidongli);
		ll_tiaoyuecao = (LinearLayout) include_tiaoyue.findViewById(R.id.ll_yidongcao);

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
		baseYidong = R1WeaponData.weaponBaseYidong[currentWeaponType];
		baseTiaoyue = R1WeaponData.weaponBaseTiaoyue[currentWeaponType];
		tv_base_gongjili.setText(baseGongji + "");
		tv_base_pohuaili.setText(basePohuai + "");
		tv_base_fangyuli.setText(baseFangyu + "");
		tv_base_tili.setText(baseTili + "");
		tv_base_wushuang.setText(baseWushuang + "");
		tv_base_yidong.setText(baseYidong + "");
		tv_base_tiaoyue.setText(baseTiaoyue + "");
		// 武器上升值
		riseGongji = R1WeaponData.R1RiseGongji[currentWeaponType] + currentWeaponRank * 3;
		risePohuai = R1WeaponData.R1RisePohuai[currentWeaponType] + currentWeaponRank * 3;
		riseFangyu = R1WeaponData.R1RiseFangyu[currentWeaponType] + currentWeaponRank * 3;
		riseTili = R1WeaponData.R1RiseTili[currentWeaponType] + currentWeaponRank * 3;
		riseWushuang = R1WeaponData.R1RiseWushuang[currentWeaponType] + currentWeaponRank * 3;
		tv_rise_gongjili.setText("( + " + riseGongji + " )");
		tv_rise_pohuaili.setText("( + " + risePohuai + " )");
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
		totalWidthYidong = ll_yidongcao.getWidth();
		totalHeightYidong = ll_yidongcao.getHeight();
		// 计算出特殊攻击点的宽和高
		tesuWidth = tv_gongjili.getWidth() / 4;
		tesuWidth2 = tv_gongjili.getWidth() / 16 * 5;
		tesuHeight = tesuWidth / 4;

		// 计算出每一点数值所占的宽度
		persentWidth = totalWidth / DuanzaoView.totalPoint;
		persentWidthYidong = totalWidthYidong / DuanzaoView.totalPointYidong;
		// 计算出基础值的长度
		int baseGongjiWidth = (int) (persentWidth * baseGongji);
		int basePohuaiWidth = (int) (persentWidth * basePohuai);
		int baseFangyuWidth = (int) (persentWidth * baseFangyu);
		int baseTiliWidth = (int) (persentWidth * baseTili);
		int baseWushuangWidth = (int) (persentWidth * baseWushuang);
		int baseYidongWidth = (int) (persentWidthYidong * baseYidong);
		int baseTiaoyueWidth = (int) (persentWidthYidong * baseTiaoyue);
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
		DuanzaoView baseYidongView = new DuanzaoView(getActivity(), false, baseYidongWidth, totalHeightYidong, 6);
		ll_yidongcao.addView(baseYidongView, baseYidongWidth, totalHeightYidong);
		DuanzaoView baseTiaoyueView = new DuanzaoView(getActivity(), false, baseTiaoyueWidth, totalHeightYidong, 6);
		ll_tiaoyuecao.addView(baseTiaoyueView, baseTiaoyueWidth, totalHeightYidong);

		// 设置当前武器名称
		switch (currentWeaponRank) {
		case 0:
			tv_weaponName.setText(R1WeaponData.R1Names[currentWeaponType]);
			break;
		case 1:
			tv_weaponName.setText(R1WeaponData.R1Names[currentWeaponType] + "·改");
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
		// 设置当前武器等级
		tv_rank.setText((currentWeaponRank + 1) + "");
		// 设置当前锻造
		tv_duanzaoData.setText(currentDuanzaozhi + "/" + maxDuanzaozhi);

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
		// 切换模式按钮----------------------------------------->
		if (v == bt_moshi) {
			if (currentMoshi == CURRENT_MOSHI_DUANZAO) {
				currentMoshi = CURRENT_MOSHI_TESUDUANZAO;
				tv_currentMoshi.setText("当前为特殊强化模式");
				tv_yidongUp.setVisibility(View.VISIBLE);
				tv_yidongDown.setVisibility(View.VISIBLE);
				tv_tiaoyueUp.setVisibility(View.VISIBLE);
				tv_tiaoyueDown.setVisibility(View.VISIBLE);
			} else if (currentMoshi == CURRENT_MOSHI_TESUDUANZAO) {
				currentMoshi = CURRENT_MOSHI_DUANZAO;
				tv_currentMoshi.setText("当前为锻造模式");
				tv_yidongUp.setVisibility(View.INVISIBLE);
				tv_yidongDown.setVisibility(View.INVISIBLE);
				tv_tiaoyueUp.setVisibility(View.INVISIBLE);
				tv_tiaoyueDown.setVisibility(View.INVISIBLE);
			}
			return;
		}

		// 攻击锻造上升按钮------------------------------------>
		if (v == tv_gongjiUp) {
			if (currentMoshi == CURRENT_MOSHI_TESUDUANZAO) {
				this.addTesuQianghua(1, currentTesuGongjiTime);
			} else if (currentMoshi == CURRENT_MOSHI_DUANZAO) {
				this.addQianghua(1, currentGongjiTime);
			}
		}
		// 攻击锻造下降按钮------------------------------------>
		else if (v == tv_gongjiDown) {
			if (currentMoshi == CURRENT_MOSHI_TESUDUANZAO) {
				this.removeTesuQianghua(1, currentTesuGongjiTime);
			} else if (currentMoshi == CURRENT_MOSHI_DUANZAO) {
				this.removeQianghua(1, currentGongjiTime);
			}
		}
		// 破坏锻造上升按钮------------------------------------>
		if (v == tv_pohuaiUp) {
			if (currentMoshi == CURRENT_MOSHI_TESUDUANZAO) {
				this.addTesuQianghua(2, currentTesuPohuaiTime);
			} else if (currentMoshi == CURRENT_MOSHI_DUANZAO) {
				this.addQianghua(2, currentPohuaiTime);
			}

		}
		// 破坏锻造下降按钮------------------------------------>
		else if (v == tv_pohuaiDown) {
			if (currentMoshi == CURRENT_MOSHI_TESUDUANZAO) {
				this.removeTesuQianghua(2, currentTesuPohuaiTime);
			} else if (currentMoshi == CURRENT_MOSHI_DUANZAO) {
				this.removeQianghua(2, currentPohuaiTime);
			}

		}
		// 防御锻造上升按钮------------------------------------>
		if (v == tv_fangyuUp) {
			if (currentMoshi == CURRENT_MOSHI_TESUDUANZAO) {
				this.addTesuQianghua(3, currentTesuFangyuTime);
			} else if (currentMoshi == CURRENT_MOSHI_DUANZAO) {
				this.addQianghua(3, currentFangyuTime);
			}

		}
		// 防御锻造下降按钮------------------------------------>
		else if (v == tv_fangyuDown) {
			if (currentMoshi == CURRENT_MOSHI_TESUDUANZAO) {
				this.removeTesuQianghua(3, currentTesuFangyuTime);
			} else if (currentMoshi == CURRENT_MOSHI_DUANZAO) {
				this.removeQianghua(3, currentFangyuTime);
			}

		}
		// 体力锻造上升按钮------------------------------------>
		if (v == tv_tiliUp) {
			if (currentMoshi == CURRENT_MOSHI_TESUDUANZAO) {
				this.addTesuQianghua(4, currentTesuTiliTime);
			} else if (currentMoshi == CURRENT_MOSHI_DUANZAO) {
				this.addQianghua(4, currentTiliTime);
			}

		}
		// 体力锻造下降按钮------------------------------------>
		else if (v == tv_tiliDown) {
			if (currentMoshi == CURRENT_MOSHI_TESUDUANZAO) {
				this.removeTesuQianghua(4, currentTesuTiliTime);
			} else if (currentMoshi == CURRENT_MOSHI_DUANZAO) {
				this.removeQianghua(4, currentTiliTime);
			}

		}
		// 无双锻造上升按钮------------------------------------>
		if (v == tv_wushuangUp) {
			if (currentMoshi == CURRENT_MOSHI_TESUDUANZAO) {
				this.addTesuQianghua(5, currentTesuWushuangTime);
			} else if (currentMoshi == CURRENT_MOSHI_DUANZAO) {
				this.addQianghua(5, currentWushuangTime);
			}
		}
		// 无双锻造下降按钮------------------------------------>
		else if (v == tv_wushuangDown) {
			if (currentMoshi == CURRENT_MOSHI_TESUDUANZAO) {
				this.removeTesuQianghua(5, currentTesuWushuangTime);
			} else if (currentMoshi == CURRENT_MOSHI_DUANZAO) {
				this.removeQianghua(5, currentWushuangTime);
			}
		}
		// 移动上升按钮------------------------------------>
		else if (v == tv_yidongUp) {
			if (currentMoshi == CURRENT_MOSHI_TESUDUANZAO) {
				this.addTesuQianghuaYidongOrTiaoyue(1, currentTesuYidongTime);
			}
		}
		// 移动下降按钮------------------------------------>
		else if (v == tv_yidongDown) {
			if (currentMoshi == CURRENT_MOSHI_TESUDUANZAO) {
				this.removeTesuQianghuaYidongOrTiaoyue(1, currentTesuYidongTime);
			}
		}
		// 跳跃上升按钮------------------------------------>
		else if (v == tv_tiaoyueUp) {
			if (currentMoshi == CURRENT_MOSHI_TESUDUANZAO) {
				this.addTesuQianghuaYidongOrTiaoyue(2, currentTesuTiaoyueTime);
			}
		}
		// 跳跃下降按钮------------------------------------>
		else if (v == tv_tiaoyueDown) {
			if (currentMoshi == CURRENT_MOSHI_TESUDUANZAO) {
				this.removeTesuQianghuaYidongOrTiaoyue(2, currentTesuTiaoyueTime);
			}
		}

	}

	/** 添加锻造强化 */
	private void addQianghua(int type, int currentDuanzaoTime) {

		// 预先判断是否超过了最大锻造值
		if (calculateCurrentDuanzaoData() + (currentDuanzaoTime + 1) > maxDuanzaozhi) {
			Toast.makeText(getActivity(), "超过最大锻造值，不能再锻造了", Toast.LENGTH_SHORT).show();
			return;
		}

		if (currentDuanzaoTime < 5) {

			switch (type) {
			case 1:
				// 添加攻击锻
				DuanzaoView riseGongjiView = new DuanzaoView(getActivity(), true, riseGongjiWidth, totalHeight, 1);
				ll_gongjicao.addView(riseGongjiView, riseGongjiWidth, totalHeight);
				// 当前攻击锻造次数增加1
				currentGongjiTime++;
				break;
			case 2:
				// 添加破坏锻
				DuanzaoView risePohuaiView = new DuanzaoView(getActivity(), true, risePohuaiWidth, totalHeight, 2);
				ll_pohuaicao.addView(risePohuaiView, risePohuaiWidth, totalHeight);
				// 当前破坏锻造次数增加1
				currentPohuaiTime++;
				break;
			case 3:
				// 添加防御锻
				DuanzaoView riseFangyuView = new DuanzaoView(getActivity(), true, riseFangyuWidth, totalHeight, 3);
				ll_fangyucao.addView(riseFangyuView, riseFangyuWidth, totalHeight);
				// 当前防御锻造次数增加1
				currentFangyuTime++;
				break;
			case 4:
				// 添加体力锻
				DuanzaoView riseTiliView = new DuanzaoView(getActivity(), true, riseTiliWidth, totalHeight, 4);
				ll_tilicao.addView(riseTiliView, riseTiliWidth, totalHeight);
				// 当前体力锻造次数增加1
				currentTiliTime++;
				break;
			case 5:
				// 添加无双锻
				DuanzaoView riseWushuangView = new DuanzaoView(getActivity(), true, riseWushuangWidth, totalHeight, 5);
				ll_wushuangcao.addView(riseWushuangView, riseWushuangWidth, totalHeight);
				// 当前无双锻造次数增加1
				currentWushuangTime++;
				break;

			}
			currentDuanzaozhi = calculateCurrentDuanzaoData();
			// 设置当前锻造
			tv_duanzaoData.setText(currentDuanzaozhi + "/" + maxDuanzaozhi);
		} else {
			Toast.makeText(getActivity(), "最大只能锻造5次", Toast.LENGTH_SHORT).show();
		}

	}

	/** 添加特殊强化(移动和跳跃) */
	private void addTesuQianghuaYidongOrTiaoyue(int type, int currentTesuTime) {
		if (currentTesuTime < 5) {

			switch (type) {
			case 1:
				baseYidong++;
				int baseYidongWidth = (int) (persentWidthYidong * baseYidong);
				DuanzaoView view_yidong = (DuanzaoView) ll_yidongcao.getChildAt(0);
				this.drawViewWidth(view_yidong, baseYidongWidth);
				view_yidong.changeWidth(baseYidongWidth);
				view_yidong.invalidate();
				currentTesuYidongTime++;
				tv_tesu_yidong.setVisibility(View.VISIBLE);
				tv_tesu_yidong.setText("+ "+currentTesuYidongTime);
				break;
			case 2:
				baseTiaoyue++;
				int baseTiaotueWidth = (int) (persentWidthYidong * baseTiaoyue);
				DuanzaoView view_tiaoyue = (DuanzaoView) ll_tiaoyuecao.getChildAt(0);
				this.drawViewWidth(view_tiaoyue, baseTiaotueWidth);
				view_tiaoyue.changeWidth(baseTiaotueWidth);
				view_tiaoyue.invalidate();
				// 当前特殊强化次数增加1
				currentTesuTiaoyueTime++;
				tv_tesu_tiaoyue.setVisibility(View.VISIBLE);
				tv_tesu_tiaoyue.setText("+ "+currentTesuTiaoyueTime);
				break;

			}
		} else {
			Toast.makeText(getActivity(), "只能特殊强化5次", Toast.LENGTH_SHORT).show();
		}

	}

	/** 减少特殊强化(移动和跳跃) */
	private void removeTesuQianghuaYidongOrTiaoyue(int type, int currentTesuTime) {
		if (currentTesuTime == 0) {
			
		}
		else{
			switch (type) {
			case 1:
				baseYidong--;
				int baseYidongWidth = (int) (persentWidthYidong * baseYidong);
				DuanzaoView view_yidong = (DuanzaoView) ll_yidongcao.getChildAt(0);
				this.drawViewWidth(view_yidong, baseYidongWidth);
				view_yidong.changeWidth(baseYidongWidth);
				view_yidong.invalidate();
				currentTesuYidongTime--;
				if (currentTesuYidongTime == 0) {
					tv_tesu_yidong.setVisibility(View.INVISIBLE);
				}
				tv_tesu_yidong.setText("+ "+currentTesuYidongTime);
				break;
			case 2:
				baseTiaoyue--;
				int baseTiaotueWidth = (int) (persentWidthYidong * baseTiaoyue);
				DuanzaoView view_tiaoyue = (DuanzaoView) ll_tiaoyuecao.getChildAt(0);
				this.drawViewWidth(view_tiaoyue, baseTiaotueWidth);
				view_tiaoyue.changeWidth(baseTiaotueWidth);
				view_tiaoyue.invalidate();
				// 当前特殊强化次数减少1
				currentTesuTiaoyueTime--;
				if (currentTesuTiaoyueTime == 0) {
					tv_tesu_tiaoyue.setVisibility(View.INVISIBLE);
				}
				tv_tesu_tiaoyue.setText("+ "+currentTesuTiaoyueTime);
				break;

			}
		} 
	}

	/** 添加特殊强化 */
	private void addTesuQianghua(int type, int currentTesuTime) {
		if (currentTesuTime < 4) {
			TesuqianghuaView tesuqianghuaView = new TesuqianghuaView(getActivity(), currentTesuTime, tesuWidth, tesuHeight);
			switch (type) {
			case 1:
				if (currentTesuTime == 0) {
					ll_tesucao_gongji.addView(tesuqianghuaView, tesuWidth, tesuHeight);
				} else {
					ll_tesucao_gongji.addView(tesuqianghuaView, tesuWidth2, tesuHeight);
				}
				// 当前特殊强化次数增加1
				currentTesuGongjiTime++;
				break;
			case 2:
				if (currentTesuTime == 0) {
					ll_tesucao_pohuai.addView(tesuqianghuaView, tesuWidth, tesuHeight);
				} else {
					ll_tesucao_pohuai.addView(tesuqianghuaView, tesuWidth2, tesuHeight);
				}
				// 当前特殊强化次数增加1
				currentTesuPohuaiTime++;
				break;
			case 3:
				if (currentTesuTime == 0) {
					ll_tesucao_fangyu.addView(tesuqianghuaView, tesuWidth, tesuHeight);
				} else {
					ll_tesucao_fangyu.addView(tesuqianghuaView, tesuWidth2, tesuHeight);
				}
				// 当前特殊强化次数增加1
				currentTesuFangyuTime++;
				break;
			case 4:
				if (currentTesuTime == 0) {
					ll_tesucao_tili.addView(tesuqianghuaView, tesuWidth, tesuHeight);
				} else {
					ll_tesucao_tili.addView(tesuqianghuaView, tesuWidth2, tesuHeight);
				}
				// 当前特殊强化次数增加1
				currentTesuTiliTime++;
				break;
			case 5:
				if (currentTesuTime == 0) {
					ll_tesucao_wushuang.addView(tesuqianghuaView, tesuWidth, tesuHeight);
				} else {
					ll_tesucao_wushuang.addView(tesuqianghuaView, tesuWidth2, tesuHeight);
				}
				// 当前特殊强化次数增加1
				currentTesuWushuangTime++;
				break;

			}
		} else {
			Toast.makeText(getActivity(), "只能特殊强化4次", Toast.LENGTH_SHORT).show();
		}

	}

	/** 移除锻造强化 */
	private void removeQianghua(int type, int currentDuanzaoTime) {
		if (currentDuanzaoTime == 0) {

		} else {
			int index;
			switch (type) {
			case 1:
				// 移除攻击锻
				index = ll_gongjicao.getChildCount();
				ll_gongjicao.removeViewAt(index - 1);
				// 当前攻击锻造次数减少1
				currentGongjiTime--;
				break;
			case 2:
				index = ll_pohuaicao.getChildCount();
				ll_pohuaicao.removeViewAt(index - 1);
				currentPohuaiTime--;
				break;
			case 3:
				index = ll_fangyucao.getChildCount();
				ll_fangyucao.removeViewAt(index - 1);
				currentFangyuTime--;
				break;
			case 4:
				index = ll_tilicao.getChildCount();
				ll_tilicao.removeViewAt(index - 1);
				currentTiliTime--;
				break;
			case 5:
				index = ll_wushuangcao.getChildCount();
				ll_wushuangcao.removeViewAt(index - 1);
				currentWushuangTime--;
				break;
			}
			currentDuanzaozhi = calculateCurrentDuanzaoData();
			// 设置当前锻造
			tv_duanzaoData.setText(currentDuanzaozhi + "/" + maxDuanzaozhi);
		}
	}

	/** 移除特殊强化 */
	private void removeTesuQianghua(int type, int currentTesuTime) {
		if (currentTesuTime == 0) {

		} else {
			int index;
			switch (type) {
			case 1:
				index = ll_tesucao_gongji.getChildCount();
				ll_tesucao_gongji.removeViewAt(index - 1);
				currentTesuGongjiTime--;
				break;
			case 2:
				index = ll_tesucao_pohuai.getChildCount();
				ll_tesucao_pohuai.removeViewAt(index - 1);
				currentTesuPohuaiTime--;
				break;
			case 3:
				index = ll_tesucao_fangyu.getChildCount();
				ll_tesucao_fangyu.removeViewAt(index - 1);
				currentTesuFangyuTime--;
				break;
			case 4:
				index = ll_tesucao_tili.getChildCount();
				ll_tesucao_tili.removeViewAt(index - 1);
				currentTesuTiliTime--;
				break;
			case 5:
				index = ll_tesucao_wushuang.getChildCount();
				ll_tesucao_wushuang.removeViewAt(index - 1);
				currentTesuWushuangTime--;
				break;
			}

		}
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
		ll_yidongcao.removeAllViews();
		ll_tiaoyuecao.removeAllViews();
		// 把特殊强化槽里的View都移除掉
		ll_tesucao_gongji.removeAllViews();
		ll_tesucao_pohuai.removeAllViews();
		ll_tesucao_fangyu.removeAllViews();
		ll_tesucao_tili.removeAllViews();
		ll_tesucao_wushuang.removeAllViews();
		// 恢复锻造相关数据
		currentDuanzaozhi = 0;
		currentGongjiTime = 0;
		currentPohuaiTime = 0;
		currentFangyuTime = 0;
		currentTiliTime = 0;
		currentWushuangTime = 0;
		// 恢复特殊强化相关数据
		currentTesuGongjiTime = 0;
		currentTesuPohuaiTime = 0;
		currentTesuFangyuTime = 0;
		currentTesuTiliTime = 0;
		currentTesuWushuangTime = 0;
	}

	private void drawViewWidth(View view, int width) {
		LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) view.getLayoutParams();
		linearParams.width = width;
		view.setLayoutParams(linearParams);
	}

}
