package com.wlx.wsolandroid;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.wlx.wsolandroid.adapter.YijianReplyAdapter;
import com.wlx.wsolandroid.model.Information;
import com.wlx.wsolandroid.model.User;
import com.wlx.wsolandroid.model.Weilixishu;
import com.wlx.wsolandroid.model.Yijian;
import com.wlx.wsolandroid.model.YijianReply;
import com.wlx.wsolandroid.utils.Utils;
import com.wlx.wsolandroid.widget.MyActionBar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class YijianReplyActivity extends Activity implements OnRefreshListener {
	private SwipeRefreshLayout swipeRefreshLayout;
	private ListView listView;
	private YijianReplyAdapter adapter;
	private List<YijianReply> replys = new ArrayList<YijianReply>();

	private String objectId;
	private EditText et_reply;
	private ProgressDialog progressDialog;
	private AlertDialog.Builder builder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yijian_reply);

		objectId = getIntent().getStringExtra("objectId");

		this.initActionBar();
		this.initView();
		this.loadData();
	}

	private void initActionBar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle("意见回复一览");
		actionBar.setLeftEnable(true);
		actionBar.setRightText("回复");
		actionBar.setRightClickListenner(new OnClickListener() {

			@Override
			public void onClick(View v) {
				et_reply = new EditText(YijianReplyActivity.this);
				builder = new AlertDialog.Builder(YijianReplyActivity.this);
				builder.setTitle("回复").setView(et_reply)
						.setNegativeButton("取消", null);
				builder.setPositiveButton("提交",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								String reply = et_reply.getText().toString();
								if (TextUtils.isEmpty(reply)) {
									Toast.makeText(YijianReplyActivity.this,
											"请填写回复内容", Toast.LENGTH_LONG)
											.show();
									return;
								}
								upLoadReply();
							}
						});
				builder.show();
			}
		});
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	private void initView() {
		Utils.setAppBackgroundColor(YijianReplyActivity.this,
				findViewById(R.id.ll));
		swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
		// 顶部刷新的样式
		swipeRefreshLayout.setColorScheme(android.R.color.holo_red_light,
				android.R.color.holo_green_light,
				android.R.color.holo_blue_bright,
				android.R.color.holo_orange_light);
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setRefreshing(true);

		listView = (ListView) findViewById(R.id.lv);
		adapter = new YijianReplyAdapter(this, replys);
		listView.setAdapter(adapter);

	}

	private void loadData() {
		BmobQuery<YijianReply> bmobQuery = new BmobQuery<YijianReply>();
		bmobQuery.setLimit(1000);
		bmobQuery.order("-createdAt");
		bmobQuery.addWhereEqualTo("replyId", objectId);
		
		
		bmobQuery.findObjects(this, new FindListener<YijianReply>() {

			@Override
			public void onSuccess(List<YijianReply> lists) {
				replys.clear();
				swipeRefreshLayout.setRefreshing(false);
				replys.addAll(lists);
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onError(int arg0, String arg1) {
				swipeRefreshLayout.setRefreshing(false);
			}
		});
	}

	private void upLoadReply() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("正在提交您的回复，请稍后");
		progressDialog.show();

		YijianReply yijianReply = new YijianReply();
		yijianReply.setContent(et_reply.getText().toString());
		yijianReply.setReplyId(objectId);
		yijianReply.setFrom(0);
		yijianReply.setUsername(User.getCurrentUser(this, User.class).getUsername());
		yijianReply.setFromOS("Android  " + android.os.Build.VERSION.RELEASE);

		yijianReply.save(this, new SaveListener() {

			@Override
			public void onSuccess() {
				progressDialog.dismiss();
				Toast.makeText(YijianReplyActivity.this, "谢谢您的回复，祝您游戏愉快",
						Toast.LENGTH_LONG).show();
				swipeRefreshLayout.setRefreshing(true);
				loadData();
			}

			@Override
			public void onFailure(int code, String arg0) {
				progressDialog.dismiss();
				Toast.makeText(YijianReplyActivity.this, "提交回复失败，请重试",
						Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public void onRefresh() {
		loadData();
	}

}
