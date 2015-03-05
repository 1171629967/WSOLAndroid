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
import com.wlx.wsolandroid.model.Music;
import com.wlx.wsolandroid.model.Yijian;
import com.wlx.wsolandroid.model.YijianReply;

/**
 * 音乐适配器
 * 
 * @author wanglixin
 */
public class MusicListAdapter extends BaseAdapter {
	

	private List<Music> lists;

	private final Context context;

	public MusicListAdapter(Context context, List<Music> lists) {
		this.context = context;
		this.lists = lists;
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	public void setData(List<Music> lists) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_item_music, null);
			holder.tv_musicName = (TextView) convertView.findViewById(R.id.tv_musicName);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Music music = lists.get(position);

		
	
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append((position+1)).append(".  ").append(music.getMusicName()).append(".mp3");
		holder.tv_musicName.setText(stringBuilder.toString());
		

		return convertView;
	}

	private class ViewHolder {
		TextView tv_musicName;
	}

}
