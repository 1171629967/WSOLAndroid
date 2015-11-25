package com.wlx.wsolandroid.widget;

import com.wlx.wsolandroid.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class CustomDialogLoadMusic extends Dialog {
	private TextView tv_comfirn;
	private TextView tv_cancel;

	private android.view.View.OnClickListener cancelClickListener;
	private android.view.View.OnClickListener confirmClickListener;

	public CustomDialogLoadMusic(Context context) {
		super(context, R.style.barcode_dialog);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_if_load_music);
		setCanceledOnTouchOutside(false);
		tv_cancel = (TextView) findViewById(R.id.tv_cancel);
		tv_comfirn = (TextView) findViewById(R.id.tv_comfirn);
	}

	public void setCancelClick(
			android.view.View.OnClickListener canccelClickListener) {
		this.cancelClickListener = canccelClickListener;
		tv_cancel.setOnClickListener(canccelClickListener);
	}

	public void setConfrimClick(
			android.view.View.OnClickListener confirmClickListener) {
		this.confirmClickListener = confirmClickListener;
		tv_comfirn.setOnClickListener(confirmClickListener);

	}

}
