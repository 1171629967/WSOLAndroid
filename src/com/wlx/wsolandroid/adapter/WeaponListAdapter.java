package com.wlx.wsolandroid.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wlx.wsolandroid.R;
import com.wlx.wsolandroid.model.WeaponJinpai;

/**
 * 金牌武器上升值列表适配器
 * 
 * @author wanglixin
 */
public class WeaponListAdapter extends BaseAdapter {

	private List<WeaponJinpai> lists = new ArrayList<WeaponJinpai>();

	private final Context context;

	public WeaponListAdapter(Context context, List<WeaponJinpai> lists) {
		this.context = context;
		this.lists = lists;
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	public void setData(List<WeaponJinpai> lists) {
		this.lists = lists;
	}

	public List<WeaponJinpai> getDate() {
		return lists;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_item_weapon, null);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_r1 = (TextView) convertView.findViewById(R.id.tv_r1);
			holder.tv_r2 = (TextView) convertView.findViewById(R.id.tv_r2);
			holder.tv_r3 = (TextView) convertView.findViewById(R.id.tv_r3);
			holder.tv_r4 = (TextView) convertView.findViewById(R.id.tv_r4);
			holder.tv_r5 = (TextView) convertView.findViewById(R.id.tv_r5);
			holder.tv_r6 = (TextView) convertView.findViewById(R.id.tv_r6);
			holder.tv_r7 = (TextView) convertView.findViewById(R.id.tv_r7);
			holder.tv_base = (TextView) convertView.findViewById(R.id.tv_base);
			holder.tv_moveAndJump = (TextView) convertView.findViewById(R.id.tv_moveAndJump);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		WeaponJinpai weapon = lists.get(position);

		StringBuilder sb1 = new StringBuilder("R1   ");
		sb1.append(weapon.getG()).append("  ").append(weapon.getP())
				.append("  ").append(weapon.getF()).append("  ")
				.append(weapon.getT()).append("  ").append(weapon.getW());

		StringBuilder sb2 = new StringBuilder("R2   ");
		sb2.append((weapon.getG() + 3)).append("  ")
				.append((weapon.getP() + 3)).append("  ")
				.append((weapon.getF() + 3)).append("  ")
				.append((weapon.getT() + 3)).append("  ")
				.append((weapon.getW() + 3));

		StringBuilder sb3 = new StringBuilder("R3   ");
		sb3.append((weapon.getG() + 6)).append("  ")
				.append((weapon.getP() + 6)).append("  ")
				.append((weapon.getF() + 6)).append("  ")
				.append((weapon.getT() + 6)).append("  ")
				.append((weapon.getW() + 6));

		StringBuilder sb4 = new StringBuilder("R4   ");
		sb4.append((weapon.getG() + 9)).append("  ")
				.append((weapon.getP() + 9)).append("  ")
				.append((weapon.getF() + 9)).append("  ")
				.append((weapon.getT() + 9)).append("  ")
				.append((weapon.getW() + 9));

		StringBuilder sb5 = new StringBuilder("R5   ");
		sb5.append((weapon.getG() + 12)).append("  ")
				.append((weapon.getP() + 12)).append("  ")
				.append((weapon.getF() + 12)).append("  ")
				.append((weapon.getT() + 12)).append("  ")
				.append((weapon.getW() + 12));

		StringBuilder sb6 = new StringBuilder("R6   ");
		sb6.append((weapon.getG() + 15)).append("  ")
				.append((weapon.getP() + 15)).append("  ")
				.append((weapon.getF() + 15)).append("  ")
				.append((weapon.getT() + 15)).append("  ")
				.append((weapon.getW() + 15));

		StringBuilder sb7 = new StringBuilder("R7   ");
		sb7.append((weapon.getG() + 18)).append("  ")
				.append((weapon.getP() + 18)).append("  ")
				.append((weapon.getF() + 18)).append("  ")
				.append((weapon.getT() + 18)).append("  ")
				.append((weapon.getW() + 18));
		
		StringBuilder sb_base = new StringBuilder("基础   ");
		sb_base.append((weapon.getG_base())).append("  ")
				.append((weapon.getP_base())).append("  ")
				.append((weapon.getF_base())).append("  ")
				.append((weapon.getT_base())).append("  ")
				.append((weapon.getW_base()));
		
		StringBuilder sb_moveAndJump = new StringBuilder("移动   ");
		sb_moveAndJump.append((weapon.getMove())).append("   跳跃   ")
				.append((weapon.getJump()));

		holder.tv_name.setText(weapon.getName());
		holder.tv_r1.setText(sb1);
		holder.tv_r2.setText(sb2);
		holder.tv_r3.setText(sb3);
		holder.tv_r4.setText(sb4);
		holder.tv_r5.setText(sb5);
		holder.tv_r6.setText(sb6);
		holder.tv_r7.setText(sb7);
		holder.tv_base.setText(sb_base);
		holder.tv_moveAndJump.setText(sb_moveAndJump);
		

		return convertView;
	}

	private class ViewHolder {

		TextView tv_name;
		TextView tv_r1;
		TextView tv_r2;
		TextView tv_r3;
		TextView tv_r4;
		TextView tv_r5;
		TextView tv_r6;
		TextView tv_r7;
		TextView tv_base;
		TextView tv_moveAndJump;
	}

}
