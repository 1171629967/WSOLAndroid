package com.wlx.wsolandroid;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.ResetPasswordListener;
import cn.bmob.v3.listener.SaveListener;

import com.umeng.analytics.MobclickAgent;
import com.wlx.wsolandroid.model.User;
import com.wlx.wsolandroid.utils.Utils;
import com.wlx.wsolandroid.widget.MyActionBar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * 找回密码页面
 * 
 * @author wanglixin
 * 
 */
public class FindPasswordActivity extends Activity implements OnClickListener {
	private EditText et_userName;
	private Button bt_sendEmail;

	private LinearLayout ll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_password);

		initActionBar();
		initView();

	}

	private void initActionBar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle("找回密码");
		actionBar.setLeftEnable(false);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);

	}

	private void initView() {
		ll = (LinearLayout) findViewById(R.id.ll);
		Utils.setAppBackgroundColor(this, ll);
		et_userName = (EditText) findViewById(R.id.et_userName);

		bt_sendEmail = (Button) findViewById(R.id.bt_sendEmail);

		bt_sendEmail.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == bt_sendEmail) {
			if (TextUtils.isEmpty(et_userName.getText().toString().trim())) {
				Toast.makeText(this, "请输入邮箱", Toast.LENGTH_LONG).show();
			} else {
				resetPassword(et_userName.getText().toString().trim());
			}
		}

	}

	private void resetPassword(String email) {
		bt_sendEmail.setEnabled(false);
		BmobUser.resetPassword(this, email, new ResetPasswordListener() {
			@Override
			public void onSuccess() {
				bt_sendEmail.setEnabled(true);
				Toast.makeText(FindPasswordActivity.this,
						"重置密码请求成功，请登录您的邮箱进行密码重置操作", Toast.LENGTH_LONG).show();
			}
	
			

			@Override
			public void onFailure(int code, String e) {
				bt_sendEmail.setEnabled(true);
				Toast.makeText(FindPasswordActivity.this, "重置密码失败" + e,
						Toast.LENGTH_LONG).show();
			}
		});
	}
	
	
	
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("找回密码");
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("找回密码");
	}

}
