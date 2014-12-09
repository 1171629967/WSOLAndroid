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
import com.wlx.wsolandroid.model.Weapon;

/**
 * 骗子名单列表适配器
 * 
 * @author wanglixin
 */
public class PianzimingdanAdapter extends BaseAdapter {
	private FinalBitmap finalBitmap;

	private List<Pianzi> lists;

	private final Context context;

	public PianzimingdanAdapter(Context context, List<Pianzi> lists) {
		this.context = context;
		this.lists = lists;
		finalBitmap = FinalBitmap.create(context);
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	public void setData(List<Pianzi> lists) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_item_pianzimingdan, null);
			holder.iv = (ImageView) convertView.findViewById(R.id.iv);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.ll_zhengju = (LinearLayout) convertView.findViewById(R.id.ll_zhengju);
			holder.tv_zhengju = (TextView) convertView.findViewById(R.id.tv_zhengju);
			holder.ll_beizhu = (LinearLayout) convertView.findViewById(R.id.ll_beizhu);
			holder.tv_beizhu = (TextView) convertView.findViewById(R.id.tv_beizhu);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Pianzi pianzi = lists.get(position);

		// 截图
		final String jietu = pianzi.getJietu();
		if (!TextUtils.isEmpty(jietu)) {
			holder.iv.setVisibility(View.VISIBLE);
			finalBitmap.display(holder.iv, jietu);		
		} else {
			holder.iv.setVisibility(View.INVISIBLE);
		}
		//头像监听
		holder.iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!TextUtils.isEmpty(jietu)) {
					Intent intent = new Intent(context,ScaleImageActivity.class);
					intent.putExtra("imgUrl", jietu);
					context.startActivity(intent);
				}
			}
		});
		// 名字
		holder.tv_name.setText(pianzi.getName());
		// 证据
		String zhengju = pianzi.getZhengjuurl();
		if (!TextUtils.isEmpty(zhengju)) {
			holder.ll_zhengju.setVisibility(View.VISIBLE);
			holder.tv_zhengju.setText(zhengju);
		} else {
			holder.ll_zhengju.setVisibility(View.GONE);
		}
		// 备注
		String beizhu = pianzi.getBeizhu();
		if (!TextUtils.isEmpty(beizhu)) {
			holder.ll_beizhu.setVisibility(View.VISIBLE);
			holder.tv_beizhu.setText(beizhu);
		} else {
			holder.ll_beizhu.setVisibility(View.GONE);
		}

		return convertView;
	}

	private class ViewHolder {
		ImageView iv;
		TextView tv_name;
		LinearLayout ll_zhengju;
		LinearLayout ll_beizhu;
		TextView tv_zhengju;
		TextView tv_beizhu;

	}

}
