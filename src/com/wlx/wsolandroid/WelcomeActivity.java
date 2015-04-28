package com.wlx.wsolandroid;

import java.util.Random;

import com.wlx.wsolandroid.application.WSOLApplication;
import com.wlx.wsolandroid.constant.Constant;
import com.wlx.wsolandroid.model.User;
import com.wlx.wsolandroid.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;

public class WelcomeActivity extends Activity {

	private View vLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		vLayout = findViewById(R.id.welcome_activity_layout);

		startImageAnimation();

	}

	private void startImageAnimation() {
		ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.1f, 1.0f,
				1.1f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
				ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(1500);
		vLayout.startAnimation(scaleAnimation);
		scaleAnimation.setFillAfter(true);
		scaleAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				User currentUser = User.getCurrentUser(WelcomeActivity.this,
						User.class);
				if (currentUser != null) {
					if (TextUtils.isEmpty(currentUser.getNickName())) {
						startActivity(new Intent(WelcomeActivity.this,
								CompletePersonInfoActivity.class));
					}
					else {
						startActivity(new Intent(WelcomeActivity.this,
								MainActivity.class));
					}
					
				} else {
					startActivity(new Intent(WelcomeActivity.this,
							LoginActivity.class));
				}
				finish();

			}
		});
	}

}
