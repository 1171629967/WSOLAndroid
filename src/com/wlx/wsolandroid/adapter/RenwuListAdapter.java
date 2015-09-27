package com.wlx.wsolandroid.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wlx.wsolandroid.R;
import com.wlx.wsolandroid.model.Renwu;
import com.wlx.wsolandroid.model.Yijian;

/**
 * 任务列表
 * 
 * @author wanglixin
 */
public class RenwuListAdapter extends BaseAdapter {
	

	private List<Renwu> lists;

	private final Context context;

	public RenwuListAdapter(Context context, List<Renwu> lists) {
		this.context = context;
		this.lists = lists;
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	public void setData(List<Renwu> lists) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_item_renwu_list, null);
			holder.tv_renwuName = (TextView) convertView.findViewById(R.id.tv_renwuName);
			holder.tv_renwuLevel = (TextView) convertView.findViewById(R.id.tv_renwuLevel);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Renwu renwu = lists.get(position);

		
		holder.tv_renwuName.setText((position+1)+".  "+renwu.getRenwuName());
		holder.tv_renwuLevel.setText(renwu.getRenwuLevel());

		return convertView;
	}

	private class ViewHolder {
		TextView tv_renwuName;
		TextView tv_renwuLevel;
	}

}
