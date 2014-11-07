package com.wlx.wsolandroid.adapter;

import com.wlx.wsolandroid.R;
import com.wlx.wsolandroid.weapondata.R1WeaponData;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WeaponRankAdapter extends BaseAdapter {
	private Context context;
	private String weaponRank[] = {"Rank1","Rank2","Rank3","Rank4","Rank5","Rank6","Rank7"};
	private int currentWeaponRank;
	
	public WeaponRankAdapter(Context context) {
		this.context = context;
		this.currentWeaponRank = 0;
	}

	@Override
	public int getCount() {
		return weaponRank.length;
	}

	
	public void setCurrentRank(int currentRank){
		this.currentWeaponRank = currentRank;
	}
	
	
	@Override
	public Object getItem(int position) {

		return null;
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_item_weaponrank, null);
			holder.tv_weaponRank = (TextView) convertView.findViewById(R.id.tv_weaponRank);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv_weaponRank.setText(weaponRank[position]);
		if (currentWeaponRank == position) {
			holder.tv_weaponRank.setBackgroundColor(context.getResources().getColor(R.color.orange));
		}
		else {
			holder.tv_weaponRank.setBackgroundColor(Color.TRANSPARENT);
		}
		
		return convertView;
	}

	private class ViewHolder {

		TextView tv_weaponRank;

	}

}
