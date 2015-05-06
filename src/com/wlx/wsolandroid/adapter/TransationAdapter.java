package com.wlx.wsolandroid.adapter;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wlx.wsolandroid.R;
import com.wlx.wsolandroid.ScaleImageActivity;
import com.wlx.wsolandroid.model.Pianzi;
import com.wlx.wsolandroid.model.Transation;
import com.wlx.wsolandroid.model.WeaponJinpai;

/**
 * 吧主担保交易适配器
 * 
 * @author wanglixin
 */
public class TransationAdapter extends BaseAdapter {


	private List<Transation> lists;

	private final Context context;

	public TransationAdapter(Context context, List<Transation> lists) {
		this.context = context;
		this.lists = lists;
		
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	public void setData(List<Transation> lists) {
		this.lists = lists;
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
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_item_transation, null);
			
			holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			
			holder.tv_url = (TextView) convertView.findViewById(R.id.tv_url);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Transation transation = lists.get(position);

		holder.tv_title.setText(transation.getTitle());
		holder.tv_url.setText(transation.getUrl());

		return convertView;
	}

	private class ViewHolder {
	
		TextView tv_title;
		
		
		TextView tv_url;

	}

}
