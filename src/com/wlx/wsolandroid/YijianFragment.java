package com.wlx.wsolandroid;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.listener.SaveListener;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.bmob.utils.BmobLog;
import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.model.Yijian;
import com.wlx.wsolandroid.widget.MyActionBar;

public class YijianFragment extends BaseFragment {
	private EditText et_tiebaName;
	private EditText et_gameName;
	private EditText et_qq;
	private EditText et_content;

	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_yijian, null);

		et_tiebaName = (EditText) view.findViewById(R.id.et_tiebaName);
		et_gameName = (EditText) view.findViewById(R.id.et_gameName);
		et_qq = (EditText) view.findViewById(R.id.et_qq);
		et_content = (EditText) view.findViewById(R.id.et_content);

		this.initActionBar(view);
		
		return view;
	}
	
	
	
	
	

	private void initActionBar(View view) {
		MyActionBar actionBar = new MyActionBar(getActivity());
		actionBar.setTitle("意见和建议");
		actionBar.setLeftEnable(true);
		actionBar.setLeftText("菜单");
		actionBar.setLeftClickListenner(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mClicklistener.menuClick();
			}
		});
		actionBar.setRightText("提交");
		actionBar.setRightClickListenner(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(et_content.getText().toString())) {
					Toast.makeText(getActivity(), "请填写您的意见和建议",
							Toast.LENGTH_LONG).show();
					return;
				}

				upLoadYijian();
			}
		});
		RelativeLayout actionbar = (RelativeLayout) view
				.findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	private void upLoadYijian() {
		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setMessage("正在提交您的意见，请稍后");
		progressDialog.show();

		Yijian yijian = new Yijian();
		yijian.setContent(et_content.getText().toString());
		yijian.setTiebaName(et_tiebaName.getText().toString());
		yijian.setGameName(et_gameName.getText().toString());
		yijian.setQq(et_qq.getText().toString());
		yijian.setFromOS("Android  " + android.os.Build.VERSION.RELEASE);

		yijian.save(getActivity(), new SaveListener() {

			@Override
			public void onSuccess() {
				progressDialog.dismiss();
				Toast.makeText(getActivity(), "谢谢您的反馈，祝您游戏愉快",
						Toast.LENGTH_LONG).show();
			}

			@Override
			public void onFailure(int code, String arg0) {
				progressDialog.dismiss();
				Toast.makeText(getActivity(), "提交失败，请重试", Toast.LENGTH_LONG)
						.show();
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();

		MobclickAgent.onPageStart("意见和建议");

	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("意见和建议");
	}

}
