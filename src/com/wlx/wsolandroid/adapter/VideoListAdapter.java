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
import com.wlx.wsolandroid.model.Video;
import com.wlx.wsolandroid.model.Yijian;
import com.wlx.wsolandroid.model.YijianReply;

/**
 * 视频适配器
 * 
 * @author wanglixin
 */
public class VideoListAdapter extends BaseAdapter {
	

	private List<Video> lists;

	private final Context context;

	public VideoListAdapter(Context context, List<Video> lists) {
		this.context = context;
		this.lists = lists;
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	public void setData(List<Video> lists) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_item_video, null);
			holder.tv_videoName = (TextView) convertView.findViewById(R.id.tv_videoName);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Video video = lists.get(position);

		
	
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append((position+1)).append(".  ").append(video.getVideoName());
		holder.tv_videoName.setText(stringBuilder.toString());
		

		return convertView;
	}

	private class ViewHolder {
		TextView tv_videoName;
	}

}
