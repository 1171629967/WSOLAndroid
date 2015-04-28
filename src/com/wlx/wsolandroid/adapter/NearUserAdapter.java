package com.wlx.wsolandroid.adapter;

import java.util.List;

import net.tsz.afinal.FinalBitmap;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.bmob.BmobProFile;
import com.wlx.wsolandroid.R;
import com.wlx.wsolandroid.WelcomeActivity;
import com.wlx.wsolandroid.constant.Constant;
import com.wlx.wsolandroid.model.Music;
import com.wlx.wsolandroid.model.User;
import com.wlx.wsolandroid.model.Yijian;
import com.wlx.wsolandroid.model.YijianReply;

/**
 * 附近的用户适配器
 * 
 * @author wanglixin
 */
public class NearUserAdapter extends BaseAdapter {

	private List<User> lists;

	private final Context context;
	private FinalBitmap finalBitmap;
	private User currentUser;

	public NearUserAdapter(Context context, List<User> lists) {
		this.context = context;
		this.lists = lists;
		finalBitmap = FinalBitmap.create(context);
		currentUser = User.getCurrentUser(context, User.class);
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	public void setData(List<User> lists) {
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
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_item_near_user, null);
			holder.iv_avator = (ImageView) convertView
					.findViewById(R.id.iv_avator);
			holder.tv_distance = (TextView) convertView
					.findViewById(R.id.tv_distance);
			holder.tv_nickName = (TextView) convertView
					.findViewById(R.id.tv_nickName);
			holder.tv_address = (TextView) convertView
					.findViewById(R.id.tv_address);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		User user = lists.get(position);

		String trueFaceUrl = BmobProFile.getInstance(context).signURL(
				user.getFaceName(), user.getFaceUrl(), Constant.BMOB_ACCESSKEY,
				0, null);
		finalBitmap.display(holder.iv_avator, trueFaceUrl);

		holder.tv_address.setText(user.getLastAddress());
		holder.tv_nickName.setText(user.getNickName());

		int distance = (int)DistanceUtil.getDistance(new LatLng(currentUser.getLastGeoPoint()
				.getLatitude(), currentUser.getLastGeoPoint().getLongitude()),
				new LatLng(user
						.getLastGeoPoint().getLatitude(),user.getLastGeoPoint().getLongitude()));
		holder.tv_distance.setText(distance/1000+"km");
		

		return convertView;
	}

	private class ViewHolder {
		ImageView iv_avator;
		TextView tv_distance;
		TextView tv_nickName;
		TextView tv_address;
	}

}
