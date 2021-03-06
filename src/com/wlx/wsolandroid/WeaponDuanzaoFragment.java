package com.wlx.wsolandroid;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.adapter.WeaponRankAdapter;
import com.wlx.wsolandroid.adapter.WeaponTypeAdapter;
import com.wlx.wsolandroid.constant.Constant;
import com.wlx.wsolandroid.model.WeaponJinpai;
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
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
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
public class WeaponDuanzaoFragment extends BaseFragment implements
		OnClickListener, OnItemClickListener {
	private List<WeaponJinpai> weaponJinpais = new ArrayList<WeaponJinpai>();

	public static int upNarrowWidth;
	public static int upNarrowHeight;

	private MyActionBar actionBar;
	private PopupWindow pop;
	/** 手机屏幕的宽 */
	private int windowWidth;
	private ListView lv_weaponType;
	private WeaponTypeAdapter weaponTypeAdapter;

	private int currentWeaponRank = 4;
	/** 表示当前是什么武器，位置代表顺序，0：偃月刀 1大斧 ...... */
	private int currentWeaponType = 0;

	private TextView tv_clearData;
	/** 武器名字 */
	private TextView tv_weaponName;
	/** 武器级别 */
	private TextView tv_rank;
	/** 武器级别增加 */
	private TextView tv_rankUp;
	/** 武器级别减少 */
	private TextView tv_rankDown;

	/** 改造值 */
	private TextView tv_gaizaoData;
	/** 当前已经改造次数（最大改造次数是5） */
	private int currentGaizaoZhi = 0;

	/** 锻造值 */
	private TextView tv_duanzaoData;
	/** 锻造次数上升 */
	private TextView tv_duanzaoTimeUp;

	/** 锻造次数上升箭头 */
	private ImageView iv_upNarrow_duanzaoTime_rise;
	/** 攻击基础数值上升箭头 */
	private ImageView iv_upNarrow_baseRise_g;
	/** 破坏基础数值上升箭头 */
	private ImageView iv_upNarrow_baseRise_p;
	/** 防御基础数值上升箭头 */
	private ImageView iv_upNarrow_baseRise_f;
	/** 体力基础数值上升箭头 */
	private ImageView iv_upNarrow_baseRise_t;
	/** 无双基础数值上升箭头 */
	private ImageView iv_upNarrow_baseRise_w;

	/** 锻造模式 */
	private static final int CURRENT_MOSHI_DUANZAO = 1001;
	/** 特殊锻造模式 */
	private static final int CURRENT_MOSHI_TESUQIANGHUA = 1002;
	/** 改造模式 */
	private static final int CURRENT_MOSHI_GAIZAO = 1003;
	/** 传说化模式 */
	private static final int CURRENT_MOSHI_CHUANSHUOHUA = 1004;
	/** 当前的模式 */
	private int currentMoshi = CURRENT_MOSHI_DUANZAO;

	/** 原始金牌 */
	private static final int YUANSHI_JINPAI = 2001;
	/** 原始25 */
	private static final int YUANSHI_25 = 2002;
	/** 原始26 */
	private static final int YUANSHI_26 = 2003;
	/** 原始28 */
	private static final int YUANSHI_28 = 2004;
	/** 当前原始 */
	private int currentYuanshi = YUANSHI_JINPAI;

	/** 真改 */
	private static final int ZHENGAI = 3001;
	/** 简改 */
	private static final int JIANGAI = 3002;
	/** 当前改造方式(真改，简改) */
	private int currentGaizao_type = ZHENGAI;
	/** 改造上升值 */
	private static final int GAIZAO_SHANGSHENGZHI = 4001;
	/** 改造基础值 */
	private static final int GAIZAO_JICHUZHI = 4002;
	/** 当前改造方式2(上升值，基础值) */
	private int currentGaizao_type2 = GAIZAO_SHANGSHENGZHI;

	/** 无传说化 */
	private static final int NO_CHUANSHUOHUA = 5000;
	/** 尖晶1阶 */
	private static final int JIANJING1 = 5001;
	/** 尖晶2阶 */
	private static final int JIANJING2 = 5002;
	/** 尖晶3阶 */
	private static final int JIANJING3 = 5003;
	/** 琉璃1阶 */
	private static final int LIULI1 = 5101;
	/** 琉璃2阶 */
	private static final int LIULI2 = 5102;
	/** 琉璃3阶 */
	private static final int LIULI3 = 5103;
	/** 玉滴1阶 */
	private static final int YUDI1 = 5201;
	/** 玉滴2阶 */
	private static final int YUDI2 = 5202;
	/** 玉滴3阶 */
	private static final int YUDI3 = 5203;
	/** 当前传说化状态 */
	private int current_chuanshuohua = NO_CHUANSHUOHUA;

	private int chuanshuohua_rise_g = 0;
	private int chuanshuohua_rise_p = 0;
	private int chuanshuohua_rise_f = 0;
	private int chuanshuohua_rise_t = 0;
	private int chuanshuohua_rise_w = 0;
	private int gaizao_base_rise_g = 0;
	private int gaizao_base_rise_p = 0;
	private int gaizao_base_rise_f = 0;
	private int gaizao_base_rise_t = 0;
	private int gaizao_base_rise_w = 0;
	private int gaizao_base_rise_move = 0;
	private int gaizao_base_rise_jump = 0;
	private int tesu_base_rise_move = 0;
	private int tesu_base_rise_jump = 0;

	// 模式切换菜单
	private TextView tv_moshi_duanzao;
	private TextView tv_moshi_tesuqianghua;
	private TextView tv_moshi_gaizao;
	private TextView tv_moshi_chuanshuohua;
	// 原始多少段菜单
	private LinearLayout ll_yuanshi;
	private TextView tv_yuanshi_jinpai;
	private TextView tv_yuanshi_25;
	private TextView tv_yuanshi_26;
	private TextView tv_yuanshi_28;
	// 简改真改
	private LinearLayout ll_gaizaoMenu;
	private TextView tv_jiangai;
	private TextView tv_zhengai;
	private TextView tv_gaizao_shangshengzhi;
	private TextView tv_gaizao_jichuzhi;
	// 传说化
	private LinearLayout ll_chuanshuohua_menu;
	private TextView tv_jianjing1;
	private TextView tv_jianjing2;
	private TextView tv_jianjing3;
	private TextView tv_liuli1;
	private TextView tv_liuli2;
	private TextView tv_liuli3;
	private TextView tv_yudi1;
	private TextView tv_yudi2;
	private TextView tv_yudi3;
	// 传说化的数值
	private TextView tv_chuanshuohua_data_g;
	private TextView tv_chuanshuohua_data_p;
	private TextView tv_chuanshuohua_data_f;
	private TextView tv_chuanshuohua_data_t;
	private TextView tv_chuanshuohua_data_w;
	// 总数值
	private TextView tv_totalG;
	private TextView tv_totalP;
	private TextView tv_totalF;
	private TextView tv_totalT;
	private TextView tv_totalW;
	private TextView tv_totalMove;
	private TextView tv_totalJump;

	// 攻击相关控件--------------------------------
	private View include_gongji;
	/** 攻击力字样 */
	private TextView tv_gongjili;
	/** 攻击锻造上升按钮 */
	private TextView tv_gongjiUp;

	/** 基础攻击力 */
	private TextView tv_base_gongjili;
	/** 攻击力上升值 */
	private TextView tv_rise_gongjili;
	/** 攻击力上升箭头 */
	private ImageView iv_rise_upNarrow_gongji;
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

	/** 基础破坏力 */
	private TextView tv_base_pohuaili;
	/** 破坏力上升值 */
	private TextView tv_rise_pohuaili;
	/** 破坏力上升箭头 */
	private ImageView iv_rise_upNarrow_pohuai;
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

	/** 基础防御力 */
	private TextView tv_base_fangyuli;
	/** 防御力上升值 */
	private TextView tv_rise_fangyuli;
	/** 防御力上升箭头 */
	private ImageView iv_rise_upNarrow_fangyu;
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

	/** 基础体力 */
	private TextView tv_base_tili;
	/** 体力上升值 */
	private TextView tv_rise_tili;
	/** 体力力上升箭头 */
	private ImageView iv_rise_upNarrow_tili;
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

	/** 基础无双 */
	private TextView tv_base_wushuang;
	/** 无双上升值 */
	private TextView tv_rise_wushuang;
	/** 无双上升箭头 */
	private ImageView iv_rise_upNarrow_wushuang;
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

	/** 基础移动 */
	private TextView tv_base_yidong;
	/** 特殊移动 */
	private TextView tv_tesu_yidong;
	/** 移动条的容器 */
	private LinearLayout ll_yidongcao;
	/** 移动特殊上升箭头 */
	private ImageView iv_upNarrow_yidong;
	// 跳跃相关控件--------------------------------
	private View include_tiaoyue;
	/** 跳跃字样 */
	private TextView tv_tiaoyue;
	/** 跳跃特殊强化上升按钮 */
	private TextView tv_tiaoyueUp;

	/** 基础跳跃 */
	private TextView tv_base_tiaoyue;
	/** 特殊跳跃 */
	private TextView tv_tesu_tiaoyue;
	/** 跳跃条的容器 */
	private LinearLayout ll_tiaoyuecao;
	/** 跳跃特殊上升箭头 */
	private ImageView iv_upNarrow_tiaoyue;

	/** 最大锻造值 */
	private int maxDuanzaozhi = 24;
	/** 上升了多少锻造值 */
	private int risedDuanzaozhi = 0;
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

	/** 特殊上升宽度（第1个的宽度） */
	private int tesuWidth;
	/** 特殊上升宽度（第2，3，4个的宽度，因为有左边看不到的部分） */
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
	// 和原来相比上升了多少点
	int risedGongji = 0;
	int risedPohuai = 0;
	int risedFangyu = 0;
	int risedTili = 0;
	int risedWushuang = 0;
	// 每一点数值所占的宽度
	float persentWidth;
	float persentWidthYidong;
	// 上升的长度
	int riseGongjiWidth;
	int risePohuaiWidth;
	int riseFangyuWidth;
	int riseTiliWidth;
	int riseWushuangWidth;
	// 基础上升的长度
	int baseGongjiWidth;
	int basePohuaiWidth;
	int baseFangyuWidth;
	int baseTiliWidth;
	int baseWushuangWidth;
	int baseYidongWidth;
	int baseTiaoyueWidth;

	// 属性槽的总长度
	int totalWidth;
	int totalWidthYidong;
	// 属性槽的高度
	int totalHeight;
	int totalHeightYidong;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_weapon_duanzao, null);
		this.initActionBar(view);
		this.initView(view);
		return view;
	}

	private void initView(View view) {
		tv_weaponName = (TextView) view.findViewById(R.id.tv_weaponName);
		tv_clearData = (TextView) view.findViewById(R.id.tv_clearData);
		tv_clearData.setOnClickListener(this);
		tv_rank = (TextView) view.findViewById(R.id.tv_rank);
		tv_rankUp = (TextView) view.findViewById(R.id.tv_rankUp);
		tv_rankUp.setOnClickListener(this);
		tv_rankDown = (TextView) view.findViewById(R.id.tv_rankDown);
		tv_rankDown.setOnClickListener(this);
		tv_gaizaoData = (TextView) view.findViewById(R.id.tv_gaizaoData);
		tv_duanzaoData = (TextView) view.findViewById(R.id.tv_duanzaoData);
		tv_duanzaoTimeUp = (TextView) view.findViewById(R.id.tv_duanzaoTimeUp);
		tv_duanzaoTimeUp.setOnClickListener(this);

		iv_upNarrow_duanzaoTime_rise = (ImageView) view
				.findViewById(R.id.iv_upNarrow_duanzaoTime_rise);

		tv_moshi_duanzao = (TextView) view.findViewById(R.id.tv_moshi_duanzao);
		tv_moshi_duanzao.setOnClickListener(this);
		tv_moshi_tesuqianghua = (TextView) view
				.findViewById(R.id.tv_moshi_tesuqianghua);
		tv_moshi_tesuqianghua.setOnClickListener(this);
		tv_moshi_gaizao = (TextView) view.findViewById(R.id.tv_moshi_gaizao);
		tv_moshi_gaizao.setOnClickListener(this);
		tv_moshi_chuanshuohua = (TextView) view
				.findViewById(R.id.tv_moshi_chuanshuohua);
		tv_moshi_chuanshuohua.setOnClickListener(this);

		// 原始多少段菜单相关控件----------------------------
		ll_yuanshi = (LinearLayout) view.findViewById(R.id.ll_yuanshi);
		tv_yuanshi_jinpai = (TextView) view
				.findViewById(R.id.tv_yuanshi_jinpai);
		tv_yuanshi_jinpai.setOnClickListener(this);
		tv_yuanshi_25 = (TextView) view.findViewById(R.id.tv_yuanshi_25);
		tv_yuanshi_25.setOnClickListener(this);
		tv_yuanshi_26 = (TextView) view.findViewById(R.id.tv_yuanshi_26);
		tv_yuanshi_26.setOnClickListener(this);
		tv_yuanshi_28 = (TextView) view.findViewById(R.id.tv_yuanshi_28);
		tv_yuanshi_28.setOnClickListener(this);

		// 简改真改菜单相关控件----------------------------
		ll_gaizaoMenu = (LinearLayout) view.findViewById(R.id.ll_gaizaoMenu);
		tv_jiangai = (TextView) view.findViewById(R.id.tv_jiangai);
		tv_jiangai.setOnClickListener(this);
		tv_zhengai = (TextView) view.findViewById(R.id.tv_zhengai);
		tv_zhengai.setOnClickListener(this);
		tv_gaizao_shangshengzhi = (TextView) view
				.findViewById(R.id.tv_gaizao_shangshengzhi);
		tv_gaizao_shangshengzhi.setOnClickListener(this);
		tv_gaizao_jichuzhi = (TextView) view
				.findViewById(R.id.tv_gaizao_jichuzhi);
		tv_gaizao_jichuzhi.setOnClickListener(this);

		// 传说化菜单相关控件----------------------------
		ll_chuanshuohua_menu = (LinearLayout) view
				.findViewById(R.id.ll_chuanshuohua_menu);
		tv_jianjing1 = (TextView) view.findViewById(R.id.tv_jianjing1);
		tv_jianjing1.setOnClickListener(this);
		tv_jianjing2 = (TextView) view.findViewById(R.id.tv_jianjing2);
		tv_jianjing2.setOnClickListener(this);
		tv_jianjing3 = (TextView) view.findViewById(R.id.tv_jianjing3);
		tv_jianjing3.setOnClickListener(this);
		tv_liuli1 = (TextView) view.findViewById(R.id.tv_liuli1);
		tv_liuli1.setOnClickListener(this);
		tv_liuli2 = (TextView) view.findViewById(R.id.tv_liuli2);
		tv_liuli2.setOnClickListener(this);
		tv_liuli3 = (TextView) view.findViewById(R.id.tv_liuli3);
		tv_liuli3.setOnClickListener(this);
		tv_yudi1 = (TextView) view.findViewById(R.id.tv_yudi1);
		tv_yudi1.setOnClickListener(this);
		tv_yudi2 = (TextView) view.findViewById(R.id.tv_yudi2);
		tv_yudi2.setOnClickListener(this);
		tv_yudi3 = (TextView) view.findViewById(R.id.tv_yudi3);
		tv_yudi3.setOnClickListener(this);
		// 总数值控件
		tv_totalG = (TextView) view.findViewById(R.id.tv_totalG);
		tv_totalP = (TextView) view.findViewById(R.id.tv_totalP);
		tv_totalF = (TextView) view.findViewById(R.id.tv_totalF);
		tv_totalT = (TextView) view.findViewById(R.id.tv_totalT);
		tv_totalW = (TextView) view.findViewById(R.id.tv_totalW);
		tv_totalMove = (TextView) view.findViewById(R.id.tv_totalMove);
		tv_totalJump = (TextView) view.findViewById(R.id.tv_totalJump);

		// 初始化攻击力相关控件----------------------------
		include_gongji = view.findViewById(R.id.include_gongji);
		tv_chuanshuohua_data_g = (TextView) include_gongji
				.findViewById(R.id.tv_chuanshuohua_data);
		iv_upNarrow_baseRise_g = (ImageView) include_gongji
				.findViewById(R.id.iv_upNarrow_baseRise);
		tv_gongjili = (TextView) include_gongji.findViewById(R.id.tv_gongjili);
		tv_gongjili.setText("攻击力");
		tv_gongjiUp = (TextView) include_gongji.findViewById(R.id.tv_gongjiUp);
		tv_gongjiUp.setOnClickListener(this);
		tv_base_gongjili = (TextView) include_gongji
				.findViewById(R.id.tv_base_gongjili);
		tv_rise_gongjili = (TextView) include_gongji
				.findViewById(R.id.tv_rise_gongjili);
		iv_rise_upNarrow_gongji = (ImageView) include_gongji
				.findViewById(R.id.iv_upNarrow_gongji_rise);
		tv_slotNumber_gongji = (TextView) include_gongji
				.findViewById(R.id.tv_slotNumber_gongji);
		ll_gongjicao = (LinearLayout) include_gongji
				.findViewById(R.id.ll_gongjicao);
		// ll_gongjicao.setOrientation(LinearLayout.HORIZONTAL);
		ll_tesucao_gongji = (LinearLayout) include_gongji
				.findViewById(R.id.ll_teshuqianghua);
		// 初始化破坏力相关控件----------------------------

		include_pohuai = view.findViewById(R.id.include_pohuai);
		tv_chuanshuohua_data_p = (TextView) include_pohuai
				.findViewById(R.id.tv_chuanshuohua_data);

		iv_upNarrow_baseRise_p = (ImageView) include_pohuai
				.findViewById(R.id.iv_upNarrow_baseRise);
		tv_pohuaili = (TextView) include_pohuai.findViewById(R.id.tv_gongjili);
		tv_pohuaili.setText("破坏力");
		tv_pohuaiUp = (TextView) include_pohuai.findViewById(R.id.tv_gongjiUp);
		tv_pohuaiUp.setOnClickListener(this);

		tv_base_pohuaili = (TextView) include_pohuai
				.findViewById(R.id.tv_base_gongjili);
		tv_rise_pohuaili = (TextView) include_pohuai
				.findViewById(R.id.tv_rise_gongjili);
		iv_rise_upNarrow_pohuai = (ImageView) include_pohuai
				.findViewById(R.id.iv_upNarrow_gongji_rise);
		tv_slotNumber_pohuai = (TextView) include_pohuai
				.findViewById(R.id.tv_slotNumber_gongji);
		ll_pohuaicao = (LinearLayout) include_pohuai
				.findViewById(R.id.ll_gongjicao);
		ll_tesucao_pohuai = (LinearLayout) include_pohuai
				.findViewById(R.id.ll_teshuqianghua);
		// 初始化防御力相关控件----------------------------

		include_fangyu = view.findViewById(R.id.include_fangyu);
		tv_chuanshuohua_data_f = (TextView) include_fangyu
				.findViewById(R.id.tv_chuanshuohua_data);

		iv_upNarrow_baseRise_f = (ImageView) include_fangyu
				.findViewById(R.id.iv_upNarrow_baseRise);
		tv_fangyuli = (TextView) include_fangyu.findViewById(R.id.tv_gongjili);
		tv_fangyuli.setText("防御力");
		tv_fangyuUp = (TextView) include_fangyu.findViewById(R.id.tv_gongjiUp);
		tv_fangyuUp.setOnClickListener(this);

		tv_base_fangyuli = (TextView) include_fangyu
				.findViewById(R.id.tv_base_gongjili);
		tv_rise_fangyuli = (TextView) include_fangyu
				.findViewById(R.id.tv_rise_gongjili);
		iv_rise_upNarrow_fangyu = (ImageView) include_fangyu
				.findViewById(R.id.iv_upNarrow_gongji_rise);
		tv_slotNumber_fangyu = (TextView) include_fangyu
				.findViewById(R.id.tv_slotNumber_gongji);
		ll_fangyucao = (LinearLayout) include_fangyu
				.findViewById(R.id.ll_gongjicao);
		ll_tesucao_fangyu = (LinearLayout) include_fangyu
				.findViewById(R.id.ll_teshuqianghua);
		// 初始化体力相关控件----------------------------

		include_tili = view.findViewById(R.id.include_tili);
		tv_chuanshuohua_data_t = (TextView) include_tili
				.findViewById(R.id.tv_chuanshuohua_data);

		iv_upNarrow_baseRise_t = (ImageView) include_tili
				.findViewById(R.id.iv_upNarrow_baseRise);
		tv_tili = (TextView) include_tili.findViewById(R.id.tv_gongjili);
		tv_tili.setText("体力    ");
		tv_tiliUp = (TextView) include_tili.findViewById(R.id.tv_gongjiUp);
		tv_tiliUp.setOnClickListener(this);

		tv_base_tili = (TextView) include_tili
				.findViewById(R.id.tv_base_gongjili);
		tv_rise_tili = (TextView) include_tili
				.findViewById(R.id.tv_rise_gongjili);
		iv_rise_upNarrow_tili = (ImageView) include_tili
				.findViewById(R.id.iv_upNarrow_gongji_rise);
		tv_slotNumber_tili = (TextView) include_tili
				.findViewById(R.id.tv_slotNumber_gongji);
		ll_tilicao = (LinearLayout) include_tili
				.findViewById(R.id.ll_gongjicao);
		ll_tesucao_tili = (LinearLayout) include_tili
				.findViewById(R.id.ll_teshuqianghua);
		// 初始化无双相关控件----------------------------

		include_wushuang = view.findViewById(R.id.include_wushuang);
		tv_chuanshuohua_data_w = (TextView) include_wushuang
				.findViewById(R.id.tv_chuanshuohua_data);

		iv_upNarrow_baseRise_w = (ImageView) include_wushuang
				.findViewById(R.id.iv_upNarrow_baseRise);
		tv_wushuang = (TextView) include_wushuang
				.findViewById(R.id.tv_gongjili);
		tv_wushuang.setText("无双    ");
		tv_wushuangUp = (TextView) include_wushuang
				.findViewById(R.id.tv_gongjiUp);
		tv_wushuangUp.setOnClickListener(this);

		tv_base_wushuang = (TextView) include_wushuang
				.findViewById(R.id.tv_base_gongjili);
		tv_rise_wushuang = (TextView) include_wushuang
				.findViewById(R.id.tv_rise_gongjili);
		iv_rise_upNarrow_wushuang = (ImageView) include_wushuang
				.findViewById(R.id.iv_upNarrow_gongji_rise);
		tv_slotNumber_wushuang = (TextView) include_wushuang
				.findViewById(R.id.tv_slotNumber_gongji);
		ll_wushuangcao = (LinearLayout) include_wushuang
				.findViewById(R.id.ll_gongjicao);
		ll_tesucao_wushuang = (LinearLayout) include_wushuang
				.findViewById(R.id.ll_teshuqianghua);
		// 初始化移动相关控件----------------------------
		include_yidong = view.findViewById(R.id.include_yidong);
		tv_yidong = (TextView) include_yidong.findViewById(R.id.tv_yidongli);
		tv_yidong.setText("移动");
		tv_yidongUp = (TextView) include_yidong.findViewById(R.id.tv_yidongUp);
		tv_yidongUp.setOnClickListener(this);

		tv_base_yidong = (TextView) include_yidong
				.findViewById(R.id.tv_base_yidongli);
		tv_tesu_yidong = (TextView) include_yidong
				.findViewById(R.id.tv_tesu_yidongli);
		ll_yidongcao = (LinearLayout) include_yidong
				.findViewById(R.id.ll_yidongcao);
		iv_upNarrow_yidong = (ImageView) include_yidong
				.findViewById(R.id.iv_upNarrow);
		// 初始化跳跃相关控件----------------------------
		include_tiaoyue = view.findViewById(R.id.include_tiaoyue);
		tv_tiaoyue = (TextView) include_tiaoyue.findViewById(R.id.tv_yidongli);
		tv_tiaoyue.setText("跳跃");
		tv_tiaoyueUp = (TextView) include_tiaoyue
				.findViewById(R.id.tv_yidongUp);
		tv_tiaoyueUp.setOnClickListener(this);

		tv_base_tiaoyue = (TextView) include_tiaoyue
				.findViewById(R.id.tv_base_yidongli);
		tv_tesu_tiaoyue = (TextView) include_tiaoyue
				.findViewById(R.id.tv_tesu_yidongli);
		ll_tiaoyuecao = (LinearLayout) include_tiaoyue
				.findViewById(R.id.ll_yidongcao);
		iv_upNarrow_tiaoyue = (ImageView) include_tiaoyue
				.findViewById(R.id.iv_upNarrow);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// 初始化pop
				View view = LayoutInflater.from(getActivity()).inflate(
						R.layout.pop_choose_weapon, null);
				weaponTypeAdapter = new WeaponTypeAdapter(getActivity(),
						weaponJinpais);
				lv_weaponType = (ListView) view
						.findViewById(R.id.lv_weaponType);
				lv_weaponType.setAdapter(weaponTypeAdapter);
				lv_weaponType
						.setOnItemClickListener(WeaponDuanzaoFragment.this);

				windowWidth = Utils.getDisplay(getActivity(), 1);
				pop = new PopupWindow(view, windowWidth / 3 * 1,
						LayoutParams.WRAP_CONTENT, true);
				pop.setBackgroundDrawable(new BitmapDrawable()); // 点击手机返回键可以取消掉popupwindow
				pop.setOutsideTouchable(true); // 设置点击窗口外边窗口消失

				SharedPreferences sp = getActivity().getSharedPreferences(
						Constant.SP_NAME, Activity.MODE_PRIVATE);

				String sp_jinpaiData = sp
						.getString(Constant.SP_JINPAI_DATA, "");
				if (TextUtils.isEmpty(sp_jinpaiData)) {
					loadJinpaiData();
				} else {
					weaponJinpais.clear();
					weaponJinpais.addAll(JSON.parseObject(sp_jinpaiData,
							new TypeReference<List<WeaponJinpai>>() {
							}));
					weaponTypeAdapter.notifyDataSetChanged();
					setData();
					setTotalData();
				}

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
					int offsetY = actionBar.getHeight() / 3 * 4;
					pop.showAsDropDown(
							getActivity().findViewById(R.id.rl_actionbar),
							offsetX, offsetY);
				}
			}
		});

		RelativeLayout actionbar = (RelativeLayout) view
				.findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	private void setData() {
		WeaponJinpai weaponJinpai = weaponJinpais.get(currentWeaponType);
		// 武器基础值
		baseGongji = weaponJinpai.getG_base();
		basePohuai = weaponJinpai.getP_base();
		baseFangyu = weaponJinpai.getF_base();
		baseTili = weaponJinpai.getT_base();
		baseWushuang = weaponJinpai.getW_base();
		baseYidong = weaponJinpai.getMove();
		baseTiaoyue = weaponJinpai.getJump();
		tv_base_gongjili.setText(baseGongji + "");
		tv_base_pohuaili.setText(basePohuai + "");
		tv_base_fangyuli.setText(baseFangyu + "");
		tv_base_tili.setText(baseTili + "");
		tv_base_wushuang.setText(baseWushuang + "");
		tv_base_yidong.setText(baseYidong + "");
		tv_base_tiaoyue.setText(baseTiaoyue + "");
		// 武器上升值
		riseGongji = weaponJinpai.getG() + currentWeaponRank * 3;
		risePohuai = weaponJinpai.getP() + currentWeaponRank * 3;
		riseFangyu = weaponJinpai.getF() + currentWeaponRank * 3;
		riseTili = weaponJinpai.getT() + currentWeaponRank * 3;
		riseWushuang = weaponJinpai.getW() + currentWeaponRank * 3;

		operateRiseDateText(1);
		operateRiseDateText(2);
		operateRiseDateText(3);
		operateRiseDateText(4);
		operateRiseDateText(5);

		// 强化位
		tv_slotNumber_gongji.setText(weaponJinpai.getSlot_g() + "");
		tv_slotNumber_pohuai.setText(weaponJinpai.getSlot_p() + "");
		tv_slotNumber_fangyu.setText(weaponJinpai.getSlot_f() + "");
		tv_slotNumber_tili.setText(weaponJinpai.getSlot_t() + "");
		tv_slotNumber_wushuang.setText(weaponJinpai.getSlot_w() + "");

		upNarrowWidth = tv_base_gongjili.getWidth() / 2;
		upNarrowHeight = tv_base_gongjili.getHeight();

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
		baseGongjiWidth = (int) (persentWidth * baseGongji);
		basePohuaiWidth = (int) (persentWidth * basePohuai);
		baseFangyuWidth = (int) (persentWidth * baseFangyu);
		baseTiliWidth = (int) (persentWidth * baseTili);
		baseWushuangWidth = (int) (persentWidth * baseWushuang);
		baseYidongWidth = (int) (persentWidthYidong * baseYidong);
		baseTiaoyueWidth = (int) (persentWidthYidong * baseTiaoyue);
		// 计算出上升值的长度
		riseGongjiWidth = (int) (persentWidth * riseGongji);
		risePohuaiWidth = (int) (persentWidth * risePohuai);
		riseFangyuWidth = (int) (persentWidth * riseFangyu);
		riseTiliWidth = (int) (persentWidth * riseTili);
		riseWushuangWidth = (int) (persentWidth * riseWushuang);
		// 添加基础View
		DuanzaoView baseGongjiView = new DuanzaoView(getActivity(), false,
				baseGongjiWidth, totalHeight, 1);
		ll_gongjicao.addView(baseGongjiView, baseGongjiWidth, totalHeight);
		DuanzaoView basePohuaiView = new DuanzaoView(getActivity(), false,
				basePohuaiWidth, totalHeight, 2);
		ll_pohuaicao.addView(basePohuaiView, basePohuaiWidth, totalHeight);
		DuanzaoView baseFangyuiView = new DuanzaoView(getActivity(), false,
				baseFangyuWidth, totalHeight, 3);
		ll_fangyucao.addView(baseFangyuiView, baseFangyuWidth, totalHeight);
		DuanzaoView baseTiliView = new DuanzaoView(getActivity(), false,
				baseTiliWidth, totalHeight, 4);
		ll_tilicao.addView(baseTiliView, baseTiliWidth, totalHeight);
		DuanzaoView baseWushuangView = new DuanzaoView(getActivity(), false,
				baseWushuangWidth, totalHeight, 5);
		ll_wushuangcao
				.addView(baseWushuangView, baseWushuangWidth, totalHeight);
		DuanzaoView baseYidongView = new DuanzaoView(getActivity(), false,
				baseYidongWidth, totalHeightYidong, 6);
		ll_yidongcao
				.addView(baseYidongView, baseYidongWidth, totalHeightYidong);
		DuanzaoView baseTiaoyueView = new DuanzaoView(getActivity(), false,
				baseTiaoyueWidth, totalHeightYidong, 6);
		ll_tiaoyuecao.addView(baseTiaoyueView, baseTiaoyueWidth,
				totalHeightYidong);

		// 设置当前武器名称和最大锻造值
		switch (currentWeaponRank) {
		case 0:
			tv_weaponName.setText(weaponJinpai.getName());
			maxDuanzaozhi = 12;
			break;
		case 1:
			tv_weaponName.setText(weaponJinpai.getName() + "·改");
			maxDuanzaozhi = 16;
			break;
		case 2:
			tv_weaponName.setText(weaponJinpai.getWeaponNameR3());
			maxDuanzaozhi = 20;
			break;
		case 3:
			tv_weaponName.setText(weaponJinpai.getWeaponNameR4());
			maxDuanzaozhi = 24;
			break;
		case 4:
			tv_weaponName.setText(weaponJinpai.getWeaponNameR5());
			maxDuanzaozhi = 24;
			break;
		case 5:
			tv_weaponName.setText("真·" + weaponJinpai.getWeaponNameR5());
			maxDuanzaozhi = 24;
			break;
		case 6:
			tv_weaponName.setText(weaponJinpai.getWeaponNameR7());
			maxDuanzaozhi = 24;
			break;
		}
		// 设置当前武器等级
		tv_rank.setText((currentWeaponRank + 1) + "");
		// 设置当前锻造
		tv_duanzaoData.setText(currentDuanzaozhi + "/" + maxDuanzaozhi);
		// 设置当前改造值
		tv_gaizaoData.setText(currentGaizaoZhi + "/5");
		// 设置传说化状态
		current_chuanshuohua = NO_CHUANSHUOHUA;
		operateChuanshuohuaMenu();
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
	public void onItemClick(AdapterView<?> adapterView, View arg1,
			int position, long arg3) {
		if (adapterView == lv_weaponType) {
			if (currentWeaponType != position) {
				currentWeaponType = position;
				weaponTypeAdapter.setCurrentType(currentWeaponType);
				weaponTypeAdapter.notifyDataSetChanged();
				currentWeaponRank = 4;
				maxDuanzaozhi = 24;
				this.clearDuanzao();
				this.setData();
				this.setTotalData();
			}
		}

	}

	@Override
	public void onClick(View v) {
		// 清除数据----------------------------------------->
		if (v == tv_clearData) {
			clearDuanzao();
			setData();
			operateYuanshiLLStatus();
			operateMoshi();
		}
		// 武器等级上升----------------------------------------->
		else if (v == tv_rankUp) {
			if (currentWeaponRank == 6) {
				Toast.makeText(getActivity(), "武器最高等级为Rank7", Toast.LENGTH_LONG)
						.show();
				return;
			}
			currentWeaponRank++;
			clearDuanzao();
			setData();
			currentYuanshi = YUANSHI_JINPAI;
			operateYuanshiLLStatus();
			operateMoshi();

		}
		// 武器等级下降----------------------------------------->
		else if (v == tv_rankDown) {
			if (currentWeaponRank == 0) {
				Toast.makeText(getActivity(), "武器最低等级为Rank1", Toast.LENGTH_LONG)
						.show();
				return;
			}
			currentWeaponRank--;
			clearDuanzao();
			setData();
			currentYuanshi = YUANSHI_JINPAI;
			operateYuanshiLLStatus();
			operateMoshi();

		}
		// 锻造次数上升----------------------------------------->
		else if (v == tv_duanzaoTimeUp) {
			if (currentGaizaoZhi == 5) {
				Toast.makeText(getActivity(), "已达到改造最大值", Toast.LENGTH_LONG)
						.show();
				return;
			}
			currentGaizaoZhi++;
			risedDuanzaozhi++;
			maxDuanzaozhi++;
			iv_upNarrow_duanzaoTime_rise.setVisibility(View.VISIBLE);
			tv_gaizaoData.setText(currentGaizaoZhi + "/5");
			tv_duanzaoData.setText(currentDuanzaozhi + "/" + maxDuanzaozhi); // 设置当前锻造
			// 设置各项基础属性下降值
			if (currentGaizao_type == ZHENGAI) {
				gaizao_base_rise_g -= 1;
				gaizao_base_rise_p -= 2;
				gaizao_base_rise_f -= 1;
				gaizao_base_rise_t -= 2;
				gaizao_base_rise_w -= 2;

			} else if (currentGaizao_type == JIANGAI) {
				gaizao_base_rise_g -= 2;
				gaizao_base_rise_p -= 3;
				gaizao_base_rise_f -= 2;
				gaizao_base_rise_t -= 4;
				gaizao_base_rise_w -= 4;
			}
			refreshBaseRise();
			operateBaseDataNarrow();
			setTotalData();
		}

		// 锻造模式按钮----------------------------------------->
		else if (v == tv_moshi_duanzao) {
			if (currentMoshi == CURRENT_MOSHI_DUANZAO) {
				return;
			}
			currentMoshi = CURRENT_MOSHI_DUANZAO;
			operateMoshi();
		}
		// 特殊强化模式按钮----------------------------------------->
		else if (v == tv_moshi_tesuqianghua) {
			if (currentMoshi == CURRENT_MOSHI_TESUQIANGHUA) {
				return;
			}
			currentMoshi = CURRENT_MOSHI_TESUQIANGHUA;
			operateMoshi();
		}
		// 改造模式按钮----------------------------------------->
		else if (v == tv_moshi_gaizao) {
			if (currentMoshi == CURRENT_MOSHI_GAIZAO) {
				return;
			}
			currentMoshi = CURRENT_MOSHI_GAIZAO;
			operateMoshi();
		}
		// 传说化模式按钮----------------------------------------->
		else if (v == tv_moshi_chuanshuohua) {
			if (currentMoshi == CURRENT_MOSHI_CHUANSHUOHUA) {
				return;
			}
			currentMoshi = CURRENT_MOSHI_CHUANSHUOHUA;
			operateMoshi();
		}

		// 原始金牌按钮----------------------------------------->
		else if (v == tv_yuanshi_jinpai) {
			if (currentYuanshi == YUANSHI_JINPAI) {
				return;
			}
			currentYuanshi = YUANSHI_JINPAI;
			clearDuanzao();
			setData();
			operateYuanshi();
		}
		// 原始25按钮----------------------------------------->
		else if (v == tv_yuanshi_25) {
			if (currentYuanshi == YUANSHI_25) {
				return;
			}
			currentYuanshi = YUANSHI_25;
			clearDuanzao();
			setData();
			operateYuanshi();
		}
		// 原始26按钮----------------------------------------->
		else if (v == tv_yuanshi_26) {
			if (currentYuanshi == YUANSHI_26) {
				return;
			}
			currentYuanshi = YUANSHI_26;
			clearDuanzao();
			setData();
			operateYuanshi();
		}
		// 原始28按钮----------------------------------------->
		else if (v == tv_yuanshi_28) {
			if (currentYuanshi == YUANSHI_28) {
				return;
			}
			currentYuanshi = YUANSHI_28;
			clearDuanzao();
			setData();
			operateYuanshi();
		}
		// 简改----------------------------------------->
		else if (v == tv_jiangai) {
			currentGaizao_type = JIANGAI;
			operateGaizao();
		}
		// 真改----------------------------------------->
		else if (v == tv_zhengai) {
			currentGaizao_type = ZHENGAI;
			operateGaizao();
		}
		// 传说化尖晶一阶----------------------------------------->
		else if (v == tv_jianjing1) {
			if (current_chuanshuohua == JIANJING1) {
				return;
			}
			current_chuanshuohua = JIANJING1;
			operateChuanshuohuaMenu();
		}
		// 传说化尖晶二阶----------------------------------------->
		else if (v == tv_jianjing2) {
			if (current_chuanshuohua == JIANJING2) {
				return;
			}
			current_chuanshuohua = JIANJING2;
			operateChuanshuohuaMenu();
		}
		// 传说化尖晶三阶----------------------------------------->
		else if (v == tv_jianjing3) {
			if (current_chuanshuohua == JIANJING3) {
				return;
			}
			current_chuanshuohua = JIANJING3;
			operateChuanshuohuaMenu();
		}
		// 传说化琉璃一阶----------------------------------------->
		else if (v == tv_liuli1) {
			if (current_chuanshuohua == LIULI1) {
				return;
			}
			current_chuanshuohua = LIULI1;
			operateChuanshuohuaMenu();
		}
		// 传说化琉璃二阶----------------------------------------->
		else if (v == tv_liuli2) {
			if (current_chuanshuohua == LIULI2) {
				return;
			}
			current_chuanshuohua = LIULI2;
			operateChuanshuohuaMenu();
		}
		// 传说化琉璃三阶----------------------------------------->
		else if (v == tv_liuli3) {
			if (current_chuanshuohua == LIULI3) {
				return;
			}
			current_chuanshuohua = LIULI3;
			operateChuanshuohuaMenu();
		}
		// 传说化玉滴一阶----------------------------------------->
		else if (v == tv_yudi1) {
			if (current_chuanshuohua == YUDI1) {
				return;
			}
			current_chuanshuohua = YUDI1;
			operateChuanshuohuaMenu();
		}
		// 传说化玉滴二阶----------------------------------------->
		else if (v == tv_yudi2) {
			if (current_chuanshuohua == YUDI2) {
				return;
			}
			current_chuanshuohua = YUDI2;
			operateChuanshuohuaMenu();
		}
		// 传说化玉滴三阶----------------------------------------->
		else if (v == tv_yudi3) {
			if (current_chuanshuohua == YUDI3) {
				return;
			}
			current_chuanshuohua = YUDI3;
			operateChuanshuohuaMenu();
		}
		// 改造上升值----------------------------------------->
		else if (v == tv_gaizao_shangshengzhi) {
			currentGaizao_type2 = GAIZAO_SHANGSHENGZHI;
			operateGaizao2();
		}
		// 改造基础值----------------------------------------->
		else if (v == tv_gaizao_jichuzhi) {
			currentGaizao_type2 = GAIZAO_JICHUZHI;
			operateGaizao2();
		}
		// 攻击锻造上升按钮------------------------------------>
		if (v == tv_gongjiUp) {
			if (currentMoshi == CURRENT_MOSHI_TESUQIANGHUA) {
				this.addTesuQianghua(1, currentTesuGongjiTime);
			} else if (currentMoshi == CURRENT_MOSHI_DUANZAO) {
				this.addQianghua(1, currentGongjiTime);
			} else if (currentMoshi == CURRENT_MOSHI_GAIZAO) {
				if (currentGaizao_type2 == GAIZAO_SHANGSHENGZHI) {
					this.upGaizaoGPFTW(1);
				} else if (currentGaizao_type2 == GAIZAO_JICHUZHI) {
					this.upGaizaoGPFTW2(1);
				}
			}
		}

		// 破坏锻造上升按钮------------------------------------>
		if (v == tv_pohuaiUp) {
			if (currentMoshi == CURRENT_MOSHI_TESUQIANGHUA) {
				this.addTesuQianghua(2, currentTesuPohuaiTime);
			} else if (currentMoshi == CURRENT_MOSHI_DUANZAO) {
				this.addQianghua(2, currentPohuaiTime);
			} else if (currentMoshi == CURRENT_MOSHI_GAIZAO) {
				if (currentGaizao_type2 == GAIZAO_SHANGSHENGZHI) {
					this.upGaizaoGPFTW(2);
				} else if (currentGaizao_type2 == GAIZAO_JICHUZHI) {
					this.upGaizaoGPFTW2(2);
				}

			}

		}

		// 防御锻造上升按钮------------------------------------>
		if (v == tv_fangyuUp) {
			if (currentMoshi == CURRENT_MOSHI_TESUQIANGHUA) {
				this.addTesuQianghua(3, currentTesuFangyuTime);
			} else if (currentMoshi == CURRENT_MOSHI_DUANZAO) {
				this.addQianghua(3, currentFangyuTime);
			} else if (currentMoshi == CURRENT_MOSHI_GAIZAO) {
				if (currentGaizao_type2 == GAIZAO_SHANGSHENGZHI) {
					this.upGaizaoGPFTW(3);
				} else if (currentGaizao_type2 == GAIZAO_JICHUZHI) {
					this.upGaizaoGPFTW2(3);
				}
			}

		}

		// 体力锻造上升按钮------------------------------------>
		if (v == tv_tiliUp) {
			if (currentMoshi == CURRENT_MOSHI_TESUQIANGHUA) {
				this.addTesuQianghua(4, currentTesuTiliTime);
			} else if (currentMoshi == CURRENT_MOSHI_DUANZAO) {
				this.addQianghua(4, currentTiliTime);
			} else if (currentMoshi == CURRENT_MOSHI_GAIZAO) {
				if (currentGaizao_type2 == GAIZAO_SHANGSHENGZHI) {
					this.upGaizaoGPFTW(4);
				} else if (currentGaizao_type2 == GAIZAO_JICHUZHI) {
					this.upGaizaoGPFTW2(4);
				}
			}

		}

		// 无双锻造上升按钮------------------------------------>
		if (v == tv_wushuangUp) {
			if (currentMoshi == CURRENT_MOSHI_TESUQIANGHUA) {
				this.addTesuQianghua(5, currentTesuWushuangTime);
			} else if (currentMoshi == CURRENT_MOSHI_DUANZAO) {
				this.addQianghua(5, currentWushuangTime);
			} else if (currentMoshi == CURRENT_MOSHI_GAIZAO) {
				if (currentGaizao_type2 == GAIZAO_SHANGSHENGZHI) {
					this.upGaizaoGPFTW(5);
				} else if (currentGaizao_type2 == GAIZAO_JICHUZHI) {
					this.upGaizaoGPFTW2(5);
				}
			}
		}

		// 移动上升按钮------------------------------------>
		else if (v == tv_yidongUp) {
			if (currentMoshi == CURRENT_MOSHI_TESUQIANGHUA) {
				this.addTesuQianghuaYidongOrTiaoyue(1, currentTesuYidongTime);
			} else if (currentMoshi == CURRENT_MOSHI_GAIZAO) {
				upGaizaoMoveOrJumo(1);
			}
		}

		// 跳跃上升按钮------------------------------------>
		else if (v == tv_tiaoyueUp) {
			if (currentMoshi == CURRENT_MOSHI_TESUQIANGHUA) {
				this.addTesuQianghuaYidongOrTiaoyue(2, currentTesuTiaoyueTime);
			}else if (currentMoshi == CURRENT_MOSHI_GAIZAO) {
				upGaizaoMoveOrJumo(2);
			}
		}

		// 设置各项属性值点数
		setTotalData();
	}

	private void operateYuanshiLLStatus() {
		if (currentWeaponRank >= 4) {
			ll_yuanshi.setVisibility(View.VISIBLE);
		} else {
			ll_yuanshi.setVisibility(View.GONE);
		}
		operateYuanshi();
	}

	/** 处理改造状态 */
	private void operateGaizao() {
		switch (currentGaizao_type) {
		case ZHENGAI:
			tv_zhengai
					.setBackgroundResource(R.drawable.borner_choose_left_selected);
			tv_jiangai
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			break;
		case JIANGAI:
			tv_zhengai
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_jiangai
					.setBackgroundResource(R.drawable.borner_choose_right_selected);
			break;
		}
	}

	/** 处理改造状态 (上升值，基础值) */
	private void operateGaizao2() {
		switch (currentGaizao_type2) {
		case GAIZAO_SHANGSHENGZHI:
			tv_gaizao_shangshengzhi
					.setBackgroundResource(R.drawable.borner_choose_left_selected);
			tv_gaizao_jichuzhi
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			tv_yidongUp.setVisibility(View.INVISIBLE);
			tv_tiaoyueUp.setVisibility(View.INVISIBLE);
			break;
		case GAIZAO_JICHUZHI:
			tv_gaizao_shangshengzhi
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_gaizao_jichuzhi
					.setBackgroundResource(R.drawable.borner_choose_right_selected);
			tv_yidongUp.setVisibility(View.VISIBLE);
			tv_tiaoyueUp.setVisibility(View.VISIBLE);
			break;
		}
	}

	/** 处理原始锻造状态 */
	private void operateYuanshi() {
		switch (currentYuanshi) {
		case YUANSHI_JINPAI:
			tv_yuanshi_jinpai
					.setBackgroundResource(R.drawable.borner_choose_left_selected);
			tv_yuanshi_25
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_yuanshi_26
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_yuanshi_28
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			if (currentWeaponRank == 0) {
				maxDuanzaozhi = 12;
			} else if (currentWeaponRank == 1) {
				maxDuanzaozhi = 16;
			} else if (currentWeaponRank == 2) {
				maxDuanzaozhi = 20;
			} else {
				maxDuanzaozhi = 24;
			}

			break;
		case YUANSHI_25:
			tv_yuanshi_jinpai
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_yuanshi_25
					.setBackgroundResource(R.drawable.borner_choose_middle_selected);
			tv_yuanshi_26
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_yuanshi_28
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			maxDuanzaozhi = 25;
			break;
		case YUANSHI_26:
			tv_yuanshi_jinpai
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_yuanshi_25
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_yuanshi_26
					.setBackgroundResource(R.drawable.borner_choose_middle_selected);
			tv_yuanshi_28
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			maxDuanzaozhi = 26;
			break;
		case YUANSHI_28:
			tv_yuanshi_jinpai
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_yuanshi_25
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_yuanshi_26
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_yuanshi_28
					.setBackgroundResource(R.drawable.borner_choose_right_selected);
			maxDuanzaozhi = 28;
			break;
		}

		tv_duanzaoData.setText(currentDuanzaozhi + "/" + maxDuanzaozhi); // 设置当前锻造
	}

	/** 处理模式状态 */
	private void operateMoshi() {
		switch (currentMoshi) {
		case CURRENT_MOSHI_DUANZAO:
			tv_moshi_duanzao
					.setBackgroundResource(R.drawable.borner_choose_left_selected);
			tv_moshi_tesuqianghua
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_moshi_gaizao
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_moshi_chuanshuohua
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			tv_gongjiUp.setVisibility(View.VISIBLE);
			tv_pohuaiUp.setVisibility(View.VISIBLE);
			tv_fangyuUp.setVisibility(View.VISIBLE);
			tv_tiliUp.setVisibility(View.VISIBLE);
			tv_wushuangUp.setVisibility(View.VISIBLE);
			tv_yidongUp.setVisibility(View.INVISIBLE);
			tv_tiaoyueUp.setVisibility(View.INVISIBLE);
			tv_duanzaoTimeUp.setVisibility(View.INVISIBLE);
			// 只有等级5以上的武器，才会有原始多段
			if (currentWeaponRank >= 4) {
				ll_yuanshi.setVisibility(View.VISIBLE);
			} else {
				ll_yuanshi.setVisibility(View.GONE);
			}
			ll_gaizaoMenu.setVisibility(View.GONE);
			ll_chuanshuohua_menu.setVisibility(View.GONE);
			break;
		case CURRENT_MOSHI_TESUQIANGHUA:
			tv_moshi_duanzao
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_moshi_tesuqianghua
					.setBackgroundResource(R.drawable.borner_choose_middle_selected);
			tv_moshi_gaizao
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_moshi_chuanshuohua
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			tv_gongjiUp.setVisibility(View.VISIBLE);
			tv_pohuaiUp.setVisibility(View.VISIBLE);
			tv_fangyuUp.setVisibility(View.VISIBLE);
			tv_tiliUp.setVisibility(View.VISIBLE);
			tv_wushuangUp.setVisibility(View.VISIBLE);
			tv_yidongUp.setVisibility(View.VISIBLE);
			tv_tiaoyueUp.setVisibility(View.INVISIBLE);
			tv_duanzaoTimeUp.setVisibility(View.INVISIBLE);
			ll_yuanshi.setVisibility(View.GONE);
			ll_gaizaoMenu.setVisibility(View.GONE);
			ll_chuanshuohua_menu.setVisibility(View.GONE);
			break;
		case CURRENT_MOSHI_GAIZAO:
			tv_moshi_duanzao
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_moshi_tesuqianghua
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_moshi_gaizao
					.setBackgroundResource(R.drawable.borner_choose_middle_selected);
			tv_moshi_chuanshuohua
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			tv_gongjiUp.setVisibility(View.VISIBLE);
			tv_pohuaiUp.setVisibility(View.VISIBLE);
			tv_fangyuUp.setVisibility(View.VISIBLE);
			tv_tiliUp.setVisibility(View.VISIBLE);
			tv_wushuangUp.setVisibility(View.VISIBLE);
			if (currentGaizao_type2 == GAIZAO_SHANGSHENGZHI) {
				tv_yidongUp.setVisibility(View.INVISIBLE);
				tv_tiaoyueUp.setVisibility(View.INVISIBLE);
			}
			else if(currentGaizao_type2 == GAIZAO_JICHUZHI){
				tv_yidongUp.setVisibility(View.VISIBLE);
				tv_tiaoyueUp.setVisibility(View.VISIBLE);
			}
			
			tv_duanzaoTimeUp.setVisibility(View.VISIBLE);
			ll_yuanshi.setVisibility(View.GONE);
			ll_gaizaoMenu.setVisibility(View.VISIBLE);
			ll_chuanshuohua_menu.setVisibility(View.GONE);
			break;
		case CURRENT_MOSHI_CHUANSHUOHUA:
			tv_moshi_duanzao
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_moshi_tesuqianghua
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_moshi_gaizao
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_moshi_chuanshuohua
					.setBackgroundResource(R.drawable.borner_choose_right_selected);
			tv_gongjiUp.setVisibility(View.INVISIBLE);
			tv_pohuaiUp.setVisibility(View.INVISIBLE);
			tv_fangyuUp.setVisibility(View.INVISIBLE);
			tv_tiliUp.setVisibility(View.INVISIBLE);
			tv_wushuangUp.setVisibility(View.INVISIBLE);
			tv_yidongUp.setVisibility(View.INVISIBLE);
			tv_tiaoyueUp.setVisibility(View.INVISIBLE);
			tv_duanzaoTimeUp.setVisibility(View.INVISIBLE);
			ll_yuanshi.setVisibility(View.GONE);
			ll_gaizaoMenu.setVisibility(View.GONE);
			ll_chuanshuohua_menu.setVisibility(View.VISIBLE);
			break;
		}
	}

	/** 处理传说化状态 */
	private void operateChuanshuohuaMenu() {
		switch (current_chuanshuohua) {
		case NO_CHUANSHUOHUA:
			tv_jianjing1
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_jianjing2
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_jianjing3
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			tv_liuli1
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_liuli2
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_liuli3
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			tv_yudi1.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_yudi2.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_yudi3.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			chuanshuohua_rise_g = 0;
			chuanshuohua_rise_p = 0;
			chuanshuohua_rise_f = 0;
			chuanshuohua_rise_t = 0;
			chuanshuohua_rise_w = 0;
			break;
		case JIANJING1:
			tv_jianjing1
					.setBackgroundResource(R.drawable.borner_choose_left_selected);
			tv_jianjing2
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_jianjing3
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			tv_liuli1
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_liuli2
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_liuli3
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			tv_yudi1.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_yudi2.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_yudi3.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			chuanshuohua_rise_g = 12;
			chuanshuohua_rise_p = 12;
			chuanshuohua_rise_f = 5;
			chuanshuohua_rise_t = 5;
			chuanshuohua_rise_w = 6;
			break;
		case JIANJING2:
			tv_jianjing1
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_jianjing2
					.setBackgroundResource(R.drawable.borner_choose_middle_selected);
			tv_jianjing3
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			tv_liuli1
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_liuli2
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_liuli3
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			tv_yudi1.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_yudi2.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_yudi3.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			chuanshuohua_rise_g = 23;
			chuanshuohua_rise_p = 23;
			chuanshuohua_rise_f = 9;
			chuanshuohua_rise_t = 9;
			chuanshuohua_rise_w = 12;
			break;
		case JIANJING3:
			tv_jianjing1
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_jianjing2
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_jianjing3
					.setBackgroundResource(R.drawable.borner_choose_right_selected);
			tv_liuli1
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_liuli2
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_liuli3
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			tv_yudi1.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_yudi2.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_yudi3.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			chuanshuohua_rise_g = 30;
			chuanshuohua_rise_p = 30;
			chuanshuohua_rise_f = 15;
			chuanshuohua_rise_t = 15;
			chuanshuohua_rise_w = 23;
			break;
		case LIULI1:
			tv_jianjing1
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_jianjing2
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_jianjing3
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			tv_liuli1
					.setBackgroundResource(R.drawable.borner_choose_left_selected);
			tv_liuli2
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_liuli3
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			tv_yudi1.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_yudi2.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_yudi3.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			chuanshuohua_rise_g = 8;
			chuanshuohua_rise_p = 8;
			chuanshuohua_rise_f = 8;
			chuanshuohua_rise_t = 8;
			chuanshuohua_rise_w = 8;
			break;
		case LIULI2:
			tv_jianjing1
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_jianjing2
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_jianjing3
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			tv_liuli1
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_liuli2
					.setBackgroundResource(R.drawable.borner_choose_middle_selected);
			tv_liuli3
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			tv_yudi1.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_yudi2.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_yudi3.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			chuanshuohua_rise_g = 15;
			chuanshuohua_rise_p = 15;
			chuanshuohua_rise_f = 15;
			chuanshuohua_rise_t = 15;
			chuanshuohua_rise_w = 15;
			break;
		case LIULI3:
			tv_jianjing1
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_jianjing2
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_jianjing3
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			tv_liuli1
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_liuli2
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_liuli3
					.setBackgroundResource(R.drawable.borner_choose_right_selected);
			tv_yudi1.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_yudi2.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_yudi3.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			chuanshuohua_rise_g = 23;
			chuanshuohua_rise_p = 23;
			chuanshuohua_rise_f = 23;
			chuanshuohua_rise_t = 23;
			chuanshuohua_rise_w = 23;
			break;
		case YUDI1:
			tv_jianjing1
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_jianjing2
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_jianjing3
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			tv_liuli1
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_liuli2
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_liuli3
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			tv_yudi1.setBackgroundResource(R.drawable.borner_choose_left_selected);
			tv_yudi2.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_yudi3.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			chuanshuohua_rise_g = 5;
			chuanshuohua_rise_p = 5;
			chuanshuohua_rise_f = 12;
			chuanshuohua_rise_t = 12;
			chuanshuohua_rise_w = 6;
			break;
		case YUDI2:
			tv_jianjing1
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_jianjing2
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_jianjing3
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			tv_liuli1
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_liuli2
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_liuli3
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			tv_yudi1.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_yudi2.setBackgroundResource(R.drawable.borner_choose_middle_selected);
			tv_yudi3.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			chuanshuohua_rise_g = 9;
			chuanshuohua_rise_p = 9;
			chuanshuohua_rise_f = 23;
			chuanshuohua_rise_t = 23;
			chuanshuohua_rise_w = 12;
			break;
		case YUDI3:
			tv_jianjing1
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_jianjing2
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_jianjing3
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			tv_liuli1
					.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_liuli2
					.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_liuli3
					.setBackgroundResource(R.drawable.borner_choose_right_not_selected);
			tv_yudi1.setBackgroundResource(R.drawable.borner_choose_left_not_selected);
			tv_yudi2.setBackgroundResource(R.drawable.borner_choose_middle_not_selected);
			tv_yudi3.setBackgroundResource(R.drawable.borner_choose_right_selected);
			chuanshuohua_rise_g = 15;
			chuanshuohua_rise_p = 15;
			chuanshuohua_rise_f = 30;
			chuanshuohua_rise_t = 30;
			chuanshuohua_rise_w = 23;
			break;
		}
		refreshBaseRise();
		operateBaseDataNarrow();
	}

	/** 添加锻造强化 */
	private void addQianghua(int type, int currentDuanzaoTime) {

		// 预先判断是否超过了最大锻造值
		if (calculateCurrentDuanzaoData() + (currentDuanzaoTime + 1) > maxDuanzaozhi) {
			Toast.makeText(getActivity(), "超过最大锻造值，不能再锻造了", Toast.LENGTH_SHORT)
					.show();
			return;
		}

		if (currentDuanzaoTime < 5) {

			switch (type) {
			case 1:
				riseGongjiWidth = (int) (persentWidth * (riseGongji + risedGongji));
				// 添加攻击锻
				DuanzaoView riseGongjiView = new DuanzaoView(getActivity(),
						true, riseGongjiWidth, totalHeight, 1);
				ll_gongjicao.addView(riseGongjiView, riseGongjiWidth,
						totalHeight);
				// 当前攻击锻造次数增加1
				currentGongjiTime++;
				break;
			case 2:
				risePohuaiWidth = (int) (persentWidth * (risePohuai + risedPohuai));
				// 添加破坏锻
				DuanzaoView risePohuaiView = new DuanzaoView(getActivity(),
						true, risePohuaiWidth, totalHeight, 2);
				ll_pohuaicao.addView(risePohuaiView, risePohuaiWidth,
						totalHeight);
				// 当前破坏锻造次数增加1
				currentPohuaiTime++;
				break;
			case 3:
				riseFangyuWidth = (int) (persentWidth * (riseFangyu + risedFangyu));
				// 添加防御锻
				DuanzaoView riseFangyuView = new DuanzaoView(getActivity(),
						true, riseFangyuWidth, totalHeight, 3);
				ll_fangyucao.addView(riseFangyuView, riseFangyuWidth,
						totalHeight);
				// 当前防御锻造次数增加1
				currentFangyuTime++;
				break;
			case 4:
				riseTiliWidth = (int) (persentWidth * (riseTili + risedTili));
				// 添加体力锻
				DuanzaoView riseTiliView = new DuanzaoView(getActivity(), true,
						riseTiliWidth, totalHeight, 4);
				ll_tilicao.addView(riseTiliView, riseTiliWidth, totalHeight);
				// 当前体力锻造次数增加1
				currentTiliTime++;
				break;
			case 5:
				riseWushuangWidth = (int) (persentWidth * (riseWushuang + risedWushuang));
				// 添加无双锻
				DuanzaoView riseWushuangView = new DuanzaoView(getActivity(),
						true, riseWushuangWidth, totalHeight, 5);
				ll_wushuangcao.addView(riseWushuangView, riseWushuangWidth,
						totalHeight);
				// 当前无双锻造次数增加1
				currentWushuangTime++;
				break;

			}
			currentDuanzaozhi = calculateCurrentDuanzaoData();
			// 设置当前锻造
			tv_duanzaoData.setText(currentDuanzaozhi + "/" + maxDuanzaozhi);
		} else {
			Toast.makeText(getActivity(), "最大只能锻造5次", Toast.LENGTH_SHORT)
					.show();
		}

	}

	/** 添加特殊强化(移动和跳跃) */
	private void addTesuQianghuaYidongOrTiaoyue(int type, int currentTesuTime) {
		if (currentTesuTime < 5) {

			switch (type) {
			case 1:
				tesu_base_rise_move++;
				
				currentTesuYidongTime++;
				tv_tesu_yidong.setVisibility(View.VISIBLE);
				tv_tesu_yidong.setText("+ " + currentTesuYidongTime);
				break;
			case 2:
				tesu_base_rise_jump++;
				
				// 当前特殊强化次数增加1
				currentTesuTiaoyueTime++;
				tv_tesu_tiaoyue.setVisibility(View.VISIBLE);
				tv_tesu_tiaoyue.setText("+ " + currentTesuTiaoyueTime);
				break;

			}
			refreshBaseRise();
		} else {
			Toast.makeText(getActivity(), "只能特殊强化5次", Toast.LENGTH_SHORT)
					.show();
		}

	}

	/** 改造移动跳跃基础上升 type : 1移动 2跳跃 */
	private void upGaizaoMoveOrJumo(int type) {
		if (5 - currentGaizaoZhi >= 1) {
			currentGaizaoZhi += 1;
			switch (type) {
			case 1:
				if (currentGaizao_type == ZHENGAI) {
					gaizao_base_rise_g -= 1;
					gaizao_base_rise_p -= 1;
					gaizao_base_rise_f -= 1;
					gaizao_base_rise_move += 2;
					gaizao_base_rise_jump -= 1;
				} else if (currentGaizao_type == JIANGAI) {
					gaizao_base_rise_g -= 2;
					gaizao_base_rise_p -= 2;
					gaizao_base_rise_f -= 2;
					gaizao_base_rise_move += 2;
					gaizao_base_rise_jump -= 2;
				}
				
				break;
			case 2:
				if (currentGaizao_type == ZHENGAI) {
					gaizao_base_rise_move -= 1;
					gaizao_base_rise_jump += 2;
				} else if (currentGaizao_type == JIANGAI) {
					gaizao_base_rise_t -= 2;
					gaizao_base_rise_w -= 2;
					gaizao_base_rise_move -= 1;
					gaizao_base_rise_jump += 2;
				}
				break;
			}
			refreshBaseRise();
			operateBaseDataNarrow();
			tv_gaizaoData.setText(currentGaizaoZhi + "/5");
		}
	}

	/** 改造GPFTW基础上升 */
	private void upGaizaoGPFTW2(int type) {
		if (5 - currentGaizaoZhi >= 1) {
			currentGaizaoZhi += 1;
			switch (type) {
			case 1:
				if (currentGaizao_type == ZHENGAI) {
					gaizao_base_rise_g += 4;
					gaizao_base_rise_f -= 2;
				} else if (currentGaizao_type == JIANGAI) {
					gaizao_base_rise_g += 4;
					gaizao_base_rise_f -= 4;
				}
				break;
			case 2:
				if (currentGaizao_type == ZHENGAI) {
					gaizao_base_rise_p += 6;
					gaizao_base_rise_f -= 2;
				} else if (currentGaizao_type == JIANGAI) {
					gaizao_base_rise_p += 6;
					gaizao_base_rise_f -= 4;
				}
				break;
			case 3:
				if (currentGaizao_type == ZHENGAI) {
					gaizao_base_rise_g -= 1;
					gaizao_base_rise_p -= 2;
					gaizao_base_rise_f += 4;
				} else if (currentGaizao_type == JIANGAI) {
					gaizao_base_rise_g -= 2;
					gaizao_base_rise_p -= 4;
					gaizao_base_rise_f += 4;
				}
				break;
			case 4:
				if (currentGaizao_type == ZHENGAI) {
					gaizao_base_rise_t += 8;
					gaizao_base_rise_w -= 4;
				} else if (currentGaizao_type == JIANGAI) {
					gaizao_base_rise_t += 8;
					gaizao_base_rise_w -= 8;
				}
				break;
			case 5:
				if (currentGaizao_type == ZHENGAI) {
					gaizao_base_rise_t -= 4;
					gaizao_base_rise_w += 8;
				} else if (currentGaizao_type == JIANGAI) {
					gaizao_base_rise_t -= 8;
					gaizao_base_rise_w += 8;
				}
				break;
			}
			refreshBaseRise();
			operateBaseDataNarrow();
			tv_gaizaoData.setText(currentGaizaoZhi + "/5");
		}

	}

	/** 改造GPFTW上升 */
	private void upGaizaoGPFTW(int type) {
		if (5 - currentGaizaoZhi >= 2) {
			currentGaizaoZhi += 2;
			int downPoint = 1;
			if (currentGaizao_type == ZHENGAI) {
				downPoint = 1;
			} else if (currentGaizao_type == JIANGAI) {
				downPoint = 2;
			}
			switch (type) {
			case 1:
				risedGongji += 5;
				risedPohuai -= downPoint;
				risedFangyu -= downPoint;
				risedTili -= downPoint;
				risedWushuang -= downPoint;
				operateRiseAll();
				break;
			case 2:
				risedGongji -= downPoint;
				risedPohuai += 5;
				risedFangyu -= downPoint;
				risedTili -= downPoint;
				risedWushuang -= downPoint;
				operateRiseAll();
				break;
			case 3:
				risedGongji -= downPoint;
				risedPohuai -= downPoint;
				risedFangyu += 5;
				risedTili -= downPoint;
				risedWushuang -= downPoint;
				operateRiseAll();
				break;
			case 4:
				risedGongji -= downPoint;
				risedPohuai -= downPoint;
				risedFangyu -= downPoint;
				risedTili += 10;
				risedWushuang -= downPoint;
				operateRiseAll();
				break;
			case 5:
				risedGongji -= downPoint;
				risedPohuai -= downPoint;
				risedFangyu -= downPoint;
				risedTili -= downPoint;
				risedWushuang += 10;
				operateRiseAll();
				break;
			}
		}
		tv_gaizaoData.setText(currentGaizaoZhi + "/5");
	}

	/** 添加特殊强化 */
	private void addTesuQianghua(int type, int currentTesuTime) {
		if (currentTesuTime < 4) {
			TesuqianghuaView tesuqianghuaView = new TesuqianghuaView(
					getActivity(), currentTesuTime, tesuWidth, tesuHeight);
			switch (type) {
			case 1:
				if (currentTesuTime == 0) {
					ll_tesucao_gongji.addView(tesuqianghuaView, tesuWidth,
							tesuHeight);
				} else {
					ll_tesucao_gongji.addView(tesuqianghuaView, tesuWidth2,
							tesuHeight);
				}
				// 当前特殊强化次数增加1
				currentTesuGongjiTime++;
				risedGongji++;
				operateRiseDateText(1);
				iv_rise_upNarrow_gongji.setVisibility(View.VISIBLE);
				refreshRise(1);
				break;
			case 2:
				if (currentTesuTime == 0) {
					ll_tesucao_pohuai.addView(tesuqianghuaView, tesuWidth,
							tesuHeight);
				} else {
					ll_tesucao_pohuai.addView(tesuqianghuaView, tesuWidth2,
							tesuHeight);
				}
				// 当前特殊强化次数增加1
				currentTesuPohuaiTime++;
				risedPohuai++;
				operateRiseDateText(2);
				iv_rise_upNarrow_pohuai.setVisibility(View.VISIBLE);
				refreshRise(2);
				break;
			case 3:
				if (currentTesuTime == 0) {
					ll_tesucao_fangyu.addView(tesuqianghuaView, tesuWidth,
							tesuHeight);
				} else {
					ll_tesucao_fangyu.addView(tesuqianghuaView, tesuWidth2,
							tesuHeight);
				}
				// 当前特殊强化次数增加1
				currentTesuFangyuTime++;
				risedFangyu++;
				operateRiseDateText(3);
				iv_rise_upNarrow_fangyu.setVisibility(View.VISIBLE);
				refreshRise(3);
				break;
			case 4:
				if (currentTesuTime == 0) {
					ll_tesucao_tili.addView(tesuqianghuaView, tesuWidth,
							tesuHeight);
				} else {
					ll_tesucao_tili.addView(tesuqianghuaView, tesuWidth2,
							tesuHeight);
				}
				// 当前特殊强化次数增加1
				currentTesuTiliTime++;
				risedTili++;
				operateRiseDateText(4);
				iv_rise_upNarrow_tili.setVisibility(View.VISIBLE);
				refreshRise(4);
				break;
			case 5:
				if (currentTesuTime == 0) {
					ll_tesucao_wushuang.addView(tesuqianghuaView, tesuWidth,
							tesuHeight);
				} else {
					ll_tesucao_wushuang.addView(tesuqianghuaView, tesuWidth2,
							tesuHeight);
				}
				// 当前特殊强化次数增加1
				currentTesuWushuangTime++;
				risedWushuang++;
				operateRiseDateText(5);
				iv_rise_upNarrow_wushuang.setVisibility(View.VISIBLE);
				refreshRise(5);
				break;

			}
		} else {
			Toast.makeText(getActivity(), "只能特殊强化4次", Toast.LENGTH_SHORT)
					.show();
		}

	}

	/** 设置各项属性值为多少 */
	private void setTotalData() {
		int childViewG = ll_gongjicao.getChildCount() - 1;
		int totalG = baseGongji + chuanshuohua_rise_g + gaizao_base_rise_g
				+ (riseGongji + risedGongji) * childViewG;
		tv_totalG.setText("攻击总数值：" + totalG);

		int childViewP = ll_pohuaicao.getChildCount() - 1;
		int totalP = basePohuai + chuanshuohua_rise_p + gaizao_base_rise_p
				+ (risePohuai + risedPohuai) * childViewP;
		tv_totalP.setText("破坏总数值：" + totalP);

		int childViewF = ll_fangyucao.getChildCount() - 1;
		int totalF = baseFangyu + chuanshuohua_rise_f + gaizao_base_rise_f
				+ (riseFangyu + risedFangyu) * childViewF;
		tv_totalF.setText("防御总数值：" + totalF);

		int childViewT = ll_tilicao.getChildCount() - 1;
		int totalT = baseTili + chuanshuohua_rise_t + gaizao_base_rise_t
				+ (riseTili + risedTili) * childViewT;
		tv_totalT.setText("体力总数值：" + totalT);

		int childViewW = ll_wushuangcao.getChildCount() - 1;
		int totalW = baseWushuang + chuanshuohua_rise_w + gaizao_base_rise_w
				+ (riseWushuang + risedWushuang) * childViewW;
		tv_totalW.setText("无双总数值：" + totalW);

		int totalMove = baseYidong + tesu_base_rise_move
				+ gaizao_base_rise_move;
		tv_totalMove.setText("移动总数值：" + totalMove);

		int totalJump = baseTiaoyue + tesu_base_rise_jump
				+ gaizao_base_rise_jump;
		tv_totalJump.setText("跳跃总数值：" + totalJump);
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
		risedDuanzaozhi = 0;
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
		currentTesuYidongTime = 0;
		currentTesuTiaoyueTime = 0;
		// 恢复和原来比上升的特殊强化数
		currentGaizaoZhi = 0;
		risedGongji = 0;
		risedPohuai = 0;
		risedFangyu = 0;
		risedTili = 0;
		risedWushuang = 0;
		// risedGongji_base = 0;
		// risedPohuai_base = 0;
		// risedFangyu_base = 0;
		// risedTili_base = 0;
		// risedWushuang_base = 0;
		chuanshuohua_rise_g = 0;
		chuanshuohua_rise_p = 0;
		chuanshuohua_rise_f = 0;
		chuanshuohua_rise_t = 0;
		chuanshuohua_rise_w = 0;
		gaizao_base_rise_g = 0;
		gaizao_base_rise_p = 0;
		gaizao_base_rise_f = 0;
		gaizao_base_rise_t = 0;
		gaizao_base_rise_w = 0;
		gaizao_base_rise_move = 0;
		gaizao_base_rise_jump = 0;
		tesu_base_rise_move = 0;
		tesu_base_rise_jump = 0;


		// 恢复传说化上升的GPFTW
		chuanshuohua_rise_g = 0;
		chuanshuohua_rise_p = 0;
		chuanshuohua_rise_f = 0;
		chuanshuohua_rise_t = 0;
		chuanshuohua_rise_w = 0;

		operateNarrow(1);
		operateNarrow(2);
		operateNarrow(3);
		operateNarrow(4);
		operateNarrow(5);
	}

	private void drawViewWidth(View view, int width) {
		LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) view
				.getLayoutParams();
		linearParams.width = width;
		view.setLayoutParams(linearParams);
	}

	/** 处理基础值上升还是下降箭头 */
	private void operateBaseDataNarrow() {
		int risedGongji_base = chuanshuohua_rise_g + gaizao_base_rise_g;
		if (risedGongji_base == 0) {
			iv_upNarrow_baseRise_g.setVisibility(View.GONE);
		} else if (risedGongji_base > 0) {
			iv_upNarrow_baseRise_g.setVisibility(View.VISIBLE);
			iv_upNarrow_baseRise_g.setImageResource(R.drawable.upnarrow);
		} else if (risedGongji_base < 0) {
			iv_upNarrow_baseRise_g.setVisibility(View.VISIBLE);
			iv_upNarrow_baseRise_g.setImageResource(R.drawable.downnarrow);
		}
		int risedPohuai_base = chuanshuohua_rise_p + gaizao_base_rise_p;
		if (risedPohuai_base == 0) {
			iv_upNarrow_baseRise_p.setVisibility(View.GONE);
		} else if (risedPohuai_base > 0) {
			iv_upNarrow_baseRise_p.setVisibility(View.VISIBLE);
			iv_upNarrow_baseRise_p.setImageResource(R.drawable.upnarrow);
		} else if (risedPohuai_base < 0) {
			iv_upNarrow_baseRise_p.setVisibility(View.VISIBLE);
			iv_upNarrow_baseRise_p.setImageResource(R.drawable.downnarrow);
		}
		int risedFangyu_base = chuanshuohua_rise_f + gaizao_base_rise_f;
		if (risedFangyu_base == 0) {
			iv_upNarrow_baseRise_f.setVisibility(View.GONE);
		} else if (risedFangyu_base > 0) {
			iv_upNarrow_baseRise_f.setVisibility(View.VISIBLE);
			iv_upNarrow_baseRise_f.setImageResource(R.drawable.upnarrow);
		} else if (risedFangyu_base < 0) {
			iv_upNarrow_baseRise_f.setVisibility(View.VISIBLE);
			iv_upNarrow_baseRise_f.setImageResource(R.drawable.downnarrow);
		}
		int risedTili_base = chuanshuohua_rise_t + gaizao_base_rise_t;
		if (risedTili_base == 0) {
			iv_upNarrow_baseRise_t.setVisibility(View.GONE);
		} else if (risedTili_base > 0) {
			iv_upNarrow_baseRise_t.setVisibility(View.VISIBLE);
			iv_upNarrow_baseRise_t.setImageResource(R.drawable.upnarrow);
		} else if (risedTili_base < 0) {
			iv_upNarrow_baseRise_t.setVisibility(View.VISIBLE);
			iv_upNarrow_baseRise_t.setImageResource(R.drawable.downnarrow);
		}
		int risedWushuang_base = chuanshuohua_rise_w + gaizao_base_rise_w;
		if (risedWushuang_base == 0) {
			iv_upNarrow_baseRise_w.setVisibility(View.GONE);
		} else if (risedWushuang_base > 0) {
			iv_upNarrow_baseRise_w.setVisibility(View.VISIBLE);
			iv_upNarrow_baseRise_w.setImageResource(R.drawable.upnarrow);
		} else if (risedWushuang_base < 0) {
			iv_upNarrow_baseRise_w.setVisibility(View.VISIBLE);
			iv_upNarrow_baseRise_w.setImageResource(R.drawable.downnarrow);
		}
		
		int risedMove_base = tesu_base_rise_move + gaizao_base_rise_move;
		if (risedMove_base == 0) {
			iv_upNarrow_yidong.setVisibility(View.GONE);
		} else if (risedMove_base > 0) {
			iv_upNarrow_yidong.setVisibility(View.VISIBLE);
			iv_upNarrow_yidong.setImageResource(R.drawable.upnarrow);
		} else if (risedMove_base < 0) {
			iv_upNarrow_yidong.setVisibility(View.VISIBLE);
			iv_upNarrow_yidong.setImageResource(R.drawable.downnarrow);
		}
		
		int risedJump_base = tesu_base_rise_jump + gaizao_base_rise_jump;
		if (risedJump_base == 0) {
			iv_upNarrow_tiaoyue.setVisibility(View.GONE);
		} else if (risedJump_base > 0) {
			iv_upNarrow_tiaoyue.setVisibility(View.VISIBLE);
			iv_upNarrow_tiaoyue.setImageResource(R.drawable.upnarrow);
		} else if (risedJump_base < 0) {
			iv_upNarrow_tiaoyue.setVisibility(View.VISIBLE);
			iv_upNarrow_tiaoyue.setImageResource(R.drawable.downnarrow);
		}
	}

	/** 处理上升还是下降箭头 */
	private void operateNarrow(int type) {
		switch (type) {
		case 1:
			if (risedGongji == 0) {
				iv_rise_upNarrow_gongji.setVisibility(View.GONE);
			} else if (risedGongji > 0) {
				iv_rise_upNarrow_gongji.setVisibility(View.VISIBLE);
				iv_rise_upNarrow_gongji.setImageResource(R.drawable.upnarrow);
			} else if (risedGongji < 0) {
				iv_rise_upNarrow_gongji.setVisibility(View.VISIBLE);
				iv_rise_upNarrow_gongji.setImageResource(R.drawable.downnarrow);
			}
			break;
		case 2:
			if (risedPohuai == 0) {
				iv_rise_upNarrow_pohuai.setVisibility(View.GONE);
			} else if (risedPohuai > 0) {
				iv_rise_upNarrow_pohuai.setVisibility(View.VISIBLE);
				iv_rise_upNarrow_pohuai.setImageResource(R.drawable.upnarrow);
			} else if (risedPohuai < 0) {
				iv_rise_upNarrow_pohuai.setVisibility(View.VISIBLE);
				iv_rise_upNarrow_pohuai.setImageResource(R.drawable.downnarrow);
			}
			break;
		case 3:
			if (risedFangyu == 0) {
				iv_rise_upNarrow_fangyu.setVisibility(View.GONE);
			} else if (risedFangyu > 0) {
				iv_rise_upNarrow_fangyu.setVisibility(View.VISIBLE);
				iv_rise_upNarrow_fangyu.setImageResource(R.drawable.upnarrow);
			} else if (risedFangyu < 0) {
				iv_rise_upNarrow_fangyu.setVisibility(View.VISIBLE);
				iv_rise_upNarrow_fangyu.setImageResource(R.drawable.downnarrow);
			}
			break;
		case 4:
			if (risedTili == 0) {
				iv_rise_upNarrow_tili.setVisibility(View.GONE);
			} else if (risedTili > 0) {
				iv_rise_upNarrow_tili.setVisibility(View.VISIBLE);
				iv_rise_upNarrow_tili.setImageResource(R.drawable.upnarrow);
			} else if (risedTili < 0) {
				iv_rise_upNarrow_tili.setVisibility(View.VISIBLE);
				iv_rise_upNarrow_tili.setImageResource(R.drawable.downnarrow);
			}
			break;
		case 5:
			if (risedWushuang == 0) {
				iv_rise_upNarrow_wushuang.setVisibility(View.GONE);
			} else if (risedWushuang > 0) {
				iv_rise_upNarrow_wushuang.setVisibility(View.VISIBLE);
				iv_rise_upNarrow_wushuang.setImageResource(R.drawable.upnarrow);
			} else if (risedWushuang < 0) {
				iv_rise_upNarrow_wushuang.setVisibility(View.VISIBLE);
				iv_rise_upNarrow_wushuang
						.setImageResource(R.drawable.downnarrow);
			}
			break;
		}
	}

	/** 刷新上升值text */
	private void operateRiseDateText(int type) {
		switch (type) {
		case 1:
			tv_rise_gongjili.setText("( + " + (riseGongji + risedGongji));
			break;
		case 2:
			tv_rise_pohuaili.setText("( + " + (risePohuai + risedPohuai));
			break;
		case 3:
			tv_rise_fangyuli.setText("( + " + (riseFangyu + risedFangyu));
			break;
		case 4:
			tv_rise_tili.setText("( + " + (riseTili + risedTili));
			break;
		case 5:
			tv_rise_wushuang.setText("( + " + (riseWushuang + risedWushuang));
			break;
		}
	}

	/** 刷新所有 */
	private void operateRiseAll() {
		refreshRise(1);
		refreshRise(2);
		refreshRise(3);
		refreshRise(4);
		refreshRise(5);
		operateRiseDateText(1);
		operateRiseDateText(2);
		operateRiseDateText(3);
		operateRiseDateText(4);
		operateRiseDateText(5);

	}

	/** 刷新传说化后，基础上升值控件的宽度 */
	private void refreshBaseRise() {
		DuanzaoView child_g = (DuanzaoView) ll_gongjicao.getChildAt(0);
		baseGongjiWidth = (int) (persentWidth * (baseGongji
				+ chuanshuohua_rise_g + gaizao_base_rise_g));
		this.drawViewWidth(child_g, baseGongjiWidth);
		child_g.changeWidth(baseGongjiWidth);
		child_g.invalidate();

		DuanzaoView child_p = (DuanzaoView) ll_pohuaicao.getChildAt(0);
		basePohuaiWidth = (int) (persentWidth * (basePohuai
				+ chuanshuohua_rise_p + gaizao_base_rise_p));
		this.drawViewWidth(child_p, basePohuaiWidth);
		child_p.changeWidth(basePohuaiWidth);
		child_p.invalidate();

		DuanzaoView child_f = (DuanzaoView) ll_fangyucao.getChildAt(0);
		baseFangyuWidth = (int) (persentWidth * (baseFangyu
				+ chuanshuohua_rise_f + gaizao_base_rise_f));
		this.drawViewWidth(child_f, baseFangyuWidth);
		child_f.changeWidth(baseFangyuWidth);
		child_f.invalidate();

		DuanzaoView child_t = (DuanzaoView) ll_tilicao.getChildAt(0);
		baseTiliWidth = (int) (persentWidth * (baseTili + chuanshuohua_rise_t + gaizao_base_rise_t));
		this.drawViewWidth(child_t, baseTiliWidth);
		child_t.changeWidth(baseTiliWidth);
		child_t.invalidate();

		DuanzaoView child_w = (DuanzaoView) ll_wushuangcao.getChildAt(0);
		baseWushuangWidth = (int) (persentWidth * (baseWushuang
				+ chuanshuohua_rise_w + gaizao_base_rise_w));
		this.drawViewWidth(child_w, baseWushuangWidth);
		child_w.changeWidth(baseWushuangWidth);
		child_w.invalidate();

		int baseYidongWidth = (int) (persentWidthYidong * (baseYidong
				+ tesu_base_rise_move + gaizao_base_rise_move));
		DuanzaoView view_yidong = (DuanzaoView) ll_yidongcao
				.getChildAt(0);
		this.drawViewWidth(view_yidong, baseYidongWidth);
		view_yidong.changeWidth(baseYidongWidth);
		view_yidong.invalidate();
		
		int baseTiaotueWidth = (int) (persentWidthYidong * (baseTiaoyue
				+ tesu_base_rise_jump + gaizao_base_rise_jump));
		DuanzaoView view_tiaoyue = (DuanzaoView) ll_tiaoyuecao
				.getChildAt(0);
		this.drawViewWidth(view_tiaoyue, baseTiaotueWidth);
		view_tiaoyue.changeWidth(baseTiaotueWidth);
		view_tiaoyue.invalidate();

		tv_base_gongjili.setText(baseGongji + gaizao_base_rise_g + "");
		tv_base_pohuaili.setText(basePohuai + gaizao_base_rise_p + "");
		tv_base_fangyuli.setText(baseFangyu + gaizao_base_rise_f + "");
		tv_base_tili.setText(baseTili + gaizao_base_rise_t + "");
		tv_base_wushuang.setText(baseWushuang + gaizao_base_rise_w + "");
		tv_base_yidong.setText(baseYidong + gaizao_base_rise_move + "");
		tv_base_tiaoyue.setText(baseTiaoyue + gaizao_base_rise_jump + "");


		if (chuanshuohua_rise_g > 0) {
			tv_chuanshuohua_data_g.setVisibility(View.VISIBLE);
			tv_chuanshuohua_data_g.setText("+" + chuanshuohua_rise_g);
		} else {
			tv_chuanshuohua_data_g.setVisibility(View.GONE);
		}

		if (chuanshuohua_rise_p > 0) {
			tv_chuanshuohua_data_p.setVisibility(View.VISIBLE);
			tv_chuanshuohua_data_p.setText("+" + chuanshuohua_rise_p);
		} else {
			tv_chuanshuohua_data_p.setVisibility(View.GONE);
		}

		if (chuanshuohua_rise_f > 0) {
			tv_chuanshuohua_data_f.setVisibility(View.VISIBLE);
			tv_chuanshuohua_data_f.setText("+" + chuanshuohua_rise_f);
		} else {
			tv_chuanshuohua_data_f.setVisibility(View.GONE);
		}

		if (chuanshuohua_rise_t > 0) {
			tv_chuanshuohua_data_t.setVisibility(View.VISIBLE);
			tv_chuanshuohua_data_t.setText("+" + chuanshuohua_rise_t);
		} else {
			tv_chuanshuohua_data_t.setVisibility(View.GONE);
		}

		if (chuanshuohua_rise_w > 0) {
			tv_chuanshuohua_data_w.setVisibility(View.VISIBLE);
			tv_chuanshuohua_data_w.setText("+" + chuanshuohua_rise_w);
		} else {
			tv_chuanshuohua_data_w.setVisibility(View.GONE);
		}
	}

	/**
	 * 刷新上升控件宽度
	 * 
	 * @param type
	 *            1攻击 2破坏 3防御 4体力 5无双
	 */
	private void refreshRise(int type) {
		switch (type) {
		case 1:
			for (int i = 1; i < ll_gongjicao.getChildCount(); i++) {
				DuanzaoView child = (DuanzaoView) ll_gongjicao.getChildAt(i);
				riseGongjiWidth = (int) (persentWidth * (riseGongji + risedGongji));
				this.drawViewWidth(child, riseGongjiWidth);
				child.changeWidth(riseGongjiWidth);
				child.invalidate();
			}
			operateNarrow(1);
			break;
		case 2:
			for (int i = 1; i < ll_pohuaicao.getChildCount(); i++) {
				DuanzaoView child = (DuanzaoView) ll_pohuaicao.getChildAt(i);
				risePohuaiWidth = (int) (persentWidth * (risePohuai + risedPohuai));
				this.drawViewWidth(child, risePohuaiWidth);
				child.changeWidth(risePohuaiWidth);
				child.invalidate();
			}
			operateNarrow(2);
			break;
		case 3:
			for (int i = 1; i < ll_fangyucao.getChildCount(); i++) {
				DuanzaoView child = (DuanzaoView) ll_fangyucao.getChildAt(i);
				riseFangyuWidth = (int) (persentWidth * (riseFangyu + risedFangyu));
				this.drawViewWidth(child, riseFangyuWidth);
				child.changeWidth(riseFangyuWidth);
				child.invalidate();
			}
			operateNarrow(3);
			break;
		case 4:
			for (int i = 1; i < ll_tilicao.getChildCount(); i++) {
				DuanzaoView child = (DuanzaoView) ll_tilicao.getChildAt(i);
				riseTiliWidth = (int) (persentWidth * (riseTili + risedTili));
				this.drawViewWidth(child, riseTiliWidth);
				child.changeWidth(riseTiliWidth);
				child.invalidate();
			}
			operateNarrow(4);
			break;
		case 5:
			for (int i = 1; i < ll_wushuangcao.getChildCount(); i++) {
				DuanzaoView child = (DuanzaoView) ll_wushuangcao.getChildAt(i);
				riseWushuangWidth = (int) (persentWidth * (riseWushuang + risedWushuang));
				this.drawViewWidth(child, riseWushuangWidth);
				child.changeWidth(riseWushuangWidth);
				child.invalidate();
			}
			operateNarrow(5);
			break;
		}

	}

	private void loadJinpaiData() {
		BmobQuery<WeaponJinpai> bmobQuery = new BmobQuery<WeaponJinpai>();
		// bmobQuery.setLimit(1000);
		bmobQuery.order("weaponId");
		bmobQuery.findObjects(getActivity(), new FindListener<WeaponJinpai>() {

			@Override
			public void onSuccess(List<WeaponJinpai> weapons) {

				// 把金牌武器数值实体类转化成json，并保存到sp
				String jinpaiJson = JSON.toJSON(weapons).toString();
				SharedPreferences sp = getActivity().getSharedPreferences(
						Constant.SP_NAME, Activity.MODE_PRIVATE);
				// 主动刷新数据，所以覆盖一下原来的数值
				SharedPreferences.Editor editor = sp.edit();
				editor.putString(Constant.SP_JINPAI_DATA, jinpaiJson).commit();
				weaponJinpais.clear();
				weaponJinpais.addAll(weapons);
				weaponTypeAdapter.notifyDataSetChanged();
				setData();
				setTotalData();
			}

			@Override
			public void onError(int arg0, String arg1) {
				Toast.makeText(getActivity(), "请打开网络，获取到金牌武器数据后，才可使用锻造模拟器功能",
						Toast.LENGTH_LONG).show();
			}
		});

	}

}
