package com.wlx.wsolandroid;

import cn.bmob.v3.listener.SaveListener;

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
 * 注册页面
 * 
 * @author wanglixin
 * 
 */
public class RegistActivity extends Activity implements OnClickListener {
	private EditText et_userName;
	private EditText et_password;
	private Button bt_confirm;
	private LinearLayout ll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);

		initActionBar();
		initView();

	}

	private void initActionBar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle("注册");
		actionBar.setLeftEnable(true);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	private void initView() {
		ll = (LinearLayout) findViewById(R.id.ll);
		Utils.setAppBackgroundColor(this, ll);
		et_userName = (EditText) findViewById(R.id.et_userName);
		et_password = (EditText) findViewById(R.id.et_password);
		bt_confirm = (Button) findViewById(R.id.bt_confirm);
		bt_confirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == bt_confirm) {
			if (TextUtils.isEmpty(et_userName.getText().toString())) {
				Toast.makeText(this, "邮箱不能为空", Toast.LENGTH_LONG).show();
				return;
			}			
			if (TextUtils.isEmpty(et_password.getText().toString())) {
				Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show();
				return;
			}
			
			registTask(et_userName.getText().toString(), et_password.getText().toString());
		}
	}
	
	private void  registTask(String userName ,String password) {
		User bu = new User();
		bu.setUsername(userName);
		bu.setPassword(password);
		bu.setEmail(userName);
		bu.signUp(this, new SaveListener() {
		    @Override
		    public void onSuccess() {
		       
		    	Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_LONG).show();
		    	startActivity(new Intent(RegistActivity.this,
						CompletePersonInfoActivity.class));
		    }
		    @Override
		    public void onFailure(int code, String msg) {
		        Toast.makeText(RegistActivity.this, "注册失败："+msg, Toast.LENGTH_LONG).show();
		    }
		});
	}

}
