package com.wlx.wsolandroid.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wlx.wsolandroid.R;
import com.wlx.wsolandroid.model.Yijian;
import com.wlx.wsolandroid.model.YijianReply;

/**
 * 意见一览适配器
 * 
 * @author wanglixin
 */
public class YijianReplyAdapter extends BaseAdapter {
	

	private List<YijianReply> lists;

	private final Context context;

	public YijianReplyAdapter(Context context, List<YijianReply> lists) {
		this.context = context;
		this.lists = lists;
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	public void setData(List<YijianReply> lists) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_item_yijian_reply, null);
			holder.tv_reply = (TextView) convertView.findViewById(R.id.tv_reply);
			holder.tv_from = (TextView) convertView.findViewById(R.id.tv_from);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		YijianReply yijianReply = lists.get(position);

		//来自
		if (yijianReply.getFrom() == 0) {
			holder.tv_from.setTextColor(Color.WHITE);
			holder.tv_reply.setTextColor(Color.WHITE);
			holder.tv_from.setText("玩家回复：");
		}
		else if(yijianReply.getFrom() == 1){
			holder.tv_from.setTextColor(context.getResources().getColor(R.color.orange));
			holder.tv_reply.setTextColor(context.getResources().getColor(R.color.orange));
			holder.tv_from.setText("作者回复：");
		}
		// 回复
		holder.tv_reply.setText(yijianReply.getContent());
		

		return convertView;
	}

	private class ViewHolder {
		TextView tv_reply;
		TextView tv_from;
	}

}
