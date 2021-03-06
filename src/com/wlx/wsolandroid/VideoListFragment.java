package com.wlx.wsolandroid;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.DownloadListener;
import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.adapter.MusicListAdapter;
import com.wlx.wsolandroid.adapter.VideoListAdapter;
import com.wlx.wsolandroid.adapter.WeaponListAdapter;
import com.wlx.wsolandroid.constant.Constant;
import com.wlx.wsolandroid.model.Music;
import com.wlx.wsolandroid.model.Video;
import com.wlx.wsolandroid.model.WeaponJinpai;
import com.wlx.wsolandroid.model.Weilixishu;
import com.wlx.wsolandroid.model.Yijian;
import com.wlx.wsolandroid.utils.Utils;
import com.wlx.wsolandroid.widget.MyActionBar;
import com.wlx.wsolandroid.widget.NumberProgressBar;

public class VideoListFragment extends BaseFragment implements OnItemClickListener, OnRefreshListener {
	private ListView lv;
	private VideoListAdapter adapter;

	private List<Video> videos = new ArrayList<Video>();

	private SwipeRefreshLayout swipeRefreshLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_videolist, null);
		this.initActionBar(view);
		this.initView(view);
		return view;
	}

	private void initView(View view) {
		lv = (ListView) view.findViewById(R.id.lv);
		adapter = new VideoListAdapter(getActivity(), videos);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);

		swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
		// 顶部刷新的样式
		swipeRefreshLayout.setColorScheme(android.R.color.holo_red_light, android.R.color.holo_green_light, android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setRefreshing(true);
		loadData();

	}

	private void initActionBar(View view) {
		MyActionBar actionBar = new MyActionBar(getActivity());
		actionBar.setTitle("游戏PK视频");
		actionBar.setLeftEnable(true);
		actionBar.setLeftText("菜单");
		actionBar.setLeftClickListenner(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mClicklistener.menuClick();
			}
		});
		RelativeLayout actionbar = (RelativeLayout) view.findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	private void loadData() {
		BmobQuery<Video> bmobQuery = new BmobQuery<Video>();
		bmobQuery.setLimit(1000);
		bmobQuery.order("videoId");
		bmobQuery.findObjects(getActivity(), new FindListener<Video>() {

			@Override
			public void onSuccess(List<Video> lists) {
				swipeRefreshLayout.setRefreshing(false);
				videos.addAll(lists);
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onError(int arg0, String arg1) {
				swipeRefreshLayout.setRefreshing(false);
			}
		});

	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("视频列表"); // 统计页面
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("视频列表");
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		openVideo(videos.get(position).getVideoUrl());
	}

	@Override
	public void onRefresh() {
		videos.clear();
		this.loadData();
	}

	private void openVideo(String videoUrl) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		//String type = "video/* ";
		Uri uri = Uri.parse(videoUrl);
		intent.setData(uri);
		startActivity(intent);
	}

	private void uploadVideo() {
		String picPath = Utils.getDownLoadMusicDir() + "shipin8.mp4";
		final BmobFile bmobFile = new BmobFile(new File(picPath));
		bmobFile.uploadblock(getActivity(), new UploadFileListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				// bmobFile.getUrl()---返回的上传文件的地址（不带域名）
				// bmobFile.getFileUrl(context)--返回的上传文件的完整地址（带域名）
				System.out.println("上传成功--->" + bmobFile.getFileUrl(getActivity()));
			}

			@Override
			public void onProgress(Integer value) {
				// TODO Auto-generated method stub
				// 返回的上传进度（百分比）
				System.out.println("jindu--->" + value);
			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				System.out.println("上传失败--->");
			}
		});
	}
}
