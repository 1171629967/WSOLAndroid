package com.wlx.wsolandroid.adapter;

import com.wlx.wsolandroid.R;
import com.wlx.wsolandroid.weapondata.R1WeaponData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WeaponTypeAdapter extends BaseAdapter {
	private Context context;
	private String weaponType[] ;

	public WeaponTypeAdapter(Context context) {
		this.context = context;
		weaponType = R1WeaponData.R1Names;
	}

	@Override
	public int getCount() {

		return weaponType.length;
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
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_item_weapontype, null);
			holder.tv_weaponName = (TextView) convertView.findViewById(R.id.tv_weaponName);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv_weaponName.setText(weaponType[position]);
		
		return convertView;
	}

	private class ViewHolder {

		TextView tv_weaponName;

	}

}
