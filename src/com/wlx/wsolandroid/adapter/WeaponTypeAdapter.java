package com.wlx.wsolandroid.adapter;

import java.util.List;

import com.wlx.wsolandroid.R;
import com.wlx.wsolandroid.model.WeaponJinpai;
import com.wlx.wsolandroid.weapondata.R1WeaponData;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WeaponTypeAdapter extends BaseAdapter {
	private Context context;
	private int currentType;//武器在列表中的位置 0：偃月刀  1：大斧
	private List<WeaponJinpai> weaponJinpais;
	
	public WeaponTypeAdapter(Context context,List<WeaponJinpai> weaponJinpais) {
		this.context = context;
		this.weaponJinpais = weaponJinpais;
		this.currentType = 0;
	}

	
	public void setCurrentType(int currentType){
		this.currentType = currentType;
	}
	
	@Override
	public int getCount() {
		return weaponJinpais.size();
	}

	
	
	
	
	@Override
	public Object getItem(int position) {
		return weaponJinpais.get(position);
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

	   WeaponJinpai weaponJinpai = (WeaponJinpai) getItem(position);
		holder.tv_weaponName.setText(weaponJinpai.getName());
		if (currentType == position) {
			holder.tv_weaponName.setBackgroundColor(context.getResources().getColor(R.color.orange));
		}
		else {
			holder.tv_weaponName.setBackgroundColor(Color.TRANSPARENT);
		}
		
		return convertView;
	}

	private class ViewHolder {

		TextView tv_weaponName;

	}

}
