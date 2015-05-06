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
 * 登录页面
 * 
 * @author wanglixin
 * 
 */
public class LoginActivity extends Activity implements OnClickListener {
	private EditText et_userName;
	private EditText et_password;
	private Button bt_login;
	private Button bt_regist;
	private LinearLayout ll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		initActionBar();
		initView();
		
		

	}

	private void initActionBar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle("登录");
		actionBar.setLeftEnable(false);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
		
	}

	private void initView() {
		ll = (LinearLayout) findViewById(R.id.ll);
		Utils.setAppBackgroundColor(this,  ll);
		et_userName = (EditText) findViewById(R.id.et_userName);
		et_password = (EditText) findViewById(R.id.et_password);
		bt_login = (Button) findViewById(R.id.bt_login);
		bt_regist = (Button) findViewById(R.id.bt_regist);
		bt_login.setOnClickListener(this);
		bt_regist.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == bt_login) {
			if (TextUtils.isEmpty(et_userName.getText().toString())) {
				Toast.makeText(this, "用户名不能为空", Toast.LENGTH_LONG).show();
				return;
			}			
			if (TextUtils.isEmpty(et_password.getText().toString())) {
				Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show();
				return;
			}
			
			loginTask(et_userName.getText().toString(), et_password.getText().toString());
		}
		else if(v == bt_regist){
			startActivity(new Intent(LoginActivity.this,RegistActivity.class));
		}
	}
	
	private void  loginTask(String userName ,String password) {
		User user = new User();		
		user.setUsername(userName);
		user.setPassword(password);
		user.login(this, new SaveListener() {
		    @Override
		    public void onSuccess() {
		        startActivity(new Intent(LoginActivity.this,MainActivity.class));
		        finish();
		    }
		    @Override
		    public void onFailure(int code, String msg) {
		    	Toast.makeText(LoginActivity.this, "登录失败："+msg, Toast.LENGTH_LONG).show();
		    }
		});
	}

}
