package com.wlx.wsolandroid;

import net.tsz.afinal.FinalBitmap;

import com.polites.android.GestureImageView;

import android.app.Activity;
import android.os.Bundle;

public class ScaleImageActivity extends Activity{
	private GestureImageView gestureImageView;
	private String imgUrl;
	private FinalBitmap finalBitmap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scaleimage);
		imgUrl = getIntent().getStringExtra("imgUrl");
		
		gestureImageView = (GestureImageView) findViewById(R.id.image);
		finalBitmap = FinalBitmap.create(this);
		finalBitmap.display(gestureImageView, imgUrl);
	}

}
