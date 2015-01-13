package com.wlx.wsolandroid.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wlx.wsolandroid.R;
import com.wlx.wsolandroid.model.Yijian;

/**
 * 意见一览适配器
 * 
 * @author wanglixin
 */
public class YijianyilanAdapter extends BaseAdapter {
	

	private List<Yijian> lists;

	private final Context context;

	public YijianyilanAdapter(Context context, List<Yijian> lists) {
		this.context = context;
		this.lists = lists;
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	public void setData(List<Yijian> lists) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_item_yijianyilan, null);
			holder.tv_yijian = (TextView) convertView.findViewById(R.id.tv_yijian);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Yijian yijian = lists.get(position);

		// 意见
		holder.tv_yijian.setText(yijian.getContent());
		

		return convertView;
	}

	private class ViewHolder {
		TextView tv_yijian;

	}

}
