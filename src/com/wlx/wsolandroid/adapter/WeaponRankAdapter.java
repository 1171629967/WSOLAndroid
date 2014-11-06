package com.wlx.wsolandroid.adapter;

import com.wlx.wsolandroid.R;
import com.wlx.wsolandroid.weapondata.R1WeaponData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WeaponRankAdapter extends BaseAdapter {
	private Context context;
	private String weaponRank[] = {"Rank1","Rank2","Rank3","Rank4","Rank5","Rank6","Rank7"};

	public WeaponRankAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {

		return weaponRank.length;
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
		
		return convertView;
	}

	private class ViewHolder {

		TextView tv_weaponRank;

	}

}
