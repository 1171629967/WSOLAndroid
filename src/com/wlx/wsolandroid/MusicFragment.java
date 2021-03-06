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
import com.wlx.wsolandroid.adapter.WeaponListAdapter;
import com.wlx.wsolandroid.constant.Constant;
import com.wlx.wsolandroid.model.Music;
import com.wlx.wsolandroid.model.WeaponJinpai;
import com.wlx.wsolandroid.model.Weilixishu;
import com.wlx.wsolandroid.model.Yijian;
import com.wlx.wsolandroid.music.PlayMusicActivity;
import com.wlx.wsolandroid.utils.Utils;
import com.wlx.wsolandroid.widget.CustomDialogLoadMusic;
import com.wlx.wsolandroid.widget.MyActionBar;
import com.wlx.wsolandroid.widget.NumberProgressBar;

public class MusicFragment extends BaseFragment implements OnItemClickListener,
		OnRefreshListener {
	private ListView lv;
	private MusicListAdapter adapter;

	private List<Music> musics = new ArrayList<Music>();

	private SwipeRefreshLayout swipeRefreshLayout;

	private NumberProgressBar numberProgressBar;
	
	private CustomDialogLoadMusic customDialogLoadMusic;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_musiclist, null);
		this.initActionBar(view);
		this.initView(view);
		return view;
	}

	private void initView(View view) {
		lv = (ListView) view.findViewById(R.id.lv);
		adapter = new MusicListAdapter(getActivity(), musics);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(this);

		numberProgressBar = (NumberProgressBar) view
				.findViewById(R.id.numberbar);

		swipeRefreshLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.swipeRefreshLayout);
		// 顶部刷新的样式
		swipeRefreshLayout.setColorScheme(android.R.color.holo_red_light,
				android.R.color.holo_green_light,
				android.R.color.holo_blue_bright,
				android.R.color.holo_orange_light);
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setRefreshing(true);
		loadData();

	}

	private void initActionBar(View view) {
		MyActionBar actionBar = new MyActionBar(getActivity());
		actionBar.setTitle("游戏音乐");
		actionBar.setLeftEnable(true);
		actionBar.setLeftText("菜单");
		actionBar.setLeftClickListenner(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mClicklistener.menuClick();
			}
		});
		RelativeLayout actionbar = (RelativeLayout) view
				.findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	private void loadData() {
		BmobQuery<Music> bmobQuery = new BmobQuery<Music>();
		bmobQuery.setLimit(1000);
		bmobQuery.order("musicId");
		bmobQuery.findObjects(getActivity(), new FindListener<Music>() {

			@Override
			public void onSuccess(List<Music> lists) {
				swipeRefreshLayout.setRefreshing(false);
				musics.addAll(lists);
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
		MobclickAgent.onPageStart("音乐列表"); // 统计页面
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("音乐列表");
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, final int position,
			long arg3) {

		final String downLoadPath = Utils.getDownLoadMusicDir()
				+ musics.get(position).getMusicFile().getFilename();
		if (downLoadPath != null && new File(downLoadPath).exists()) {
			openMusic(musics.get(position));
			return;
		}

		// 判断一下网络
		switch (Utils.GetNetype(getActivity())) {
		case -1:
			Toast.makeText(getActivity(), "当前网络状况不可用，请检查是否打开了网络", Toast.LENGTH_LONG).show();
			break;
		case 1:
			loadMusic(position, downLoadPath);
			break;
		case 2:			
		case 3:
			customDialogLoadMusic = new CustomDialogLoadMusic(getActivity());
			customDialogLoadMusic.show();
			customDialogLoadMusic.setCancelClick(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					customDialogLoadMusic.dismiss();
				}
			});
			customDialogLoadMusic.setConfrimClick(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					customDialogLoadMusic.dismiss();
					loadMusic(position, downLoadPath);
				}
			});
			break;
		default:
			break;
		}

		
	}

	@Override
	public void onRefresh() {
		musics.clear();
		this.loadData();
	}

	private void openMusic(Music music) {
		String downLoadPath = Utils.getDownLoadMusicDir()
				+ music.getMusicFile().getFilename();
		music.setData(downLoadPath);
		Intent intent = new Intent(getActivity(), PlayMusicActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("music", music);
		intent.putExtras(bundle);
		startActivity(intent);

		//
		// Uri uri = Uri.parse(musicPath);
		// Intent intent = new Intent(Intent.ACTION_VIEW);
		// //intent.addCategory(Intent.CATEGORY_APP_MUSIC);
		// intent.setDataAndType(uri, "audio/*");
		// startActivity(intent);
	}
	
	
	
	private void loadMusic(final int position,String downLoadPath){
		final String downLoadUrl = musics.get(position).getMusicFile()
				.getFileUrl(getActivity());
		FinalHttp finalHttp = new FinalHttp();
		finalHttp.download(downLoadUrl, downLoadPath, true,
				new AjaxCallBack<File>() {

					@Override
					public void onStart() {
						numberProgressBar.setVisibility(View.VISIBLE);
						numberProgressBar.setProgress(0);
						Toast.makeText(getActivity(), "开始下载音乐",
								Toast.LENGTH_SHORT).show();
						super.onStart();
					}

					@Override
					public void onLoading(long count, long current) {
						numberProgressBar
								.setProgress((int) (current * 100 / count));
						super.onLoading(count, current);
					}

					@Override
					public void onSuccess(File t) {
						numberProgressBar.setVisibility(View.GONE);
						Toast.makeText(getActivity(), "下载音乐成功",
								Toast.LENGTH_SHORT).show();
						openMusic(musics.get(position));
						super.onSuccess(t);
					}

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						numberProgressBar.setVisibility(View.GONE);
						Toast.makeText(getActivity(), "下载音乐失败：" + strMsg,
								Toast.LENGTH_LONG).show();
						super.onFailure(t, errorNo, strMsg);
					}

				});

	}

}
